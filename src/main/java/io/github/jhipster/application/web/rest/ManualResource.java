package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Manual;
import io.github.jhipster.application.repository.ManualRepository;
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
 * REST controller for managing Manual.
 */
@RestController
@RequestMapping("/api")
public class ManualResource {

    private final Logger log = LoggerFactory.getLogger(ManualResource.class);

    private static final String ENTITY_NAME = "manual";

    private final ManualRepository manualRepository;

    public ManualResource(ManualRepository manualRepository) {
        this.manualRepository = manualRepository;
    }

    /**
     * POST  /manuals : Create a new manual.
     *
     * @param manual the manual to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manual, or with status 400 (Bad Request) if the manual has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manuals")
    public ResponseEntity<Manual> createManual(@RequestBody Manual manual) throws URISyntaxException {
        log.debug("REST request to save Manual : {}", manual);
        if (manual.getId() != null) {
            throw new BadRequestAlertException("A new manual cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Manual result = manualRepository.save(manual);
        return ResponseEntity.created(new URI("/api/manuals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manuals : Updates an existing manual.
     *
     * @param manual the manual to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manual,
     * or with status 400 (Bad Request) if the manual is not valid,
     * or with status 500 (Internal Server Error) if the manual couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manuals")
    public ResponseEntity<Manual> updateManual(@RequestBody Manual manual) throws URISyntaxException {
        log.debug("REST request to update Manual : {}", manual);
        if (manual.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Manual result = manualRepository.save(manual);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, manual.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manuals : get all the manuals.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of manuals in body
     */
    @GetMapping("/manuals")
    public List<Manual> getAllManuals(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Manuals");
        return manualRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /manuals/:id : get the "id" manual.
     *
     * @param id the id of the manual to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manual, or with status 404 (Not Found)
     */
    @GetMapping("/manuals/{id}")
    public ResponseEntity<Manual> getManual(@PathVariable Long id) {
        log.debug("REST request to get Manual : {}", id);
        Optional<Manual> manual = manualRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(manual);
    }

    /**
     * DELETE  /manuals/:id : delete the "id" manual.
     *
     * @param id the id of the manual to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manuals/{id}")
    public ResponseEntity<Void> deleteManual(@PathVariable Long id) {
        log.debug("REST request to delete Manual : {}", id);
        manualRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
