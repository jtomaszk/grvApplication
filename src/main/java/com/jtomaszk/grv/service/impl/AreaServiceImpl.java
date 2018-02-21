package com.jtomaszk.grv.service.impl;

import com.jtomaszk.grv.service.AreaService;
import com.jtomaszk.grv.domain.Area;
import com.jtomaszk.grv.repository.AreaRepository;
import com.jtomaszk.grv.service.dto.AreaDTO;
import com.jtomaszk.grv.service.mapper.AreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Area.
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    private final Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);

    private final AreaRepository areaRepository;

    private final AreaMapper areaMapper;

    public AreaServiceImpl(AreaRepository areaRepository, AreaMapper areaMapper) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
    }

    /**
     * Save a area.
     *
     * @param areaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AreaDTO save(AreaDTO areaDTO) {
        log.debug("Request to save Area : {}", areaDTO);
        Area area = areaMapper.toEntity(areaDTO);
        area = areaRepository.save(area);
        return areaMapper.toDto(area);
    }

    /**
     * Get all the areas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Area : {}", id);
        areaRepository.delete(id);
    }
}
