package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.AreaQueryService;
import com.jtomaszk.grv.service.AreaService;
import com.jtomaszk.grv.service.dto.AreaCriteria;
import com.jtomaszk.grv.service.dto.AreaDTO;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing Area.
 */
@RestController
@RequestMapping("/api")
public class AreaResource {

    private final Logger log = LoggerFactory.getLogger(AreaResource.class);

    private static final String ENTITY_NAME = "area";

    private final AreaService areaService;

    private final AreaQueryService areaQueryService;

    public AreaResource(AreaService areaService, AreaQueryService areaQueryService) {
        this.areaService = areaService;
        this.areaQueryService = areaQueryService;
    }

    /**
     * POST  /areas : Create a new area.
     *
     * @param areaDTO the areaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new areaDTO, or with status 400 (Bad Request) if the area has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/areas")
    @Timed
    public ResponseEntity<AreaDTO> createArea(@Valid @RequestBody AreaDTO areaDTO) throws URISyntaxException {
        log.debug("REST request to save Area : {}", areaDTO);
        if (areaDTO.getId() != null) {
            throw new BadRequestAlertException("A new area cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaDTO result = areaService.save(areaDTO);
        return ResponseEntity.created(new URI("/api/areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /areas : Updates an existing area.
     *
     * @param areaDTO the areaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated areaDTO,
     * or with status 400 (Bad Request) if the areaDTO is not valid,
     * or with status 500 (Internal Server Error) if the areaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/areas")
    @Timed
    public ResponseEntity<AreaDTO> updateArea(@Valid @RequestBody AreaDTO areaDTO) throws URISyntaxException {
        log.debug("REST request to update Area : {}", areaDTO);
        if (areaDTO.getId() == null) {
            return createArea(areaDTO);
        }
        AreaDTO result = areaService.save(areaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, areaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /areas : get all the areas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of areas in body
     */
    @GetMapping("/areas")
    @Timed
    public ResponseEntity<List<AreaDTO>> getAllAreas(AreaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Areas by criteria: {}", criteria);
        Page<AreaDTO> page = areaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/areas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /areas/:id : get the "id" area.
     *
     * @param id the id of the areaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the areaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/areas/{id}")
    @Timed
    public ResponseEntity<AreaDTO> getArea(@PathVariable Long id) {
        log.debug("REST request to get Area : {}", id);
        AreaDTO areaDTO = areaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(areaDTO));
    }

    /**
     * DELETE  /areas/:id : delete the "id" area.
     *
     * @param id the id of the areaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/areas/{id}")
    @Timed
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        log.debug("REST request to delete Area : {}", id);
        areaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/areas?query=:query : search for the area corresponding
     * to the query.
     *
     * @param query the query of the area search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/areas")
    @Timed
    public ResponseEntity<List<AreaDTO>> searchAreas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Areas for query {}", query);
        Page<AreaDTO> page = areaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/areas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
