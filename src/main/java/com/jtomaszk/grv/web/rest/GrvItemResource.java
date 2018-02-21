package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.GrvItemService;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import com.jtomaszk.grv.service.dto.GrvItemDTO;
import com.jtomaszk.grv.service.dto.GrvItemCriteria;
import com.jtomaszk.grv.service.GrvItemQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GrvItem.
 */
@RestController
@RequestMapping("/api")
public class GrvItemResource {

    private final Logger log = LoggerFactory.getLogger(GrvItemResource.class);

    private static final String ENTITY_NAME = "grvItem";

    private final GrvItemService grvItemService;

    private final GrvItemQueryService grvItemQueryService;

    public GrvItemResource(GrvItemService grvItemService, GrvItemQueryService grvItemQueryService) {
        this.grvItemService = grvItemService;
        this.grvItemQueryService = grvItemQueryService;
    }

    /**
     * POST  /grv-items : Create a new grvItem.
     *
     * @param grvItemDTO the grvItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grvItemDTO, or with status 400 (Bad Request) if the grvItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grv-items")
    @Timed
    public ResponseEntity<GrvItemDTO> createGrvItem(@Valid @RequestBody GrvItemDTO grvItemDTO) throws URISyntaxException {
        log.debug("REST request to save GrvItem : {}", grvItemDTO);
        if (grvItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new grvItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrvItemDTO result = grvItemService.save(grvItemDTO);
        return ResponseEntity.created(new URI("/api/grv-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grv-items : Updates an existing grvItem.
     *
     * @param grvItemDTO the grvItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grvItemDTO,
     * or with status 400 (Bad Request) if the grvItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the grvItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grv-items")
    @Timed
    public ResponseEntity<GrvItemDTO> updateGrvItem(@Valid @RequestBody GrvItemDTO grvItemDTO) throws URISyntaxException {
        log.debug("REST request to update GrvItem : {}", grvItemDTO);
        if (grvItemDTO.getId() == null) {
            return createGrvItem(grvItemDTO);
        }
        GrvItemDTO result = grvItemService.save(grvItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grvItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grv-items : get all the grvItems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of grvItems in body
     */
    @GetMapping("/grv-items")
    @Timed
    public ResponseEntity<List<GrvItemDTO>> getAllGrvItems(GrvItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GrvItems by criteria: {}", criteria);
        Page<GrvItemDTO> page = grvItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grv-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grv-items/:id : get the "id" grvItem.
     *
     * @param id the id of the grvItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grvItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/grv-items/{id}")
    @Timed
    public ResponseEntity<GrvItemDTO> getGrvItem(@PathVariable Long id) {
        log.debug("REST request to get GrvItem : {}", id);
        GrvItemDTO grvItemDTO = grvItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grvItemDTO));
    }

    /**
     * DELETE  /grv-items/:id : delete the "id" grvItem.
     *
     * @param id the id of the grvItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grv-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrvItem(@PathVariable Long id) {
        log.debug("REST request to delete GrvItem : {}", id);
        grvItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/grv-items?query=:query : search for the grvItem corresponding
     * to the query.
     *
     * @param query the query of the grvItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/grv-items")
    @Timed
    public ResponseEntity<List<GrvItemDTO>> searchGrvItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrvItems for query {}", query);
        Page<GrvItemDTO> page = grvItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/grv-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
