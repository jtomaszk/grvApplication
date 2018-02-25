package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.ParseError;
import com.jtomaszk.grv.repository.ParseErrorRepository;
import com.jtomaszk.grv.repository.search.ParseErrorSearchRepository;
import com.jtomaszk.grv.service.dto.ParseErrorDTO;
import com.jtomaszk.grv.service.mapper.ParseErrorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParseError.
 */
@Service
@Transactional
public class ParseErrorService {

    private final Logger log = LoggerFactory.getLogger(ParseErrorService.class);

    private final ParseErrorRepository parseErrorRepository;

    private final ParseErrorMapper parseErrorMapper;

    private final ParseErrorSearchRepository parseErrorSearchRepository;

    public ParseErrorService(ParseErrorRepository parseErrorRepository, ParseErrorMapper parseErrorMapper, ParseErrorSearchRepository parseErrorSearchRepository) {
        this.parseErrorRepository = parseErrorRepository;
        this.parseErrorMapper = parseErrorMapper;
        this.parseErrorSearchRepository = parseErrorSearchRepository;
    }

    /**
     * Save a parseError.
     *
     * @param parseErrorDTO the entity to save
     * @return the persisted entity
     */
    public ParseErrorDTO save(ParseErrorDTO parseErrorDTO) {
        log.debug("Request to save ParseError : {}", parseErrorDTO);
        ParseError parseError = parseErrorMapper.toEntity(parseErrorDTO);
        parseError = parseErrorRepository.save(parseError);
        ParseErrorDTO result = parseErrorMapper.toDto(parseError);
        parseErrorSearchRepository.save(parseError);
        return result;
    }

    /**
     * Get all the parseErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ParseErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParseErrors");
        return parseErrorRepository.findAll(pageable)
            .map(parseErrorMapper::toDto);
    }

    /**
     * Get one parseError by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ParseErrorDTO findOne(Long id) {
        log.debug("Request to get ParseError : {}", id);
        ParseError parseError = parseErrorRepository.findOne(id);
        return parseErrorMapper.toDto(parseError);
    }

    /**
     * Delete the parseError by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ParseError : {}", id);
        parseErrorRepository.delete(id);
        parseErrorSearchRepository.delete(id);
    }

    /**
     * Search for the parseError corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ParseErrorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParseErrors for query {}", query);
        Page<ParseError> result = parseErrorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(parseErrorMapper::toDto);
    }
}
