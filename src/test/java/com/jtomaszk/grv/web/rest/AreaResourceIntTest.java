package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.Area;
import com.jtomaszk.grv.domain.Source;
import com.jtomaszk.grv.repository.AreaRepository;
import com.jtomaszk.grv.service.AreaService;
import com.jtomaszk.grv.service.dto.AreaDTO;
import com.jtomaszk.grv.service.mapper.AreaMapper;
import com.jtomaszk.grv.web.rest.errors.ExceptionTranslator;
import com.jtomaszk.grv.service.dto.AreaCriteria;
import com.jtomaszk.grv.service.AreaQueryService;

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
 * Test class for the AreaResource REST controller.
 *
 * @see AreaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class AreaResourceIntTest {

    private static final String DEFAULT_AREA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AREA_NAME = "BBBBBBBBBB";

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaQueryService areaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAreaMockMvc;

    private Area area;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AreaResource areaResource = new AreaResource(areaService, areaQueryService);
        this.restAreaMockMvc = MockMvcBuilders.standaloneSetup(areaResource)
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
    public static Area createEntity(EntityManager em) {
        Area area = new Area()
            .areaName(DEFAULT_AREA_NAME);
        return area;
    }

    @Before
    public void initTest() {
        area = createEntity(em);
    }

    @Test
    @Transactional
    public void createArea() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);
        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate + 1);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getAreaName()).isEqualTo(DEFAULT_AREA_NAME);
    }

    @Test
    @Transactional
    public void createAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaRepository.findAll().size();

        // Create the Area with an existing ID
        area.setId(1L);
        AreaDTO areaDTO = areaMapper.toDto(area);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAreaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaRepository.findAll().size();
        // set the field null
        area.setAreaName(null);

        // Create the Area, which fails.
        AreaDTO areaDTO = areaMapper.toDto(area);

        restAreaMockMvc.perform(post("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isBadRequest());

        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAreas() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaName").value(hasItem(DEFAULT_AREA_NAME.toString())));
    }

    @Test
    @Transactional
    public void getArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", area.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(area.getId().intValue()))
            .andExpect(jsonPath("$.areaName").value(DEFAULT_AREA_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllAreasByAreaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName equals to DEFAULT_AREA_NAME
        defaultAreaShouldBeFound("areaName.equals=" + DEFAULT_AREA_NAME);

        // Get all the areaList where areaName equals to UPDATED_AREA_NAME
        defaultAreaShouldNotBeFound("areaName.equals=" + UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    public void getAllAreasByAreaNameIsInShouldWork() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName in DEFAULT_AREA_NAME or UPDATED_AREA_NAME
        defaultAreaShouldBeFound("areaName.in=" + DEFAULT_AREA_NAME + "," + UPDATED_AREA_NAME);

        // Get all the areaList where areaName equals to UPDATED_AREA_NAME
        defaultAreaShouldNotBeFound("areaName.in=" + UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    public void getAllAreasByAreaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);

        // Get all the areaList where areaName is not null
        defaultAreaShouldBeFound("areaName.specified=true");

        // Get all the areaList where areaName is null
        defaultAreaShouldNotBeFound("areaName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAreasBySourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        Source sources = SourceResourceIntTest.createEntity(em);
        em.persist(sources);
        em.flush();
        area.addSources(sources);
        areaRepository.saveAndFlush(area);
        Long sourcesId = sources.getId();

        // Get all the areaList where sources equals to sourcesId
        defaultAreaShouldBeFound("sourcesId.equals=" + sourcesId);

        // Get all the areaList where sources equals to sourcesId + 1
        defaultAreaShouldNotBeFound("sourcesId.equals=" + (sourcesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAreaShouldBeFound(String filter) throws Exception {
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(area.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaName").value(hasItem(DEFAULT_AREA_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAreaShouldNotBeFound(String filter) throws Exception {
        restAreaMockMvc.perform(get("/api/areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingArea() throws Exception {
        // Get the area
        restAreaMockMvc.perform(get("/api/areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Update the area
        Area updatedArea = areaRepository.findOne(area.getId());
        // Disconnect from session so that the updates on updatedArea are not directly saved in db
        em.detach(updatedArea);
        updatedArea
            .areaName(UPDATED_AREA_NAME);
        AreaDTO areaDTO = areaMapper.toDto(updatedArea);

        restAreaMockMvc.perform(put("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isOk());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate);
        Area testArea = areaList.get(areaList.size() - 1);
        assertThat(testArea.getAreaName()).isEqualTo(UPDATED_AREA_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingArea() throws Exception {
        int databaseSizeBeforeUpdate = areaRepository.findAll().size();

        // Create the Area
        AreaDTO areaDTO = areaMapper.toDto(area);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAreaMockMvc.perform(put("/api/areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaDTO)))
            .andExpect(status().isCreated());

        // Validate the Area in the database
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArea() throws Exception {
        // Initialize the database
        areaRepository.saveAndFlush(area);
        int databaseSizeBeforeDelete = areaRepository.findAll().size();

        // Get the area
        restAreaMockMvc.perform(delete("/api/areas/{id}", area.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Area> areaList = areaRepository.findAll();
        assertThat(areaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Area.class);
        Area area1 = new Area();
        area1.setId(1L);
        Area area2 = new Area();
        area2.setId(area1.getId());
        assertThat(area1).isEqualTo(area2);
        area2.setId(2L);
        assertThat(area1).isNotEqualTo(area2);
        area1.setId(null);
        assertThat(area1).isNotEqualTo(area2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaDTO.class);
        AreaDTO areaDTO1 = new AreaDTO();
        areaDTO1.setId(1L);
        AreaDTO areaDTO2 = new AreaDTO();
        assertThat(areaDTO1).isNotEqualTo(areaDTO2);
        areaDTO2.setId(areaDTO1.getId());
        assertThat(areaDTO1).isEqualTo(areaDTO2);
        areaDTO2.setId(2L);
        assertThat(areaDTO1).isNotEqualTo(areaDTO2);
        areaDTO1.setId(null);
        assertThat(areaDTO1).isNotEqualTo(areaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(areaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(areaMapper.fromId(null)).isNull();
    }
}
