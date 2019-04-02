package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Workflow;
import io.github.jhipster.application.repository.WorkflowRepository;
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

import io.github.jhipster.application.domain.enumeration.WorkflowStatus;
/**
 * Test class for the WorkflowResource REST controller.
 *
 * @see WorkflowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class WorkflowResourceIntTest {

    private static final WorkflowStatus DEFAULT_STATUS = WorkflowStatus.IN_PROGRESS;
    private static final WorkflowStatus UPDATED_STATUS = WorkflowStatus.COMPLETED;

    @Autowired
    private WorkflowRepository workflowRepository;

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

    private MockMvc restWorkflowMockMvc;

    private Workflow workflow;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkflowResource workflowResource = new WorkflowResource(workflowRepository);
        this.restWorkflowMockMvc = MockMvcBuilders.standaloneSetup(workflowResource)
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
    public static Workflow createEntity(EntityManager em) {
        Workflow workflow = new Workflow()
            .status(DEFAULT_STATUS);
        return workflow;
    }

    @Before
    public void initTest() {
        workflow = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkflow() throws Exception {
        int databaseSizeBeforeCreate = workflowRepository.findAll().size();

        // Create the Workflow
        restWorkflowMockMvc.perform(post("/api/workflows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workflow)))
            .andExpect(status().isCreated());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeCreate + 1);
        Workflow testWorkflow = workflowList.get(workflowList.size() - 1);
        assertThat(testWorkflow.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWorkflowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workflowRepository.findAll().size();

        // Create the Workflow with an existing ID
        workflow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkflowMockMvc.perform(post("/api/workflows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workflow)))
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkflows() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        // Get all the workflowList
        restWorkflowMockMvc.perform(get("/api/workflows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workflow.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getWorkflow() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        // Get the workflow
        restWorkflowMockMvc.perform(get("/api/workflows/{id}", workflow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workflow.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkflow() throws Exception {
        // Get the workflow
        restWorkflowMockMvc.perform(get("/api/workflows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkflow() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();

        // Update the workflow
        Workflow updatedWorkflow = workflowRepository.findById(workflow.getId()).get();
        // Disconnect from session so that the updates on updatedWorkflow are not directly saved in db
        em.detach(updatedWorkflow);
        updatedWorkflow
            .status(UPDATED_STATUS);

        restWorkflowMockMvc.perform(put("/api/workflows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkflow)))
            .andExpect(status().isOk());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
        Workflow testWorkflow = workflowList.get(workflowList.size() - 1);
        assertThat(testWorkflow.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkflow() throws Exception {
        int databaseSizeBeforeUpdate = workflowRepository.findAll().size();

        // Create the Workflow

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowMockMvc.perform(put("/api/workflows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workflow)))
            .andExpect(status().isBadRequest());

        // Validate the Workflow in the database
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkflow() throws Exception {
        // Initialize the database
        workflowRepository.saveAndFlush(workflow);

        int databaseSizeBeforeDelete = workflowRepository.findAll().size();

        // Delete the workflow
        restWorkflowMockMvc.perform(delete("/api/workflows/{id}", workflow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Workflow> workflowList = workflowRepository.findAll();
        assertThat(workflowList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workflow.class);
        Workflow workflow1 = new Workflow();
        workflow1.setId(1L);
        Workflow workflow2 = new Workflow();
        workflow2.setId(workflow1.getId());
        assertThat(workflow1).isEqualTo(workflow2);
        workflow2.setId(2L);
        assertThat(workflow1).isNotEqualTo(workflow2);
        workflow1.setId(null);
        assertThat(workflow1).isNotEqualTo(workflow2);
    }
}
