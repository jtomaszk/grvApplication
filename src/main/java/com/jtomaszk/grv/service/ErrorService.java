package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.Error;
import com.jtomaszk.grv.repository.ErrorRepository;
import com.jtomaszk.grv.repository.search.ErrorSearchRepository;
import com.jtomaszk.grv.service.dto.ErrorDTO;
import com.jtomaszk.grv.service.mapper.ErrorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Error.
 */
@Service
@Transactional
public class ErrorService {

    private final Logger log = LoggerFactory.getLogger(ErrorService.class);

    private final ErrorRepository errorRepository;

    private final ErrorMapper errorMapper;

    private final ErrorSearchRepository errorSearchRepository;

    public ErrorService(ErrorRepository errorRepository, ErrorMapper errorMapper, ErrorSearchRepository errorSearchRepository) {
        this.errorRepository = errorRepository;
        this.errorMapper = errorMapper;
        this.errorSearchRepository = errorSearchRepository;
    }

    /**
     * Save a error.
     *
     * @param errorDTO the entity to save
     * @return the persisted entity
     */
    public ErrorDTO save(ErrorDTO errorDTO) {
        log.debug("Request to save Error : {}", errorDTO);
        Error error = errorMapper.toEntity(errorDTO);
        error = errorRepository.save(error);
        ErrorDTO result = errorMapper.toDto(error);
        errorSearchRepository.save(error);
        return result;
    }

    /**
     * Get all the errors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Errors");
        return errorRepository.findAll(pageable)
            .map(errorMapper::toDto);
    }

    /**
     * Get one error by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ErrorDTO findOne(Long id) {
        log.debug("Request to get Error : {}", id);
        Error error = errorRepository.findOne(id);
        return errorMapper.toDto(error);
    }

    /**
     * Delete the error by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Error : {}", id);
        errorRepository.delete(id);
        errorSearchRepository.delete(id);
    }

    /**
     * Search for the error corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ErrorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Errors for query {}", query);
        Page<Error> result = errorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(errorMapper::toDto);
    }
}
