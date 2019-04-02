package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.VersionSummary;
import io.github.jhipster.application.repository.VersionSummaryRepository;
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
 * Test class for the VersionSummaryResource REST controller.
 *
 * @see VersionSummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class VersionSummaryResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private VersionSummaryRepository versionSummaryRepository;

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

    private MockMvc restVersionSummaryMockMvc;

    private VersionSummary versionSummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VersionSummaryResource versionSummaryResource = new VersionSummaryResource(versionSummaryRepository);
        this.restVersionSummaryMockMvc = MockMvcBuilders.standaloneSetup(versionSummaryResource)
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
    public static VersionSummary createEntity(EntityManager em) {
        VersionSummary versionSummary = new VersionSummary()
            .version(DEFAULT_VERSION);
        return versionSummary;
    }

    @Before
    public void initTest() {
        versionSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersionSummary() throws Exception {
        int databaseSizeBeforeCreate = versionSummaryRepository.findAll().size();

        // Create the VersionSummary
        restVersionSummaryMockMvc.perform(post("/api/version-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionSummary)))
            .andExpect(status().isCreated());

        // Validate the VersionSummary in the database
        List<VersionSummary> versionSummaryList = versionSummaryRepository.findAll();
        assertThat(versionSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        VersionSummary testVersionSummary = versionSummaryList.get(versionSummaryList.size() - 1);
        assertThat(testVersionSummary.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createVersionSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionSummaryRepository.findAll().size();

        // Create the VersionSummary with an existing ID
        versionSummary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionSummaryMockMvc.perform(post("/api/version-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionSummary)))
            .andExpect(status().isBadRequest());

        // Validate the VersionSummary in the database
        List<VersionSummary> versionSummaryList = versionSummaryRepository.findAll();
        assertThat(versionSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVersionSummaries() throws Exception {
        // Initialize the database
        versionSummaryRepository.saveAndFlush(versionSummary);

        // Get all the versionSummaryList
        restVersionSummaryMockMvc.perform(get("/api/version-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getVersionSummary() throws Exception {
        // Initialize the database
        versionSummaryRepository.saveAndFlush(versionSummary);

        // Get the versionSummary
        restVersionSummaryMockMvc.perform(get("/api/version-summaries/{id}", versionSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(versionSummary.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVersionSummary() throws Exception {
        // Get the versionSummary
        restVersionSummaryMockMvc.perform(get("/api/version-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersionSummary() throws Exception {
        // Initialize the database
        versionSummaryRepository.saveAndFlush(versionSummary);

        int databaseSizeBeforeUpdate = versionSummaryRepository.findAll().size();

        // Update the versionSummary
        VersionSummary updatedVersionSummary = versionSummaryRepository.findById(versionSummary.getId()).get();
        // Disconnect from session so that the updates on updatedVersionSummary are not directly saved in db
        em.detach(updatedVersionSummary);
        updatedVersionSummary
            .version(UPDATED_VERSION);

        restVersionSummaryMockMvc.perform(put("/api/version-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersionSummary)))
            .andExpect(status().isOk());

        // Validate the VersionSummary in the database
        List<VersionSummary> versionSummaryList = versionSummaryRepository.findAll();
        assertThat(versionSummaryList).hasSize(databaseSizeBeforeUpdate);
        VersionSummary testVersionSummary = versionSummaryList.get(versionSummaryList.size() - 1);
        assertThat(testVersionSummary.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingVersionSummary() throws Exception {
        int databaseSizeBeforeUpdate = versionSummaryRepository.findAll().size();

        // Create the VersionSummary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionSummaryMockMvc.perform(put("/api/version-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionSummary)))
            .andExpect(status().isBadRequest());

        // Validate the VersionSummary in the database
        List<VersionSummary> versionSummaryList = versionSummaryRepository.findAll();
        assertThat(versionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersionSummary() throws Exception {
        // Initialize the database
        versionSummaryRepository.saveAndFlush(versionSummary);

        int databaseSizeBeforeDelete = versionSummaryRepository.findAll().size();

        // Delete the versionSummary
        restVersionSummaryMockMvc.perform(delete("/api/version-summaries/{id}", versionSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VersionSummary> versionSummaryList = versionSummaryRepository.findAll();
        assertThat(versionSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionSummary.class);
        VersionSummary versionSummary1 = new VersionSummary();
        versionSummary1.setId(1L);
        VersionSummary versionSummary2 = new VersionSummary();
        versionSummary2.setId(versionSummary1.getId());
        assertThat(versionSummary1).isEqualTo(versionSummary2);
        versionSummary2.setId(2L);
        assertThat(versionSummary1).isNotEqualTo(versionSummary2);
        versionSummary1.setId(null);
        assertThat(versionSummary1).isNotEqualTo(versionSummary2);
    }
}
