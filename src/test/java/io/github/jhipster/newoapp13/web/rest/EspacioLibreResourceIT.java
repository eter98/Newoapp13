package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.EspacioLibre;
import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.domain.TipoEspacio;
import io.github.jhipster.newoapp13.repository.EspacioLibreRepository;
import io.github.jhipster.newoapp13.repository.search.EspacioLibreSearchRepository;
import io.github.jhipster.newoapp13.service.EspacioLibreService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.EspacioLibreCriteria;
import io.github.jhipster.newoapp13.service.EspacioLibreQueryService;

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
 * Integration tests for the {@link EspacioLibreResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class EspacioLibreResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACIDAD_INSTALADA = 1;
    private static final Integer UPDATED_CAPACIDAD_INSTALADA = 2;
    private static final Integer SMALLER_CAPACIDAD_INSTALADA = 1 - 1;

    private static final String DEFAULT_WIFI = "AAAAAAAAAA";
    private static final String UPDATED_WIFI = "BBBBBBBBBB";

    private static final Integer DEFAULT_TARIFA_1_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_1_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_1_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_2_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_2_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_2_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_3_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_3_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_3_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_4_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_4_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_4_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_5_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_5_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_5_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_6_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_6_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_6_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_7_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_7_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_7_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_8_H_MIEMBRO = 1;
    private static final Integer UPDATED_TARIFA_8_H_MIEMBRO = 2;
    private static final Integer SMALLER_TARIFA_8_H_MIEMBRO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_1_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_1_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_1_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_2_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_2_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_2_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_3_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_3_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_3_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_4_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_4_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_4_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_5_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_5_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_5_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_6_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_6_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_6_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_7_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_7_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_7_H_INVITADO = 1 - 1;

    private static final Integer DEFAULT_TARIFA_8_H_INVITADO = 1;
    private static final Integer UPDATED_TARIFA_8_H_INVITADO = 2;
    private static final Integer SMALLER_TARIFA_8_H_INVITADO = 1 - 1;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

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

    @Autowired
    private EspacioLibreRepository espacioLibreRepository;

    @Autowired
    private EspacioLibreService espacioLibreService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.EspacioLibreSearchRepositoryMockConfiguration
     */
    @Autowired
    private EspacioLibreSearchRepository mockEspacioLibreSearchRepository;

    @Autowired
    private EspacioLibreQueryService espacioLibreQueryService;

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

    private MockMvc restEspacioLibreMockMvc;

    private EspacioLibre espacioLibre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspacioLibreResource espacioLibreResource = new EspacioLibreResource(espacioLibreService, espacioLibreQueryService);
        this.restEspacioLibreMockMvc = MockMvcBuilders.standaloneSetup(espacioLibreResource)
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
    public static EspacioLibre createEntity(EntityManager em) {
        EspacioLibre espacioLibre = new EspacioLibre()
            .nombre(DEFAULT_NOMBRE)
            .capacidadInstalada(DEFAULT_CAPACIDAD_INSTALADA)
            .wifi(DEFAULT_WIFI)
            .tarifa1hMiembro(DEFAULT_TARIFA_1_H_MIEMBRO)
            .tarifa2hMiembro(DEFAULT_TARIFA_2_H_MIEMBRO)
            .tarifa3hMiembro(DEFAULT_TARIFA_3_H_MIEMBRO)
            .tarifa4hMiembro(DEFAULT_TARIFA_4_H_MIEMBRO)
            .tarifa5hMiembro(DEFAULT_TARIFA_5_H_MIEMBRO)
            .tarifa6hMiembro(DEFAULT_TARIFA_6_H_MIEMBRO)
            .tarifa7hMiembro(DEFAULT_TARIFA_7_H_MIEMBRO)
            .tarifa8hMiembro(DEFAULT_TARIFA_8_H_MIEMBRO)
            .tarifa1hInvitado(DEFAULT_TARIFA_1_H_INVITADO)
            .tarifa2hInvitado(DEFAULT_TARIFA_2_H_INVITADO)
            .tarifa3hInvitado(DEFAULT_TARIFA_3_H_INVITADO)
            .tarifa4hInvitado(DEFAULT_TARIFA_4_H_INVITADO)
            .tarifa5hInvitado(DEFAULT_TARIFA_5_H_INVITADO)
            .tarifa6hInvitado(DEFAULT_TARIFA_6_H_INVITADO)
            .tarifa7hInvitado(DEFAULT_TARIFA_7_H_INVITADO)
            .tarifa8hInvitado(DEFAULT_TARIFA_8_H_INVITADO)
            .impuesto(DEFAULT_IMPUESTO)
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
            .imagen6ContentType(DEFAULT_IMAGEN_6_CONTENT_TYPE);
        return espacioLibre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EspacioLibre createUpdatedEntity(EntityManager em) {
        EspacioLibre espacioLibre = new EspacioLibre()
            .nombre(UPDATED_NOMBRE)
            .capacidadInstalada(UPDATED_CAPACIDAD_INSTALADA)
            .wifi(UPDATED_WIFI)
            .tarifa1hMiembro(UPDATED_TARIFA_1_H_MIEMBRO)
            .tarifa2hMiembro(UPDATED_TARIFA_2_H_MIEMBRO)
            .tarifa3hMiembro(UPDATED_TARIFA_3_H_MIEMBRO)
            .tarifa4hMiembro(UPDATED_TARIFA_4_H_MIEMBRO)
            .tarifa5hMiembro(UPDATED_TARIFA_5_H_MIEMBRO)
            .tarifa6hMiembro(UPDATED_TARIFA_6_H_MIEMBRO)
            .tarifa7hMiembro(UPDATED_TARIFA_7_H_MIEMBRO)
            .tarifa8hMiembro(UPDATED_TARIFA_8_H_MIEMBRO)
            .tarifa1hInvitado(UPDATED_TARIFA_1_H_INVITADO)
            .tarifa2hInvitado(UPDATED_TARIFA_2_H_INVITADO)
            .tarifa3hInvitado(UPDATED_TARIFA_3_H_INVITADO)
            .tarifa4hInvitado(UPDATED_TARIFA_4_H_INVITADO)
            .tarifa5hInvitado(UPDATED_TARIFA_5_H_INVITADO)
            .tarifa6hInvitado(UPDATED_TARIFA_6_H_INVITADO)
            .tarifa7hInvitado(UPDATED_TARIFA_7_H_INVITADO)
            .tarifa8hInvitado(UPDATED_TARIFA_8_H_INVITADO)
            .impuesto(UPDATED_IMPUESTO)
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
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE);
        return espacioLibre;
    }

    @BeforeEach
    public void initTest() {
        espacioLibre = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspacioLibre() throws Exception {
        int databaseSizeBeforeCreate = espacioLibreRepository.findAll().size();

        // Create the EspacioLibre
        restEspacioLibreMockMvc.perform(post("/api/espacio-libres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacioLibre)))
            .andExpect(status().isCreated());

        // Validate the EspacioLibre in the database
        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeCreate + 1);
        EspacioLibre testEspacioLibre = espacioLibreList.get(espacioLibreList.size() - 1);
        assertThat(testEspacioLibre.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEspacioLibre.getCapacidadInstalada()).isEqualTo(DEFAULT_CAPACIDAD_INSTALADA);
        assertThat(testEspacioLibre.getWifi()).isEqualTo(DEFAULT_WIFI);
        assertThat(testEspacioLibre.getTarifa1hMiembro()).isEqualTo(DEFAULT_TARIFA_1_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa2hMiembro()).isEqualTo(DEFAULT_TARIFA_2_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa3hMiembro()).isEqualTo(DEFAULT_TARIFA_3_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa4hMiembro()).isEqualTo(DEFAULT_TARIFA_4_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa5hMiembro()).isEqualTo(DEFAULT_TARIFA_5_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa6hMiembro()).isEqualTo(DEFAULT_TARIFA_6_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa7hMiembro()).isEqualTo(DEFAULT_TARIFA_7_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa8hMiembro()).isEqualTo(DEFAULT_TARIFA_8_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa1hInvitado()).isEqualTo(DEFAULT_TARIFA_1_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa2hInvitado()).isEqualTo(DEFAULT_TARIFA_2_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa3hInvitado()).isEqualTo(DEFAULT_TARIFA_3_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa4hInvitado()).isEqualTo(DEFAULT_TARIFA_4_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa5hInvitado()).isEqualTo(DEFAULT_TARIFA_5_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa6hInvitado()).isEqualTo(DEFAULT_TARIFA_6_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa7hInvitado()).isEqualTo(DEFAULT_TARIFA_7_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa8hInvitado()).isEqualTo(DEFAULT_TARIFA_8_H_INVITADO);
        assertThat(testEspacioLibre.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testEspacioLibre.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testEspacioLibre.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testEspacioLibre.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testEspacioLibre.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen3()).isEqualTo(DEFAULT_IMAGEN_3);
        assertThat(testEspacioLibre.getImagen3ContentType()).isEqualTo(DEFAULT_IMAGEN_3_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen4()).isEqualTo(DEFAULT_IMAGEN_4);
        assertThat(testEspacioLibre.getImagen4ContentType()).isEqualTo(DEFAULT_IMAGEN_4_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen5()).isEqualTo(DEFAULT_IMAGEN_5);
        assertThat(testEspacioLibre.getImagen5ContentType()).isEqualTo(DEFAULT_IMAGEN_5_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen6()).isEqualTo(DEFAULT_IMAGEN_6);
        assertThat(testEspacioLibre.getImagen6ContentType()).isEqualTo(DEFAULT_IMAGEN_6_CONTENT_TYPE);

        // Validate the EspacioLibre in Elasticsearch
        verify(mockEspacioLibreSearchRepository, times(1)).save(testEspacioLibre);
    }

    @Test
    @Transactional
    public void createEspacioLibreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = espacioLibreRepository.findAll().size();

        // Create the EspacioLibre with an existing ID
        espacioLibre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspacioLibreMockMvc.perform(post("/api/espacio-libres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacioLibre)))
            .andExpect(status().isBadRequest());

        // Validate the EspacioLibre in the database
        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeCreate);

        // Validate the EspacioLibre in Elasticsearch
        verify(mockEspacioLibreSearchRepository, times(0)).save(espacioLibre);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = espacioLibreRepository.findAll().size();
        // set the field null
        espacioLibre.setNombre(null);

        // Create the EspacioLibre, which fails.

        restEspacioLibreMockMvc.perform(post("/api/espacio-libres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacioLibre)))
            .andExpect(status().isBadRequest());

        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacidadInstaladaIsRequired() throws Exception {
        int databaseSizeBeforeTest = espacioLibreRepository.findAll().size();
        // set the field null
        espacioLibre.setCapacidadInstalada(null);

        // Create the EspacioLibre, which fails.

        restEspacioLibreMockMvc.perform(post("/api/espacio-libres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacioLibre)))
            .andExpect(status().isBadRequest());

        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspacioLibres() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espacioLibre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].capacidadInstalada").value(hasItem(DEFAULT_CAPACIDAD_INSTALADA)))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI.toString())))
            .andExpect(jsonPath("$.[*].tarifa1hMiembro").value(hasItem(DEFAULT_TARIFA_1_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa2hMiembro").value(hasItem(DEFAULT_TARIFA_2_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa3hMiembro").value(hasItem(DEFAULT_TARIFA_3_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa4hMiembro").value(hasItem(DEFAULT_TARIFA_4_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa5hMiembro").value(hasItem(DEFAULT_TARIFA_5_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa6hMiembro").value(hasItem(DEFAULT_TARIFA_6_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa7hMiembro").value(hasItem(DEFAULT_TARIFA_7_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa8hMiembro").value(hasItem(DEFAULT_TARIFA_8_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa1hInvitado").value(hasItem(DEFAULT_TARIFA_1_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa2hInvitado").value(hasItem(DEFAULT_TARIFA_2_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa3hInvitado").value(hasItem(DEFAULT_TARIFA_3_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa4hInvitado").value(hasItem(DEFAULT_TARIFA_4_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa5hInvitado").value(hasItem(DEFAULT_TARIFA_5_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa6hInvitado").value(hasItem(DEFAULT_TARIFA_6_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa7hInvitado").value(hasItem(DEFAULT_TARIFA_7_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa8hInvitado").value(hasItem(DEFAULT_TARIFA_8_H_INVITADO)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
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
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))));
    }
    
    @Test
    @Transactional
    public void getEspacioLibre() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get the espacioLibre
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres/{id}", espacioLibre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(espacioLibre.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.capacidadInstalada").value(DEFAULT_CAPACIDAD_INSTALADA))
            .andExpect(jsonPath("$.wifi").value(DEFAULT_WIFI.toString()))
            .andExpect(jsonPath("$.tarifa1hMiembro").value(DEFAULT_TARIFA_1_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa2hMiembro").value(DEFAULT_TARIFA_2_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa3hMiembro").value(DEFAULT_TARIFA_3_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa4hMiembro").value(DEFAULT_TARIFA_4_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa5hMiembro").value(DEFAULT_TARIFA_5_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa6hMiembro").value(DEFAULT_TARIFA_6_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa7hMiembro").value(DEFAULT_TARIFA_7_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa8hMiembro").value(DEFAULT_TARIFA_8_H_MIEMBRO))
            .andExpect(jsonPath("$.tarifa1hInvitado").value(DEFAULT_TARIFA_1_H_INVITADO))
            .andExpect(jsonPath("$.tarifa2hInvitado").value(DEFAULT_TARIFA_2_H_INVITADO))
            .andExpect(jsonPath("$.tarifa3hInvitado").value(DEFAULT_TARIFA_3_H_INVITADO))
            .andExpect(jsonPath("$.tarifa4hInvitado").value(DEFAULT_TARIFA_4_H_INVITADO))
            .andExpect(jsonPath("$.tarifa5hInvitado").value(DEFAULT_TARIFA_5_H_INVITADO))
            .andExpect(jsonPath("$.tarifa6hInvitado").value(DEFAULT_TARIFA_6_H_INVITADO))
            .andExpect(jsonPath("$.tarifa7hInvitado").value(DEFAULT_TARIFA_7_H_INVITADO))
            .andExpect(jsonPath("$.tarifa8hInvitado").value(DEFAULT_TARIFA_8_H_INVITADO))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()))
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
            .andExpect(jsonPath("$.imagen6").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_6)));
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where nombre equals to DEFAULT_NOMBRE
        defaultEspacioLibreShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the espacioLibreList where nombre equals to UPDATED_NOMBRE
        defaultEspacioLibreShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultEspacioLibreShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the espacioLibreList where nombre equals to UPDATED_NOMBRE
        defaultEspacioLibreShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where nombre is not null
        defaultEspacioLibreShouldBeFound("nombre.specified=true");

        // Get all the espacioLibreList where nombre is null
        defaultEspacioLibreShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada equals to DEFAULT_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldBeFound("capacidadInstalada.equals=" + DEFAULT_CAPACIDAD_INSTALADA);

        // Get all the espacioLibreList where capacidadInstalada equals to UPDATED_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.equals=" + UPDATED_CAPACIDAD_INSTALADA);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada in DEFAULT_CAPACIDAD_INSTALADA or UPDATED_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldBeFound("capacidadInstalada.in=" + DEFAULT_CAPACIDAD_INSTALADA + "," + UPDATED_CAPACIDAD_INSTALADA);

        // Get all the espacioLibreList where capacidadInstalada equals to UPDATED_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.in=" + UPDATED_CAPACIDAD_INSTALADA);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada is not null
        defaultEspacioLibreShouldBeFound("capacidadInstalada.specified=true");

        // Get all the espacioLibreList where capacidadInstalada is null
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada is greater than or equal to DEFAULT_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldBeFound("capacidadInstalada.greaterThanOrEqual=" + DEFAULT_CAPACIDAD_INSTALADA);

        // Get all the espacioLibreList where capacidadInstalada is greater than or equal to UPDATED_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.greaterThanOrEqual=" + UPDATED_CAPACIDAD_INSTALADA);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada is less than or equal to DEFAULT_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldBeFound("capacidadInstalada.lessThanOrEqual=" + DEFAULT_CAPACIDAD_INSTALADA);

        // Get all the espacioLibreList where capacidadInstalada is less than or equal to SMALLER_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.lessThanOrEqual=" + SMALLER_CAPACIDAD_INSTALADA);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada is less than DEFAULT_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.lessThan=" + DEFAULT_CAPACIDAD_INSTALADA);

        // Get all the espacioLibreList where capacidadInstalada is less than UPDATED_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldBeFound("capacidadInstalada.lessThan=" + UPDATED_CAPACIDAD_INSTALADA);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByCapacidadInstaladaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where capacidadInstalada is greater than DEFAULT_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldNotBeFound("capacidadInstalada.greaterThan=" + DEFAULT_CAPACIDAD_INSTALADA);

        // Get all the espacioLibreList where capacidadInstalada is greater than SMALLER_CAPACIDAD_INSTALADA
        defaultEspacioLibreShouldBeFound("capacidadInstalada.greaterThan=" + SMALLER_CAPACIDAD_INSTALADA);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByWifiIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where wifi equals to DEFAULT_WIFI
        defaultEspacioLibreShouldBeFound("wifi.equals=" + DEFAULT_WIFI);

        // Get all the espacioLibreList where wifi equals to UPDATED_WIFI
        defaultEspacioLibreShouldNotBeFound("wifi.equals=" + UPDATED_WIFI);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByWifiIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where wifi in DEFAULT_WIFI or UPDATED_WIFI
        defaultEspacioLibreShouldBeFound("wifi.in=" + DEFAULT_WIFI + "," + UPDATED_WIFI);

        // Get all the espacioLibreList where wifi equals to UPDATED_WIFI
        defaultEspacioLibreShouldNotBeFound("wifi.in=" + UPDATED_WIFI);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByWifiIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where wifi is not null
        defaultEspacioLibreShouldBeFound("wifi.specified=true");

        // Get all the espacioLibreList where wifi is null
        defaultEspacioLibreShouldNotBeFound("wifi.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro equals to DEFAULT_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.equals=" + DEFAULT_TARIFA_1_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa1hMiembro equals to UPDATED_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.equals=" + UPDATED_TARIFA_1_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro in DEFAULT_TARIFA_1_H_MIEMBRO or UPDATED_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.in=" + DEFAULT_TARIFA_1_H_MIEMBRO + "," + UPDATED_TARIFA_1_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa1hMiembro equals to UPDATED_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.in=" + UPDATED_TARIFA_1_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa1hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro is greater than or equal to DEFAULT_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_1_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa1hMiembro is greater than or equal to UPDATED_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_1_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro is less than or equal to DEFAULT_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_1_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa1hMiembro is less than or equal to SMALLER_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_1_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro is less than DEFAULT_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.lessThan=" + DEFAULT_TARIFA_1_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa1hMiembro is less than UPDATED_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.lessThan=" + UPDATED_TARIFA_1_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hMiembro is greater than DEFAULT_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa1hMiembro.greaterThan=" + DEFAULT_TARIFA_1_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa1hMiembro is greater than SMALLER_TARIFA_1_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa1hMiembro.greaterThan=" + SMALLER_TARIFA_1_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro equals to DEFAULT_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.equals=" + DEFAULT_TARIFA_2_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa2hMiembro equals to UPDATED_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.equals=" + UPDATED_TARIFA_2_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro in DEFAULT_TARIFA_2_H_MIEMBRO or UPDATED_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.in=" + DEFAULT_TARIFA_2_H_MIEMBRO + "," + UPDATED_TARIFA_2_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa2hMiembro equals to UPDATED_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.in=" + UPDATED_TARIFA_2_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa2hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro is greater than or equal to DEFAULT_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_2_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa2hMiembro is greater than or equal to UPDATED_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_2_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro is less than or equal to DEFAULT_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_2_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa2hMiembro is less than or equal to SMALLER_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_2_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro is less than DEFAULT_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.lessThan=" + DEFAULT_TARIFA_2_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa2hMiembro is less than UPDATED_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.lessThan=" + UPDATED_TARIFA_2_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hMiembro is greater than DEFAULT_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa2hMiembro.greaterThan=" + DEFAULT_TARIFA_2_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa2hMiembro is greater than SMALLER_TARIFA_2_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa2hMiembro.greaterThan=" + SMALLER_TARIFA_2_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro equals to DEFAULT_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.equals=" + DEFAULT_TARIFA_3_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa3hMiembro equals to UPDATED_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.equals=" + UPDATED_TARIFA_3_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro in DEFAULT_TARIFA_3_H_MIEMBRO or UPDATED_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.in=" + DEFAULT_TARIFA_3_H_MIEMBRO + "," + UPDATED_TARIFA_3_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa3hMiembro equals to UPDATED_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.in=" + UPDATED_TARIFA_3_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa3hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro is greater than or equal to DEFAULT_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_3_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa3hMiembro is greater than or equal to UPDATED_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_3_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro is less than or equal to DEFAULT_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_3_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa3hMiembro is less than or equal to SMALLER_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_3_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro is less than DEFAULT_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.lessThan=" + DEFAULT_TARIFA_3_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa3hMiembro is less than UPDATED_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.lessThan=" + UPDATED_TARIFA_3_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hMiembro is greater than DEFAULT_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa3hMiembro.greaterThan=" + DEFAULT_TARIFA_3_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa3hMiembro is greater than SMALLER_TARIFA_3_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa3hMiembro.greaterThan=" + SMALLER_TARIFA_3_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro equals to DEFAULT_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.equals=" + DEFAULT_TARIFA_4_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa4hMiembro equals to UPDATED_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.equals=" + UPDATED_TARIFA_4_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro in DEFAULT_TARIFA_4_H_MIEMBRO or UPDATED_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.in=" + DEFAULT_TARIFA_4_H_MIEMBRO + "," + UPDATED_TARIFA_4_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa4hMiembro equals to UPDATED_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.in=" + UPDATED_TARIFA_4_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa4hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro is greater than or equal to DEFAULT_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_4_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa4hMiembro is greater than or equal to UPDATED_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_4_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro is less than or equal to DEFAULT_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_4_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa4hMiembro is less than or equal to SMALLER_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_4_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro is less than DEFAULT_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.lessThan=" + DEFAULT_TARIFA_4_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa4hMiembro is less than UPDATED_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.lessThan=" + UPDATED_TARIFA_4_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hMiembro is greater than DEFAULT_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa4hMiembro.greaterThan=" + DEFAULT_TARIFA_4_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa4hMiembro is greater than SMALLER_TARIFA_4_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa4hMiembro.greaterThan=" + SMALLER_TARIFA_4_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro equals to DEFAULT_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.equals=" + DEFAULT_TARIFA_5_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa5hMiembro equals to UPDATED_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.equals=" + UPDATED_TARIFA_5_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro in DEFAULT_TARIFA_5_H_MIEMBRO or UPDATED_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.in=" + DEFAULT_TARIFA_5_H_MIEMBRO + "," + UPDATED_TARIFA_5_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa5hMiembro equals to UPDATED_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.in=" + UPDATED_TARIFA_5_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa5hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro is greater than or equal to DEFAULT_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_5_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa5hMiembro is greater than or equal to UPDATED_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_5_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro is less than or equal to DEFAULT_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_5_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa5hMiembro is less than or equal to SMALLER_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_5_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro is less than DEFAULT_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.lessThan=" + DEFAULT_TARIFA_5_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa5hMiembro is less than UPDATED_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.lessThan=" + UPDATED_TARIFA_5_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hMiembro is greater than DEFAULT_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa5hMiembro.greaterThan=" + DEFAULT_TARIFA_5_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa5hMiembro is greater than SMALLER_TARIFA_5_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa5hMiembro.greaterThan=" + SMALLER_TARIFA_5_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro equals to DEFAULT_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.equals=" + DEFAULT_TARIFA_6_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa6hMiembro equals to UPDATED_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.equals=" + UPDATED_TARIFA_6_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro in DEFAULT_TARIFA_6_H_MIEMBRO or UPDATED_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.in=" + DEFAULT_TARIFA_6_H_MIEMBRO + "," + UPDATED_TARIFA_6_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa6hMiembro equals to UPDATED_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.in=" + UPDATED_TARIFA_6_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa6hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro is greater than or equal to DEFAULT_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_6_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa6hMiembro is greater than or equal to UPDATED_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_6_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro is less than or equal to DEFAULT_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_6_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa6hMiembro is less than or equal to SMALLER_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_6_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro is less than DEFAULT_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.lessThan=" + DEFAULT_TARIFA_6_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa6hMiembro is less than UPDATED_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.lessThan=" + UPDATED_TARIFA_6_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hMiembro is greater than DEFAULT_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa6hMiembro.greaterThan=" + DEFAULT_TARIFA_6_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa6hMiembro is greater than SMALLER_TARIFA_6_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa6hMiembro.greaterThan=" + SMALLER_TARIFA_6_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro equals to DEFAULT_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.equals=" + DEFAULT_TARIFA_7_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa7hMiembro equals to UPDATED_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.equals=" + UPDATED_TARIFA_7_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro in DEFAULT_TARIFA_7_H_MIEMBRO or UPDATED_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.in=" + DEFAULT_TARIFA_7_H_MIEMBRO + "," + UPDATED_TARIFA_7_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa7hMiembro equals to UPDATED_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.in=" + UPDATED_TARIFA_7_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa7hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro is greater than or equal to DEFAULT_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_7_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa7hMiembro is greater than or equal to UPDATED_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_7_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro is less than or equal to DEFAULT_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_7_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa7hMiembro is less than or equal to SMALLER_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_7_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro is less than DEFAULT_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.lessThan=" + DEFAULT_TARIFA_7_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa7hMiembro is less than UPDATED_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.lessThan=" + UPDATED_TARIFA_7_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hMiembro is greater than DEFAULT_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa7hMiembro.greaterThan=" + DEFAULT_TARIFA_7_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa7hMiembro is greater than SMALLER_TARIFA_7_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa7hMiembro.greaterThan=" + SMALLER_TARIFA_7_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro equals to DEFAULT_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.equals=" + DEFAULT_TARIFA_8_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa8hMiembro equals to UPDATED_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.equals=" + UPDATED_TARIFA_8_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro in DEFAULT_TARIFA_8_H_MIEMBRO or UPDATED_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.in=" + DEFAULT_TARIFA_8_H_MIEMBRO + "," + UPDATED_TARIFA_8_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa8hMiembro equals to UPDATED_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.in=" + UPDATED_TARIFA_8_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro is not null
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.specified=true");

        // Get all the espacioLibreList where tarifa8hMiembro is null
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro is greater than or equal to DEFAULT_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.greaterThanOrEqual=" + DEFAULT_TARIFA_8_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa8hMiembro is greater than or equal to UPDATED_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.greaterThanOrEqual=" + UPDATED_TARIFA_8_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro is less than or equal to DEFAULT_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.lessThanOrEqual=" + DEFAULT_TARIFA_8_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa8hMiembro is less than or equal to SMALLER_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.lessThanOrEqual=" + SMALLER_TARIFA_8_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro is less than DEFAULT_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.lessThan=" + DEFAULT_TARIFA_8_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa8hMiembro is less than UPDATED_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.lessThan=" + UPDATED_TARIFA_8_H_MIEMBRO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hMiembroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hMiembro is greater than DEFAULT_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldNotBeFound("tarifa8hMiembro.greaterThan=" + DEFAULT_TARIFA_8_H_MIEMBRO);

        // Get all the espacioLibreList where tarifa8hMiembro is greater than SMALLER_TARIFA_8_H_MIEMBRO
        defaultEspacioLibreShouldBeFound("tarifa8hMiembro.greaterThan=" + SMALLER_TARIFA_8_H_MIEMBRO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado equals to DEFAULT_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.equals=" + DEFAULT_TARIFA_1_H_INVITADO);

        // Get all the espacioLibreList where tarifa1hInvitado equals to UPDATED_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.equals=" + UPDATED_TARIFA_1_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado in DEFAULT_TARIFA_1_H_INVITADO or UPDATED_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.in=" + DEFAULT_TARIFA_1_H_INVITADO + "," + UPDATED_TARIFA_1_H_INVITADO);

        // Get all the espacioLibreList where tarifa1hInvitado equals to UPDATED_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.in=" + UPDATED_TARIFA_1_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa1hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado is greater than or equal to DEFAULT_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_1_H_INVITADO);

        // Get all the espacioLibreList where tarifa1hInvitado is greater than or equal to UPDATED_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_1_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado is less than or equal to DEFAULT_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_1_H_INVITADO);

        // Get all the espacioLibreList where tarifa1hInvitado is less than or equal to SMALLER_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_1_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado is less than DEFAULT_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.lessThan=" + DEFAULT_TARIFA_1_H_INVITADO);

        // Get all the espacioLibreList where tarifa1hInvitado is less than UPDATED_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.lessThan=" + UPDATED_TARIFA_1_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa1hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa1hInvitado is greater than DEFAULT_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa1hInvitado.greaterThan=" + DEFAULT_TARIFA_1_H_INVITADO);

        // Get all the espacioLibreList where tarifa1hInvitado is greater than SMALLER_TARIFA_1_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa1hInvitado.greaterThan=" + SMALLER_TARIFA_1_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado equals to DEFAULT_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.equals=" + DEFAULT_TARIFA_2_H_INVITADO);

        // Get all the espacioLibreList where tarifa2hInvitado equals to UPDATED_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.equals=" + UPDATED_TARIFA_2_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado in DEFAULT_TARIFA_2_H_INVITADO or UPDATED_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.in=" + DEFAULT_TARIFA_2_H_INVITADO + "," + UPDATED_TARIFA_2_H_INVITADO);

        // Get all the espacioLibreList where tarifa2hInvitado equals to UPDATED_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.in=" + UPDATED_TARIFA_2_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa2hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado is greater than or equal to DEFAULT_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_2_H_INVITADO);

        // Get all the espacioLibreList where tarifa2hInvitado is greater than or equal to UPDATED_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_2_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado is less than or equal to DEFAULT_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_2_H_INVITADO);

        // Get all the espacioLibreList where tarifa2hInvitado is less than or equal to SMALLER_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_2_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado is less than DEFAULT_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.lessThan=" + DEFAULT_TARIFA_2_H_INVITADO);

        // Get all the espacioLibreList where tarifa2hInvitado is less than UPDATED_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.lessThan=" + UPDATED_TARIFA_2_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa2hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa2hInvitado is greater than DEFAULT_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa2hInvitado.greaterThan=" + DEFAULT_TARIFA_2_H_INVITADO);

        // Get all the espacioLibreList where tarifa2hInvitado is greater than SMALLER_TARIFA_2_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa2hInvitado.greaterThan=" + SMALLER_TARIFA_2_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado equals to DEFAULT_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.equals=" + DEFAULT_TARIFA_3_H_INVITADO);

        // Get all the espacioLibreList where tarifa3hInvitado equals to UPDATED_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.equals=" + UPDATED_TARIFA_3_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado in DEFAULT_TARIFA_3_H_INVITADO or UPDATED_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.in=" + DEFAULT_TARIFA_3_H_INVITADO + "," + UPDATED_TARIFA_3_H_INVITADO);

        // Get all the espacioLibreList where tarifa3hInvitado equals to UPDATED_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.in=" + UPDATED_TARIFA_3_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa3hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado is greater than or equal to DEFAULT_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_3_H_INVITADO);

        // Get all the espacioLibreList where tarifa3hInvitado is greater than or equal to UPDATED_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_3_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado is less than or equal to DEFAULT_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_3_H_INVITADO);

        // Get all the espacioLibreList where tarifa3hInvitado is less than or equal to SMALLER_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_3_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado is less than DEFAULT_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.lessThan=" + DEFAULT_TARIFA_3_H_INVITADO);

        // Get all the espacioLibreList where tarifa3hInvitado is less than UPDATED_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.lessThan=" + UPDATED_TARIFA_3_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa3hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa3hInvitado is greater than DEFAULT_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa3hInvitado.greaterThan=" + DEFAULT_TARIFA_3_H_INVITADO);

        // Get all the espacioLibreList where tarifa3hInvitado is greater than SMALLER_TARIFA_3_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa3hInvitado.greaterThan=" + SMALLER_TARIFA_3_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado equals to DEFAULT_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.equals=" + DEFAULT_TARIFA_4_H_INVITADO);

        // Get all the espacioLibreList where tarifa4hInvitado equals to UPDATED_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.equals=" + UPDATED_TARIFA_4_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado in DEFAULT_TARIFA_4_H_INVITADO or UPDATED_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.in=" + DEFAULT_TARIFA_4_H_INVITADO + "," + UPDATED_TARIFA_4_H_INVITADO);

        // Get all the espacioLibreList where tarifa4hInvitado equals to UPDATED_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.in=" + UPDATED_TARIFA_4_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa4hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado is greater than or equal to DEFAULT_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_4_H_INVITADO);

        // Get all the espacioLibreList where tarifa4hInvitado is greater than or equal to UPDATED_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_4_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado is less than or equal to DEFAULT_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_4_H_INVITADO);

        // Get all the espacioLibreList where tarifa4hInvitado is less than or equal to SMALLER_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_4_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado is less than DEFAULT_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.lessThan=" + DEFAULT_TARIFA_4_H_INVITADO);

        // Get all the espacioLibreList where tarifa4hInvitado is less than UPDATED_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.lessThan=" + UPDATED_TARIFA_4_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa4hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa4hInvitado is greater than DEFAULT_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa4hInvitado.greaterThan=" + DEFAULT_TARIFA_4_H_INVITADO);

        // Get all the espacioLibreList where tarifa4hInvitado is greater than SMALLER_TARIFA_4_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa4hInvitado.greaterThan=" + SMALLER_TARIFA_4_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado equals to DEFAULT_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.equals=" + DEFAULT_TARIFA_5_H_INVITADO);

        // Get all the espacioLibreList where tarifa5hInvitado equals to UPDATED_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.equals=" + UPDATED_TARIFA_5_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado in DEFAULT_TARIFA_5_H_INVITADO or UPDATED_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.in=" + DEFAULT_TARIFA_5_H_INVITADO + "," + UPDATED_TARIFA_5_H_INVITADO);

        // Get all the espacioLibreList where tarifa5hInvitado equals to UPDATED_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.in=" + UPDATED_TARIFA_5_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa5hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado is greater than or equal to DEFAULT_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_5_H_INVITADO);

        // Get all the espacioLibreList where tarifa5hInvitado is greater than or equal to UPDATED_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_5_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado is less than or equal to DEFAULT_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_5_H_INVITADO);

        // Get all the espacioLibreList where tarifa5hInvitado is less than or equal to SMALLER_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_5_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado is less than DEFAULT_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.lessThan=" + DEFAULT_TARIFA_5_H_INVITADO);

        // Get all the espacioLibreList where tarifa5hInvitado is less than UPDATED_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.lessThan=" + UPDATED_TARIFA_5_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa5hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa5hInvitado is greater than DEFAULT_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa5hInvitado.greaterThan=" + DEFAULT_TARIFA_5_H_INVITADO);

        // Get all the espacioLibreList where tarifa5hInvitado is greater than SMALLER_TARIFA_5_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa5hInvitado.greaterThan=" + SMALLER_TARIFA_5_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado equals to DEFAULT_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.equals=" + DEFAULT_TARIFA_6_H_INVITADO);

        // Get all the espacioLibreList where tarifa6hInvitado equals to UPDATED_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.equals=" + UPDATED_TARIFA_6_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado in DEFAULT_TARIFA_6_H_INVITADO or UPDATED_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.in=" + DEFAULT_TARIFA_6_H_INVITADO + "," + UPDATED_TARIFA_6_H_INVITADO);

        // Get all the espacioLibreList where tarifa6hInvitado equals to UPDATED_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.in=" + UPDATED_TARIFA_6_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa6hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado is greater than or equal to DEFAULT_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_6_H_INVITADO);

        // Get all the espacioLibreList where tarifa6hInvitado is greater than or equal to UPDATED_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_6_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado is less than or equal to DEFAULT_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_6_H_INVITADO);

        // Get all the espacioLibreList where tarifa6hInvitado is less than or equal to SMALLER_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_6_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado is less than DEFAULT_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.lessThan=" + DEFAULT_TARIFA_6_H_INVITADO);

        // Get all the espacioLibreList where tarifa6hInvitado is less than UPDATED_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.lessThan=" + UPDATED_TARIFA_6_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa6hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa6hInvitado is greater than DEFAULT_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa6hInvitado.greaterThan=" + DEFAULT_TARIFA_6_H_INVITADO);

        // Get all the espacioLibreList where tarifa6hInvitado is greater than SMALLER_TARIFA_6_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa6hInvitado.greaterThan=" + SMALLER_TARIFA_6_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado equals to DEFAULT_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.equals=" + DEFAULT_TARIFA_7_H_INVITADO);

        // Get all the espacioLibreList where tarifa7hInvitado equals to UPDATED_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.equals=" + UPDATED_TARIFA_7_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado in DEFAULT_TARIFA_7_H_INVITADO or UPDATED_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.in=" + DEFAULT_TARIFA_7_H_INVITADO + "," + UPDATED_TARIFA_7_H_INVITADO);

        // Get all the espacioLibreList where tarifa7hInvitado equals to UPDATED_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.in=" + UPDATED_TARIFA_7_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa7hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado is greater than or equal to DEFAULT_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_7_H_INVITADO);

        // Get all the espacioLibreList where tarifa7hInvitado is greater than or equal to UPDATED_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_7_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado is less than or equal to DEFAULT_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_7_H_INVITADO);

        // Get all the espacioLibreList where tarifa7hInvitado is less than or equal to SMALLER_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_7_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado is less than DEFAULT_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.lessThan=" + DEFAULT_TARIFA_7_H_INVITADO);

        // Get all the espacioLibreList where tarifa7hInvitado is less than UPDATED_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.lessThan=" + UPDATED_TARIFA_7_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa7hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa7hInvitado is greater than DEFAULT_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa7hInvitado.greaterThan=" + DEFAULT_TARIFA_7_H_INVITADO);

        // Get all the espacioLibreList where tarifa7hInvitado is greater than SMALLER_TARIFA_7_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa7hInvitado.greaterThan=" + SMALLER_TARIFA_7_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado equals to DEFAULT_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.equals=" + DEFAULT_TARIFA_8_H_INVITADO);

        // Get all the espacioLibreList where tarifa8hInvitado equals to UPDATED_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.equals=" + UPDATED_TARIFA_8_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado in DEFAULT_TARIFA_8_H_INVITADO or UPDATED_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.in=" + DEFAULT_TARIFA_8_H_INVITADO + "," + UPDATED_TARIFA_8_H_INVITADO);

        // Get all the espacioLibreList where tarifa8hInvitado equals to UPDATED_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.in=" + UPDATED_TARIFA_8_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado is not null
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.specified=true");

        // Get all the espacioLibreList where tarifa8hInvitado is null
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado is greater than or equal to DEFAULT_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.greaterThanOrEqual=" + DEFAULT_TARIFA_8_H_INVITADO);

        // Get all the espacioLibreList where tarifa8hInvitado is greater than or equal to UPDATED_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.greaterThanOrEqual=" + UPDATED_TARIFA_8_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado is less than or equal to DEFAULT_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.lessThanOrEqual=" + DEFAULT_TARIFA_8_H_INVITADO);

        // Get all the espacioLibreList where tarifa8hInvitado is less than or equal to SMALLER_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.lessThanOrEqual=" + SMALLER_TARIFA_8_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsLessThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado is less than DEFAULT_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.lessThan=" + DEFAULT_TARIFA_8_H_INVITADO);

        // Get all the espacioLibreList where tarifa8hInvitado is less than UPDATED_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.lessThan=" + UPDATED_TARIFA_8_H_INVITADO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByTarifa8hInvitadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where tarifa8hInvitado is greater than DEFAULT_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldNotBeFound("tarifa8hInvitado.greaterThan=" + DEFAULT_TARIFA_8_H_INVITADO);

        // Get all the espacioLibreList where tarifa8hInvitado is greater than SMALLER_TARIFA_8_H_INVITADO
        defaultEspacioLibreShouldBeFound("tarifa8hInvitado.greaterThan=" + SMALLER_TARIFA_8_H_INVITADO);
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where impuesto equals to DEFAULT_IMPUESTO
        defaultEspacioLibreShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the espacioLibreList where impuesto equals to UPDATED_IMPUESTO
        defaultEspacioLibreShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultEspacioLibreShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the espacioLibreList where impuesto equals to UPDATED_IMPUESTO
        defaultEspacioLibreShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllEspacioLibresByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);

        // Get all the espacioLibreList where impuesto is not null
        defaultEspacioLibreShouldBeFound("impuesto.specified=true");

        // Get all the espacioLibreList where impuesto is null
        defaultEspacioLibreShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllEspacioLibresBySedeIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);
        Sedes sede = SedesResourceIT.createEntity(em);
        em.persist(sede);
        em.flush();
        espacioLibre.setSede(sede);
        espacioLibreRepository.saveAndFlush(espacioLibre);
        Long sedeId = sede.getId();

        // Get all the espacioLibreList where sede equals to sedeId
        defaultEspacioLibreShouldBeFound("sedeId.equals=" + sedeId);

        // Get all the espacioLibreList where sede equals to sedeId + 1
        defaultEspacioLibreShouldNotBeFound("sedeId.equals=" + (sedeId + 1));
    }


    @Test
    @Transactional
    public void getAllEspacioLibresByTipoEspacioIsEqualToSomething() throws Exception {
        // Initialize the database
        espacioLibreRepository.saveAndFlush(espacioLibre);
        TipoEspacio tipoEspacio = TipoEspacioResourceIT.createEntity(em);
        em.persist(tipoEspacio);
        em.flush();
        espacioLibre.setTipoEspacio(tipoEspacio);
        espacioLibreRepository.saveAndFlush(espacioLibre);
        Long tipoEspacioId = tipoEspacio.getId();

        // Get all the espacioLibreList where tipoEspacio equals to tipoEspacioId
        defaultEspacioLibreShouldBeFound("tipoEspacioId.equals=" + tipoEspacioId);

        // Get all the espacioLibreList where tipoEspacio equals to tipoEspacioId + 1
        defaultEspacioLibreShouldNotBeFound("tipoEspacioId.equals=" + (tipoEspacioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEspacioLibreShouldBeFound(String filter) throws Exception {
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espacioLibre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].capacidadInstalada").value(hasItem(DEFAULT_CAPACIDAD_INSTALADA)))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI)))
            .andExpect(jsonPath("$.[*].tarifa1hMiembro").value(hasItem(DEFAULT_TARIFA_1_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa2hMiembro").value(hasItem(DEFAULT_TARIFA_2_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa3hMiembro").value(hasItem(DEFAULT_TARIFA_3_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa4hMiembro").value(hasItem(DEFAULT_TARIFA_4_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa5hMiembro").value(hasItem(DEFAULT_TARIFA_5_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa6hMiembro").value(hasItem(DEFAULT_TARIFA_6_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa7hMiembro").value(hasItem(DEFAULT_TARIFA_7_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa8hMiembro").value(hasItem(DEFAULT_TARIFA_8_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa1hInvitado").value(hasItem(DEFAULT_TARIFA_1_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa2hInvitado").value(hasItem(DEFAULT_TARIFA_2_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa3hInvitado").value(hasItem(DEFAULT_TARIFA_3_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa4hInvitado").value(hasItem(DEFAULT_TARIFA_4_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa5hInvitado").value(hasItem(DEFAULT_TARIFA_5_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa6hInvitado").value(hasItem(DEFAULT_TARIFA_6_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa7hInvitado").value(hasItem(DEFAULT_TARIFA_7_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa8hInvitado").value(hasItem(DEFAULT_TARIFA_8_H_INVITADO)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
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
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))));

        // Check, that the count call also returns 1
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEspacioLibreShouldNotBeFound(String filter) throws Exception {
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEspacioLibre() throws Exception {
        // Get the espacioLibre
        restEspacioLibreMockMvc.perform(get("/api/espacio-libres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspacioLibre() throws Exception {
        // Initialize the database
        espacioLibreService.save(espacioLibre);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEspacioLibreSearchRepository);

        int databaseSizeBeforeUpdate = espacioLibreRepository.findAll().size();

        // Update the espacioLibre
        EspacioLibre updatedEspacioLibre = espacioLibreRepository.findById(espacioLibre.getId()).get();
        // Disconnect from session so that the updates on updatedEspacioLibre are not directly saved in db
        em.detach(updatedEspacioLibre);
        updatedEspacioLibre
            .nombre(UPDATED_NOMBRE)
            .capacidadInstalada(UPDATED_CAPACIDAD_INSTALADA)
            .wifi(UPDATED_WIFI)
            .tarifa1hMiembro(UPDATED_TARIFA_1_H_MIEMBRO)
            .tarifa2hMiembro(UPDATED_TARIFA_2_H_MIEMBRO)
            .tarifa3hMiembro(UPDATED_TARIFA_3_H_MIEMBRO)
            .tarifa4hMiembro(UPDATED_TARIFA_4_H_MIEMBRO)
            .tarifa5hMiembro(UPDATED_TARIFA_5_H_MIEMBRO)
            .tarifa6hMiembro(UPDATED_TARIFA_6_H_MIEMBRO)
            .tarifa7hMiembro(UPDATED_TARIFA_7_H_MIEMBRO)
            .tarifa8hMiembro(UPDATED_TARIFA_8_H_MIEMBRO)
            .tarifa1hInvitado(UPDATED_TARIFA_1_H_INVITADO)
            .tarifa2hInvitado(UPDATED_TARIFA_2_H_INVITADO)
            .tarifa3hInvitado(UPDATED_TARIFA_3_H_INVITADO)
            .tarifa4hInvitado(UPDATED_TARIFA_4_H_INVITADO)
            .tarifa5hInvitado(UPDATED_TARIFA_5_H_INVITADO)
            .tarifa6hInvitado(UPDATED_TARIFA_6_H_INVITADO)
            .tarifa7hInvitado(UPDATED_TARIFA_7_H_INVITADO)
            .tarifa8hInvitado(UPDATED_TARIFA_8_H_INVITADO)
            .impuesto(UPDATED_IMPUESTO)
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
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE);

        restEspacioLibreMockMvc.perform(put("/api/espacio-libres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEspacioLibre)))
            .andExpect(status().isOk());

        // Validate the EspacioLibre in the database
        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeUpdate);
        EspacioLibre testEspacioLibre = espacioLibreList.get(espacioLibreList.size() - 1);
        assertThat(testEspacioLibre.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEspacioLibre.getCapacidadInstalada()).isEqualTo(UPDATED_CAPACIDAD_INSTALADA);
        assertThat(testEspacioLibre.getWifi()).isEqualTo(UPDATED_WIFI);
        assertThat(testEspacioLibre.getTarifa1hMiembro()).isEqualTo(UPDATED_TARIFA_1_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa2hMiembro()).isEqualTo(UPDATED_TARIFA_2_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa3hMiembro()).isEqualTo(UPDATED_TARIFA_3_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa4hMiembro()).isEqualTo(UPDATED_TARIFA_4_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa5hMiembro()).isEqualTo(UPDATED_TARIFA_5_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa6hMiembro()).isEqualTo(UPDATED_TARIFA_6_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa7hMiembro()).isEqualTo(UPDATED_TARIFA_7_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa8hMiembro()).isEqualTo(UPDATED_TARIFA_8_H_MIEMBRO);
        assertThat(testEspacioLibre.getTarifa1hInvitado()).isEqualTo(UPDATED_TARIFA_1_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa2hInvitado()).isEqualTo(UPDATED_TARIFA_2_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa3hInvitado()).isEqualTo(UPDATED_TARIFA_3_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa4hInvitado()).isEqualTo(UPDATED_TARIFA_4_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa5hInvitado()).isEqualTo(UPDATED_TARIFA_5_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa6hInvitado()).isEqualTo(UPDATED_TARIFA_6_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa7hInvitado()).isEqualTo(UPDATED_TARIFA_7_H_INVITADO);
        assertThat(testEspacioLibre.getTarifa8hInvitado()).isEqualTo(UPDATED_TARIFA_8_H_INVITADO);
        assertThat(testEspacioLibre.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testEspacioLibre.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testEspacioLibre.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testEspacioLibre.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testEspacioLibre.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen3()).isEqualTo(UPDATED_IMAGEN_3);
        assertThat(testEspacioLibre.getImagen3ContentType()).isEqualTo(UPDATED_IMAGEN_3_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen4()).isEqualTo(UPDATED_IMAGEN_4);
        assertThat(testEspacioLibre.getImagen4ContentType()).isEqualTo(UPDATED_IMAGEN_4_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen5()).isEqualTo(UPDATED_IMAGEN_5);
        assertThat(testEspacioLibre.getImagen5ContentType()).isEqualTo(UPDATED_IMAGEN_5_CONTENT_TYPE);
        assertThat(testEspacioLibre.getImagen6()).isEqualTo(UPDATED_IMAGEN_6);
        assertThat(testEspacioLibre.getImagen6ContentType()).isEqualTo(UPDATED_IMAGEN_6_CONTENT_TYPE);

        // Validate the EspacioLibre in Elasticsearch
        verify(mockEspacioLibreSearchRepository, times(1)).save(testEspacioLibre);
    }

    @Test
    @Transactional
    public void updateNonExistingEspacioLibre() throws Exception {
        int databaseSizeBeforeUpdate = espacioLibreRepository.findAll().size();

        // Create the EspacioLibre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspacioLibreMockMvc.perform(put("/api/espacio-libres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(espacioLibre)))
            .andExpect(status().isBadRequest());

        // Validate the EspacioLibre in the database
        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EspacioLibre in Elasticsearch
        verify(mockEspacioLibreSearchRepository, times(0)).save(espacioLibre);
    }

    @Test
    @Transactional
    public void deleteEspacioLibre() throws Exception {
        // Initialize the database
        espacioLibreService.save(espacioLibre);

        int databaseSizeBeforeDelete = espacioLibreRepository.findAll().size();

        // Delete the espacioLibre
        restEspacioLibreMockMvc.perform(delete("/api/espacio-libres/{id}", espacioLibre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EspacioLibre> espacioLibreList = espacioLibreRepository.findAll();
        assertThat(espacioLibreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EspacioLibre in Elasticsearch
        verify(mockEspacioLibreSearchRepository, times(1)).deleteById(espacioLibre.getId());
    }

    @Test
    @Transactional
    public void searchEspacioLibre() throws Exception {
        // Initialize the database
        espacioLibreService.save(espacioLibre);
        when(mockEspacioLibreSearchRepository.search(queryStringQuery("id:" + espacioLibre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(espacioLibre), PageRequest.of(0, 1), 1));
        // Search the espacioLibre
        restEspacioLibreMockMvc.perform(get("/api/_search/espacio-libres?query=id:" + espacioLibre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espacioLibre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].capacidadInstalada").value(hasItem(DEFAULT_CAPACIDAD_INSTALADA)))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI)))
            .andExpect(jsonPath("$.[*].tarifa1hMiembro").value(hasItem(DEFAULT_TARIFA_1_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa2hMiembro").value(hasItem(DEFAULT_TARIFA_2_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa3hMiembro").value(hasItem(DEFAULT_TARIFA_3_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa4hMiembro").value(hasItem(DEFAULT_TARIFA_4_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa5hMiembro").value(hasItem(DEFAULT_TARIFA_5_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa6hMiembro").value(hasItem(DEFAULT_TARIFA_6_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa7hMiembro").value(hasItem(DEFAULT_TARIFA_7_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa8hMiembro").value(hasItem(DEFAULT_TARIFA_8_H_MIEMBRO)))
            .andExpect(jsonPath("$.[*].tarifa1hInvitado").value(hasItem(DEFAULT_TARIFA_1_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa2hInvitado").value(hasItem(DEFAULT_TARIFA_2_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa3hInvitado").value(hasItem(DEFAULT_TARIFA_3_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa4hInvitado").value(hasItem(DEFAULT_TARIFA_4_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa5hInvitado").value(hasItem(DEFAULT_TARIFA_5_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa6hInvitado").value(hasItem(DEFAULT_TARIFA_6_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa7hInvitado").value(hasItem(DEFAULT_TARIFA_7_H_INVITADO)))
            .andExpect(jsonPath("$.[*].tarifa8hInvitado").value(hasItem(DEFAULT_TARIFA_8_H_INVITADO)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
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
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspacioLibre.class);
        EspacioLibre espacioLibre1 = new EspacioLibre();
        espacioLibre1.setId(1L);
        EspacioLibre espacioLibre2 = new EspacioLibre();
        espacioLibre2.setId(espacioLibre1.getId());
        assertThat(espacioLibre1).isEqualTo(espacioLibre2);
        espacioLibre2.setId(2L);
        assertThat(espacioLibre1).isNotEqualTo(espacioLibre2);
        espacioLibre1.setId(null);
        assertThat(espacioLibre1).isNotEqualTo(espacioLibre2);
    }
}
