package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.repository.PatternColumnRepository;
import com.jtomaszk.grv.repository.search.PatternColumnSearchRepository;
import com.jtomaszk.grv.service.dto.PatternColumnDTO;
import com.jtomaszk.grv.service.mapper.PatternColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing PatternColumn.
 */
@Service
@Transactional
public class PatternColumnService {

    private final Logger log = LoggerFactory.getLogger(PatternColumnService.class);

    private final PatternColumnRepository patternColumnRepository;

    private final PatternColumnMapper patternColumnMapper;

    private final PatternColumnSearchRepository patternColumnSearchRepository;

    public PatternColumnService(PatternColumnRepository patternColumnRepository, PatternColumnMapper patternColumnMapper, PatternColumnSearchRepository patternColumnSearchRepository) {
        this.patternColumnRepository = patternColumnRepository;
        this.patternColumnMapper = patternColumnMapper;
        this.patternColumnSearchRepository = patternColumnSearchRepository;
    }

    /**
     * Save a patternColumn.
     *
     * @param patternColumnDTO the entity to save
     * @return the persisted entity
     */
    public PatternColumnDTO save(PatternColumnDTO patternColumnDTO) {
        log.debug("Request to save PatternColumn : {}", patternColumnDTO);
        PatternColumn patternColumn = patternColumnMapper.toEntity(patternColumnDTO);
        patternColumn = patternColumnRepository.save(patternColumn);
        PatternColumnDTO result = patternColumnMapper.toDto(patternColumn);
        patternColumnSearchRepository.save(patternColumn);
        return result;
    }

    /**
     * Get all the patternColumns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PatternColumnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PatternColumns");
        return patternColumnRepository.findAll(pageable)
            .map(patternColumnMapper::toDto);
    }

    /**
     * Get one patternColumn by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PatternColumnDTO findOne(Long id) {
        log.debug("Request to get PatternColumn : {}", id);
        PatternColumn patternColumn = patternColumnRepository.findOne(id);
        return patternColumnMapper.toDto(patternColumn);
    }

    /**
     * Delete the patternColumn by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PatternColumn : {}", id);
        patternColumnRepository.delete(id);
        patternColumnSearchRepository.delete(id);
    }

    /**
     * Search for the patternColumn corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PatternColumnDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PatternColumns for query {}", query);
        Page<PatternColumn> result = patternColumnSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(patternColumnMapper::toDto);
    }
}
