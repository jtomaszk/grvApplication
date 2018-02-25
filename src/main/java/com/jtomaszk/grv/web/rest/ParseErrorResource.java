package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.ParseErrorService;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import com.jtomaszk.grv.service.dto.ParseErrorDTO;
import com.jtomaszk.grv.service.dto.ParseErrorCriteria;
import com.jtomaszk.grv.service.ParseErrorQueryService;
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
 * REST controller for managing ParseError.
 */
@RestController
@RequestMapping("/api")
public class ParseErrorResource {

    private final Logger log = LoggerFactory.getLogger(ParseErrorResource.class);

    private static final String ENTITY_NAME = "parseError";

    private final ParseErrorService parseErrorService;

    private final ParseErrorQueryService parseErrorQueryService;

    public ParseErrorResource(ParseErrorService parseErrorService, ParseErrorQueryService parseErrorQueryService) {
        this.parseErrorService = parseErrorService;
        this.parseErrorQueryService = parseErrorQueryService;
    }

    /**
     * POST  /parse-errors : Create a new parseError.
     *
     * @param parseErrorDTO the parseErrorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parseErrorDTO, or with status 400 (Bad Request) if the parseError has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parse-errors")
    @Timed
    public ResponseEntity<ParseErrorDTO> createParseError(@Valid @RequestBody ParseErrorDTO parseErrorDTO) throws URISyntaxException {
        log.debug("REST request to save ParseError : {}", parseErrorDTO);
        if (parseErrorDTO.getId() != null) {
            throw new BadRequestAlertException("A new parseError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParseErrorDTO result = parseErrorService.save(parseErrorDTO);
        return ResponseEntity.created(new URI("/api/parse-errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parse-errors : Updates an existing parseError.
     *
     * @param parseErrorDTO the parseErrorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parseErrorDTO,
     * or with status 400 (Bad Request) if the parseErrorDTO is not valid,
     * or with status 500 (Internal Server Error) if the parseErrorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parse-errors")
    @Timed
    public ResponseEntity<ParseErrorDTO> updateParseError(@Valid @RequestBody ParseErrorDTO parseErrorDTO) throws URISyntaxException {
        log.debug("REST request to update ParseError : {}", parseErrorDTO);
        if (parseErrorDTO.getId() == null) {
            return createParseError(parseErrorDTO);
        }
        ParseErrorDTO result = parseErrorService.save(parseErrorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parseErrorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parse-errors : get all the parseErrors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of parseErrors in body
     */
    @GetMapping("/parse-errors")
    @Timed
    public ResponseEntity<List<ParseErrorDTO>> getAllParseErrors(ParseErrorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParseErrors by criteria: {}", criteria);
        Page<ParseErrorDTO> page = parseErrorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parse-errors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parse-errors/:id : get the "id" parseError.
     *
     * @param id the id of the parseErrorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parseErrorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parse-errors/{id}")
    @Timed
    public ResponseEntity<ParseErrorDTO> getParseError(@PathVariable Long id) {
        log.debug("REST request to get ParseError : {}", id);
        ParseErrorDTO parseErrorDTO = parseErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parseErrorDTO));
    }

    /**
     * DELETE  /parse-errors/:id : delete the "id" parseError.
     *
     * @param id the id of the parseErrorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parse-errors/{id}")
    @Timed
    public ResponseEntity<Void> deleteParseError(@PathVariable Long id) {
        log.debug("REST request to delete ParseError : {}", id);
        parseErrorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parse-errors?query=:query : search for the parseError corresponding
     * to the query.
     *
     * @param query the query of the parseError search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/parse-errors")
    @Timed
    public ResponseEntity<List<ParseErrorDTO>> searchParseErrors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParseErrors for query {}", query);
        Page<ParseErrorDTO> page = parseErrorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/parse-errors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
