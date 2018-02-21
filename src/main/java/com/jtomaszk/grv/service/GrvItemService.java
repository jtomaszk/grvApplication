package com.jtomaszk.grv.service;

import com.jtomaszk.grv.service.dto.GrvItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GrvItem.
 */
public interface GrvItemService {

    /**
     * Save a grvItem.
     *
     * @param grvItemDTO the entity to save
     * @return the persisted entity
     */
    GrvItemDTO save(GrvItemDTO grvItemDTO);

    /**
     * Get all the grvItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GrvItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" grvItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GrvItemDTO findOne(Long id);

    /**
     * Delete the "id" grvItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the grvItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GrvItemDTO> search(String query, Pageable pageable);
}
