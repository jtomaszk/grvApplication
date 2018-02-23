package com.jtomaszk.grv.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.domain.*; // for static metamodels
import com.jtomaszk.grv.repository.PatternColumnRepository;
import com.jtomaszk.grv.repository.search.PatternColumnSearchRepository;
import com.jtomaszk.grv.service.dto.PatternColumnCriteria;

import com.jtomaszk.grv.service.dto.PatternColumnDTO;
import com.jtomaszk.grv.service.mapper.PatternColumnMapper;
import com.jtomaszk.grv.domain.enumeration.ColumnEnum;

/**
 * Service for executing complex queries for PatternColumn entities in the database.
 * The main input is a {@link PatternColumnCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatternColumnDTO} or a {@link Page} of {@link PatternColumnDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatternColumnQueryService extends QueryService<PatternColumn> {

    private final Logger log = LoggerFactory.getLogger(PatternColumnQueryService.class);


    private final PatternColumnRepository patternColumnRepository;

    private final PatternColumnMapper patternColumnMapper;

    private final PatternColumnSearchRepository patternColumnSearchRepository;

    public PatternColumnQueryService(PatternColumnRepository patternColumnRepository, PatternColumnMapper patternColumnMapper, PatternColumnSearchRepository patternColumnSearchRepository) {
        this.patternColumnRepository = patternColumnRepository;
        this.patternColumnMapper = patternColumnMapper;
        this.patternColumnSearchRepository = patternColumnSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PatternColumnDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatternColumnDTO> findByCriteria(PatternColumnCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PatternColumn> specification = createSpecification(criteria);
        return patternColumnMapper.toDto(patternColumnRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PatternColumnDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatternColumnDTO> findByCriteria(PatternColumnCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PatternColumn> specification = createSpecification(criteria);
        final Page<PatternColumn> result = patternColumnRepository.findAll(specification, page);
        return result.map(patternColumnMapper::toDto);
    }

    /**
     * Function to convert PatternColumnCriteria to a {@link Specifications}
     */
    private Specifications<PatternColumn> createSpecification(PatternColumnCriteria criteria) {
        Specifications<PatternColumn> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PatternColumn_.id));
            }
            if (criteria.getColumn() != null) {
                specification = specification.and(buildSpecification(criteria.getColumn(), PatternColumn_.column));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), PatternColumn_.value));
            }
            if (criteria.getPatternId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPatternId(), PatternColumn_.pattern, InputPattern_.id));
            }
        }
        return specification;
    }

}
