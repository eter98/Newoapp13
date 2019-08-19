package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Facturacion;
import io.github.jhipster.newoapp13.domain.Empresa;
import io.github.jhipster.newoapp13.domain.CuentaAsociada;
import io.github.jhipster.newoapp13.repository.FacturacionRepository;
import io.github.jhipster.newoapp13.repository.search.FacturacionSearchRepository;
import io.github.jhipster.newoapp13.service.FacturacionService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.FacturacionCriteria;
import io.github.jhipster.newoapp13.service.FacturacionQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.TipoPersonad;
import io.github.jhipster.newoapp13.domain.enumeration.PeriodicidadFacturaciond;
/**
 * Integration tests for the {@link FacturacionResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class FacturacionResourceIT {

    private static final String DEFAULT_TITULAR_FACTURA = "AAAAAAAAAA";
    private static final String UPDATED_TITULAR_FACTURA = "BBBBBBBBBB";

    private static final TipoPersonad DEFAULT_TIPO_PERSONA = TipoPersonad.NATURAL;
    private static final TipoPersonad UPDATED_TIPO_PERSONA = TipoPersonad.JURIDICA;

    private static final PeriodicidadFacturaciond DEFAULT_PERIODICIDAD_FACTURACION = PeriodicidadFacturaciond.SEMANAL;
    private static final PeriodicidadFacturaciond UPDATED_PERIODICIDAD_FACTURACION = PeriodicidadFacturaciond.QUINCENAL;

    private static final Integer DEFAULT_MAXIMO_MONTO = 1;
    private static final Integer UPDATED_MAXIMO_MONTO = 2;
    private static final Integer SMALLER_MAXIMO_MONTO = 1 - 1;

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;
    private static final Integer SMALLER_VALOR = 1 - 1;

    @Autowired
    private FacturacionRepository facturacionRepository;

    @Autowired
    private FacturacionService facturacionService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.FacturacionSearchRepositoryMockConfiguration
     */
    @Autowired
    private FacturacionSearchRepository mockFacturacionSearchRepository;

    @Autowired
    private FacturacionQueryService facturacionQueryService;

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

    private MockMvc restFacturacionMockMvc;

    private Facturacion facturacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacturacionResource facturacionResource = new FacturacionResource(facturacionService, facturacionQueryService);
        this.restFacturacionMockMvc = MockMvcBuilders.standaloneSetup(facturacionResource)
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
    public static Facturacion createEntity(EntityManager em) {
        Facturacion facturacion = new Facturacion()
            .titularFactura(DEFAULT_TITULAR_FACTURA)
            .tipoPersona(DEFAULT_TIPO_PERSONA)
            .periodicidadFacturacion(DEFAULT_PERIODICIDAD_FACTURACION)
            .maximoMonto(DEFAULT_MAXIMO_MONTO)
            .valor(DEFAULT_VALOR);
        return facturacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facturacion createUpdatedEntity(EntityManager em) {
        Facturacion facturacion = new Facturacion()
            .titularFactura(UPDATED_TITULAR_FACTURA)
            .tipoPersona(UPDATED_TIPO_PERSONA)
            .periodicidadFacturacion(UPDATED_PERIODICIDAD_FACTURACION)
            .maximoMonto(UPDATED_MAXIMO_MONTO)
            .valor(UPDATED_VALOR);
        return facturacion;
    }

    @BeforeEach
    public void initTest() {
        facturacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacturacion() throws Exception {
        int databaseSizeBeforeCreate = facturacionRepository.findAll().size();

        // Create the Facturacion
        restFacturacionMockMvc.perform(post("/api/facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturacion)))
            .andExpect(status().isCreated());

        // Validate the Facturacion in the database
        List<Facturacion> facturacionList = facturacionRepository.findAll();
        assertThat(facturacionList).hasSize(databaseSizeBeforeCreate + 1);
        Facturacion testFacturacion = facturacionList.get(facturacionList.size() - 1);
        assertThat(testFacturacion.getTitularFactura()).isEqualTo(DEFAULT_TITULAR_FACTURA);
        assertThat(testFacturacion.getTipoPersona()).isEqualTo(DEFAULT_TIPO_PERSONA);
        assertThat(testFacturacion.getPeriodicidadFacturacion()).isEqualTo(DEFAULT_PERIODICIDAD_FACTURACION);
        assertThat(testFacturacion.getMaximoMonto()).isEqualTo(DEFAULT_MAXIMO_MONTO);
        assertThat(testFacturacion.getValor()).isEqualTo(DEFAULT_VALOR);

        // Validate the Facturacion in Elasticsearch
        verify(mockFacturacionSearchRepository, times(1)).save(testFacturacion);
    }

    @Test
    @Transactional
    public void createFacturacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturacionRepository.findAll().size();

        // Create the Facturacion with an existing ID
        facturacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturacionMockMvc.perform(post("/api/facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturacion)))
            .andExpect(status().isBadRequest());

        // Validate the Facturacion in the database
        List<Facturacion> facturacionList = facturacionRepository.findAll();
        assertThat(facturacionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Facturacion in Elasticsearch
        verify(mockFacturacionSearchRepository, times(0)).save(facturacion);
    }


    @Test
    @Transactional
    public void getAllFacturacions() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList
        restFacturacionMockMvc.perform(get("/api/facturacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].titularFactura").value(hasItem(DEFAULT_TITULAR_FACTURA.toString())))
            .andExpect(jsonPath("$.[*].tipoPersona").value(hasItem(DEFAULT_TIPO_PERSONA.toString())))
            .andExpect(jsonPath("$.[*].periodicidadFacturacion").value(hasItem(DEFAULT_PERIODICIDAD_FACTURACION.toString())))
            .andExpect(jsonPath("$.[*].maximoMonto").value(hasItem(DEFAULT_MAXIMO_MONTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getFacturacion() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get the facturacion
        restFacturacionMockMvc.perform(get("/api/facturacions/{id}", facturacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facturacion.getId().intValue()))
            .andExpect(jsonPath("$.titularFactura").value(DEFAULT_TITULAR_FACTURA.toString()))
            .andExpect(jsonPath("$.tipoPersona").value(DEFAULT_TIPO_PERSONA.toString()))
            .andExpect(jsonPath("$.periodicidadFacturacion").value(DEFAULT_PERIODICIDAD_FACTURACION.toString()))
            .andExpect(jsonPath("$.maximoMonto").value(DEFAULT_MAXIMO_MONTO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    public void getAllFacturacionsByTitularFacturaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where titularFactura equals to DEFAULT_TITULAR_FACTURA
        defaultFacturacionShouldBeFound("titularFactura.equals=" + DEFAULT_TITULAR_FACTURA);

        // Get all the facturacionList where titularFactura equals to UPDATED_TITULAR_FACTURA
        defaultFacturacionShouldNotBeFound("titularFactura.equals=" + UPDATED_TITULAR_FACTURA);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByTitularFacturaIsInShouldWork() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where titularFactura in DEFAULT_TITULAR_FACTURA or UPDATED_TITULAR_FACTURA
        defaultFacturacionShouldBeFound("titularFactura.in=" + DEFAULT_TITULAR_FACTURA + "," + UPDATED_TITULAR_FACTURA);

        // Get all the facturacionList where titularFactura equals to UPDATED_TITULAR_FACTURA
        defaultFacturacionShouldNotBeFound("titularFactura.in=" + UPDATED_TITULAR_FACTURA);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByTitularFacturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where titularFactura is not null
        defaultFacturacionShouldBeFound("titularFactura.specified=true");

        // Get all the facturacionList where titularFactura is null
        defaultFacturacionShouldNotBeFound("titularFactura.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturacionsByTipoPersonaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where tipoPersona equals to DEFAULT_TIPO_PERSONA
        defaultFacturacionShouldBeFound("tipoPersona.equals=" + DEFAULT_TIPO_PERSONA);

        // Get all the facturacionList where tipoPersona equals to UPDATED_TIPO_PERSONA
        defaultFacturacionShouldNotBeFound("tipoPersona.equals=" + UPDATED_TIPO_PERSONA);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByTipoPersonaIsInShouldWork() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where tipoPersona in DEFAULT_TIPO_PERSONA or UPDATED_TIPO_PERSONA
        defaultFacturacionShouldBeFound("tipoPersona.in=" + DEFAULT_TIPO_PERSONA + "," + UPDATED_TIPO_PERSONA);

        // Get all the facturacionList where tipoPersona equals to UPDATED_TIPO_PERSONA
        defaultFacturacionShouldNotBeFound("tipoPersona.in=" + UPDATED_TIPO_PERSONA);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByTipoPersonaIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where tipoPersona is not null
        defaultFacturacionShouldBeFound("tipoPersona.specified=true");

        // Get all the facturacionList where tipoPersona is null
        defaultFacturacionShouldNotBeFound("tipoPersona.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturacionsByPeriodicidadFacturacionIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where periodicidadFacturacion equals to DEFAULT_PERIODICIDAD_FACTURACION
        defaultFacturacionShouldBeFound("periodicidadFacturacion.equals=" + DEFAULT_PERIODICIDAD_FACTURACION);

        // Get all the facturacionList where periodicidadFacturacion equals to UPDATED_PERIODICIDAD_FACTURACION
        defaultFacturacionShouldNotBeFound("periodicidadFacturacion.equals=" + UPDATED_PERIODICIDAD_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByPeriodicidadFacturacionIsInShouldWork() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where periodicidadFacturacion in DEFAULT_PERIODICIDAD_FACTURACION or UPDATED_PERIODICIDAD_FACTURACION
        defaultFacturacionShouldBeFound("periodicidadFacturacion.in=" + DEFAULT_PERIODICIDAD_FACTURACION + "," + UPDATED_PERIODICIDAD_FACTURACION);

        // Get all the facturacionList where periodicidadFacturacion equals to UPDATED_PERIODICIDAD_FACTURACION
        defaultFacturacionShouldNotBeFound("periodicidadFacturacion.in=" + UPDATED_PERIODICIDAD_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByPeriodicidadFacturacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where periodicidadFacturacion is not null
        defaultFacturacionShouldBeFound("periodicidadFacturacion.specified=true");

        // Get all the facturacionList where periodicidadFacturacion is null
        defaultFacturacionShouldNotBeFound("periodicidadFacturacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto equals to DEFAULT_MAXIMO_MONTO
        defaultFacturacionShouldBeFound("maximoMonto.equals=" + DEFAULT_MAXIMO_MONTO);

        // Get all the facturacionList where maximoMonto equals to UPDATED_MAXIMO_MONTO
        defaultFacturacionShouldNotBeFound("maximoMonto.equals=" + UPDATED_MAXIMO_MONTO);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsInShouldWork() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto in DEFAULT_MAXIMO_MONTO or UPDATED_MAXIMO_MONTO
        defaultFacturacionShouldBeFound("maximoMonto.in=" + DEFAULT_MAXIMO_MONTO + "," + UPDATED_MAXIMO_MONTO);

        // Get all the facturacionList where maximoMonto equals to UPDATED_MAXIMO_MONTO
        defaultFacturacionShouldNotBeFound("maximoMonto.in=" + UPDATED_MAXIMO_MONTO);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto is not null
        defaultFacturacionShouldBeFound("maximoMonto.specified=true");

        // Get all the facturacionList where maximoMonto is null
        defaultFacturacionShouldNotBeFound("maximoMonto.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto is greater than or equal to DEFAULT_MAXIMO_MONTO
        defaultFacturacionShouldBeFound("maximoMonto.greaterThanOrEqual=" + DEFAULT_MAXIMO_MONTO);

        // Get all the facturacionList where maximoMonto is greater than or equal to UPDATED_MAXIMO_MONTO
        defaultFacturacionShouldNotBeFound("maximoMonto.greaterThanOrEqual=" + UPDATED_MAXIMO_MONTO);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto is less than or equal to DEFAULT_MAXIMO_MONTO
        defaultFacturacionShouldBeFound("maximoMonto.lessThanOrEqual=" + DEFAULT_MAXIMO_MONTO);

        // Get all the facturacionList where maximoMonto is less than or equal to SMALLER_MAXIMO_MONTO
        defaultFacturacionShouldNotBeFound("maximoMonto.lessThanOrEqual=" + SMALLER_MAXIMO_MONTO);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsLessThanSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto is less than DEFAULT_MAXIMO_MONTO
        defaultFacturacionShouldNotBeFound("maximoMonto.lessThan=" + DEFAULT_MAXIMO_MONTO);

        // Get all the facturacionList where maximoMonto is less than UPDATED_MAXIMO_MONTO
        defaultFacturacionShouldBeFound("maximoMonto.lessThan=" + UPDATED_MAXIMO_MONTO);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByMaximoMontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where maximoMonto is greater than DEFAULT_MAXIMO_MONTO
        defaultFacturacionShouldNotBeFound("maximoMonto.greaterThan=" + DEFAULT_MAXIMO_MONTO);

        // Get all the facturacionList where maximoMonto is greater than SMALLER_MAXIMO_MONTO
        defaultFacturacionShouldBeFound("maximoMonto.greaterThan=" + SMALLER_MAXIMO_MONTO);
    }


    @Test
    @Transactional
    public void getAllFacturacionsByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor equals to DEFAULT_VALOR
        defaultFacturacionShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the facturacionList where valor equals to UPDATED_VALOR
        defaultFacturacionShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByValorIsInShouldWork() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultFacturacionShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the facturacionList where valor equals to UPDATED_VALOR
        defaultFacturacionShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor is not null
        defaultFacturacionShouldBeFound("valor.specified=true");

        // Get all the facturacionList where valor is null
        defaultFacturacionShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturacionsByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor is greater than or equal to DEFAULT_VALOR
        defaultFacturacionShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the facturacionList where valor is greater than or equal to UPDATED_VALOR
        defaultFacturacionShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor is less than or equal to DEFAULT_VALOR
        defaultFacturacionShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the facturacionList where valor is less than or equal to SMALLER_VALOR
        defaultFacturacionShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor is less than DEFAULT_VALOR
        defaultFacturacionShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the facturacionList where valor is less than UPDATED_VALOR
        defaultFacturacionShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllFacturacionsByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);

        // Get all the facturacionList where valor is greater than DEFAULT_VALOR
        defaultFacturacionShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the facturacionList where valor is greater than SMALLER_VALOR
        defaultFacturacionShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllFacturacionsByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        facturacion.setEmpresa(empresa);
        facturacionRepository.saveAndFlush(facturacion);
        Long empresaId = empresa.getId();

        // Get all the facturacionList where empresa equals to empresaId
        defaultFacturacionShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the facturacionList where empresa equals to empresaId + 1
        defaultFacturacionShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllFacturacionsByIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        facturacionRepository.saveAndFlush(facturacion);
        CuentaAsociada identificacion = CuentaAsociadaResourceIT.createEntity(em);
        em.persist(identificacion);
        em.flush();
        facturacion.setIdentificacion(identificacion);
        facturacionRepository.saveAndFlush(facturacion);
        Long identificacionId = identificacion.getId();

        // Get all the facturacionList where identificacion equals to identificacionId
        defaultFacturacionShouldBeFound("identificacionId.equals=" + identificacionId);

        // Get all the facturacionList where identificacion equals to identificacionId + 1
        defaultFacturacionShouldNotBeFound("identificacionId.equals=" + (identificacionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacturacionShouldBeFound(String filter) throws Exception {
        restFacturacionMockMvc.perform(get("/api/facturacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].titularFactura").value(hasItem(DEFAULT_TITULAR_FACTURA)))
            .andExpect(jsonPath("$.[*].tipoPersona").value(hasItem(DEFAULT_TIPO_PERSONA.toString())))
            .andExpect(jsonPath("$.[*].periodicidadFacturacion").value(hasItem(DEFAULT_PERIODICIDAD_FACTURACION.toString())))
            .andExpect(jsonPath("$.[*].maximoMonto").value(hasItem(DEFAULT_MAXIMO_MONTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));

        // Check, that the count call also returns 1
        restFacturacionMockMvc.perform(get("/api/facturacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacturacionShouldNotBeFound(String filter) throws Exception {
        restFacturacionMockMvc.perform(get("/api/facturacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacturacionMockMvc.perform(get("/api/facturacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFacturacion() throws Exception {
        // Get the facturacion
        restFacturacionMockMvc.perform(get("/api/facturacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacturacion() throws Exception {
        // Initialize the database
        facturacionService.save(facturacion);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFacturacionSearchRepository);

        int databaseSizeBeforeUpdate = facturacionRepository.findAll().size();

        // Update the facturacion
        Facturacion updatedFacturacion = facturacionRepository.findById(facturacion.getId()).get();
        // Disconnect from session so that the updates on updatedFacturacion are not directly saved in db
        em.detach(updatedFacturacion);
        updatedFacturacion
            .titularFactura(UPDATED_TITULAR_FACTURA)
            .tipoPersona(UPDATED_TIPO_PERSONA)
            .periodicidadFacturacion(UPDATED_PERIODICIDAD_FACTURACION)
            .maximoMonto(UPDATED_MAXIMO_MONTO)
            .valor(UPDATED_VALOR);

        restFacturacionMockMvc.perform(put("/api/facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacturacion)))
            .andExpect(status().isOk());

        // Validate the Facturacion in the database
        List<Facturacion> facturacionList = facturacionRepository.findAll();
        assertThat(facturacionList).hasSize(databaseSizeBeforeUpdate);
        Facturacion testFacturacion = facturacionList.get(facturacionList.size() - 1);
        assertThat(testFacturacion.getTitularFactura()).isEqualTo(UPDATED_TITULAR_FACTURA);
        assertThat(testFacturacion.getTipoPersona()).isEqualTo(UPDATED_TIPO_PERSONA);
        assertThat(testFacturacion.getPeriodicidadFacturacion()).isEqualTo(UPDATED_PERIODICIDAD_FACTURACION);
        assertThat(testFacturacion.getMaximoMonto()).isEqualTo(UPDATED_MAXIMO_MONTO);
        assertThat(testFacturacion.getValor()).isEqualTo(UPDATED_VALOR);

        // Validate the Facturacion in Elasticsearch
        verify(mockFacturacionSearchRepository, times(1)).save(testFacturacion);
    }

    @Test
    @Transactional
    public void updateNonExistingFacturacion() throws Exception {
        int databaseSizeBeforeUpdate = facturacionRepository.findAll().size();

        // Create the Facturacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturacionMockMvc.perform(put("/api/facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturacion)))
            .andExpect(status().isBadRequest());

        // Validate the Facturacion in the database
        List<Facturacion> facturacionList = facturacionRepository.findAll();
        assertThat(facturacionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Facturacion in Elasticsearch
        verify(mockFacturacionSearchRepository, times(0)).save(facturacion);
    }

    @Test
    @Transactional
    public void deleteFacturacion() throws Exception {
        // Initialize the database
        facturacionService.save(facturacion);

        int databaseSizeBeforeDelete = facturacionRepository.findAll().size();

        // Delete the facturacion
        restFacturacionMockMvc.perform(delete("/api/facturacions/{id}", facturacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facturacion> facturacionList = facturacionRepository.findAll();
        assertThat(facturacionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Facturacion in Elasticsearch
        verify(mockFacturacionSearchRepository, times(1)).deleteById(facturacion.getId());
    }

    @Test
    @Transactional
    public void searchFacturacion() throws Exception {
        // Initialize the database
        facturacionService.save(facturacion);
        when(mockFacturacionSearchRepository.search(queryStringQuery("id:" + facturacion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(facturacion), PageRequest.of(0, 1), 1));
        // Search the facturacion
        restFacturacionMockMvc.perform(get("/api/_search/facturacions?query=id:" + facturacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].titularFactura").value(hasItem(DEFAULT_TITULAR_FACTURA)))
            .andExpect(jsonPath("$.[*].tipoPersona").value(hasItem(DEFAULT_TIPO_PERSONA.toString())))
            .andExpect(jsonPath("$.[*].periodicidadFacturacion").value(hasItem(DEFAULT_PERIODICIDAD_FACTURACION.toString())))
            .andExpect(jsonPath("$.[*].maximoMonto").value(hasItem(DEFAULT_MAXIMO_MONTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facturacion.class);
        Facturacion facturacion1 = new Facturacion();
        facturacion1.setId(1L);
        Facturacion facturacion2 = new Facturacion();
        facturacion2.setId(facturacion1.getId());
        assertThat(facturacion1).isEqualTo(facturacion2);
        facturacion2.setId(2L);
        assertThat(facturacion1).isNotEqualTo(facturacion2);
        facturacion1.setId(null);
        assertThat(facturacion1).isNotEqualTo(facturacion2);
    }
}
