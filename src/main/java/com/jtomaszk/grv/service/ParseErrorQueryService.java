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

import com.jtomaszk.grv.domain.ParseError;
import com.jtomaszk.grv.domain.*; // for static metamodels
import com.jtomaszk.grv.repository.ParseErrorRepository;
import com.jtomaszk.grv.repository.search.ParseErrorSearchRepository;
import com.jtomaszk.grv.service.dto.ParseErrorCriteria;

import com.jtomaszk.grv.service.dto.ParseErrorDTO;
import com.jtomaszk.grv.service.mapper.ParseErrorMapper;

/**
 * Service for executing complex queries for ParseError entities in the database.
 * The main input is a {@link ParseErrorCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParseErrorDTO} or a {@link Page} of {@link ParseErrorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParseErrorQueryService extends QueryService<ParseError> {

    private final Logger log = LoggerFactory.getLogger(ParseErrorQueryService.class);


    private final ParseErrorRepository parseErrorRepository;

    private final ParseErrorMapper parseErrorMapper;

    private final ParseErrorSearchRepository parseErrorSearchRepository;

    public ParseErrorQueryService(ParseErrorRepository parseErrorRepository, ParseErrorMapper parseErrorMapper, ParseErrorSearchRepository parseErrorSearchRepository) {
        this.parseErrorRepository = parseErrorRepository;
        this.parseErrorMapper = parseErrorMapper;
        this.parseErrorSearchRepository = parseErrorSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParseErrorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParseErrorDTO> findByCriteria(ParseErrorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ParseError> specification = createSpecification(criteria);
        return parseErrorMapper.toDto(parseErrorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParseErrorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParseErrorDTO> findByCriteria(ParseErrorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ParseError> specification = createSpecification(criteria);
        final Page<ParseError> result = parseErrorRepository.findAll(specification, page);
        return result.map(parseErrorMapper::toDto);
    }

    /**
     * Function to convert ParseErrorCriteria to a {@link Specifications}
     */
    private Specifications<ParseError> createSpecification(ParseErrorCriteria criteria) {
        Specifications<ParseError> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParseError_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ParseError_.title));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ParseError_.createdDate));
            }
            if (criteria.getSourceId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourceId(), ParseError_.source, Source_.id));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getItemId(), ParseError_.item, GrvItem_.id));
            }
        }
        return specification;
    }

}
