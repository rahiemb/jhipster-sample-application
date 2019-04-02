package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.DocumentTemplate;
import io.github.jhipster.application.repository.DocumentTemplateRepository;
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
 * Test class for the DocumentTemplateResource REST controller.
 *
 * @see DocumentTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DocumentTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_FOOTER = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_FOOTER = "BBBBBBBBBB";

    private static final String DEFAULT_APPENDIX_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_APPENDIX_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_APPENDIX_FOOTER = "AAAAAAAAAA";
    private static final String UPDATED_APPENDIX_FOOTER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Autowired
    private DocumentTemplateRepository documentTemplateRepository;

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

    private MockMvc restDocumentTemplateMockMvc;

    private DocumentTemplate documentTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentTemplateResource documentTemplateResource = new DocumentTemplateResource(documentTemplateRepository);
        this.restDocumentTemplateMockMvc = MockMvcBuilders.standaloneSetup(documentTemplateResource)
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
    public static DocumentTemplate createEntity(EntityManager em) {
        DocumentTemplate documentTemplate = new DocumentTemplate()
            .name(DEFAULT_NAME)
            .primaryHeader(DEFAULT_PRIMARY_HEADER)
            .primaryFooter(DEFAULT_PRIMARY_FOOTER)
            .appendixHeader(DEFAULT_APPENDIX_HEADER)
            .appendixFooter(DEFAULT_APPENDIX_FOOTER)
            .enabled(DEFAULT_ENABLED);
        return documentTemplate;
    }

    @Before
    public void initTest() {
        documentTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTemplate() throws Exception {
        int databaseSizeBeforeCreate = documentTemplateRepository.findAll().size();

        // Create the DocumentTemplate
        restDocumentTemplateMockMvc.perform(post("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplate)))
            .andExpect(status().isCreated());

        // Validate the DocumentTemplate in the database
        List<DocumentTemplate> documentTemplateList = documentTemplateRepository.findAll();
        assertThat(documentTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTemplate testDocumentTemplate = documentTemplateList.get(documentTemplateList.size() - 1);
        assertThat(testDocumentTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentTemplate.getPrimaryHeader()).isEqualTo(DEFAULT_PRIMARY_HEADER);
        assertThat(testDocumentTemplate.getPrimaryFooter()).isEqualTo(DEFAULT_PRIMARY_FOOTER);
        assertThat(testDocumentTemplate.getAppendixHeader()).isEqualTo(DEFAULT_APPENDIX_HEADER);
        assertThat(testDocumentTemplate.getAppendixFooter()).isEqualTo(DEFAULT_APPENDIX_FOOTER);
        assertThat(testDocumentTemplate.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createDocumentTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTemplateRepository.findAll().size();

        // Create the DocumentTemplate with an existing ID
        documentTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTemplateMockMvc.perform(post("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTemplate in the database
        List<DocumentTemplate> documentTemplateList = documentTemplateRepository.findAll();
        assertThat(documentTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocumentTemplates() throws Exception {
        // Initialize the database
        documentTemplateRepository.saveAndFlush(documentTemplate);

        // Get all the documentTemplateList
        restDocumentTemplateMockMvc.perform(get("/api/document-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].primaryHeader").value(hasItem(DEFAULT_PRIMARY_HEADER.toString())))
            .andExpect(jsonPath("$.[*].primaryFooter").value(hasItem(DEFAULT_PRIMARY_FOOTER.toString())))
            .andExpect(jsonPath("$.[*].appendixHeader").value(hasItem(DEFAULT_APPENDIX_HEADER.toString())))
            .andExpect(jsonPath("$.[*].appendixFooter").value(hasItem(DEFAULT_APPENDIX_FOOTER.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDocumentTemplate() throws Exception {
        // Initialize the database
        documentTemplateRepository.saveAndFlush(documentTemplate);

        // Get the documentTemplate
        restDocumentTemplateMockMvc.perform(get("/api/document-templates/{id}", documentTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.primaryHeader").value(DEFAULT_PRIMARY_HEADER.toString()))
            .andExpect(jsonPath("$.primaryFooter").value(DEFAULT_PRIMARY_FOOTER.toString()))
            .andExpect(jsonPath("$.appendixHeader").value(DEFAULT_APPENDIX_HEADER.toString()))
            .andExpect(jsonPath("$.appendixFooter").value(DEFAULT_APPENDIX_FOOTER.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentTemplate() throws Exception {
        // Get the documentTemplate
        restDocumentTemplateMockMvc.perform(get("/api/document-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTemplate() throws Exception {
        // Initialize the database
        documentTemplateRepository.saveAndFlush(documentTemplate);

        int databaseSizeBeforeUpdate = documentTemplateRepository.findAll().size();

        // Update the documentTemplate
        DocumentTemplate updatedDocumentTemplate = documentTemplateRepository.findById(documentTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTemplate are not directly saved in db
        em.detach(updatedDocumentTemplate);
        updatedDocumentTemplate
            .name(UPDATED_NAME)
            .primaryHeader(UPDATED_PRIMARY_HEADER)
            .primaryFooter(UPDATED_PRIMARY_FOOTER)
            .appendixHeader(UPDATED_APPENDIX_HEADER)
            .appendixFooter(UPDATED_APPENDIX_FOOTER)
            .enabled(UPDATED_ENABLED);

        restDocumentTemplateMockMvc.perform(put("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentTemplate)))
            .andExpect(status().isOk());

        // Validate the DocumentTemplate in the database
        List<DocumentTemplate> documentTemplateList = documentTemplateRepository.findAll();
        assertThat(documentTemplateList).hasSize(databaseSizeBeforeUpdate);
        DocumentTemplate testDocumentTemplate = documentTemplateList.get(documentTemplateList.size() - 1);
        assertThat(testDocumentTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentTemplate.getPrimaryHeader()).isEqualTo(UPDATED_PRIMARY_HEADER);
        assertThat(testDocumentTemplate.getPrimaryFooter()).isEqualTo(UPDATED_PRIMARY_FOOTER);
        assertThat(testDocumentTemplate.getAppendixHeader()).isEqualTo(UPDATED_APPENDIX_HEADER);
        assertThat(testDocumentTemplate.getAppendixFooter()).isEqualTo(UPDATED_APPENDIX_FOOTER);
        assertThat(testDocumentTemplate.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTemplate() throws Exception {
        int databaseSizeBeforeUpdate = documentTemplateRepository.findAll().size();

        // Create the DocumentTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTemplateMockMvc.perform(put("/api/document-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTemplate in the database
        List<DocumentTemplate> documentTemplateList = documentTemplateRepository.findAll();
        assertThat(documentTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentTemplate() throws Exception {
        // Initialize the database
        documentTemplateRepository.saveAndFlush(documentTemplate);

        int databaseSizeBeforeDelete = documentTemplateRepository.findAll().size();

        // Delete the documentTemplate
        restDocumentTemplateMockMvc.perform(delete("/api/document-templates/{id}", documentTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentTemplate> documentTemplateList = documentTemplateRepository.findAll();
        assertThat(documentTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTemplate.class);
        DocumentTemplate documentTemplate1 = new DocumentTemplate();
        documentTemplate1.setId(1L);
        DocumentTemplate documentTemplate2 = new DocumentTemplate();
        documentTemplate2.setId(documentTemplate1.getId());
        assertThat(documentTemplate1).isEqualTo(documentTemplate2);
        documentTemplate2.setId(2L);
        assertThat(documentTemplate1).isNotEqualTo(documentTemplate2);
        documentTemplate1.setId(null);
        assertThat(documentTemplate1).isNotEqualTo(documentTemplate2);
    }
}
