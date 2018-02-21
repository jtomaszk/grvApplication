package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.Pattern;
import com.jtomaszk.grv.repository.PatternRepository;
import com.jtomaszk.grv.service.PatternService;
import com.jtomaszk.grv.repository.search.PatternSearchRepository;
import com.jtomaszk.grv.service.dto.PatternDTO;
import com.jtomaszk.grv.service.mapper.PatternMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;

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

/**
 * Test class for the PatternResource REST controller.
 *
 * @see PatternResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class PatternResourceIntTest {

    private static final String DEFAULT_COLUMNS = "AAAAAAAAAA";
    private static final String UPDATED_COLUMNS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUES = "AAAAAAAAAA";
    private static final String UPDATED_VALUES = "BBBBBBBBBB";

    @Autowired
    private PatternRepository patternRepository;

    @Autowired
    private PatternMapper patternMapper;

    @Autowired
    private PatternService patternService;

    @Autowired
    private PatternSearchRepository patternSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatternMockMvc;

    private Pattern pattern;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatternResource patternResource = new PatternResource(patternService);
        this.restPatternMockMvc = MockMvcBuilders.standaloneSetup(patternResource)
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
    public static Pattern createEntity(EntityManager em) {
        Pattern pattern = new Pattern()
            .columns(DEFAULT_COLUMNS)
            .values(DEFAULT_VALUES);
        return pattern;
    }

    @Before
    public void initTest() {
        patternSearchRepository.deleteAll();
        pattern = createEntity(em);
    }

    @Test
    @Transactional
    public void createPattern() throws Exception {
        int databaseSizeBeforeCreate = patternRepository.findAll().size();

        // Create the Pattern
        PatternDTO patternDTO = patternMapper.toDto(pattern);
        restPatternMockMvc.perform(post("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternDTO)))
            .andExpect(status().isCreated());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeCreate + 1);
        Pattern testPattern = patternList.get(patternList.size() - 1);
        assertThat(testPattern.getColumns()).isEqualTo(DEFAULT_COLUMNS);
        assertThat(testPattern.getValues()).isEqualTo(DEFAULT_VALUES);

        // Validate the Pattern in Elasticsearch
        Pattern patternEs = patternSearchRepository.findOne(testPattern.getId());
        assertThat(patternEs).isEqualToIgnoringGivenFields(testPattern);
    }

    @Test
    @Transactional
    public void createPatternWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patternRepository.findAll().size();

        // Create the Pattern with an existing ID
        pattern.setId(1L);
        PatternDTO patternDTO = patternMapper.toDto(pattern);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatternMockMvc.perform(post("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkColumnsIsRequired() throws Exception {
        int databaseSizeBeforeTest = patternRepository.findAll().size();
        // set the field null
        pattern.setColumns(null);

        // Create the Pattern, which fails.
        PatternDTO patternDTO = patternMapper.toDto(pattern);

        restPatternMockMvc.perform(post("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternDTO)))
            .andExpect(status().isBadRequest());

        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValuesIsRequired() throws Exception {
        int databaseSizeBeforeTest = patternRepository.findAll().size();
        // set the field null
        pattern.setValues(null);

        // Create the Pattern, which fails.
        PatternDTO patternDTO = patternMapper.toDto(pattern);

        restPatternMockMvc.perform(post("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternDTO)))
            .andExpect(status().isBadRequest());

        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatterns() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);

        // Get all the patternList
        restPatternMockMvc.perform(get("/api/patterns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].columns").value(hasItem(DEFAULT_COLUMNS.toString())))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES.toString())));
    }

    @Test
    @Transactional
    public void getPattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);

        // Get the pattern
        restPatternMockMvc.perform(get("/api/patterns/{id}", pattern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pattern.getId().intValue()))
            .andExpect(jsonPath("$.columns").value(DEFAULT_COLUMNS.toString()))
            .andExpect(jsonPath("$.values").value(DEFAULT_VALUES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPattern() throws Exception {
        // Get the pattern
        restPatternMockMvc.perform(get("/api/patterns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);
        patternSearchRepository.save(pattern);
        int databaseSizeBeforeUpdate = patternRepository.findAll().size();

        // Update the pattern
        Pattern updatedPattern = patternRepository.findOne(pattern.getId());
        // Disconnect from session so that the updates on updatedPattern are not directly saved in db
        em.detach(updatedPattern);
        updatedPattern
            .columns(UPDATED_COLUMNS)
            .values(UPDATED_VALUES);
        PatternDTO patternDTO = patternMapper.toDto(updatedPattern);

        restPatternMockMvc.perform(put("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternDTO)))
            .andExpect(status().isOk());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeUpdate);
        Pattern testPattern = patternList.get(patternList.size() - 1);
        assertThat(testPattern.getColumns()).isEqualTo(UPDATED_COLUMNS);
        assertThat(testPattern.getValues()).isEqualTo(UPDATED_VALUES);

        // Validate the Pattern in Elasticsearch
        Pattern patternEs = patternSearchRepository.findOne(testPattern.getId());
        assertThat(patternEs).isEqualToIgnoringGivenFields(testPattern);
    }

    @Test
    @Transactional
    public void updateNonExistingPattern() throws Exception {
        int databaseSizeBeforeUpdate = patternRepository.findAll().size();

        // Create the Pattern
        PatternDTO patternDTO = patternMapper.toDto(pattern);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatternMockMvc.perform(put("/api/patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patternDTO)))
            .andExpect(status().isCreated());

        // Validate the Pattern in the database
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);
        patternSearchRepository.save(pattern);
        int databaseSizeBeforeDelete = patternRepository.findAll().size();

        // Get the pattern
        restPatternMockMvc.perform(delete("/api/patterns/{id}", pattern.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean patternExistsInEs = patternSearchRepository.exists(pattern.getId());
        assertThat(patternExistsInEs).isFalse();

        // Validate the database is empty
        List<Pattern> patternList = patternRepository.findAll();
        assertThat(patternList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPattern() throws Exception {
        // Initialize the database
        patternRepository.saveAndFlush(pattern);
        patternSearchRepository.save(pattern);

        // Search the pattern
        restPatternMockMvc.perform(get("/api/_search/patterns?query=id:" + pattern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].columns").value(hasItem(DEFAULT_COLUMNS.toString())))
            .andExpect(jsonPath("$.[*].values").value(hasItem(DEFAULT_VALUES.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pattern.class);
        Pattern pattern1 = new Pattern();
        pattern1.setId(1L);
        Pattern pattern2 = new Pattern();
        pattern2.setId(pattern1.getId());
        assertThat(pattern1).isEqualTo(pattern2);
        pattern2.setId(2L);
        assertThat(pattern1).isNotEqualTo(pattern2);
        pattern1.setId(null);
        assertThat(pattern1).isNotEqualTo(pattern2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatternDTO.class);
        PatternDTO patternDTO1 = new PatternDTO();
        patternDTO1.setId(1L);
        PatternDTO patternDTO2 = new PatternDTO();
        assertThat(patternDTO1).isNotEqualTo(patternDTO2);
        patternDTO2.setId(patternDTO1.getId());
        assertThat(patternDTO1).isEqualTo(patternDTO2);
        patternDTO2.setId(2L);
        assertThat(patternDTO1).isNotEqualTo(patternDTO2);
        patternDTO1.setId(null);
        assertThat(patternDTO1).isNotEqualTo(patternDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(patternMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(patternMapper.fromId(null)).isNull();
    }
}
