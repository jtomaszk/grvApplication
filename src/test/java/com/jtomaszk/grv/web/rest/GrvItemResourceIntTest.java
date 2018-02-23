package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.domain.Location;
import com.jtomaszk.grv.domain.SourceArchive;
import com.jtomaszk.grv.domain.GrvItemPerson;
import com.jtomaszk.grv.domain.Error;
import com.jtomaszk.grv.repository.GrvItemRepository;
import com.jtomaszk.grv.service.GrvItemService;
import com.jtomaszk.grv.repository.search.GrvItemSearchRepository;
import com.jtomaszk.grv.service.dto.GrvItemDTO;
import com.jtomaszk.grv.service.mapper.GrvItemMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.GrvItemCriteria;
import com.jtomaszk.grv.service.GrvItemQueryService;

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
 * Test class for the GrvItemResource REST controller.
 *
 * @see GrvItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class GrvItemResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VALID_TO_DATE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_VALID_TO_DATE_STRING = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXTERNALID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNALID = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCNR = "AAAAAAAAAA";
    private static final String UPDATED_DOCNR = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GrvItemRepository grvItemRepository;

    @Autowired
    private GrvItemMapper grvItemMapper;

    @Autowired
    private GrvItemService grvItemService;

    @Autowired
    private GrvItemSearchRepository grvItemSearchRepository;

    @Autowired
    private GrvItemQueryService grvItemQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrvItemMockMvc;

    private GrvItem grvItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrvItemResource grvItemResource = new GrvItemResource(grvItemService, grvItemQueryService);
        this.restGrvItemMockMvc = MockMvcBuilders.standaloneSetup(grvItemResource)
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
    public static GrvItem createEntity(EntityManager em) {
        GrvItem grvItem = new GrvItem()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .validToDateString(DEFAULT_VALID_TO_DATE_STRING)
            .validToDate(DEFAULT_VALID_TO_DATE)
            .externalid(DEFAULT_EXTERNALID)
            .info(DEFAULT_INFO)
            .docnr(DEFAULT_DOCNR)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        Source source = SourceResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        grvItem.setSource(source);
        // Add required entity
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        grvItem.setLocation(location);
        return grvItem;
    }

    @Before
    public void initTest() {
        grvItemSearchRepository.deleteAll();
        grvItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrvItem() throws Exception {
        int databaseSizeBeforeCreate = grvItemRepository.findAll().size();

        // Create the GrvItem
        GrvItemDTO grvItemDTO = grvItemMapper.toDto(grvItem);
        restGrvItemMockMvc.perform(post("/api/grv-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemDTO)))
            .andExpect(status().isCreated());

        // Validate the GrvItem in the database
        List<GrvItem> grvItemList = grvItemRepository.findAll();
        assertThat(grvItemList).hasSize(databaseSizeBeforeCreate + 1);
        GrvItem testGrvItem = grvItemList.get(grvItemList.size() - 1);
        assertThat(testGrvItem.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testGrvItem.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testGrvItem.getValidToDateString()).isEqualTo(DEFAULT_VALID_TO_DATE_STRING);
        assertThat(testGrvItem.getValidToDate()).isEqualTo(DEFAULT_VALID_TO_DATE);
        assertThat(testGrvItem.getExternalid()).isEqualTo(DEFAULT_EXTERNALID);
        assertThat(testGrvItem.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testGrvItem.getDocnr()).isEqualTo(DEFAULT_DOCNR);
        assertThat(testGrvItem.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the GrvItem in Elasticsearch
        GrvItem grvItemEs = grvItemSearchRepository.findOne(testGrvItem.getId());
        assertThat(grvItemEs).isEqualToIgnoringGivenFields(testGrvItem);
    }

    @Test
    @Transactional
    public void createGrvItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grvItemRepository.findAll().size();

        // Create the GrvItem with an existing ID
        grvItem.setId(1L);
        GrvItemDTO grvItemDTO = grvItemMapper.toDto(grvItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrvItemMockMvc.perform(post("/api/grv-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrvItem in the database
        List<GrvItem> grvItemList = grvItemRepository.findAll();
        assertThat(grvItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = grvItemRepository.findAll().size();
        // set the field null
        grvItem.setCreatedDate(null);

        // Create the GrvItem, which fails.
        GrvItemDTO grvItemDTO = grvItemMapper.toDto(grvItem);

        restGrvItemMockMvc.perform(post("/api/grv-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemDTO)))
            .andExpect(status().isBadRequest());

        List<GrvItem> grvItemList = grvItemRepository.findAll();
        assertThat(grvItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrvItems() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList
        restGrvItemMockMvc.perform(get("/api/grv-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grvItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].validToDateString").value(hasItem(DEFAULT_VALID_TO_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].validToDate").value(hasItem(DEFAULT_VALID_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].externalid").value(hasItem(DEFAULT_EXTERNALID.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())))
            .andExpect(jsonPath("$.[*].docnr").value(hasItem(DEFAULT_DOCNR.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getGrvItem() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get the grvItem
        restGrvItemMockMvc.perform(get("/api/grv-items/{id}", grvItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grvItem.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.validToDateString").value(DEFAULT_VALID_TO_DATE_STRING.toString()))
            .andExpect(jsonPath("$.validToDate").value(DEFAULT_VALID_TO_DATE.toString()))
            .andExpect(jsonPath("$.externalid").value(DEFAULT_EXTERNALID.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()))
            .andExpect(jsonPath("$.docnr").value(DEFAULT_DOCNR.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllGrvItemsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where startDate equals to DEFAULT_START_DATE
        defaultGrvItemShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the grvItemList where startDate equals to UPDATED_START_DATE
        defaultGrvItemShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultGrvItemShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the grvItemList where startDate equals to UPDATED_START_DATE
        defaultGrvItemShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where startDate is not null
        defaultGrvItemShouldBeFound("startDate.specified=true");

        // Get all the grvItemList where startDate is null
        defaultGrvItemShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where endDate equals to DEFAULT_END_DATE
        defaultGrvItemShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the grvItemList where endDate equals to UPDATED_END_DATE
        defaultGrvItemShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultGrvItemShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the grvItemList where endDate equals to UPDATED_END_DATE
        defaultGrvItemShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where endDate is not null
        defaultGrvItemShouldBeFound("endDate.specified=true");

        // Get all the grvItemList where endDate is null
        defaultGrvItemShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsByValidToDateStringIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where validToDateString equals to DEFAULT_VALID_TO_DATE_STRING
        defaultGrvItemShouldBeFound("validToDateString.equals=" + DEFAULT_VALID_TO_DATE_STRING);

        // Get all the grvItemList where validToDateString equals to UPDATED_VALID_TO_DATE_STRING
        defaultGrvItemShouldNotBeFound("validToDateString.equals=" + UPDATED_VALID_TO_DATE_STRING);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByValidToDateStringIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where validToDateString in DEFAULT_VALID_TO_DATE_STRING or UPDATED_VALID_TO_DATE_STRING
        defaultGrvItemShouldBeFound("validToDateString.in=" + DEFAULT_VALID_TO_DATE_STRING + "," + UPDATED_VALID_TO_DATE_STRING);

        // Get all the grvItemList where validToDateString equals to UPDATED_VALID_TO_DATE_STRING
        defaultGrvItemShouldNotBeFound("validToDateString.in=" + UPDATED_VALID_TO_DATE_STRING);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByValidToDateStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where validToDateString is not null
        defaultGrvItemShouldBeFound("validToDateString.specified=true");

        // Get all the grvItemList where validToDateString is null
        defaultGrvItemShouldNotBeFound("validToDateString.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsByValidToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where validToDate equals to DEFAULT_VALID_TO_DATE
        defaultGrvItemShouldBeFound("validToDate.equals=" + DEFAULT_VALID_TO_DATE);

        // Get all the grvItemList where validToDate equals to UPDATED_VALID_TO_DATE
        defaultGrvItemShouldNotBeFound("validToDate.equals=" + UPDATED_VALID_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByValidToDateIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where validToDate in DEFAULT_VALID_TO_DATE or UPDATED_VALID_TO_DATE
        defaultGrvItemShouldBeFound("validToDate.in=" + DEFAULT_VALID_TO_DATE + "," + UPDATED_VALID_TO_DATE);

        // Get all the grvItemList where validToDate equals to UPDATED_VALID_TO_DATE
        defaultGrvItemShouldNotBeFound("validToDate.in=" + UPDATED_VALID_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByValidToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where validToDate is not null
        defaultGrvItemShouldBeFound("validToDate.specified=true");

        // Get all the grvItemList where validToDate is null
        defaultGrvItemShouldNotBeFound("validToDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsByExternalidIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where externalid equals to DEFAULT_EXTERNALID
        defaultGrvItemShouldBeFound("externalid.equals=" + DEFAULT_EXTERNALID);

        // Get all the grvItemList where externalid equals to UPDATED_EXTERNALID
        defaultGrvItemShouldNotBeFound("externalid.equals=" + UPDATED_EXTERNALID);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByExternalidIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where externalid in DEFAULT_EXTERNALID or UPDATED_EXTERNALID
        defaultGrvItemShouldBeFound("externalid.in=" + DEFAULT_EXTERNALID + "," + UPDATED_EXTERNALID);

        // Get all the grvItemList where externalid equals to UPDATED_EXTERNALID
        defaultGrvItemShouldNotBeFound("externalid.in=" + UPDATED_EXTERNALID);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByExternalidIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where externalid is not null
        defaultGrvItemShouldBeFound("externalid.specified=true");

        // Get all the grvItemList where externalid is null
        defaultGrvItemShouldNotBeFound("externalid.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsByDocnrIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where docnr equals to DEFAULT_DOCNR
        defaultGrvItemShouldBeFound("docnr.equals=" + DEFAULT_DOCNR);

        // Get all the grvItemList where docnr equals to UPDATED_DOCNR
        defaultGrvItemShouldNotBeFound("docnr.equals=" + UPDATED_DOCNR);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByDocnrIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where docnr in DEFAULT_DOCNR or UPDATED_DOCNR
        defaultGrvItemShouldBeFound("docnr.in=" + DEFAULT_DOCNR + "," + UPDATED_DOCNR);

        // Get all the grvItemList where docnr equals to UPDATED_DOCNR
        defaultGrvItemShouldNotBeFound("docnr.in=" + UPDATED_DOCNR);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByDocnrIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where docnr is not null
        defaultGrvItemShouldBeFound("docnr.specified=true");

        // Get all the grvItemList where docnr is null
        defaultGrvItemShouldNotBeFound("docnr.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where createdDate equals to DEFAULT_CREATED_DATE
        defaultGrvItemShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the grvItemList where createdDate equals to UPDATED_CREATED_DATE
        defaultGrvItemShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultGrvItemShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the grvItemList where createdDate equals to UPDATED_CREATED_DATE
        defaultGrvItemShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGrvItemsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);

        // Get all the grvItemList where createdDate is not null
        defaultGrvItemShouldBeFound("createdDate.specified=true");

        // Get all the grvItemList where createdDate is null
        defaultGrvItemShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        Source source = SourceResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        grvItem.setSource(source);
        grvItemRepository.saveAndFlush(grvItem);
        Long sourceId = source.getId();

        // Get all the grvItemList where source equals to sourceId
        defaultGrvItemShouldBeFound("sourceId.equals=" + sourceId);

        // Get all the grvItemList where source equals to sourceId + 1
        defaultGrvItemShouldNotBeFound("sourceId.equals=" + (sourceId + 1));
    }


    @Test
    @Transactional
    public void getAllGrvItemsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        grvItem.setLocation(location);
        grvItemRepository.saveAndFlush(grvItem);
        Long locationId = location.getId();

        // Get all the grvItemList where location equals to locationId
        defaultGrvItemShouldBeFound("locationId.equals=" + locationId);

        // Get all the grvItemList where location equals to locationId + 1
        defaultGrvItemShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllGrvItemsBySourceArchiveIsEqualToSomething() throws Exception {
        // Initialize the database
        SourceArchive sourceArchive = SourceArchiveResourceIntTest.createEntity(em);
        em.persist(sourceArchive);
        em.flush();
        grvItem.setSourceArchive(sourceArchive);
        grvItemRepository.saveAndFlush(grvItem);
        Long sourceArchiveId = sourceArchive.getId();

        // Get all the grvItemList where sourceArchive equals to sourceArchiveId
        defaultGrvItemShouldBeFound("sourceArchiveId.equals=" + sourceArchiveId);

        // Get all the grvItemList where sourceArchive equals to sourceArchiveId + 1
        defaultGrvItemShouldNotBeFound("sourceArchiveId.equals=" + (sourceArchiveId + 1));
    }


    @Test
    @Transactional
    public void getAllGrvItemsByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        GrvItemPerson person = GrvItemPersonResourceIntTest.createEntity(em);
        em.persist(person);
        em.flush();
        grvItem.setPerson(person);
        person.setItem(grvItem);
        grvItemRepository.saveAndFlush(grvItem);
        Long personId = person.getId();

        // Get all the grvItemList where person equals to personId
        defaultGrvItemShouldBeFound("personId.equals=" + personId);

        // Get all the grvItemList where person equals to personId + 1
        defaultGrvItemShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllGrvItemsByErrorsIsEqualToSomething() throws Exception {
        // Initialize the database
        Error errors = ErrorResourceIntTest.createEntity(em);
        em.persist(errors);
        em.flush();
        grvItem.addErrors(errors);
        grvItemRepository.saveAndFlush(grvItem);
        Long errorsId = errors.getId();

        // Get all the grvItemList where errors equals to errorsId
        defaultGrvItemShouldBeFound("errorsId.equals=" + errorsId);

        // Get all the grvItemList where errors equals to errorsId + 1
        defaultGrvItemShouldNotBeFound("errorsId.equals=" + (errorsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGrvItemShouldBeFound(String filter) throws Exception {
        restGrvItemMockMvc.perform(get("/api/grv-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grvItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].validToDateString").value(hasItem(DEFAULT_VALID_TO_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].validToDate").value(hasItem(DEFAULT_VALID_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].externalid").value(hasItem(DEFAULT_EXTERNALID.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())))
            .andExpect(jsonPath("$.[*].docnr").value(hasItem(DEFAULT_DOCNR.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGrvItemShouldNotBeFound(String filter) throws Exception {
        restGrvItemMockMvc.perform(get("/api/grv-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingGrvItem() throws Exception {
        // Get the grvItem
        restGrvItemMockMvc.perform(get("/api/grv-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrvItem() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);
        grvItemSearchRepository.save(grvItem);
        int databaseSizeBeforeUpdate = grvItemRepository.findAll().size();

        // Update the grvItem
        GrvItem updatedGrvItem = grvItemRepository.findOne(grvItem.getId());
        // Disconnect from session so that the updates on updatedGrvItem are not directly saved in db
        em.detach(updatedGrvItem);
        updatedGrvItem
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .validToDateString(UPDATED_VALID_TO_DATE_STRING)
            .validToDate(UPDATED_VALID_TO_DATE)
            .externalid(UPDATED_EXTERNALID)
            .info(UPDATED_INFO)
            .docnr(UPDATED_DOCNR)
            .createdDate(UPDATED_CREATED_DATE);
        GrvItemDTO grvItemDTO = grvItemMapper.toDto(updatedGrvItem);

        restGrvItemMockMvc.perform(put("/api/grv-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemDTO)))
            .andExpect(status().isOk());

        // Validate the GrvItem in the database
        List<GrvItem> grvItemList = grvItemRepository.findAll();
        assertThat(grvItemList).hasSize(databaseSizeBeforeUpdate);
        GrvItem testGrvItem = grvItemList.get(grvItemList.size() - 1);
        assertThat(testGrvItem.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGrvItem.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testGrvItem.getValidToDateString()).isEqualTo(UPDATED_VALID_TO_DATE_STRING);
        assertThat(testGrvItem.getValidToDate()).isEqualTo(UPDATED_VALID_TO_DATE);
        assertThat(testGrvItem.getExternalid()).isEqualTo(UPDATED_EXTERNALID);
        assertThat(testGrvItem.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testGrvItem.getDocnr()).isEqualTo(UPDATED_DOCNR);
        assertThat(testGrvItem.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the GrvItem in Elasticsearch
        GrvItem grvItemEs = grvItemSearchRepository.findOne(testGrvItem.getId());
        assertThat(grvItemEs).isEqualToIgnoringGivenFields(testGrvItem);
    }

    @Test
    @Transactional
    public void updateNonExistingGrvItem() throws Exception {
        int databaseSizeBeforeUpdate = grvItemRepository.findAll().size();

        // Create the GrvItem
        GrvItemDTO grvItemDTO = grvItemMapper.toDto(grvItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrvItemMockMvc.perform(put("/api/grv-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemDTO)))
            .andExpect(status().isCreated());

        // Validate the GrvItem in the database
        List<GrvItem> grvItemList = grvItemRepository.findAll();
        assertThat(grvItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrvItem() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);
        grvItemSearchRepository.save(grvItem);
        int databaseSizeBeforeDelete = grvItemRepository.findAll().size();

        // Get the grvItem
        restGrvItemMockMvc.perform(delete("/api/grv-items/{id}", grvItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean grvItemExistsInEs = grvItemSearchRepository.exists(grvItem.getId());
        assertThat(grvItemExistsInEs).isFalse();

        // Validate the database is empty
        List<GrvItem> grvItemList = grvItemRepository.findAll();
        assertThat(grvItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGrvItem() throws Exception {
        // Initialize the database
        grvItemRepository.saveAndFlush(grvItem);
        grvItemSearchRepository.save(grvItem);

        // Search the grvItem
        restGrvItemMockMvc.perform(get("/api/_search/grv-items?query=id:" + grvItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grvItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].validToDateString").value(hasItem(DEFAULT_VALID_TO_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].validToDate").value(hasItem(DEFAULT_VALID_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].externalid").value(hasItem(DEFAULT_EXTERNALID.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())))
            .andExpect(jsonPath("$.[*].docnr").value(hasItem(DEFAULT_DOCNR.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrvItem.class);
        GrvItem grvItem1 = new GrvItem();
        grvItem1.setId(1L);
        GrvItem grvItem2 = new GrvItem();
        grvItem2.setId(grvItem1.getId());
        assertThat(grvItem1).isEqualTo(grvItem2);
        grvItem2.setId(2L);
        assertThat(grvItem1).isNotEqualTo(grvItem2);
        grvItem1.setId(null);
        assertThat(grvItem1).isNotEqualTo(grvItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrvItemDTO.class);
        GrvItemDTO grvItemDTO1 = new GrvItemDTO();
        grvItemDTO1.setId(1L);
        GrvItemDTO grvItemDTO2 = new GrvItemDTO();
        assertThat(grvItemDTO1).isNotEqualTo(grvItemDTO2);
        grvItemDTO2.setId(grvItemDTO1.getId());
        assertThat(grvItemDTO1).isEqualTo(grvItemDTO2);
        grvItemDTO2.setId(2L);
        assertThat(grvItemDTO1).isNotEqualTo(grvItemDTO2);
        grvItemDTO1.setId(null);
        assertThat(grvItemDTO1).isNotEqualTo(grvItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grvItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grvItemMapper.fromId(null)).isNull();
    }
}
