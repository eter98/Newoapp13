package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Landing;
import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.repository.LandingRepository;
import io.github.jhipster.newoapp13.repository.search.LandingSearchRepository;
import io.github.jhipster.newoapp13.service.LandingService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.LandingCriteria;
import io.github.jhipster.newoapp13.service.LandingQueryService;

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
import org.springframework.util.Base64Utils;
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

import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
/**
 * Integration tests for the {@link LandingResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class LandingResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_FACILIDADES = "AAAAAAAAAA";
    private static final String UPDATED_FACILIDADES = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_NEGOCIO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_NEGOCIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PUESTOS = 1;
    private static final Integer UPDATED_NUMERO_PUESTOS = 2;
    private static final Integer SMALLER_NUMERO_PUESTOS = 1 - 1;

    private static final Integer DEFAULT_TARIFA_MENSUAL = 1;
    private static final Integer UPDATED_TARIFA_MENSUAL = 2;
    private static final Integer SMALLER_TARIFA_MENSUAL = 1 - 1;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    private static final byte[] DEFAULT_IMAGEN_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_3_CONTENT_TYPE = "image/png";

    @Autowired
    private LandingRepository landingRepository;

    @Autowired
    private LandingService landingService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.LandingSearchRepositoryMockConfiguration
     */
    @Autowired
    private LandingSearchRepository mockLandingSearchRepository;

    @Autowired
    private LandingQueryService landingQueryService;

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

    private MockMvc restLandingMockMvc;

    private Landing landing;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LandingResource landingResource = new LandingResource(landingService, landingQueryService);
        this.restLandingMockMvc = MockMvcBuilders.standaloneSetup(landingResource)
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
    public static Landing createEntity(EntityManager em) {
        Landing landing = new Landing()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .facilidades(DEFAULT_FACILIDADES)
            .telefonoNegocio(DEFAULT_TELEFONO_NEGOCIO)
            .numeroPuestos(DEFAULT_NUMERO_PUESTOS)
            .tarifaMensual(DEFAULT_TARIFA_MENSUAL)
            .impuesto(DEFAULT_IMPUESTO)
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .imagen3(DEFAULT_IMAGEN_3)
            .imagen3ContentType(DEFAULT_IMAGEN_3_CONTENT_TYPE);
        return landing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Landing createUpdatedEntity(EntityManager em) {
        Landing landing = new Landing()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .facilidades(UPDATED_FACILIDADES)
            .telefonoNegocio(UPDATED_TELEFONO_NEGOCIO)
            .numeroPuestos(UPDATED_NUMERO_PUESTOS)
            .tarifaMensual(UPDATED_TARIFA_MENSUAL)
            .impuesto(UPDATED_IMPUESTO)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE);
        return landing;
    }

    @BeforeEach
    public void initTest() {
        landing = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanding() throws Exception {
        int databaseSizeBeforeCreate = landingRepository.findAll().size();

        // Create the Landing
        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isCreated());

        // Validate the Landing in the database
        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeCreate + 1);
        Landing testLanding = landingList.get(landingList.size() - 1);
        assertThat(testLanding.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLanding.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testLanding.getFacilidades()).isEqualTo(DEFAULT_FACILIDADES);
        assertThat(testLanding.getTelefonoNegocio()).isEqualTo(DEFAULT_TELEFONO_NEGOCIO);
        assertThat(testLanding.getNumeroPuestos()).isEqualTo(DEFAULT_NUMERO_PUESTOS);
        assertThat(testLanding.getTarifaMensual()).isEqualTo(DEFAULT_TARIFA_MENSUAL);
        assertThat(testLanding.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testLanding.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testLanding.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testLanding.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testLanding.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testLanding.getImagen3()).isEqualTo(DEFAULT_IMAGEN_3);
        assertThat(testLanding.getImagen3ContentType()).isEqualTo(DEFAULT_IMAGEN_3_CONTENT_TYPE);

        // Validate the Landing in Elasticsearch
        verify(mockLandingSearchRepository, times(1)).save(testLanding);
    }

    @Test
    @Transactional
    public void createLandingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = landingRepository.findAll().size();

        // Create the Landing with an existing ID
        landing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        // Validate the Landing in the database
        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Landing in Elasticsearch
        verify(mockLandingSearchRepository, times(0)).save(landing);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = landingRepository.findAll().size();
        // set the field null
        landing.setNombre(null);

        // Create the Landing, which fails.

        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = landingRepository.findAll().size();
        // set the field null
        landing.setDescripcion(null);

        // Create the Landing, which fails.

        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFacilidadesIsRequired() throws Exception {
        int databaseSizeBeforeTest = landingRepository.findAll().size();
        // set the field null
        landing.setFacilidades(null);

        // Create the Landing, which fails.

        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroPuestosIsRequired() throws Exception {
        int databaseSizeBeforeTest = landingRepository.findAll().size();
        // set the field null
        landing.setNumeroPuestos(null);

        // Create the Landing, which fails.

        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTarifaMensualIsRequired() throws Exception {
        int databaseSizeBeforeTest = landingRepository.findAll().size();
        // set the field null
        landing.setTarifaMensual(null);

        // Create the Landing, which fails.

        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImpuestoIsRequired() throws Exception {
        int databaseSizeBeforeTest = landingRepository.findAll().size();
        // set the field null
        landing.setImpuesto(null);

        // Create the Landing, which fails.

        restLandingMockMvc.perform(post("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLandings() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList
        restLandingMockMvc.perform(get("/api/landings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landing.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].facilidades").value(hasItem(DEFAULT_FACILIDADES.toString())))
            .andExpect(jsonPath("$.[*].telefonoNegocio").value(hasItem(DEFAULT_TELEFONO_NEGOCIO.toString())))
            .andExpect(jsonPath("$.[*].numeroPuestos").value(hasItem(DEFAULT_NUMERO_PUESTOS)))
            .andExpect(jsonPath("$.[*].tarifaMensual").value(hasItem(DEFAULT_TARIFA_MENSUAL)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))));
    }
    
    @Test
    @Transactional
    public void getLanding() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get the landing
        restLandingMockMvc.perform(get("/api/landings/{id}", landing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(landing.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.facilidades").value(DEFAULT_FACILIDADES.toString()))
            .andExpect(jsonPath("$.telefonoNegocio").value(DEFAULT_TELEFONO_NEGOCIO.toString()))
            .andExpect(jsonPath("$.numeroPuestos").value(DEFAULT_NUMERO_PUESTOS))
            .andExpect(jsonPath("$.tarifaMensual").value(DEFAULT_TARIFA_MENSUAL))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.imagen3ContentType").value(DEFAULT_IMAGEN_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen3").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_3)));
    }

    @Test
    @Transactional
    public void getAllLandingsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where nombre equals to DEFAULT_NOMBRE
        defaultLandingShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the landingList where nombre equals to UPDATED_NOMBRE
        defaultLandingShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllLandingsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultLandingShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the landingList where nombre equals to UPDATED_NOMBRE
        defaultLandingShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllLandingsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where nombre is not null
        defaultLandingShouldBeFound("nombre.specified=true");

        // Get all the landingList where nombre is null
        defaultLandingShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where descripcion equals to DEFAULT_DESCRIPCION
        defaultLandingShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the landingList where descripcion equals to UPDATED_DESCRIPCION
        defaultLandingShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllLandingsByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultLandingShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the landingList where descripcion equals to UPDATED_DESCRIPCION
        defaultLandingShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllLandingsByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where descripcion is not null
        defaultLandingShouldBeFound("descripcion.specified=true");

        // Get all the landingList where descripcion is null
        defaultLandingShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsByFacilidadesIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where facilidades equals to DEFAULT_FACILIDADES
        defaultLandingShouldBeFound("facilidades.equals=" + DEFAULT_FACILIDADES);

        // Get all the landingList where facilidades equals to UPDATED_FACILIDADES
        defaultLandingShouldNotBeFound("facilidades.equals=" + UPDATED_FACILIDADES);
    }

    @Test
    @Transactional
    public void getAllLandingsByFacilidadesIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where facilidades in DEFAULT_FACILIDADES or UPDATED_FACILIDADES
        defaultLandingShouldBeFound("facilidades.in=" + DEFAULT_FACILIDADES + "," + UPDATED_FACILIDADES);

        // Get all the landingList where facilidades equals to UPDATED_FACILIDADES
        defaultLandingShouldNotBeFound("facilidades.in=" + UPDATED_FACILIDADES);
    }

    @Test
    @Transactional
    public void getAllLandingsByFacilidadesIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where facilidades is not null
        defaultLandingShouldBeFound("facilidades.specified=true");

        // Get all the landingList where facilidades is null
        defaultLandingShouldNotBeFound("facilidades.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsByTelefonoNegocioIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where telefonoNegocio equals to DEFAULT_TELEFONO_NEGOCIO
        defaultLandingShouldBeFound("telefonoNegocio.equals=" + DEFAULT_TELEFONO_NEGOCIO);

        // Get all the landingList where telefonoNegocio equals to UPDATED_TELEFONO_NEGOCIO
        defaultLandingShouldNotBeFound("telefonoNegocio.equals=" + UPDATED_TELEFONO_NEGOCIO);
    }

    @Test
    @Transactional
    public void getAllLandingsByTelefonoNegocioIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where telefonoNegocio in DEFAULT_TELEFONO_NEGOCIO or UPDATED_TELEFONO_NEGOCIO
        defaultLandingShouldBeFound("telefonoNegocio.in=" + DEFAULT_TELEFONO_NEGOCIO + "," + UPDATED_TELEFONO_NEGOCIO);

        // Get all the landingList where telefonoNegocio equals to UPDATED_TELEFONO_NEGOCIO
        defaultLandingShouldNotBeFound("telefonoNegocio.in=" + UPDATED_TELEFONO_NEGOCIO);
    }

    @Test
    @Transactional
    public void getAllLandingsByTelefonoNegocioIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where telefonoNegocio is not null
        defaultLandingShouldBeFound("telefonoNegocio.specified=true");

        // Get all the landingList where telefonoNegocio is null
        defaultLandingShouldNotBeFound("telefonoNegocio.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos equals to DEFAULT_NUMERO_PUESTOS
        defaultLandingShouldBeFound("numeroPuestos.equals=" + DEFAULT_NUMERO_PUESTOS);

        // Get all the landingList where numeroPuestos equals to UPDATED_NUMERO_PUESTOS
        defaultLandingShouldNotBeFound("numeroPuestos.equals=" + UPDATED_NUMERO_PUESTOS);
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos in DEFAULT_NUMERO_PUESTOS or UPDATED_NUMERO_PUESTOS
        defaultLandingShouldBeFound("numeroPuestos.in=" + DEFAULT_NUMERO_PUESTOS + "," + UPDATED_NUMERO_PUESTOS);

        // Get all the landingList where numeroPuestos equals to UPDATED_NUMERO_PUESTOS
        defaultLandingShouldNotBeFound("numeroPuestos.in=" + UPDATED_NUMERO_PUESTOS);
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos is not null
        defaultLandingShouldBeFound("numeroPuestos.specified=true");

        // Get all the landingList where numeroPuestos is null
        defaultLandingShouldNotBeFound("numeroPuestos.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos is greater than or equal to DEFAULT_NUMERO_PUESTOS
        defaultLandingShouldBeFound("numeroPuestos.greaterThanOrEqual=" + DEFAULT_NUMERO_PUESTOS);

        // Get all the landingList where numeroPuestos is greater than or equal to UPDATED_NUMERO_PUESTOS
        defaultLandingShouldNotBeFound("numeroPuestos.greaterThanOrEqual=" + UPDATED_NUMERO_PUESTOS);
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos is less than or equal to DEFAULT_NUMERO_PUESTOS
        defaultLandingShouldBeFound("numeroPuestos.lessThanOrEqual=" + DEFAULT_NUMERO_PUESTOS);

        // Get all the landingList where numeroPuestos is less than or equal to SMALLER_NUMERO_PUESTOS
        defaultLandingShouldNotBeFound("numeroPuestos.lessThanOrEqual=" + SMALLER_NUMERO_PUESTOS);
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsLessThanSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos is less than DEFAULT_NUMERO_PUESTOS
        defaultLandingShouldNotBeFound("numeroPuestos.lessThan=" + DEFAULT_NUMERO_PUESTOS);

        // Get all the landingList where numeroPuestos is less than UPDATED_NUMERO_PUESTOS
        defaultLandingShouldBeFound("numeroPuestos.lessThan=" + UPDATED_NUMERO_PUESTOS);
    }

    @Test
    @Transactional
    public void getAllLandingsByNumeroPuestosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where numeroPuestos is greater than DEFAULT_NUMERO_PUESTOS
        defaultLandingShouldNotBeFound("numeroPuestos.greaterThan=" + DEFAULT_NUMERO_PUESTOS);

        // Get all the landingList where numeroPuestos is greater than SMALLER_NUMERO_PUESTOS
        defaultLandingShouldBeFound("numeroPuestos.greaterThan=" + SMALLER_NUMERO_PUESTOS);
    }


    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual equals to DEFAULT_TARIFA_MENSUAL
        defaultLandingShouldBeFound("tarifaMensual.equals=" + DEFAULT_TARIFA_MENSUAL);

        // Get all the landingList where tarifaMensual equals to UPDATED_TARIFA_MENSUAL
        defaultLandingShouldNotBeFound("tarifaMensual.equals=" + UPDATED_TARIFA_MENSUAL);
    }

    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual in DEFAULT_TARIFA_MENSUAL or UPDATED_TARIFA_MENSUAL
        defaultLandingShouldBeFound("tarifaMensual.in=" + DEFAULT_TARIFA_MENSUAL + "," + UPDATED_TARIFA_MENSUAL);

        // Get all the landingList where tarifaMensual equals to UPDATED_TARIFA_MENSUAL
        defaultLandingShouldNotBeFound("tarifaMensual.in=" + UPDATED_TARIFA_MENSUAL);
    }

    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual is not null
        defaultLandingShouldBeFound("tarifaMensual.specified=true");

        // Get all the landingList where tarifaMensual is null
        defaultLandingShouldNotBeFound("tarifaMensual.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual is greater than or equal to DEFAULT_TARIFA_MENSUAL
        defaultLandingShouldBeFound("tarifaMensual.greaterThanOrEqual=" + DEFAULT_TARIFA_MENSUAL);

        // Get all the landingList where tarifaMensual is greater than or equal to UPDATED_TARIFA_MENSUAL
        defaultLandingShouldNotBeFound("tarifaMensual.greaterThanOrEqual=" + UPDATED_TARIFA_MENSUAL);
    }

    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual is less than or equal to DEFAULT_TARIFA_MENSUAL
        defaultLandingShouldBeFound("tarifaMensual.lessThanOrEqual=" + DEFAULT_TARIFA_MENSUAL);

        // Get all the landingList where tarifaMensual is less than or equal to SMALLER_TARIFA_MENSUAL
        defaultLandingShouldNotBeFound("tarifaMensual.lessThanOrEqual=" + SMALLER_TARIFA_MENSUAL);
    }

    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsLessThanSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual is less than DEFAULT_TARIFA_MENSUAL
        defaultLandingShouldNotBeFound("tarifaMensual.lessThan=" + DEFAULT_TARIFA_MENSUAL);

        // Get all the landingList where tarifaMensual is less than UPDATED_TARIFA_MENSUAL
        defaultLandingShouldBeFound("tarifaMensual.lessThan=" + UPDATED_TARIFA_MENSUAL);
    }

    @Test
    @Transactional
    public void getAllLandingsByTarifaMensualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where tarifaMensual is greater than DEFAULT_TARIFA_MENSUAL
        defaultLandingShouldNotBeFound("tarifaMensual.greaterThan=" + DEFAULT_TARIFA_MENSUAL);

        // Get all the landingList where tarifaMensual is greater than SMALLER_TARIFA_MENSUAL
        defaultLandingShouldBeFound("tarifaMensual.greaterThan=" + SMALLER_TARIFA_MENSUAL);
    }


    @Test
    @Transactional
    public void getAllLandingsByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where impuesto equals to DEFAULT_IMPUESTO
        defaultLandingShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the landingList where impuesto equals to UPDATED_IMPUESTO
        defaultLandingShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllLandingsByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultLandingShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the landingList where impuesto equals to UPDATED_IMPUESTO
        defaultLandingShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllLandingsByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);

        // Get all the landingList where impuesto is not null
        defaultLandingShouldBeFound("impuesto.specified=true");

        // Get all the landingList where impuesto is null
        defaultLandingShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllLandingsBySedeIsEqualToSomething() throws Exception {
        // Initialize the database
        landingRepository.saveAndFlush(landing);
        Sedes sede = SedesResourceIT.createEntity(em);
        em.persist(sede);
        em.flush();
        landing.setSede(sede);
        landingRepository.saveAndFlush(landing);
        Long sedeId = sede.getId();

        // Get all the landingList where sede equals to sedeId
        defaultLandingShouldBeFound("sedeId.equals=" + sedeId);

        // Get all the landingList where sede equals to sedeId + 1
        defaultLandingShouldNotBeFound("sedeId.equals=" + (sedeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLandingShouldBeFound(String filter) throws Exception {
        restLandingMockMvc.perform(get("/api/landings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landing.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].facilidades").value(hasItem(DEFAULT_FACILIDADES)))
            .andExpect(jsonPath("$.[*].telefonoNegocio").value(hasItem(DEFAULT_TELEFONO_NEGOCIO)))
            .andExpect(jsonPath("$.[*].numeroPuestos").value(hasItem(DEFAULT_NUMERO_PUESTOS)))
            .andExpect(jsonPath("$.[*].tarifaMensual").value(hasItem(DEFAULT_TARIFA_MENSUAL)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))));

        // Check, that the count call also returns 1
        restLandingMockMvc.perform(get("/api/landings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLandingShouldNotBeFound(String filter) throws Exception {
        restLandingMockMvc.perform(get("/api/landings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLandingMockMvc.perform(get("/api/landings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLanding() throws Exception {
        // Get the landing
        restLandingMockMvc.perform(get("/api/landings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanding() throws Exception {
        // Initialize the database
        landingService.save(landing);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLandingSearchRepository);

        int databaseSizeBeforeUpdate = landingRepository.findAll().size();

        // Update the landing
        Landing updatedLanding = landingRepository.findById(landing.getId()).get();
        // Disconnect from session so that the updates on updatedLanding are not directly saved in db
        em.detach(updatedLanding);
        updatedLanding
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .facilidades(UPDATED_FACILIDADES)
            .telefonoNegocio(UPDATED_TELEFONO_NEGOCIO)
            .numeroPuestos(UPDATED_NUMERO_PUESTOS)
            .tarifaMensual(UPDATED_TARIFA_MENSUAL)
            .impuesto(UPDATED_IMPUESTO)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE);

        restLandingMockMvc.perform(put("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLanding)))
            .andExpect(status().isOk());

        // Validate the Landing in the database
        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeUpdate);
        Landing testLanding = landingList.get(landingList.size() - 1);
        assertThat(testLanding.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLanding.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testLanding.getFacilidades()).isEqualTo(UPDATED_FACILIDADES);
        assertThat(testLanding.getTelefonoNegocio()).isEqualTo(UPDATED_TELEFONO_NEGOCIO);
        assertThat(testLanding.getNumeroPuestos()).isEqualTo(UPDATED_NUMERO_PUESTOS);
        assertThat(testLanding.getTarifaMensual()).isEqualTo(UPDATED_TARIFA_MENSUAL);
        assertThat(testLanding.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testLanding.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testLanding.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testLanding.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testLanding.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testLanding.getImagen3()).isEqualTo(UPDATED_IMAGEN_3);
        assertThat(testLanding.getImagen3ContentType()).isEqualTo(UPDATED_IMAGEN_3_CONTENT_TYPE);

        // Validate the Landing in Elasticsearch
        verify(mockLandingSearchRepository, times(1)).save(testLanding);
    }

    @Test
    @Transactional
    public void updateNonExistingLanding() throws Exception {
        int databaseSizeBeforeUpdate = landingRepository.findAll().size();

        // Create the Landing

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLandingMockMvc.perform(put("/api/landings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(landing)))
            .andExpect(status().isBadRequest());

        // Validate the Landing in the database
        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Landing in Elasticsearch
        verify(mockLandingSearchRepository, times(0)).save(landing);
    }

    @Test
    @Transactional
    public void deleteLanding() throws Exception {
        // Initialize the database
        landingService.save(landing);

        int databaseSizeBeforeDelete = landingRepository.findAll().size();

        // Delete the landing
        restLandingMockMvc.perform(delete("/api/landings/{id}", landing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Landing> landingList = landingRepository.findAll();
        assertThat(landingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Landing in Elasticsearch
        verify(mockLandingSearchRepository, times(1)).deleteById(landing.getId());
    }

    @Test
    @Transactional
    public void searchLanding() throws Exception {
        // Initialize the database
        landingService.save(landing);
        when(mockLandingSearchRepository.search(queryStringQuery("id:" + landing.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(landing), PageRequest.of(0, 1), 1));
        // Search the landing
        restLandingMockMvc.perform(get("/api/_search/landings?query=id:" + landing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(landing.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].facilidades").value(hasItem(DEFAULT_FACILIDADES)))
            .andExpect(jsonPath("$.[*].telefonoNegocio").value(hasItem(DEFAULT_TELEFONO_NEGOCIO)))
            .andExpect(jsonPath("$.[*].numeroPuestos").value(hasItem(DEFAULT_NUMERO_PUESTOS)))
            .andExpect(jsonPath("$.[*].tarifaMensual").value(hasItem(DEFAULT_TARIFA_MENSUAL)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Landing.class);
        Landing landing1 = new Landing();
        landing1.setId(1L);
        Landing landing2 = new Landing();
        landing2.setId(landing1.getId());
        assertThat(landing1).isEqualTo(landing2);
        landing2.setId(2L);
        assertThat(landing1).isNotEqualTo(landing2);
        landing1.setId(null);
        assertThat(landing1).isNotEqualTo(landing2);
    }
}
