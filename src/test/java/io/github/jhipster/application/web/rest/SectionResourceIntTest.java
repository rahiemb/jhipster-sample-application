package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Section;
import io.github.jhipster.application.repository.SectionRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SectionResource REST controller.
 *
 * @see SectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class SectionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEED = 1;
    private static final Integer UPDATED_SEED = 2;

    private static final String DEFAULT_MASK = "AAAAAAAAAA";
    private static final String UPDATED_MASK = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final Boolean DEFAULT_RETIRED = false;
    private static final Boolean UPDATED_RETIRED = true;

    private static final LocalDate DEFAULT_RETIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETIRED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RETIRED_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_RETIRED_NOTE = "BBBBBBBBBB";

    @Autowired
    private SectionRepository sectionRepository;

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

    private MockMvc restSectionMockMvc;

    private Section section;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectionResource sectionResource = new SectionResource(sectionRepository);
        this.restSectionMockMvc = MockMvcBuilders.standaloneSetup(sectionResource)
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
    public static Section createEntity(EntityManager em) {
        Section section = new Section()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .seed(DEFAULT_SEED)
            .mask(DEFAULT_MASK)
            .description(DEFAULT_DESCRIPTION)
            .enabled(DEFAULT_ENABLED)
            .link(DEFAULT_LINK)
            .orderId(DEFAULT_ORDER_ID)
            .retired(DEFAULT_RETIRED)
            .retiredDate(DEFAULT_RETIRED_DATE)
            .retiredNote(DEFAULT_RETIRED_NOTE);
        return section;
    }

    @Before
    public void initTest() {
        section = createEntity(em);
    }

    @Test
    @Transactional
    public void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSection.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSection.getSeed()).isEqualTo(DEFAULT_SEED);
        assertThat(testSection.getMask()).isEqualTo(DEFAULT_MASK);
        assertThat(testSection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSection.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testSection.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testSection.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testSection.isRetired()).isEqualTo(DEFAULT_RETIRED);
        assertThat(testSection.getRetiredDate()).isEqualTo(DEFAULT_RETIRED_DATE);
        assertThat(testSection.getRetiredNote()).isEqualTo(DEFAULT_RETIRED_NOTE);
    }

    @Test
    @Transactional
    public void createSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section with an existing ID
        section.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSections() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList
        restSectionMockMvc.perform(get("/api/sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].seed").value(hasItem(DEFAULT_SEED)))
            .andExpect(jsonPath("$.[*].mask").value(hasItem(DEFAULT_MASK.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].retired").value(hasItem(DEFAULT_RETIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].retiredDate").value(hasItem(DEFAULT_RETIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].retiredNote").value(hasItem(DEFAULT_RETIRED_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(section.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.seed").value(DEFAULT_SEED))
            .andExpect(jsonPath("$.mask").value(DEFAULT_MASK.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.retired").value(DEFAULT_RETIRED.booleanValue()))
            .andExpect(jsonPath("$.retiredDate").value(DEFAULT_RETIRED_DATE.toString()))
            .andExpect(jsonPath("$.retiredNote").value(DEFAULT_RETIRED_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSection() throws Exception {
        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section
        Section updatedSection = sectionRepository.findById(section.getId()).get();
        // Disconnect from session so that the updates on updatedSection are not directly saved in db
        em.detach(updatedSection);
        updatedSection
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .seed(UPDATED_SEED)
            .mask(UPDATED_MASK)
            .description(UPDATED_DESCRIPTION)
            .enabled(UPDATED_ENABLED)
            .link(UPDATED_LINK)
            .orderId(UPDATED_ORDER_ID)
            .retired(UPDATED_RETIRED)
            .retiredDate(UPDATED_RETIRED_DATE)
            .retiredNote(UPDATED_RETIRED_NOTE);

        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSection)))
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSection.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSection.getSeed()).isEqualTo(UPDATED_SEED);
        assertThat(testSection.getMask()).isEqualTo(UPDATED_MASK);
        assertThat(testSection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSection.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testSection.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testSection.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testSection.isRetired()).isEqualTo(UPDATED_RETIRED);
        assertThat(testSection.getRetiredDate()).isEqualTo(UPDATED_RETIRED_DATE);
        assertThat(testSection.getRetiredNote()).isEqualTo(UPDATED_RETIRED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Create the Section

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(section)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeDelete = sectionRepository.findAll().size();

        // Delete the section
        restSectionMockMvc.perform(delete("/api/sections/{id}", section.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Section.class);
        Section section1 = new Section();
        section1.setId(1L);
        Section section2 = new Section();
        section2.setId(section1.getId());
        assertThat(section1).isEqualTo(section2);
        section2.setId(2L);
        assertThat(section1).isNotEqualTo(section2);
        section1.setId(null);
        assertThat(section1).isNotEqualTo(section2);
    }
}
