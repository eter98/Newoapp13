package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.UsoRecursoFisico;
import io.github.jhipster.newoapp13.domain.RecursosFisicos;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.UsoRecursoFisicoRepository;
import io.github.jhipster.newoapp13.repository.search.UsoRecursoFisicoSearchRepository;
import io.github.jhipster.newoapp13.service.UsoRecursoFisicoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.UsoRecursoFisicoCriteria;
import io.github.jhipster.newoapp13.service.UsoRecursoFisicoQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.newoapp13.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.newoapp13.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.newoapp13.domain.enumeration.TipoIniciod;
/**
 * Integration tests for the {@link UsoRecursoFisicoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class UsoRecursoFisicoResourceIT {

    private static final ZonedDateTime DEFAULT_REGISTRO_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRO_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTRO_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_REGISTRO_FECHA_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRO_FECHA_FINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTRO_FECHA_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final TipoIniciod DEFAULT_TIPO_REGISTRO = TipoIniciod.Inicio;
    private static final TipoIniciod UPDATED_TIPO_REGISTRO = TipoIniciod.Fin;

    @Autowired
    private UsoRecursoFisicoRepository usoRecursoFisicoRepository;

    @Autowired
    private UsoRecursoFisicoService usoRecursoFisicoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.UsoRecursoFisicoSearchRepositoryMockConfiguration
     */
    @Autowired
    private UsoRecursoFisicoSearchRepository mockUsoRecursoFisicoSearchRepository;

    @Autowired
    private UsoRecursoFisicoQueryService usoRecursoFisicoQueryService;

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

    private MockMvc restUsoRecursoFisicoMockMvc;

    private UsoRecursoFisico usoRecursoFisico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsoRecursoFisicoResource usoRecursoFisicoResource = new UsoRecursoFisicoResource(usoRecursoFisicoService, usoRecursoFisicoQueryService);
        this.restUsoRecursoFisicoMockMvc = MockMvcBuilders.standaloneSetup(usoRecursoFisicoResource)
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
    public static UsoRecursoFisico createEntity(EntityManager em) {
        UsoRecursoFisico usoRecursoFisico = new UsoRecursoFisico()
            .registroFechaInicio(DEFAULT_REGISTRO_FECHA_INICIO)
            .registroFechaFinal(DEFAULT_REGISTRO_FECHA_FINAL)
            .tipoRegistro(DEFAULT_TIPO_REGISTRO);
        return usoRecursoFisico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsoRecursoFisico createUpdatedEntity(EntityManager em) {
        UsoRecursoFisico usoRecursoFisico = new UsoRecursoFisico()
            .registroFechaInicio(UPDATED_REGISTRO_FECHA_INICIO)
            .registroFechaFinal(UPDATED_REGISTRO_FECHA_FINAL)
            .tipoRegistro(UPDATED_TIPO_REGISTRO);
        return usoRecursoFisico;
    }

    @BeforeEach
    public void initTest() {
        usoRecursoFisico = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsoRecursoFisico() throws Exception {
        int databaseSizeBeforeCreate = usoRecursoFisicoRepository.findAll().size();

        // Create the UsoRecursoFisico
        restUsoRecursoFisicoMockMvc.perform(post("/api/uso-recurso-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usoRecursoFisico)))
            .andExpect(status().isCreated());

        // Validate the UsoRecursoFisico in the database
        List<UsoRecursoFisico> usoRecursoFisicoList = usoRecursoFisicoRepository.findAll();
        assertThat(usoRecursoFisicoList).hasSize(databaseSizeBeforeCreate + 1);
        UsoRecursoFisico testUsoRecursoFisico = usoRecursoFisicoList.get(usoRecursoFisicoList.size() - 1);
        assertThat(testUsoRecursoFisico.getRegistroFechaInicio()).isEqualTo(DEFAULT_REGISTRO_FECHA_INICIO);
        assertThat(testUsoRecursoFisico.getRegistroFechaFinal()).isEqualTo(DEFAULT_REGISTRO_FECHA_FINAL);
        assertThat(testUsoRecursoFisico.getTipoRegistro()).isEqualTo(DEFAULT_TIPO_REGISTRO);

        // Validate the UsoRecursoFisico in Elasticsearch
        verify(mockUsoRecursoFisicoSearchRepository, times(1)).save(testUsoRecursoFisico);
    }

    @Test
    @Transactional
    public void createUsoRecursoFisicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usoRecursoFisicoRepository.findAll().size();

        // Create the UsoRecursoFisico with an existing ID
        usoRecursoFisico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsoRecursoFisicoMockMvc.perform(post("/api/uso-recurso-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usoRecursoFisico)))
            .andExpect(status().isBadRequest());

        // Validate the UsoRecursoFisico in the database
        List<UsoRecursoFisico> usoRecursoFisicoList = usoRecursoFisicoRepository.findAll();
        assertThat(usoRecursoFisicoList).hasSize(databaseSizeBeforeCreate);

        // Validate the UsoRecursoFisico in Elasticsearch
        verify(mockUsoRecursoFisicoSearchRepository, times(0)).save(usoRecursoFisico);
    }


    @Test
    @Transactional
    public void getAllUsoRecursoFisicos() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usoRecursoFisico.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFechaInicio").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].registroFechaFinal").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_FINAL))))
            .andExpect(jsonPath("$.[*].tipoRegistro").value(hasItem(DEFAULT_TIPO_REGISTRO.toString())));
    }
    
    @Test
    @Transactional
    public void getUsoRecursoFisico() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get the usoRecursoFisico
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos/{id}", usoRecursoFisico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usoRecursoFisico.getId().intValue()))
            .andExpect(jsonPath("$.registroFechaInicio").value(sameInstant(DEFAULT_REGISTRO_FECHA_INICIO)))
            .andExpect(jsonPath("$.registroFechaFinal").value(sameInstant(DEFAULT_REGISTRO_FECHA_FINAL)))
            .andExpect(jsonPath("$.tipoRegistro").value(DEFAULT_TIPO_REGISTRO.toString()));
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio equals to DEFAULT_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.equals=" + DEFAULT_REGISTRO_FECHA_INICIO);

        // Get all the usoRecursoFisicoList where registroFechaInicio equals to UPDATED_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.equals=" + UPDATED_REGISTRO_FECHA_INICIO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsInShouldWork() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio in DEFAULT_REGISTRO_FECHA_INICIO or UPDATED_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.in=" + DEFAULT_REGISTRO_FECHA_INICIO + "," + UPDATED_REGISTRO_FECHA_INICIO);

        // Get all the usoRecursoFisicoList where registroFechaInicio equals to UPDATED_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.in=" + UPDATED_REGISTRO_FECHA_INICIO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio is not null
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.specified=true");

        // Get all the usoRecursoFisicoList where registroFechaInicio is null
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio is greater than or equal to DEFAULT_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.greaterThanOrEqual=" + DEFAULT_REGISTRO_FECHA_INICIO);

        // Get all the usoRecursoFisicoList where registroFechaInicio is greater than or equal to UPDATED_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.greaterThanOrEqual=" + UPDATED_REGISTRO_FECHA_INICIO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio is less than or equal to DEFAULT_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.lessThanOrEqual=" + DEFAULT_REGISTRO_FECHA_INICIO);

        // Get all the usoRecursoFisicoList where registroFechaInicio is less than or equal to SMALLER_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.lessThanOrEqual=" + SMALLER_REGISTRO_FECHA_INICIO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsLessThanSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio is less than DEFAULT_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.lessThan=" + DEFAULT_REGISTRO_FECHA_INICIO);

        // Get all the usoRecursoFisicoList where registroFechaInicio is less than UPDATED_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.lessThan=" + UPDATED_REGISTRO_FECHA_INICIO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaInicioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaInicio is greater than DEFAULT_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaInicio.greaterThan=" + DEFAULT_REGISTRO_FECHA_INICIO);

        // Get all the usoRecursoFisicoList where registroFechaInicio is greater than SMALLER_REGISTRO_FECHA_INICIO
        defaultUsoRecursoFisicoShouldBeFound("registroFechaInicio.greaterThan=" + SMALLER_REGISTRO_FECHA_INICIO);
    }


    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal equals to DEFAULT_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.equals=" + DEFAULT_REGISTRO_FECHA_FINAL);

        // Get all the usoRecursoFisicoList where registroFechaFinal equals to UPDATED_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.equals=" + UPDATED_REGISTRO_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsInShouldWork() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal in DEFAULT_REGISTRO_FECHA_FINAL or UPDATED_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.in=" + DEFAULT_REGISTRO_FECHA_FINAL + "," + UPDATED_REGISTRO_FECHA_FINAL);

        // Get all the usoRecursoFisicoList where registroFechaFinal equals to UPDATED_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.in=" + UPDATED_REGISTRO_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal is not null
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.specified=true");

        // Get all the usoRecursoFisicoList where registroFechaFinal is null
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal is greater than or equal to DEFAULT_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.greaterThanOrEqual=" + DEFAULT_REGISTRO_FECHA_FINAL);

        // Get all the usoRecursoFisicoList where registroFechaFinal is greater than or equal to UPDATED_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.greaterThanOrEqual=" + UPDATED_REGISTRO_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal is less than or equal to DEFAULT_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.lessThanOrEqual=" + DEFAULT_REGISTRO_FECHA_FINAL);

        // Get all the usoRecursoFisicoList where registroFechaFinal is less than or equal to SMALLER_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.lessThanOrEqual=" + SMALLER_REGISTRO_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal is less than DEFAULT_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.lessThan=" + DEFAULT_REGISTRO_FECHA_FINAL);

        // Get all the usoRecursoFisicoList where registroFechaFinal is less than UPDATED_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.lessThan=" + UPDATED_REGISTRO_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRegistroFechaFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where registroFechaFinal is greater than DEFAULT_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldNotBeFound("registroFechaFinal.greaterThan=" + DEFAULT_REGISTRO_FECHA_FINAL);

        // Get all the usoRecursoFisicoList where registroFechaFinal is greater than SMALLER_REGISTRO_FECHA_FINAL
        defaultUsoRecursoFisicoShouldBeFound("registroFechaFinal.greaterThan=" + SMALLER_REGISTRO_FECHA_FINAL);
    }


    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByTipoRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where tipoRegistro equals to DEFAULT_TIPO_REGISTRO
        defaultUsoRecursoFisicoShouldBeFound("tipoRegistro.equals=" + DEFAULT_TIPO_REGISTRO);

        // Get all the usoRecursoFisicoList where tipoRegistro equals to UPDATED_TIPO_REGISTRO
        defaultUsoRecursoFisicoShouldNotBeFound("tipoRegistro.equals=" + UPDATED_TIPO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByTipoRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where tipoRegistro in DEFAULT_TIPO_REGISTRO or UPDATED_TIPO_REGISTRO
        defaultUsoRecursoFisicoShouldBeFound("tipoRegistro.in=" + DEFAULT_TIPO_REGISTRO + "," + UPDATED_TIPO_REGISTRO);

        // Get all the usoRecursoFisicoList where tipoRegistro equals to UPDATED_TIPO_REGISTRO
        defaultUsoRecursoFisicoShouldNotBeFound("tipoRegistro.in=" + UPDATED_TIPO_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByTipoRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);

        // Get all the usoRecursoFisicoList where tipoRegistro is not null
        defaultUsoRecursoFisicoShouldBeFound("tipoRegistro.specified=true");

        // Get all the usoRecursoFisicoList where tipoRegistro is null
        defaultUsoRecursoFisicoShouldNotBeFound("tipoRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);
        RecursosFisicos recurso = RecursosFisicosResourceIT.createEntity(em);
        em.persist(recurso);
        em.flush();
        usoRecursoFisico.setRecurso(recurso);
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);
        Long recursoId = recurso.getId();

        // Get all the usoRecursoFisicoList where recurso equals to recursoId
        defaultUsoRecursoFisicoShouldBeFound("recursoId.equals=" + recursoId);

        // Get all the usoRecursoFisicoList where recurso equals to recursoId + 1
        defaultUsoRecursoFisicoShouldNotBeFound("recursoId.equals=" + (recursoId + 1));
    }


    @Test
    @Transactional
    public void getAllUsoRecursoFisicosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        usoRecursoFisico.setMiembro(miembro);
        usoRecursoFisicoRepository.saveAndFlush(usoRecursoFisico);
        Long miembroId = miembro.getId();

        // Get all the usoRecursoFisicoList where miembro equals to miembroId
        defaultUsoRecursoFisicoShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the usoRecursoFisicoList where miembro equals to miembroId + 1
        defaultUsoRecursoFisicoShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsoRecursoFisicoShouldBeFound(String filter) throws Exception {
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usoRecursoFisico.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFechaInicio").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].registroFechaFinal").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_FINAL))))
            .andExpect(jsonPath("$.[*].tipoRegistro").value(hasItem(DEFAULT_TIPO_REGISTRO.toString())));

        // Check, that the count call also returns 1
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsoRecursoFisicoShouldNotBeFound(String filter) throws Exception {
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUsoRecursoFisico() throws Exception {
        // Get the usoRecursoFisico
        restUsoRecursoFisicoMockMvc.perform(get("/api/uso-recurso-fisicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsoRecursoFisico() throws Exception {
        // Initialize the database
        usoRecursoFisicoService.save(usoRecursoFisico);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUsoRecursoFisicoSearchRepository);

        int databaseSizeBeforeUpdate = usoRecursoFisicoRepository.findAll().size();

        // Update the usoRecursoFisico
        UsoRecursoFisico updatedUsoRecursoFisico = usoRecursoFisicoRepository.findById(usoRecursoFisico.getId()).get();
        // Disconnect from session so that the updates on updatedUsoRecursoFisico are not directly saved in db
        em.detach(updatedUsoRecursoFisico);
        updatedUsoRecursoFisico
            .registroFechaInicio(UPDATED_REGISTRO_FECHA_INICIO)
            .registroFechaFinal(UPDATED_REGISTRO_FECHA_FINAL)
            .tipoRegistro(UPDATED_TIPO_REGISTRO);

        restUsoRecursoFisicoMockMvc.perform(put("/api/uso-recurso-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsoRecursoFisico)))
            .andExpect(status().isOk());

        // Validate the UsoRecursoFisico in the database
        List<UsoRecursoFisico> usoRecursoFisicoList = usoRecursoFisicoRepository.findAll();
        assertThat(usoRecursoFisicoList).hasSize(databaseSizeBeforeUpdate);
        UsoRecursoFisico testUsoRecursoFisico = usoRecursoFisicoList.get(usoRecursoFisicoList.size() - 1);
        assertThat(testUsoRecursoFisico.getRegistroFechaInicio()).isEqualTo(UPDATED_REGISTRO_FECHA_INICIO);
        assertThat(testUsoRecursoFisico.getRegistroFechaFinal()).isEqualTo(UPDATED_REGISTRO_FECHA_FINAL);
        assertThat(testUsoRecursoFisico.getTipoRegistro()).isEqualTo(UPDATED_TIPO_REGISTRO);

        // Validate the UsoRecursoFisico in Elasticsearch
        verify(mockUsoRecursoFisicoSearchRepository, times(1)).save(testUsoRecursoFisico);
    }

    @Test
    @Transactional
    public void updateNonExistingUsoRecursoFisico() throws Exception {
        int databaseSizeBeforeUpdate = usoRecursoFisicoRepository.findAll().size();

        // Create the UsoRecursoFisico

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsoRecursoFisicoMockMvc.perform(put("/api/uso-recurso-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usoRecursoFisico)))
            .andExpect(status().isBadRequest());

        // Validate the UsoRecursoFisico in the database
        List<UsoRecursoFisico> usoRecursoFisicoList = usoRecursoFisicoRepository.findAll();
        assertThat(usoRecursoFisicoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UsoRecursoFisico in Elasticsearch
        verify(mockUsoRecursoFisicoSearchRepository, times(0)).save(usoRecursoFisico);
    }

    @Test
    @Transactional
    public void deleteUsoRecursoFisico() throws Exception {
        // Initialize the database
        usoRecursoFisicoService.save(usoRecursoFisico);

        int databaseSizeBeforeDelete = usoRecursoFisicoRepository.findAll().size();

        // Delete the usoRecursoFisico
        restUsoRecursoFisicoMockMvc.perform(delete("/api/uso-recurso-fisicos/{id}", usoRecursoFisico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsoRecursoFisico> usoRecursoFisicoList = usoRecursoFisicoRepository.findAll();
        assertThat(usoRecursoFisicoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UsoRecursoFisico in Elasticsearch
        verify(mockUsoRecursoFisicoSearchRepository, times(1)).deleteById(usoRecursoFisico.getId());
    }

    @Test
    @Transactional
    public void searchUsoRecursoFisico() throws Exception {
        // Initialize the database
        usoRecursoFisicoService.save(usoRecursoFisico);
        when(mockUsoRecursoFisicoSearchRepository.search(queryStringQuery("id:" + usoRecursoFisico.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(usoRecursoFisico), PageRequest.of(0, 1), 1));
        // Search the usoRecursoFisico
        restUsoRecursoFisicoMockMvc.perform(get("/api/_search/uso-recurso-fisicos?query=id:" + usoRecursoFisico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usoRecursoFisico.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFechaInicio").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].registroFechaFinal").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_FINAL))))
            .andExpect(jsonPath("$.[*].tipoRegistro").value(hasItem(DEFAULT_TIPO_REGISTRO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsoRecursoFisico.class);
        UsoRecursoFisico usoRecursoFisico1 = new UsoRecursoFisico();
        usoRecursoFisico1.setId(1L);
        UsoRecursoFisico usoRecursoFisico2 = new UsoRecursoFisico();
        usoRecursoFisico2.setId(usoRecursoFisico1.getId());
        assertThat(usoRecursoFisico1).isEqualTo(usoRecursoFisico2);
        usoRecursoFisico2.setId(2L);
        assertThat(usoRecursoFisico1).isNotEqualTo(usoRecursoFisico2);
        usoRecursoFisico1.setId(null);
        assertThat(usoRecursoFisico1).isNotEqualTo(usoRecursoFisico2);
    }
}
