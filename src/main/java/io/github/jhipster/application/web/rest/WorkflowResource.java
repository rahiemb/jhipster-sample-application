package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Workflow;
import io.github.jhipster.application.repository.WorkflowRepository;
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
 * REST controller for managing Workflow.
 */
@RestController
@RequestMapping("/api")
public class WorkflowResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowResource.class);

    private static final String ENTITY_NAME = "workflow";

    private final WorkflowRepository workflowRepository;

    public WorkflowResource(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    /**
     * POST  /workflows : Create a new workflow.
     *
     * @param workflow the workflow to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workflow, or with status 400 (Bad Request) if the workflow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workflows")
    public ResponseEntity<Workflow> createWorkflow(@RequestBody Workflow workflow) throws URISyntaxException {
        log.debug("REST request to save Workflow : {}", workflow);
        if (workflow.getId() != null) {
            throw new BadRequestAlertException("A new workflow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Workflow result = workflowRepository.save(workflow);
        return ResponseEntity.created(new URI("/api/workflows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workflows : Updates an existing workflow.
     *
     * @param workflow the workflow to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workflow,
     * or with status 400 (Bad Request) if the workflow is not valid,
     * or with status 500 (Internal Server Error) if the workflow couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workflows")
    public ResponseEntity<Workflow> updateWorkflow(@RequestBody Workflow workflow) throws URISyntaxException {
        log.debug("REST request to update Workflow : {}", workflow);
        if (workflow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Workflow result = workflowRepository.save(workflow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workflow.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workflows : get all the workflows.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workflows in body
     */
    @GetMapping("/workflows")
    public List<Workflow> getAllWorkflows() {
        log.debug("REST request to get all Workflows");
        return workflowRepository.findAll();
    }

    /**
     * GET  /workflows/:id : get the "id" workflow.
     *
     * @param id the id of the workflow to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workflow, or with status 404 (Not Found)
     */
    @GetMapping("/workflows/{id}")
    public ResponseEntity<Workflow> getWorkflow(@PathVariable Long id) {
        log.debug("REST request to get Workflow : {}", id);
        Optional<Workflow> workflow = workflowRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workflow);
    }

    /**
     * DELETE  /workflows/:id : delete the "id" workflow.
     *
     * @param id the id of the workflow to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workflows/{id}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable Long id) {
        log.debug("REST request to delete Workflow : {}", id);
        workflowRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
