package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Lock;
import io.github.jhipster.application.repository.LockRepository;
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

import io.github.jhipster.application.domain.enumeration.LockType;
import io.github.jhipster.application.domain.enumeration.ObjectType;
/**
 * Test class for the LockResource REST controller.
 *
 * @see LockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class LockResourceIntTest {

    private static final Long DEFAULT_OBJECT_ID = 1L;
    private static final Long UPDATED_OBJECT_ID = 2L;

    private static final LockType DEFAULT_LOCK_TYPE = LockType.CHECKOUT;
    private static final LockType UPDATED_LOCK_TYPE = LockType.LOCK;

    private static final ObjectType DEFAULT_OBJECT_TYPE = ObjectType.CATEGORY;
    private static final ObjectType UPDATED_OBJECT_TYPE = ObjectType.DOCUMENT;

    @Autowired
    private LockRepository lockRepository;

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

    private MockMvc restLockMockMvc;

    private Lock lock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LockResource lockResource = new LockResource(lockRepository);
        this.restLockMockMvc = MockMvcBuilders.standaloneSetup(lockResource)
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
    public static Lock createEntity(EntityManager em) {
        Lock lock = new Lock()
            .objectId(DEFAULT_OBJECT_ID)
            .lockType(DEFAULT_LOCK_TYPE)
            .objectType(DEFAULT_OBJECT_TYPE);
        return lock;
    }

    @Before
    public void initTest() {
        lock = createEntity(em);
    }

    @Test
    @Transactional
    public void createLock() throws Exception {
        int databaseSizeBeforeCreate = lockRepository.findAll().size();

        // Create the Lock
        restLockMockMvc.perform(post("/api/locks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lock)))
            .andExpect(status().isCreated());

        // Validate the Lock in the database
        List<Lock> lockList = lockRepository.findAll();
        assertThat(lockList).hasSize(databaseSizeBeforeCreate + 1);
        Lock testLock = lockList.get(lockList.size() - 1);
        assertThat(testLock.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testLock.getLockType()).isEqualTo(DEFAULT_LOCK_TYPE);
        assertThat(testLock.getObjectType()).isEqualTo(DEFAULT_OBJECT_TYPE);
    }

    @Test
    @Transactional
    public void createLockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lockRepository.findAll().size();

        // Create the Lock with an existing ID
        lock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLockMockMvc.perform(post("/api/locks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lock)))
            .andExpect(status().isBadRequest());

        // Validate the Lock in the database
        List<Lock> lockList = lockRepository.findAll();
        assertThat(lockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocks() throws Exception {
        // Initialize the database
        lockRepository.saveAndFlush(lock);

        // Get all the lockList
        restLockMockMvc.perform(get("/api/locks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lock.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].lockType").value(hasItem(DEFAULT_LOCK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(DEFAULT_OBJECT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getLock() throws Exception {
        // Initialize the database
        lockRepository.saveAndFlush(lock);

        // Get the lock
        restLockMockMvc.perform(get("/api/locks/{id}", lock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lock.getId().intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.intValue()))
            .andExpect(jsonPath("$.lockType").value(DEFAULT_LOCK_TYPE.toString()))
            .andExpect(jsonPath("$.objectType").value(DEFAULT_OBJECT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLock() throws Exception {
        // Get the lock
        restLockMockMvc.perform(get("/api/locks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLock() throws Exception {
        // Initialize the database
        lockRepository.saveAndFlush(lock);

        int databaseSizeBeforeUpdate = lockRepository.findAll().size();

        // Update the lock
        Lock updatedLock = lockRepository.findById(lock.getId()).get();
        // Disconnect from session so that the updates on updatedLock are not directly saved in db
        em.detach(updatedLock);
        updatedLock
            .objectId(UPDATED_OBJECT_ID)
            .lockType(UPDATED_LOCK_TYPE)
            .objectType(UPDATED_OBJECT_TYPE);

        restLockMockMvc.perform(put("/api/locks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLock)))
            .andExpect(status().isOk());

        // Validate the Lock in the database
        List<Lock> lockList = lockRepository.findAll();
        assertThat(lockList).hasSize(databaseSizeBeforeUpdate);
        Lock testLock = lockList.get(lockList.size() - 1);
        assertThat(testLock.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testLock.getLockType()).isEqualTo(UPDATED_LOCK_TYPE);
        assertThat(testLock.getObjectType()).isEqualTo(UPDATED_OBJECT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLock() throws Exception {
        int databaseSizeBeforeUpdate = lockRepository.findAll().size();

        // Create the Lock

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLockMockMvc.perform(put("/api/locks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lock)))
            .andExpect(status().isBadRequest());

        // Validate the Lock in the database
        List<Lock> lockList = lockRepository.findAll();
        assertThat(lockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLock() throws Exception {
        // Initialize the database
        lockRepository.saveAndFlush(lock);

        int databaseSizeBeforeDelete = lockRepository.findAll().size();

        // Delete the lock
        restLockMockMvc.perform(delete("/api/locks/{id}", lock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lock> lockList = lockRepository.findAll();
        assertThat(lockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lock.class);
        Lock lock1 = new Lock();
        lock1.setId(1L);
        Lock lock2 = new Lock();
        lock2.setId(lock1.getId());
        assertThat(lock1).isEqualTo(lock2);
        lock2.setId(2L);
        assertThat(lock1).isNotEqualTo(lock2);
        lock1.setId(null);
        assertThat(lock1).isNotEqualTo(lock2);
    }
}
