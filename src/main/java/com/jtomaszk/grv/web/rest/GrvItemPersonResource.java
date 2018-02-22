package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.GrvItemPersonService;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import com.jtomaszk.grv.service.dto.GrvItemPersonDTO;
import com.jtomaszk.grv.service.dto.GrvItemPersonCriteria;
import com.jtomaszk.grv.service.GrvItemPersonQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GrvItemPerson.
 */
@RestController
@RequestMapping("/api")
public class GrvItemPersonResource {

    private final Logger log = LoggerFactory.getLogger(GrvItemPersonResource.class);

    private static final String ENTITY_NAME = "grvItemPerson";

    private final GrvItemPersonService grvItemPersonService;

    private final GrvItemPersonQueryService grvItemPersonQueryService;

    public GrvItemPersonResource(GrvItemPersonService grvItemPersonService, GrvItemPersonQueryService grvItemPersonQueryService) {
        this.grvItemPersonService = grvItemPersonService;
        this.grvItemPersonQueryService = grvItemPersonQueryService;
    }

    /**
     * POST  /grv-item-people : Create a new grvItemPerson.
     *
     * @param grvItemPersonDTO the grvItemPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grvItemPersonDTO, or with status 400 (Bad Request) if the grvItemPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grv-item-people")
    @Timed
    public ResponseEntity<GrvItemPersonDTO> createGrvItemPerson(@RequestBody GrvItemPersonDTO grvItemPersonDTO) throws URISyntaxException {
        log.debug("REST request to save GrvItemPerson : {}", grvItemPersonDTO);
        if (grvItemPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new grvItemPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrvItemPersonDTO result = grvItemPersonService.save(grvItemPersonDTO);
        return ResponseEntity.created(new URI("/api/grv-item-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grv-item-people : Updates an existing grvItemPerson.
     *
     * @param grvItemPersonDTO the grvItemPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grvItemPersonDTO,
     * or with status 400 (Bad Request) if the grvItemPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the grvItemPersonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grv-item-people")
    @Timed
    public ResponseEntity<GrvItemPersonDTO> updateGrvItemPerson(@RequestBody GrvItemPersonDTO grvItemPersonDTO) throws URISyntaxException {
        log.debug("REST request to update GrvItemPerson : {}", grvItemPersonDTO);
        if (grvItemPersonDTO.getId() == null) {
            return createGrvItemPerson(grvItemPersonDTO);
        }
        GrvItemPersonDTO result = grvItemPersonService.save(grvItemPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grvItemPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grv-item-people : get all the grvItemPeople.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of grvItemPeople in body
     */
    @GetMapping("/grv-item-people")
    @Timed
    public ResponseEntity<List<GrvItemPersonDTO>> getAllGrvItemPeople(GrvItemPersonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GrvItemPeople by criteria: {}", criteria);
        Page<GrvItemPersonDTO> page = grvItemPersonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grv-item-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grv-item-people/:id : get the "id" grvItemPerson.
     *
     * @param id the id of the grvItemPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grvItemPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/grv-item-people/{id}")
    @Timed
    public ResponseEntity<GrvItemPersonDTO> getGrvItemPerson(@PathVariable Long id) {
        log.debug("REST request to get GrvItemPerson : {}", id);
        GrvItemPersonDTO grvItemPersonDTO = grvItemPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grvItemPersonDTO));
    }

    /**
     * DELETE  /grv-item-people/:id : delete the "id" grvItemPerson.
     *
     * @param id the id of the grvItemPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grv-item-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrvItemPerson(@PathVariable Long id) {
        log.debug("REST request to delete GrvItemPerson : {}", id);
        grvItemPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/grv-item-people?query=:query : search for the grvItemPerson corresponding
     * to the query.
     *
     * @param query the query of the grvItemPerson search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/grv-item-people")
    @Timed
    public ResponseEntity<List<GrvItemPersonDTO>> searchGrvItemPeople(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrvItemPeople for query {}", query);
        Page<GrvItemPersonDTO> page = grvItemPersonService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/grv-item-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
