package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.VersionSummary;
import io.github.jhipster.application.repository.VersionSummaryRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VersionSummary.
 */
@RestController
@RequestMapping("/api")
public class VersionSummaryResource {

    private final Logger log = LoggerFactory.getLogger(VersionSummaryResource.class);

    private static final String ENTITY_NAME = "versionSummary";

    private final VersionSummaryRepository versionSummaryRepository;

    public VersionSummaryResource(VersionSummaryRepository versionSummaryRepository) {
        this.versionSummaryRepository = versionSummaryRepository;
    }

    /**
     * POST  /version-summaries : Create a new versionSummary.
     *
     * @param versionSummary the versionSummary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new versionSummary, or with status 400 (Bad Request) if the versionSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/version-summaries")
    public ResponseEntity<VersionSummary> createVersionSummary(@RequestBody VersionSummary versionSummary) throws URISyntaxException {
        log.debug("REST request to save VersionSummary : {}", versionSummary);
        if (versionSummary.getId() != null) {
            throw new BadRequestAlertException("A new versionSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VersionSummary result = versionSummaryRepository.save(versionSummary);
        return ResponseEntity.created(new URI("/api/version-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /version-summaries : Updates an existing versionSummary.
     *
     * @param versionSummary the versionSummary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated versionSummary,
     * or with status 400 (Bad Request) if the versionSummary is not valid,
     * or with status 500 (Internal Server Error) if the versionSummary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/version-summaries")
    public ResponseEntity<VersionSummary> updateVersionSummary(@RequestBody VersionSummary versionSummary) throws URISyntaxException {
        log.debug("REST request to update VersionSummary : {}", versionSummary);
        if (versionSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VersionSummary result = versionSummaryRepository.save(versionSummary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, versionSummary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /version-summaries : get all the versionSummaries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of versionSummaries in body
     */
    @GetMapping("/version-summaries")
    public List<VersionSummary> getAllVersionSummaries() {
        log.debug("REST request to get all VersionSummaries");
        return versionSummaryRepository.findAll();
    }

    /**
     * GET  /version-summaries/:id : get the "id" versionSummary.
     *
     * @param id the id of the versionSummary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the versionSummary, or with status 404 (Not Found)
     */
    @GetMapping("/version-summaries/{id}")
    public ResponseEntity<VersionSummary> getVersionSummary(@PathVariable Long id) {
        log.debug("REST request to get VersionSummary : {}", id);
        Optional<VersionSummary> versionSummary = versionSummaryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(versionSummary);
    }

    /**
     * DELETE  /version-summaries/:id : delete the "id" versionSummary.
     *
     * @param id the id of the versionSummary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/version-summaries/{id}")
    public ResponseEntity<Void> deleteVersionSummary(@PathVariable Long id) {
        log.debug("REST request to delete VersionSummary : {}", id);
        versionSummaryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
