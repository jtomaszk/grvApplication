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

import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.domain.*; // for static metamodels
import com.jtomaszk.grv.repository.InputPatternRepository;
import com.jtomaszk.grv.repository.search.InputPatternSearchRepository;
import com.jtomaszk.grv.service.dto.InputPatternCriteria;

import com.jtomaszk.grv.service.dto.InputPatternDTO;
import com.jtomaszk.grv.service.mapper.InputPatternMapper;

/**
 * Service for executing complex queries for InputPattern entities in the database.
 * The main input is a {@link InputPatternCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InputPatternDTO} or a {@link Page} of {@link InputPatternDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InputPatternQueryService extends QueryService<InputPattern> {

    private final Logger log = LoggerFactory.getLogger(InputPatternQueryService.class);


    private final InputPatternRepository inputPatternRepository;

    private final InputPatternMapper inputPatternMapper;

    private final InputPatternSearchRepository inputPatternSearchRepository;

    public InputPatternQueryService(InputPatternRepository inputPatternRepository, InputPatternMapper inputPatternMapper, InputPatternSearchRepository inputPatternSearchRepository) {
        this.inputPatternRepository = inputPatternRepository;
        this.inputPatternMapper = inputPatternMapper;
        this.inputPatternSearchRepository = inputPatternSearchRepository;
    }

    /**
     * Return a {@link List} of {@link InputPatternDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InputPatternDTO> findByCriteria(InputPatternCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<InputPattern> specification = createSpecification(criteria);
        return inputPatternMapper.toDto(inputPatternRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InputPatternDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InputPatternDTO> findByCriteria(InputPatternCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<InputPattern> specification = createSpecification(criteria);
        final Page<InputPattern> result = inputPatternRepository.findAll(specification, page);
        return result.map(inputPatternMapper::toDto);
    }

    /**
     * Function to convert InputPatternCriteria to a {@link Specifications}
     */
    private Specifications<InputPattern> createSpecification(InputPatternCriteria criteria) {
        Specifications<InputPattern> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InputPattern_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), InputPattern_.title));
            }
            if (criteria.getSourcesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourcesId(), InputPattern_.sources, Source_.id));
            }
            if (criteria.getColumnsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getColumnsId(), InputPattern_.columns, PatternColumn_.id));
            }
        }
        return specification;
    }

}
