package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.ParseError;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.repository.ParseErrorRepository;
import com.jtomaszk.grv.service.ParseErrorService;
import com.jtomaszk.grv.repository.search.ParseErrorSearchRepository;
import com.jtomaszk.grv.service.dto.ParseErrorDTO;
import com.jtomaszk.grv.service.mapper.ParseErrorMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.ParseErrorCriteria;
import com.jtomaszk.grv.service.ParseErrorQueryService;

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
 * Test class for the ParseErrorResource REST controller.
 *
 * @see ParseErrorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class ParseErrorResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MSG = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ParseErrorRepository parseErrorRepository;

    @Autowired
    private ParseErrorMapper parseErrorMapper;

    @Autowired
    private ParseErrorService parseErrorService;

    @Autowired
    private ParseErrorSearchRepository parseErrorSearchRepository;

    @Autowired
    private ParseErrorQueryService parseErrorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParseErrorMockMvc;

    private ParseError parseError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParseErrorResource parseErrorResource = new ParseErrorResource(parseErrorService, parseErrorQueryService);
        this.restParseErrorMockMvc = MockMvcBuilders.standaloneSetup(parseErrorResource)
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
    public static ParseError createEntity(EntityManager em) {
        ParseError parseError = new ParseError()
            .title(DEFAULT_TITLE)
            .msg(DEFAULT_MSG)
            .createdDate(DEFAULT_CREATED_DATE);
        return parseError;
    }

    @Before
    public void initTest() {
        parseErrorSearchRepository.deleteAll();
        parseError = createEntity(em);
    }

    @Test
    @Transactional
    public void createParseError() throws Exception {
        int databaseSizeBeforeCreate = parseErrorRepository.findAll().size();

        // Create the ParseError
        ParseErrorDTO parseErrorDTO = parseErrorMapper.toDto(parseError);
        restParseErrorMockMvc.perform(post("/api/parse-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parseErrorDTO)))
            .andExpect(status().isCreated());

        // Validate the ParseError in the database
        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeCreate + 1);
        ParseError testParseError = parseErrorList.get(parseErrorList.size() - 1);
        assertThat(testParseError.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testParseError.getMsg()).isEqualTo(DEFAULT_MSG);
        assertThat(testParseError.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the ParseError in Elasticsearch
        ParseError parseErrorEs = parseErrorSearchRepository.findOne(testParseError.getId());
        assertThat(parseErrorEs).isEqualToIgnoringGivenFields(testParseError);
    }

    @Test
    @Transactional
    public void createParseErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parseErrorRepository.findAll().size();

        // Create the ParseError with an existing ID
        parseError.setId(1L);
        ParseErrorDTO parseErrorDTO = parseErrorMapper.toDto(parseError);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParseErrorMockMvc.perform(post("/api/parse-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parseErrorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParseError in the database
        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = parseErrorRepository.findAll().size();
        // set the field null
        parseError.setTitle(null);

        // Create the ParseError, which fails.
        ParseErrorDTO parseErrorDTO = parseErrorMapper.toDto(parseError);

        restParseErrorMockMvc.perform(post("/api/parse-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parseErrorDTO)))
            .andExpect(status().isBadRequest());

        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = parseErrorRepository.findAll().size();
        // set the field null
        parseError.setCreatedDate(null);

        // Create the ParseError, which fails.
        ParseErrorDTO parseErrorDTO = parseErrorMapper.toDto(parseError);

        restParseErrorMockMvc.perform(post("/api/parse-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parseErrorDTO)))
            .andExpect(status().isBadRequest());

        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParseErrors() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList
        restParseErrorMockMvc.perform(get("/api/parse-errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parseError.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getParseError() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get the parseError
        restParseErrorMockMvc.perform(get("/api/parse-errors/{id}", parseError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parseError.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.msg").value(DEFAULT_MSG.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllParseErrorsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList where title equals to DEFAULT_TITLE
        defaultParseErrorShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the parseErrorList where title equals to UPDATED_TITLE
        defaultParseErrorShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllParseErrorsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultParseErrorShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the parseErrorList where title equals to UPDATED_TITLE
        defaultParseErrorShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllParseErrorsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList where title is not null
        defaultParseErrorShouldBeFound("title.specified=true");

        // Get all the parseErrorList where title is null
        defaultParseErrorShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllParseErrorsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList where createdDate equals to DEFAULT_CREATED_DATE
        defaultParseErrorShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the parseErrorList where createdDate equals to UPDATED_CREATED_DATE
        defaultParseErrorShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllParseErrorsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultParseErrorShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the parseErrorList where createdDate equals to UPDATED_CREATED_DATE
        defaultParseErrorShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllParseErrorsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);

        // Get all the parseErrorList where createdDate is not null
        defaultParseErrorShouldBeFound("createdDate.specified=true");

        // Get all the parseErrorList where createdDate is null
        defaultParseErrorShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllParseErrorsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        Source source = SourceResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        parseError.setSource(source);
        parseErrorRepository.saveAndFlush(parseError);
        Long sourceId = source.getId();

        // Get all the parseErrorList where source equals to sourceId
        defaultParseErrorShouldBeFound("sourceId.equals=" + sourceId);

        // Get all the parseErrorList where source equals to sourceId + 1
        defaultParseErrorShouldNotBeFound("sourceId.equals=" + (sourceId + 1));
    }


    @Test
    @Transactional
    public void getAllParseErrorsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        GrvItem item = GrvItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        parseError.setItem(item);
        parseErrorRepository.saveAndFlush(parseError);
        Long itemId = item.getId();

        // Get all the parseErrorList where item equals to itemId
        defaultParseErrorShouldBeFound("itemId.equals=" + itemId);

        // Get all the parseErrorList where item equals to itemId + 1
        defaultParseErrorShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParseErrorShouldBeFound(String filter) throws Exception {
        restParseErrorMockMvc.perform(get("/api/parse-errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parseError.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParseErrorShouldNotBeFound(String filter) throws Exception {
        restParseErrorMockMvc.perform(get("/api/parse-errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingParseError() throws Exception {
        // Get the parseError
        restParseErrorMockMvc.perform(get("/api/parse-errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParseError() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);
        parseErrorSearchRepository.save(parseError);
        int databaseSizeBeforeUpdate = parseErrorRepository.findAll().size();

        // Update the parseError
        ParseError updatedParseError = parseErrorRepository.findOne(parseError.getId());
        // Disconnect from session so that the updates on updatedParseError are not directly saved in db
        em.detach(updatedParseError);
        updatedParseError
            .title(UPDATED_TITLE)
            .msg(UPDATED_MSG)
            .createdDate(UPDATED_CREATED_DATE);
        ParseErrorDTO parseErrorDTO = parseErrorMapper.toDto(updatedParseError);

        restParseErrorMockMvc.perform(put("/api/parse-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parseErrorDTO)))
            .andExpect(status().isOk());

        // Validate the ParseError in the database
        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeUpdate);
        ParseError testParseError = parseErrorList.get(parseErrorList.size() - 1);
        assertThat(testParseError.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testParseError.getMsg()).isEqualTo(UPDATED_MSG);
        assertThat(testParseError.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the ParseError in Elasticsearch
        ParseError parseErrorEs = parseErrorSearchRepository.findOne(testParseError.getId());
        assertThat(parseErrorEs).isEqualToIgnoringGivenFields(testParseError);
    }

    @Test
    @Transactional
    public void updateNonExistingParseError() throws Exception {
        int databaseSizeBeforeUpdate = parseErrorRepository.findAll().size();

        // Create the ParseError
        ParseErrorDTO parseErrorDTO = parseErrorMapper.toDto(parseError);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParseErrorMockMvc.perform(put("/api/parse-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parseErrorDTO)))
            .andExpect(status().isCreated());

        // Validate the ParseError in the database
        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParseError() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);
        parseErrorSearchRepository.save(parseError);
        int databaseSizeBeforeDelete = parseErrorRepository.findAll().size();

        // Get the parseError
        restParseErrorMockMvc.perform(delete("/api/parse-errors/{id}", parseError.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean parseErrorExistsInEs = parseErrorSearchRepository.exists(parseError.getId());
        assertThat(parseErrorExistsInEs).isFalse();

        // Validate the database is empty
        List<ParseError> parseErrorList = parseErrorRepository.findAll();
        assertThat(parseErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchParseError() throws Exception {
        // Initialize the database
        parseErrorRepository.saveAndFlush(parseError);
        parseErrorSearchRepository.save(parseError);

        // Search the parseError
        restParseErrorMockMvc.perform(get("/api/_search/parse-errors?query=id:" + parseError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parseError.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParseError.class);
        ParseError parseError1 = new ParseError();
        parseError1.setId(1L);
        ParseError parseError2 = new ParseError();
        parseError2.setId(parseError1.getId());
        assertThat(parseError1).isEqualTo(parseError2);
        parseError2.setId(2L);
        assertThat(parseError1).isNotEqualTo(parseError2);
        parseError1.setId(null);
        assertThat(parseError1).isNotEqualTo(parseError2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParseErrorDTO.class);
        ParseErrorDTO parseErrorDTO1 = new ParseErrorDTO();
        parseErrorDTO1.setId(1L);
        ParseErrorDTO parseErrorDTO2 = new ParseErrorDTO();
        assertThat(parseErrorDTO1).isNotEqualTo(parseErrorDTO2);
        parseErrorDTO2.setId(parseErrorDTO1.getId());
        assertThat(parseErrorDTO1).isEqualTo(parseErrorDTO2);
        parseErrorDTO2.setId(2L);
        assertThat(parseErrorDTO1).isNotEqualTo(parseErrorDTO2);
        parseErrorDTO1.setId(null);
        assertThat(parseErrorDTO1).isNotEqualTo(parseErrorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parseErrorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parseErrorMapper.fromId(null)).isNull();
    }
}
