package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.PerfilEquipoEmpresa;
import io.github.jhipster.newoapp13.domain.Empresa;
import io.github.jhipster.newoapp13.repository.PerfilEquipoEmpresaRepository;
import io.github.jhipster.newoapp13.repository.search.PerfilEquipoEmpresaSearchRepository;
import io.github.jhipster.newoapp13.service.PerfilEquipoEmpresaService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.PerfilEquipoEmpresaCriteria;
import io.github.jhipster.newoapp13.service.PerfilEquipoEmpresaQueryService;

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

/**
 * Integration tests for the {@link PerfilEquipoEmpresaResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class PerfilEquipoEmpresaResourceIT {

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

    @Autowired
    private PerfilEquipoEmpresaRepository perfilEquipoEmpresaRepository;

    @Autowired
    private PerfilEquipoEmpresaService perfilEquipoEmpresaService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.PerfilEquipoEmpresaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PerfilEquipoEmpresaSearchRepository mockPerfilEquipoEmpresaSearchRepository;

    @Autowired
    private PerfilEquipoEmpresaQueryService perfilEquipoEmpresaQueryService;

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

    private MockMvc restPerfilEquipoEmpresaMockMvc;

    private PerfilEquipoEmpresa perfilEquipoEmpresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerfilEquipoEmpresaResource perfilEquipoEmpresaResource = new PerfilEquipoEmpresaResource(perfilEquipoEmpresaService, perfilEquipoEmpresaQueryService);
        this.restPerfilEquipoEmpresaMockMvc = MockMvcBuilders.standaloneSetup(perfilEquipoEmpresaResource)
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
    public static PerfilEquipoEmpresa createEntity(EntityManager em) {
        PerfilEquipoEmpresa perfilEquipoEmpresa = new PerfilEquipoEmpresa()
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
            .twiter(DEFAULT_TWITER);
        return perfilEquipoEmpresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilEquipoEmpresa createUpdatedEntity(EntityManager em) {
        PerfilEquipoEmpresa perfilEquipoEmpresa = new PerfilEquipoEmpresa()
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
            .twiter(UPDATED_TWITER);
        return perfilEquipoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        perfilEquipoEmpresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfilEquipoEmpresa() throws Exception {
        int databaseSizeBeforeCreate = perfilEquipoEmpresaRepository.findAll().size();

        // Create the PerfilEquipoEmpresa
        restPerfilEquipoEmpresaMockMvc.perform(post("/api/perfil-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilEquipoEmpresa)))
            .andExpect(status().isCreated());

        // Validate the PerfilEquipoEmpresa in the database
        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeCreate + 1);
        PerfilEquipoEmpresa testPerfilEquipoEmpresa = perfilEquipoEmpresaList.get(perfilEquipoEmpresaList.size() - 1);
        assertThat(testPerfilEquipoEmpresa.getBiografia()).isEqualTo(DEFAULT_BIOGRAFIA);
        assertThat(testPerfilEquipoEmpresa.getFoto1()).isEqualTo(DEFAULT_FOTO_1);
        assertThat(testPerfilEquipoEmpresa.getFoto1ContentType()).isEqualTo(DEFAULT_FOTO_1_CONTENT_TYPE);
        assertThat(testPerfilEquipoEmpresa.getFoto2()).isEqualTo(DEFAULT_FOTO_2);
        assertThat(testPerfilEquipoEmpresa.getFoto2ContentType()).isEqualTo(DEFAULT_FOTO_2_CONTENT_TYPE);
        assertThat(testPerfilEquipoEmpresa.getFot3()).isEqualTo(DEFAULT_FOT_3);
        assertThat(testPerfilEquipoEmpresa.getFot3ContentType()).isEqualTo(DEFAULT_FOT_3_CONTENT_TYPE);
        assertThat(testPerfilEquipoEmpresa.getConocimientosQueDomina()).isEqualTo(DEFAULT_CONOCIMIENTOS_QUE_DOMINA);
        assertThat(testPerfilEquipoEmpresa.getTemasDeInteres()).isEqualTo(DEFAULT_TEMAS_DE_INTERES);
        assertThat(testPerfilEquipoEmpresa.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testPerfilEquipoEmpresa.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testPerfilEquipoEmpresa.getIdGoogle()).isEqualTo(DEFAULT_ID_GOOGLE);
        assertThat(testPerfilEquipoEmpresa.getTwiter()).isEqualTo(DEFAULT_TWITER);

        // Validate the PerfilEquipoEmpresa in Elasticsearch
        verify(mockPerfilEquipoEmpresaSearchRepository, times(1)).save(testPerfilEquipoEmpresa);
    }

    @Test
    @Transactional
    public void createPerfilEquipoEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilEquipoEmpresaRepository.findAll().size();

        // Create the PerfilEquipoEmpresa with an existing ID
        perfilEquipoEmpresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilEquipoEmpresaMockMvc.perform(post("/api/perfil-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilEquipoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilEquipoEmpresa in the database
        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeCreate);

        // Validate the PerfilEquipoEmpresa in Elasticsearch
        verify(mockPerfilEquipoEmpresaSearchRepository, times(0)).save(perfilEquipoEmpresa);
    }


    @Test
    @Transactional
    public void checkConocimientosQueDominaIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilEquipoEmpresaRepository.findAll().size();
        // set the field null
        perfilEquipoEmpresa.setConocimientosQueDomina(null);

        // Create the PerfilEquipoEmpresa, which fails.

        restPerfilEquipoEmpresaMockMvc.perform(post("/api/perfil-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilEquipoEmpresa)))
            .andExpect(status().isBadRequest());

        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemasDeInteresIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilEquipoEmpresaRepository.findAll().size();
        // set the field null
        perfilEquipoEmpresa.setTemasDeInteres(null);

        // Create the PerfilEquipoEmpresa, which fails.

        restPerfilEquipoEmpresaMockMvc.perform(post("/api/perfil-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilEquipoEmpresa)))
            .andExpect(status().isBadRequest());

        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresas() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilEquipoEmpresa.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER.toString())));
    }
    
    @Test
    @Transactional
    public void getPerfilEquipoEmpresa() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get the perfilEquipoEmpresa
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas/{id}", perfilEquipoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfilEquipoEmpresa.getId().intValue()))
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
            .andExpect(jsonPath("$.twiter").value(DEFAULT_TWITER.toString()));
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByConocimientosQueDominaIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where conocimientosQueDomina equals to DEFAULT_CONOCIMIENTOS_QUE_DOMINA
        defaultPerfilEquipoEmpresaShouldBeFound("conocimientosQueDomina.equals=" + DEFAULT_CONOCIMIENTOS_QUE_DOMINA);

        // Get all the perfilEquipoEmpresaList where conocimientosQueDomina equals to UPDATED_CONOCIMIENTOS_QUE_DOMINA
        defaultPerfilEquipoEmpresaShouldNotBeFound("conocimientosQueDomina.equals=" + UPDATED_CONOCIMIENTOS_QUE_DOMINA);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByConocimientosQueDominaIsInShouldWork() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where conocimientosQueDomina in DEFAULT_CONOCIMIENTOS_QUE_DOMINA or UPDATED_CONOCIMIENTOS_QUE_DOMINA
        defaultPerfilEquipoEmpresaShouldBeFound("conocimientosQueDomina.in=" + DEFAULT_CONOCIMIENTOS_QUE_DOMINA + "," + UPDATED_CONOCIMIENTOS_QUE_DOMINA);

        // Get all the perfilEquipoEmpresaList where conocimientosQueDomina equals to UPDATED_CONOCIMIENTOS_QUE_DOMINA
        defaultPerfilEquipoEmpresaShouldNotBeFound("conocimientosQueDomina.in=" + UPDATED_CONOCIMIENTOS_QUE_DOMINA);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByConocimientosQueDominaIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where conocimientosQueDomina is not null
        defaultPerfilEquipoEmpresaShouldBeFound("conocimientosQueDomina.specified=true");

        // Get all the perfilEquipoEmpresaList where conocimientosQueDomina is null
        defaultPerfilEquipoEmpresaShouldNotBeFound("conocimientosQueDomina.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByTemasDeInteresIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where temasDeInteres equals to DEFAULT_TEMAS_DE_INTERES
        defaultPerfilEquipoEmpresaShouldBeFound("temasDeInteres.equals=" + DEFAULT_TEMAS_DE_INTERES);

        // Get all the perfilEquipoEmpresaList where temasDeInteres equals to UPDATED_TEMAS_DE_INTERES
        defaultPerfilEquipoEmpresaShouldNotBeFound("temasDeInteres.equals=" + UPDATED_TEMAS_DE_INTERES);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByTemasDeInteresIsInShouldWork() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where temasDeInteres in DEFAULT_TEMAS_DE_INTERES or UPDATED_TEMAS_DE_INTERES
        defaultPerfilEquipoEmpresaShouldBeFound("temasDeInteres.in=" + DEFAULT_TEMAS_DE_INTERES + "," + UPDATED_TEMAS_DE_INTERES);

        // Get all the perfilEquipoEmpresaList where temasDeInteres equals to UPDATED_TEMAS_DE_INTERES
        defaultPerfilEquipoEmpresaShouldNotBeFound("temasDeInteres.in=" + UPDATED_TEMAS_DE_INTERES);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByTemasDeInteresIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where temasDeInteres is not null
        defaultPerfilEquipoEmpresaShouldBeFound("temasDeInteres.specified=true");

        // Get all the perfilEquipoEmpresaList where temasDeInteres is null
        defaultPerfilEquipoEmpresaShouldNotBeFound("temasDeInteres.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByFacebookIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where facebook equals to DEFAULT_FACEBOOK
        defaultPerfilEquipoEmpresaShouldBeFound("facebook.equals=" + DEFAULT_FACEBOOK);

        // Get all the perfilEquipoEmpresaList where facebook equals to UPDATED_FACEBOOK
        defaultPerfilEquipoEmpresaShouldNotBeFound("facebook.equals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByFacebookIsInShouldWork() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where facebook in DEFAULT_FACEBOOK or UPDATED_FACEBOOK
        defaultPerfilEquipoEmpresaShouldBeFound("facebook.in=" + DEFAULT_FACEBOOK + "," + UPDATED_FACEBOOK);

        // Get all the perfilEquipoEmpresaList where facebook equals to UPDATED_FACEBOOK
        defaultPerfilEquipoEmpresaShouldNotBeFound("facebook.in=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByFacebookIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where facebook is not null
        defaultPerfilEquipoEmpresaShouldBeFound("facebook.specified=true");

        // Get all the perfilEquipoEmpresaList where facebook is null
        defaultPerfilEquipoEmpresaShouldNotBeFound("facebook.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByInstagramIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where instagram equals to DEFAULT_INSTAGRAM
        defaultPerfilEquipoEmpresaShouldBeFound("instagram.equals=" + DEFAULT_INSTAGRAM);

        // Get all the perfilEquipoEmpresaList where instagram equals to UPDATED_INSTAGRAM
        defaultPerfilEquipoEmpresaShouldNotBeFound("instagram.equals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByInstagramIsInShouldWork() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where instagram in DEFAULT_INSTAGRAM or UPDATED_INSTAGRAM
        defaultPerfilEquipoEmpresaShouldBeFound("instagram.in=" + DEFAULT_INSTAGRAM + "," + UPDATED_INSTAGRAM);

        // Get all the perfilEquipoEmpresaList where instagram equals to UPDATED_INSTAGRAM
        defaultPerfilEquipoEmpresaShouldNotBeFound("instagram.in=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByInstagramIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where instagram is not null
        defaultPerfilEquipoEmpresaShouldBeFound("instagram.specified=true");

        // Get all the perfilEquipoEmpresaList where instagram is null
        defaultPerfilEquipoEmpresaShouldNotBeFound("instagram.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByIdGoogleIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where idGoogle equals to DEFAULT_ID_GOOGLE
        defaultPerfilEquipoEmpresaShouldBeFound("idGoogle.equals=" + DEFAULT_ID_GOOGLE);

        // Get all the perfilEquipoEmpresaList where idGoogle equals to UPDATED_ID_GOOGLE
        defaultPerfilEquipoEmpresaShouldNotBeFound("idGoogle.equals=" + UPDATED_ID_GOOGLE);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByIdGoogleIsInShouldWork() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where idGoogle in DEFAULT_ID_GOOGLE or UPDATED_ID_GOOGLE
        defaultPerfilEquipoEmpresaShouldBeFound("idGoogle.in=" + DEFAULT_ID_GOOGLE + "," + UPDATED_ID_GOOGLE);

        // Get all the perfilEquipoEmpresaList where idGoogle equals to UPDATED_ID_GOOGLE
        defaultPerfilEquipoEmpresaShouldNotBeFound("idGoogle.in=" + UPDATED_ID_GOOGLE);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByIdGoogleIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where idGoogle is not null
        defaultPerfilEquipoEmpresaShouldBeFound("idGoogle.specified=true");

        // Get all the perfilEquipoEmpresaList where idGoogle is null
        defaultPerfilEquipoEmpresaShouldNotBeFound("idGoogle.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByTwiterIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where twiter equals to DEFAULT_TWITER
        defaultPerfilEquipoEmpresaShouldBeFound("twiter.equals=" + DEFAULT_TWITER);

        // Get all the perfilEquipoEmpresaList where twiter equals to UPDATED_TWITER
        defaultPerfilEquipoEmpresaShouldNotBeFound("twiter.equals=" + UPDATED_TWITER);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByTwiterIsInShouldWork() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where twiter in DEFAULT_TWITER or UPDATED_TWITER
        defaultPerfilEquipoEmpresaShouldBeFound("twiter.in=" + DEFAULT_TWITER + "," + UPDATED_TWITER);

        // Get all the perfilEquipoEmpresaList where twiter equals to UPDATED_TWITER
        defaultPerfilEquipoEmpresaShouldNotBeFound("twiter.in=" + UPDATED_TWITER);
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByTwiterIsNullOrNotNull() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);

        // Get all the perfilEquipoEmpresaList where twiter is not null
        defaultPerfilEquipoEmpresaShouldBeFound("twiter.specified=true");

        // Get all the perfilEquipoEmpresaList where twiter is null
        defaultPerfilEquipoEmpresaShouldNotBeFound("twiter.specified=false");
    }

    @Test
    @Transactional
    public void getAllPerfilEquipoEmpresasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        perfilEquipoEmpresa.setEmpresa(empresa);
        perfilEquipoEmpresaRepository.saveAndFlush(perfilEquipoEmpresa);
        Long empresaId = empresa.getId();

        // Get all the perfilEquipoEmpresaList where empresa equals to empresaId
        defaultPerfilEquipoEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the perfilEquipoEmpresaList where empresa equals to empresaId + 1
        defaultPerfilEquipoEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilEquipoEmpresaShouldBeFound(String filter) throws Exception {
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilEquipoEmpresa.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER)));

        // Check, that the count call also returns 1
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilEquipoEmpresaShouldNotBeFound(String filter) throws Exception {
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPerfilEquipoEmpresa() throws Exception {
        // Get the perfilEquipoEmpresa
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/perfil-equipo-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfilEquipoEmpresa() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaService.save(perfilEquipoEmpresa);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPerfilEquipoEmpresaSearchRepository);

        int databaseSizeBeforeUpdate = perfilEquipoEmpresaRepository.findAll().size();

        // Update the perfilEquipoEmpresa
        PerfilEquipoEmpresa updatedPerfilEquipoEmpresa = perfilEquipoEmpresaRepository.findById(perfilEquipoEmpresa.getId()).get();
        // Disconnect from session so that the updates on updatedPerfilEquipoEmpresa are not directly saved in db
        em.detach(updatedPerfilEquipoEmpresa);
        updatedPerfilEquipoEmpresa
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
            .twiter(UPDATED_TWITER);

        restPerfilEquipoEmpresaMockMvc.perform(put("/api/perfil-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerfilEquipoEmpresa)))
            .andExpect(status().isOk());

        // Validate the PerfilEquipoEmpresa in the database
        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeUpdate);
        PerfilEquipoEmpresa testPerfilEquipoEmpresa = perfilEquipoEmpresaList.get(perfilEquipoEmpresaList.size() - 1);
        assertThat(testPerfilEquipoEmpresa.getBiografia()).isEqualTo(UPDATED_BIOGRAFIA);
        assertThat(testPerfilEquipoEmpresa.getFoto1()).isEqualTo(UPDATED_FOTO_1);
        assertThat(testPerfilEquipoEmpresa.getFoto1ContentType()).isEqualTo(UPDATED_FOTO_1_CONTENT_TYPE);
        assertThat(testPerfilEquipoEmpresa.getFoto2()).isEqualTo(UPDATED_FOTO_2);
        assertThat(testPerfilEquipoEmpresa.getFoto2ContentType()).isEqualTo(UPDATED_FOTO_2_CONTENT_TYPE);
        assertThat(testPerfilEquipoEmpresa.getFot3()).isEqualTo(UPDATED_FOT_3);
        assertThat(testPerfilEquipoEmpresa.getFot3ContentType()).isEqualTo(UPDATED_FOT_3_CONTENT_TYPE);
        assertThat(testPerfilEquipoEmpresa.getConocimientosQueDomina()).isEqualTo(UPDATED_CONOCIMIENTOS_QUE_DOMINA);
        assertThat(testPerfilEquipoEmpresa.getTemasDeInteres()).isEqualTo(UPDATED_TEMAS_DE_INTERES);
        assertThat(testPerfilEquipoEmpresa.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testPerfilEquipoEmpresa.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testPerfilEquipoEmpresa.getIdGoogle()).isEqualTo(UPDATED_ID_GOOGLE);
        assertThat(testPerfilEquipoEmpresa.getTwiter()).isEqualTo(UPDATED_TWITER);

        // Validate the PerfilEquipoEmpresa in Elasticsearch
        verify(mockPerfilEquipoEmpresaSearchRepository, times(1)).save(testPerfilEquipoEmpresa);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfilEquipoEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = perfilEquipoEmpresaRepository.findAll().size();

        // Create the PerfilEquipoEmpresa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilEquipoEmpresaMockMvc.perform(put("/api/perfil-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfilEquipoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilEquipoEmpresa in the database
        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PerfilEquipoEmpresa in Elasticsearch
        verify(mockPerfilEquipoEmpresaSearchRepository, times(0)).save(perfilEquipoEmpresa);
    }

    @Test
    @Transactional
    public void deletePerfilEquipoEmpresa() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaService.save(perfilEquipoEmpresa);

        int databaseSizeBeforeDelete = perfilEquipoEmpresaRepository.findAll().size();

        // Delete the perfilEquipoEmpresa
        restPerfilEquipoEmpresaMockMvc.perform(delete("/api/perfil-equipo-empresas/{id}", perfilEquipoEmpresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfilEquipoEmpresa> perfilEquipoEmpresaList = perfilEquipoEmpresaRepository.findAll();
        assertThat(perfilEquipoEmpresaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PerfilEquipoEmpresa in Elasticsearch
        verify(mockPerfilEquipoEmpresaSearchRepository, times(1)).deleteById(perfilEquipoEmpresa.getId());
    }

    @Test
    @Transactional
    public void searchPerfilEquipoEmpresa() throws Exception {
        // Initialize the database
        perfilEquipoEmpresaService.save(perfilEquipoEmpresa);
        when(mockPerfilEquipoEmpresaSearchRepository.search(queryStringQuery("id:" + perfilEquipoEmpresa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(perfilEquipoEmpresa), PageRequest.of(0, 1), 1));
        // Search the perfilEquipoEmpresa
        restPerfilEquipoEmpresaMockMvc.perform(get("/api/_search/perfil-equipo-empresas?query=id:" + perfilEquipoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilEquipoEmpresa.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilEquipoEmpresa.class);
        PerfilEquipoEmpresa perfilEquipoEmpresa1 = new PerfilEquipoEmpresa();
        perfilEquipoEmpresa1.setId(1L);
        PerfilEquipoEmpresa perfilEquipoEmpresa2 = new PerfilEquipoEmpresa();
        perfilEquipoEmpresa2.setId(perfilEquipoEmpresa1.getId());
        assertThat(perfilEquipoEmpresa1).isEqualTo(perfilEquipoEmpresa2);
        perfilEquipoEmpresa2.setId(2L);
        assertThat(perfilEquipoEmpresa1).isNotEqualTo(perfilEquipoEmpresa2);
        perfilEquipoEmpresa1.setId(null);
        assertThat(perfilEquipoEmpresa1).isNotEqualTo(perfilEquipoEmpresa2);
    }
}
