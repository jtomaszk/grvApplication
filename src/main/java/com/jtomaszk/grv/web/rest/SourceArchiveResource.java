package com.jtomaszk.grv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jtomaszk.grv.service.SourceArchiveService;
import com.jtomaszk.grv.web.rest.errors.BadRequestAlertException;
import com.jtomaszk.grv.web.rest.util.HeaderUtil;
import com.jtomaszk.grv.web.rest.util.PaginationUtil;
import com.jtomaszk.grv.service.dto.SourceArchiveDTO;
import com.jtomaszk.grv.service.dto.SourceArchiveCriteria;
import com.jtomaszk.grv.service.SourceArchiveQueryService;
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

/**
 * REST controller for managing SourceArchive.
 */
@RestController
@RequestMapping("/api")
public class SourceArchiveResource {

    private final Logger log = LoggerFactory.getLogger(SourceArchiveResource.class);

    private static final String ENTITY_NAME = "sourceArchive";

    private final SourceArchiveService sourceArchiveService;

    private final SourceArchiveQueryService sourceArchiveQueryService;

    public SourceArchiveResource(SourceArchiveService sourceArchiveService, SourceArchiveQueryService sourceArchiveQueryService) {
        this.sourceArchiveService = sourceArchiveService;
        this.sourceArchiveQueryService = sourceArchiveQueryService;
    }

    /**
     * POST  /source-archives : Create a new sourceArchive.
     *
     * @param sourceArchiveDTO the sourceArchiveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourceArchiveDTO, or with status 400 (Bad Request) if the sourceArchive has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/source-archives")
    @Timed
    public ResponseEntity<SourceArchiveDTO> createSourceArchive(@Valid @RequestBody SourceArchiveDTO sourceArchiveDTO) throws URISyntaxException {
        log.debug("REST request to save SourceArchive : {}", sourceArchiveDTO);
        if (sourceArchiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new sourceArchive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceArchiveDTO result = sourceArchiveService.save(sourceArchiveDTO);
        return ResponseEntity.created(new URI("/api/source-archives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /source-archives : Updates an existing sourceArchive.
     *
     * @param sourceArchiveDTO the sourceArchiveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourceArchiveDTO,
     * or with status 400 (Bad Request) if the sourceArchiveDTO is not valid,
     * or with status 500 (Internal Server Error) if the sourceArchiveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/source-archives")
    @Timed
    public ResponseEntity<SourceArchiveDTO> updateSourceArchive(@Valid @RequestBody SourceArchiveDTO sourceArchiveDTO) throws URISyntaxException {
        log.debug("REST request to update SourceArchive : {}", sourceArchiveDTO);
        if (sourceArchiveDTO.getId() == null) {
            return createSourceArchive(sourceArchiveDTO);
        }
        SourceArchiveDTO result = sourceArchiveService.save(sourceArchiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourceArchiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /source-archives : get all the sourceArchives.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sourceArchives in body
     */
    @GetMapping("/source-archives")
    @Timed
    public ResponseEntity<List<SourceArchiveDTO>> getAllSourceArchives(SourceArchiveCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SourceArchives by criteria: {}", criteria);
        Page<SourceArchiveDTO> page = sourceArchiveQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/source-archives");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /source-archives/:id : get the "id" sourceArchive.
     *
     * @param id the id of the sourceArchiveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourceArchiveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/source-archives/{id}")
    @Timed
    public ResponseEntity<SourceArchiveDTO> getSourceArchive(@PathVariable Long id) {
        log.debug("REST request to get SourceArchive : {}", id);
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sourceArchiveDTO));
    }

    /**
     * DELETE  /source-archives/:id : delete the "id" sourceArchive.
     *
     * @param id the id of the sourceArchiveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/source-archives/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourceArchive(@PathVariable Long id) {
        log.debug("REST request to delete SourceArchive : {}", id);
        sourceArchiveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
