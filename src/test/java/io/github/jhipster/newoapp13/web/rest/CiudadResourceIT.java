package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Ciudad;
import io.github.jhipster.newoapp13.domain.Pais;
import io.github.jhipster.newoapp13.repository.CiudadRepository;
import io.github.jhipster.newoapp13.repository.search.CiudadSearchRepository;
import io.github.jhipster.newoapp13.service.CiudadService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.CiudadCriteria;
import io.github.jhipster.newoapp13.service.CiudadQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.newoapp13.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CiudadResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class CiudadResourceIT {

    private static final String DEFAULT_NOMBRE_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CIUDAD = "BBBBBBBBBB";

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private CiudadService ciudadService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.CiudadSearchRepositoryMockConfiguration
     */
    @Autowired
    private CiudadSearchRepository mockCiudadSearchRepository;

    @Autowired
    private CiudadQueryService ciudadQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCiudadMockMvc;

    private Ciudad ciudad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiudadResource ciudadResource = new CiudadResource(ciudadService, ciudadQueryService);
        this.restCiudadMockMvc = MockMvcBuilders.standaloneSetup(ciudadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ciudad createEntity(EntityManager em) {
        Ciudad ciudad = new Ciudad()
            .nombreCiudad(DEFAULT_NOMBRE_CIUDAD);
        return ciudad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ciudad createUpdatedEntity(EntityManager em) {
        Ciudad ciudad = new Ciudad()
            .nombreCiudad(UPDATED_NOMBRE_CIUDAD);
        return ciudad;
    }

    @BeforeEach
    public void initTest() {
        ciudad = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiudad() throws Exception {
        int databaseSizeBeforeCreate = ciudadRepository.findAll().size();

        // Create the Ciudad
        restCiudadMockMvc.perform(post("/api/ciudads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isCreated());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeCreate + 1);
        Ciudad testCiudad = ciudadList.get(ciudadList.size() - 1);
        assertThat(testCiudad.getNombreCiudad()).isEqualTo(DEFAULT_NOMBRE_CIUDAD);

        // Validate the Ciudad in Elasticsearch
        verify(mockCiudadSearchRepository, times(1)).save(testCiudad);
    }

    @Test
    @Transactional
    public void createCiudadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciudadRepository.findAll().size();

        // Create the Ciudad with an existing ID
        ciudad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiudadMockMvc.perform(post("/api/ciudads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isBadRequest());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ciudad in Elasticsearch
        verify(mockCiudadSearchRepository, times(0)).save(ciudad);
    }


    @Test
    @Transactional
    public void checkNombreCiudadIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciudadRepository.findAll().size();
        // set the field null
        ciudad.setNombreCiudad(null);

        // Create the Ciudad, which fails.

        restCiudadMockMvc.perform(post("/api/ciudads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isBadRequest());

        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiudads() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList
        restCiudadMockMvc.perform(get("/api/ciudads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciudad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCiudad").value(hasItem(DEFAULT_NOMBRE_CIUDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getCiudad() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get the ciudad
        restCiudadMockMvc.perform(get("/api/ciudads/{id}", ciudad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciudad.getId().intValue()))
            .andExpect(jsonPath("$.nombreCiudad").value(DEFAULT_NOMBRE_CIUDAD.toString()));
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsEqualToSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad equals to DEFAULT_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.equals=" + DEFAULT_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad equals to UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.equals=" + UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsInShouldWork() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad in DEFAULT_NOMBRE_CIUDAD or UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.in=" + DEFAULT_NOMBRE_CIUDAD + "," + UPDATED_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad equals to UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.in=" + UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad is not null
        defaultCiudadShouldBeFound("nombreCiudad.specified=true");

        // Get all the ciudadList where nombreCiudad is null
        defaultCiudadShouldNotBeFound("nombreCiudad.specified=false");
    }

    @Test
    @Transactional
    public void getAllCiudadsByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);
        Pais pais = PaisResourceIT.createEntity(em);
        em.persist(pais);
        em.flush();
        ciudad.setPais(pais);
        ciudadRepository.saveAndFlush(ciudad);
        Long paisId = pais.getId();

        // Get all the ciudadList where pais equals to paisId
        defaultCiudadShouldBeFound("paisId.equals=" + paisId);

        // Get all the ciudadList where pais equals to paisId + 1
        defaultCiudadShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCiudadShouldBeFound(String filter) throws Exception {
        restCiudadMockMvc.perform(get("/api/ciudads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciudad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCiudad").value(hasItem(DEFAULT_NOMBRE_CIUDAD)));

        // Check, that the count call also returns 1
        restCiudadMockMvc.perform(get("/api/ciudads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCiudadShouldNotBeFound(String filter) throws Exception {
        restCiudadMockMvc.perform(get("/api/ciudads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCiudadMockMvc.perform(get("/api/ciudads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCiudad() throws Exception {
        // Get the ciudad
        restCiudadMockMvc.perform(get("/api/ciudads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiudad() throws Exception {
        // Initialize the database
        ciudadService.save(ciudad);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCiudadSearchRepository);

        int databaseSizeBeforeUpdate = ciudadRepository.findAll().size();

        // Update the ciudad
        Ciudad updatedCiudad = ciudadRepository.findById(ciudad.getId()).get();
        // Disconnect from session so that the updates on updatedCiudad are not directly saved in db
        em.detach(updatedCiudad);
        updatedCiudad
            .nombreCiudad(UPDATED_NOMBRE_CIUDAD);

        restCiudadMockMvc.perform(put("/api/ciudads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCiudad)))
            .andExpect(status().isOk());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeUpdate);
        Ciudad testCiudad = ciudadList.get(ciudadList.size() - 1);
        assertThat(testCiudad.getNombreCiudad()).isEqualTo(UPDATED_NOMBRE_CIUDAD);

        // Validate the Ciudad in Elasticsearch
        verify(mockCiudadSearchRepository, times(1)).save(testCiudad);
    }

    @Test
    @Transactional
    public void updateNonExistingCiudad() throws Exception {
        int databaseSizeBeforeUpdate = ciudadRepository.findAll().size();

        // Create the Ciudad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiudadMockMvc.perform(put("/api/ciudads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isBadRequest());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ciudad in Elasticsearch
        verify(mockCiudadSearchRepository, times(0)).save(ciudad);
    }

    @Test
    @Transactional
    public void deleteCiudad() throws Exception {
        // Initialize the database
        ciudadService.save(ciudad);

        int databaseSizeBeforeDelete = ciudadRepository.findAll().size();

        // Delete the ciudad
        restCiudadMockMvc.perform(delete("/api/ciudads/{id}", ciudad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ciudad in Elasticsearch
        verify(mockCiudadSearchRepository, times(1)).deleteById(ciudad.getId());
    }

    @Test
    @Transactional
    public void searchCiudad() throws Exception {
        // Initialize the database
        ciudadService.save(ciudad);
        when(mockCiudadSearchRepository.search(queryStringQuery("id:" + ciudad.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ciudad), PageRequest.of(0, 1), 1));
        // Search the ciudad
        restCiudadMockMvc.perform(get("/api/_search/ciudads?query=id:" + ciudad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciudad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCiudad").value(hasItem(DEFAULT_NOMBRE_CIUDAD)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ciudad.class);
        Ciudad ciudad1 = new Ciudad();
        ciudad1.setId(1L);
        Ciudad ciudad2 = new Ciudad();
        ciudad2.setId(ciudad1.getId());
        assertThat(ciudad1).isEqualTo(ciudad2);
        ciudad2.setId(2L);
        assertThat(ciudad1).isNotEqualTo(ciudad2);
        ciudad1.setId(null);
        assertThat(ciudad1).isNotEqualTo(ciudad2);
    }
}
