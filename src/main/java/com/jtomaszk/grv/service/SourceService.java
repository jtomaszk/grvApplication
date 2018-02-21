package com.jtomaszk.grv.service;

import com.jtomaszk.grv.service.dto.SourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Source.
 */
public interface SourceService {

    /**
     * Save a source.
     *
     * @param sourceDTO the entity to save
     * @return the persisted entity
     */
    SourceDTO save(SourceDTO sourceDTO);

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SourceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" source.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SourceDTO findOne(Long id);

    /**
     * Delete the "id" source.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
