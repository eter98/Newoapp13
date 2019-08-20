package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.TipoRecurso;
import io.github.jhipster.newoapp13.repository.TipoRecursoRepository;
import io.github.jhipster.newoapp13.repository.search.TipoRecursoSearchRepository;
import io.github.jhipster.newoapp13.service.TipoRecursoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.TipoRecursoCriteria;
import io.github.jhipster.newoapp13.service.TipoRecursoQueryService;

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
 * Integration tests for the {@link TipoRecursoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class TipoRecursoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoRecursoRepository tipoRecursoRepository;

    @Autowired
    private TipoRecursoService tipoRecursoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.TipoRecursoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoRecursoSearchRepository mockTipoRecursoSearchRepository;

    @Autowired
    private TipoRecursoQueryService tipoRecursoQueryService;

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

    private MockMvc restTipoRecursoMockMvc;

    private TipoRecurso tipoRecurso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoRecursoResource tipoRecursoResource = new TipoRecursoResource(tipoRecursoService, tipoRecursoQueryService);
        this.restTipoRecursoMockMvc = MockMvcBuilders.standaloneSetup(tipoRecursoResource)
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
    public static TipoRecurso createEntity(EntityManager em) {
        TipoRecurso tipoRecurso = new TipoRecurso()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecurso createUpdatedEntity(EntityManager em) {
        TipoRecurso tipoRecurso = new TipoRecurso()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        return tipoRecurso;
    }

    @BeforeEach
    public void initTest() {
        tipoRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRecurso() throws Exception {
        int databaseSizeBeforeCreate = tipoRecursoRepository.findAll().size();

        // Create the TipoRecurso
        restTipoRecursoMockMvc.perform(post("/api/tipo-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isCreated());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoRecurso.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the TipoRecurso in Elasticsearch
        verify(mockTipoRecursoSearchRepository, times(1)).save(testTipoRecurso);
    }

    @Test
    @Transactional
    public void createTipoRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRecursoRepository.findAll().size();

        // Create the TipoRecurso with an existing ID
        tipoRecurso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRecursoMockMvc.perform(post("/api/tipo-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoRecurso in Elasticsearch
        verify(mockTipoRecursoSearchRepository, times(0)).save(tipoRecurso);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRecursoRepository.findAll().size();
        // set the field null
        tipoRecurso.setNombre(null);

        // Create the TipoRecurso, which fails.

        restTipoRecursoMockMvc.perform(post("/api/tipo-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRecursoRepository.findAll().size();
        // set the field null
        tipoRecurso.setDescripcion(null);

        // Create the TipoRecurso, which fails.

        restTipoRecursoMockMvc.perform(post("/api/tipo-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoRecursos() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get the tipoRecurso
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/{id}", tipoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRecurso.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where nombre equals to DEFAULT_NOMBRE
        defaultTipoRecursoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tipoRecursoList where nombre equals to UPDATED_NOMBRE
        defaultTipoRecursoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTipoRecursoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tipoRecursoList where nombre equals to UPDATED_NOMBRE
        defaultTipoRecursoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where nombre is not null
        defaultTipoRecursoShouldBeFound("nombre.specified=true");

        // Get all the tipoRecursoList where nombre is null
        defaultTipoRecursoShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTipoRecursoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoRecursoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoRecursoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTipoRecursoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tipoRecursoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoRecursoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoRecursosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRecursoRepository.saveAndFlush(tipoRecurso);

        // Get all the tipoRecursoList where descripcion is not null
        defaultTipoRecursoShouldBeFound("descripcion.specified=true");

        // Get all the tipoRecursoList where descripcion is null
        defaultTipoRecursoShouldNotBeFound("descripcion.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoRecursoShouldBeFound(String filter) throws Exception {
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoRecursoShouldNotBeFound(String filter) throws Exception {
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoRecurso() throws Exception {
        // Get the tipoRecurso
        restTipoRecursoMockMvc.perform(get("/api/tipo-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoService.save(tipoRecurso);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTipoRecursoSearchRepository);

        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // Update the tipoRecurso
        TipoRecurso updatedTipoRecurso = tipoRecursoRepository.findById(tipoRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRecurso are not directly saved in db
        em.detach(updatedTipoRecurso);
        updatedTipoRecurso
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);

        restTipoRecursoMockMvc.perform(put("/api/tipo-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoRecurso)))
            .andExpect(status().isOk());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);
        TipoRecurso testTipoRecurso = tipoRecursoList.get(tipoRecursoList.size() - 1);
        assertThat(testTipoRecurso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoRecurso.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the TipoRecurso in Elasticsearch
        verify(mockTipoRecursoSearchRepository, times(1)).save(testTipoRecurso);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRecurso() throws Exception {
        int databaseSizeBeforeUpdate = tipoRecursoRepository.findAll().size();

        // Create the TipoRecurso

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRecursoMockMvc.perform(put("/api/tipo-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRecurso)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecurso in the database
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoRecurso in Elasticsearch
        verify(mockTipoRecursoSearchRepository, times(0)).save(tipoRecurso);
    }

    @Test
    @Transactional
    public void deleteTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoService.save(tipoRecurso);

        int databaseSizeBeforeDelete = tipoRecursoRepository.findAll().size();

        // Delete the tipoRecurso
        restTipoRecursoMockMvc.perform(delete("/api/tipo-recursos/{id}", tipoRecurso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRecurso> tipoRecursoList = tipoRecursoRepository.findAll();
        assertThat(tipoRecursoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoRecurso in Elasticsearch
        verify(mockTipoRecursoSearchRepository, times(1)).deleteById(tipoRecurso.getId());
    }

    @Test
    @Transactional
    public void searchTipoRecurso() throws Exception {
        // Initialize the database
        tipoRecursoService.save(tipoRecurso);
        when(mockTipoRecursoSearchRepository.search(queryStringQuery("id:" + tipoRecurso.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoRecurso), PageRequest.of(0, 1), 1));
        // Search the tipoRecurso
        restTipoRecursoMockMvc.perform(get("/api/_search/tipo-recursos?query=id:" + tipoRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRecurso.class);
        TipoRecurso tipoRecurso1 = new TipoRecurso();
        tipoRecurso1.setId(1L);
        TipoRecurso tipoRecurso2 = new TipoRecurso();
        tipoRecurso2.setId(tipoRecurso1.getId());
        assertThat(tipoRecurso1).isEqualTo(tipoRecurso2);
        tipoRecurso2.setId(2L);
        assertThat(tipoRecurso1).isNotEqualTo(tipoRecurso2);
        tipoRecurso1.setId(null);
        assertThat(tipoRecurso1).isNotEqualTo(tipoRecurso2);
    }
}
