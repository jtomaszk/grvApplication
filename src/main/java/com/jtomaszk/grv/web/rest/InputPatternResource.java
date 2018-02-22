package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.InputPatternQueryService;
import com.jtomaszk.grv.service.InputPatternService;
import com.jtomaszk.grv.service.dto.InputPatternCriteria;
import com.jtomaszk.grv.service.dto.InputPatternDTO;
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
 * REST controller for managing InputPattern.
 */
@RestController
@RequestMapping("/api")
public class InputPatternResource {

    private final Logger log = LoggerFactory.getLogger(InputPatternResource.class);

    private static final String ENTITY_NAME = "inputPattern";

    private final InputPatternService inputPatternService;

    private final InputPatternQueryService inputPatternQueryService;

    public InputPatternResource(InputPatternService inputPatternService, InputPatternQueryService inputPatternQueryService) {
        this.inputPatternService = inputPatternService;
        this.inputPatternQueryService = inputPatternQueryService;
    }

    /**
     * POST  /input-patterns : Create a new inputPattern.
     *
     * @param inputPatternDTO the inputPatternDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inputPatternDTO, or with status 400 (Bad Request) if the inputPattern has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/input-patterns")
    @Timed
    public ResponseEntity<InputPatternDTO> createInputPattern(@Valid @RequestBody InputPatternDTO inputPatternDTO) throws URISyntaxException {
        log.debug("REST request to save InputPattern : {}", inputPatternDTO);
        if (inputPatternDTO.getId() != null) {
            throw new BadRequestAlertException("A new inputPattern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InputPatternDTO result = inputPatternService.save(inputPatternDTO);
        return ResponseEntity.created(new URI("/api/input-patterns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /input-patterns : Updates an existing inputPattern.
     *
     * @param inputPatternDTO the inputPatternDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inputPatternDTO,
     * or with status 400 (Bad Request) if the inputPatternDTO is not valid,
     * or with status 500 (Internal Server Error) if the inputPatternDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/input-patterns")
    @Timed
    public ResponseEntity<InputPatternDTO> updateInputPattern(@Valid @RequestBody InputPatternDTO inputPatternDTO) throws URISyntaxException {
        log.debug("REST request to update InputPattern : {}", inputPatternDTO);
        if (inputPatternDTO.getId() == null) {
            return createInputPattern(inputPatternDTO);
        }
        InputPatternDTO result = inputPatternService.save(inputPatternDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inputPatternDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /input-patterns : get all the inputPatterns.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of inputPatterns in body
     */
    @GetMapping("/input-patterns")
    @Timed
    public ResponseEntity<List<InputPatternDTO>> getAllInputPatterns(InputPatternCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InputPatterns by criteria: {}", criteria);
        Page<InputPatternDTO> page = inputPatternQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/input-patterns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /input-patterns/:id : get the "id" inputPattern.
     *
     * @param id the id of the inputPatternDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inputPatternDTO, or with status 404 (Not Found)
     */
    @GetMapping("/input-patterns/{id}")
    @Timed
    public ResponseEntity<InputPatternDTO> getInputPattern(@PathVariable Long id) {
        log.debug("REST request to get InputPattern : {}", id);
        InputPatternDTO inputPatternDTO = inputPatternService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inputPatternDTO));
    }

    /**
     * DELETE  /input-patterns/:id : delete the "id" inputPattern.
     *
     * @param id the id of the inputPatternDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/input-patterns/{id}")
    @Timed
    public ResponseEntity<Void> deleteInputPattern(@PathVariable Long id) {
        log.debug("REST request to delete InputPattern : {}", id);
        inputPatternService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/input-patterns?query=:query : search for the inputPattern corresponding
     * to the query.
     *
     * @param query the query of the inputPattern search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/input-patterns")
    @Timed
    public ResponseEntity<List<InputPatternDTO>> searchInputPatterns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InputPatterns for query {}", query);
        Page<InputPatternDTO> page = inputPatternService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/input-patterns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
