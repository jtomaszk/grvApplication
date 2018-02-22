package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.repository.InputPatternRepository;
import com.jtomaszk.grv.service.InputPatternService;
import com.jtomaszk.grv.repository.search.InputPatternSearchRepository;
import com.jtomaszk.grv.service.dto.InputPatternDTO;
import com.jtomaszk.grv.service.mapper.InputPatternMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.InputPatternCriteria;
import com.jtomaszk.grv.service.InputPatternQueryService;

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
 * Test class for the InputPatternResource REST controller.
 *
 * @see InputPatternResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class InputPatternResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private InputPatternRepository inputPatternRepository;

    @Autowired
    private InputPatternMapper inputPatternMapper;

    @Autowired
    private InputPatternService inputPatternService;

    @Autowired
    private InputPatternSearchRepository inputPatternSearchRepository;

    @Autowired
    private InputPatternQueryService inputPatternQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInputPatternMockMvc;

    private InputPattern inputPattern;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InputPatternResource inputPatternResource = new InputPatternResource(inputPatternService, inputPatternQueryService);
        this.restInputPatternMockMvc = MockMvcBuilders.standaloneSetup(inputPatternResource)
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
    public static InputPattern createEntity(EntityManager em) {
        InputPattern inputPattern = new InputPattern()
            .title(DEFAULT_TITLE);
        return inputPattern;
    }

    @Before
    public void initTest() {
        inputPatternSearchRepository.deleteAll();
        inputPattern = createEntity(em);
    }

    @Test
    @Transactional
    public void createInputPattern() throws Exception {
        int databaseSizeBeforeCreate = inputPatternRepository.findAll().size();

        // Create the InputPattern
        InputPatternDTO inputPatternDTO = inputPatternMapper.toDto(inputPattern);
        restInputPatternMockMvc.perform(post("/api/input-patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputPatternDTO)))
            .andExpect(status().isCreated());

        // Validate the InputPattern in the database
        List<InputPattern> inputPatternList = inputPatternRepository.findAll();
        assertThat(inputPatternList).hasSize(databaseSizeBeforeCreate + 1);
        InputPattern testInputPattern = inputPatternList.get(inputPatternList.size() - 1);
        assertThat(testInputPattern.getTitle()).isEqualTo(DEFAULT_TITLE);

        // Validate the InputPattern in Elasticsearch
        InputPattern inputPatternEs = inputPatternSearchRepository.findOne(testInputPattern.getId());
        assertThat(inputPatternEs).isEqualToIgnoringGivenFields(testInputPattern);
    }

    @Test
    @Transactional
    public void createInputPatternWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputPatternRepository.findAll().size();

        // Create the InputPattern with an existing ID
        inputPattern.setId(1L);
        InputPatternDTO inputPatternDTO = inputPatternMapper.toDto(inputPattern);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputPatternMockMvc.perform(post("/api/input-patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputPatternDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InputPattern in the database
        List<InputPattern> inputPatternList = inputPatternRepository.findAll();
        assertThat(inputPatternList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = inputPatternRepository.findAll().size();
        // set the field null
        inputPattern.setTitle(null);

        // Create the InputPattern, which fails.
        InputPatternDTO inputPatternDTO = inputPatternMapper.toDto(inputPattern);

        restInputPatternMockMvc.perform(post("/api/input-patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputPatternDTO)))
            .andExpect(status().isBadRequest());

        List<InputPattern> inputPatternList = inputPatternRepository.findAll();
        assertThat(inputPatternList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInputPatterns() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);

        // Get all the inputPatternList
        restInputPatternMockMvc.perform(get("/api/input-patterns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputPattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getInputPattern() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);

        // Get the inputPattern
        restInputPatternMockMvc.perform(get("/api/input-patterns/{id}", inputPattern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inputPattern.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getAllInputPatternsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);

        // Get all the inputPatternList where title equals to DEFAULT_TITLE
        defaultInputPatternShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the inputPatternList where title equals to UPDATED_TITLE
        defaultInputPatternShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllInputPatternsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);

        // Get all the inputPatternList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultInputPatternShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the inputPatternList where title equals to UPDATED_TITLE
        defaultInputPatternShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllInputPatternsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);

        // Get all the inputPatternList where title is not null
        defaultInputPatternShouldBeFound("title.specified=true");

        // Get all the inputPatternList where title is null
        defaultInputPatternShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllInputPatternsBySourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        Source sources = SourceResourceIntTest.createEntity(em);
        em.persist(sources);
        em.flush();
        inputPattern.addSources(sources);
        inputPatternRepository.saveAndFlush(inputPattern);
        Long sourcesId = sources.getId();

        // Get all the inputPatternList where sources equals to sourcesId
        defaultInputPatternShouldBeFound("sourcesId.equals=" + sourcesId);

        // Get all the inputPatternList where sources equals to sourcesId + 1
        defaultInputPatternShouldNotBeFound("sourcesId.equals=" + (sourcesId + 1));
    }


    @Test
    @Transactional
    public void getAllInputPatternsByColumnsIsEqualToSomething() throws Exception {
        // Initialize the database
        PatternColumn columns = PatternColumnResourceIntTest.createEntity(em);
        em.persist(columns);
        em.flush();
        inputPattern.addColumns(columns);
        inputPatternRepository.saveAndFlush(inputPattern);
        Long columnsId = columns.getId();

        // Get all the inputPatternList where columns equals to columnsId
        defaultInputPatternShouldBeFound("columnsId.equals=" + columnsId);

        // Get all the inputPatternList where columns equals to columnsId + 1
        defaultInputPatternShouldNotBeFound("columnsId.equals=" + (columnsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInputPatternShouldBeFound(String filter) throws Exception {
        restInputPatternMockMvc.perform(get("/api/input-patterns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputPattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInputPatternShouldNotBeFound(String filter) throws Exception {
        restInputPatternMockMvc.perform(get("/api/input-patterns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingInputPattern() throws Exception {
        // Get the inputPattern
        restInputPatternMockMvc.perform(get("/api/input-patterns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInputPattern() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);
        inputPatternSearchRepository.save(inputPattern);
        int databaseSizeBeforeUpdate = inputPatternRepository.findAll().size();

        // Update the inputPattern
        InputPattern updatedInputPattern = inputPatternRepository.findOne(inputPattern.getId());
        // Disconnect from session so that the updates on updatedInputPattern are not directly saved in db
        em.detach(updatedInputPattern);
        updatedInputPattern
            .title(UPDATED_TITLE);
        InputPatternDTO inputPatternDTO = inputPatternMapper.toDto(updatedInputPattern);

        restInputPatternMockMvc.perform(put("/api/input-patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputPatternDTO)))
            .andExpect(status().isOk());

        // Validate the InputPattern in the database
        List<InputPattern> inputPatternList = inputPatternRepository.findAll();
        assertThat(inputPatternList).hasSize(databaseSizeBeforeUpdate);
        InputPattern testInputPattern = inputPatternList.get(inputPatternList.size() - 1);
        assertThat(testInputPattern.getTitle()).isEqualTo(UPDATED_TITLE);

        // Validate the InputPattern in Elasticsearch
        InputPattern inputPatternEs = inputPatternSearchRepository.findOne(testInputPattern.getId());
        assertThat(inputPatternEs).isEqualToIgnoringGivenFields(testInputPattern);
    }

    @Test
    @Transactional
    public void updateNonExistingInputPattern() throws Exception {
        int databaseSizeBeforeUpdate = inputPatternRepository.findAll().size();

        // Create the InputPattern
        InputPatternDTO inputPatternDTO = inputPatternMapper.toDto(inputPattern);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInputPatternMockMvc.perform(put("/api/input-patterns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputPatternDTO)))
            .andExpect(status().isCreated());

        // Validate the InputPattern in the database
        List<InputPattern> inputPatternList = inputPatternRepository.findAll();
        assertThat(inputPatternList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInputPattern() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);
        inputPatternSearchRepository.save(inputPattern);
        int databaseSizeBeforeDelete = inputPatternRepository.findAll().size();

        // Get the inputPattern
        restInputPatternMockMvc.perform(delete("/api/input-patterns/{id}", inputPattern.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean inputPatternExistsInEs = inputPatternSearchRepository.exists(inputPattern.getId());
        assertThat(inputPatternExistsInEs).isFalse();

        // Validate the database is empty
        List<InputPattern> inputPatternList = inputPatternRepository.findAll();
        assertThat(inputPatternList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInputPattern() throws Exception {
        // Initialize the database
        inputPatternRepository.saveAndFlush(inputPattern);
        inputPatternSearchRepository.save(inputPattern);

        // Search the inputPattern
        restInputPatternMockMvc.perform(get("/api/_search/input-patterns?query=id:" + inputPattern.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputPattern.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputPattern.class);
        InputPattern inputPattern1 = new InputPattern();
        inputPattern1.setId(1L);
        InputPattern inputPattern2 = new InputPattern();
        inputPattern2.setId(inputPattern1.getId());
        assertThat(inputPattern1).isEqualTo(inputPattern2);
        inputPattern2.setId(2L);
        assertThat(inputPattern1).isNotEqualTo(inputPattern2);
        inputPattern1.setId(null);
        assertThat(inputPattern1).isNotEqualTo(inputPattern2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputPatternDTO.class);
        InputPatternDTO inputPatternDTO1 = new InputPatternDTO();
        inputPatternDTO1.setId(1L);
        InputPatternDTO inputPatternDTO2 = new InputPatternDTO();
        assertThat(inputPatternDTO1).isNotEqualTo(inputPatternDTO2);
        inputPatternDTO2.setId(inputPatternDTO1.getId());
        assertThat(inputPatternDTO1).isEqualTo(inputPatternDTO2);
        inputPatternDTO2.setId(2L);
        assertThat(inputPatternDTO1).isNotEqualTo(inputPatternDTO2);
        inputPatternDTO1.setId(null);
        assertThat(inputPatternDTO1).isNotEqualTo(inputPatternDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inputPatternMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inputPatternMapper.fromId(null)).isNull();
    }
}
