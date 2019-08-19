package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.repository.EspaciosReservaRepository;
import io.github.jhipster.newoapp13.repository.search.EspaciosReservaSearchRepository;
import io.github.jhipster.newoapp13.service.EspaciosReservaService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.EspaciosReservaCriteria;
import io.github.jhipster.newoapp13.service.EspaciosReservaQueryService;

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
 * Integration tests for the {@link EspaciosReservaResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class EspaciosReservaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_FACILIDADES = "AAAAAAAAAA";
    private static final String UPDATED_FACILIDADES = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACIDAD = 1;
    private static final Integer UPDATED_CAPACIDAD = 2;
    private static final Integer SMALLER_CAPACIDAD = 1 - 1;

    private static final String DEFAULT_APERTURA = "AAAAA";
    private static final String UPDATED_APERTURA = "BBBBB";

    private static final String DEFAULT_CIERRE = "AAAAA";
    private static final String UPDATED_CIERRE = "BBBBB";

    private static final String DEFAULT_WIFI = "AAAAAAAAAA";
    private static final String UPDATED_WIFI = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

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

    private static final byte[] DEFAULT_IMAGEN_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_4_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_5 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_5 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_5_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_5_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_6 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_6 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_6_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_6_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_TARIFA_1_HORA = 1;
    private static final Integer UPDATED_TARIFA_1_HORA = 2;
    private static final Integer SMALLER_TARIFA_1_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_2_HORA = 1;
    private static final Integer UPDATED_TARIFA_2_HORA = 2;
    private static final Integer SMALLER_TARIFA_2_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_3_HORA = 1;
    private static final Integer UPDATED_TARIFA_3_HORA = 2;
    private static final Integer SMALLER_TARIFA_3_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_4_HORA = 1;
    private static final Integer UPDATED_TARIFA_4_HORA = 2;
    private static final Integer SMALLER_TARIFA_4_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_5_HORA = 1;
    private static final Integer UPDATED_TARIFA_5_HORA = 2;
    private static final Integer SMALLER_TARIFA_5_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_6_HORA = 1;
    private static final Integer UPDATED_TARIFA_6_HORA = 2;
    private static final Integer SMALLER_TARIFA_6_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_7_HORA = 1;
    private static final Integer UPDATED_TARIFA_7_HORA = 2;
    private static final Integer SMALLER_TARIFA_7_HORA = 1 - 1;

    private static final Integer DEFAULT_TARIFA_8_HORA = 1;
    private static final Integer UPDATED_TARIFA_8_HORA = 2;
    private static final Integer SMALLER_TARIFA_8_HORA = 1 - 1;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    @Autowired
    private EspaciosReservaRepository espaciosReservaRepository;

    @Autowired
    private EspaciosReservaService espaciosReservaService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.EspaciosReservaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EspaciosReservaSearchRepository mockEspaciosReservaSearchRepository;

    @Autowired
    private EspaciosReservaQueryService espaciosReservaQueryService;

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

    private MockMvc restEspaciosReservaMockMvc;

    private EspaciosReserva espaciosReserva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspaciosReservaResource espaciosReservaResource = new EspaciosReservaResource(espaciosReservaService, espaciosReservaQueryService);
        this.restEspaciosReservaMockMvc = MockMvcBuilders.standaloneSetup(espaciosReservaResource)
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
    public static EspaciosReserva createEntity(EntityManager em) {
        EspaciosReserva espaciosReserva = new EspaciosReserva()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .facilidades(DEFAULT_FACILIDADES)
            .capacidad(DEFAULT_CAPACIDAD)
            .apertura(DEFAULT_APERTURA)
            .cierre(DEFAULT_CIERRE)
            .wifi(DEFAULT_WIFI)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .imagen3(DEFAULT_IMAGEN_3)
            .imagen3ContentType(DEFAULT_IMAGEN_3_CONTENT_TYPE)
            .imagen4(DEFAULT_IMAGEN_4)
            .imagen4ContentType(DEFAULT_IMAGEN_4_CONTENT_TYPE)
            .imagen5(DEFAULT_IMAGEN_5)
            .imagen5ContentType(DEFAULT_IMAGEN_5_CONTENT_TYPE)
            .imagen6(DEFAULT_IMAGEN_6)
            .imagen6ContentType(DEFAULT_IMAGEN_6_CONTENT_TYPE)
            .tarifa1Hora(DEFAULT_TARIFA_1_HORA)
            .tarifa2Hora(DEFAULT_TARIFA_2_HORA)
            .tarifa3Hora(DEFAULT_TARIFA_3_HORA)
            .tarifa4Hora(DEFAULT_TARIFA_4_HORA)
            .tarifa5Hora(DEFAULT_TARIFA_5_HORA)
            .tarifa6Hora(DEFAULT_TARIFA_6_HORA)
            .tarifa7Hora(DEFAULT_TARIFA_7_HORA)
            .tarifa8Hora(DEFAULT_TARIFA_8_HORA)
            .impuesto(DEFAULT_IMPUESTO);
        return espaciosReserva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspaciosReserva createUpdatedEntity(EntityManager em) {
        EspaciosReserva espaciosReserva = new EspaciosReserva()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .facilidades(UPDATED_FACILIDADES)
            .capacidad(UPDATED_CAPACIDAD)
            .apertura(UPDATED_APERTURA)
            .cierre(UPDATED_CIERRE)
            .wifi(UPDATED_WIFI)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE)
            .tarifa1Hora(UPDATED_TARIFA_1_HORA)
            .tarifa2Hora(UPDATED_TARIFA_2_HORA)
            .tarifa3Hora(UPDATED_TARIFA_3_HORA)
            .tarifa4Hora(UPDATED_TARIFA_4_HORA)
            .tarifa5Hora(UPDATED_TARIFA_5_HORA)
            .tarifa6Hora(UPDATED_TARIFA_6_HORA)
            .tarifa7Hora(UPDATED_TARIFA_7_HORA)
            .tarifa8Hora(UPDATED_TARIFA_8_HORA)
            .impuesto(UPDATED_IMPUESTO);
        return espaciosReserva;
    }

    @BeforeEach
    public void initTest() {
        espaciosReserva = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspaciosReserva() throws Exception {
        int databaseSizeBeforeCreate = espaciosReservaRepository.findAll().size();

        // Create the EspaciosReserva
        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isCreated());

        // Validate the EspaciosReserva in the database
        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeCreate + 1);
        EspaciosReserva testEspaciosReserva = espaciosReservaList.get(espaciosReservaList.size() - 1);
        assertThat(testEspaciosReserva.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEspaciosReserva.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEspaciosReserva.getFacilidades()).isEqualTo(DEFAULT_FACILIDADES);
        assertThat(testEspaciosReserva.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
        assertThat(testEspaciosReserva.getApertura()).isEqualTo(DEFAULT_APERTURA);
        assertThat(testEspaciosReserva.getCierre()).isEqualTo(DEFAULT_CIERRE);
        assertThat(testEspaciosReserva.getWifi()).isEqualTo(DEFAULT_WIFI);
        assertThat(testEspaciosReserva.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testEspaciosReserva.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testEspaciosReserva.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testEspaciosReserva.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen3()).isEqualTo(DEFAULT_IMAGEN_3);
        assertThat(testEspaciosReserva.getImagen3ContentType()).isEqualTo(DEFAULT_IMAGEN_3_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen4()).isEqualTo(DEFAULT_IMAGEN_4);
        assertThat(testEspaciosReserva.getImagen4ContentType()).isEqualTo(DEFAULT_IMAGEN_4_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen5()).isEqualTo(DEFAULT_IMAGEN_5);
        assertThat(testEspaciosReserva.getImagen5ContentType()).isEqualTo(DEFAULT_IMAGEN_5_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen6()).isEqualTo(DEFAULT_IMAGEN_6);
        assertThat(testEspaciosReserva.getImagen6ContentType()).isEqualTo(DEFAULT_IMAGEN_6_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getTarifa1Hora()).isEqualTo(DEFAULT_TARIFA_1_HORA);
        assertThat(testEspaciosReserva.getTarifa2Hora()).isEqualTo(DEFAULT_TARIFA_2_HORA);
        assertThat(testEspaciosReserva.getTarifa3Hora()).isEqualTo(DEFAULT_TARIFA_3_HORA);
        assertThat(testEspaciosReserva.getTarifa4Hora()).isEqualTo(DEFAULT_TARIFA_4_HORA);
        assertThat(testEspaciosReserva.getTarifa5Hora()).isEqualTo(DEFAULT_TARIFA_5_HORA);
        assertThat(testEspaciosReserva.getTarifa6Hora()).isEqualTo(DEFAULT_TARIFA_6_HORA);
        assertThat(testEspaciosReserva.getTarifa7Hora()).isEqualTo(DEFAULT_TARIFA_7_HORA);
        assertThat(testEspaciosReserva.getTarifa8Hora()).isEqualTo(DEFAULT_TARIFA_8_HORA);
        assertThat(testEspaciosReserva.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);

        // Validate the EspaciosReserva in Elasticsearch
        verify(mockEspaciosReservaSearchRepository, times(1)).save(testEspaciosReserva);
    }

    @Test
    @Transactional
    public void createEspaciosReservaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = espaciosReservaRepository.findAll().size();

        // Create the EspaciosReserva with an existing ID
        espaciosReserva.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        // Validate the EspaciosReserva in the database
        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeCreate);

        // Validate the EspaciosReserva in Elasticsearch
        verify(mockEspaciosReservaSearchRepository, times(0)).save(espaciosReserva);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaciosReservaRepository.findAll().size();
        // set the field null
        espaciosReserva.setNombre(null);

        // Create the EspaciosReserva, which fails.

        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaciosReservaRepository.findAll().size();
        // set the field null
        espaciosReserva.setDescripcion(null);

        // Create the EspaciosReserva, which fails.

        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFacilidadesIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaciosReservaRepository.findAll().size();
        // set the field null
        espaciosReserva.setFacilidades(null);

        // Create the EspaciosReserva, which fails.

        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaciosReservaRepository.findAll().size();
        // set the field null
        espaciosReserva.setCapacidad(null);

        // Create the EspaciosReserva, which fails.

        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAperturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaciosReservaRepository.findAll().size();
        // set the field null
        espaciosReserva.setApertura(null);

        // Create the EspaciosReserva, which fails.

        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCierreIsRequired() throws Exception {
        int databaseSizeBeforeTest = espaciosReservaRepository.findAll().size();
        // set the field null
        espaciosReserva.setCierre(null);

        // Create the EspaciosReserva, which fails.

        restEspaciosReservaMockMvc.perform(post("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservas() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espaciosReserva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].facilidades").value(hasItem(DEFAULT_FACILIDADES.toString())))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD)))
            .andExpect(jsonPath("$.[*].apertura").value(hasItem(DEFAULT_APERTURA.toString())))
            .andExpect(jsonPath("$.[*].cierre").value(hasItem(DEFAULT_CIERRE.toString())))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))))
            .andExpect(jsonPath("$.[*].tarifa1Hora").value(hasItem(DEFAULT_TARIFA_1_HORA)))
            .andExpect(jsonPath("$.[*].tarifa2Hora").value(hasItem(DEFAULT_TARIFA_2_HORA)))
            .andExpect(jsonPath("$.[*].tarifa3Hora").value(hasItem(DEFAULT_TARIFA_3_HORA)))
            .andExpect(jsonPath("$.[*].tarifa4Hora").value(hasItem(DEFAULT_TARIFA_4_HORA)))
            .andExpect(jsonPath("$.[*].tarifa5Hora").value(hasItem(DEFAULT_TARIFA_5_HORA)))
            .andExpect(jsonPath("$.[*].tarifa6Hora").value(hasItem(DEFAULT_TARIFA_6_HORA)))
            .andExpect(jsonPath("$.[*].tarifa7Hora").value(hasItem(DEFAULT_TARIFA_7_HORA)))
            .andExpect(jsonPath("$.[*].tarifa8Hora").value(hasItem(DEFAULT_TARIFA_8_HORA)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())));
    }
    
    @Test
    @Transactional
    public void getEspaciosReserva() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get the espaciosReserva
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas/{id}", espaciosReserva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(espaciosReserva.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.facilidades").value(DEFAULT_FACILIDADES.toString()))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD))
            .andExpect(jsonPath("$.apertura").value(DEFAULT_APERTURA.toString()))
            .andExpect(jsonPath("$.cierre").value(DEFAULT_CIERRE.toString()))
            .andExpect(jsonPath("$.wifi").value(DEFAULT_WIFI.toString()))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.imagen3ContentType").value(DEFAULT_IMAGEN_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen3").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_3)))
            .andExpect(jsonPath("$.imagen4ContentType").value(DEFAULT_IMAGEN_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen4").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_4)))
            .andExpect(jsonPath("$.imagen5ContentType").value(DEFAULT_IMAGEN_5_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen5").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_5)))
            .andExpect(jsonPath("$.imagen6ContentType").value(DEFAULT_IMAGEN_6_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen6").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_6)))
            .andExpect(jsonPath("$.tarifa1Hora").value(DEFAULT_TARIFA_1_HORA))
            .andExpect(jsonPath("$.tarifa2Hora").value(DEFAULT_TARIFA_2_HORA))
            .andExpect(jsonPath("$.tarifa3Hora").value(DEFAULT_TARIFA_3_HORA))
            .andExpect(jsonPath("$.tarifa4Hora").value(DEFAULT_TARIFA_4_HORA))
            .andExpect(jsonPath("$.tarifa5Hora").value(DEFAULT_TARIFA_5_HORA))
            .andExpect(jsonPath("$.tarifa6Hora").value(DEFAULT_TARIFA_6_HORA))
            .andExpect(jsonPath("$.tarifa7Hora").value(DEFAULT_TARIFA_7_HORA))
            .andExpect(jsonPath("$.tarifa8Hora").value(DEFAULT_TARIFA_8_HORA))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()));
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where nombre equals to DEFAULT_NOMBRE
        defaultEspaciosReservaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the espaciosReservaList where nombre equals to UPDATED_NOMBRE
        defaultEspaciosReservaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultEspaciosReservaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the espaciosReservaList where nombre equals to UPDATED_NOMBRE
        defaultEspaciosReservaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where nombre is not null
        defaultEspaciosReservaShouldBeFound("nombre.specified=true");

        // Get all the espaciosReservaList where nombre is null
        defaultEspaciosReservaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultEspaciosReservaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the espaciosReservaList where descripcion equals to UPDATED_DESCRIPCION
        defaultEspaciosReservaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultEspaciosReservaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the espaciosReservaList where descripcion equals to UPDATED_DESCRIPCION
        defaultEspaciosReservaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where descripcion is not null
        defaultEspaciosReservaShouldBeFound("descripcion.specified=true");

        // Get all the espaciosReservaList where descripcion is null
        defaultEspaciosReservaShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByFacilidadesIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where facilidades equals to DEFAULT_FACILIDADES
        defaultEspaciosReservaShouldBeFound("facilidades.equals=" + DEFAULT_FACILIDADES);

        // Get all the espaciosReservaList where facilidades equals to UPDATED_FACILIDADES
        defaultEspaciosReservaShouldNotBeFound("facilidades.equals=" + UPDATED_FACILIDADES);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByFacilidadesIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where facilidades in DEFAULT_FACILIDADES or UPDATED_FACILIDADES
        defaultEspaciosReservaShouldBeFound("facilidades.in=" + DEFAULT_FACILIDADES + "," + UPDATED_FACILIDADES);

        // Get all the espaciosReservaList where facilidades equals to UPDATED_FACILIDADES
        defaultEspaciosReservaShouldNotBeFound("facilidades.in=" + UPDATED_FACILIDADES);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByFacilidadesIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where facilidades is not null
        defaultEspaciosReservaShouldBeFound("facilidades.specified=true");

        // Get all the espaciosReservaList where facilidades is null
        defaultEspaciosReservaShouldNotBeFound("facilidades.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad equals to DEFAULT_CAPACIDAD
        defaultEspaciosReservaShouldBeFound("capacidad.equals=" + DEFAULT_CAPACIDAD);

        // Get all the espaciosReservaList where capacidad equals to UPDATED_CAPACIDAD
        defaultEspaciosReservaShouldNotBeFound("capacidad.equals=" + UPDATED_CAPACIDAD);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad in DEFAULT_CAPACIDAD or UPDATED_CAPACIDAD
        defaultEspaciosReservaShouldBeFound("capacidad.in=" + DEFAULT_CAPACIDAD + "," + UPDATED_CAPACIDAD);

        // Get all the espaciosReservaList where capacidad equals to UPDATED_CAPACIDAD
        defaultEspaciosReservaShouldNotBeFound("capacidad.in=" + UPDATED_CAPACIDAD);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad is not null
        defaultEspaciosReservaShouldBeFound("capacidad.specified=true");

        // Get all the espaciosReservaList where capacidad is null
        defaultEspaciosReservaShouldNotBeFound("capacidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad is greater than or equal to DEFAULT_CAPACIDAD
        defaultEspaciosReservaShouldBeFound("capacidad.greaterThanOrEqual=" + DEFAULT_CAPACIDAD);

        // Get all the espaciosReservaList where capacidad is greater than or equal to UPDATED_CAPACIDAD
        defaultEspaciosReservaShouldNotBeFound("capacidad.greaterThanOrEqual=" + UPDATED_CAPACIDAD);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad is less than or equal to DEFAULT_CAPACIDAD
        defaultEspaciosReservaShouldBeFound("capacidad.lessThanOrEqual=" + DEFAULT_CAPACIDAD);

        // Get all the espaciosReservaList where capacidad is less than or equal to SMALLER_CAPACIDAD
        defaultEspaciosReservaShouldNotBeFound("capacidad.lessThanOrEqual=" + SMALLER_CAPACIDAD);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad is less than DEFAULT_CAPACIDAD
        defaultEspaciosReservaShouldNotBeFound("capacidad.lessThan=" + DEFAULT_CAPACIDAD);

        // Get all the espaciosReservaList where capacidad is less than UPDATED_CAPACIDAD
        defaultEspaciosReservaShouldBeFound("capacidad.lessThan=" + UPDATED_CAPACIDAD);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCapacidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where capacidad is greater than DEFAULT_CAPACIDAD
        defaultEspaciosReservaShouldNotBeFound("capacidad.greaterThan=" + DEFAULT_CAPACIDAD);

        // Get all the espaciosReservaList where capacidad is greater than SMALLER_CAPACIDAD
        defaultEspaciosReservaShouldBeFound("capacidad.greaterThan=" + SMALLER_CAPACIDAD);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByAperturaIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where apertura equals to DEFAULT_APERTURA
        defaultEspaciosReservaShouldBeFound("apertura.equals=" + DEFAULT_APERTURA);

        // Get all the espaciosReservaList where apertura equals to UPDATED_APERTURA
        defaultEspaciosReservaShouldNotBeFound("apertura.equals=" + UPDATED_APERTURA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByAperturaIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where apertura in DEFAULT_APERTURA or UPDATED_APERTURA
        defaultEspaciosReservaShouldBeFound("apertura.in=" + DEFAULT_APERTURA + "," + UPDATED_APERTURA);

        // Get all the espaciosReservaList where apertura equals to UPDATED_APERTURA
        defaultEspaciosReservaShouldNotBeFound("apertura.in=" + UPDATED_APERTURA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByAperturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where apertura is not null
        defaultEspaciosReservaShouldBeFound("apertura.specified=true");

        // Get all the espaciosReservaList where apertura is null
        defaultEspaciosReservaShouldNotBeFound("apertura.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCierreIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where cierre equals to DEFAULT_CIERRE
        defaultEspaciosReservaShouldBeFound("cierre.equals=" + DEFAULT_CIERRE);

        // Get all the espaciosReservaList where cierre equals to UPDATED_CIERRE
        defaultEspaciosReservaShouldNotBeFound("cierre.equals=" + UPDATED_CIERRE);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCierreIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where cierre in DEFAULT_CIERRE or UPDATED_CIERRE
        defaultEspaciosReservaShouldBeFound("cierre.in=" + DEFAULT_CIERRE + "," + UPDATED_CIERRE);

        // Get all the espaciosReservaList where cierre equals to UPDATED_CIERRE
        defaultEspaciosReservaShouldNotBeFound("cierre.in=" + UPDATED_CIERRE);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByCierreIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where cierre is not null
        defaultEspaciosReservaShouldBeFound("cierre.specified=true");

        // Get all the espaciosReservaList where cierre is null
        defaultEspaciosReservaShouldNotBeFound("cierre.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByWifiIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where wifi equals to DEFAULT_WIFI
        defaultEspaciosReservaShouldBeFound("wifi.equals=" + DEFAULT_WIFI);

        // Get all the espaciosReservaList where wifi equals to UPDATED_WIFI
        defaultEspaciosReservaShouldNotBeFound("wifi.equals=" + UPDATED_WIFI);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByWifiIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where wifi in DEFAULT_WIFI or UPDATED_WIFI
        defaultEspaciosReservaShouldBeFound("wifi.in=" + DEFAULT_WIFI + "," + UPDATED_WIFI);

        // Get all the espaciosReservaList where wifi equals to UPDATED_WIFI
        defaultEspaciosReservaShouldNotBeFound("wifi.in=" + UPDATED_WIFI);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByWifiIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where wifi is not null
        defaultEspaciosReservaShouldBeFound("wifi.specified=true");

        // Get all the espaciosReservaList where wifi is null
        defaultEspaciosReservaShouldNotBeFound("wifi.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora equals to DEFAULT_TARIFA_1_HORA
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.equals=" + DEFAULT_TARIFA_1_HORA);

        // Get all the espaciosReservaList where tarifa1Hora equals to UPDATED_TARIFA_1_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.equals=" + UPDATED_TARIFA_1_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora in DEFAULT_TARIFA_1_HORA or UPDATED_TARIFA_1_HORA
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.in=" + DEFAULT_TARIFA_1_HORA + "," + UPDATED_TARIFA_1_HORA);

        // Get all the espaciosReservaList where tarifa1Hora equals to UPDATED_TARIFA_1_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.in=" + UPDATED_TARIFA_1_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.specified=true");

        // Get all the espaciosReservaList where tarifa1Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora is greater than or equal to DEFAULT_TARIFA_1_HORA
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_1_HORA);

        // Get all the espaciosReservaList where tarifa1Hora is greater than or equal to UPDATED_TARIFA_1_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.greaterThanOrEqual=" + UPDATED_TARIFA_1_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora is less than or equal to DEFAULT_TARIFA_1_HORA
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.lessThanOrEqual=" + DEFAULT_TARIFA_1_HORA);

        // Get all the espaciosReservaList where tarifa1Hora is less than or equal to SMALLER_TARIFA_1_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.lessThanOrEqual=" + SMALLER_TARIFA_1_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora is less than DEFAULT_TARIFA_1_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.lessThan=" + DEFAULT_TARIFA_1_HORA);

        // Get all the espaciosReservaList where tarifa1Hora is less than UPDATED_TARIFA_1_HORA
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.lessThan=" + UPDATED_TARIFA_1_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa1HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa1Hora is greater than DEFAULT_TARIFA_1_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa1Hora.greaterThan=" + DEFAULT_TARIFA_1_HORA);

        // Get all the espaciosReservaList where tarifa1Hora is greater than SMALLER_TARIFA_1_HORA
        defaultEspaciosReservaShouldBeFound("tarifa1Hora.greaterThan=" + SMALLER_TARIFA_1_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora equals to DEFAULT_TARIFA_2_HORA
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.equals=" + DEFAULT_TARIFA_2_HORA);

        // Get all the espaciosReservaList where tarifa2Hora equals to UPDATED_TARIFA_2_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.equals=" + UPDATED_TARIFA_2_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora in DEFAULT_TARIFA_2_HORA or UPDATED_TARIFA_2_HORA
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.in=" + DEFAULT_TARIFA_2_HORA + "," + UPDATED_TARIFA_2_HORA);

        // Get all the espaciosReservaList where tarifa2Hora equals to UPDATED_TARIFA_2_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.in=" + UPDATED_TARIFA_2_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.specified=true");

        // Get all the espaciosReservaList where tarifa2Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora is greater than or equal to DEFAULT_TARIFA_2_HORA
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_2_HORA);

        // Get all the espaciosReservaList where tarifa2Hora is greater than or equal to UPDATED_TARIFA_2_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.greaterThanOrEqual=" + UPDATED_TARIFA_2_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora is less than or equal to DEFAULT_TARIFA_2_HORA
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.lessThanOrEqual=" + DEFAULT_TARIFA_2_HORA);

        // Get all the espaciosReservaList where tarifa2Hora is less than or equal to SMALLER_TARIFA_2_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.lessThanOrEqual=" + SMALLER_TARIFA_2_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora is less than DEFAULT_TARIFA_2_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.lessThan=" + DEFAULT_TARIFA_2_HORA);

        // Get all the espaciosReservaList where tarifa2Hora is less than UPDATED_TARIFA_2_HORA
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.lessThan=" + UPDATED_TARIFA_2_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa2HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa2Hora is greater than DEFAULT_TARIFA_2_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa2Hora.greaterThan=" + DEFAULT_TARIFA_2_HORA);

        // Get all the espaciosReservaList where tarifa2Hora is greater than SMALLER_TARIFA_2_HORA
        defaultEspaciosReservaShouldBeFound("tarifa2Hora.greaterThan=" + SMALLER_TARIFA_2_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora equals to DEFAULT_TARIFA_3_HORA
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.equals=" + DEFAULT_TARIFA_3_HORA);

        // Get all the espaciosReservaList where tarifa3Hora equals to UPDATED_TARIFA_3_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.equals=" + UPDATED_TARIFA_3_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora in DEFAULT_TARIFA_3_HORA or UPDATED_TARIFA_3_HORA
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.in=" + DEFAULT_TARIFA_3_HORA + "," + UPDATED_TARIFA_3_HORA);

        // Get all the espaciosReservaList where tarifa3Hora equals to UPDATED_TARIFA_3_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.in=" + UPDATED_TARIFA_3_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.specified=true");

        // Get all the espaciosReservaList where tarifa3Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora is greater than or equal to DEFAULT_TARIFA_3_HORA
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_3_HORA);

        // Get all the espaciosReservaList where tarifa3Hora is greater than or equal to UPDATED_TARIFA_3_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.greaterThanOrEqual=" + UPDATED_TARIFA_3_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora is less than or equal to DEFAULT_TARIFA_3_HORA
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.lessThanOrEqual=" + DEFAULT_TARIFA_3_HORA);

        // Get all the espaciosReservaList where tarifa3Hora is less than or equal to SMALLER_TARIFA_3_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.lessThanOrEqual=" + SMALLER_TARIFA_3_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora is less than DEFAULT_TARIFA_3_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.lessThan=" + DEFAULT_TARIFA_3_HORA);

        // Get all the espaciosReservaList where tarifa3Hora is less than UPDATED_TARIFA_3_HORA
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.lessThan=" + UPDATED_TARIFA_3_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa3HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa3Hora is greater than DEFAULT_TARIFA_3_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa3Hora.greaterThan=" + DEFAULT_TARIFA_3_HORA);

        // Get all the espaciosReservaList where tarifa3Hora is greater than SMALLER_TARIFA_3_HORA
        defaultEspaciosReservaShouldBeFound("tarifa3Hora.greaterThan=" + SMALLER_TARIFA_3_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora equals to DEFAULT_TARIFA_4_HORA
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.equals=" + DEFAULT_TARIFA_4_HORA);

        // Get all the espaciosReservaList where tarifa4Hora equals to UPDATED_TARIFA_4_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.equals=" + UPDATED_TARIFA_4_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora in DEFAULT_TARIFA_4_HORA or UPDATED_TARIFA_4_HORA
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.in=" + DEFAULT_TARIFA_4_HORA + "," + UPDATED_TARIFA_4_HORA);

        // Get all the espaciosReservaList where tarifa4Hora equals to UPDATED_TARIFA_4_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.in=" + UPDATED_TARIFA_4_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.specified=true");

        // Get all the espaciosReservaList where tarifa4Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora is greater than or equal to DEFAULT_TARIFA_4_HORA
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_4_HORA);

        // Get all the espaciosReservaList where tarifa4Hora is greater than or equal to UPDATED_TARIFA_4_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.greaterThanOrEqual=" + UPDATED_TARIFA_4_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora is less than or equal to DEFAULT_TARIFA_4_HORA
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.lessThanOrEqual=" + DEFAULT_TARIFA_4_HORA);

        // Get all the espaciosReservaList where tarifa4Hora is less than or equal to SMALLER_TARIFA_4_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.lessThanOrEqual=" + SMALLER_TARIFA_4_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora is less than DEFAULT_TARIFA_4_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.lessThan=" + DEFAULT_TARIFA_4_HORA);

        // Get all the espaciosReservaList where tarifa4Hora is less than UPDATED_TARIFA_4_HORA
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.lessThan=" + UPDATED_TARIFA_4_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa4HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa4Hora is greater than DEFAULT_TARIFA_4_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa4Hora.greaterThan=" + DEFAULT_TARIFA_4_HORA);

        // Get all the espaciosReservaList where tarifa4Hora is greater than SMALLER_TARIFA_4_HORA
        defaultEspaciosReservaShouldBeFound("tarifa4Hora.greaterThan=" + SMALLER_TARIFA_4_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora equals to DEFAULT_TARIFA_5_HORA
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.equals=" + DEFAULT_TARIFA_5_HORA);

        // Get all the espaciosReservaList where tarifa5Hora equals to UPDATED_TARIFA_5_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.equals=" + UPDATED_TARIFA_5_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora in DEFAULT_TARIFA_5_HORA or UPDATED_TARIFA_5_HORA
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.in=" + DEFAULT_TARIFA_5_HORA + "," + UPDATED_TARIFA_5_HORA);

        // Get all the espaciosReservaList where tarifa5Hora equals to UPDATED_TARIFA_5_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.in=" + UPDATED_TARIFA_5_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.specified=true");

        // Get all the espaciosReservaList where tarifa5Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora is greater than or equal to DEFAULT_TARIFA_5_HORA
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_5_HORA);

        // Get all the espaciosReservaList where tarifa5Hora is greater than or equal to UPDATED_TARIFA_5_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.greaterThanOrEqual=" + UPDATED_TARIFA_5_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora is less than or equal to DEFAULT_TARIFA_5_HORA
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.lessThanOrEqual=" + DEFAULT_TARIFA_5_HORA);

        // Get all the espaciosReservaList where tarifa5Hora is less than or equal to SMALLER_TARIFA_5_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.lessThanOrEqual=" + SMALLER_TARIFA_5_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora is less than DEFAULT_TARIFA_5_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.lessThan=" + DEFAULT_TARIFA_5_HORA);

        // Get all the espaciosReservaList where tarifa5Hora is less than UPDATED_TARIFA_5_HORA
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.lessThan=" + UPDATED_TARIFA_5_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa5HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa5Hora is greater than DEFAULT_TARIFA_5_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa5Hora.greaterThan=" + DEFAULT_TARIFA_5_HORA);

        // Get all the espaciosReservaList where tarifa5Hora is greater than SMALLER_TARIFA_5_HORA
        defaultEspaciosReservaShouldBeFound("tarifa5Hora.greaterThan=" + SMALLER_TARIFA_5_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora equals to DEFAULT_TARIFA_6_HORA
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.equals=" + DEFAULT_TARIFA_6_HORA);

        // Get all the espaciosReservaList where tarifa6Hora equals to UPDATED_TARIFA_6_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.equals=" + UPDATED_TARIFA_6_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora in DEFAULT_TARIFA_6_HORA or UPDATED_TARIFA_6_HORA
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.in=" + DEFAULT_TARIFA_6_HORA + "," + UPDATED_TARIFA_6_HORA);

        // Get all the espaciosReservaList where tarifa6Hora equals to UPDATED_TARIFA_6_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.in=" + UPDATED_TARIFA_6_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.specified=true");

        // Get all the espaciosReservaList where tarifa6Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora is greater than or equal to DEFAULT_TARIFA_6_HORA
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_6_HORA);

        // Get all the espaciosReservaList where tarifa6Hora is greater than or equal to UPDATED_TARIFA_6_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.greaterThanOrEqual=" + UPDATED_TARIFA_6_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora is less than or equal to DEFAULT_TARIFA_6_HORA
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.lessThanOrEqual=" + DEFAULT_TARIFA_6_HORA);

        // Get all the espaciosReservaList where tarifa6Hora is less than or equal to SMALLER_TARIFA_6_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.lessThanOrEqual=" + SMALLER_TARIFA_6_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora is less than DEFAULT_TARIFA_6_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.lessThan=" + DEFAULT_TARIFA_6_HORA);

        // Get all the espaciosReservaList where tarifa6Hora is less than UPDATED_TARIFA_6_HORA
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.lessThan=" + UPDATED_TARIFA_6_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa6HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa6Hora is greater than DEFAULT_TARIFA_6_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa6Hora.greaterThan=" + DEFAULT_TARIFA_6_HORA);

        // Get all the espaciosReservaList where tarifa6Hora is greater than SMALLER_TARIFA_6_HORA
        defaultEspaciosReservaShouldBeFound("tarifa6Hora.greaterThan=" + SMALLER_TARIFA_6_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora equals to DEFAULT_TARIFA_7_HORA
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.equals=" + DEFAULT_TARIFA_7_HORA);

        // Get all the espaciosReservaList where tarifa7Hora equals to UPDATED_TARIFA_7_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.equals=" + UPDATED_TARIFA_7_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora in DEFAULT_TARIFA_7_HORA or UPDATED_TARIFA_7_HORA
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.in=" + DEFAULT_TARIFA_7_HORA + "," + UPDATED_TARIFA_7_HORA);

        // Get all the espaciosReservaList where tarifa7Hora equals to UPDATED_TARIFA_7_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.in=" + UPDATED_TARIFA_7_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.specified=true");

        // Get all the espaciosReservaList where tarifa7Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora is greater than or equal to DEFAULT_TARIFA_7_HORA
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_7_HORA);

        // Get all the espaciosReservaList where tarifa7Hora is greater than or equal to UPDATED_TARIFA_7_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.greaterThanOrEqual=" + UPDATED_TARIFA_7_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora is less than or equal to DEFAULT_TARIFA_7_HORA
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.lessThanOrEqual=" + DEFAULT_TARIFA_7_HORA);

        // Get all the espaciosReservaList where tarifa7Hora is less than or equal to SMALLER_TARIFA_7_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.lessThanOrEqual=" + SMALLER_TARIFA_7_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora is less than DEFAULT_TARIFA_7_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.lessThan=" + DEFAULT_TARIFA_7_HORA);

        // Get all the espaciosReservaList where tarifa7Hora is less than UPDATED_TARIFA_7_HORA
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.lessThan=" + UPDATED_TARIFA_7_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa7HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa7Hora is greater than DEFAULT_TARIFA_7_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa7Hora.greaterThan=" + DEFAULT_TARIFA_7_HORA);

        // Get all the espaciosReservaList where tarifa7Hora is greater than SMALLER_TARIFA_7_HORA
        defaultEspaciosReservaShouldBeFound("tarifa7Hora.greaterThan=" + SMALLER_TARIFA_7_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora equals to DEFAULT_TARIFA_8_HORA
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.equals=" + DEFAULT_TARIFA_8_HORA);

        // Get all the espaciosReservaList where tarifa8Hora equals to UPDATED_TARIFA_8_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.equals=" + UPDATED_TARIFA_8_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora in DEFAULT_TARIFA_8_HORA or UPDATED_TARIFA_8_HORA
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.in=" + DEFAULT_TARIFA_8_HORA + "," + UPDATED_TARIFA_8_HORA);

        // Get all the espaciosReservaList where tarifa8Hora equals to UPDATED_TARIFA_8_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.in=" + UPDATED_TARIFA_8_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora is not null
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.specified=true");

        // Get all the espaciosReservaList where tarifa8Hora is null
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora is greater than or equal to DEFAULT_TARIFA_8_HORA
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.greaterThanOrEqual=" + DEFAULT_TARIFA_8_HORA);

        // Get all the espaciosReservaList where tarifa8Hora is greater than or equal to UPDATED_TARIFA_8_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.greaterThanOrEqual=" + UPDATED_TARIFA_8_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora is less than or equal to DEFAULT_TARIFA_8_HORA
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.lessThanOrEqual=" + DEFAULT_TARIFA_8_HORA);

        // Get all the espaciosReservaList where tarifa8Hora is less than or equal to SMALLER_TARIFA_8_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.lessThanOrEqual=" + SMALLER_TARIFA_8_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsLessThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora is less than DEFAULT_TARIFA_8_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.lessThan=" + DEFAULT_TARIFA_8_HORA);

        // Get all the espaciosReservaList where tarifa8Hora is less than UPDATED_TARIFA_8_HORA
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.lessThan=" + UPDATED_TARIFA_8_HORA);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByTarifa8HoraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where tarifa8Hora is greater than DEFAULT_TARIFA_8_HORA
        defaultEspaciosReservaShouldNotBeFound("tarifa8Hora.greaterThan=" + DEFAULT_TARIFA_8_HORA);

        // Get all the espaciosReservaList where tarifa8Hora is greater than SMALLER_TARIFA_8_HORA
        defaultEspaciosReservaShouldBeFound("tarifa8Hora.greaterThan=" + SMALLER_TARIFA_8_HORA);
    }


    @Test
    @Transactional
    public void getAllEspaciosReservasByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where impuesto equals to DEFAULT_IMPUESTO
        defaultEspaciosReservaShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the espaciosReservaList where impuesto equals to UPDATED_IMPUESTO
        defaultEspaciosReservaShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultEspaciosReservaShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the espaciosReservaList where impuesto equals to UPDATED_IMPUESTO
        defaultEspaciosReservaShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);

        // Get all the espaciosReservaList where impuesto is not null
        defaultEspaciosReservaShouldBeFound("impuesto.specified=true");

        // Get all the espaciosReservaList where impuesto is null
        defaultEspaciosReservaShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspaciosReservasBySedeIsEqualToSomething() throws Exception {
        // Initialize the database
        espaciosReservaRepository.saveAndFlush(espaciosReserva);
        Sedes sede = SedesResourceIT.createEntity(em);
        em.persist(sede);
        em.flush();
        espaciosReserva.setSede(sede);
        espaciosReservaRepository.saveAndFlush(espaciosReserva);
        Long sedeId = sede.getId();

        // Get all the espaciosReservaList where sede equals to sedeId
        defaultEspaciosReservaShouldBeFound("sedeId.equals=" + sedeId);

        // Get all the espaciosReservaList where sede equals to sedeId + 1
        defaultEspaciosReservaShouldNotBeFound("sedeId.equals=" + (sedeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEspaciosReservaShouldBeFound(String filter) throws Exception {
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espaciosReserva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].facilidades").value(hasItem(DEFAULT_FACILIDADES)))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD)))
            .andExpect(jsonPath("$.[*].apertura").value(hasItem(DEFAULT_APERTURA)))
            .andExpect(jsonPath("$.[*].cierre").value(hasItem(DEFAULT_CIERRE)))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI)))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))))
            .andExpect(jsonPath("$.[*].tarifa1Hora").value(hasItem(DEFAULT_TARIFA_1_HORA)))
            .andExpect(jsonPath("$.[*].tarifa2Hora").value(hasItem(DEFAULT_TARIFA_2_HORA)))
            .andExpect(jsonPath("$.[*].tarifa3Hora").value(hasItem(DEFAULT_TARIFA_3_HORA)))
            .andExpect(jsonPath("$.[*].tarifa4Hora").value(hasItem(DEFAULT_TARIFA_4_HORA)))
            .andExpect(jsonPath("$.[*].tarifa5Hora").value(hasItem(DEFAULT_TARIFA_5_HORA)))
            .andExpect(jsonPath("$.[*].tarifa6Hora").value(hasItem(DEFAULT_TARIFA_6_HORA)))
            .andExpect(jsonPath("$.[*].tarifa7Hora").value(hasItem(DEFAULT_TARIFA_7_HORA)))
            .andExpect(jsonPath("$.[*].tarifa8Hora").value(hasItem(DEFAULT_TARIFA_8_HORA)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())));

        // Check, that the count call also returns 1
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEspaciosReservaShouldNotBeFound(String filter) throws Exception {
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEspaciosReserva() throws Exception {
        // Get the espaciosReserva
        restEspaciosReservaMockMvc.perform(get("/api/espacios-reservas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspaciosReserva() throws Exception {
        // Initialize the database
        espaciosReservaService.save(espaciosReserva);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEspaciosReservaSearchRepository);

        int databaseSizeBeforeUpdate = espaciosReservaRepository.findAll().size();

        // Update the espaciosReserva
        EspaciosReserva updatedEspaciosReserva = espaciosReservaRepository.findById(espaciosReserva.getId()).get();
        // Disconnect from session so that the updates on updatedEspaciosReserva are not directly saved in db
        em.detach(updatedEspaciosReserva);
        updatedEspaciosReserva
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .facilidades(UPDATED_FACILIDADES)
            .capacidad(UPDATED_CAPACIDAD)
            .apertura(UPDATED_APERTURA)
            .cierre(UPDATED_CIERRE)
            .wifi(UPDATED_WIFI)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE)
            .tarifa1Hora(UPDATED_TARIFA_1_HORA)
            .tarifa2Hora(UPDATED_TARIFA_2_HORA)
            .tarifa3Hora(UPDATED_TARIFA_3_HORA)
            .tarifa4Hora(UPDATED_TARIFA_4_HORA)
            .tarifa5Hora(UPDATED_TARIFA_5_HORA)
            .tarifa6Hora(UPDATED_TARIFA_6_HORA)
            .tarifa7Hora(UPDATED_TARIFA_7_HORA)
            .tarifa8Hora(UPDATED_TARIFA_8_HORA)
            .impuesto(UPDATED_IMPUESTO);

        restEspaciosReservaMockMvc.perform(put("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEspaciosReserva)))
            .andExpect(status().isOk());

        // Validate the EspaciosReserva in the database
        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeUpdate);
        EspaciosReserva testEspaciosReserva = espaciosReservaList.get(espaciosReservaList.size() - 1);
        assertThat(testEspaciosReserva.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEspaciosReserva.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEspaciosReserva.getFacilidades()).isEqualTo(UPDATED_FACILIDADES);
        assertThat(testEspaciosReserva.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
        assertThat(testEspaciosReserva.getApertura()).isEqualTo(UPDATED_APERTURA);
        assertThat(testEspaciosReserva.getCierre()).isEqualTo(UPDATED_CIERRE);
        assertThat(testEspaciosReserva.getWifi()).isEqualTo(UPDATED_WIFI);
        assertThat(testEspaciosReserva.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testEspaciosReserva.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testEspaciosReserva.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testEspaciosReserva.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen3()).isEqualTo(UPDATED_IMAGEN_3);
        assertThat(testEspaciosReserva.getImagen3ContentType()).isEqualTo(UPDATED_IMAGEN_3_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen4()).isEqualTo(UPDATED_IMAGEN_4);
        assertThat(testEspaciosReserva.getImagen4ContentType()).isEqualTo(UPDATED_IMAGEN_4_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen5()).isEqualTo(UPDATED_IMAGEN_5);
        assertThat(testEspaciosReserva.getImagen5ContentType()).isEqualTo(UPDATED_IMAGEN_5_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getImagen6()).isEqualTo(UPDATED_IMAGEN_6);
        assertThat(testEspaciosReserva.getImagen6ContentType()).isEqualTo(UPDATED_IMAGEN_6_CONTENT_TYPE);
        assertThat(testEspaciosReserva.getTarifa1Hora()).isEqualTo(UPDATED_TARIFA_1_HORA);
        assertThat(testEspaciosReserva.getTarifa2Hora()).isEqualTo(UPDATED_TARIFA_2_HORA);
        assertThat(testEspaciosReserva.getTarifa3Hora()).isEqualTo(UPDATED_TARIFA_3_HORA);
        assertThat(testEspaciosReserva.getTarifa4Hora()).isEqualTo(UPDATED_TARIFA_4_HORA);
        assertThat(testEspaciosReserva.getTarifa5Hora()).isEqualTo(UPDATED_TARIFA_5_HORA);
        assertThat(testEspaciosReserva.getTarifa6Hora()).isEqualTo(UPDATED_TARIFA_6_HORA);
        assertThat(testEspaciosReserva.getTarifa7Hora()).isEqualTo(UPDATED_TARIFA_7_HORA);
        assertThat(testEspaciosReserva.getTarifa8Hora()).isEqualTo(UPDATED_TARIFA_8_HORA);
        assertThat(testEspaciosReserva.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);

        // Validate the EspaciosReserva in Elasticsearch
        verify(mockEspaciosReservaSearchRepository, times(1)).save(testEspaciosReserva);
    }

    @Test
    @Transactional
    public void updateNonExistingEspaciosReserva() throws Exception {
        int databaseSizeBeforeUpdate = espaciosReservaRepository.findAll().size();

        // Create the EspaciosReserva

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspaciosReservaMockMvc.perform(put("/api/espacios-reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espaciosReserva)))
            .andExpect(status().isBadRequest());

        // Validate the EspaciosReserva in the database
        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EspaciosReserva in Elasticsearch
        verify(mockEspaciosReservaSearchRepository, times(0)).save(espaciosReserva);
    }

    @Test
    @Transactional
    public void deleteEspaciosReserva() throws Exception {
        // Initialize the database
        espaciosReservaService.save(espaciosReserva);

        int databaseSizeBeforeDelete = espaciosReservaRepository.findAll().size();

        // Delete the espaciosReserva
        restEspaciosReservaMockMvc.perform(delete("/api/espacios-reservas/{id}", espaciosReserva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EspaciosReserva> espaciosReservaList = espaciosReservaRepository.findAll();
        assertThat(espaciosReservaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EspaciosReserva in Elasticsearch
        verify(mockEspaciosReservaSearchRepository, times(1)).deleteById(espaciosReserva.getId());
    }

    @Test
    @Transactional
    public void searchEspaciosReserva() throws Exception {
        // Initialize the database
        espaciosReservaService.save(espaciosReserva);
        when(mockEspaciosReservaSearchRepository.search(queryStringQuery("id:" + espaciosReserva.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(espaciosReserva), PageRequest.of(0, 1), 1));
        // Search the espaciosReserva
        restEspaciosReservaMockMvc.perform(get("/api/_search/espacios-reservas?query=id:" + espaciosReserva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espaciosReserva.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].facilidades").value(hasItem(DEFAULT_FACILIDADES)))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD)))
            .andExpect(jsonPath("$.[*].apertura").value(hasItem(DEFAULT_APERTURA)))
            .andExpect(jsonPath("$.[*].cierre").value(hasItem(DEFAULT_CIERRE)))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI)))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))))
            .andExpect(jsonPath("$.[*].tarifa1Hora").value(hasItem(DEFAULT_TARIFA_1_HORA)))
            .andExpect(jsonPath("$.[*].tarifa2Hora").value(hasItem(DEFAULT_TARIFA_2_HORA)))
            .andExpect(jsonPath("$.[*].tarifa3Hora").value(hasItem(DEFAULT_TARIFA_3_HORA)))
            .andExpect(jsonPath("$.[*].tarifa4Hora").value(hasItem(DEFAULT_TARIFA_4_HORA)))
            .andExpect(jsonPath("$.[*].tarifa5Hora").value(hasItem(DEFAULT_TARIFA_5_HORA)))
            .andExpect(jsonPath("$.[*].tarifa6Hora").value(hasItem(DEFAULT_TARIFA_6_HORA)))
            .andExpect(jsonPath("$.[*].tarifa7Hora").value(hasItem(DEFAULT_TARIFA_7_HORA)))
            .andExpect(jsonPath("$.[*].tarifa8Hora").value(hasItem(DEFAULT_TARIFA_8_HORA)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspaciosReserva.class);
        EspaciosReserva espaciosReserva1 = new EspaciosReserva();
        espaciosReserva1.setId(1L);
        EspaciosReserva espaciosReserva2 = new EspaciosReserva();
        espaciosReserva2.setId(espaciosReserva1.getId());
        assertThat(espaciosReserva1).isEqualTo(espaciosReserva2);
        espaciosReserva2.setId(2L);
        assertThat(espaciosReserva1).isNotEqualTo(espaciosReserva2);
        espaciosReserva1.setId(null);
        assertThat(espaciosReserva1).isNotEqualTo(espaciosReserva2);
    }
}
