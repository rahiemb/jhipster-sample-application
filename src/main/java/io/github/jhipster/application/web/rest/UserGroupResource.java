package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.UserGroup;
import io.github.jhipster.application.repository.UserGroupRepository;
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
 * REST controller for managing UserGroup.
 */
@RestController
@RequestMapping("/api")
public class UserGroupResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupResource.class);

    private static final String ENTITY_NAME = "userGroup";

    private final UserGroupRepository userGroupRepository;

    public UserGroupResource(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    /**
     * POST  /user-groups : Create a new userGroup.
     *
     * @param userGroup the userGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userGroup, or with status 400 (Bad Request) if the userGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-groups")
    public ResponseEntity<UserGroup> createUserGroup(@RequestBody UserGroup userGroup) throws URISyntaxException {
        log.debug("REST request to save UserGroup : {}", userGroup);
        if (userGroup.getId() != null) {
            throw new BadRequestAlertException("A new userGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroup result = userGroupRepository.save(userGroup);
        return ResponseEntity.created(new URI("/api/user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-groups : Updates an existing userGroup.
     *
     * @param userGroup the userGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userGroup,
     * or with status 400 (Bad Request) if the userGroup is not valid,
     * or with status 500 (Internal Server Error) if the userGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-groups")
    public ResponseEntity<UserGroup> updateUserGroup(@RequestBody UserGroup userGroup) throws URISyntaxException {
        log.debug("REST request to update UserGroup : {}", userGroup);
        if (userGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroup result = userGroupRepository.save(userGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-groups : get all the userGroups.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of userGroups in body
     */
    @GetMapping("/user-groups")
    public List<UserGroup> getAllUserGroups(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all UserGroups");
        return userGroupRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /user-groups/:id : get the "id" userGroup.
     *
     * @param id the id of the userGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userGroup, or with status 404 (Not Found)
     */
    @GetMapping("/user-groups/{id}")
    public ResponseEntity<UserGroup> getUserGroup(@PathVariable Long id) {
        log.debug("REST request to get UserGroup : {}", id);
        Optional<UserGroup> userGroup = userGroupRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(userGroup);
    }

    /**
     * DELETE  /user-groups/:id : delete the "id" userGroup.
     *
     * @param id the id of the userGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-groups/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete UserGroup : {}", id);
        userGroupRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
