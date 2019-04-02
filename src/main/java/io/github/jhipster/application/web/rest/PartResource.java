package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Part;
import io.github.jhipster.application.repository.PartRepository;
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
 * REST controller for managing Part.
 */
@RestController
@RequestMapping("/api")
public class PartResource {

    private final Logger log = LoggerFactory.getLogger(PartResource.class);

    private static final String ENTITY_NAME = "part";

    private final PartRepository partRepository;

    public PartResource(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /**
     * POST  /parts : Create a new part.
     *
     * @param part the part to create
     * @return the ResponseEntity with status 201 (Created) and with body the new part, or with status 400 (Bad Request) if the part has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parts")
    public ResponseEntity<Part> createPart(@RequestBody Part part) throws URISyntaxException {
        log.debug("REST request to save Part : {}", part);
        if (part.getId() != null) {
            throw new BadRequestAlertException("A new part cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Part result = partRepository.save(part);
        return ResponseEntity.created(new URI("/api/parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parts : Updates an existing part.
     *
     * @param part the part to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated part,
     * or with status 400 (Bad Request) if the part is not valid,
     * or with status 500 (Internal Server Error) if the part couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parts")
    public ResponseEntity<Part> updatePart(@RequestBody Part part) throws URISyntaxException {
        log.debug("REST request to update Part : {}", part);
        if (part.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Part result = partRepository.save(part);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, part.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parts : get all the parts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parts in body
     */
    @GetMapping("/parts")
    public List<Part> getAllParts() {
        log.debug("REST request to get all Parts");
        return partRepository.findAll();
    }

    /**
     * GET  /parts/:id : get the "id" part.
     *
     * @param id the id of the part to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the part, or with status 404 (Not Found)
     */
    @GetMapping("/parts/{id}")
    public ResponseEntity<Part> getPart(@PathVariable Long id) {
        log.debug("REST request to get Part : {}", id);
        Optional<Part> part = partRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(part);
    }

    /**
     * DELETE  /parts/:id : delete the "id" part.
     *
     * @param id the id of the part to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parts/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {
        log.debug("REST request to delete Part : {}", id);
        partRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
