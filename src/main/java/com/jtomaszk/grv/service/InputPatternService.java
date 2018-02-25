package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.repository.InputPatternRepository;
import com.jtomaszk.grv.repository.search.InputPatternSearchRepository;
import com.jtomaszk.grv.service.dto.InputPatternDTO;
import com.jtomaszk.grv.service.mapper.InputPatternMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing InputPattern.
 */
@Service
@Transactional
public class InputPatternService {

    private final Logger log = LoggerFactory.getLogger(InputPatternService.class);

    private final InputPatternRepository inputPatternRepository;

    private final InputPatternMapper inputPatternMapper;

    private final InputPatternSearchRepository inputPatternSearchRepository;

    public InputPatternService(InputPatternRepository inputPatternRepository, InputPatternMapper inputPatternMapper, InputPatternSearchRepository inputPatternSearchRepository) {
        this.inputPatternRepository = inputPatternRepository;
        this.inputPatternMapper = inputPatternMapper;
        this.inputPatternSearchRepository = inputPatternSearchRepository;
    }

    /**
     * Save a inputPattern.
     *
     * @param inputPatternDTO the entity to save
     * @return the persisted entity
     */
    public InputPatternDTO save(InputPatternDTO inputPatternDTO) {
        log.debug("Request to save InputPattern : {}", inputPatternDTO);
        InputPattern inputPattern = inputPatternMapper.toEntity(inputPatternDTO);
        inputPattern = inputPatternRepository.save(inputPattern);
        InputPatternDTO result = inputPatternMapper.toDto(inputPattern);
        inputPatternSearchRepository.save(inputPattern);
        return result;
    }

    /**
     * Get all the inputPatterns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InputPatternDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InputPatterns");
        return inputPatternRepository.findAll(pageable)
            .map(inputPatternMapper::toDto);
    }

    /**
     * Get one inputPattern by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InputPatternDTO findOne(Long id) {
        log.debug("Request to get InputPattern : {}", id);
        InputPattern inputPattern = inputPatternRepository.findOne(id);
        return inputPatternMapper.toDto(inputPattern);
    }

    /**
     * Delete the inputPattern by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InputPattern : {}", id);
        inputPatternRepository.delete(id);
        inputPatternSearchRepository.delete(id);
    }

    /**
     * Search for the inputPattern corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InputPatternDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InputPatterns for query {}", query);
        Page<InputPattern> result = inputPatternSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(inputPatternMapper::toDto);
    }
}
