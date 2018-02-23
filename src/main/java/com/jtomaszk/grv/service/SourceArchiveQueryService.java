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

import com.jtomaszk.grv.domain.SourceArchive;
import com.jtomaszk.grv.domain.*; // for static metamodels
import com.jtomaszk.grv.repository.SourceArchiveRepository;
import com.jtomaszk.grv.repository.search.SourceArchiveSearchRepository;
import com.jtomaszk.grv.service.dto.SourceArchiveCriteria;

import com.jtomaszk.grv.service.dto.SourceArchiveDTO;
import com.jtomaszk.grv.service.mapper.SourceArchiveMapper;

/**
 * Service for executing complex queries for SourceArchive entities in the database.
 * The main input is a {@link SourceArchiveCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceArchiveDTO} or a {@link Page} of {@link SourceArchiveDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceArchiveQueryService extends QueryService<SourceArchive> {

    private final Logger log = LoggerFactory.getLogger(SourceArchiveQueryService.class);


    private final SourceArchiveRepository sourceArchiveRepository;

    private final SourceArchiveMapper sourceArchiveMapper;

    private final SourceArchiveSearchRepository sourceArchiveSearchRepository;

    public SourceArchiveQueryService(SourceArchiveRepository sourceArchiveRepository, SourceArchiveMapper sourceArchiveMapper, SourceArchiveSearchRepository sourceArchiveSearchRepository) {
        this.sourceArchiveRepository = sourceArchiveRepository;
        this.sourceArchiveMapper = sourceArchiveMapper;
        this.sourceArchiveSearchRepository = sourceArchiveSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SourceArchiveDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceArchiveDTO> findByCriteria(SourceArchiveCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SourceArchive> specification = createSpecification(criteria);
        return sourceArchiveMapper.toDto(sourceArchiveRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SourceArchiveDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceArchiveDTO> findByCriteria(SourceArchiveCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SourceArchive> specification = createSpecification(criteria);
        final Page<SourceArchive> result = sourceArchiveRepository.findAll(specification, page);
        return result.map(sourceArchiveMapper::toDto);
    }

    /**
     * Function to convert SourceArchiveCriteria to a {@link Specifications}
     */
    private Specifications<SourceArchive> createSpecification(SourceArchiveCriteria criteria) {
        Specifications<SourceArchive> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceArchive_.id));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SourceArchive_.createdDate));
            }
            if (criteria.getSourceId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourceId(), SourceArchive_.source, Source_.id));
            }
        }
        return specification;
    }

}
