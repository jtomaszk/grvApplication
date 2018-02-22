package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.PatternService;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import com.jtomaszk.grv.service.dto.PatternDTO;
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
 * REST controller for managing Pattern.
 */
@RestController
@RequestMapping("/api")
public class PatternResource {

    private final Logger log = LoggerFactory.getLogger(PatternResource.class);

    private static final String ENTITY_NAME = "pattern";

    private final PatternService patternService;

    public PatternResource(PatternService patternService) {
        this.patternService = patternService;
    }

    /**
     * POST  /patterns : Create a new pattern.
     *
     * @param patternDTO the patternDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patternDTO, or with status 400 (Bad Request) if the pattern has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patterns")
    @Timed
    public ResponseEntity<PatternDTO> createPattern(@Valid @RequestBody PatternDTO patternDTO) throws URISyntaxException {
        log.debug("REST request to save Pattern : {}", patternDTO);
        if (patternDTO.getId() != null) {
            throw new BadRequestAlertException("A new pattern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatternDTO result = patternService.save(patternDTO);
        return ResponseEntity.created(new URI("/api/patterns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patterns : Updates an existing pattern.
     *
     * @param patternDTO the patternDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patternDTO,
     * or with status 400 (Bad Request) if the patternDTO is not valid,
     * or with status 500 (Internal Server Error) if the patternDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patterns")
    @Timed
    public ResponseEntity<PatternDTO> updatePattern(@Valid @RequestBody PatternDTO patternDTO) throws URISyntaxException {
        log.debug("REST request to update Pattern : {}", patternDTO);
        if (patternDTO.getId() == null) {
            return createPattern(patternDTO);
        }
        PatternDTO result = patternService.save(patternDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patternDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patterns : get all the patterns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of patterns in body
     */
    @GetMapping("/patterns")
    @Timed
    public ResponseEntity<List<PatternDTO>> getAllPatterns(Pageable pageable) {
        log.debug("REST request to get a page of Patterns");
        Page<PatternDTO> page = patternService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patterns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /patterns/:id : get the "id" pattern.
     *
     * @param id the id of the patternDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patternDTO, or with status 404 (Not Found)
     */
    @GetMapping("/patterns/{id}")
    @Timed
    public ResponseEntity<PatternDTO> getPattern(@PathVariable Long id) {
        log.debug("REST request to get Pattern : {}", id);
        PatternDTO patternDTO = patternService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(patternDTO));
    }

    /**
     * DELETE  /patterns/:id : delete the "id" pattern.
     *
     * @param id the id of the patternDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patterns/{id}")
    @Timed
    public ResponseEntity<Void> deletePattern(@PathVariable Long id) {
        log.debug("REST request to delete Pattern : {}", id);
        patternService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/patterns?query=:query : search for the pattern corresponding
     * to the query.
     *
     * @param query the query of the pattern search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/patterns")
    @Timed
    public ResponseEntity<List<PatternDTO>> searchPatterns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Patterns for query {}", query);
        Page<PatternDTO> page = patternService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/patterns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
