package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.TipoEspacio;
import io.github.jhipster.newoapp13.repository.TipoEspacioRepository;
import io.github.jhipster.newoapp13.repository.search.TipoEspacioSearchRepository;
import io.github.jhipster.newoapp13.service.TipoEspacioService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.TipoEspacioCriteria;
import io.github.jhipster.newoapp13.service.TipoEspacioQueryService;

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
 * Integration tests for the {@link TipoEspacioResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class TipoEspacioResourceIT {

    private static final String DEFAULT_TIPO_ESPACIO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ESPACIO = "BBBBBBBBBB";

    @Autowired
    private TipoEspacioRepository tipoEspacioRepository;

    @Autowired
    private TipoEspacioService tipoEspacioService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.TipoEspacioSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoEspacioSearchRepository mockTipoEspacioSearchRepository;

    @Autowired
    private TipoEspacioQueryService tipoEspacioQueryService;

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

    private MockMvc restTipoEspacioMockMvc;

    private TipoEspacio tipoEspacio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoEspacioResource tipoEspacioResource = new TipoEspacioResource(tipoEspacioService, tipoEspacioQueryService);
        this.restTipoEspacioMockMvc = MockMvcBuilders.standaloneSetup(tipoEspacioResource)
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
    public static TipoEspacio createEntity(EntityManager em) {
        TipoEspacio tipoEspacio = new TipoEspacio()
            .tipoEspacio(DEFAULT_TIPO_ESPACIO);
        return tipoEspacio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEspacio createUpdatedEntity(EntityManager em) {
        TipoEspacio tipoEspacio = new TipoEspacio()
            .tipoEspacio(UPDATED_TIPO_ESPACIO);
        return tipoEspacio;
    }

    @BeforeEach
    public void initTest() {
        tipoEspacio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEspacio() throws Exception {
        int databaseSizeBeforeCreate = tipoEspacioRepository.findAll().size();

        // Create the TipoEspacio
        restTipoEspacioMockMvc.perform(post("/api/tipo-espacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEspacio)))
            .andExpect(status().isCreated());

        // Validate the TipoEspacio in the database
        List<TipoEspacio> tipoEspacioList = tipoEspacioRepository.findAll();
        assertThat(tipoEspacioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEspacio testTipoEspacio = tipoEspacioList.get(tipoEspacioList.size() - 1);
        assertThat(testTipoEspacio.getTipoEspacio()).isEqualTo(DEFAULT_TIPO_ESPACIO);

        // Validate the TipoEspacio in Elasticsearch
        verify(mockTipoEspacioSearchRepository, times(1)).save(testTipoEspacio);
    }

    @Test
    @Transactional
    public void createTipoEspacioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEspacioRepository.findAll().size();

        // Create the TipoEspacio with an existing ID
        tipoEspacio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEspacioMockMvc.perform(post("/api/tipo-espacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEspacio)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEspacio in the database
        List<TipoEspacio> tipoEspacioList = tipoEspacioRepository.findAll();
        assertThat(tipoEspacioList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoEspacio in Elasticsearch
        verify(mockTipoEspacioSearchRepository, times(0)).save(tipoEspacio);
    }


    @Test
    @Transactional
    public void checkTipoEspacioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEspacioRepository.findAll().size();
        // set the field null
        tipoEspacio.setTipoEspacio(null);

        // Create the TipoEspacio, which fails.

        restTipoEspacioMockMvc.perform(post("/api/tipo-espacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEspacio)))
            .andExpect(status().isBadRequest());

        List<TipoEspacio> tipoEspacioList = tipoEspacioRepository.findAll();
        assertThat(tipoEspacioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEspacios() throws Exception {
        // Initialize the database
        tipoEspacioRepository.saveAndFlush(tipoEspacio);

        // Get all the tipoEspacioList
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEspacio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEspacio").value(hasItem(DEFAULT_TIPO_ESPACIO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoEspacio() throws Exception {
        // Initialize the database
        tipoEspacioRepository.saveAndFlush(tipoEspacio);

        // Get the tipoEspacio
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios/{id}", tipoEspacio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEspacio.getId().intValue()))
            .andExpect(jsonPath("$.tipoEspacio").value(DEFAULT_TIPO_ESPACIO.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoEspaciosByTipoEspacioIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoEspacioRepository.saveAndFlush(tipoEspacio);

        // Get all the tipoEspacioList where tipoEspacio equals to DEFAULT_TIPO_ESPACIO
        defaultTipoEspacioShouldBeFound("tipoEspacio.equals=" + DEFAULT_TIPO_ESPACIO);

        // Get all the tipoEspacioList where tipoEspacio equals to UPDATED_TIPO_ESPACIO
        defaultTipoEspacioShouldNotBeFound("tipoEspacio.equals=" + UPDATED_TIPO_ESPACIO);
    }

    @Test
    @Transactional
    public void getAllTipoEspaciosByTipoEspacioIsInShouldWork() throws Exception {
        // Initialize the database
        tipoEspacioRepository.saveAndFlush(tipoEspacio);

        // Get all the tipoEspacioList where tipoEspacio in DEFAULT_TIPO_ESPACIO or UPDATED_TIPO_ESPACIO
        defaultTipoEspacioShouldBeFound("tipoEspacio.in=" + DEFAULT_TIPO_ESPACIO + "," + UPDATED_TIPO_ESPACIO);

        // Get all the tipoEspacioList where tipoEspacio equals to UPDATED_TIPO_ESPACIO
        defaultTipoEspacioShouldNotBeFound("tipoEspacio.in=" + UPDATED_TIPO_ESPACIO);
    }

    @Test
    @Transactional
    public void getAllTipoEspaciosByTipoEspacioIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoEspacioRepository.saveAndFlush(tipoEspacio);

        // Get all the tipoEspacioList where tipoEspacio is not null
        defaultTipoEspacioShouldBeFound("tipoEspacio.specified=true");

        // Get all the tipoEspacioList where tipoEspacio is null
        defaultTipoEspacioShouldNotBeFound("tipoEspacio.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoEspacioShouldBeFound(String filter) throws Exception {
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEspacio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEspacio").value(hasItem(DEFAULT_TIPO_ESPACIO)));

        // Check, that the count call also returns 1
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoEspacioShouldNotBeFound(String filter) throws Exception {
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoEspacio() throws Exception {
        // Get the tipoEspacio
        restTipoEspacioMockMvc.perform(get("/api/tipo-espacios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEspacio() throws Exception {
        // Initialize the database
        tipoEspacioService.save(tipoEspacio);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTipoEspacioSearchRepository);

        int databaseSizeBeforeUpdate = tipoEspacioRepository.findAll().size();

        // Update the tipoEspacio
        TipoEspacio updatedTipoEspacio = tipoEspacioRepository.findById(tipoEspacio.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEspacio are not directly saved in db
        em.detach(updatedTipoEspacio);
        updatedTipoEspacio
            .tipoEspacio(UPDATED_TIPO_ESPACIO);

        restTipoEspacioMockMvc.perform(put("/api/tipo-espacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoEspacio)))
            .andExpect(status().isOk());

        // Validate the TipoEspacio in the database
        List<TipoEspacio> tipoEspacioList = tipoEspacioRepository.findAll();
        assertThat(tipoEspacioList).hasSize(databaseSizeBeforeUpdate);
        TipoEspacio testTipoEspacio = tipoEspacioList.get(tipoEspacioList.size() - 1);
        assertThat(testTipoEspacio.getTipoEspacio()).isEqualTo(UPDATED_TIPO_ESPACIO);

        // Validate the TipoEspacio in Elasticsearch
        verify(mockTipoEspacioSearchRepository, times(1)).save(testTipoEspacio);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEspacio() throws Exception {
        int databaseSizeBeforeUpdate = tipoEspacioRepository.findAll().size();

        // Create the TipoEspacio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEspacioMockMvc.perform(put("/api/tipo-espacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEspacio)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEspacio in the database
        List<TipoEspacio> tipoEspacioList = tipoEspacioRepository.findAll();
        assertThat(tipoEspacioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoEspacio in Elasticsearch
        verify(mockTipoEspacioSearchRepository, times(0)).save(tipoEspacio);
    }

    @Test
    @Transactional
    public void deleteTipoEspacio() throws Exception {
        // Initialize the database
        tipoEspacioService.save(tipoEspacio);

        int databaseSizeBeforeDelete = tipoEspacioRepository.findAll().size();

        // Delete the tipoEspacio
        restTipoEspacioMockMvc.perform(delete("/api/tipo-espacios/{id}", tipoEspacio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEspacio> tipoEspacioList = tipoEspacioRepository.findAll();
        assertThat(tipoEspacioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoEspacio in Elasticsearch
        verify(mockTipoEspacioSearchRepository, times(1)).deleteById(tipoEspacio.getId());
    }

    @Test
    @Transactional
    public void searchTipoEspacio() throws Exception {
        // Initialize the database
        tipoEspacioService.save(tipoEspacio);
        when(mockTipoEspacioSearchRepository.search(queryStringQuery("id:" + tipoEspacio.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoEspacio), PageRequest.of(0, 1), 1));
        // Search the tipoEspacio
        restTipoEspacioMockMvc.perform(get("/api/_search/tipo-espacios?query=id:" + tipoEspacio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEspacio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEspacio").value(hasItem(DEFAULT_TIPO_ESPACIO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEspacio.class);
        TipoEspacio tipoEspacio1 = new TipoEspacio();
        tipoEspacio1.setId(1L);
        TipoEspacio tipoEspacio2 = new TipoEspacio();
        tipoEspacio2.setId(tipoEspacio1.getId());
        assertThat(tipoEspacio1).isEqualTo(tipoEspacio2);
        tipoEspacio2.setId(2L);
        assertThat(tipoEspacio1).isNotEqualTo(tipoEspacio2);
        tipoEspacio1.setId(null);
        assertThat(tipoEspacio1).isNotEqualTo(tipoEspacio2);
    }
}
