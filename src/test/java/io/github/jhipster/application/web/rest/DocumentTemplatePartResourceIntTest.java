package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.DocumentTemplatePart;
import io.github.jhipster.application.repository.DocumentTemplatePartRepository;
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
 * Test class for the DocumentTemplatePartResource REST controller.
 *
 * @see DocumentTemplatePartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DocumentTemplatePartResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ_ONLY = false;
    private static final Boolean UPDATED_READ_ONLY = true;

    @Autowired
    private DocumentTemplatePartRepository documentTemplatePartRepository;

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

    private MockMvc restDocumentTemplatePartMockMvc;

    private DocumentTemplatePart documentTemplatePart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentTemplatePartResource documentTemplatePartResource = new DocumentTemplatePartResource(documentTemplatePartRepository);
        this.restDocumentTemplatePartMockMvc = MockMvcBuilders.standaloneSetup(documentTemplatePartResource)
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
    public static DocumentTemplatePart createEntity(EntityManager em) {
        DocumentTemplatePart documentTemplatePart = new DocumentTemplatePart()
            .name(DEFAULT_NAME)
            .text(DEFAULT_TEXT)
            .readOnly(DEFAULT_READ_ONLY);
        return documentTemplatePart;
    }

    @Before
    public void initTest() {
        documentTemplatePart = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTemplatePart() throws Exception {
        int databaseSizeBeforeCreate = documentTemplatePartRepository.findAll().size();

        // Create the DocumentTemplatePart
        restDocumentTemplatePartMockMvc.perform(post("/api/document-template-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplatePart)))
            .andExpect(status().isCreated());

        // Validate the DocumentTemplatePart in the database
        List<DocumentTemplatePart> documentTemplatePartList = documentTemplatePartRepository.findAll();
        assertThat(documentTemplatePartList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTemplatePart testDocumentTemplatePart = documentTemplatePartList.get(documentTemplatePartList.size() - 1);
        assertThat(testDocumentTemplatePart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentTemplatePart.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testDocumentTemplatePart.isReadOnly()).isEqualTo(DEFAULT_READ_ONLY);
    }

    @Test
    @Transactional
    public void createDocumentTemplatePartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTemplatePartRepository.findAll().size();

        // Create the DocumentTemplatePart with an existing ID
        documentTemplatePart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTemplatePartMockMvc.perform(post("/api/document-template-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplatePart)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTemplatePart in the database
        List<DocumentTemplatePart> documentTemplatePartList = documentTemplatePartRepository.findAll();
        assertThat(documentTemplatePartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocumentTemplateParts() throws Exception {
        // Initialize the database
        documentTemplatePartRepository.saveAndFlush(documentTemplatePart);

        // Get all the documentTemplatePartList
        restDocumentTemplatePartMockMvc.perform(get("/api/document-template-parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTemplatePart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].readOnly").value(hasItem(DEFAULT_READ_ONLY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDocumentTemplatePart() throws Exception {
        // Initialize the database
        documentTemplatePartRepository.saveAndFlush(documentTemplatePart);

        // Get the documentTemplatePart
        restDocumentTemplatePartMockMvc.perform(get("/api/document-template-parts/{id}", documentTemplatePart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentTemplatePart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.readOnly").value(DEFAULT_READ_ONLY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentTemplatePart() throws Exception {
        // Get the documentTemplatePart
        restDocumentTemplatePartMockMvc.perform(get("/api/document-template-parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTemplatePart() throws Exception {
        // Initialize the database
        documentTemplatePartRepository.saveAndFlush(documentTemplatePart);

        int databaseSizeBeforeUpdate = documentTemplatePartRepository.findAll().size();

        // Update the documentTemplatePart
        DocumentTemplatePart updatedDocumentTemplatePart = documentTemplatePartRepository.findById(documentTemplatePart.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTemplatePart are not directly saved in db
        em.detach(updatedDocumentTemplatePart);
        updatedDocumentTemplatePart
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT)
            .readOnly(UPDATED_READ_ONLY);

        restDocumentTemplatePartMockMvc.perform(put("/api/document-template-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentTemplatePart)))
            .andExpect(status().isOk());

        // Validate the DocumentTemplatePart in the database
        List<DocumentTemplatePart> documentTemplatePartList = documentTemplatePartRepository.findAll();
        assertThat(documentTemplatePartList).hasSize(databaseSizeBeforeUpdate);
        DocumentTemplatePart testDocumentTemplatePart = documentTemplatePartList.get(documentTemplatePartList.size() - 1);
        assertThat(testDocumentTemplatePart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentTemplatePart.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testDocumentTemplatePart.isReadOnly()).isEqualTo(UPDATED_READ_ONLY);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTemplatePart() throws Exception {
        int databaseSizeBeforeUpdate = documentTemplatePartRepository.findAll().size();

        // Create the DocumentTemplatePart

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTemplatePartMockMvc.perform(put("/api/document-template-parts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplatePart)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTemplatePart in the database
        List<DocumentTemplatePart> documentTemplatePartList = documentTemplatePartRepository.findAll();
        assertThat(documentTemplatePartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentTemplatePart() throws Exception {
        // Initialize the database
        documentTemplatePartRepository.saveAndFlush(documentTemplatePart);

        int databaseSizeBeforeDelete = documentTemplatePartRepository.findAll().size();

        // Delete the documentTemplatePart
        restDocumentTemplatePartMockMvc.perform(delete("/api/document-template-parts/{id}", documentTemplatePart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentTemplatePart> documentTemplatePartList = documentTemplatePartRepository.findAll();
        assertThat(documentTemplatePartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTemplatePart.class);
        DocumentTemplatePart documentTemplatePart1 = new DocumentTemplatePart();
        documentTemplatePart1.setId(1L);
        DocumentTemplatePart documentTemplatePart2 = new DocumentTemplatePart();
        documentTemplatePart2.setId(documentTemplatePart1.getId());
        assertThat(documentTemplatePart1).isEqualTo(documentTemplatePart2);
        documentTemplatePart2.setId(2L);
        assertThat(documentTemplatePart1).isNotEqualTo(documentTemplatePart2);
        documentTemplatePart1.setId(null);
        assertThat(documentTemplatePart1).isNotEqualTo(documentTemplatePart2);
    }
}
