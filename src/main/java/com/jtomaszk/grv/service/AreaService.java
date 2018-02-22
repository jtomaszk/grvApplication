package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.Area;
import com.jtomaszk.grv.repository.AreaRepository;
import com.jtomaszk.grv.repository.search.AreaSearchRepository;
import com.jtomaszk.grv.service.dto.AreaDTO;
import com.jtomaszk.grv.service.mapper.AreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Area.
 */
@Service
@Transactional
public class AreaService {

    private final Logger log = LoggerFactory.getLogger(AreaService.class);

    private final AreaRepository areaRepository;

    private final AreaMapper areaMapper;

    private final AreaSearchRepository areaSearchRepository;

    public AreaService(AreaRepository areaRepository, AreaMapper areaMapper, AreaSearchRepository areaSearchRepository) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
        this.areaSearchRepository = areaSearchRepository;
    }

    /**
     * Save a area.
     *
     * @param areaDTO the entity to save
     * @return the persisted entity
     */
    public AreaDTO save(AreaDTO areaDTO) {
        log.debug("Request to save Area : {}", areaDTO);
        Area area = areaMapper.toEntity(areaDTO);
        area = areaRepository.save(area);
        AreaDTO result = areaMapper.toDto(area);
        areaSearchRepository.save(area);
        return result;
    }

    /**
     * Get all the areas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AreaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Areas");
        return areaRepository.findAll(pageable)
            .map(areaMapper::toDto);
    }

    /**
     * Get one area by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AreaDTO findOne(Long id) {
        log.debug("Request to get Area : {}", id);
        Area area = areaRepository.findOne(id);
        return areaMapper.toDto(area);
    }

    /**
     * Delete the area by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Area : {}", id);
        areaRepository.delete(id);
        areaSearchRepository.delete(id);
    }

    /**
     * Search for the area corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AreaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Areas for query {}", query);
        Page<Area> result = areaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(areaMapper::toDto);
    }
}
