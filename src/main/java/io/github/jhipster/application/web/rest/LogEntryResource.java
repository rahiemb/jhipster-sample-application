package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.LogEntry;
import io.github.jhipster.application.repository.LogEntryRepository;
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
 * REST controller for managing LogEntry.
 */
@RestController
@RequestMapping("/api")
public class LogEntryResource {

    private final Logger log = LoggerFactory.getLogger(LogEntryResource.class);

    private static final String ENTITY_NAME = "logEntry";

    private final LogEntryRepository logEntryRepository;

    public LogEntryResource(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    /**
     * POST  /log-entries : Create a new logEntry.
     *
     * @param logEntry the logEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logEntry, or with status 400 (Bad Request) if the logEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-entries")
    public ResponseEntity<LogEntry> createLogEntry(@RequestBody LogEntry logEntry) throws URISyntaxException {
        log.debug("REST request to save LogEntry : {}", logEntry);
        if (logEntry.getId() != null) {
            throw new BadRequestAlertException("A new logEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogEntry result = logEntryRepository.save(logEntry);
        return ResponseEntity.created(new URI("/api/log-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-entries : Updates an existing logEntry.
     *
     * @param logEntry the logEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logEntry,
     * or with status 400 (Bad Request) if the logEntry is not valid,
     * or with status 500 (Internal Server Error) if the logEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-entries")
    public ResponseEntity<LogEntry> updateLogEntry(@RequestBody LogEntry logEntry) throws URISyntaxException {
        log.debug("REST request to update LogEntry : {}", logEntry);
        if (logEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogEntry result = logEntryRepository.save(logEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-entries : get all the logEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logEntries in body
     */
    @GetMapping("/log-entries")
    public List<LogEntry> getAllLogEntries() {
        log.debug("REST request to get all LogEntries");
        return logEntryRepository.findAll();
    }

    /**
     * GET  /log-entries/:id : get the "id" logEntry.
     *
     * @param id the id of the logEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logEntry, or with status 404 (Not Found)
     */
    @GetMapping("/log-entries/{id}")
    public ResponseEntity<LogEntry> getLogEntry(@PathVariable Long id) {
        log.debug("REST request to get LogEntry : {}", id);
        Optional<LogEntry> logEntry = logEntryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(logEntry);
    }

    /**
     * DELETE  /log-entries/:id : delete the "id" logEntry.
     *
     * @param id the id of the logEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-entries/{id}")
    public ResponseEntity<Void> deleteLogEntry(@PathVariable Long id) {
        log.debug("REST request to delete LogEntry : {}", id);
        logEntryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
