package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.SourceArchive;
import com.jtomaszk.grv.repository.SourceArchiveRepository;
import com.jtomaszk.grv.repository.search.SourceArchiveSearchRepository;
import com.jtomaszk.grv.service.dto.SourceArchiveDTO;
import com.jtomaszk.grv.service.mapper.SourceArchiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SourceArchive.
 */
@Service
@Transactional
public class SourceArchiveService {

    private final Logger log = LoggerFactory.getLogger(SourceArchiveService.class);

    private final SourceArchiveRepository sourceArchiveRepository;

    private final SourceArchiveMapper sourceArchiveMapper;

    private final SourceArchiveSearchRepository sourceArchiveSearchRepository;

    public SourceArchiveService(SourceArchiveRepository sourceArchiveRepository, SourceArchiveMapper sourceArchiveMapper, SourceArchiveSearchRepository sourceArchiveSearchRepository) {
        this.sourceArchiveRepository = sourceArchiveRepository;
        this.sourceArchiveMapper = sourceArchiveMapper;
        this.sourceArchiveSearchRepository = sourceArchiveSearchRepository;
    }

    /**
     * Save a sourceArchive.
     *
     * @param sourceArchiveDTO the entity to save
     * @return the persisted entity
     */
    public SourceArchiveDTO save(SourceArchiveDTO sourceArchiveDTO) {
        log.debug("Request to save SourceArchive : {}", sourceArchiveDTO);
        SourceArchive sourceArchive = sourceArchiveMapper.toEntity(sourceArchiveDTO);
        sourceArchive = sourceArchiveRepository.save(sourceArchive);
        SourceArchiveDTO result = sourceArchiveMapper.toDto(sourceArchive);
        sourceArchiveSearchRepository.save(sourceArchive);
        return result;
    }

    /**
     * Get all the sourceArchives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SourceArchiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SourceArchives");
        return sourceArchiveRepository.findAll(pageable)
            .map(sourceArchiveMapper::toDto);
    }

    /**
     * Get one sourceArchive by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SourceArchiveDTO findOne(Long id) {
        log.debug("Request to get SourceArchive : {}", id);
        SourceArchive sourceArchive = sourceArchiveRepository.findOne(id);
        return sourceArchiveMapper.toDto(sourceArchive);
    }

    /**
     * Delete the sourceArchive by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SourceArchive : {}", id);
        sourceArchiveRepository.delete(id);
        sourceArchiveSearchRepository.delete(id);
    }

    /**
     * Search for the sourceArchive corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SourceArchiveDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SourceArchives for query {}", query);
        Page<SourceArchive> result = sourceArchiveSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(sourceArchiveMapper::toDto);
    }
}
