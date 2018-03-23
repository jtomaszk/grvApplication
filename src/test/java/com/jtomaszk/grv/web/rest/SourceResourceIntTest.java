package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.domain.Area;
import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.domain.ParseError;
import com.jtomaszk.grv.domain.SourceArchive;
import com.jtomaszk.grv.domain.Location;
import com.jtomaszk.grv.repository.SourceRepository;
import com.jtomaszk.grv.service.SourceService;
import com.jtomaszk.grv.repository.search.SourceSearchRepository;
import com.jtomaszk.grv.service.dto.SourceDTO;
import com.jtomaszk.grv.service.mapper.SourceMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.SourceCriteria;
import com.jtomaszk.grv.service.SourceQueryService;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.jtomaszk.grv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jtomaszk.grv.domain.enumeration.SourceStatusEnum;
/**
 * Test class for the SourceResource REST controller.
 *
 * @see SourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class SourceResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final SourceStatusEnum DEFAULT_STATUS = SourceStatusEnum.OK;
    private static final SourceStatusEnum UPDATED_STATUS = SourceStatusEnum.ERROR;

    private static final Instant DEFAULT_LAST_RUN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_RUN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourceMapper sourceMapper;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private SourceSearchRepository sourceSearchRepository;

    @Autowired
    private SourceQueryService sourceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceMockMvc;

    private Source source;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceResource sourceResource = new SourceResource(sourceService, sourceQueryService);
        this.restSourceMockMvc = MockMvcBuilders.standaloneSetup(sourceResource)
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
    public static Source createEntity(EntityManager em) {
        Source source = new Source()
            .title(DEFAULT_TITLE)
            .url(DEFAULT_URL)
            .status(DEFAULT_STATUS)
            .lastRunDate(DEFAULT_LAST_RUN_DATE)
            .info(DEFAULT_INFO);
        // Add required entity
        Area area = AreaResourceIntTest.createEntity(em);
        em.persist(area);
        em.flush();
        source.setArea(area);
        // Add required entity
        InputPattern pattern = InputPatternResourceIntTest.createEntity(em);
        em.persist(pattern);
        em.flush();
        source.setPattern(pattern);
        return source;
    }

    @Before
    public void initTest() {
        sourceSearchRepository.deleteAll();
        source = createEntity(em);
    }

    @Test
    @Transactional
    public void createSource() throws Exception {
        int databaseSizeBeforeCreate = sourceRepository.findAll().size();

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);
        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDTO)))
            .andExpect(status().isCreated());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeCreate + 1);
        Source testSource = sourceList.get(sourceList.size() - 1);
        assertThat(testSource.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSource.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSource.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSource.getLastRunDate()).isEqualTo(DEFAULT_LAST_RUN_DATE);
        assertThat(testSource.getInfo()).isEqualTo(DEFAULT_INFO);

        // Validate the Source in Elasticsearch
        Source sourceEs = sourceSearchRepository.findOne(testSource.getId());
        assertThat(sourceEs).isEqualToIgnoringGivenFields(testSource);
    }

    @Test
    @Transactional
    public void createSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceRepository.findAll().size();

        // Create the Source with an existing ID
        source.setId(1L);
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setTitle(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setUrl(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSources() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList
        restSourceMockMvc.perform(get("/api/sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(source.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastRunDate").value(hasItem(DEFAULT_LAST_RUN_DATE.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }

    @Test
    @Transactional
    public void getSource() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get the source
        restSourceMockMvc.perform(get("/api/sources/{id}", source.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(source.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastRunDate").value(DEFAULT_LAST_RUN_DATE.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()));
    }

    @Test
    @Transactional
    public void getAllSourcesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where title equals to DEFAULT_TITLE
        defaultSourceShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the sourceList where title equals to UPDATED_TITLE
        defaultSourceShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSourcesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSourceShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the sourceList where title equals to UPDATED_TITLE
        defaultSourceShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSourcesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where title is not null
        defaultSourceShouldBeFound("title.specified=true");

        // Get all the sourceList where title is null
        defaultSourceShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where url equals to DEFAULT_URL
        defaultSourceShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the sourceList where url equals to UPDATED_URL
        defaultSourceShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSourcesByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where url in DEFAULT_URL or UPDATED_URL
        defaultSourceShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the sourceList where url equals to UPDATED_URL
        defaultSourceShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSourcesByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where url is not null
        defaultSourceShouldBeFound("url.specified=true");

        // Get all the sourceList where url is null
        defaultSourceShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where status equals to DEFAULT_STATUS
        defaultSourceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the sourceList where status equals to UPDATED_STATUS
        defaultSourceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSourcesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSourceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the sourceList where status equals to UPDATED_STATUS
        defaultSourceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSourcesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where status is not null
        defaultSourceShouldBeFound("status.specified=true");

        // Get all the sourceList where status is null
        defaultSourceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByLastRunDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where lastRunDate equals to DEFAULT_LAST_RUN_DATE
        defaultSourceShouldBeFound("lastRunDate.equals=" + DEFAULT_LAST_RUN_DATE);

        // Get all the sourceList where lastRunDate equals to UPDATED_LAST_RUN_DATE
        defaultSourceShouldNotBeFound("lastRunDate.equals=" + UPDATED_LAST_RUN_DATE);
    }

    @Test
    @Transactional
    public void getAllSourcesByLastRunDateIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where lastRunDate in DEFAULT_LAST_RUN_DATE or UPDATED_LAST_RUN_DATE
        defaultSourceShouldBeFound("lastRunDate.in=" + DEFAULT_LAST_RUN_DATE + "," + UPDATED_LAST_RUN_DATE);

        // Get all the sourceList where lastRunDate equals to UPDATED_LAST_RUN_DATE
        defaultSourceShouldNotBeFound("lastRunDate.in=" + UPDATED_LAST_RUN_DATE);
    }

    @Test
    @Transactional
    public void getAllSourcesByLastRunDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where lastRunDate is not null
        defaultSourceShouldBeFound("lastRunDate.specified=true");

        // Get all the sourceList where lastRunDate is null
        defaultSourceShouldNotBeFound("lastRunDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where info equals to DEFAULT_INFO
        defaultSourceShouldBeFound("info.equals=" + DEFAULT_INFO);

        // Get all the sourceList where info equals to UPDATED_INFO
        defaultSourceShouldNotBeFound("info.equals=" + UPDATED_INFO);
    }

    @Test
    @Transactional
    public void getAllSourcesByInfoIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where info in DEFAULT_INFO or UPDATED_INFO
        defaultSourceShouldBeFound("info.in=" + DEFAULT_INFO + "," + UPDATED_INFO);

        // Get all the sourceList where info equals to UPDATED_INFO
        defaultSourceShouldNotBeFound("info.in=" + UPDATED_INFO);
    }

    @Test
    @Transactional
    public void getAllSourcesByInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where info is not null
        defaultSourceShouldBeFound("info.specified=true");

        // Get all the sourceList where info is null
        defaultSourceShouldNotBeFound("info.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        Area area = AreaResourceIntTest.createEntity(em);
        em.persist(area);
        em.flush();
        source.setArea(area);
        sourceRepository.saveAndFlush(source);
        Long areaId = area.getId();

        // Get all the sourceList where area equals to areaId
        defaultSourceShouldBeFound("areaId.equals=" + areaId);

        // Get all the sourceList where area equals to areaId + 1
        defaultSourceShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllSourcesByPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        InputPattern pattern = InputPatternResourceIntTest.createEntity(em);
        em.persist(pattern);
        em.flush();
        source.setPattern(pattern);
        sourceRepository.saveAndFlush(source);
        Long patternId = pattern.getId();

        // Get all the sourceList where pattern equals to patternId
        defaultSourceShouldBeFound("patternId.equals=" + patternId);

        // Get all the sourceList where pattern equals to patternId + 1
        defaultSourceShouldNotBeFound("patternId.equals=" + (patternId + 1));
    }


    @Test
    @Transactional
    public void getAllSourcesByErrorsIsEqualToSomething() throws Exception {
        // Initialize the database
        ParseError errors = ParseErrorResourceIntTest.createEntity(em);
        em.persist(errors);
        em.flush();
        source.addErrors(errors);
        sourceRepository.saveAndFlush(source);
        Long errorsId = errors.getId();

        // Get all the sourceList where errors equals to errorsId
        defaultSourceShouldBeFound("errorsId.equals=" + errorsId);

        // Get all the sourceList where errors equals to errorsId + 1
        defaultSourceShouldNotBeFound("errorsId.equals=" + (errorsId + 1));
    }


    @Test
    @Transactional
    public void getAllSourcesByArchivesIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceArchive archives = SourceArchiveResourceIntTest.createEntity(em);
        em.persist(archives);
        em.flush();
        source.addArchives(archives);
        sourceRepository.saveAndFlush(source);
        Long archivesId = archives.getId();

        // Get all the sourceList where archives equals to archivesId
        defaultSourceShouldBeFound("archivesId.equals=" + archivesId);

        // Get all the sourceList where archives equals to archivesId + 1
        defaultSourceShouldNotBeFound("archivesId.equals=" + (archivesId + 1));
    }


    @Test
    @Transactional
    public void getAllSourcesByLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        Location locations = LocationResourceIntTest.createEntity(em);
        em.persist(locations);
        em.flush();
        source.addLocations(locations);
        sourceRepository.saveAndFlush(source);
        Long locationsId = locations.getId();

        // Get all the sourceList where locations equals to locationsId
        defaultSourceShouldBeFound("locationsId.equals=" + locationsId);

        // Get all the sourceList where locations equals to locationsId + 1
        defaultSourceShouldNotBeFound("locationsId.equals=" + (locationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceShouldBeFound(String filter) throws Exception {
        restSourceMockMvc.perform(get("/api/sources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(source.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastRunDate").value(hasItem(DEFAULT_LAST_RUN_DATE.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceShouldNotBeFound(String filter) throws Exception {
        restSourceMockMvc.perform(get("/api/sources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSource() throws Exception {
        // Get the source
        restSourceMockMvc.perform(get("/api/sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSource() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);
        sourceSearchRepository.save(source);
        int databaseSizeBeforeUpdate = sourceRepository.findAll().size();

        // Update the source
        Source updatedSource = sourceRepository.findOne(source.getId());
        // Disconnect from session so that the updates on updatedSource are not directly saved in db
        em.detach(updatedSource);
        updatedSource
            .title(UPDATED_TITLE)
            .url(UPDATED_URL)
            .status(UPDATED_STATUS)
            .lastRunDate(UPDATED_LAST_RUN_DATE)
            .info(UPDATED_INFO);
        SourceDTO sourceDTO = sourceMapper.toDto(updatedSource);

        restSourceMockMvc.perform(put("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDTO)))
            .andExpect(status().isOk());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeUpdate);
        Source testSource = sourceList.get(sourceList.size() - 1);
        assertThat(testSource.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSource.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSource.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSource.getLastRunDate()).isEqualTo(UPDATED_LAST_RUN_DATE);
        assertThat(testSource.getInfo()).isEqualTo(UPDATED_INFO);

        // Validate the Source in Elasticsearch
        Source sourceEs = sourceSearchRepository.findOne(testSource.getId());
        assertThat(sourceEs).isEqualToIgnoringGivenFields(testSource);
    }

    @Test
    @Transactional
    public void updateNonExistingSource() throws Exception {
        int databaseSizeBeforeUpdate = sourceRepository.findAll().size();

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSourceMockMvc.perform(put("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourceDTO)))
            .andExpect(status().isCreated());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSource() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);
        sourceSearchRepository.save(source);
        int databaseSizeBeforeDelete = sourceRepository.findAll().size();

        // Get the source
        restSourceMockMvc.perform(delete("/api/sources/{id}", source.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sourceExistsInEs = sourceSearchRepository.exists(source.getId());
        assertThat(sourceExistsInEs).isFalse();

        // Validate the database is empty
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSource() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);
        sourceSearchRepository.save(source);

        // Search the source
        restSourceMockMvc.perform(get("/api/_search/sources?query=id:" + source.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(source.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastRunDate").value(hasItem(DEFAULT_LAST_RUN_DATE.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Source.class);
        Source source1 = new Source();
        source1.setId(1L);
        Source source2 = new Source();
        source2.setId(source1.getId());
        assertThat(source1).isEqualTo(source2);
        source2.setId(2L);
        assertThat(source1).isNotEqualTo(source2);
        source1.setId(null);
        assertThat(source1).isNotEqualTo(source2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SourceDTO.class);
        SourceDTO sourceDTO1 = new SourceDTO();
        sourceDTO1.setId(1L);
        SourceDTO sourceDTO2 = new SourceDTO();
        assertThat(sourceDTO1).isNotEqualTo(sourceDTO2);
        sourceDTO2.setId(sourceDTO1.getId());
        assertThat(sourceDTO1).isEqualTo(sourceDTO2);
        sourceDTO2.setId(2L);
        assertThat(sourceDTO1).isNotEqualTo(sourceDTO2);
        sourceDTO1.setId(null);
        assertThat(sourceDTO1).isNotEqualTo(sourceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sourceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sourceMapper.fromId(null)).isNull();
    }
}
