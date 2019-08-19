package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.CuentaAsociada;
import io.github.jhipster.newoapp13.repository.CuentaAsociadaRepository;
import io.github.jhipster.newoapp13.repository.search.CuentaAsociadaSearchRepository;
import io.github.jhipster.newoapp13.service.CuentaAsociadaService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.CuentaAsociadaCriteria;
import io.github.jhipster.newoapp13.service.CuentaAsociadaQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CuentaAsociadaResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class CuentaAsociadaResourceIT {

    private static final String DEFAULT_IDENTIFICACIONTITULAR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACIONTITULAR = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_TITULAR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_TITULAR = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_TITULAR = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_TITULAR = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CUENTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CUENTA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CUENTA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CUENTA = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_SEGURIDAD = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_SEGURIDAD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private CuentaAsociadaRepository cuentaAsociadaRepository;

    @Autowired
    private CuentaAsociadaService cuentaAsociadaService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.CuentaAsociadaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CuentaAsociadaSearchRepository mockCuentaAsociadaSearchRepository;

    @Autowired
    private CuentaAsociadaQueryService cuentaAsociadaQueryService;

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

    private MockMvc restCuentaAsociadaMockMvc;

    private CuentaAsociada cuentaAsociada;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CuentaAsociadaResource cuentaAsociadaResource = new CuentaAsociadaResource(cuentaAsociadaService, cuentaAsociadaQueryService);
        this.restCuentaAsociadaMockMvc = MockMvcBuilders.standaloneSetup(cuentaAsociadaResource)
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
    public static CuentaAsociada createEntity(EntityManager em) {
        CuentaAsociada cuentaAsociada = new CuentaAsociada()
            .identificaciontitular(DEFAULT_IDENTIFICACIONTITULAR)
            .nombreTitular(DEFAULT_NOMBRE_TITULAR)
            .apellidoTitular(DEFAULT_APELLIDO_TITULAR)
            .numeroCuenta(DEFAULT_NUMERO_CUENTA)
            .tipoCuenta(DEFAULT_TIPO_CUENTA)
            .codigoSeguridad(DEFAULT_CODIGO_SEGURIDAD)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO);
        return cuentaAsociada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CuentaAsociada createUpdatedEntity(EntityManager em) {
        CuentaAsociada cuentaAsociada = new CuentaAsociada()
            .identificaciontitular(UPDATED_IDENTIFICACIONTITULAR)
            .nombreTitular(UPDATED_NOMBRE_TITULAR)
            .apellidoTitular(UPDATED_APELLIDO_TITULAR)
            .numeroCuenta(UPDATED_NUMERO_CUENTA)
            .tipoCuenta(UPDATED_TIPO_CUENTA)
            .codigoSeguridad(UPDATED_CODIGO_SEGURIDAD)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO);
        return cuentaAsociada;
    }

    @BeforeEach
    public void initTest() {
        cuentaAsociada = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuentaAsociada() throws Exception {
        int databaseSizeBeforeCreate = cuentaAsociadaRepository.findAll().size();

        // Create the CuentaAsociada
        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isCreated());

        // Validate the CuentaAsociada in the database
        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeCreate + 1);
        CuentaAsociada testCuentaAsociada = cuentaAsociadaList.get(cuentaAsociadaList.size() - 1);
        assertThat(testCuentaAsociada.getIdentificaciontitular()).isEqualTo(DEFAULT_IDENTIFICACIONTITULAR);
        assertThat(testCuentaAsociada.getNombreTitular()).isEqualTo(DEFAULT_NOMBRE_TITULAR);
        assertThat(testCuentaAsociada.getApellidoTitular()).isEqualTo(DEFAULT_APELLIDO_TITULAR);
        assertThat(testCuentaAsociada.getNumeroCuenta()).isEqualTo(DEFAULT_NUMERO_CUENTA);
        assertThat(testCuentaAsociada.getTipoCuenta()).isEqualTo(DEFAULT_TIPO_CUENTA);
        assertThat(testCuentaAsociada.getCodigoSeguridad()).isEqualTo(DEFAULT_CODIGO_SEGURIDAD);
        assertThat(testCuentaAsociada.getFechaVencimiento()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO);

        // Validate the CuentaAsociada in Elasticsearch
        verify(mockCuentaAsociadaSearchRepository, times(1)).save(testCuentaAsociada);
    }

    @Test
    @Transactional
    public void createCuentaAsociadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuentaAsociadaRepository.findAll().size();

        // Create the CuentaAsociada with an existing ID
        cuentaAsociada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        // Validate the CuentaAsociada in the database
        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeCreate);

        // Validate the CuentaAsociada in Elasticsearch
        verify(mockCuentaAsociadaSearchRepository, times(0)).save(cuentaAsociada);
    }


    @Test
    @Transactional
    public void checkIdentificaciontitularIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setIdentificaciontitular(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreTitularIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setNombreTitular(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoTitularIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setApellidoTitular(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCuentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setNumeroCuenta(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoCuentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setTipoCuenta(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoSeguridadIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setCodigoSeguridad(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaVencimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaAsociadaRepository.findAll().size();
        // set the field null
        cuentaAsociada.setFechaVencimiento(null);

        // Create the CuentaAsociada, which fails.

        restCuentaAsociadaMockMvc.perform(post("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadas() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentaAsociada.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificaciontitular").value(hasItem(DEFAULT_IDENTIFICACIONTITULAR.toString())))
            .andExpect(jsonPath("$.[*].nombreTitular").value(hasItem(DEFAULT_NOMBRE_TITULAR.toString())))
            .andExpect(jsonPath("$.[*].apellidoTitular").value(hasItem(DEFAULT_APELLIDO_TITULAR.toString())))
            .andExpect(jsonPath("$.[*].numeroCuenta").value(hasItem(DEFAULT_NUMERO_CUENTA.toString())))
            .andExpect(jsonPath("$.[*].tipoCuenta").value(hasItem(DEFAULT_TIPO_CUENTA.toString())))
            .andExpect(jsonPath("$.[*].codigoSeguridad").value(hasItem(DEFAULT_CODIGO_SEGURIDAD.toString())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getCuentaAsociada() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get the cuentaAsociada
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas/{id}", cuentaAsociada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cuentaAsociada.getId().intValue()))
            .andExpect(jsonPath("$.identificaciontitular").value(DEFAULT_IDENTIFICACIONTITULAR.toString()))
            .andExpect(jsonPath("$.nombreTitular").value(DEFAULT_NOMBRE_TITULAR.toString()))
            .andExpect(jsonPath("$.apellidoTitular").value(DEFAULT_APELLIDO_TITULAR.toString()))
            .andExpect(jsonPath("$.numeroCuenta").value(DEFAULT_NUMERO_CUENTA.toString()))
            .andExpect(jsonPath("$.tipoCuenta").value(DEFAULT_TIPO_CUENTA.toString()))
            .andExpect(jsonPath("$.codigoSeguridad").value(DEFAULT_CODIGO_SEGURIDAD.toString()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByIdentificaciontitularIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where identificaciontitular equals to DEFAULT_IDENTIFICACIONTITULAR
        defaultCuentaAsociadaShouldBeFound("identificaciontitular.equals=" + DEFAULT_IDENTIFICACIONTITULAR);

        // Get all the cuentaAsociadaList where identificaciontitular equals to UPDATED_IDENTIFICACIONTITULAR
        defaultCuentaAsociadaShouldNotBeFound("identificaciontitular.equals=" + UPDATED_IDENTIFICACIONTITULAR);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByIdentificaciontitularIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where identificaciontitular in DEFAULT_IDENTIFICACIONTITULAR or UPDATED_IDENTIFICACIONTITULAR
        defaultCuentaAsociadaShouldBeFound("identificaciontitular.in=" + DEFAULT_IDENTIFICACIONTITULAR + "," + UPDATED_IDENTIFICACIONTITULAR);

        // Get all the cuentaAsociadaList where identificaciontitular equals to UPDATED_IDENTIFICACIONTITULAR
        defaultCuentaAsociadaShouldNotBeFound("identificaciontitular.in=" + UPDATED_IDENTIFICACIONTITULAR);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByIdentificaciontitularIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where identificaciontitular is not null
        defaultCuentaAsociadaShouldBeFound("identificaciontitular.specified=true");

        // Get all the cuentaAsociadaList where identificaciontitular is null
        defaultCuentaAsociadaShouldNotBeFound("identificaciontitular.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByNombreTitularIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where nombreTitular equals to DEFAULT_NOMBRE_TITULAR
        defaultCuentaAsociadaShouldBeFound("nombreTitular.equals=" + DEFAULT_NOMBRE_TITULAR);

        // Get all the cuentaAsociadaList where nombreTitular equals to UPDATED_NOMBRE_TITULAR
        defaultCuentaAsociadaShouldNotBeFound("nombreTitular.equals=" + UPDATED_NOMBRE_TITULAR);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByNombreTitularIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where nombreTitular in DEFAULT_NOMBRE_TITULAR or UPDATED_NOMBRE_TITULAR
        defaultCuentaAsociadaShouldBeFound("nombreTitular.in=" + DEFAULT_NOMBRE_TITULAR + "," + UPDATED_NOMBRE_TITULAR);

        // Get all the cuentaAsociadaList where nombreTitular equals to UPDATED_NOMBRE_TITULAR
        defaultCuentaAsociadaShouldNotBeFound("nombreTitular.in=" + UPDATED_NOMBRE_TITULAR);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByNombreTitularIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where nombreTitular is not null
        defaultCuentaAsociadaShouldBeFound("nombreTitular.specified=true");

        // Get all the cuentaAsociadaList where nombreTitular is null
        defaultCuentaAsociadaShouldNotBeFound("nombreTitular.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByApellidoTitularIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where apellidoTitular equals to DEFAULT_APELLIDO_TITULAR
        defaultCuentaAsociadaShouldBeFound("apellidoTitular.equals=" + DEFAULT_APELLIDO_TITULAR);

        // Get all the cuentaAsociadaList where apellidoTitular equals to UPDATED_APELLIDO_TITULAR
        defaultCuentaAsociadaShouldNotBeFound("apellidoTitular.equals=" + UPDATED_APELLIDO_TITULAR);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByApellidoTitularIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where apellidoTitular in DEFAULT_APELLIDO_TITULAR or UPDATED_APELLIDO_TITULAR
        defaultCuentaAsociadaShouldBeFound("apellidoTitular.in=" + DEFAULT_APELLIDO_TITULAR + "," + UPDATED_APELLIDO_TITULAR);

        // Get all the cuentaAsociadaList where apellidoTitular equals to UPDATED_APELLIDO_TITULAR
        defaultCuentaAsociadaShouldNotBeFound("apellidoTitular.in=" + UPDATED_APELLIDO_TITULAR);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByApellidoTitularIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where apellidoTitular is not null
        defaultCuentaAsociadaShouldBeFound("apellidoTitular.specified=true");

        // Get all the cuentaAsociadaList where apellidoTitular is null
        defaultCuentaAsociadaShouldNotBeFound("apellidoTitular.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByNumeroCuentaIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where numeroCuenta equals to DEFAULT_NUMERO_CUENTA
        defaultCuentaAsociadaShouldBeFound("numeroCuenta.equals=" + DEFAULT_NUMERO_CUENTA);

        // Get all the cuentaAsociadaList where numeroCuenta equals to UPDATED_NUMERO_CUENTA
        defaultCuentaAsociadaShouldNotBeFound("numeroCuenta.equals=" + UPDATED_NUMERO_CUENTA);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByNumeroCuentaIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where numeroCuenta in DEFAULT_NUMERO_CUENTA or UPDATED_NUMERO_CUENTA
        defaultCuentaAsociadaShouldBeFound("numeroCuenta.in=" + DEFAULT_NUMERO_CUENTA + "," + UPDATED_NUMERO_CUENTA);

        // Get all the cuentaAsociadaList where numeroCuenta equals to UPDATED_NUMERO_CUENTA
        defaultCuentaAsociadaShouldNotBeFound("numeroCuenta.in=" + UPDATED_NUMERO_CUENTA);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByNumeroCuentaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where numeroCuenta is not null
        defaultCuentaAsociadaShouldBeFound("numeroCuenta.specified=true");

        // Get all the cuentaAsociadaList where numeroCuenta is null
        defaultCuentaAsociadaShouldNotBeFound("numeroCuenta.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByTipoCuentaIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where tipoCuenta equals to DEFAULT_TIPO_CUENTA
        defaultCuentaAsociadaShouldBeFound("tipoCuenta.equals=" + DEFAULT_TIPO_CUENTA);

        // Get all the cuentaAsociadaList where tipoCuenta equals to UPDATED_TIPO_CUENTA
        defaultCuentaAsociadaShouldNotBeFound("tipoCuenta.equals=" + UPDATED_TIPO_CUENTA);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByTipoCuentaIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where tipoCuenta in DEFAULT_TIPO_CUENTA or UPDATED_TIPO_CUENTA
        defaultCuentaAsociadaShouldBeFound("tipoCuenta.in=" + DEFAULT_TIPO_CUENTA + "," + UPDATED_TIPO_CUENTA);

        // Get all the cuentaAsociadaList where tipoCuenta equals to UPDATED_TIPO_CUENTA
        defaultCuentaAsociadaShouldNotBeFound("tipoCuenta.in=" + UPDATED_TIPO_CUENTA);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByTipoCuentaIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where tipoCuenta is not null
        defaultCuentaAsociadaShouldBeFound("tipoCuenta.specified=true");

        // Get all the cuentaAsociadaList where tipoCuenta is null
        defaultCuentaAsociadaShouldNotBeFound("tipoCuenta.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByCodigoSeguridadIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where codigoSeguridad equals to DEFAULT_CODIGO_SEGURIDAD
        defaultCuentaAsociadaShouldBeFound("codigoSeguridad.equals=" + DEFAULT_CODIGO_SEGURIDAD);

        // Get all the cuentaAsociadaList where codigoSeguridad equals to UPDATED_CODIGO_SEGURIDAD
        defaultCuentaAsociadaShouldNotBeFound("codigoSeguridad.equals=" + UPDATED_CODIGO_SEGURIDAD);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByCodigoSeguridadIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where codigoSeguridad in DEFAULT_CODIGO_SEGURIDAD or UPDATED_CODIGO_SEGURIDAD
        defaultCuentaAsociadaShouldBeFound("codigoSeguridad.in=" + DEFAULT_CODIGO_SEGURIDAD + "," + UPDATED_CODIGO_SEGURIDAD);

        // Get all the cuentaAsociadaList where codigoSeguridad equals to UPDATED_CODIGO_SEGURIDAD
        defaultCuentaAsociadaShouldNotBeFound("codigoSeguridad.in=" + UPDATED_CODIGO_SEGURIDAD);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByCodigoSeguridadIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where codigoSeguridad is not null
        defaultCuentaAsociadaShouldBeFound("codigoSeguridad.specified=true");

        // Get all the cuentaAsociadaList where codigoSeguridad is null
        defaultCuentaAsociadaShouldNotBeFound("codigoSeguridad.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento equals to DEFAULT_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.equals=" + DEFAULT_FECHA_VENCIMIENTO);

        // Get all the cuentaAsociadaList where fechaVencimiento equals to UPDATED_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.equals=" + UPDATED_FECHA_VENCIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsInShouldWork() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento in DEFAULT_FECHA_VENCIMIENTO or UPDATED_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.in=" + DEFAULT_FECHA_VENCIMIENTO + "," + UPDATED_FECHA_VENCIMIENTO);

        // Get all the cuentaAsociadaList where fechaVencimiento equals to UPDATED_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.in=" + UPDATED_FECHA_VENCIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento is not null
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.specified=true");

        // Get all the cuentaAsociadaList where fechaVencimiento is null
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento is greater than or equal to DEFAULT_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.greaterThanOrEqual=" + DEFAULT_FECHA_VENCIMIENTO);

        // Get all the cuentaAsociadaList where fechaVencimiento is greater than or equal to UPDATED_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.greaterThanOrEqual=" + UPDATED_FECHA_VENCIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento is less than or equal to DEFAULT_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.lessThanOrEqual=" + DEFAULT_FECHA_VENCIMIENTO);

        // Get all the cuentaAsociadaList where fechaVencimiento is less than or equal to SMALLER_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.lessThanOrEqual=" + SMALLER_FECHA_VENCIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento is less than DEFAULT_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.lessThan=" + DEFAULT_FECHA_VENCIMIENTO);

        // Get all the cuentaAsociadaList where fechaVencimiento is less than UPDATED_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.lessThan=" + UPDATED_FECHA_VENCIMIENTO);
    }

    @Test
    @Transactional
    public void getAllCuentaAsociadasByFechaVencimientoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cuentaAsociadaRepository.saveAndFlush(cuentaAsociada);

        // Get all the cuentaAsociadaList where fechaVencimiento is greater than DEFAULT_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldNotBeFound("fechaVencimiento.greaterThan=" + DEFAULT_FECHA_VENCIMIENTO);

        // Get all the cuentaAsociadaList where fechaVencimiento is greater than SMALLER_FECHA_VENCIMIENTO
        defaultCuentaAsociadaShouldBeFound("fechaVencimiento.greaterThan=" + SMALLER_FECHA_VENCIMIENTO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCuentaAsociadaShouldBeFound(String filter) throws Exception {
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentaAsociada.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificaciontitular").value(hasItem(DEFAULT_IDENTIFICACIONTITULAR)))
            .andExpect(jsonPath("$.[*].nombreTitular").value(hasItem(DEFAULT_NOMBRE_TITULAR)))
            .andExpect(jsonPath("$.[*].apellidoTitular").value(hasItem(DEFAULT_APELLIDO_TITULAR)))
            .andExpect(jsonPath("$.[*].numeroCuenta").value(hasItem(DEFAULT_NUMERO_CUENTA)))
            .andExpect(jsonPath("$.[*].tipoCuenta").value(hasItem(DEFAULT_TIPO_CUENTA)))
            .andExpect(jsonPath("$.[*].codigoSeguridad").value(hasItem(DEFAULT_CODIGO_SEGURIDAD)))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())));

        // Check, that the count call also returns 1
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCuentaAsociadaShouldNotBeFound(String filter) throws Exception {
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCuentaAsociada() throws Exception {
        // Get the cuentaAsociada
        restCuentaAsociadaMockMvc.perform(get("/api/cuenta-asociadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuentaAsociada() throws Exception {
        // Initialize the database
        cuentaAsociadaService.save(cuentaAsociada);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCuentaAsociadaSearchRepository);

        int databaseSizeBeforeUpdate = cuentaAsociadaRepository.findAll().size();

        // Update the cuentaAsociada
        CuentaAsociada updatedCuentaAsociada = cuentaAsociadaRepository.findById(cuentaAsociada.getId()).get();
        // Disconnect from session so that the updates on updatedCuentaAsociada are not directly saved in db
        em.detach(updatedCuentaAsociada);
        updatedCuentaAsociada
            .identificaciontitular(UPDATED_IDENTIFICACIONTITULAR)
            .nombreTitular(UPDATED_NOMBRE_TITULAR)
            .apellidoTitular(UPDATED_APELLIDO_TITULAR)
            .numeroCuenta(UPDATED_NUMERO_CUENTA)
            .tipoCuenta(UPDATED_TIPO_CUENTA)
            .codigoSeguridad(UPDATED_CODIGO_SEGURIDAD)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO);

        restCuentaAsociadaMockMvc.perform(put("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCuentaAsociada)))
            .andExpect(status().isOk());

        // Validate the CuentaAsociada in the database
        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeUpdate);
        CuentaAsociada testCuentaAsociada = cuentaAsociadaList.get(cuentaAsociadaList.size() - 1);
        assertThat(testCuentaAsociada.getIdentificaciontitular()).isEqualTo(UPDATED_IDENTIFICACIONTITULAR);
        assertThat(testCuentaAsociada.getNombreTitular()).isEqualTo(UPDATED_NOMBRE_TITULAR);
        assertThat(testCuentaAsociada.getApellidoTitular()).isEqualTo(UPDATED_APELLIDO_TITULAR);
        assertThat(testCuentaAsociada.getNumeroCuenta()).isEqualTo(UPDATED_NUMERO_CUENTA);
        assertThat(testCuentaAsociada.getTipoCuenta()).isEqualTo(UPDATED_TIPO_CUENTA);
        assertThat(testCuentaAsociada.getCodigoSeguridad()).isEqualTo(UPDATED_CODIGO_SEGURIDAD);
        assertThat(testCuentaAsociada.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);

        // Validate the CuentaAsociada in Elasticsearch
        verify(mockCuentaAsociadaSearchRepository, times(1)).save(testCuentaAsociada);
    }

    @Test
    @Transactional
    public void updateNonExistingCuentaAsociada() throws Exception {
        int databaseSizeBeforeUpdate = cuentaAsociadaRepository.findAll().size();

        // Create the CuentaAsociada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaAsociadaMockMvc.perform(put("/api/cuenta-asociadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaAsociada)))
            .andExpect(status().isBadRequest());

        // Validate the CuentaAsociada in the database
        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CuentaAsociada in Elasticsearch
        verify(mockCuentaAsociadaSearchRepository, times(0)).save(cuentaAsociada);
    }

    @Test
    @Transactional
    public void deleteCuentaAsociada() throws Exception {
        // Initialize the database
        cuentaAsociadaService.save(cuentaAsociada);

        int databaseSizeBeforeDelete = cuentaAsociadaRepository.findAll().size();

        // Delete the cuentaAsociada
        restCuentaAsociadaMockMvc.perform(delete("/api/cuenta-asociadas/{id}", cuentaAsociada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CuentaAsociada> cuentaAsociadaList = cuentaAsociadaRepository.findAll();
        assertThat(cuentaAsociadaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CuentaAsociada in Elasticsearch
        verify(mockCuentaAsociadaSearchRepository, times(1)).deleteById(cuentaAsociada.getId());
    }

    @Test
    @Transactional
    public void searchCuentaAsociada() throws Exception {
        // Initialize the database
        cuentaAsociadaService.save(cuentaAsociada);
        when(mockCuentaAsociadaSearchRepository.search(queryStringQuery("id:" + cuentaAsociada.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cuentaAsociada), PageRequest.of(0, 1), 1));
        // Search the cuentaAsociada
        restCuentaAsociadaMockMvc.perform(get("/api/_search/cuenta-asociadas?query=id:" + cuentaAsociada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentaAsociada.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificaciontitular").value(hasItem(DEFAULT_IDENTIFICACIONTITULAR)))
            .andExpect(jsonPath("$.[*].nombreTitular").value(hasItem(DEFAULT_NOMBRE_TITULAR)))
            .andExpect(jsonPath("$.[*].apellidoTitular").value(hasItem(DEFAULT_APELLIDO_TITULAR)))
            .andExpect(jsonPath("$.[*].numeroCuenta").value(hasItem(DEFAULT_NUMERO_CUENTA)))
            .andExpect(jsonPath("$.[*].tipoCuenta").value(hasItem(DEFAULT_TIPO_CUENTA)))
            .andExpect(jsonPath("$.[*].codigoSeguridad").value(hasItem(DEFAULT_CODIGO_SEGURIDAD)))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentaAsociada.class);
        CuentaAsociada cuentaAsociada1 = new CuentaAsociada();
        cuentaAsociada1.setId(1L);
        CuentaAsociada cuentaAsociada2 = new CuentaAsociada();
        cuentaAsociada2.setId(cuentaAsociada1.getId());
        assertThat(cuentaAsociada1).isEqualTo(cuentaAsociada2);
        cuentaAsociada2.setId(2L);
        assertThat(cuentaAsociada1).isNotEqualTo(cuentaAsociada2);
        cuentaAsociada1.setId(null);
        assertThat(cuentaAsociada1).isNotEqualTo(cuentaAsociada2);
    }
}
