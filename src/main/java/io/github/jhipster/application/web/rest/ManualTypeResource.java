package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.ManualType;
import io.github.jhipster.application.repository.ManualTypeRepository;
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
 * REST controller for managing ManualType.
 */
@RestController
@RequestMapping("/api")
public class ManualTypeResource {

    private final Logger log = LoggerFactory.getLogger(ManualTypeResource.class);

    private static final String ENTITY_NAME = "manualType";

    private final ManualTypeRepository manualTypeRepository;

    public ManualTypeResource(ManualTypeRepository manualTypeRepository) {
        this.manualTypeRepository = manualTypeRepository;
    }

    /**
     * POST  /manual-types : Create a new manualType.
     *
     * @param manualType the manualType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manualType, or with status 400 (Bad Request) if the manualType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manual-types")
    public ResponseEntity<ManualType> createManualType(@RequestBody ManualType manualType) throws URISyntaxException {
        log.debug("REST request to save ManualType : {}", manualType);
        if (manualType.getId() != null) {
            throw new BadRequestAlertException("A new manualType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManualType result = manualTypeRepository.save(manualType);
        return ResponseEntity.created(new URI("/api/manual-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manual-types : Updates an existing manualType.
     *
     * @param manualType the manualType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manualType,
     * or with status 400 (Bad Request) if the manualType is not valid,
     * or with status 500 (Internal Server Error) if the manualType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manual-types")
    public ResponseEntity<ManualType> updateManualType(@RequestBody ManualType manualType) throws URISyntaxException {
        log.debug("REST request to update ManualType : {}", manualType);
        if (manualType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ManualType result = manualTypeRepository.save(manualType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, manualType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manual-types : get all the manualTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of manualTypes in body
     */
    @GetMapping("/manual-types")
    public List<ManualType> getAllManualTypes() {
        log.debug("REST request to get all ManualTypes");
        return manualTypeRepository.findAll();
    }

    /**
     * GET  /manual-types/:id : get the "id" manualType.
     *
     * @param id the id of the manualType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manualType, or with status 404 (Not Found)
     */
    @GetMapping("/manual-types/{id}")
    public ResponseEntity<ManualType> getManualType(@PathVariable Long id) {
        log.debug("REST request to get ManualType : {}", id);
        Optional<ManualType> manualType = manualTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(manualType);
    }

    /**
     * DELETE  /manual-types/:id : delete the "id" manualType.
     *
     * @param id the id of the manualType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manual-types/{id}")
    public ResponseEntity<Void> deleteManualType(@PathVariable Long id) {
        log.debug("REST request to delete ManualType : {}", id);
        manualTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
