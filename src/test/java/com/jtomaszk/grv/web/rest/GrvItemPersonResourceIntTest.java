package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.GrvItemPerson;
import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.repository.GrvItemPersonRepository;
import com.jtomaszk.grv.service.GrvItemPersonService;
import com.jtomaszk.grv.repository.search.GrvItemPersonSearchRepository;
import com.jtomaszk.grv.service.dto.GrvItemPersonDTO;
import com.jtomaszk.grv.service.mapper.GrvItemPersonMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.GrvItemPersonCriteria;
import com.jtomaszk.grv.service.GrvItemPersonQueryService;

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
 * Test class for the GrvItemPersonResource REST controller.
 *
 * @see GrvItemPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class GrvItemPersonResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANOTHER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ANOTHER_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_START_DATE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE_STRING = "BBBBBBBBBB";

    @Autowired
    private GrvItemPersonRepository grvItemPersonRepository;

    @Autowired
    private GrvItemPersonMapper grvItemPersonMapper;

    @Autowired
    private GrvItemPersonService grvItemPersonService;

    @Autowired
    private GrvItemPersonSearchRepository grvItemPersonSearchRepository;

    @Autowired
    private GrvItemPersonQueryService grvItemPersonQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrvItemPersonMockMvc;

    private GrvItemPerson grvItemPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrvItemPersonResource grvItemPersonResource = new GrvItemPersonResource(grvItemPersonService, grvItemPersonQueryService);
        this.restGrvItemPersonMockMvc = MockMvcBuilders.standaloneSetup(grvItemPersonResource)
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
    public static GrvItemPerson createEntity(EntityManager em) {
        GrvItemPerson grvItemPerson = new GrvItemPerson()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .anotherLastName(DEFAULT_ANOTHER_LAST_NAME)
            .startDateString(DEFAULT_START_DATE_STRING)
            .endDateString(DEFAULT_END_DATE_STRING);
        return grvItemPerson;
    }

    @Before
    public void initTest() {
        grvItemPersonSearchRepository.deleteAll();
        grvItemPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrvItemPerson() throws Exception {
        int databaseSizeBeforeCreate = grvItemPersonRepository.findAll().size();

        // Create the GrvItemPerson
        GrvItemPersonDTO grvItemPersonDTO = grvItemPersonMapper.toDto(grvItemPerson);
        restGrvItemPersonMockMvc.perform(post("/api/grv-item-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the GrvItemPerson in the database
        List<GrvItemPerson> grvItemPersonList = grvItemPersonRepository.findAll();
        assertThat(grvItemPersonList).hasSize(databaseSizeBeforeCreate + 1);
        GrvItemPerson testGrvItemPerson = grvItemPersonList.get(grvItemPersonList.size() - 1);
        assertThat(testGrvItemPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testGrvItemPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testGrvItemPerson.getAnotherLastName()).isEqualTo(DEFAULT_ANOTHER_LAST_NAME);
        assertThat(testGrvItemPerson.getStartDateString()).isEqualTo(DEFAULT_START_DATE_STRING);
        assertThat(testGrvItemPerson.getEndDateString()).isEqualTo(DEFAULT_END_DATE_STRING);

        // Validate the GrvItemPerson in Elasticsearch
        GrvItemPerson grvItemPersonEs = grvItemPersonSearchRepository.findOne(testGrvItemPerson.getId());
        assertThat(grvItemPersonEs).isEqualToIgnoringGivenFields(testGrvItemPerson);
    }

    @Test
    @Transactional
    public void createGrvItemPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grvItemPersonRepository.findAll().size();

        // Create the GrvItemPerson with an existing ID
        grvItemPerson.setId(1L);
        GrvItemPersonDTO grvItemPersonDTO = grvItemPersonMapper.toDto(grvItemPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrvItemPersonMockMvc.perform(post("/api/grv-item-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrvItemPerson in the database
        List<GrvItemPerson> grvItemPersonList = grvItemPersonRepository.findAll();
        assertThat(grvItemPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeople() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList
        restGrvItemPersonMockMvc.perform(get("/api/grv-item-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grvItemPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].anotherLastName").value(hasItem(DEFAULT_ANOTHER_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDateString").value(hasItem(DEFAULT_START_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].endDateString").value(hasItem(DEFAULT_END_DATE_STRING.toString())));
    }

    @Test
    @Transactional
    public void getGrvItemPerson() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get the grvItemPerson
        restGrvItemPersonMockMvc.perform(get("/api/grv-item-people/{id}", grvItemPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grvItemPerson.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.anotherLastName").value(DEFAULT_ANOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.startDateString").value(DEFAULT_START_DATE_STRING.toString()))
            .andExpect(jsonPath("$.endDateString").value(DEFAULT_END_DATE_STRING.toString()));
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where firstName equals to DEFAULT_FIRST_NAME
        defaultGrvItemPersonShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the grvItemPersonList where firstName equals to UPDATED_FIRST_NAME
        defaultGrvItemPersonShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultGrvItemPersonShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the grvItemPersonList where firstName equals to UPDATED_FIRST_NAME
        defaultGrvItemPersonShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where firstName is not null
        defaultGrvItemPersonShouldBeFound("firstName.specified=true");

        // Get all the grvItemPersonList where firstName is null
        defaultGrvItemPersonShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where lastName equals to DEFAULT_LAST_NAME
        defaultGrvItemPersonShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the grvItemPersonList where lastName equals to UPDATED_LAST_NAME
        defaultGrvItemPersonShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultGrvItemPersonShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the grvItemPersonList where lastName equals to UPDATED_LAST_NAME
        defaultGrvItemPersonShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where lastName is not null
        defaultGrvItemPersonShouldBeFound("lastName.specified=true");

        // Get all the grvItemPersonList where lastName is null
        defaultGrvItemPersonShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByAnotherLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where anotherLastName equals to DEFAULT_ANOTHER_LAST_NAME
        defaultGrvItemPersonShouldBeFound("anotherLastName.equals=" + DEFAULT_ANOTHER_LAST_NAME);

        // Get all the grvItemPersonList where anotherLastName equals to UPDATED_ANOTHER_LAST_NAME
        defaultGrvItemPersonShouldNotBeFound("anotherLastName.equals=" + UPDATED_ANOTHER_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByAnotherLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where anotherLastName in DEFAULT_ANOTHER_LAST_NAME or UPDATED_ANOTHER_LAST_NAME
        defaultGrvItemPersonShouldBeFound("anotherLastName.in=" + DEFAULT_ANOTHER_LAST_NAME + "," + UPDATED_ANOTHER_LAST_NAME);

        // Get all the grvItemPersonList where anotherLastName equals to UPDATED_ANOTHER_LAST_NAME
        defaultGrvItemPersonShouldNotBeFound("anotherLastName.in=" + UPDATED_ANOTHER_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByAnotherLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where anotherLastName is not null
        defaultGrvItemPersonShouldBeFound("anotherLastName.specified=true");

        // Get all the grvItemPersonList where anotherLastName is null
        defaultGrvItemPersonShouldNotBeFound("anotherLastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByStartDateStringIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where startDateString equals to DEFAULT_START_DATE_STRING
        defaultGrvItemPersonShouldBeFound("startDateString.equals=" + DEFAULT_START_DATE_STRING);

        // Get all the grvItemPersonList where startDateString equals to UPDATED_START_DATE_STRING
        defaultGrvItemPersonShouldNotBeFound("startDateString.equals=" + UPDATED_START_DATE_STRING);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByStartDateStringIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where startDateString in DEFAULT_START_DATE_STRING or UPDATED_START_DATE_STRING
        defaultGrvItemPersonShouldBeFound("startDateString.in=" + DEFAULT_START_DATE_STRING + "," + UPDATED_START_DATE_STRING);

        // Get all the grvItemPersonList where startDateString equals to UPDATED_START_DATE_STRING
        defaultGrvItemPersonShouldNotBeFound("startDateString.in=" + UPDATED_START_DATE_STRING);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByStartDateStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where startDateString is not null
        defaultGrvItemPersonShouldBeFound("startDateString.specified=true");

        // Get all the grvItemPersonList where startDateString is null
        defaultGrvItemPersonShouldNotBeFound("startDateString.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByEndDateStringIsEqualToSomething() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where endDateString equals to DEFAULT_END_DATE_STRING
        defaultGrvItemPersonShouldBeFound("endDateString.equals=" + DEFAULT_END_DATE_STRING);

        // Get all the grvItemPersonList where endDateString equals to UPDATED_END_DATE_STRING
        defaultGrvItemPersonShouldNotBeFound("endDateString.equals=" + UPDATED_END_DATE_STRING);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByEndDateStringIsInShouldWork() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where endDateString in DEFAULT_END_DATE_STRING or UPDATED_END_DATE_STRING
        defaultGrvItemPersonShouldBeFound("endDateString.in=" + DEFAULT_END_DATE_STRING + "," + UPDATED_END_DATE_STRING);

        // Get all the grvItemPersonList where endDateString equals to UPDATED_END_DATE_STRING
        defaultGrvItemPersonShouldNotBeFound("endDateString.in=" + UPDATED_END_DATE_STRING);
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByEndDateStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);

        // Get all the grvItemPersonList where endDateString is not null
        defaultGrvItemPersonShouldBeFound("endDateString.specified=true");

        // Get all the grvItemPersonList where endDateString is null
        defaultGrvItemPersonShouldNotBeFound("endDateString.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrvItemPeopleByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        GrvItem item = GrvItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        grvItemPerson.setItem(item);
        grvItemPersonRepository.saveAndFlush(grvItemPerson);
        Long itemId = item.getId();

        // Get all the grvItemPersonList where item equals to itemId
        defaultGrvItemPersonShouldBeFound("itemId.equals=" + itemId);

        // Get all the grvItemPersonList where item equals to itemId + 1
        defaultGrvItemPersonShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGrvItemPersonShouldBeFound(String filter) throws Exception {
        restGrvItemPersonMockMvc.perform(get("/api/grv-item-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grvItemPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].anotherLastName").value(hasItem(DEFAULT_ANOTHER_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDateString").value(hasItem(DEFAULT_START_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].endDateString").value(hasItem(DEFAULT_END_DATE_STRING.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGrvItemPersonShouldNotBeFound(String filter) throws Exception {
        restGrvItemPersonMockMvc.perform(get("/api/grv-item-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingGrvItemPerson() throws Exception {
        // Get the grvItemPerson
        restGrvItemPersonMockMvc.perform(get("/api/grv-item-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrvItemPerson() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);
        grvItemPersonSearchRepository.save(grvItemPerson);
        int databaseSizeBeforeUpdate = grvItemPersonRepository.findAll().size();

        // Update the grvItemPerson
        GrvItemPerson updatedGrvItemPerson = grvItemPersonRepository.findOne(grvItemPerson.getId());
        // Disconnect from session so that the updates on updatedGrvItemPerson are not directly saved in db
        em.detach(updatedGrvItemPerson);
        updatedGrvItemPerson
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .anotherLastName(UPDATED_ANOTHER_LAST_NAME)
            .startDateString(UPDATED_START_DATE_STRING)
            .endDateString(UPDATED_END_DATE_STRING);
        GrvItemPersonDTO grvItemPersonDTO = grvItemPersonMapper.toDto(updatedGrvItemPerson);

        restGrvItemPersonMockMvc.perform(put("/api/grv-item-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemPersonDTO)))
            .andExpect(status().isOk());

        // Validate the GrvItemPerson in the database
        List<GrvItemPerson> grvItemPersonList = grvItemPersonRepository.findAll();
        assertThat(grvItemPersonList).hasSize(databaseSizeBeforeUpdate);
        GrvItemPerson testGrvItemPerson = grvItemPersonList.get(grvItemPersonList.size() - 1);
        assertThat(testGrvItemPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGrvItemPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testGrvItemPerson.getAnotherLastName()).isEqualTo(UPDATED_ANOTHER_LAST_NAME);
        assertThat(testGrvItemPerson.getStartDateString()).isEqualTo(UPDATED_START_DATE_STRING);
        assertThat(testGrvItemPerson.getEndDateString()).isEqualTo(UPDATED_END_DATE_STRING);

        // Validate the GrvItemPerson in Elasticsearch
        GrvItemPerson grvItemPersonEs = grvItemPersonSearchRepository.findOne(testGrvItemPerson.getId());
        assertThat(grvItemPersonEs).isEqualToIgnoringGivenFields(testGrvItemPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingGrvItemPerson() throws Exception {
        int databaseSizeBeforeUpdate = grvItemPersonRepository.findAll().size();

        // Create the GrvItemPerson
        GrvItemPersonDTO grvItemPersonDTO = grvItemPersonMapper.toDto(grvItemPerson);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrvItemPersonMockMvc.perform(put("/api/grv-item-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grvItemPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the GrvItemPerson in the database
        List<GrvItemPerson> grvItemPersonList = grvItemPersonRepository.findAll();
        assertThat(grvItemPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrvItemPerson() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);
        grvItemPersonSearchRepository.save(grvItemPerson);
        int databaseSizeBeforeDelete = grvItemPersonRepository.findAll().size();

        // Get the grvItemPerson
        restGrvItemPersonMockMvc.perform(delete("/api/grv-item-people/{id}", grvItemPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean grvItemPersonExistsInEs = grvItemPersonSearchRepository.exists(grvItemPerson.getId());
        assertThat(grvItemPersonExistsInEs).isFalse();

        // Validate the database is empty
        List<GrvItemPerson> grvItemPersonList = grvItemPersonRepository.findAll();
        assertThat(grvItemPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGrvItemPerson() throws Exception {
        // Initialize the database
        grvItemPersonRepository.saveAndFlush(grvItemPerson);
        grvItemPersonSearchRepository.save(grvItemPerson);

        // Search the grvItemPerson
        restGrvItemPersonMockMvc.perform(get("/api/_search/grv-item-people?query=id:" + grvItemPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grvItemPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].anotherLastName").value(hasItem(DEFAULT_ANOTHER_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDateString").value(hasItem(DEFAULT_START_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].endDateString").value(hasItem(DEFAULT_END_DATE_STRING.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrvItemPerson.class);
        GrvItemPerson grvItemPerson1 = new GrvItemPerson();
        grvItemPerson1.setId(1L);
        GrvItemPerson grvItemPerson2 = new GrvItemPerson();
        grvItemPerson2.setId(grvItemPerson1.getId());
        assertThat(grvItemPerson1).isEqualTo(grvItemPerson2);
        grvItemPerson2.setId(2L);
        assertThat(grvItemPerson1).isNotEqualTo(grvItemPerson2);
        grvItemPerson1.setId(null);
        assertThat(grvItemPerson1).isNotEqualTo(grvItemPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrvItemPersonDTO.class);
        GrvItemPersonDTO grvItemPersonDTO1 = new GrvItemPersonDTO();
        grvItemPersonDTO1.setId(1L);
        GrvItemPersonDTO grvItemPersonDTO2 = new GrvItemPersonDTO();
        assertThat(grvItemPersonDTO1).isNotEqualTo(grvItemPersonDTO2);
        grvItemPersonDTO2.setId(grvItemPersonDTO1.getId());
        assertThat(grvItemPersonDTO1).isEqualTo(grvItemPersonDTO2);
        grvItemPersonDTO2.setId(2L);
        assertThat(grvItemPersonDTO1).isNotEqualTo(grvItemPersonDTO2);
        grvItemPersonDTO1.setId(null);
        assertThat(grvItemPersonDTO1).isNotEqualTo(grvItemPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grvItemPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grvItemPersonMapper.fromId(null)).isNull();
    }
}
