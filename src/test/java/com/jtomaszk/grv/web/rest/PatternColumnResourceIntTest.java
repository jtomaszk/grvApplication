package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.domain.Pattern;
import com.jtomaszk.grv.repository.PatternColumnRepository;
import com.jtomaszk.grv.service.PatternColumnService;
import com.jtomaszk.grv.repository.search.PatternColumnSearchRepository;
import com.jtomaszk.grv.service.dto.PatternColumnDTO;
import com.jtomaszk.grv.service.mapper.PatternColumnMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.PatternColumnCriteria;
import com.jtomaszk.grv.service.PatternColumnQueryService;

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
import java.util.List;

import static com.jtomaszk.grv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jtomaszk.grv.domain.enumeration.Column;
/**
 * Test class for the PatternColumnResource REST controller.
 *
 * @see PatternColumnResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class PatternColumnResourceIntTest {

    private static final Column DEFAULT_COLUMN = Column.FIRST_NAME;
    private static final Column UPDATED_COLUMN = Column.LAST_NAME;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private PatternColumnRepository patternColumnRepository;

    @Autowired
    private PatternColumnMapper patternColumnMapper;

    @Autowired
    private PatternColumnService patternColumnService;

    @Autowired
    private PatternColumnSearchRepository patternColumnSearchRepository;

    @Autowired
    private PatternColumnQueryService patternColumnQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatternColumnMockMvc;

    private PatternColumn patternColumn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatternColumnResource patternColumnResource = new PatternColumnResource(patternColumnService, patternColumnQueryService);
        this.restPatternColumnMockMvc = MockMvcBuilders.standaloneSetup(patternColumnResource)
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
    public static PatternColumn createEntity(EntityManager em) {
        PatternColumn patternColumn = new PatternColumn()
            .column(DEFAULT_COLUMN)
            .value(DEFAULT_VALUE);
        // Add required entity
        Pattern pattern = PatternResourceIntTest.createEntity(em);
        em.persist(pattern);
        em.flush();
        patternColumn.setPattern(pattern);
        return patternColumn;
    }

    @Before
    public void initTest() {
        patternColumnSearchRepository.deleteAll();
        patternColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatternColumn() throws Exception {
        int databaseSizeBeforeCreate = patternColumnRepository.findAll().size();

        // Create the PatternColumn
        PatternColumnDTO patternColumnDTO = patternColumnMapper.toDto(patternColumn);
        restPatternColumnMockMvc.perform(post("/api/pattern-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the PatternColumn in the database
        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeCreate + 1);
        PatternColumn testPatternColumn = patternColumnList.get(patternColumnList.size() - 1);
        assertThat(testPatternColumn.getColumn()).isEqualTo(DEFAULT_COLUMN);
        assertThat(testPatternColumn.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the PatternColumn in Elasticsearch
        PatternColumn patternColumnEs = patternColumnSearchRepository.findOne(testPatternColumn.getId());
        assertThat(patternColumnEs).isEqualToIgnoringGivenFields(testPatternColumn);
    }

    @Test
    @Transactional
    public void createPatternColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patternColumnRepository.findAll().size();

        // Create the PatternColumn with an existing ID
        patternColumn.setId(1L);
        PatternColumnDTO patternColumnDTO = patternColumnMapper.toDto(patternColumn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatternColumnMockMvc.perform(post("/api/pattern-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PatternColumn in the database
        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkColumnIsRequired() throws Exception {
        int databaseSizeBeforeTest = patternColumnRepository.findAll().size();
        // set the field null
        patternColumn.setColumn(null);

        // Create the PatternColumn, which fails.
        PatternColumnDTO patternColumnDTO = patternColumnMapper.toDto(patternColumn);

        restPatternColumnMockMvc.perform(post("/api/pattern-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternColumnDTO)))
            .andExpect(status().isBadRequest());

        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = patternColumnRepository.findAll().size();
        // set the field null
        patternColumn.setValue(null);

        // Create the PatternColumn, which fails.
        PatternColumnDTO patternColumnDTO = patternColumnMapper.toDto(patternColumn);

        restPatternColumnMockMvc.perform(post("/api/pattern-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternColumnDTO)))
            .andExpect(status().isBadRequest());

        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatternColumns() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList
        restPatternColumnMockMvc.perform(get("/api/pattern-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patternColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].column").value(hasItem(DEFAULT_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getPatternColumn() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get the patternColumn
        restPatternColumnMockMvc.perform(get("/api/pattern-columns/{id}", patternColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patternColumn.getId().intValue()))
            .andExpect(jsonPath("$.column").value(DEFAULT_COLUMN.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByColumnIsEqualToSomething() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList where column equals to DEFAULT_COLUMN
        defaultPatternColumnShouldBeFound("column.equals=" + DEFAULT_COLUMN);

        // Get all the patternColumnList where column equals to UPDATED_COLUMN
        defaultPatternColumnShouldNotBeFound("column.equals=" + UPDATED_COLUMN);
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByColumnIsInShouldWork() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList where column in DEFAULT_COLUMN or UPDATED_COLUMN
        defaultPatternColumnShouldBeFound("column.in=" + DEFAULT_COLUMN + "," + UPDATED_COLUMN);

        // Get all the patternColumnList where column equals to UPDATED_COLUMN
        defaultPatternColumnShouldNotBeFound("column.in=" + UPDATED_COLUMN);
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByColumnIsNullOrNotNull() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList where column is not null
        defaultPatternColumnShouldBeFound("column.specified=true");

        // Get all the patternColumnList where column is null
        defaultPatternColumnShouldNotBeFound("column.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList where value equals to DEFAULT_VALUE
        defaultPatternColumnShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the patternColumnList where value equals to UPDATED_VALUE
        defaultPatternColumnShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPatternColumnShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the patternColumnList where value equals to UPDATED_VALUE
        defaultPatternColumnShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);

        // Get all the patternColumnList where value is not null
        defaultPatternColumnShouldBeFound("value.specified=true");

        // Get all the patternColumnList where value is null
        defaultPatternColumnShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatternColumnsByPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        Pattern pattern = PatternResourceIntTest.createEntity(em);
        em.persist(pattern);
        em.flush();
        patternColumn.setPattern(pattern);
        patternColumnRepository.saveAndFlush(patternColumn);
        Long patternId = pattern.getId();

        // Get all the patternColumnList where pattern equals to patternId
        defaultPatternColumnShouldBeFound("patternId.equals=" + patternId);

        // Get all the patternColumnList where pattern equals to patternId + 1
        defaultPatternColumnShouldNotBeFound("patternId.equals=" + (patternId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPatternColumnShouldBeFound(String filter) throws Exception {
        restPatternColumnMockMvc.perform(get("/api/pattern-columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patternColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].column").value(hasItem(DEFAULT_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPatternColumnShouldNotBeFound(String filter) throws Exception {
        restPatternColumnMockMvc.perform(get("/api/pattern-columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPatternColumn() throws Exception {
        // Get the patternColumn
        restPatternColumnMockMvc.perform(get("/api/pattern-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatternColumn() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);
        patternColumnSearchRepository.save(patternColumn);
        int databaseSizeBeforeUpdate = patternColumnRepository.findAll().size();

        // Update the patternColumn
        PatternColumn updatedPatternColumn = patternColumnRepository.findOne(patternColumn.getId());
        // Disconnect from session so that the updates on updatedPatternColumn are not directly saved in db
        em.detach(updatedPatternColumn);
        updatedPatternColumn
            .column(UPDATED_COLUMN)
            .value(UPDATED_VALUE);
        PatternColumnDTO patternColumnDTO = patternColumnMapper.toDto(updatedPatternColumn);

        restPatternColumnMockMvc.perform(put("/api/pattern-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternColumnDTO)))
            .andExpect(status().isOk());

        // Validate the PatternColumn in the database
        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeUpdate);
        PatternColumn testPatternColumn = patternColumnList.get(patternColumnList.size() - 1);
        assertThat(testPatternColumn.getColumn()).isEqualTo(UPDATED_COLUMN);
        assertThat(testPatternColumn.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the PatternColumn in Elasticsearch
        PatternColumn patternColumnEs = patternColumnSearchRepository.findOne(testPatternColumn.getId());
        assertThat(patternColumnEs).isEqualToIgnoringGivenFields(testPatternColumn);
    }

    @Test
    @Transactional
    public void updateNonExistingPatternColumn() throws Exception {
        int databaseSizeBeforeUpdate = patternColumnRepository.findAll().size();

        // Create the PatternColumn
        PatternColumnDTO patternColumnDTO = patternColumnMapper.toDto(patternColumn);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatternColumnMockMvc.perform(put("/api/pattern-columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the PatternColumn in the database
        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePatternColumn() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);
        patternColumnSearchRepository.save(patternColumn);
        int databaseSizeBeforeDelete = patternColumnRepository.findAll().size();

        // Get the patternColumn
        restPatternColumnMockMvc.perform(delete("/api/pattern-columns/{id}", patternColumn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean patternColumnExistsInEs = patternColumnSearchRepository.exists(patternColumn.getId());
        assertThat(patternColumnExistsInEs).isFalse();

        // Validate the database is empty
        List<PatternColumn> patternColumnList = patternColumnRepository.findAll();
        assertThat(patternColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPatternColumn() throws Exception {
        // Initialize the database
        patternColumnRepository.saveAndFlush(patternColumn);
        patternColumnSearchRepository.save(patternColumn);

        // Search the patternColumn
        restPatternColumnMockMvc.perform(get("/api/_search/pattern-columns?query=id:" + patternColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patternColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].column").value(hasItem(DEFAULT_COLUMN.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatternColumn.class);
        PatternColumn patternColumn1 = new PatternColumn();
        patternColumn1.setId(1L);
        PatternColumn patternColumn2 = new PatternColumn();
        patternColumn2.setId(patternColumn1.getId());
        assertThat(patternColumn1).isEqualTo(patternColumn2);
        patternColumn2.setId(2L);
        assertThat(patternColumn1).isNotEqualTo(patternColumn2);
        patternColumn1.setId(null);
        assertThat(patternColumn1).isNotEqualTo(patternColumn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatternColumnDTO.class);
        PatternColumnDTO patternColumnDTO1 = new PatternColumnDTO();
        patternColumnDTO1.setId(1L);
        PatternColumnDTO patternColumnDTO2 = new PatternColumnDTO();
        assertThat(patternColumnDTO1).isNotEqualTo(patternColumnDTO2);
        patternColumnDTO2.setId(patternColumnDTO1.getId());
        assertThat(patternColumnDTO1).isEqualTo(patternColumnDTO2);
        patternColumnDTO2.setId(2L);
        assertThat(patternColumnDTO1).isNotEqualTo(patternColumnDTO2);
        patternColumnDTO1.setId(null);
        assertThat(patternColumnDTO1).isNotEqualTo(patternColumnDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patternColumnMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patternColumnMapper.fromId(null)).isNull();
    }
}
