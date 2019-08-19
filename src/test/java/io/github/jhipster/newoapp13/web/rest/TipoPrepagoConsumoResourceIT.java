package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo;
import io.github.jhipster.newoapp13.domain.Beneficio;
import io.github.jhipster.newoapp13.repository.TipoPrepagoConsumoRepository;
import io.github.jhipster.newoapp13.repository.search.TipoPrepagoConsumoSearchRepository;
import io.github.jhipster.newoapp13.service.TipoPrepagoConsumoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.TipoPrepagoConsumoCriteria;
import io.github.jhipster.newoapp13.service.TipoPrepagoConsumoQueryService;

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
 * Integration tests for the {@link TipoPrepagoConsumoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class TipoPrepagoConsumoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR_MINIMO = 1;
    private static final Integer UPDATED_VALOR_MINIMO = 2;
    private static final Integer SMALLER_VALOR_MINIMO = 1 - 1;

    private static final Integer DEFAULT_VALOR_MAXIMO = 1;
    private static final Integer UPDATED_VALOR_MAXIMO = 2;
    private static final Integer SMALLER_VALOR_MAXIMO = 1 - 1;

    @Autowired
    private TipoPrepagoConsumoRepository tipoPrepagoConsumoRepository;

    @Autowired
    private TipoPrepagoConsumoService tipoPrepagoConsumoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.TipoPrepagoConsumoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoPrepagoConsumoSearchRepository mockTipoPrepagoConsumoSearchRepository;

    @Autowired
    private TipoPrepagoConsumoQueryService tipoPrepagoConsumoQueryService;

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

    private MockMvc restTipoPrepagoConsumoMockMvc;

    private TipoPrepagoConsumo tipoPrepagoConsumo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoPrepagoConsumoResource tipoPrepagoConsumoResource = new TipoPrepagoConsumoResource(tipoPrepagoConsumoService, tipoPrepagoConsumoQueryService);
        this.restTipoPrepagoConsumoMockMvc = MockMvcBuilders.standaloneSetup(tipoPrepagoConsumoResource)
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
    public static TipoPrepagoConsumo createEntity(EntityManager em) {
        TipoPrepagoConsumo tipoPrepagoConsumo = new TipoPrepagoConsumo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .valorMinimo(DEFAULT_VALOR_MINIMO)
            .valorMaximo(DEFAULT_VALOR_MAXIMO);
        return tipoPrepagoConsumo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPrepagoConsumo createUpdatedEntity(EntityManager em) {
        TipoPrepagoConsumo tipoPrepagoConsumo = new TipoPrepagoConsumo()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .valorMinimo(UPDATED_VALOR_MINIMO)
            .valorMaximo(UPDATED_VALOR_MAXIMO);
        return tipoPrepagoConsumo;
    }

    @BeforeEach
    public void initTest() {
        tipoPrepagoConsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoPrepagoConsumo() throws Exception {
        int databaseSizeBeforeCreate = tipoPrepagoConsumoRepository.findAll().size();

        // Create the TipoPrepagoConsumo
        restTipoPrepagoConsumoMockMvc.perform(post("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isCreated());

        // Validate the TipoPrepagoConsumo in the database
        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPrepagoConsumo testTipoPrepagoConsumo = tipoPrepagoConsumoList.get(tipoPrepagoConsumoList.size() - 1);
        assertThat(testTipoPrepagoConsumo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoPrepagoConsumo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTipoPrepagoConsumo.getValorMinimo()).isEqualTo(DEFAULT_VALOR_MINIMO);
        assertThat(testTipoPrepagoConsumo.getValorMaximo()).isEqualTo(DEFAULT_VALOR_MAXIMO);

        // Validate the TipoPrepagoConsumo in Elasticsearch
        verify(mockTipoPrepagoConsumoSearchRepository, times(1)).save(testTipoPrepagoConsumo);
    }

    @Test
    @Transactional
    public void createTipoPrepagoConsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoPrepagoConsumoRepository.findAll().size();

        // Create the TipoPrepagoConsumo with an existing ID
        tipoPrepagoConsumo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPrepagoConsumoMockMvc.perform(post("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPrepagoConsumo in the database
        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoPrepagoConsumo in Elasticsearch
        verify(mockTipoPrepagoConsumoSearchRepository, times(0)).save(tipoPrepagoConsumo);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPrepagoConsumoRepository.findAll().size();
        // set the field null
        tipoPrepagoConsumo.setNombre(null);

        // Create the TipoPrepagoConsumo, which fails.

        restTipoPrepagoConsumoMockMvc.perform(post("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isBadRequest());

        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPrepagoConsumoRepository.findAll().size();
        // set the field null
        tipoPrepagoConsumo.setDescripcion(null);

        // Create the TipoPrepagoConsumo, which fails.

        restTipoPrepagoConsumoMockMvc.perform(post("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isBadRequest());

        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorMinimoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPrepagoConsumoRepository.findAll().size();
        // set the field null
        tipoPrepagoConsumo.setValorMinimo(null);

        // Create the TipoPrepagoConsumo, which fails.

        restTipoPrepagoConsumoMockMvc.perform(post("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isBadRequest());

        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorMaximoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPrepagoConsumoRepository.findAll().size();
        // set the field null
        tipoPrepagoConsumo.setValorMaximo(null);

        // Create the TipoPrepagoConsumo, which fails.

        restTipoPrepagoConsumoMockMvc.perform(post("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isBadRequest());

        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumos() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPrepagoConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].valorMinimo").value(hasItem(DEFAULT_VALOR_MINIMO)))
            .andExpect(jsonPath("$.[*].valorMaximo").value(hasItem(DEFAULT_VALOR_MAXIMO)));
    }
    
    @Test
    @Transactional
    public void getTipoPrepagoConsumo() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get the tipoPrepagoConsumo
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos/{id}", tipoPrepagoConsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPrepagoConsumo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.valorMinimo").value(DEFAULT_VALOR_MINIMO))
            .andExpect(jsonPath("$.valorMaximo").value(DEFAULT_VALOR_MAXIMO));
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where nombre equals to DEFAULT_NOMBRE
        defaultTipoPrepagoConsumoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tipoPrepagoConsumoList where nombre equals to UPDATED_NOMBRE
        defaultTipoPrepagoConsumoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTipoPrepagoConsumoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tipoPrepagoConsumoList where nombre equals to UPDATED_NOMBRE
        defaultTipoPrepagoConsumoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where nombre is not null
        defaultTipoPrepagoConsumoShouldBeFound("nombre.specified=true");

        // Get all the tipoPrepagoConsumoList where nombre is null
        defaultTipoPrepagoConsumoShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTipoPrepagoConsumoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoPrepagoConsumoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoPrepagoConsumoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTipoPrepagoConsumoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tipoPrepagoConsumoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoPrepagoConsumoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where descripcion is not null
        defaultTipoPrepagoConsumoShouldBeFound("descripcion.specified=true");

        // Get all the tipoPrepagoConsumoList where descripcion is null
        defaultTipoPrepagoConsumoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo equals to DEFAULT_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.equals=" + DEFAULT_VALOR_MINIMO);

        // Get all the tipoPrepagoConsumoList where valorMinimo equals to UPDATED_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.equals=" + UPDATED_VALOR_MINIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo in DEFAULT_VALOR_MINIMO or UPDATED_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.in=" + DEFAULT_VALOR_MINIMO + "," + UPDATED_VALOR_MINIMO);

        // Get all the tipoPrepagoConsumoList where valorMinimo equals to UPDATED_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.in=" + UPDATED_VALOR_MINIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo is not null
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.specified=true");

        // Get all the tipoPrepagoConsumoList where valorMinimo is null
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo is greater than or equal to DEFAULT_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.greaterThanOrEqual=" + DEFAULT_VALOR_MINIMO);

        // Get all the tipoPrepagoConsumoList where valorMinimo is greater than or equal to UPDATED_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.greaterThanOrEqual=" + UPDATED_VALOR_MINIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo is less than or equal to DEFAULT_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.lessThanOrEqual=" + DEFAULT_VALOR_MINIMO);

        // Get all the tipoPrepagoConsumoList where valorMinimo is less than or equal to SMALLER_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.lessThanOrEqual=" + SMALLER_VALOR_MINIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsLessThanSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo is less than DEFAULT_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.lessThan=" + DEFAULT_VALOR_MINIMO);

        // Get all the tipoPrepagoConsumoList where valorMinimo is less than UPDATED_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.lessThan=" + UPDATED_VALOR_MINIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMinimoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMinimo is greater than DEFAULT_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMinimo.greaterThan=" + DEFAULT_VALOR_MINIMO);

        // Get all the tipoPrepagoConsumoList where valorMinimo is greater than SMALLER_VALOR_MINIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMinimo.greaterThan=" + SMALLER_VALOR_MINIMO);
    }


    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo equals to DEFAULT_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.equals=" + DEFAULT_VALOR_MAXIMO);

        // Get all the tipoPrepagoConsumoList where valorMaximo equals to UPDATED_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.equals=" + UPDATED_VALOR_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo in DEFAULT_VALOR_MAXIMO or UPDATED_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.in=" + DEFAULT_VALOR_MAXIMO + "," + UPDATED_VALOR_MAXIMO);

        // Get all the tipoPrepagoConsumoList where valorMaximo equals to UPDATED_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.in=" + UPDATED_VALOR_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo is not null
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.specified=true");

        // Get all the tipoPrepagoConsumoList where valorMaximo is null
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo is greater than or equal to DEFAULT_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.greaterThanOrEqual=" + DEFAULT_VALOR_MAXIMO);

        // Get all the tipoPrepagoConsumoList where valorMaximo is greater than or equal to UPDATED_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.greaterThanOrEqual=" + UPDATED_VALOR_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo is less than or equal to DEFAULT_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.lessThanOrEqual=" + DEFAULT_VALOR_MAXIMO);

        // Get all the tipoPrepagoConsumoList where valorMaximo is less than or equal to SMALLER_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.lessThanOrEqual=" + SMALLER_VALOR_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsLessThanSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo is less than DEFAULT_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.lessThan=" + DEFAULT_VALOR_MAXIMO);

        // Get all the tipoPrepagoConsumoList where valorMaximo is less than UPDATED_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.lessThan=" + UPDATED_VALOR_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByValorMaximoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);

        // Get all the tipoPrepagoConsumoList where valorMaximo is greater than DEFAULT_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldNotBeFound("valorMaximo.greaterThan=" + DEFAULT_VALOR_MAXIMO);

        // Get all the tipoPrepagoConsumoList where valorMaximo is greater than SMALLER_VALOR_MAXIMO
        defaultTipoPrepagoConsumoShouldBeFound("valorMaximo.greaterThan=" + SMALLER_VALOR_MAXIMO);
    }


    @Test
    @Transactional
    public void getAllTipoPrepagoConsumosByTipoBeneficioIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);
        Beneficio tipoBeneficio = BeneficioResourceIT.createEntity(em);
        em.persist(tipoBeneficio);
        em.flush();
        tipoPrepagoConsumo.setTipoBeneficio(tipoBeneficio);
        tipoPrepagoConsumoRepository.saveAndFlush(tipoPrepagoConsumo);
        Long tipoBeneficioId = tipoBeneficio.getId();

        // Get all the tipoPrepagoConsumoList where tipoBeneficio equals to tipoBeneficioId
        defaultTipoPrepagoConsumoShouldBeFound("tipoBeneficioId.equals=" + tipoBeneficioId);

        // Get all the tipoPrepagoConsumoList where tipoBeneficio equals to tipoBeneficioId + 1
        defaultTipoPrepagoConsumoShouldNotBeFound("tipoBeneficioId.equals=" + (tipoBeneficioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoPrepagoConsumoShouldBeFound(String filter) throws Exception {
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPrepagoConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].valorMinimo").value(hasItem(DEFAULT_VALOR_MINIMO)))
            .andExpect(jsonPath("$.[*].valorMaximo").value(hasItem(DEFAULT_VALOR_MAXIMO)));

        // Check, that the count call also returns 1
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoPrepagoConsumoShouldNotBeFound(String filter) throws Exception {
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoPrepagoConsumo() throws Exception {
        // Get the tipoPrepagoConsumo
        restTipoPrepagoConsumoMockMvc.perform(get("/api/tipo-prepago-consumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoPrepagoConsumo() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoService.save(tipoPrepagoConsumo);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTipoPrepagoConsumoSearchRepository);

        int databaseSizeBeforeUpdate = tipoPrepagoConsumoRepository.findAll().size();

        // Update the tipoPrepagoConsumo
        TipoPrepagoConsumo updatedTipoPrepagoConsumo = tipoPrepagoConsumoRepository.findById(tipoPrepagoConsumo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPrepagoConsumo are not directly saved in db
        em.detach(updatedTipoPrepagoConsumo);
        updatedTipoPrepagoConsumo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .valorMinimo(UPDATED_VALOR_MINIMO)
            .valorMaximo(UPDATED_VALOR_MAXIMO);

        restTipoPrepagoConsumoMockMvc.perform(put("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoPrepagoConsumo)))
            .andExpect(status().isOk());

        // Validate the TipoPrepagoConsumo in the database
        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeUpdate);
        TipoPrepagoConsumo testTipoPrepagoConsumo = tipoPrepagoConsumoList.get(tipoPrepagoConsumoList.size() - 1);
        assertThat(testTipoPrepagoConsumo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoPrepagoConsumo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTipoPrepagoConsumo.getValorMinimo()).isEqualTo(UPDATED_VALOR_MINIMO);
        assertThat(testTipoPrepagoConsumo.getValorMaximo()).isEqualTo(UPDATED_VALOR_MAXIMO);

        // Validate the TipoPrepagoConsumo in Elasticsearch
        verify(mockTipoPrepagoConsumoSearchRepository, times(1)).save(testTipoPrepagoConsumo);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoPrepagoConsumo() throws Exception {
        int databaseSizeBeforeUpdate = tipoPrepagoConsumoRepository.findAll().size();

        // Create the TipoPrepagoConsumo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPrepagoConsumoMockMvc.perform(put("/api/tipo-prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPrepagoConsumo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPrepagoConsumo in the database
        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoPrepagoConsumo in Elasticsearch
        verify(mockTipoPrepagoConsumoSearchRepository, times(0)).save(tipoPrepagoConsumo);
    }

    @Test
    @Transactional
    public void deleteTipoPrepagoConsumo() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoService.save(tipoPrepagoConsumo);

        int databaseSizeBeforeDelete = tipoPrepagoConsumoRepository.findAll().size();

        // Delete the tipoPrepagoConsumo
        restTipoPrepagoConsumoMockMvc.perform(delete("/api/tipo-prepago-consumos/{id}", tipoPrepagoConsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPrepagoConsumo> tipoPrepagoConsumoList = tipoPrepagoConsumoRepository.findAll();
        assertThat(tipoPrepagoConsumoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoPrepagoConsumo in Elasticsearch
        verify(mockTipoPrepagoConsumoSearchRepository, times(1)).deleteById(tipoPrepagoConsumo.getId());
    }

    @Test
    @Transactional
    public void searchTipoPrepagoConsumo() throws Exception {
        // Initialize the database
        tipoPrepagoConsumoService.save(tipoPrepagoConsumo);
        when(mockTipoPrepagoConsumoSearchRepository.search(queryStringQuery("id:" + tipoPrepagoConsumo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoPrepagoConsumo), PageRequest.of(0, 1), 1));
        // Search the tipoPrepagoConsumo
        restTipoPrepagoConsumoMockMvc.perform(get("/api/_search/tipo-prepago-consumos?query=id:" + tipoPrepagoConsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPrepagoConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].valorMinimo").value(hasItem(DEFAULT_VALOR_MINIMO)))
            .andExpect(jsonPath("$.[*].valorMaximo").value(hasItem(DEFAULT_VALOR_MAXIMO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPrepagoConsumo.class);
        TipoPrepagoConsumo tipoPrepagoConsumo1 = new TipoPrepagoConsumo();
        tipoPrepagoConsumo1.setId(1L);
        TipoPrepagoConsumo tipoPrepagoConsumo2 = new TipoPrepagoConsumo();
        tipoPrepagoConsumo2.setId(tipoPrepagoConsumo1.getId());
        assertThat(tipoPrepagoConsumo1).isEqualTo(tipoPrepagoConsumo2);
        tipoPrepagoConsumo2.setId(2L);
        assertThat(tipoPrepagoConsumo1).isNotEqualTo(tipoPrepagoConsumo2);
        tipoPrepagoConsumo1.setId(null);
        assertThat(tipoPrepagoConsumo1).isNotEqualTo(tipoPrepagoConsumo2);
    }
}
