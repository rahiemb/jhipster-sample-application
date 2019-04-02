package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Ability;
import io.github.jhipster.application.repository.AbilityRepository;
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
 * REST controller for managing Ability.
 */
@RestController
@RequestMapping("/api")
public class AbilityResource {

    private final Logger log = LoggerFactory.getLogger(AbilityResource.class);

    private static final String ENTITY_NAME = "ability";

    private final AbilityRepository abilityRepository;

    public AbilityResource(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    /**
     * POST  /abilities : Create a new ability.
     *
     * @param ability the ability to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ability, or with status 400 (Bad Request) if the ability has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abilities")
    public ResponseEntity<Ability> createAbility(@RequestBody Ability ability) throws URISyntaxException {
        log.debug("REST request to save Ability : {}", ability);
        if (ability.getId() != null) {
            throw new BadRequestAlertException("A new ability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ability result = abilityRepository.save(ability);
        return ResponseEntity.created(new URI("/api/abilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abilities : Updates an existing ability.
     *
     * @param ability the ability to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ability,
     * or with status 400 (Bad Request) if the ability is not valid,
     * or with status 500 (Internal Server Error) if the ability couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abilities")
    public ResponseEntity<Ability> updateAbility(@RequestBody Ability ability) throws URISyntaxException {
        log.debug("REST request to update Ability : {}", ability);
        if (ability.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ability result = abilityRepository.save(ability);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ability.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abilities : get all the abilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of abilities in body
     */
    @GetMapping("/abilities")
    public List<Ability> getAllAbilities() {
        log.debug("REST request to get all Abilities");
        return abilityRepository.findAll();
    }

    /**
     * GET  /abilities/:id : get the "id" ability.
     *
     * @param id the id of the ability to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ability, or with status 404 (Not Found)
     */
    @GetMapping("/abilities/{id}")
    public ResponseEntity<Ability> getAbility(@PathVariable Long id) {
        log.debug("REST request to get Ability : {}", id);
        Optional<Ability> ability = abilityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ability);
    }

    /**
     * DELETE  /abilities/:id : delete the "id" ability.
     *
     * @param id the id of the ability to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abilities/{id}")
    public ResponseEntity<Void> deleteAbility(@PathVariable Long id) {
        log.debug("REST request to delete Ability : {}", id);
        abilityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
