package com.jtomaszk.grv.service;


import com.jtomaszk.grv.domain.Area_;
import com.jtomaszk.grv.domain.InputPattern_;
import com.jtomaszk.grv.domain.Location_;
import com.jtomaszk.grv.domain.ParseError_;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.domain.SourceArchive_;
import com.jtomaszk.grv.domain.Source_;
import com.jtomaszk.grv.repository.SourceRepository;
import com.jtomaszk.grv.repository.search.SourceSearchRepository;
import com.jtomaszk.grv.service.dto.SourceCriteria;
import com.jtomaszk.grv.service.dto.SourceDTO;
import com.jtomaszk.grv.service.mapper.SourceMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Source entities in the database.
 * The main input is a {@link SourceCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceDTO} or a {@link Page} of {@link SourceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceQueryService extends QueryService<Source> {

    private final Logger log = LoggerFactory.getLogger(SourceQueryService.class);


    private final SourceRepository sourceRepository;

    private final SourceMapper sourceMapper;

    private final SourceSearchRepository sourceSearchRepository;

    public SourceQueryService(SourceRepository sourceRepository, SourceMapper sourceMapper, SourceSearchRepository sourceSearchRepository) {
        this.sourceRepository = sourceRepository;
        this.sourceMapper = sourceMapper;
        this.sourceSearchRepository = sourceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SourceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceDTO> findByCriteria(SourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Source> specification = createSpecification(criteria);
        return sourceMapper.toDto(sourceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SourceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceDTO> findByCriteria(SourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Source> specification = createSpecification(criteria);
        final Page<Source> result = sourceRepository.findAll(specification, page);
        return result.map(sourceMapper::toDto);
    }

    /**
     * Function to convert SourceCriteria to a {@link Specifications}
     */
    private Specifications<Source> createSpecification(SourceCriteria criteria) {
        Specifications<Source> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Source_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Source_.title));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Source_.url));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Source_.status));
            }
            if (criteria.getLastRunDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastRunDate(), Source_.lastRunDate));
            }
            if (criteria.getInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfo(), Source_.info));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAreaId(), Source_.area, Area_.id));
            }
            if (criteria.getPatternId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPatternId(), Source_.pattern, InputPattern_.id));
            }
            if (criteria.getErrorsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getErrorsId(), Source_.errors, ParseError_.id));
            }
            if (criteria.getArchivesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getArchivesId(), Source_.archives, SourceArchive_.id));
            }
            if (criteria.getLocationsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationsId(), Source_.locations, Location_.id));
            }
        }
        return specification;
    }

}
