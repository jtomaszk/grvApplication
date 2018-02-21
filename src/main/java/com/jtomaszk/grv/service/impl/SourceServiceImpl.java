package com.jtomaszk.grv.service.impl;

import com.jtomaszk.grv.service.SourceService;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.repository.SourceRepository;
import com.jtomaszk.grv.service.dto.SourceDTO;
import com.jtomaszk.grv.service.mapper.SourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Source.
 */
@Service
@Transactional
public class SourceServiceImpl implements SourceService {

    private final Logger log = LoggerFactory.getLogger(SourceServiceImpl.class);

    private final SourceRepository sourceRepository;

    private final SourceMapper sourceMapper;

    public SourceServiceImpl(SourceRepository sourceRepository, SourceMapper sourceMapper) {
        this.sourceRepository = sourceRepository;
        this.sourceMapper = sourceMapper;
    }

    /**
     * Save a source.
     *
     * @param sourceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceDTO save(SourceDTO sourceDTO) {
        log.debug("Request to save Source : {}", sourceDTO);
        Source source = sourceMapper.toEntity(sourceDTO);
        source = sourceRepository.save(source);
        return sourceMapper.toDto(source);
    }

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sources");
        return sourceRepository.findAll(pageable)
            .map(sourceMapper::toDto);
    }

    /**
     * Get one source by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SourceDTO findOne(Long id) {
        log.debug("Request to get Source : {}", id);
        Source source = sourceRepository.findOne(id);
        return sourceMapper.toDto(source);
    }

    /**
     * Delete the source by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Source : {}", id);
        sourceRepository.delete(id);
    }
}
