package com.jtomaszk.grv.service;

import com.jtomaszk.grv.domain.LocationImage;
import com.jtomaszk.grv.repository.LocationImageRepository;
import com.jtomaszk.grv.repository.search.LocationImageSearchRepository;
import com.jtomaszk.grv.service.dto.LocationImageDTO;
import com.jtomaszk.grv.service.mapper.LocationImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing LocationImage.
 */
@Service
@Transactional
public class LocationImageService {

    private final Logger log = LoggerFactory.getLogger(LocationImageService.class);

    private final LocationImageRepository locationImageRepository;

    private final LocationImageMapper locationImageMapper;

    private final LocationImageSearchRepository locationImageSearchRepository;

    public LocationImageService(LocationImageRepository locationImageRepository, LocationImageMapper locationImageMapper, LocationImageSearchRepository locationImageSearchRepository) {
        this.locationImageRepository = locationImageRepository;
        this.locationImageMapper = locationImageMapper;
        this.locationImageSearchRepository = locationImageSearchRepository;
    }

    /**
     * Save a locationImage.
     *
     * @param locationImageDTO the entity to save
     * @return the persisted entity
     */
    public LocationImageDTO save(LocationImageDTO locationImageDTO) {
        log.debug("Request to save LocationImage : {}", locationImageDTO);
        LocationImage locationImage = locationImageMapper.toEntity(locationImageDTO);
        locationImage = locationImageRepository.save(locationImage);
        LocationImageDTO result = locationImageMapper.toDto(locationImage);
        locationImageSearchRepository.save(locationImage);
        return result;
    }

    /**
     * Get all the locationImages.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LocationImageDTO> findAll() {
        log.debug("Request to get all LocationImages");
        return locationImageRepository.findAll().stream()
            .map(locationImageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one locationImage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LocationImageDTO findOne(Long id) {
        log.debug("Request to get LocationImage : {}", id);
        LocationImage locationImage = locationImageRepository.findOne(id);
        return locationImageMapper.toDto(locationImage);
    }

    /**
     * Delete the locationImage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationImage : {}", id);
        locationImageRepository.delete(id);
        locationImageSearchRepository.delete(id);
    }

    /**
     * Search for the locationImage corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LocationImageDTO> search(String query) {
        log.debug("Request to search LocationImages for query {}", query);
        return StreamSupport
            .stream(locationImageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(locationImageMapper::toDto)
            .collect(Collectors.toList());
    }
}
