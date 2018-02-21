package com.jtomaszk.grv.service.impl;

import com.jtomaszk.grv.service.GrvItemService;
import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.repository.GrvItemRepository;
import com.jtomaszk.grv.repository.search.GrvItemSearchRepository;
import com.jtomaszk.grv.service.dto.GrvItemDTO;
import com.jtomaszk.grv.service.mapper.GrvItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GrvItem.
 */
@Service
@Transactional
public class GrvItemServiceImpl implements GrvItemService {

    private final Logger log = LoggerFactory.getLogger(GrvItemServiceImpl.class);

    private final GrvItemRepository grvItemRepository;

    private final GrvItemMapper grvItemMapper;

    private final GrvItemSearchRepository grvItemSearchRepository;

    public GrvItemServiceImpl(GrvItemRepository grvItemRepository, GrvItemMapper grvItemMapper, GrvItemSearchRepository grvItemSearchRepository) {
        this.grvItemRepository = grvItemRepository;
        this.grvItemMapper = grvItemMapper;
        this.grvItemSearchRepository = grvItemSearchRepository;
    }

    /**
     * Save a grvItem.
     *
     * @param grvItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GrvItemDTO save(GrvItemDTO grvItemDTO) {
        log.debug("Request to save GrvItem : {}", grvItemDTO);
        GrvItem grvItem = grvItemMapper.toEntity(grvItemDTO);
        grvItem = grvItemRepository.save(grvItem);
        GrvItemDTO result = grvItemMapper.toDto(grvItem);
        grvItemSearchRepository.save(grvItem);
        return result;
    }

    /**
     * Get all the grvItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GrvItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrvItems");
        return grvItemRepository.findAll(pageable)
            .map(grvItemMapper::toDto);
    }

    /**
     * Get one grvItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GrvItemDTO findOne(Long id) {
        log.debug("Request to get GrvItem : {}", id);
        GrvItem grvItem = grvItemRepository.findOne(id);
        return grvItemMapper.toDto(grvItem);
    }

    /**
     * Delete the grvItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GrvItem : {}", id);
        grvItemRepository.delete(id);
        grvItemSearchRepository.delete(id);
    }

    /**
     * Search for the grvItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GrvItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrvItems for query {}", query);
        Page<GrvItem> result = grvItemSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(grvItemMapper::toDto);
    }
}
