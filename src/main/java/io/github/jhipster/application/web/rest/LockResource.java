package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Lock;
import io.github.jhipster.application.repository.LockRepository;
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
 * REST controller for managing Lock.
 */
@RestController
@RequestMapping("/api")
public class LockResource {

    private final Logger log = LoggerFactory.getLogger(LockResource.class);

    private static final String ENTITY_NAME = "lock";

    private final LockRepository lockRepository;

    public LockResource(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    /**
     * POST  /locks : Create a new lock.
     *
     * @param lock the lock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lock, or with status 400 (Bad Request) if the lock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locks")
    public ResponseEntity<Lock> createLock(@RequestBody Lock lock) throws URISyntaxException {
        log.debug("REST request to save Lock : {}", lock);
        if (lock.getId() != null) {
            throw new BadRequestAlertException("A new lock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lock result = lockRepository.save(lock);
        return ResponseEntity.created(new URI("/api/locks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locks : Updates an existing lock.
     *
     * @param lock the lock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lock,
     * or with status 400 (Bad Request) if the lock is not valid,
     * or with status 500 (Internal Server Error) if the lock couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locks")
    public ResponseEntity<Lock> updateLock(@RequestBody Lock lock) throws URISyntaxException {
        log.debug("REST request to update Lock : {}", lock);
        if (lock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lock result = lockRepository.save(lock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locks : get all the locks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locks in body
     */
    @GetMapping("/locks")
    public List<Lock> getAllLocks() {
        log.debug("REST request to get all Locks");
        return lockRepository.findAll();
    }

    /**
     * GET  /locks/:id : get the "id" lock.
     *
     * @param id the id of the lock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lock, or with status 404 (Not Found)
     */
    @GetMapping("/locks/{id}")
    public ResponseEntity<Lock> getLock(@PathVariable Long id) {
        log.debug("REST request to get Lock : {}", id);
        Optional<Lock> lock = lockRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lock);
    }

    /**
     * DELETE  /locks/:id : delete the "id" lock.
     *
     * @param id the id of the lock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locks/{id}")
    public ResponseEntity<Void> deleteLock(@PathVariable Long id) {
        log.debug("REST request to delete Lock : {}", id);
        lockRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
