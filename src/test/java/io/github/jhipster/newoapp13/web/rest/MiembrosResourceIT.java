package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.User;
import io.github.jhipster.newoapp13.domain.Pais;
import io.github.jhipster.newoapp13.repository.MiembrosRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosSearchRepository;
import io.github.jhipster.newoapp13.service.MiembrosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.MiembrosCriteria;
import io.github.jhipster.newoapp13.service.MiembrosQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.newoapp13.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.newoapp13.domain.enumeration.TipoDocumentod;
import io.github.jhipster.newoapp13.domain.enumeration.Generod;
/**
 * Integration tests for the {@link MiembrosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class MiembrosResourceIT {

    private static final TipoDocumentod DEFAULT_TIPO_DOCUMENTO = TipoDocumentod.Cedula;
    private static final TipoDocumentod UPDATED_TIPO_DOCUMENTO = TipoDocumentod.Cedula_Extranjeria;

    private static final Integer DEFAULT_IDENTIFICACION = 1;
    private static final Integer UPDATED_IDENTIFICACION = 2;
    private static final Integer SMALLER_IDENTIFICACION = 1 - 1;

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_FECHA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_REGISTRO = Instant.ofEpochMilli(-1L);

    private static final Generod DEFAULT_GENERO = Generod.Masculino;
    private static final Generod UPDATED_GENERO = Generod.Femenino;

    private static final String DEFAULT_CELULAR = "AAAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBBB";

    private static final String DEFAULT_BIOGRAFIA = "AAAAAAAAAA";
    private static final String UPDATED_BIOGRAFIA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FOTO_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FOT_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOT_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOT_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOT_3_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONOCIMIENTOS_QUE_DOMINA = "AAAAAAAAAA";
    private static final String UPDATED_CONOCIMIENTOS_QUE_DOMINA = "BBBBBBBBBB";

    private static final String DEFAULT_TEMAS_DE_INTERES = "AAAAAAAAAA";
    private static final String UPDATED_TEMAS_DE_INTERES = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_ID_GOOGLE = "AAAAAAAAAA";
    private static final String UPDATED_ID_GOOGLE = "BBBBBBBBBB";

    private static final String DEFAULT_TWITER = "AAAAAAAAAA";
    private static final String UPDATED_TWITER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DERECHOS_DE_COMPRA = false;
    private static final Boolean UPDATED_DERECHOS_DE_COMPRA = true;

    private static final Boolean DEFAULT_ACCESO_ILIMITADO = false;
    private static final Boolean UPDATED_ACCESO_ILIMITADO = true;

    private static final Boolean DEFAULT_ALIADO = false;
    private static final Boolean UPDATED_ALIADO = true;

    private static final Boolean DEFAULT_HOST = false;
    private static final Boolean UPDATED_HOST = true;

    @Autowired
    private MiembrosRepository miembrosRepository;

    @Autowired
    private MiembrosService miembrosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.MiembrosSearchRepositoryMockConfiguration
     */
    @Autowired
    private MiembrosSearchRepository mockMiembrosSearchRepository;

    @Autowired
    private MiembrosQueryService miembrosQueryService;

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

    private MockMvc restMiembrosMockMvc;

    private Miembros miembros;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MiembrosResource miembrosResource = new MiembrosResource(miembrosService, miembrosQueryService);
        this.restMiembrosMockMvc = MockMvcBuilders.standaloneSetup(miembrosResource)
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
    public static Miembros createEntity(EntityManager em) {
        Miembros miembros = new Miembros()
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .identificacion(DEFAULT_IDENTIFICACION)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .genero(DEFAULT_GENERO)
            .celular(DEFAULT_CELULAR)
            .biografia(DEFAULT_BIOGRAFIA)
            .foto1(DEFAULT_FOTO_1)
            .foto1ContentType(DEFAULT_FOTO_1_CONTENT_TYPE)
            .foto2(DEFAULT_FOTO_2)
            .foto2ContentType(DEFAULT_FOTO_2_CONTENT_TYPE)
            .fot3(DEFAULT_FOT_3)
            .fot3ContentType(DEFAULT_FOT_3_CONTENT_TYPE)
            .conocimientosQueDomina(DEFAULT_CONOCIMIENTOS_QUE_DOMINA)
            .temasDeInteres(DEFAULT_TEMAS_DE_INTERES)
            .facebook(DEFAULT_FACEBOOK)
            .instagram(DEFAULT_INSTAGRAM)
            .idGoogle(DEFAULT_ID_GOOGLE)
            .twiter(DEFAULT_TWITER)
            .derechosDeCompra(DEFAULT_DERECHOS_DE_COMPRA)
            .accesoIlimitado(DEFAULT_ACCESO_ILIMITADO)
            .aliado(DEFAULT_ALIADO)
            .host(DEFAULT_HOST);
        return miembros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Miembros createUpdatedEntity(EntityManager em) {
        Miembros miembros = new Miembros()
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .identificacion(UPDATED_IDENTIFICACION)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .genero(UPDATED_GENERO)
            .celular(UPDATED_CELULAR)
            .biografia(UPDATED_BIOGRAFIA)
            .foto1(UPDATED_FOTO_1)
            .foto1ContentType(UPDATED_FOTO_1_CONTENT_TYPE)
            .foto2(UPDATED_FOTO_2)
            .foto2ContentType(UPDATED_FOTO_2_CONTENT_TYPE)
            .fot3(UPDATED_FOT_3)
            .fot3ContentType(UPDATED_FOT_3_CONTENT_TYPE)
            .conocimientosQueDomina(UPDATED_CONOCIMIENTOS_QUE_DOMINA)
            .temasDeInteres(UPDATED_TEMAS_DE_INTERES)
            .facebook(UPDATED_FACEBOOK)
            .instagram(UPDATED_INSTAGRAM)
            .idGoogle(UPDATED_ID_GOOGLE)
            .twiter(UPDATED_TWITER)
            .derechosDeCompra(UPDATED_DERECHOS_DE_COMPRA)
            .accesoIlimitado(UPDATED_ACCESO_ILIMITADO)
            .aliado(UPDATED_ALIADO)
            .host(UPDATED_HOST);
        return miembros;
    }

    @BeforeEach
    public void initTest() {
        miembros = createEntity(em);
    }

    @Test
    @Transactional
    public void createMiembros() throws Exception {
        int databaseSizeBeforeCreate = miembrosRepository.findAll().size();

        // Create the Miembros
        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isCreated());

        // Validate the Miembros in the database
        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeCreate + 1);
        Miembros testMiembros = miembrosList.get(miembrosList.size() - 1);
        assertThat(testMiembros.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testMiembros.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testMiembros.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testMiembros.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testMiembros.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testMiembros.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testMiembros.getBiografia()).isEqualTo(DEFAULT_BIOGRAFIA);
        assertThat(testMiembros.getFoto1()).isEqualTo(DEFAULT_FOTO_1);
        assertThat(testMiembros.getFoto1ContentType()).isEqualTo(DEFAULT_FOTO_1_CONTENT_TYPE);
        assertThat(testMiembros.getFoto2()).isEqualTo(DEFAULT_FOTO_2);
        assertThat(testMiembros.getFoto2ContentType()).isEqualTo(DEFAULT_FOTO_2_CONTENT_TYPE);
        assertThat(testMiembros.getFot3()).isEqualTo(DEFAULT_FOT_3);
        assertThat(testMiembros.getFot3ContentType()).isEqualTo(DEFAULT_FOT_3_CONTENT_TYPE);
        assertThat(testMiembros.getConocimientosQueDomina()).isEqualTo(DEFAULT_CONOCIMIENTOS_QUE_DOMINA);
        assertThat(testMiembros.getTemasDeInteres()).isEqualTo(DEFAULT_TEMAS_DE_INTERES);
        assertThat(testMiembros.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testMiembros.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testMiembros.getIdGoogle()).isEqualTo(DEFAULT_ID_GOOGLE);
        assertThat(testMiembros.getTwiter()).isEqualTo(DEFAULT_TWITER);
        assertThat(testMiembros.isDerechosDeCompra()).isEqualTo(DEFAULT_DERECHOS_DE_COMPRA);
        assertThat(testMiembros.isAccesoIlimitado()).isEqualTo(DEFAULT_ACCESO_ILIMITADO);
        assertThat(testMiembros.isAliado()).isEqualTo(DEFAULT_ALIADO);
        assertThat(testMiembros.isHost()).isEqualTo(DEFAULT_HOST);

        // Validate the Miembros in Elasticsearch
        verify(mockMiembrosSearchRepository, times(1)).save(testMiembros);
    }

    @Test
    @Transactional
    public void createMiembrosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = miembrosRepository.findAll().size();

        // Create the Miembros with an existing ID
        miembros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        // Validate the Miembros in the database
        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Miembros in Elasticsearch
        verify(mockMiembrosSearchRepository, times(0)).save(miembros);
    }


    @Test
    @Transactional
    public void checkTipoDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setTipoDocumento(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setIdentificacion(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setFechaNacimiento(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneroIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setGenero(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setCelular(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConocimientosQueDominaIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setConocimientosQueDomina(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemasDeInteresIsRequired() throws Exception {
        int databaseSizeBeforeTest = miembrosRepository.findAll().size();
        // set the field null
        miembros.setTemasDeInteres(null);

        // Create the Miembros, which fails.

        restMiembrosMockMvc.perform(post("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMiembros() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList
        restMiembrosMockMvc.perform(get("/api/miembros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembros.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].biografia").value(hasItem(DEFAULT_BIOGRAFIA.toString())))
            .andExpect(jsonPath("$.[*].foto1ContentType").value(hasItem(DEFAULT_FOTO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_1))))
            .andExpect(jsonPath("$.[*].foto2ContentType").value(hasItem(DEFAULT_FOTO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_2))))
            .andExpect(jsonPath("$.[*].fot3ContentType").value(hasItem(DEFAULT_FOT_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fot3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOT_3))))
            .andExpect(jsonPath("$.[*].conocimientosQueDomina").value(hasItem(DEFAULT_CONOCIMIENTOS_QUE_DOMINA.toString())))
            .andExpect(jsonPath("$.[*].temasDeInteres").value(hasItem(DEFAULT_TEMAS_DE_INTERES.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].idGoogle").value(hasItem(DEFAULT_ID_GOOGLE.toString())))
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER.toString())))
            .andExpect(jsonPath("$.[*].derechosDeCompra").value(hasItem(DEFAULT_DERECHOS_DE_COMPRA.booleanValue())))
            .andExpect(jsonPath("$.[*].accesoIlimitado").value(hasItem(DEFAULT_ACCESO_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].aliado").value(hasItem(DEFAULT_ALIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMiembros() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get the miembros
        restMiembrosMockMvc.perform(get("/api/miembros/{id}", miembros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(miembros.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.biografia").value(DEFAULT_BIOGRAFIA.toString()))
            .andExpect(jsonPath("$.foto1ContentType").value(DEFAULT_FOTO_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto1").value(Base64Utils.encodeToString(DEFAULT_FOTO_1)))
            .andExpect(jsonPath("$.foto2ContentType").value(DEFAULT_FOTO_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto2").value(Base64Utils.encodeToString(DEFAULT_FOTO_2)))
            .andExpect(jsonPath("$.fot3ContentType").value(DEFAULT_FOT_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.fot3").value(Base64Utils.encodeToString(DEFAULT_FOT_3)))
            .andExpect(jsonPath("$.conocimientosQueDomina").value(DEFAULT_CONOCIMIENTOS_QUE_DOMINA.toString()))
            .andExpect(jsonPath("$.temasDeInteres").value(DEFAULT_TEMAS_DE_INTERES.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.idGoogle").value(DEFAULT_ID_GOOGLE.toString()))
            .andExpect(jsonPath("$.twiter").value(DEFAULT_TWITER.toString()))
            .andExpect(jsonPath("$.derechosDeCompra").value(DEFAULT_DERECHOS_DE_COMPRA.booleanValue()))
            .andExpect(jsonPath("$.accesoIlimitado").value(DEFAULT_ACCESO_ILIMITADO.booleanValue()))
            .andExpect(jsonPath("$.aliado").value(DEFAULT_ALIADO.booleanValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllMiembrosByTipoDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where tipoDocumento equals to DEFAULT_TIPO_DOCUMENTO
        defaultMiembrosShouldBeFound("tipoDocumento.equals=" + DEFAULT_TIPO_DOCUMENTO);

        // Get all the miembrosList where tipoDocumento equals to UPDATED_TIPO_DOCUMENTO
        defaultMiembrosShouldNotBeFound("tipoDocumento.equals=" + UPDATED_TIPO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByTipoDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where tipoDocumento in DEFAULT_TIPO_DOCUMENTO or UPDATED_TIPO_DOCUMENTO
        defaultMiembrosShouldBeFound("tipoDocumento.in=" + DEFAULT_TIPO_DOCUMENTO + "," + UPDATED_TIPO_DOCUMENTO);

        // Get all the miembrosList where tipoDocumento equals to UPDATED_TIPO_DOCUMENTO
        defaultMiembrosShouldNotBeFound("tipoDocumento.in=" + UPDATED_TIPO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByTipoDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where tipoDocumento is not null
        defaultMiembrosShouldBeFound("tipoDocumento.specified=true");

        // Get all the miembrosList where tipoDocumento is null
        defaultMiembrosShouldNotBeFound("tipoDocumento.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion equals to DEFAULT_IDENTIFICACION
        defaultMiembrosShouldBeFound("identificacion.equals=" + DEFAULT_IDENTIFICACION);

        // Get all the miembrosList where identificacion equals to UPDATED_IDENTIFICACION
        defaultMiembrosShouldNotBeFound("identificacion.equals=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion in DEFAULT_IDENTIFICACION or UPDATED_IDENTIFICACION
        defaultMiembrosShouldBeFound("identificacion.in=" + DEFAULT_IDENTIFICACION + "," + UPDATED_IDENTIFICACION);

        // Get all the miembrosList where identificacion equals to UPDATED_IDENTIFICACION
        defaultMiembrosShouldNotBeFound("identificacion.in=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion is not null
        defaultMiembrosShouldBeFound("identificacion.specified=true");

        // Get all the miembrosList where identificacion is null
        defaultMiembrosShouldNotBeFound("identificacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion is greater than or equal to DEFAULT_IDENTIFICACION
        defaultMiembrosShouldBeFound("identificacion.greaterThanOrEqual=" + DEFAULT_IDENTIFICACION);

        // Get all the miembrosList where identificacion is greater than or equal to UPDATED_IDENTIFICACION
        defaultMiembrosShouldNotBeFound("identificacion.greaterThanOrEqual=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion is less than or equal to DEFAULT_IDENTIFICACION
        defaultMiembrosShouldBeFound("identificacion.lessThanOrEqual=" + DEFAULT_IDENTIFICACION);

        // Get all the miembrosList where identificacion is less than or equal to SMALLER_IDENTIFICACION
        defaultMiembrosShouldNotBeFound("identificacion.lessThanOrEqual=" + SMALLER_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsLessThanSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion is less than DEFAULT_IDENTIFICACION
        defaultMiembrosShouldNotBeFound("identificacion.lessThan=" + DEFAULT_IDENTIFICACION);

        // Get all the miembrosList where identificacion is less than UPDATED_IDENTIFICACION
        defaultMiembrosShouldBeFound("identificacion.lessThan=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdentificacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where identificacion is greater than DEFAULT_IDENTIFICACION
        defaultMiembrosShouldNotBeFound("identificacion.greaterThan=" + DEFAULT_IDENTIFICACION);

        // Get all the miembrosList where identificacion is greater than SMALLER_IDENTIFICACION
        defaultMiembrosShouldBeFound("identificacion.greaterThan=" + SMALLER_IDENTIFICACION);
    }


    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento equals to DEFAULT_FECHA_NACIMIENTO
        defaultMiembrosShouldBeFound("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the miembrosList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultMiembrosShouldNotBeFound("fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento in DEFAULT_FECHA_NACIMIENTO or UPDATED_FECHA_NACIMIENTO
        defaultMiembrosShouldBeFound("fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO);

        // Get all the miembrosList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultMiembrosShouldNotBeFound("fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento is not null
        defaultMiembrosShouldBeFound("fechaNacimiento.specified=true");

        // Get all the miembrosList where fechaNacimiento is null
        defaultMiembrosShouldNotBeFound("fechaNacimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento is greater than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultMiembrosShouldBeFound("fechaNacimiento.greaterThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the miembrosList where fechaNacimiento is greater than or equal to UPDATED_FECHA_NACIMIENTO
        defaultMiembrosShouldNotBeFound("fechaNacimiento.greaterThanOrEqual=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento is less than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultMiembrosShouldBeFound("fechaNacimiento.lessThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the miembrosList where fechaNacimiento is less than or equal to SMALLER_FECHA_NACIMIENTO
        defaultMiembrosShouldNotBeFound("fechaNacimiento.lessThanOrEqual=" + SMALLER_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento is less than DEFAULT_FECHA_NACIMIENTO
        defaultMiembrosShouldNotBeFound("fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the miembrosList where fechaNacimiento is less than UPDATED_FECHA_NACIMIENTO
        defaultMiembrosShouldBeFound("fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaNacimientoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaNacimiento is greater than DEFAULT_FECHA_NACIMIENTO
        defaultMiembrosShouldNotBeFound("fechaNacimiento.greaterThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the miembrosList where fechaNacimiento is greater than SMALLER_FECHA_NACIMIENTO
        defaultMiembrosShouldBeFound("fechaNacimiento.greaterThan=" + SMALLER_FECHA_NACIMIENTO);
    }


    @Test
    @Transactional
    public void getAllMiembrosByFechaRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaRegistro equals to DEFAULT_FECHA_REGISTRO
        defaultMiembrosShouldBeFound("fechaRegistro.equals=" + DEFAULT_FECHA_REGISTRO);

        // Get all the miembrosList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultMiembrosShouldNotBeFound("fechaRegistro.equals=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaRegistro in DEFAULT_FECHA_REGISTRO or UPDATED_FECHA_REGISTRO
        defaultMiembrosShouldBeFound("fechaRegistro.in=" + DEFAULT_FECHA_REGISTRO + "," + UPDATED_FECHA_REGISTRO);

        // Get all the miembrosList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultMiembrosShouldNotBeFound("fechaRegistro.in=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFechaRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where fechaRegistro is not null
        defaultMiembrosShouldBeFound("fechaRegistro.specified=true");

        // Get all the miembrosList where fechaRegistro is null
        defaultMiembrosShouldNotBeFound("fechaRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByGeneroIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where genero equals to DEFAULT_GENERO
        defaultMiembrosShouldBeFound("genero.equals=" + DEFAULT_GENERO);

        // Get all the miembrosList where genero equals to UPDATED_GENERO
        defaultMiembrosShouldNotBeFound("genero.equals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByGeneroIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where genero in DEFAULT_GENERO or UPDATED_GENERO
        defaultMiembrosShouldBeFound("genero.in=" + DEFAULT_GENERO + "," + UPDATED_GENERO);

        // Get all the miembrosList where genero equals to UPDATED_GENERO
        defaultMiembrosShouldNotBeFound("genero.in=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByGeneroIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where genero is not null
        defaultMiembrosShouldBeFound("genero.specified=true");

        // Get all the miembrosList where genero is null
        defaultMiembrosShouldNotBeFound("genero.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where celular equals to DEFAULT_CELULAR
        defaultMiembrosShouldBeFound("celular.equals=" + DEFAULT_CELULAR);

        // Get all the miembrosList where celular equals to UPDATED_CELULAR
        defaultMiembrosShouldNotBeFound("celular.equals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllMiembrosByCelularIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where celular in DEFAULT_CELULAR or UPDATED_CELULAR
        defaultMiembrosShouldBeFound("celular.in=" + DEFAULT_CELULAR + "," + UPDATED_CELULAR);

        // Get all the miembrosList where celular equals to UPDATED_CELULAR
        defaultMiembrosShouldNotBeFound("celular.in=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllMiembrosByCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where celular is not null
        defaultMiembrosShouldBeFound("celular.specified=true");

        // Get all the miembrosList where celular is null
        defaultMiembrosShouldNotBeFound("celular.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByConocimientosQueDominaIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where conocimientosQueDomina equals to DEFAULT_CONOCIMIENTOS_QUE_DOMINA
        defaultMiembrosShouldBeFound("conocimientosQueDomina.equals=" + DEFAULT_CONOCIMIENTOS_QUE_DOMINA);

        // Get all the miembrosList where conocimientosQueDomina equals to UPDATED_CONOCIMIENTOS_QUE_DOMINA
        defaultMiembrosShouldNotBeFound("conocimientosQueDomina.equals=" + UPDATED_CONOCIMIENTOS_QUE_DOMINA);
    }

    @Test
    @Transactional
    public void getAllMiembrosByConocimientosQueDominaIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where conocimientosQueDomina in DEFAULT_CONOCIMIENTOS_QUE_DOMINA or UPDATED_CONOCIMIENTOS_QUE_DOMINA
        defaultMiembrosShouldBeFound("conocimientosQueDomina.in=" + DEFAULT_CONOCIMIENTOS_QUE_DOMINA + "," + UPDATED_CONOCIMIENTOS_QUE_DOMINA);

        // Get all the miembrosList where conocimientosQueDomina equals to UPDATED_CONOCIMIENTOS_QUE_DOMINA
        defaultMiembrosShouldNotBeFound("conocimientosQueDomina.in=" + UPDATED_CONOCIMIENTOS_QUE_DOMINA);
    }

    @Test
    @Transactional
    public void getAllMiembrosByConocimientosQueDominaIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where conocimientosQueDomina is not null
        defaultMiembrosShouldBeFound("conocimientosQueDomina.specified=true");

        // Get all the miembrosList where conocimientosQueDomina is null
        defaultMiembrosShouldNotBeFound("conocimientosQueDomina.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByTemasDeInteresIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where temasDeInteres equals to DEFAULT_TEMAS_DE_INTERES
        defaultMiembrosShouldBeFound("temasDeInteres.equals=" + DEFAULT_TEMAS_DE_INTERES);

        // Get all the miembrosList where temasDeInteres equals to UPDATED_TEMAS_DE_INTERES
        defaultMiembrosShouldNotBeFound("temasDeInteres.equals=" + UPDATED_TEMAS_DE_INTERES);
    }

    @Test
    @Transactional
    public void getAllMiembrosByTemasDeInteresIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where temasDeInteres in DEFAULT_TEMAS_DE_INTERES or UPDATED_TEMAS_DE_INTERES
        defaultMiembrosShouldBeFound("temasDeInteres.in=" + DEFAULT_TEMAS_DE_INTERES + "," + UPDATED_TEMAS_DE_INTERES);

        // Get all the miembrosList where temasDeInteres equals to UPDATED_TEMAS_DE_INTERES
        defaultMiembrosShouldNotBeFound("temasDeInteres.in=" + UPDATED_TEMAS_DE_INTERES);
    }

    @Test
    @Transactional
    public void getAllMiembrosByTemasDeInteresIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where temasDeInteres is not null
        defaultMiembrosShouldBeFound("temasDeInteres.specified=true");

        // Get all the miembrosList where temasDeInteres is null
        defaultMiembrosShouldNotBeFound("temasDeInteres.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByFacebookIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where facebook equals to DEFAULT_FACEBOOK
        defaultMiembrosShouldBeFound("facebook.equals=" + DEFAULT_FACEBOOK);

        // Get all the miembrosList where facebook equals to UPDATED_FACEBOOK
        defaultMiembrosShouldNotBeFound("facebook.equals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFacebookIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where facebook in DEFAULT_FACEBOOK or UPDATED_FACEBOOK
        defaultMiembrosShouldBeFound("facebook.in=" + DEFAULT_FACEBOOK + "," + UPDATED_FACEBOOK);

        // Get all the miembrosList where facebook equals to UPDATED_FACEBOOK
        defaultMiembrosShouldNotBeFound("facebook.in=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllMiembrosByFacebookIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where facebook is not null
        defaultMiembrosShouldBeFound("facebook.specified=true");

        // Get all the miembrosList where facebook is null
        defaultMiembrosShouldNotBeFound("facebook.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByInstagramIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where instagram equals to DEFAULT_INSTAGRAM
        defaultMiembrosShouldBeFound("instagram.equals=" + DEFAULT_INSTAGRAM);

        // Get all the miembrosList where instagram equals to UPDATED_INSTAGRAM
        defaultMiembrosShouldNotBeFound("instagram.equals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllMiembrosByInstagramIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where instagram in DEFAULT_INSTAGRAM or UPDATED_INSTAGRAM
        defaultMiembrosShouldBeFound("instagram.in=" + DEFAULT_INSTAGRAM + "," + UPDATED_INSTAGRAM);

        // Get all the miembrosList where instagram equals to UPDATED_INSTAGRAM
        defaultMiembrosShouldNotBeFound("instagram.in=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllMiembrosByInstagramIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where instagram is not null
        defaultMiembrosShouldBeFound("instagram.specified=true");

        // Get all the miembrosList where instagram is null
        defaultMiembrosShouldNotBeFound("instagram.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdGoogleIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where idGoogle equals to DEFAULT_ID_GOOGLE
        defaultMiembrosShouldBeFound("idGoogle.equals=" + DEFAULT_ID_GOOGLE);

        // Get all the miembrosList where idGoogle equals to UPDATED_ID_GOOGLE
        defaultMiembrosShouldNotBeFound("idGoogle.equals=" + UPDATED_ID_GOOGLE);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdGoogleIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where idGoogle in DEFAULT_ID_GOOGLE or UPDATED_ID_GOOGLE
        defaultMiembrosShouldBeFound("idGoogle.in=" + DEFAULT_ID_GOOGLE + "," + UPDATED_ID_GOOGLE);

        // Get all the miembrosList where idGoogle equals to UPDATED_ID_GOOGLE
        defaultMiembrosShouldNotBeFound("idGoogle.in=" + UPDATED_ID_GOOGLE);
    }

    @Test
    @Transactional
    public void getAllMiembrosByIdGoogleIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where idGoogle is not null
        defaultMiembrosShouldBeFound("idGoogle.specified=true");

        // Get all the miembrosList where idGoogle is null
        defaultMiembrosShouldNotBeFound("idGoogle.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByTwiterIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where twiter equals to DEFAULT_TWITER
        defaultMiembrosShouldBeFound("twiter.equals=" + DEFAULT_TWITER);

        // Get all the miembrosList where twiter equals to UPDATED_TWITER
        defaultMiembrosShouldNotBeFound("twiter.equals=" + UPDATED_TWITER);
    }

    @Test
    @Transactional
    public void getAllMiembrosByTwiterIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where twiter in DEFAULT_TWITER or UPDATED_TWITER
        defaultMiembrosShouldBeFound("twiter.in=" + DEFAULT_TWITER + "," + UPDATED_TWITER);

        // Get all the miembrosList where twiter equals to UPDATED_TWITER
        defaultMiembrosShouldNotBeFound("twiter.in=" + UPDATED_TWITER);
    }

    @Test
    @Transactional
    public void getAllMiembrosByTwiterIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where twiter is not null
        defaultMiembrosShouldBeFound("twiter.specified=true");

        // Get all the miembrosList where twiter is null
        defaultMiembrosShouldNotBeFound("twiter.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByDerechosDeCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where derechosDeCompra equals to DEFAULT_DERECHOS_DE_COMPRA
        defaultMiembrosShouldBeFound("derechosDeCompra.equals=" + DEFAULT_DERECHOS_DE_COMPRA);

        // Get all the miembrosList where derechosDeCompra equals to UPDATED_DERECHOS_DE_COMPRA
        defaultMiembrosShouldNotBeFound("derechosDeCompra.equals=" + UPDATED_DERECHOS_DE_COMPRA);
    }

    @Test
    @Transactional
    public void getAllMiembrosByDerechosDeCompraIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where derechosDeCompra in DEFAULT_DERECHOS_DE_COMPRA or UPDATED_DERECHOS_DE_COMPRA
        defaultMiembrosShouldBeFound("derechosDeCompra.in=" + DEFAULT_DERECHOS_DE_COMPRA + "," + UPDATED_DERECHOS_DE_COMPRA);

        // Get all the miembrosList where derechosDeCompra equals to UPDATED_DERECHOS_DE_COMPRA
        defaultMiembrosShouldNotBeFound("derechosDeCompra.in=" + UPDATED_DERECHOS_DE_COMPRA);
    }

    @Test
    @Transactional
    public void getAllMiembrosByDerechosDeCompraIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where derechosDeCompra is not null
        defaultMiembrosShouldBeFound("derechosDeCompra.specified=true");

        // Get all the miembrosList where derechosDeCompra is null
        defaultMiembrosShouldNotBeFound("derechosDeCompra.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByAccesoIlimitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where accesoIlimitado equals to DEFAULT_ACCESO_ILIMITADO
        defaultMiembrosShouldBeFound("accesoIlimitado.equals=" + DEFAULT_ACCESO_ILIMITADO);

        // Get all the miembrosList where accesoIlimitado equals to UPDATED_ACCESO_ILIMITADO
        defaultMiembrosShouldNotBeFound("accesoIlimitado.equals=" + UPDATED_ACCESO_ILIMITADO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByAccesoIlimitadoIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where accesoIlimitado in DEFAULT_ACCESO_ILIMITADO or UPDATED_ACCESO_ILIMITADO
        defaultMiembrosShouldBeFound("accesoIlimitado.in=" + DEFAULT_ACCESO_ILIMITADO + "," + UPDATED_ACCESO_ILIMITADO);

        // Get all the miembrosList where accesoIlimitado equals to UPDATED_ACCESO_ILIMITADO
        defaultMiembrosShouldNotBeFound("accesoIlimitado.in=" + UPDATED_ACCESO_ILIMITADO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByAccesoIlimitadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where accesoIlimitado is not null
        defaultMiembrosShouldBeFound("accesoIlimitado.specified=true");

        // Get all the miembrosList where accesoIlimitado is null
        defaultMiembrosShouldNotBeFound("accesoIlimitado.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByAliadoIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where aliado equals to DEFAULT_ALIADO
        defaultMiembrosShouldBeFound("aliado.equals=" + DEFAULT_ALIADO);

        // Get all the miembrosList where aliado equals to UPDATED_ALIADO
        defaultMiembrosShouldNotBeFound("aliado.equals=" + UPDATED_ALIADO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByAliadoIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where aliado in DEFAULT_ALIADO or UPDATED_ALIADO
        defaultMiembrosShouldBeFound("aliado.in=" + DEFAULT_ALIADO + "," + UPDATED_ALIADO);

        // Get all the miembrosList where aliado equals to UPDATED_ALIADO
        defaultMiembrosShouldNotBeFound("aliado.in=" + UPDATED_ALIADO);
    }

    @Test
    @Transactional
    public void getAllMiembrosByAliadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where aliado is not null
        defaultMiembrosShouldBeFound("aliado.specified=true");

        // Get all the miembrosList where aliado is null
        defaultMiembrosShouldNotBeFound("aliado.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByHostIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where host equals to DEFAULT_HOST
        defaultMiembrosShouldBeFound("host.equals=" + DEFAULT_HOST);

        // Get all the miembrosList where host equals to UPDATED_HOST
        defaultMiembrosShouldNotBeFound("host.equals=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    public void getAllMiembrosByHostIsInShouldWork() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where host in DEFAULT_HOST or UPDATED_HOST
        defaultMiembrosShouldBeFound("host.in=" + DEFAULT_HOST + "," + UPDATED_HOST);

        // Get all the miembrosList where host equals to UPDATED_HOST
        defaultMiembrosShouldNotBeFound("host.in=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    public void getAllMiembrosByHostIsNullOrNotNull() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);

        // Get all the miembrosList where host is not null
        defaultMiembrosShouldBeFound("host.specified=true");

        // Get all the miembrosList where host is null
        defaultMiembrosShouldNotBeFound("host.specified=false");
    }

    @Test
    @Transactional
    public void getAllMiembrosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);
        User miembro = UserResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        miembros.setMiembro(miembro);
        miembrosRepository.saveAndFlush(miembros);
        Long miembroId = miembro.getId();

        // Get all the miembrosList where miembro equals to miembroId
        defaultMiembrosShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the miembrosList where miembro equals to miembroId + 1
        defaultMiembrosShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }


    @Test
    @Transactional
    public void getAllMiembrosByNacionalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosRepository.saveAndFlush(miembros);
        Pais nacionalidad = PaisResourceIT.createEntity(em);
        em.persist(nacionalidad);
        em.flush();
        miembros.setNacionalidad(nacionalidad);
        miembrosRepository.saveAndFlush(miembros);
        Long nacionalidadId = nacionalidad.getId();

        // Get all the miembrosList where nacionalidad equals to nacionalidadId
        defaultMiembrosShouldBeFound("nacionalidadId.equals=" + nacionalidadId);

        // Get all the miembrosList where nacionalidad equals to nacionalidadId + 1
        defaultMiembrosShouldNotBeFound("nacionalidadId.equals=" + (nacionalidadId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMiembrosShouldBeFound(String filter) throws Exception {
        restMiembrosMockMvc.perform(get("/api/miembros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembros.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].biografia").value(hasItem(DEFAULT_BIOGRAFIA.toString())))
            .andExpect(jsonPath("$.[*].foto1ContentType").value(hasItem(DEFAULT_FOTO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_1))))
            .andExpect(jsonPath("$.[*].foto2ContentType").value(hasItem(DEFAULT_FOTO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_2))))
            .andExpect(jsonPath("$.[*].fot3ContentType").value(hasItem(DEFAULT_FOT_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fot3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOT_3))))
            .andExpect(jsonPath("$.[*].conocimientosQueDomina").value(hasItem(DEFAULT_CONOCIMIENTOS_QUE_DOMINA)))
            .andExpect(jsonPath("$.[*].temasDeInteres").value(hasItem(DEFAULT_TEMAS_DE_INTERES)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].idGoogle").value(hasItem(DEFAULT_ID_GOOGLE)))
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER)))
            .andExpect(jsonPath("$.[*].derechosDeCompra").value(hasItem(DEFAULT_DERECHOS_DE_COMPRA.booleanValue())))
            .andExpect(jsonPath("$.[*].accesoIlimitado").value(hasItem(DEFAULT_ACCESO_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].aliado").value(hasItem(DEFAULT_ALIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.booleanValue())));

        // Check, that the count call also returns 1
        restMiembrosMockMvc.perform(get("/api/miembros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMiembrosShouldNotBeFound(String filter) throws Exception {
        restMiembrosMockMvc.perform(get("/api/miembros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMiembrosMockMvc.perform(get("/api/miembros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMiembros() throws Exception {
        // Get the miembros
        restMiembrosMockMvc.perform(get("/api/miembros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMiembros() throws Exception {
        // Initialize the database
        miembrosService.save(miembros);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMiembrosSearchRepository);

        int databaseSizeBeforeUpdate = miembrosRepository.findAll().size();

        // Update the miembros
        Miembros updatedMiembros = miembrosRepository.findById(miembros.getId()).get();
        // Disconnect from session so that the updates on updatedMiembros are not directly saved in db
        em.detach(updatedMiembros);
        updatedMiembros
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .identificacion(UPDATED_IDENTIFICACION)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .genero(UPDATED_GENERO)
            .celular(UPDATED_CELULAR)
            .biografia(UPDATED_BIOGRAFIA)
            .foto1(UPDATED_FOTO_1)
            .foto1ContentType(UPDATED_FOTO_1_CONTENT_TYPE)
            .foto2(UPDATED_FOTO_2)
            .foto2ContentType(UPDATED_FOTO_2_CONTENT_TYPE)
            .fot3(UPDATED_FOT_3)
            .fot3ContentType(UPDATED_FOT_3_CONTENT_TYPE)
            .conocimientosQueDomina(UPDATED_CONOCIMIENTOS_QUE_DOMINA)
            .temasDeInteres(UPDATED_TEMAS_DE_INTERES)
            .facebook(UPDATED_FACEBOOK)
            .instagram(UPDATED_INSTAGRAM)
            .idGoogle(UPDATED_ID_GOOGLE)
            .twiter(UPDATED_TWITER)
            .derechosDeCompra(UPDATED_DERECHOS_DE_COMPRA)
            .accesoIlimitado(UPDATED_ACCESO_ILIMITADO)
            .aliado(UPDATED_ALIADO)
            .host(UPDATED_HOST);

        restMiembrosMockMvc.perform(put("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMiembros)))
            .andExpect(status().isOk());

        // Validate the Miembros in the database
        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeUpdate);
        Miembros testMiembros = miembrosList.get(miembrosList.size() - 1);
        assertThat(testMiembros.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testMiembros.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testMiembros.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testMiembros.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testMiembros.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testMiembros.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testMiembros.getBiografia()).isEqualTo(UPDATED_BIOGRAFIA);
        assertThat(testMiembros.getFoto1()).isEqualTo(UPDATED_FOTO_1);
        assertThat(testMiembros.getFoto1ContentType()).isEqualTo(UPDATED_FOTO_1_CONTENT_TYPE);
        assertThat(testMiembros.getFoto2()).isEqualTo(UPDATED_FOTO_2);
        assertThat(testMiembros.getFoto2ContentType()).isEqualTo(UPDATED_FOTO_2_CONTENT_TYPE);
        assertThat(testMiembros.getFot3()).isEqualTo(UPDATED_FOT_3);
        assertThat(testMiembros.getFot3ContentType()).isEqualTo(UPDATED_FOT_3_CONTENT_TYPE);
        assertThat(testMiembros.getConocimientosQueDomina()).isEqualTo(UPDATED_CONOCIMIENTOS_QUE_DOMINA);
        assertThat(testMiembros.getTemasDeInteres()).isEqualTo(UPDATED_TEMAS_DE_INTERES);
        assertThat(testMiembros.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testMiembros.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testMiembros.getIdGoogle()).isEqualTo(UPDATED_ID_GOOGLE);
        assertThat(testMiembros.getTwiter()).isEqualTo(UPDATED_TWITER);
        assertThat(testMiembros.isDerechosDeCompra()).isEqualTo(UPDATED_DERECHOS_DE_COMPRA);
        assertThat(testMiembros.isAccesoIlimitado()).isEqualTo(UPDATED_ACCESO_ILIMITADO);
        assertThat(testMiembros.isAliado()).isEqualTo(UPDATED_ALIADO);
        assertThat(testMiembros.isHost()).isEqualTo(UPDATED_HOST);

        // Validate the Miembros in Elasticsearch
        verify(mockMiembrosSearchRepository, times(1)).save(testMiembros);
    }

    @Test
    @Transactional
    public void updateNonExistingMiembros() throws Exception {
        int databaseSizeBeforeUpdate = miembrosRepository.findAll().size();

        // Create the Miembros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiembrosMockMvc.perform(put("/api/miembros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembros)))
            .andExpect(status().isBadRequest());

        // Validate the Miembros in the database
        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Miembros in Elasticsearch
        verify(mockMiembrosSearchRepository, times(0)).save(miembros);
    }

    @Test
    @Transactional
    public void deleteMiembros() throws Exception {
        // Initialize the database
        miembrosService.save(miembros);

        int databaseSizeBeforeDelete = miembrosRepository.findAll().size();

        // Delete the miembros
        restMiembrosMockMvc.perform(delete("/api/miembros/{id}", miembros.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Miembros> miembrosList = miembrosRepository.findAll();
        assertThat(miembrosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Miembros in Elasticsearch
        verify(mockMiembrosSearchRepository, times(1)).deleteById(miembros.getId());
    }

    @Test
    @Transactional
    public void searchMiembros() throws Exception {
        // Initialize the database
        miembrosService.save(miembros);
        when(mockMiembrosSearchRepository.search(queryStringQuery("id:" + miembros.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(miembros), PageRequest.of(0, 1), 1));
        // Search the miembros
        restMiembrosMockMvc.perform(get("/api/_search/miembros?query=id:" + miembros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembros.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].biografia").value(hasItem(DEFAULT_BIOGRAFIA.toString())))
            .andExpect(jsonPath("$.[*].foto1ContentType").value(hasItem(DEFAULT_FOTO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_1))))
            .andExpect(jsonPath("$.[*].foto2ContentType").value(hasItem(DEFAULT_FOTO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_2))))
            .andExpect(jsonPath("$.[*].fot3ContentType").value(hasItem(DEFAULT_FOT_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fot3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOT_3))))
            .andExpect(jsonPath("$.[*].conocimientosQueDomina").value(hasItem(DEFAULT_CONOCIMIENTOS_QUE_DOMINA)))
            .andExpect(jsonPath("$.[*].temasDeInteres").value(hasItem(DEFAULT_TEMAS_DE_INTERES)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].idGoogle").value(hasItem(DEFAULT_ID_GOOGLE)))
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER)))
            .andExpect(jsonPath("$.[*].derechosDeCompra").value(hasItem(DEFAULT_DERECHOS_DE_COMPRA.booleanValue())))
            .andExpect(jsonPath("$.[*].accesoIlimitado").value(hasItem(DEFAULT_ACCESO_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].aliado").value(hasItem(DEFAULT_ALIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Miembros.class);
        Miembros miembros1 = new Miembros();
        miembros1.setId(1L);
        Miembros miembros2 = new Miembros();
        miembros2.setId(miembros1.getId());
        assertThat(miembros1).isEqualTo(miembros2);
        miembros2.setId(2L);
        assertThat(miembros1).isNotEqualTo(miembros2);
        miembros1.setId(null);
        assertThat(miembros1).isNotEqualTo(miembros2);
    }
}
