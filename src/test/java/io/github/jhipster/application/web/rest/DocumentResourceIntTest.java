package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Document;
import io.github.jhipster.application.repository.DocumentRepository;
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

import io.github.jhipster.application.domain.enumeration.ExpirationType;
import io.github.jhipster.application.domain.enumeration.TimeInterval;
import io.github.jhipster.application.domain.enumeration.ExpirationBase;
/**
 * Test class for the DocumentResource REST controller.
 *
 * @see DocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DocumentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEED = 1;
    private static final Integer UPDATED_SEED = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SUPERSEDES_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUPERSEDES_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORIGINAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORIGINAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_REVISION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVISION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ExpirationType DEFAULT_EXPIRATION_TYPE = ExpirationType.DATE;
    private static final ExpirationType UPDATED_EXPIRATION_TYPE = ExpirationType.INTERVAL;

    private static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EXPIRATION_PERIOD = 1;
    private static final Integer UPDATED_EXPIRATION_PERIOD = 2;

    private static final TimeInterval DEFAULT_EXPIRATION_INTERVAL = TimeInterval.DAYS;
    private static final TimeInterval UPDATED_EXPIRATION_INTERVAL = TimeInterval.WEEKS;

    private static final ExpirationBase DEFAULT_EXPIRATION_BASE = ExpirationBase.EFFECTIVEDATE;
    private static final ExpirationBase UPDATED_EXPIRATION_BASE = ExpirationBase.APPROVALDATE;

    private static final Boolean DEFAULT_TABLE_OF_CONTENTS = false;
    private static final Boolean UPDATED_TABLE_OF_CONTENTS = true;

    private static final Boolean DEFAULT_RETIRED = false;
    private static final Boolean UPDATED_RETIRED = true;

    private static final LocalDate DEFAULT_RETIRED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETIRED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RETIRED_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_RETIRED_NOTE = "BBBBBBBBBB";

    @Autowired
    private DocumentRepository documentRepository;

    @Mock
    private DocumentRepository documentRepositoryMock;

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

    private MockMvc restDocumentMockMvc;

    private Document document;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentResource documentResource = new DocumentResource(documentRepository);
        this.restDocumentMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
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
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .seed(DEFAULT_SEED)
            .description(DEFAULT_DESCRIPTION)
            .enabled(DEFAULT_ENABLED)
            .link(DEFAULT_LINK)
            .orderId(DEFAULT_ORDER_ID)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .supersedesDate(DEFAULT_SUPERSEDES_DATE)
            .originalDate(DEFAULT_ORIGINAL_DATE)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .revisionDate(DEFAULT_REVISION_DATE)
            .expirationType(DEFAULT_EXPIRATION_TYPE)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .expirationPeriod(DEFAULT_EXPIRATION_PERIOD)
            .expirationInterval(DEFAULT_EXPIRATION_INTERVAL)
            .expirationBase(DEFAULT_EXPIRATION_BASE)
            .tableOfContents(DEFAULT_TABLE_OF_CONTENTS)
            .retired(DEFAULT_RETIRED)
            .retiredDate(DEFAULT_RETIRED_DATE)
            .retiredNote(DEFAULT_RETIRED_NOTE);
        return document;
    }

    @Before
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocument.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDocument.getSeed()).isEqualTo(DEFAULT_SEED);
        assertThat(testDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocument.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testDocument.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testDocument.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testDocument.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testDocument.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testDocument.getSupersedesDate()).isEqualTo(DEFAULT_SUPERSEDES_DATE);
        assertThat(testDocument.getOriginalDate()).isEqualTo(DEFAULT_ORIGINAL_DATE);
        assertThat(testDocument.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testDocument.getRevisionDate()).isEqualTo(DEFAULT_REVISION_DATE);
        assertThat(testDocument.getExpirationType()).isEqualTo(DEFAULT_EXPIRATION_TYPE);
        assertThat(testDocument.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testDocument.getExpirationPeriod()).isEqualTo(DEFAULT_EXPIRATION_PERIOD);
        assertThat(testDocument.getExpirationInterval()).isEqualTo(DEFAULT_EXPIRATION_INTERVAL);
        assertThat(testDocument.getExpirationBase()).isEqualTo(DEFAULT_EXPIRATION_BASE);
        assertThat(testDocument.isTableOfContents()).isEqualTo(DEFAULT_TABLE_OF_CONTENTS);
        assertThat(testDocument.isRetired()).isEqualTo(DEFAULT_RETIRED);
        assertThat(testDocument.getRetiredDate()).isEqualTo(DEFAULT_RETIRED_DATE);
        assertThat(testDocument.getRetiredNote()).isEqualTo(DEFAULT_RETIRED_NOTE);
    }

    @Test
    @Transactional
    public void createDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document with an existing ID
        document.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].seed").value(hasItem(DEFAULT_SEED)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].supersedesDate").value(hasItem(DEFAULT_SUPERSEDES_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalDate").value(hasItem(DEFAULT_ORIGINAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].revisionDate").value(hasItem(DEFAULT_REVISION_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationType").value(hasItem(DEFAULT_EXPIRATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationPeriod").value(hasItem(DEFAULT_EXPIRATION_PERIOD)))
            .andExpect(jsonPath("$.[*].expirationInterval").value(hasItem(DEFAULT_EXPIRATION_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].expirationBase").value(hasItem(DEFAULT_EXPIRATION_BASE.toString())))
            .andExpect(jsonPath("$.[*].tableOfContents").value(hasItem(DEFAULT_TABLE_OF_CONTENTS.booleanValue())))
            .andExpect(jsonPath("$.[*].retired").value(hasItem(DEFAULT_RETIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].retiredDate").value(hasItem(DEFAULT_RETIRED_DATE.toString())))
            .andExpect(jsonPath("$.[*].retiredNote").value(hasItem(DEFAULT_RETIRED_NOTE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDocumentsWithEagerRelationshipsIsEnabled() throws Exception {
        DocumentResource documentResource = new DocumentResource(documentRepositoryMock);
        when(documentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDocumentMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDocumentMockMvc.perform(get("/api/documents?eagerload=true"))
        .andExpect(status().isOk());

        verify(documentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDocumentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DocumentResource documentResource = new DocumentResource(documentRepositoryMock);
            when(documentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDocumentMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDocumentMockMvc.perform(get("/api/documents?eagerload=true"))
        .andExpect(status().isOk());

            verify(documentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.seed").value(DEFAULT_SEED))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.supersedesDate").value(DEFAULT_SUPERSEDES_DATE.toString()))
            .andExpect(jsonPath("$.originalDate").value(DEFAULT_ORIGINAL_DATE.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.revisionDate").value(DEFAULT_REVISION_DATE.toString()))
            .andExpect(jsonPath("$.expirationType").value(DEFAULT_EXPIRATION_TYPE.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.expirationPeriod").value(DEFAULT_EXPIRATION_PERIOD))
            .andExpect(jsonPath("$.expirationInterval").value(DEFAULT_EXPIRATION_INTERVAL.toString()))
            .andExpect(jsonPath("$.expirationBase").value(DEFAULT_EXPIRATION_BASE.toString()))
            .andExpect(jsonPath("$.tableOfContents").value(DEFAULT_TABLE_OF_CONTENTS.booleanValue()))
            .andExpect(jsonPath("$.retired").value(DEFAULT_RETIRED.booleanValue()))
            .andExpect(jsonPath("$.retiredDate").value(DEFAULT_RETIRED_DATE.toString()))
            .andExpect(jsonPath("$.retiredNote").value(DEFAULT_RETIRED_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .seed(UPDATED_SEED)
            .description(UPDATED_DESCRIPTION)
            .enabled(UPDATED_ENABLED)
            .link(UPDATED_LINK)
            .orderId(UPDATED_ORDER_ID)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .supersedesDate(UPDATED_SUPERSEDES_DATE)
            .originalDate(UPDATED_ORIGINAL_DATE)
            .reviewDate(UPDATED_REVIEW_DATE)
            .revisionDate(UPDATED_REVISION_DATE)
            .expirationType(UPDATED_EXPIRATION_TYPE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .expirationPeriod(UPDATED_EXPIRATION_PERIOD)
            .expirationInterval(UPDATED_EXPIRATION_INTERVAL)
            .expirationBase(UPDATED_EXPIRATION_BASE)
            .tableOfContents(UPDATED_TABLE_OF_CONTENTS)
            .retired(UPDATED_RETIRED)
            .retiredDate(UPDATED_RETIRED_DATE)
            .retiredNote(UPDATED_RETIRED_NOTE);

        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocument)))
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocument.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDocument.getSeed()).isEqualTo(UPDATED_SEED);
        assertThat(testDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocument.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testDocument.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testDocument.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testDocument.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testDocument.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testDocument.getSupersedesDate()).isEqualTo(UPDATED_SUPERSEDES_DATE);
        assertThat(testDocument.getOriginalDate()).isEqualTo(UPDATED_ORIGINAL_DATE);
        assertThat(testDocument.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testDocument.getRevisionDate()).isEqualTo(UPDATED_REVISION_DATE);
        assertThat(testDocument.getExpirationType()).isEqualTo(UPDATED_EXPIRATION_TYPE);
        assertThat(testDocument.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testDocument.getExpirationPeriod()).isEqualTo(UPDATED_EXPIRATION_PERIOD);
        assertThat(testDocument.getExpirationInterval()).isEqualTo(UPDATED_EXPIRATION_INTERVAL);
        assertThat(testDocument.getExpirationBase()).isEqualTo(UPDATED_EXPIRATION_BASE);
        assertThat(testDocument.isTableOfContents()).isEqualTo(UPDATED_TABLE_OF_CONTENTS);
        assertThat(testDocument.isRetired()).isEqualTo(UPDATED_RETIRED);
        assertThat(testDocument.getRetiredDate()).isEqualTo(UPDATED_RETIRED_DATE);
        assertThat(testDocument.getRetiredNote()).isEqualTo(UPDATED_RETIRED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Create the Document

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc.perform(delete("/api/documents/{id}", document.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = new Document();
        document1.setId(1L);
        Document document2 = new Document();
        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);
        document2.setId(2L);
        assertThat(document1).isNotEqualTo(document2);
        document1.setId(null);
        assertThat(document1).isNotEqualTo(document2);
    }
}
