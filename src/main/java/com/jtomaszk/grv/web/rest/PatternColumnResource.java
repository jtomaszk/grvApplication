package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.PatternColumnService;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import com.jtomaszk.grv.service.dto.PatternColumnDTO;
import com.jtomaszk.grv.service.dto.PatternColumnCriteria;
import com.jtomaszk.grv.service.PatternColumnQueryService;
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
 * REST controller for managing PatternColumn.
 */
@RestController
@RequestMapping("/api")
public class PatternColumnResource {

    private final Logger log = LoggerFactory.getLogger(PatternColumnResource.class);

    private static final String ENTITY_NAME = "patternColumn";

    private final PatternColumnService patternColumnService;

    private final PatternColumnQueryService patternColumnQueryService;

    public PatternColumnResource(PatternColumnService patternColumnService, PatternColumnQueryService patternColumnQueryService) {
        this.patternColumnService = patternColumnService;
        this.patternColumnQueryService = patternColumnQueryService;
    }

    /**
     * POST  /pattern-columns : Create a new patternColumn.
     *
     * @param patternColumnDTO the patternColumnDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patternColumnDTO, or with status 400 (Bad Request) if the patternColumn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pattern-columns")
    @Timed
    public ResponseEntity<PatternColumnDTO> createPatternColumn(@Valid @RequestBody PatternColumnDTO patternColumnDTO) throws URISyntaxException {
        log.debug("REST request to save PatternColumn : {}", patternColumnDTO);
        if (patternColumnDTO.getId() != null) {
            throw new BadRequestAlertException("A new patternColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatternColumnDTO result = patternColumnService.save(patternColumnDTO);
        return ResponseEntity.created(new URI("/api/pattern-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pattern-columns : Updates an existing patternColumn.
     *
     * @param patternColumnDTO the patternColumnDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patternColumnDTO,
     * or with status 400 (Bad Request) if the patternColumnDTO is not valid,
     * or with status 500 (Internal Server Error) if the patternColumnDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pattern-columns")
    @Timed
    public ResponseEntity<PatternColumnDTO> updatePatternColumn(@Valid @RequestBody PatternColumnDTO patternColumnDTO) throws URISyntaxException {
        log.debug("REST request to update PatternColumn : {}", patternColumnDTO);
        if (patternColumnDTO.getId() == null) {
            return createPatternColumn(patternColumnDTO);
        }
        PatternColumnDTO result = patternColumnService.save(patternColumnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patternColumnDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pattern-columns : get all the patternColumns.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of patternColumns in body
     */
    @GetMapping("/pattern-columns")
    @Timed
    public ResponseEntity<List<PatternColumnDTO>> getAllPatternColumns(PatternColumnCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PatternColumns by criteria: {}", criteria);
        Page<PatternColumnDTO> page = patternColumnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pattern-columns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pattern-columns/:id : get the "id" patternColumn.
     *
     * @param id the id of the patternColumnDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patternColumnDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pattern-columns/{id}")
    @Timed
    public ResponseEntity<PatternColumnDTO> getPatternColumn(@PathVariable Long id) {
        log.debug("REST request to get PatternColumn : {}", id);
        PatternColumnDTO patternColumnDTO = patternColumnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(patternColumnDTO));
    }

    /**
     * DELETE  /pattern-columns/:id : delete the "id" patternColumn.
     *
     * @param id the id of the patternColumnDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pattern-columns/{id}")
    @Timed
    public ResponseEntity<Void> deletePatternColumn(@PathVariable Long id) {
        log.debug("REST request to delete PatternColumn : {}", id);
        patternColumnService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pattern-columns?query=:query : search for the patternColumn corresponding
     * to the query.
     *
     * @param query the query of the patternColumn search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pattern-columns")
    @Timed
    public ResponseEntity<List<PatternColumnDTO>> searchPatternColumns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PatternColumns for query {}", query);
        Page<PatternColumnDTO> page = patternColumnService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pattern-columns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
