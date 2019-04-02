package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Recipient;
import io.github.jhipster.application.repository.RecipientRepository;
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
 * REST controller for managing Recipient.
 */
@RestController
@RequestMapping("/api")
public class RecipientResource {

    private final Logger log = LoggerFactory.getLogger(RecipientResource.class);

    private static final String ENTITY_NAME = "recipient";

    private final RecipientRepository recipientRepository;

    public RecipientResource(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    /**
     * POST  /recipients : Create a new recipient.
     *
     * @param recipient the recipient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipient, or with status 400 (Bad Request) if the recipient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipients")
    public ResponseEntity<Recipient> createRecipient(@RequestBody Recipient recipient) throws URISyntaxException {
        log.debug("REST request to save Recipient : {}", recipient);
        if (recipient.getId() != null) {
            throw new BadRequestAlertException("A new recipient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recipient result = recipientRepository.save(recipient);
        return ResponseEntity.created(new URI("/api/recipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipients : Updates an existing recipient.
     *
     * @param recipient the recipient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipient,
     * or with status 400 (Bad Request) if the recipient is not valid,
     * or with status 500 (Internal Server Error) if the recipient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipients")
    public ResponseEntity<Recipient> updateRecipient(@RequestBody Recipient recipient) throws URISyntaxException {
        log.debug("REST request to update Recipient : {}", recipient);
        if (recipient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recipient result = recipientRepository.save(recipient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipients : get all the recipients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recipients in body
     */
    @GetMapping("/recipients")
    public List<Recipient> getAllRecipients() {
        log.debug("REST request to get all Recipients");
        return recipientRepository.findAll();
    }

    /**
     * GET  /recipients/:id : get the "id" recipient.
     *
     * @param id the id of the recipient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipient, or with status 404 (Not Found)
     */
    @GetMapping("/recipients/{id}")
    public ResponseEntity<Recipient> getRecipient(@PathVariable Long id) {
        log.debug("REST request to get Recipient : {}", id);
        Optional<Recipient> recipient = recipientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recipient);
    }

    /**
     * DELETE  /recipients/:id : delete the "id" recipient.
     *
     * @param id the id of the recipient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipients/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        log.debug("REST request to delete Recipient : {}", id);
        recipientRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
