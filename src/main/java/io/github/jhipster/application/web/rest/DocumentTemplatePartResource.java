package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.DocumentTemplatePart;
import io.github.jhipster.application.repository.DocumentTemplatePartRepository;
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
 * REST controller for managing DocumentTemplatePart.
 */
@RestController
@RequestMapping("/api")
public class DocumentTemplatePartResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTemplatePartResource.class);

    private static final String ENTITY_NAME = "documentTemplatePart";

    private final DocumentTemplatePartRepository documentTemplatePartRepository;

    public DocumentTemplatePartResource(DocumentTemplatePartRepository documentTemplatePartRepository) {
        this.documentTemplatePartRepository = documentTemplatePartRepository;
    }

    /**
     * POST  /document-template-parts : Create a new documentTemplatePart.
     *
     * @param documentTemplatePart the documentTemplatePart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTemplatePart, or with status 400 (Bad Request) if the documentTemplatePart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-template-parts")
    public ResponseEntity<DocumentTemplatePart> createDocumentTemplatePart(@RequestBody DocumentTemplatePart documentTemplatePart) throws URISyntaxException {
        log.debug("REST request to save DocumentTemplatePart : {}", documentTemplatePart);
        if (documentTemplatePart.getId() != null) {
            throw new BadRequestAlertException("A new documentTemplatePart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTemplatePart result = documentTemplatePartRepository.save(documentTemplatePart);
        return ResponseEntity.created(new URI("/api/document-template-parts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-template-parts : Updates an existing documentTemplatePart.
     *
     * @param documentTemplatePart the documentTemplatePart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTemplatePart,
     * or with status 400 (Bad Request) if the documentTemplatePart is not valid,
     * or with status 500 (Internal Server Error) if the documentTemplatePart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-template-parts")
    public ResponseEntity<DocumentTemplatePart> updateDocumentTemplatePart(@RequestBody DocumentTemplatePart documentTemplatePart) throws URISyntaxException {
        log.debug("REST request to update DocumentTemplatePart : {}", documentTemplatePart);
        if (documentTemplatePart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTemplatePart result = documentTemplatePartRepository.save(documentTemplatePart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTemplatePart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-template-parts : get all the documentTemplateParts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of documentTemplateParts in body
     */
    @GetMapping("/document-template-parts")
    public List<DocumentTemplatePart> getAllDocumentTemplateParts() {
        log.debug("REST request to get all DocumentTemplateParts");
        return documentTemplatePartRepository.findAll();
    }

    /**
     * GET  /document-template-parts/:id : get the "id" documentTemplatePart.
     *
     * @param id the id of the documentTemplatePart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTemplatePart, or with status 404 (Not Found)
     */
    @GetMapping("/document-template-parts/{id}")
    public ResponseEntity<DocumentTemplatePart> getDocumentTemplatePart(@PathVariable Long id) {
        log.debug("REST request to get DocumentTemplatePart : {}", id);
        Optional<DocumentTemplatePart> documentTemplatePart = documentTemplatePartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentTemplatePart);
    }

    /**
     * DELETE  /document-template-parts/:id : delete the "id" documentTemplatePart.
     *
     * @param id the id of the documentTemplatePart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-template-parts/{id}")
    public ResponseEntity<Void> deleteDocumentTemplatePart(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTemplatePart : {}", id);
        documentTemplatePartRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
