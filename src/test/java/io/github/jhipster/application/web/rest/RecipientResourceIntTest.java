package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Recipient;
import io.github.jhipster.application.repository.RecipientRepository;
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
 * Test class for the RecipientResource REST controller.
 *
 * @see RecipientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class RecipientResourceIntTest {

    private static final String DEFAULT_NOTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SENT = false;
    private static final Boolean UPDATED_SENT = true;

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RecipientRepository recipientRepository;

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

    private MockMvc restRecipientMockMvc;

    private Recipient recipient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipientResource recipientResource = new RecipientResource(recipientRepository);
        this.restRecipientMockMvc = MockMvcBuilders.standaloneSetup(recipientResource)
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
    public static Recipient createEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .notification(DEFAULT_NOTIFICATION)
            .user(DEFAULT_USER)
            .sent(DEFAULT_SENT)
            .timestamp(DEFAULT_TIMESTAMP);
        return recipient;
    }

    @Before
    public void initTest() {
        recipient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipient() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isCreated());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate + 1);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getNotification()).isEqualTo(DEFAULT_NOTIFICATION);
        assertThat(testRecipient.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testRecipient.isSent()).isEqualTo(DEFAULT_SENT);
        assertThat(testRecipient.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createRecipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient with an existing ID
        recipient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecipients() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get all the recipientList
        restRecipientMockMvc.perform(get("/api/recipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].notification").value(hasItem(DEFAULT_NOTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].sent").value(hasItem(DEFAULT_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipient.getId().intValue()))
            .andExpect(jsonPath("$.notification").value(DEFAULT_NOTIFICATION.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.sent").value(DEFAULT_SENT.booleanValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipient() throws Exception {
        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Update the recipient
        Recipient updatedRecipient = recipientRepository.findById(recipient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipient are not directly saved in db
        em.detach(updatedRecipient);
        updatedRecipient
            .notification(UPDATED_NOTIFICATION)
            .user(UPDATED_USER)
            .sent(UPDATED_SENT)
            .timestamp(UPDATED_TIMESTAMP);

        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecipient)))
            .andExpect(status().isOk());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getNotification()).isEqualTo(UPDATED_NOTIFICATION);
        assertThat(testRecipient.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testRecipient.isSent()).isEqualTo(UPDATED_SENT);
        assertThat(testRecipient.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipient() throws Exception {
        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Create the Recipient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeDelete = recipientRepository.findAll().size();

        // Delete the recipient
        restRecipientMockMvc.perform(delete("/api/recipients/{id}", recipient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipient.class);
        Recipient recipient1 = new Recipient();
        recipient1.setId(1L);
        Recipient recipient2 = new Recipient();
        recipient2.setId(recipient1.getId());
        assertThat(recipient1).isEqualTo(recipient2);
        recipient2.setId(2L);
        assertThat(recipient1).isNotEqualTo(recipient2);
        recipient1.setId(null);
        assertThat(recipient1).isNotEqualTo(recipient2);
    }
}
