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

import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.domain.*; // for static metamodels
import com.jtomaszk.grv.repository.GrvItemRepository;
import com.jtomaszk.grv.repository.search.GrvItemSearchRepository;
import com.jtomaszk.grv.service.dto.GrvItemCriteria;

import com.jtomaszk.grv.service.dto.GrvItemDTO;
import com.jtomaszk.grv.service.mapper.GrvItemMapper;

/**
 * Service for executing complex queries for GrvItem entities in the database.
 * The main input is a {@link GrvItemCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GrvItemDTO} or a {@link Page} of {@link GrvItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GrvItemQueryService extends QueryService<GrvItem> {

    private final Logger log = LoggerFactory.getLogger(GrvItemQueryService.class);


    private final GrvItemRepository grvItemRepository;

    private final GrvItemMapper grvItemMapper;

    private final GrvItemSearchRepository grvItemSearchRepository;

    public GrvItemQueryService(GrvItemRepository grvItemRepository, GrvItemMapper grvItemMapper, GrvItemSearchRepository grvItemSearchRepository) {
        this.grvItemRepository = grvItemRepository;
        this.grvItemMapper = grvItemMapper;
        this.grvItemSearchRepository = grvItemSearchRepository;
    }

    /**
     * Return a {@link List} of {@link GrvItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GrvItemDTO> findByCriteria(GrvItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<GrvItem> specification = createSpecification(criteria);
        return grvItemMapper.toDto(grvItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GrvItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GrvItemDTO> findByCriteria(GrvItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<GrvItem> specification = createSpecification(criteria);
        final Page<GrvItem> result = grvItemRepository.findAll(specification, page);
        return result.map(grvItemMapper::toDto);
    }

    /**
     * Function to convert GrvItemCriteria to a {@link Specifications}
     */
    private Specifications<GrvItem> createSpecification(GrvItemCriteria criteria) {
        Specifications<GrvItem> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GrvItem_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), GrvItem_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), GrvItem_.lastName));
            }
            if (criteria.getAnotherLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnotherLastName(), GrvItem_.anotherLastName));
            }
            if (criteria.getStartDateString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDateString(), GrvItem_.startDateString));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), GrvItem_.startDate));
            }
            if (criteria.getEndDateString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDateString(), GrvItem_.endDateString));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), GrvItem_.endDate));
            }
            if (criteria.getValidToDateString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidToDateString(), GrvItem_.validToDateString));
            }
            if (criteria.getValidToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidToDate(), GrvItem_.validToDate));
            }
            if (criteria.getCoords() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoords(), GrvItem_.coords));
            }
            if (criteria.getExternalid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExternalid(), GrvItem_.externalid));
            }
            if (criteria.getExternalboxid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExternalboxid(), GrvItem_.externalboxid));
            }
            if (criteria.getInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfo(), GrvItem_.info));
            }
            if (criteria.getDocnr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocnr(), GrvItem_.docnr));
            }
            if (criteria.getSourceId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourceId(), GrvItem_.source, Source_.id));
            }
        }
        return specification;
    }

}
