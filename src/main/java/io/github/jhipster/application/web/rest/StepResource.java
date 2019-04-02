package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Step;
import io.github.jhipster.application.repository.StepRepository;
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
 * REST controller for managing Step.
 */
@RestController
@RequestMapping("/api")
public class StepResource {

    private final Logger log = LoggerFactory.getLogger(StepResource.class);

    private static final String ENTITY_NAME = "step";

    private final StepRepository stepRepository;

    public StepResource(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    /**
     * POST  /steps : Create a new step.
     *
     * @param step the step to create
     * @return the ResponseEntity with status 201 (Created) and with body the new step, or with status 400 (Bad Request) if the step has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/steps")
    public ResponseEntity<Step> createStep(@RequestBody Step step) throws URISyntaxException {
        log.debug("REST request to save Step : {}", step);
        if (step.getId() != null) {
            throw new BadRequestAlertException("A new step cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Step result = stepRepository.save(step);
        return ResponseEntity.created(new URI("/api/steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /steps : Updates an existing step.
     *
     * @param step the step to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated step,
     * or with status 400 (Bad Request) if the step is not valid,
     * or with status 500 (Internal Server Error) if the step couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/steps")
    public ResponseEntity<Step> updateStep(@RequestBody Step step) throws URISyntaxException {
        log.debug("REST request to update Step : {}", step);
        if (step.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Step result = stepRepository.save(step);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, step.getId().toString()))
            .body(result);
    }

    /**
     * GET  /steps : get all the steps.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of steps in body
     */
    @GetMapping("/steps")
    public List<Step> getAllSteps(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Steps");
        return stepRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /steps/:id : get the "id" step.
     *
     * @param id the id of the step to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the step, or with status 404 (Not Found)
     */
    @GetMapping("/steps/{id}")
    public ResponseEntity<Step> getStep(@PathVariable Long id) {
        log.debug("REST request to get Step : {}", id);
        Optional<Step> step = stepRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(step);
    }

    /**
     * DELETE  /steps/:id : delete the "id" step.
     *
     * @param id the id of the step to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/steps/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long id) {
        log.debug("REST request to delete Step : {}", id);
        stepRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
