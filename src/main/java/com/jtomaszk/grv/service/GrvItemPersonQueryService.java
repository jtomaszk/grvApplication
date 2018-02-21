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

import com.jtomaszk.grv.domain.GrvItemPerson;
import com.jtomaszk.grv.domain.*; // for static metamodels
import com.jtomaszk.grv.repository.GrvItemPersonRepository;
import com.jtomaszk.grv.repository.search.GrvItemPersonSearchRepository;
import com.jtomaszk.grv.service.dto.GrvItemPersonCriteria;

import com.jtomaszk.grv.service.dto.GrvItemPersonDTO;
import com.jtomaszk.grv.service.mapper.GrvItemPersonMapper;

/**
 * Service for executing complex queries for GrvItemPerson entities in the database.
 * The main input is a {@link GrvItemPersonCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GrvItemPersonDTO} or a {@link Page} of {@link GrvItemPersonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GrvItemPersonQueryService extends QueryService<GrvItemPerson> {

    private final Logger log = LoggerFactory.getLogger(GrvItemPersonQueryService.class);


    private final GrvItemPersonRepository grvItemPersonRepository;

    private final GrvItemPersonMapper grvItemPersonMapper;

    private final GrvItemPersonSearchRepository grvItemPersonSearchRepository;

    public GrvItemPersonQueryService(GrvItemPersonRepository grvItemPersonRepository, GrvItemPersonMapper grvItemPersonMapper, GrvItemPersonSearchRepository grvItemPersonSearchRepository) {
        this.grvItemPersonRepository = grvItemPersonRepository;
        this.grvItemPersonMapper = grvItemPersonMapper;
        this.grvItemPersonSearchRepository = grvItemPersonSearchRepository;
    }

    /**
     * Return a {@link List} of {@link GrvItemPersonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GrvItemPersonDTO> findByCriteria(GrvItemPersonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<GrvItemPerson> specification = createSpecification(criteria);
        return grvItemPersonMapper.toDto(grvItemPersonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GrvItemPersonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GrvItemPersonDTO> findByCriteria(GrvItemPersonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<GrvItemPerson> specification = createSpecification(criteria);
        final Page<GrvItemPerson> result = grvItemPersonRepository.findAll(specification, page);
        return result.map(grvItemPersonMapper::toDto);
    }

    /**
     * Function to convert GrvItemPersonCriteria to a {@link Specifications}
     */
    private Specifications<GrvItemPerson> createSpecification(GrvItemPersonCriteria criteria) {
        Specifications<GrvItemPerson> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GrvItemPerson_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), GrvItemPerson_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), GrvItemPerson_.lastName));
            }
            if (criteria.getAnotherLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnotherLastName(), GrvItemPerson_.anotherLastName));
            }
            if (criteria.getStartDateString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDateString(), GrvItemPerson_.startDateString));
            }
            if (criteria.getEndDateString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDateString(), GrvItemPerson_.endDateString));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getItemId(), GrvItemPerson_.item, GrvItem_.id));
            }
        }
        return specification;
    }

}
