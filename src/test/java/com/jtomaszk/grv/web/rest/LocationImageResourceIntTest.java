package com.jtomaszk.grv.web.rest;

import com.jtomaszk.grv.GrvApplicationApp;

import com.jtomaszk.grv.domain.LocationImage;
import com.jtomaszk.grv.domain.Location;
import com.jtomaszk.grv.repository.LocationImageRepository;
import com.jtomaszk.grv.service.LocationImageService;
import com.jtomaszk.grv.repository.search.LocationImageSearchRepository;
import com.jtomaszk.grv.service.dto.LocationImageDTO;
import com.jtomaszk.grv.service.mapper.LocationImageMapper;
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
 * Test class for the LocationImageResource REST controller.
 *
 * @see LocationImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrvApplicationApp.class)
public class LocationImageResourceIntTest {

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private LocationImageRepository locationImageRepository;

    @Autowired
    private LocationImageMapper locationImageMapper;

    @Autowired
    private LocationImageService locationImageService;

    @Autowired
    private LocationImageSearchRepository locationImageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocationImageMockMvc;

    private LocationImage locationImage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationImageResource locationImageResource = new LocationImageResource(locationImageService);
        this.restLocationImageMockMvc = MockMvcBuilders.standaloneSetup(locationImageResource)
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
    public static LocationImage createEntity(EntityManager em) {
        LocationImage locationImage = new LocationImage()
            .createdDate(DEFAULT_CREATED_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        locationImage.setLocation(location);
        return locationImage;
    }

    @Before
    public void initTest() {
        locationImageSearchRepository.deleteAll();
        locationImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocationImage() throws Exception {
        int databaseSizeBeforeCreate = locationImageRepository.findAll().size();

        // Create the LocationImage
        LocationImageDTO locationImageDTO = locationImageMapper.toDto(locationImage);
        restLocationImageMockMvc.perform(post("/api/location-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationImageDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationImage in the database
        List<LocationImage> locationImageList = locationImageRepository.findAll();
        assertThat(locationImageList).hasSize(databaseSizeBeforeCreate + 1);
        LocationImage testLocationImage = locationImageList.get(locationImageList.size() - 1);
        assertThat(testLocationImage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLocationImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testLocationImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);

        // Validate the LocationImage in Elasticsearch
        LocationImage locationImageEs = locationImageSearchRepository.findOne(testLocationImage.getId());
        assertThat(locationImageEs).isEqualToIgnoringGivenFields(testLocationImage);
    }

    @Test
    @Transactional
    public void createLocationImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationImageRepository.findAll().size();

        // Create the LocationImage with an existing ID
        locationImage.setId(1L);
        LocationImageDTO locationImageDTO = locationImageMapper.toDto(locationImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationImageMockMvc.perform(post("/api/location-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationImage in the database
        List<LocationImage> locationImageList = locationImageRepository.findAll();
        assertThat(locationImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationImageRepository.findAll().size();
        // set the field null
        locationImage.setCreatedDate(null);

        // Create the LocationImage, which fails.
        LocationImageDTO locationImageDTO = locationImageMapper.toDto(locationImage);

        restLocationImageMockMvc.perform(post("/api/location-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationImageDTO)))
            .andExpect(status().isBadRequest());

        List<LocationImage> locationImageList = locationImageRepository.findAll();
        assertThat(locationImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocationImages() throws Exception {
        // Initialize the database
        locationImageRepository.saveAndFlush(locationImage);

        // Get all the locationImageList
        restLocationImageMockMvc.perform(get("/api/location-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getLocationImage() throws Exception {
        // Initialize the database
        locationImageRepository.saveAndFlush(locationImage);

        // Get the locationImage
        restLocationImageMockMvc.perform(get("/api/location-images/{id}", locationImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locationImage.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingLocationImage() throws Exception {
        // Get the locationImage
        restLocationImageMockMvc.perform(get("/api/location-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocationImage() throws Exception {
        // Initialize the database
        locationImageRepository.saveAndFlush(locationImage);
        locationImageSearchRepository.save(locationImage);
        int databaseSizeBeforeUpdate = locationImageRepository.findAll().size();

        // Update the locationImage
        LocationImage updatedLocationImage = locationImageRepository.findOne(locationImage.getId());
        // Disconnect from session so that the updates on updatedLocationImage are not directly saved in db
        em.detach(updatedLocationImage);
        updatedLocationImage
            .createdDate(UPDATED_CREATED_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        LocationImageDTO locationImageDTO = locationImageMapper.toDto(updatedLocationImage);

        restLocationImageMockMvc.perform(put("/api/location-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationImageDTO)))
            .andExpect(status().isOk());

        // Validate the LocationImage in the database
        List<LocationImage> locationImageList = locationImageRepository.findAll();
        assertThat(locationImageList).hasSize(databaseSizeBeforeUpdate);
        LocationImage testLocationImage = locationImageList.get(locationImageList.size() - 1);
        assertThat(testLocationImage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLocationImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLocationImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);

        // Validate the LocationImage in Elasticsearch
        LocationImage locationImageEs = locationImageSearchRepository.findOne(testLocationImage.getId());
        assertThat(locationImageEs).isEqualToIgnoringGivenFields(testLocationImage);
    }

    @Test
    @Transactional
    public void updateNonExistingLocationImage() throws Exception {
        int databaseSizeBeforeUpdate = locationImageRepository.findAll().size();

        // Create the LocationImage
        LocationImageDTO locationImageDTO = locationImageMapper.toDto(locationImage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocationImageMockMvc.perform(put("/api/location-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationImageDTO)))
            .andExpect(status().isCreated());

        // Validate the LocationImage in the database
        List<LocationImage> locationImageList = locationImageRepository.findAll();
        assertThat(locationImageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocationImage() throws Exception {
        // Initialize the database
        locationImageRepository.saveAndFlush(locationImage);
        locationImageSearchRepository.save(locationImage);
        int databaseSizeBeforeDelete = locationImageRepository.findAll().size();

        // Get the locationImage
        restLocationImageMockMvc.perform(delete("/api/location-images/{id}", locationImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean locationImageExistsInEs = locationImageSearchRepository.exists(locationImage.getId());
        assertThat(locationImageExistsInEs).isFalse();

        // Validate the database is empty
        List<LocationImage> locationImageList = locationImageRepository.findAll();
        assertThat(locationImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocationImage() throws Exception {
        // Initialize the database
        locationImageRepository.saveAndFlush(locationImage);
        locationImageSearchRepository.save(locationImage);

        // Search the locationImage
        restLocationImageMockMvc.perform(get("/api/_search/location-images?query=id:" + locationImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationImage.class);
        LocationImage locationImage1 = new LocationImage();
        locationImage1.setId(1L);
        LocationImage locationImage2 = new LocationImage();
        locationImage2.setId(locationImage1.getId());
        assertThat(locationImage1).isEqualTo(locationImage2);
        locationImage2.setId(2L);
        assertThat(locationImage1).isNotEqualTo(locationImage2);
        locationImage1.setId(null);
        assertThat(locationImage1).isNotEqualTo(locationImage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationImageDTO.class);
        LocationImageDTO locationImageDTO1 = new LocationImageDTO();
        locationImageDTO1.setId(1L);
        LocationImageDTO locationImageDTO2 = new LocationImageDTO();
        assertThat(locationImageDTO1).isNotEqualTo(locationImageDTO2);
        locationImageDTO2.setId(locationImageDTO1.getId());
        assertThat(locationImageDTO1).isEqualTo(locationImageDTO2);
        locationImageDTO2.setId(2L);
        assertThat(locationImageDTO1).isNotEqualTo(locationImageDTO2);
        locationImageDTO1.setId(null);
        assertThat(locationImageDTO1).isNotEqualTo(locationImageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationImageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationImageMapper.fromId(null)).isNull();
    }
}
