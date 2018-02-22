package com.jtomaszk.grv.service;


import com.jtomaszk.grv.domain.Error;
import com.jtomaszk.grv.domain.Error_;
import com.jtomaszk.grv.domain.GrvItem_;
import com.jtomaszk.grv.domain.Source_;
import com.jtomaszk.grv.repository.ErrorRepository;
import com.jtomaszk.grv.repository.search.ErrorSearchRepository;
import com.jtomaszk.grv.service.dto.ErrorCriteria;
import com.jtomaszk.grv.service.dto.ErrorDTO;
import com.jtomaszk.grv.service.mapper.ErrorMapper;
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
 * Service for executing complex queries for Error entities in the database.
 * The main input is a {@link ErrorCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ErrorDTO} or a {@link Page} of {@link ErrorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ErrorQueryService extends QueryService<Error> {

    private final Logger log = LoggerFactory.getLogger(ErrorQueryService.class);


    private final ErrorRepository errorRepository;

    private final ErrorMapper errorMapper;

    private final ErrorSearchRepository errorSearchRepository;

    public ErrorQueryService(ErrorRepository errorRepository, ErrorMapper errorMapper, ErrorSearchRepository errorSearchRepository) {
        this.errorRepository = errorRepository;
        this.errorMapper = errorMapper;
        this.errorSearchRepository = errorSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ErrorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ErrorDTO> findByCriteria(ErrorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Error> specification = createSpecification(criteria);
        return errorMapper.toDto(errorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ErrorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ErrorDTO> findByCriteria(ErrorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Error> specification = createSpecification(criteria);
        final Page<Error> result = errorRepository.findAll(specification, page);
        return result.map(errorMapper::toDto);
    }

    /**
     * Function to convert ErrorCriteria to a {@link Specifications}
     */
    private Specifications<Error> createSpecification(ErrorCriteria criteria) {
        Specifications<Error> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Error_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Error_.title));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Error_.createdDate));
            }
            if (criteria.getSourceId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourceId(), Error_.source, Source_.id));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getItemId(), Error_.item, GrvItem_.id));
            }
        }
        return specification;
    }

}
