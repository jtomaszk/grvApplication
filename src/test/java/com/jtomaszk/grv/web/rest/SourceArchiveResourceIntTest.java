package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.SourceArchive;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.repository.SourceArchiveRepository;
import com.jtomaszk.grv.service.SourceArchiveService;
import com.jtomaszk.grv.service.dto.SourceArchiveDTO;
import com.jtomaszk.grv.service.mapper.SourceArchiveMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.SourceArchiveCriteria;
import com.jtomaszk.grv.service.SourceArchiveQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.jtomaszk.grv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SourceArchiveResource REST controller.
 *
 * @see SourceArchiveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class SourceArchiveResourceIntTest {

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JSON = "AAAAAAAAAA";
    private static final String UPDATED_JSON = "BBBBBBBBBB";

    @Autowired
    private SourceArchiveRepository sourceArchiveRepository;

    @Autowired
    private SourceArchiveMapper sourceArchiveMapper;

    @Autowired
    private SourceArchiveService sourceArchiveService;

    @Autowired
    private SourceArchiveQueryService sourceArchiveQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceArchiveMockMvc;

    private SourceArchive sourceArchive;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceArchiveResource sourceArchiveResource = new SourceArchiveResource(sourceArchiveService, sourceArchiveQueryService);
        this.restSourceArchiveMockMvc = MockMvcBuilders.standaloneSetup(sourceArchiveResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceArchive createEntity(EntityManager em) {
        SourceArchive sourceArchive = new SourceArchive()
            .createdDate(DEFAULT_CREATED_DATE)
            .json(DEFAULT_JSON);
        // Add required entity
        Source source = SourceResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        sourceArchive.setSource(source);
        return sourceArchive;
    }

    @Before
    public void initTest() {
        sourceArchive = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourceArchive() throws Exception {
        int databaseSizeBeforeCreate = sourceArchiveRepository.findAll().size();

        // Create the SourceArchive
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveMapper.toDto(sourceArchive);
        restSourceArchiveMockMvc.perform(post("/api/source-archives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceArchiveDTO)))
            .andExpect(status().isCreated());

        // Validate the SourceArchive in the database
        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeCreate + 1);
        SourceArchive testSourceArchive = sourceArchiveList.get(sourceArchiveList.size() - 1);
        assertThat(testSourceArchive.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSourceArchive.getJson()).isEqualTo(DEFAULT_JSON);
    }

    @Test
    @Transactional
    public void createSourceArchiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceArchiveRepository.findAll().size();

        // Create the SourceArchive with an existing ID
        sourceArchive.setId(1L);
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveMapper.toDto(sourceArchive);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceArchiveMockMvc.perform(post("/api/source-archives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceArchiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SourceArchive in the database
        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceArchiveRepository.findAll().size();
        // set the field null
        sourceArchive.setCreatedDate(null);

        // Create the SourceArchive, which fails.
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveMapper.toDto(sourceArchive);

        restSourceArchiveMockMvc.perform(post("/api/source-archives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceArchiveDTO)))
            .andExpect(status().isBadRequest());

        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJsonIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceArchiveRepository.findAll().size();
        // set the field null
        sourceArchive.setJson(null);

        // Create the SourceArchive, which fails.
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveMapper.toDto(sourceArchive);

        restSourceArchiveMockMvc.perform(post("/api/source-archives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceArchiveDTO)))
            .andExpect(status().isBadRequest());

        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSourceArchives() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);

        // Get all the sourceArchiveList
        restSourceArchiveMockMvc.perform(get("/api/source-archives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceArchive.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON.toString())));
    }

    @Test
    @Transactional
    public void getSourceArchive() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);

        // Get the sourceArchive
        restSourceArchiveMockMvc.perform(get("/api/source-archives/{id}", sourceArchive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourceArchive.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.json").value(DEFAULT_JSON.toString()));
    }

    @Test
    @Transactional
    public void getAllSourceArchivesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);

        // Get all the sourceArchiveList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSourceArchiveShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the sourceArchiveList where createdDate equals to UPDATED_CREATED_DATE
        defaultSourceArchiveShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSourceArchivesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);

        // Get all the sourceArchiveList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSourceArchiveShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the sourceArchiveList where createdDate equals to UPDATED_CREATED_DATE
        defaultSourceArchiveShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSourceArchivesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);

        // Get all the sourceArchiveList where createdDate is not null
        defaultSourceArchiveShouldBeFound("createdDate.specified=true");

        // Get all the sourceArchiveList where createdDate is null
        defaultSourceArchiveShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourceArchivesBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        Source source = SourceResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        sourceArchive.setSource(source);
        sourceArchiveRepository.saveAndFlush(sourceArchive);
        Long sourceId = source.getId();

        // Get all the sourceArchiveList where source equals to sourceId
        defaultSourceArchiveShouldBeFound("sourceId.equals=" + sourceId);

        // Get all the sourceArchiveList where source equals to sourceId + 1
        defaultSourceArchiveShouldNotBeFound("sourceId.equals=" + (sourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceArchiveShouldBeFound(String filter) throws Exception {
        restSourceArchiveMockMvc.perform(get("/api/source-archives?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceArchive.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceArchiveShouldNotBeFound(String filter) throws Exception {
        restSourceArchiveMockMvc.perform(get("/api/source-archives?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSourceArchive() throws Exception {
        // Get the sourceArchive
        restSourceArchiveMockMvc.perform(get("/api/source-archives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourceArchive() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);
        int databaseSizeBeforeUpdate = sourceArchiveRepository.findAll().size();

        // Update the sourceArchive
        SourceArchive updatedSourceArchive = sourceArchiveRepository.findOne(sourceArchive.getId());
        // Disconnect from session so that the updates on updatedSourceArchive are not directly saved in db
        em.detach(updatedSourceArchive);
        updatedSourceArchive
            .createdDate(UPDATED_CREATED_DATE)
            .json(UPDATED_JSON);
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveMapper.toDto(updatedSourceArchive);

        restSourceArchiveMockMvc.perform(put("/api/source-archives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceArchiveDTO)))
            .andExpect(status().isOk());

        // Validate the SourceArchive in the database
        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeUpdate);
        SourceArchive testSourceArchive = sourceArchiveList.get(sourceArchiveList.size() - 1);
        assertThat(testSourceArchive.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSourceArchive.getJson()).isEqualTo(UPDATED_JSON);
    }

    @Test
    @Transactional
    public void updateNonExistingSourceArchive() throws Exception {
        int databaseSizeBeforeUpdate = sourceArchiveRepository.findAll().size();

        // Create the SourceArchive
        SourceArchiveDTO sourceArchiveDTO = sourceArchiveMapper.toDto(sourceArchive);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSourceArchiveMockMvc.perform(put("/api/source-archives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceArchiveDTO)))
            .andExpect(status().isCreated());

        // Validate the SourceArchive in the database
        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSourceArchive() throws Exception {
        // Initialize the database
        sourceArchiveRepository.saveAndFlush(sourceArchive);
        int databaseSizeBeforeDelete = sourceArchiveRepository.findAll().size();

        // Get the sourceArchive
        restSourceArchiveMockMvc.perform(delete("/api/source-archives/{id}", sourceArchive.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SourceArchive> sourceArchiveList = sourceArchiveRepository.findAll();
        assertThat(sourceArchiveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceArchive.class);
        SourceArchive sourceArchive1 = new SourceArchive();
        sourceArchive1.setId(1L);
        SourceArchive sourceArchive2 = new SourceArchive();
        sourceArchive2.setId(sourceArchive1.getId());
        assertThat(sourceArchive1).isEqualTo(sourceArchive2);
        sourceArchive2.setId(2L);
        assertThat(sourceArchive1).isNotEqualTo(sourceArchive2);
        sourceArchive1.setId(null);
        assertThat(sourceArchive1).isNotEqualTo(sourceArchive2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceArchiveDTO.class);
        SourceArchiveDTO sourceArchiveDTO1 = new SourceArchiveDTO();
        sourceArchiveDTO1.setId(1L);
        SourceArchiveDTO sourceArchiveDTO2 = new SourceArchiveDTO();
        assertThat(sourceArchiveDTO1).isNotEqualTo(sourceArchiveDTO2);
        sourceArchiveDTO2.setId(sourceArchiveDTO1.getId());
        assertThat(sourceArchiveDTO1).isEqualTo(sourceArchiveDTO2);
        sourceArchiveDTO2.setId(2L);
        assertThat(sourceArchiveDTO1).isNotEqualTo(sourceArchiveDTO2);
        sourceArchiveDTO1.setId(null);
        assertThat(sourceArchiveDTO1).isNotEqualTo(sourceArchiveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sourceArchiveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sourceArchiveMapper.fromId(null)).isNull();
    }
}
