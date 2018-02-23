package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.LocationImageService;
import com.jtomaszk.grv.service.dto.LocationImageDTO;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LocationImage.
 */
@RestController
@RequestMapping("/api")
public class LocationImageResource {

    private final Logger log = LoggerFactory.getLogger(LocationImageResource.class);

    private static final String ENTITY_NAME = "locationImage";

    private final LocationImageService locationImageService;

    public LocationImageResource(LocationImageService locationImageService) {
        this.locationImageService = locationImageService;
    }

    /**
     * POST  /location-images : Create a new locationImage.
     *
     * @param locationImageDTO the locationImageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationImageDTO, or with status 400 (Bad Request) if the locationImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/location-images")
    @Timed
    public ResponseEntity<LocationImageDTO> createLocationImage(@Valid @RequestBody LocationImageDTO locationImageDTO) throws URISyntaxException {
        log.debug("REST request to save LocationImage : {}", locationImageDTO);
        if (locationImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationImageDTO result = locationImageService.save(locationImageDTO);
        return ResponseEntity.created(new URI("/api/location-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /location-images : Updates an existing locationImage.
     *
     * @param locationImageDTO the locationImageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationImageDTO,
     * or with status 400 (Bad Request) if the locationImageDTO is not valid,
     * or with status 500 (Internal Server Error) if the locationImageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/location-images")
    @Timed
    public ResponseEntity<LocationImageDTO> updateLocationImage(@Valid @RequestBody LocationImageDTO locationImageDTO) throws URISyntaxException {
        log.debug("REST request to update LocationImage : {}", locationImageDTO);
        if (locationImageDTO.getId() == null) {
            return createLocationImage(locationImageDTO);
        }
        LocationImageDTO result = locationImageService.save(locationImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /location-images : get all the locationImages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locationImages in body
     */
    @GetMapping("/location-images")
    @Timed
    public List<LocationImageDTO> getAllLocationImages() {
        log.debug("REST request to get all LocationImages");
        return locationImageService.findAll();
        }

    /**
     * GET  /location-images/:id : get the "id" locationImage.
     *
     * @param id the id of the locationImageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationImageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/location-images/{id}")
    @Timed
    public ResponseEntity<LocationImageDTO> getLocationImage(@PathVariable Long id) {
        log.debug("REST request to get LocationImage : {}", id);
        LocationImageDTO locationImageDTO = locationImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationImageDTO));
    }

    /**
     * DELETE  /location-images/:id : delete the "id" locationImage.
     *
     * @param id the id of the locationImageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/location-images/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocationImage(@PathVariable Long id) {
        log.debug("REST request to delete LocationImage : {}", id);
        locationImageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/location-images?query=:query : search for the locationImage corresponding
     * to the query.
     *
     * @param query the query of the locationImage search
     * @return the result of the search
     */
    @GetMapping("/_search/location-images")
    @Timed
    public List<LocationImageDTO> searchLocationImages(@RequestParam String query) {
        log.debug("REST request to search LocationImages for query {}", query);
        return locationImageService.search(query);
    }

}
