package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.DocumentTemplate;
import io.github.jhipster.application.repository.DocumentTemplateRepository;
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
 * REST controller for managing DocumentTemplate.
 */
@RestController
@RequestMapping("/api")
public class DocumentTemplateResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTemplateResource.class);

    private static final String ENTITY_NAME = "documentTemplate";

    private final DocumentTemplateRepository documentTemplateRepository;

    public DocumentTemplateResource(DocumentTemplateRepository documentTemplateRepository) {
        this.documentTemplateRepository = documentTemplateRepository;
    }

    /**
     * POST  /document-templates : Create a new documentTemplate.
     *
     * @param documentTemplate the documentTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTemplate, or with status 400 (Bad Request) if the documentTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-templates")
    public ResponseEntity<DocumentTemplate> createDocumentTemplate(@RequestBody DocumentTemplate documentTemplate) throws URISyntaxException {
        log.debug("REST request to save DocumentTemplate : {}", documentTemplate);
        if (documentTemplate.getId() != null) {
            throw new BadRequestAlertException("A new documentTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTemplate result = documentTemplateRepository.save(documentTemplate);
        return ResponseEntity.created(new URI("/api/document-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-templates : Updates an existing documentTemplate.
     *
     * @param documentTemplate the documentTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTemplate,
     * or with status 400 (Bad Request) if the documentTemplate is not valid,
     * or with status 500 (Internal Server Error) if the documentTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-templates")
    public ResponseEntity<DocumentTemplate> updateDocumentTemplate(@RequestBody DocumentTemplate documentTemplate) throws URISyntaxException {
        log.debug("REST request to update DocumentTemplate : {}", documentTemplate);
        if (documentTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTemplate result = documentTemplateRepository.save(documentTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-templates : get all the documentTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of documentTemplates in body
     */
    @GetMapping("/document-templates")
    public List<DocumentTemplate> getAllDocumentTemplates() {
        log.debug("REST request to get all DocumentTemplates");
        return documentTemplateRepository.findAll();
    }

    /**
     * GET  /document-templates/:id : get the "id" documentTemplate.
     *
     * @param id the id of the documentTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/document-templates/{id}")
    public ResponseEntity<DocumentTemplate> getDocumentTemplate(@PathVariable Long id) {
        log.debug("REST request to get DocumentTemplate : {}", id);
        Optional<DocumentTemplate> documentTemplate = documentTemplateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentTemplate);
    }

    /**
     * DELETE  /document-templates/:id : delete the "id" documentTemplate.
     *
     * @param id the id of the documentTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-templates/{id}")
    public ResponseEntity<Void> deleteDocumentTemplate(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTemplate : {}", id);
        documentTemplateRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
