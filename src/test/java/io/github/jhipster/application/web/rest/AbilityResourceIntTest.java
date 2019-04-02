package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Ability;
import io.github.jhipster.application.repository.AbilityRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.AbilityAction;
import io.github.jhipster.application.domain.enumeration.AbilityScope;
/**
 * Test class for the AbilityResource REST controller.
 *
 * @see AbilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class AbilityResourceIntTest {

    private static final Long DEFAULT_RESOURCE_ID = 1L;
    private static final Long UPDATED_RESOURCE_ID = 2L;

    private static final AbilityAction DEFAULT_ACTION = AbilityAction.VIEW;
    private static final AbilityAction UPDATED_ACTION = AbilityAction.EDIT;

    private static final AbilityScope DEFAULT_SCOPE = AbilityScope.ALLOW;
    private static final AbilityScope UPDATED_SCOPE = AbilityScope.DENY;

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAbilityMockMvc;

    private Ability ability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbilityResource abilityResource = new AbilityResource(abilityRepository);
        this.restAbilityMockMvc = MockMvcBuilders.standaloneSetup(abilityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ability createEntity(EntityManager em) {
        Ability ability = new Ability()
            .resourceId(DEFAULT_RESOURCE_ID)
            .action(DEFAULT_ACTION)
            .scope(DEFAULT_SCOPE);
        return ability;
    }

    @Before
    public void initTest() {
        ability = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbility() throws Exception {
        int databaseSizeBeforeCreate = abilityRepository.findAll().size();

        // Create the Ability
        restAbilityMockMvc.perform(post("/api/abilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ability)))
            .andExpect(status().isCreated());

        // Validate the Ability in the database
        List<Ability> abilityList = abilityRepository.findAll();
        assertThat(abilityList).hasSize(databaseSizeBeforeCreate + 1);
        Ability testAbility = abilityList.get(abilityList.size() - 1);
        assertThat(testAbility.getResourceId()).isEqualTo(DEFAULT_RESOURCE_ID);
        assertThat(testAbility.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAbility.getScope()).isEqualTo(DEFAULT_SCOPE);
    }

    @Test
    @Transactional
    public void createAbilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abilityRepository.findAll().size();

        // Create the Ability with an existing ID
        ability.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbilityMockMvc.perform(post("/api/abilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ability)))
            .andExpect(status().isBadRequest());

        // Validate the Ability in the database
        List<Ability> abilityList = abilityRepository.findAll();
        assertThat(abilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAbilities() throws Exception {
        // Initialize the database
        abilityRepository.saveAndFlush(ability);

        // Get all the abilityList
        restAbilityMockMvc.perform(get("/api/abilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ability.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].scope").value(hasItem(DEFAULT_SCOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAbility() throws Exception {
        // Initialize the database
        abilityRepository.saveAndFlush(ability);

        // Get the ability
        restAbilityMockMvc.perform(get("/api/abilities/{id}", ability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ability.getId().intValue()))
            .andExpect(jsonPath("$.resourceId").value(DEFAULT_RESOURCE_ID.intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.scope").value(DEFAULT_SCOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbility() throws Exception {
        // Get the ability
        restAbilityMockMvc.perform(get("/api/abilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbility() throws Exception {
        // Initialize the database
        abilityRepository.saveAndFlush(ability);

        int databaseSizeBeforeUpdate = abilityRepository.findAll().size();

        // Update the ability
        Ability updatedAbility = abilityRepository.findById(ability.getId()).get();
        // Disconnect from session so that the updates on updatedAbility are not directly saved in db
        em.detach(updatedAbility);
        updatedAbility
            .resourceId(UPDATED_RESOURCE_ID)
            .action(UPDATED_ACTION)
            .scope(UPDATED_SCOPE);

        restAbilityMockMvc.perform(put("/api/abilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbility)))
            .andExpect(status().isOk());

        // Validate the Ability in the database
        List<Ability> abilityList = abilityRepository.findAll();
        assertThat(abilityList).hasSize(databaseSizeBeforeUpdate);
        Ability testAbility = abilityList.get(abilityList.size() - 1);
        assertThat(testAbility.getResourceId()).isEqualTo(UPDATED_RESOURCE_ID);
        assertThat(testAbility.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAbility.getScope()).isEqualTo(UPDATED_SCOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAbility() throws Exception {
        int databaseSizeBeforeUpdate = abilityRepository.findAll().size();

        // Create the Ability

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbilityMockMvc.perform(put("/api/abilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ability)))
            .andExpect(status().isBadRequest());

        // Validate the Ability in the database
        List<Ability> abilityList = abilityRepository.findAll();
        assertThat(abilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbility() throws Exception {
        // Initialize the database
        abilityRepository.saveAndFlush(ability);

        int databaseSizeBeforeDelete = abilityRepository.findAll().size();

        // Delete the ability
        restAbilityMockMvc.perform(delete("/api/abilities/{id}", ability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ability> abilityList = abilityRepository.findAll();
        assertThat(abilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ability.class);
        Ability ability1 = new Ability();
        ability1.setId(1L);
        Ability ability2 = new Ability();
        ability2.setId(ability1.getId());
        assertThat(ability1).isEqualTo(ability2);
        ability2.setId(2L);
        assertThat(ability1).isNotEqualTo(ability2);
        ability1.setId(null);
        assertThat(ability1).isNotEqualTo(ability2);
    }
}
