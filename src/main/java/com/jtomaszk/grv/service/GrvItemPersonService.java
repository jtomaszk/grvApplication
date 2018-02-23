package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.GrvItemPerson;
import com.jtomaszk.grv.repository.GrvItemPersonRepository;
import com.jtomaszk.grv.repository.search.GrvItemPersonSearchRepository;
import com.jtomaszk.grv.service.dto.GrvItemPersonDTO;
import com.jtomaszk.grv.service.mapper.GrvItemPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GrvItemPerson.
 */
@Service
@Transactional
public class GrvItemPersonService {

    private final Logger log = LoggerFactory.getLogger(GrvItemPersonService.class);

    private final GrvItemPersonRepository grvItemPersonRepository;

    private final GrvItemPersonMapper grvItemPersonMapper;

    private final GrvItemPersonSearchRepository grvItemPersonSearchRepository;

    public GrvItemPersonService(GrvItemPersonRepository grvItemPersonRepository, GrvItemPersonMapper grvItemPersonMapper, GrvItemPersonSearchRepository grvItemPersonSearchRepository) {
        this.grvItemPersonRepository = grvItemPersonRepository;
        this.grvItemPersonMapper = grvItemPersonMapper;
        this.grvItemPersonSearchRepository = grvItemPersonSearchRepository;
    }

    /**
     * Save a grvItemPerson.
     *
     * @param grvItemPersonDTO the entity to save
     * @return the persisted entity
     */
    public GrvItemPersonDTO save(GrvItemPersonDTO grvItemPersonDTO) {
        log.debug("Request to save GrvItemPerson : {}", grvItemPersonDTO);
        GrvItemPerson grvItemPerson = grvItemPersonMapper.toEntity(grvItemPersonDTO);
        grvItemPerson = grvItemPersonRepository.save(grvItemPerson);
        GrvItemPersonDTO result = grvItemPersonMapper.toDto(grvItemPerson);
        grvItemPersonSearchRepository.save(grvItemPerson);
        return result;
    }

    /**
     * Get all the grvItemPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GrvItemPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrvItemPeople");
        return grvItemPersonRepository.findAll(pageable)
            .map(grvItemPersonMapper::toDto);
    }

    /**
     * Get one grvItemPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GrvItemPersonDTO findOne(Long id) {
        log.debug("Request to get GrvItemPerson : {}", id);
        GrvItemPerson grvItemPerson = grvItemPersonRepository.findOne(id);
        return grvItemPersonMapper.toDto(grvItemPerson);
    }

    /**
     * Delete the grvItemPerson by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GrvItemPerson : {}", id);
        grvItemPersonRepository.delete(id);
        grvItemPersonSearchRepository.delete(id);
    }

    /**
     * Search for the grvItemPerson corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GrvItemPersonDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrvItemPeople for query {}", query);
        Page<GrvItemPerson> result = grvItemPersonSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(grvItemPersonMapper::toDto);
    }
}
