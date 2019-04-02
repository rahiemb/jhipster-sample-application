package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Bookmark;
import io.github.jhipster.application.repository.BookmarkRepository;
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
 * REST controller for managing Bookmark.
 */
@RestController
@RequestMapping("/api")
public class BookmarkResource {

    private final Logger log = LoggerFactory.getLogger(BookmarkResource.class);

    private static final String ENTITY_NAME = "bookmark";

    private final BookmarkRepository bookmarkRepository;

    public BookmarkResource(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    /**
     * POST  /bookmarks : Create a new bookmark.
     *
     * @param bookmark the bookmark to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookmark, or with status 400 (Bad Request) if the bookmark has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bookmarks")
    public ResponseEntity<Bookmark> createBookmark(@RequestBody Bookmark bookmark) throws URISyntaxException {
        log.debug("REST request to save Bookmark : {}", bookmark);
        if (bookmark.getId() != null) {
            throw new BadRequestAlertException("A new bookmark cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bookmark result = bookmarkRepository.save(bookmark);
        return ResponseEntity.created(new URI("/api/bookmarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bookmarks : Updates an existing bookmark.
     *
     * @param bookmark the bookmark to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookmark,
     * or with status 400 (Bad Request) if the bookmark is not valid,
     * or with status 500 (Internal Server Error) if the bookmark couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bookmarks")
    public ResponseEntity<Bookmark> updateBookmark(@RequestBody Bookmark bookmark) throws URISyntaxException {
        log.debug("REST request to update Bookmark : {}", bookmark);
        if (bookmark.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bookmark result = bookmarkRepository.save(bookmark);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookmark.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bookmarks : get all the bookmarks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookmarks in body
     */
    @GetMapping("/bookmarks")
    public List<Bookmark> getAllBookmarks() {
        log.debug("REST request to get all Bookmarks");
        return bookmarkRepository.findAll();
    }

    /**
     * GET  /bookmarks/:id : get the "id" bookmark.
     *
     * @param id the id of the bookmark to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookmark, or with status 404 (Not Found)
     */
    @GetMapping("/bookmarks/{id}")
    public ResponseEntity<Bookmark> getBookmark(@PathVariable Long id) {
        log.debug("REST request to get Bookmark : {}", id);
        Optional<Bookmark> bookmark = bookmarkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bookmark);
    }

    /**
     * DELETE  /bookmarks/:id : delete the "id" bookmark.
     *
     * @param id the id of the bookmark to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bookmarks/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
        log.debug("REST request to delete Bookmark : {}", id);
        bookmarkRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
