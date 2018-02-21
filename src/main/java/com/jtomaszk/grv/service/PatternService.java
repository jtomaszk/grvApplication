package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.Pattern;
import com.jtomaszk.grv.repository.PatternRepository;
import com.jtomaszk.grv.repository.search.PatternSearchRepository;
import com.jtomaszk.grv.service.dto.PatternDTO;
import com.jtomaszk.grv.service.mapper.PatternMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Pattern.
 */
@Service
@Transactional
public class PatternService {

    private final Logger log = LoggerFactory.getLogger(PatternService.class);

    private final PatternRepository patternRepository;

    private final PatternMapper patternMapper;

    private final PatternSearchRepository patternSearchRepository;

    public PatternService(PatternRepository patternRepository, PatternMapper patternMapper, PatternSearchRepository patternSearchRepository) {
        this.patternRepository = patternRepository;
        this.patternMapper = patternMapper;
        this.patternSearchRepository = patternSearchRepository;
    }

    /**
     * Save a pattern.
     *
     * @param patternDTO the entity to save
     * @return the persisted entity
     */
    public PatternDTO save(PatternDTO patternDTO) {
        log.debug("Request to save Pattern : {}", patternDTO);
        Pattern pattern = patternMapper.toEntity(patternDTO);
        pattern = patternRepository.save(pattern);
        PatternDTO result = patternMapper.toDto(pattern);
        patternSearchRepository.save(pattern);
        return result;
    }

    /**
     * Get all the patterns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PatternDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patterns");
        return patternRepository.findAll(pageable)
            .map(patternMapper::toDto);
    }

    /**
     * Get one pattern by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatternDTO findOne(Long id) {
        log.debug("Request to get Pattern : {}", id);
        Pattern pattern = patternRepository.findOne(id);
        return patternMapper.toDto(pattern);
    }

    /**
     * Delete the pattern by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pattern : {}", id);
        patternRepository.delete(id);
        patternSearchRepository.delete(id);
    }

    /**
     * Search for the pattern corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PatternDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Patterns for query {}", query);
        Page<Pattern> result = patternSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(patternMapper::toDto);
    }
}
