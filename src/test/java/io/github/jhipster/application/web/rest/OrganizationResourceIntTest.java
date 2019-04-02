package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Organization;
import io.github.jhipster.application.repository.OrganizationRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class OrganizationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final String DEFAULT_SITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REPOSITORY = "AAAAAAAAAA";
    private static final String UPDATED_REPOSITORY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_THUMBNAIL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMBNAIL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_THUMBNAIL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMBNAIL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SORL_CORE = "AAAAAAAAAA";
    private static final String UPDATED_SORL_CORE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVAL_DATE_ENABLED = false;
    private static final Boolean UPDATED_APPROVAL_DATE_ENABLED = true;

    private static final Boolean DEFAULT_EFFECTIVE_DATE_ENABLED = false;
    private static final Boolean UPDATED_EFFECTIVE_DATE_ENABLED = true;

    private static final Boolean DEFAULT_ORIGINAL_DATE_ENABLED = false;
    private static final Boolean UPDATED_ORIGINAL_DATE_ENABLED = true;

    private static final Boolean DEFAULT_REVIEW_DATE_ENABLED = false;
    private static final Boolean UPDATED_REVIEW_DATE_ENABLED = true;

    private static final Boolean DEFAULT_REVISION_DATE_ENABLED = false;
    private static final Boolean UPDATED_REVISION_DATE_ENABLED = true;

    private static final Boolean DEFAULT_SUPERSEDES_DATE_ENABLED = false;
    private static final Boolean UPDATED_SUPERSEDES_DATE_ENABLED = true;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationRepository organizationRepositoryMock;

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

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationResource organizationResource = new OrganizationResource(organizationRepository);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
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
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .orderId(DEFAULT_ORDER_ID)
            .siteUrl(DEFAULT_SITE_URL)
            .repository(DEFAULT_REPOSITORY)
            .thumbnail(DEFAULT_THUMBNAIL)
            .thumbnailContentType(DEFAULT_THUMBNAIL_CONTENT_TYPE)
            .sorlCore(DEFAULT_SORL_CORE)
            .approvalDateEnabled(DEFAULT_APPROVAL_DATE_ENABLED)
            .effectiveDateEnabled(DEFAULT_EFFECTIVE_DATE_ENABLED)
            .originalDateEnabled(DEFAULT_ORIGINAL_DATE_ENABLED)
            .reviewDateEnabled(DEFAULT_REVIEW_DATE_ENABLED)
            .revisionDateEnabled(DEFAULT_REVISION_DATE_ENABLED)
            .supersedesDateEnabled(DEFAULT_SUPERSEDES_DATE_ENABLED);
        return organization;
    }

    @Before
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testOrganization.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrganization.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
        assertThat(testOrganization.getRepository()).isEqualTo(DEFAULT_REPOSITORY);
        assertThat(testOrganization.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testOrganization.getThumbnailContentType()).isEqualTo(DEFAULT_THUMBNAIL_CONTENT_TYPE);
        assertThat(testOrganization.getSorlCore()).isEqualTo(DEFAULT_SORL_CORE);
        assertThat(testOrganization.isApprovalDateEnabled()).isEqualTo(DEFAULT_APPROVAL_DATE_ENABLED);
        assertThat(testOrganization.isEffectiveDateEnabled()).isEqualTo(DEFAULT_EFFECTIVE_DATE_ENABLED);
        assertThat(testOrganization.isOriginalDateEnabled()).isEqualTo(DEFAULT_ORIGINAL_DATE_ENABLED);
        assertThat(testOrganization.isReviewDateEnabled()).isEqualTo(DEFAULT_REVIEW_DATE_ENABLED);
        assertThat(testOrganization.isRevisionDateEnabled()).isEqualTo(DEFAULT_REVISION_DATE_ENABLED);
        assertThat(testOrganization.isSupersedesDateEnabled()).isEqualTo(DEFAULT_SUPERSEDES_DATE_ENABLED);
    }

    @Test
    @Transactional
    public void createOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization with an existing ID
        organization.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL.toString())))
            .andExpect(jsonPath("$.[*].repository").value(hasItem(DEFAULT_REPOSITORY.toString())))
            .andExpect(jsonPath("$.[*].thumbnailContentType").value(hasItem(DEFAULT_THUMBNAIL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMBNAIL))))
            .andExpect(jsonPath("$.[*].sorlCore").value(hasItem(DEFAULT_SORL_CORE.toString())))
            .andExpect(jsonPath("$.[*].approvalDateEnabled").value(hasItem(DEFAULT_APPROVAL_DATE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].effectiveDateEnabled").value(hasItem(DEFAULT_EFFECTIVE_DATE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].originalDateEnabled").value(hasItem(DEFAULT_ORIGINAL_DATE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].reviewDateEnabled").value(hasItem(DEFAULT_REVIEW_DATE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].revisionDateEnabled").value(hasItem(DEFAULT_REVISION_DATE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].supersedesDateEnabled").value(hasItem(DEFAULT_SUPERSEDES_DATE_ENABLED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllOrganizationsWithEagerRelationshipsIsEnabled() throws Exception {
        OrganizationResource organizationResource = new OrganizationResource(organizationRepositoryMock);
        when(organizationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrganizationMockMvc.perform(get("/api/organizations?eagerload=true"))
        .andExpect(status().isOk());

        verify(organizationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllOrganizationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        OrganizationResource organizationResource = new OrganizationResource(organizationRepositoryMock);
            when(organizationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrganizationMockMvc.perform(get("/api/organizations?eagerload=true"))
        .andExpect(status().isOk());

            verify(organizationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.siteUrl").value(DEFAULT_SITE_URL.toString()))
            .andExpect(jsonPath("$.repository").value(DEFAULT_REPOSITORY.toString()))
            .andExpect(jsonPath("$.thumbnailContentType").value(DEFAULT_THUMBNAIL_CONTENT_TYPE))
            .andExpect(jsonPath("$.thumbnail").value(Base64Utils.encodeToString(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.sorlCore").value(DEFAULT_SORL_CORE.toString()))
            .andExpect(jsonPath("$.approvalDateEnabled").value(DEFAULT_APPROVAL_DATE_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.effectiveDateEnabled").value(DEFAULT_EFFECTIVE_DATE_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.originalDateEnabled").value(DEFAULT_ORIGINAL_DATE_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.reviewDateEnabled").value(DEFAULT_REVIEW_DATE_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.revisionDateEnabled").value(DEFAULT_REVISION_DATE_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.supersedesDateEnabled").value(DEFAULT_SUPERSEDES_DATE_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .orderId(UPDATED_ORDER_ID)
            .siteUrl(UPDATED_SITE_URL)
            .repository(UPDATED_REPOSITORY)
            .thumbnail(UPDATED_THUMBNAIL)
            .thumbnailContentType(UPDATED_THUMBNAIL_CONTENT_TYPE)
            .sorlCore(UPDATED_SORL_CORE)
            .approvalDateEnabled(UPDATED_APPROVAL_DATE_ENABLED)
            .effectiveDateEnabled(UPDATED_EFFECTIVE_DATE_ENABLED)
            .originalDateEnabled(UPDATED_ORIGINAL_DATE_ENABLED)
            .reviewDateEnabled(UPDATED_REVIEW_DATE_ENABLED)
            .revisionDateEnabled(UPDATED_REVISION_DATE_ENABLED)
            .supersedesDateEnabled(UPDATED_SUPERSEDES_DATE_ENABLED);

        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganization)))
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testOrganization.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrganization.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
        assertThat(testOrganization.getRepository()).isEqualTo(UPDATED_REPOSITORY);
        assertThat(testOrganization.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testOrganization.getThumbnailContentType()).isEqualTo(UPDATED_THUMBNAIL_CONTENT_TYPE);
        assertThat(testOrganization.getSorlCore()).isEqualTo(UPDATED_SORL_CORE);
        assertThat(testOrganization.isApprovalDateEnabled()).isEqualTo(UPDATED_APPROVAL_DATE_ENABLED);
        assertThat(testOrganization.isEffectiveDateEnabled()).isEqualTo(UPDATED_EFFECTIVE_DATE_ENABLED);
        assertThat(testOrganization.isOriginalDateEnabled()).isEqualTo(UPDATED_ORIGINAL_DATE_ENABLED);
        assertThat(testOrganization.isReviewDateEnabled()).isEqualTo(UPDATED_REVIEW_DATE_ENABLED);
        assertThat(testOrganization.isRevisionDateEnabled()).isEqualTo(UPDATED_REVISION_DATE_ENABLED);
        assertThat(testOrganization.isSupersedesDateEnabled()).isEqualTo(UPDATED_SUPERSEDES_DATE_ENABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Create the Organization

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organization)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = new Organization();
        organization1.setId(1L);
        Organization organization2 = new Organization();
        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);
        organization2.setId(2L);
        assertThat(organization1).isNotEqualTo(organization2);
        organization1.setId(null);
        assertThat(organization1).isNotEqualTo(organization2);
    }
}
