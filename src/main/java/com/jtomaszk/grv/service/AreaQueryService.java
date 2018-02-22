package com.jtomaszk.grv.service;


import com.jtomaszk.grv.domain.Area;
import com.jtomaszk.grv.domain.Area_;
import com.jtomaszk.grv.domain.Source_;
import com.jtomaszk.grv.repository.AreaRepository;
import com.jtomaszk.grv.repository.search.AreaSearchRepository;
import com.jtomaszk.grv.service.dto.AreaCriteria;
import com.jtomaszk.grv.service.dto.AreaDTO;
import com.jtomaszk.grv.service.mapper.AreaMapper;
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
 * Service for executing complex queries for Area entities in the database.
 * The main input is a {@link AreaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AreaDTO} or a {@link Page} of {@link AreaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AreaQueryService extends QueryService<Area> {

    private final Logger log = LoggerFactory.getLogger(AreaQueryService.class);


    private final AreaRepository areaRepository;

    private final AreaMapper areaMapper;

    private final AreaSearchRepository areaSearchRepository;

    public AreaQueryService(AreaRepository areaRepository, AreaMapper areaMapper, AreaSearchRepository areaSearchRepository) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
        this.areaSearchRepository = areaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AreaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AreaDTO> findByCriteria(AreaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Area> specification = createSpecification(criteria);
        return areaMapper.toDto(areaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AreaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AreaDTO> findByCriteria(AreaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Area> specification = createSpecification(criteria);
        final Page<Area> result = areaRepository.findAll(specification, page);
        return result.map(areaMapper::toDto);
    }

    /**
     * Function to convert AreaCriteria to a {@link Specifications}
     */
    private Specifications<Area> createSpecification(AreaCriteria criteria) {
        Specifications<Area> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Area_.id));
            }
            if (criteria.getAreaName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaName(), Area_.areaName));
            }
            if (criteria.getSourcesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSourcesId(), Area_.sources, Source_.id));
            }
        }
        return specification;
    }

}
