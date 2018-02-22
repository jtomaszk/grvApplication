package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.Error;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.repository.ErrorRepository;
import com.jtomaszk.grv.service.ErrorService;
import com.jtomaszk.grv.repository.search.ErrorSearchRepository;
import com.jtomaszk.grv.service.dto.ErrorDTO;
import com.jtomaszk.grv.service.mapper.ErrorMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.ErrorCriteria;
import com.jtomaszk.grv.service.ErrorQueryService;

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
 * Test class for the ErrorResource REST controller.
 *
 * @see ErrorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class ErrorResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MSG = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ErrorRepository errorRepository;

    @Autowired
    private ErrorMapper errorMapper;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private ErrorSearchRepository errorSearchRepository;

    @Autowired
    private ErrorQueryService errorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restErrorMockMvc;

    private Error error;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ErrorResource errorResource = new ErrorResource(errorService, errorQueryService);
        this.restErrorMockMvc = MockMvcBuilders.standaloneSetup(errorResource)
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
    public static Error createEntity(EntityManager em) {
        Error error = new Error()
            .title(DEFAULT_TITLE)
            .msg(DEFAULT_MSG)
            .createdDate(DEFAULT_CREATED_DATE);
        return error;
    }

    @Before
    public void initTest() {
        errorSearchRepository.deleteAll();
        error = createEntity(em);
    }

    @Test
    @Transactional
    public void createError() throws Exception {
        int databaseSizeBeforeCreate = errorRepository.findAll().size();

        // Create the Error
        ErrorDTO errorDTO = errorMapper.toDto(error);
        restErrorMockMvc.perform(post("/api/errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorDTO)))
            .andExpect(status().isCreated());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeCreate + 1);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testError.getMsg()).isEqualTo(DEFAULT_MSG);
        assertThat(testError.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the Error in Elasticsearch
        Error errorEs = errorSearchRepository.findOne(testError.getId());
        assertThat(errorEs).isEqualToIgnoringGivenFields(testError);
    }

    @Test
    @Transactional
    public void createErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = errorRepository.findAll().size();

        // Create the Error with an existing ID
        error.setId(1L);
        ErrorDTO errorDTO = errorMapper.toDto(error);

        // An entity with an existing ID cannot be created, so this API call must fail
        restErrorMockMvc.perform(post("/api/errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = errorRepository.findAll().size();
        // set the field null
        error.setTitle(null);

        // Create the Error, which fails.
        ErrorDTO errorDTO = errorMapper.toDto(error);

        restErrorMockMvc.perform(post("/api/errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorDTO)))
            .andExpect(status().isBadRequest());

        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = errorRepository.findAll().size();
        // set the field null
        error.setCreatedDate(null);

        // Create the Error, which fails.
        ErrorDTO errorDTO = errorMapper.toDto(error);

        restErrorMockMvc.perform(post("/api/errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorDTO)))
            .andExpect(status().isBadRequest());

        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllErrors() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList
        restErrorMockMvc.perform(get("/api/errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(error.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get the error
        restErrorMockMvc.perform(get("/api/errors/{id}", error.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(error.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.msg").value(DEFAULT_MSG.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllErrorsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList where title equals to DEFAULT_TITLE
        defaultErrorShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the errorList where title equals to UPDATED_TITLE
        defaultErrorShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllErrorsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultErrorShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the errorList where title equals to UPDATED_TITLE
        defaultErrorShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllErrorsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList where title is not null
        defaultErrorShouldBeFound("title.specified=true");

        // Get all the errorList where title is null
        defaultErrorShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllErrorsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList where createdDate equals to DEFAULT_CREATED_DATE
        defaultErrorShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the errorList where createdDate equals to UPDATED_CREATED_DATE
        defaultErrorShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultErrorShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the errorList where createdDate equals to UPDATED_CREATED_DATE
        defaultErrorShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList where createdDate is not null
        defaultErrorShouldBeFound("createdDate.specified=true");

        // Get all the errorList where createdDate is null
        defaultErrorShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllErrorsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        Source source = SourceResourceIntTest.createEntity(em);
        em.persist(source);
        em.flush();
        error.setSource(source);
        errorRepository.saveAndFlush(error);
        Long sourceId = source.getId();

        // Get all the errorList where source equals to sourceId
        defaultErrorShouldBeFound("sourceId.equals=" + sourceId);

        // Get all the errorList where source equals to sourceId + 1
        defaultErrorShouldNotBeFound("sourceId.equals=" + (sourceId + 1));
    }


    @Test
    @Transactional
    public void getAllErrorsByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        GrvItem item = GrvItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        error.setItem(item);
        errorRepository.saveAndFlush(error);
        Long itemId = item.getId();

        // Get all the errorList where item equals to itemId
        defaultErrorShouldBeFound("itemId.equals=" + itemId);

        // Get all the errorList where item equals to itemId + 1
        defaultErrorShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultErrorShouldBeFound(String filter) throws Exception {
        restErrorMockMvc.perform(get("/api/errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(error.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultErrorShouldNotBeFound(String filter) throws Exception {
        restErrorMockMvc.perform(get("/api/errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingError() throws Exception {
        // Get the error
        restErrorMockMvc.perform(get("/api/errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);
        errorSearchRepository.save(error);
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();

        // Update the error
        Error updatedError = errorRepository.findOne(error.getId());
        // Disconnect from session so that the updates on updatedError are not directly saved in db
        em.detach(updatedError);
        updatedError
            .title(UPDATED_TITLE)
            .msg(UPDATED_MSG)
            .createdDate(UPDATED_CREATED_DATE);
        ErrorDTO errorDTO = errorMapper.toDto(updatedError);

        restErrorMockMvc.perform(put("/api/errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorDTO)))
            .andExpect(status().isOk());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testError.getMsg()).isEqualTo(UPDATED_MSG);
        assertThat(testError.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the Error in Elasticsearch
        Error errorEs = errorSearchRepository.findOne(testError.getId());
        assertThat(errorEs).isEqualToIgnoringGivenFields(testError);
    }

    @Test
    @Transactional
    public void updateNonExistingError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();

        // Create the Error
        ErrorDTO errorDTO = errorMapper.toDto(error);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restErrorMockMvc.perform(put("/api/errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorDTO)))
            .andExpect(status().isCreated());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);
        errorSearchRepository.save(error);
        int databaseSizeBeforeDelete = errorRepository.findAll().size();

        // Get the error
        restErrorMockMvc.perform(delete("/api/errors/{id}", error.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean errorExistsInEs = errorSearchRepository.exists(error.getId());
        assertThat(errorExistsInEs).isFalse();

        // Validate the database is empty
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);
        errorSearchRepository.save(error);

        // Search the error
        restErrorMockMvc.perform(get("/api/_search/errors?query=id:" + error.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(error.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Error.class);
        Error error1 = new Error();
        error1.setId(1L);
        Error error2 = new Error();
        error2.setId(error1.getId());
        assertThat(error1).isEqualTo(error2);
        error2.setId(2L);
        assertThat(error1).isNotEqualTo(error2);
        error1.setId(null);
        assertThat(error1).isNotEqualTo(error2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrorDTO.class);
        ErrorDTO errorDTO1 = new ErrorDTO();
        errorDTO1.setId(1L);
        ErrorDTO errorDTO2 = new ErrorDTO();
        assertThat(errorDTO1).isNotEqualTo(errorDTO2);
        errorDTO2.setId(errorDTO1.getId());
        assertThat(errorDTO1).isEqualTo(errorDTO2);
        errorDTO2.setId(2L);
        assertThat(errorDTO1).isNotEqualTo(errorDTO2);
        errorDTO1.setId(null);
        assertThat(errorDTO1).isNotEqualTo(errorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(errorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(errorMapper.fromId(null)).isNull();
    }
}
