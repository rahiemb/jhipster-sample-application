package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Manual;
import io.github.jhipster.application.repository.ManualRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ManualResource REST controller.
 *
 * @see ManualResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ManualResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TRACK_CHANGES_ENABLED = false;
    private static final Boolean UPDATED_TRACK_CHANGES_ENABLED = true;

    private static final Boolean DEFAULT_RETIRED = false;
    private static final Boolean UPDATED_RETIRED = true;

    private static final LocalDate DEFAULT_RETIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETIRED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RETIRED_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_RETIRED_NOTE = "BBBBBBBBBB";

    @Autowired
    private ManualRepository manualRepository;

    @Mock
    private ManualRepository manualRepositoryMock;

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

    private MockMvc restManualMockMvc;

    private Manual manual;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ManualResource manualResource = new ManualResource(manualRepository);
        this.restManualMockMvc = MockMvcBuilders.standaloneSetup(manualResource)
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
    public static Manual createEntity(EntityManager em) {
        Manual manual = new Manual()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .enabled(DEFAULT_ENABLED)
            .link(DEFAULT_LINK)
            .description(DEFAULT_DESCRIPTION)
            .trackChangesEnabled(DEFAULT_TRACK_CHANGES_ENABLED)
            .retired(DEFAULT_RETIRED)
            .retiredDate(DEFAULT_RETIRED_DATE)
            .retiredNote(DEFAULT_RETIRED_NOTE);
        return manual;
    }

    @Before
    public void initTest() {
        manual = createEntity(em);
    }

    @Test
    @Transactional
    public void createManual() throws Exception {
        int databaseSizeBeforeCreate = manualRepository.findAll().size();

        // Create the Manual
        restManualMockMvc.perform(post("/api/manuals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manual)))
            .andExpect(status().isCreated());

        // Validate the Manual in the database
        List<Manual> manualList = manualRepository.findAll();
        assertThat(manualList).hasSize(databaseSizeBeforeCreate + 1);
        Manual testManual = manualList.get(manualList.size() - 1);
        assertThat(testManual.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManual.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testManual.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testManual.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testManual.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testManual.isTrackChangesEnabled()).isEqualTo(DEFAULT_TRACK_CHANGES_ENABLED);
        assertThat(testManual.isRetired()).isEqualTo(DEFAULT_RETIRED);
        assertThat(testManual.getRetiredDate()).isEqualTo(DEFAULT_RETIRED_DATE);
        assertThat(testManual.getRetiredNote()).isEqualTo(DEFAULT_RETIRED_NOTE);
    }

    @Test
    @Transactional
    public void createManualWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manualRepository.findAll().size();

        // Create the Manual with an existing ID
        manual.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManualMockMvc.perform(post("/api/manuals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manual)))
            .andExpect(status().isBadRequest());

        // Validate the Manual in the database
        List<Manual> manualList = manualRepository.findAll();
        assertThat(manualList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllManuals() throws Exception {
        // Initialize the database
        manualRepository.saveAndFlush(manual);

        // Get all the manualList
        restManualMockMvc.perform(get("/api/manuals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manual.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].trackChangesEnabled").value(hasItem(DEFAULT_TRACK_CHANGES_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].retired").value(hasItem(DEFAULT_RETIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].retiredDate").value(hasItem(DEFAULT_RETIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].retiredNote").value(hasItem(DEFAULT_RETIRED_NOTE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllManualsWithEagerRelationshipsIsEnabled() throws Exception {
        ManualResource manualResource = new ManualResource(manualRepositoryMock);
        when(manualRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restManualMockMvc = MockMvcBuilders.standaloneSetup(manualResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restManualMockMvc.perform(get("/api/manuals?eagerload=true"))
        .andExpect(status().isOk());

        verify(manualRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllManualsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ManualResource manualResource = new ManualResource(manualRepositoryMock);
            when(manualRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restManualMockMvc = MockMvcBuilders.standaloneSetup(manualResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restManualMockMvc.perform(get("/api/manuals?eagerload=true"))
        .andExpect(status().isOk());

            verify(manualRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getManual() throws Exception {
        // Initialize the database
        manualRepository.saveAndFlush(manual);

        // Get the manual
        restManualMockMvc.perform(get("/api/manuals/{id}", manual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manual.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.trackChangesEnabled").value(DEFAULT_TRACK_CHANGES_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.retired").value(DEFAULT_RETIRED.booleanValue()))
            .andExpect(jsonPath("$.retiredDate").value(DEFAULT_RETIRED_DATE.toString()))
            .andExpect(jsonPath("$.retiredNote").value(DEFAULT_RETIRED_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManual() throws Exception {
        // Get the manual
        restManualMockMvc.perform(get("/api/manuals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManual() throws Exception {
        // Initialize the database
        manualRepository.saveAndFlush(manual);

        int databaseSizeBeforeUpdate = manualRepository.findAll().size();

        // Update the manual
        Manual updatedManual = manualRepository.findById(manual.getId()).get();
        // Disconnect from session so that the updates on updatedManual are not directly saved in db
        em.detach(updatedManual);
        updatedManual
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .link(UPDATED_LINK)
            .description(UPDATED_DESCRIPTION)
            .trackChangesEnabled(UPDATED_TRACK_CHANGES_ENABLED)
            .retired(UPDATED_RETIRED)
            .retiredDate(UPDATED_RETIRED_DATE)
            .retiredNote(UPDATED_RETIRED_NOTE);

        restManualMockMvc.perform(put("/api/manuals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedManual)))
            .andExpect(status().isOk());

        // Validate the Manual in the database
        List<Manual> manualList = manualRepository.findAll();
        assertThat(manualList).hasSize(databaseSizeBeforeUpdate);
        Manual testManual = manualList.get(manualList.size() - 1);
        assertThat(testManual.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManual.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testManual.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testManual.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testManual.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testManual.isTrackChangesEnabled()).isEqualTo(UPDATED_TRACK_CHANGES_ENABLED);
        assertThat(testManual.isRetired()).isEqualTo(UPDATED_RETIRED);
        assertThat(testManual.getRetiredDate()).isEqualTo(UPDATED_RETIRED_DATE);
        assertThat(testManual.getRetiredNote()).isEqualTo(UPDATED_RETIRED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingManual() throws Exception {
        int databaseSizeBeforeUpdate = manualRepository.findAll().size();

        // Create the Manual

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManualMockMvc.perform(put("/api/manuals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manual)))
            .andExpect(status().isBadRequest());

        // Validate the Manual in the database
        List<Manual> manualList = manualRepository.findAll();
        assertThat(manualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteManual() throws Exception {
        // Initialize the database
        manualRepository.saveAndFlush(manual);

        int databaseSizeBeforeDelete = manualRepository.findAll().size();

        // Delete the manual
        restManualMockMvc.perform(delete("/api/manuals/{id}", manual.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Manual> manualList = manualRepository.findAll();
        assertThat(manualList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manual.class);
        Manual manual1 = new Manual();
        manual1.setId(1L);
        Manual manual2 = new Manual();
        manual2.setId(manual1.getId());
        assertThat(manual1).isEqualTo(manual2);
        manual2.setId(2L);
        assertThat(manual1).isNotEqualTo(manual2);
        manual1.setId(null);
        assertThat(manual1).isNotEqualTo(manual2);
    }
}
