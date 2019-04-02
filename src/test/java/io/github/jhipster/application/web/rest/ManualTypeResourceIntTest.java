package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.ManualType;
import io.github.jhipster.application.repository.ManualTypeRepository;
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

/**
 * Test class for the ManualTypeResource REST controller.
 *
 * @see ManualTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ManualTypeResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private ManualTypeRepository manualTypeRepository;

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

    private MockMvc restManualTypeMockMvc;

    private ManualType manualType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ManualTypeResource manualTypeResource = new ManualTypeResource(manualTypeRepository);
        this.restManualTypeMockMvc = MockMvcBuilders.standaloneSetup(manualTypeResource)
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
    public static ManualType createEntity(EntityManager em) {
        ManualType manualType = new ManualType()
            .value(DEFAULT_VALUE)
            .enabled(DEFAULT_ENABLED);
        return manualType;
    }

    @Before
    public void initTest() {
        manualType = createEntity(em);
    }

    @Test
    @Transactional
    public void createManualType() throws Exception {
        int databaseSizeBeforeCreate = manualTypeRepository.findAll().size();

        // Create the ManualType
        restManualTypeMockMvc.perform(post("/api/manual-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manualType)))
            .andExpect(status().isCreated());

        // Validate the ManualType in the database
        List<ManualType> manualTypeList = manualTypeRepository.findAll();
        assertThat(manualTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ManualType testManualType = manualTypeList.get(manualTypeList.size() - 1);
        assertThat(testManualType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testManualType.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createManualTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manualTypeRepository.findAll().size();

        // Create the ManualType with an existing ID
        manualType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManualTypeMockMvc.perform(post("/api/manual-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manualType)))
            .andExpect(status().isBadRequest());

        // Validate the ManualType in the database
        List<ManualType> manualTypeList = manualTypeRepository.findAll();
        assertThat(manualTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllManualTypes() throws Exception {
        // Initialize the database
        manualTypeRepository.saveAndFlush(manualType);

        // Get all the manualTypeList
        restManualTypeMockMvc.perform(get("/api/manual-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manualType.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getManualType() throws Exception {
        // Initialize the database
        manualTypeRepository.saveAndFlush(manualType);

        // Get the manualType
        restManualTypeMockMvc.perform(get("/api/manual-types/{id}", manualType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manualType.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingManualType() throws Exception {
        // Get the manualType
        restManualTypeMockMvc.perform(get("/api/manual-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManualType() throws Exception {
        // Initialize the database
        manualTypeRepository.saveAndFlush(manualType);

        int databaseSizeBeforeUpdate = manualTypeRepository.findAll().size();

        // Update the manualType
        ManualType updatedManualType = manualTypeRepository.findById(manualType.getId()).get();
        // Disconnect from session so that the updates on updatedManualType are not directly saved in db
        em.detach(updatedManualType);
        updatedManualType
            .value(UPDATED_VALUE)
            .enabled(UPDATED_ENABLED);

        restManualTypeMockMvc.perform(put("/api/manual-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedManualType)))
            .andExpect(status().isOk());

        // Validate the ManualType in the database
        List<ManualType> manualTypeList = manualTypeRepository.findAll();
        assertThat(manualTypeList).hasSize(databaseSizeBeforeUpdate);
        ManualType testManualType = manualTypeList.get(manualTypeList.size() - 1);
        assertThat(testManualType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testManualType.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingManualType() throws Exception {
        int databaseSizeBeforeUpdate = manualTypeRepository.findAll().size();

        // Create the ManualType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManualTypeMockMvc.perform(put("/api/manual-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manualType)))
            .andExpect(status().isBadRequest());

        // Validate the ManualType in the database
        List<ManualType> manualTypeList = manualTypeRepository.findAll();
        assertThat(manualTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteManualType() throws Exception {
        // Initialize the database
        manualTypeRepository.saveAndFlush(manualType);

        int databaseSizeBeforeDelete = manualTypeRepository.findAll().size();

        // Delete the manualType
        restManualTypeMockMvc.perform(delete("/api/manual-types/{id}", manualType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ManualType> manualTypeList = manualTypeRepository.findAll();
        assertThat(manualTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManualType.class);
        ManualType manualType1 = new ManualType();
        manualType1.setId(1L);
        ManualType manualType2 = new ManualType();
        manualType2.setId(manualType1.getId());
        assertThat(manualType1).isEqualTo(manualType2);
        manualType2.setId(2L);
        assertThat(manualType1).isNotEqualTo(manualType2);
        manualType1.setId(null);
        assertThat(manualType1).isNotEqualTo(manualType2);
    }
}
