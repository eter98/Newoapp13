package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.repository.GruposRepository;
import io.github.jhipster.newoapp13.repository.search.GruposSearchRepository;
import io.github.jhipster.newoapp13.service.GruposService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.GruposCriteria;
import io.github.jhipster.newoapp13.service.GruposQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.TipoGrupod;
import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
/**
 * Integration tests for the {@link GruposResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class GruposResourceIT {

    private static final String DEFAULT_NOMBRE_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_GRUPO = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITER = "AAAAAAAAAA";
    private static final String UPDATED_TWITER = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN = "BBBBBBBBBB";

    private static final TipoGrupod DEFAULT_TIPO_GRUPO = TipoGrupod.INTERNO;
    private static final TipoGrupod UPDATED_TIPO_GRUPO = TipoGrupod.EXTERNO;

    private static final TipoConsumod DEFAULT_TIPO_CONSUMO = TipoConsumod.GRATIS;
    private static final TipoConsumod UPDATED_TIPO_CONSUMO = TipoConsumod.PAGO;

    private static final Integer DEFAULT_VALOR_SUSCRIPCION = 1;
    private static final Integer UPDATED_VALOR_SUSCRIPCION = 2;
    private static final Integer SMALLER_VALOR_SUSCRIPCION = 1 - 1;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    private static final String DEFAULT_REGLAS_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_REGLAS_GRUPO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_AUDIO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AUDIO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AUDIO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AUDIO_CONTENT_TYPE = "image/png";

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

    private static final byte[] DEFAULT_BANNER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_CONTENT_TYPE = "image/png";

    @Autowired
    private GruposRepository gruposRepository;

    @Autowired
    private GruposService gruposService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.GruposSearchRepositoryMockConfiguration
     */
    @Autowired
    private GruposSearchRepository mockGruposSearchRepository;

    @Autowired
    private GruposQueryService gruposQueryService;

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

    private MockMvc restGruposMockMvc;

    private Grupos grupos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GruposResource gruposResource = new GruposResource(gruposService, gruposQueryService);
        this.restGruposMockMvc = MockMvcBuilders.standaloneSetup(gruposResource)
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
    public static Grupos createEntity(EntityManager em) {
        Grupos grupos = new Grupos()
            .nombreGrupo(DEFAULT_NOMBRE_GRUPO)
            .instagram(DEFAULT_INSTAGRAM)
            .facebook(DEFAULT_FACEBOOK)
            .twiter(DEFAULT_TWITER)
            .linkedIn(DEFAULT_LINKED_IN)
            .tipoGrupo(DEFAULT_TIPO_GRUPO)
            .tipoConsumo(DEFAULT_TIPO_CONSUMO)
            .valorSuscripcion(DEFAULT_VALOR_SUSCRIPCION)
            .impuesto(DEFAULT_IMPUESTO)
            .reglasGrupo(DEFAULT_REGLAS_GRUPO)
            .audio(DEFAULT_AUDIO)
            .audioContentType(DEFAULT_AUDIO_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .banner(DEFAULT_BANNER)
            .bannerContentType(DEFAULT_BANNER_CONTENT_TYPE);
        return grupos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grupos createUpdatedEntity(EntityManager em) {
        Grupos grupos = new Grupos()
            .nombreGrupo(UPDATED_NOMBRE_GRUPO)
            .instagram(UPDATED_INSTAGRAM)
            .facebook(UPDATED_FACEBOOK)
            .twiter(UPDATED_TWITER)
            .linkedIn(UPDATED_LINKED_IN)
            .tipoGrupo(UPDATED_TIPO_GRUPO)
            .tipoConsumo(UPDATED_TIPO_CONSUMO)
            .valorSuscripcion(UPDATED_VALOR_SUSCRIPCION)
            .impuesto(UPDATED_IMPUESTO)
            .reglasGrupo(UPDATED_REGLAS_GRUPO)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .banner(UPDATED_BANNER)
            .bannerContentType(UPDATED_BANNER_CONTENT_TYPE);
        return grupos;
    }

    @BeforeEach
    public void initTest() {
        grupos = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupos() throws Exception {
        int databaseSizeBeforeCreate = gruposRepository.findAll().size();

        // Create the Grupos
        restGruposMockMvc.perform(post("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupos)))
            .andExpect(status().isCreated());

        // Validate the Grupos in the database
        List<Grupos> gruposList = gruposRepository.findAll();
        assertThat(gruposList).hasSize(databaseSizeBeforeCreate + 1);
        Grupos testGrupos = gruposList.get(gruposList.size() - 1);
        assertThat(testGrupos.getNombreGrupo()).isEqualTo(DEFAULT_NOMBRE_GRUPO);
        assertThat(testGrupos.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testGrupos.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testGrupos.getTwiter()).isEqualTo(DEFAULT_TWITER);
        assertThat(testGrupos.getLinkedIn()).isEqualTo(DEFAULT_LINKED_IN);
        assertThat(testGrupos.getTipoGrupo()).isEqualTo(DEFAULT_TIPO_GRUPO);
        assertThat(testGrupos.getTipoConsumo()).isEqualTo(DEFAULT_TIPO_CONSUMO);
        assertThat(testGrupos.getValorSuscripcion()).isEqualTo(DEFAULT_VALOR_SUSCRIPCION);
        assertThat(testGrupos.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testGrupos.getReglasGrupo()).isEqualTo(DEFAULT_REGLAS_GRUPO);
        assertThat(testGrupos.getAudio()).isEqualTo(DEFAULT_AUDIO);
        assertThat(testGrupos.getAudioContentType()).isEqualTo(DEFAULT_AUDIO_CONTENT_TYPE);
        assertThat(testGrupos.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testGrupos.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testGrupos.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testGrupos.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testGrupos.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testGrupos.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testGrupos.getBanner()).isEqualTo(DEFAULT_BANNER);
        assertThat(testGrupos.getBannerContentType()).isEqualTo(DEFAULT_BANNER_CONTENT_TYPE);

        // Validate the Grupos in Elasticsearch
        verify(mockGruposSearchRepository, times(1)).save(testGrupos);
    }

    @Test
    @Transactional
    public void createGruposWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gruposRepository.findAll().size();

        // Create the Grupos with an existing ID
        grupos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGruposMockMvc.perform(post("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupos)))
            .andExpect(status().isBadRequest());

        // Validate the Grupos in the database
        List<Grupos> gruposList = gruposRepository.findAll();
        assertThat(gruposList).hasSize(databaseSizeBeforeCreate);

        // Validate the Grupos in Elasticsearch
        verify(mockGruposSearchRepository, times(0)).save(grupos);
    }


    @Test
    @Transactional
    public void checkNombreGrupoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gruposRepository.findAll().size();
        // set the field null
        grupos.setNombreGrupo(null);

        // Create the Grupos, which fails.

        restGruposMockMvc.perform(post("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupos)))
            .andExpect(status().isBadRequest());

        List<Grupos> gruposList = gruposRepository.findAll();
        assertThat(gruposList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupos() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList
        restGruposMockMvc.perform(get("/api/grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreGrupo").value(hasItem(DEFAULT_NOMBRE_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER.toString())))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN.toString())))
            .andExpect(jsonPath("$.[*].tipoGrupo").value(hasItem(DEFAULT_TIPO_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valorSuscripcion").value(hasItem(DEFAULT_VALOR_SUSCRIPCION)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].reglasGrupo").value(hasItem(DEFAULT_REGLAS_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))));
    }
    
    @Test
    @Transactional
    public void getGrupos() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get the grupos
        restGruposMockMvc.perform(get("/api/grupos/{id}", grupos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupos.getId().intValue()))
            .andExpect(jsonPath("$.nombreGrupo").value(DEFAULT_NOMBRE_GRUPO.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.twiter").value(DEFAULT_TWITER.toString()))
            .andExpect(jsonPath("$.linkedIn").value(DEFAULT_LINKED_IN.toString()))
            .andExpect(jsonPath("$.tipoGrupo").value(DEFAULT_TIPO_GRUPO.toString()))
            .andExpect(jsonPath("$.tipoConsumo").value(DEFAULT_TIPO_CONSUMO.toString()))
            .andExpect(jsonPath("$.valorSuscripcion").value(DEFAULT_VALOR_SUSCRIPCION))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()))
            .andExpect(jsonPath("$.reglasGrupo").value(DEFAULT_REGLAS_GRUPO.toString()))
            .andExpect(jsonPath("$.audioContentType").value(DEFAULT_AUDIO_CONTENT_TYPE))
            .andExpect(jsonPath("$.audio").value(Base64Utils.encodeToString(DEFAULT_AUDIO)))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.bannerContentType").value(DEFAULT_BANNER_CONTENT_TYPE))
            .andExpect(jsonPath("$.banner").value(Base64Utils.encodeToString(DEFAULT_BANNER)));
    }

    @Test
    @Transactional
    public void getAllGruposByNombreGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where nombreGrupo equals to DEFAULT_NOMBRE_GRUPO
        defaultGruposShouldBeFound("nombreGrupo.equals=" + DEFAULT_NOMBRE_GRUPO);

        // Get all the gruposList where nombreGrupo equals to UPDATED_NOMBRE_GRUPO
        defaultGruposShouldNotBeFound("nombreGrupo.equals=" + UPDATED_NOMBRE_GRUPO);
    }

    @Test
    @Transactional
    public void getAllGruposByNombreGrupoIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where nombreGrupo in DEFAULT_NOMBRE_GRUPO or UPDATED_NOMBRE_GRUPO
        defaultGruposShouldBeFound("nombreGrupo.in=" + DEFAULT_NOMBRE_GRUPO + "," + UPDATED_NOMBRE_GRUPO);

        // Get all the gruposList where nombreGrupo equals to UPDATED_NOMBRE_GRUPO
        defaultGruposShouldNotBeFound("nombreGrupo.in=" + UPDATED_NOMBRE_GRUPO);
    }

    @Test
    @Transactional
    public void getAllGruposByNombreGrupoIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where nombreGrupo is not null
        defaultGruposShouldBeFound("nombreGrupo.specified=true");

        // Get all the gruposList where nombreGrupo is null
        defaultGruposShouldNotBeFound("nombreGrupo.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByInstagramIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where instagram equals to DEFAULT_INSTAGRAM
        defaultGruposShouldBeFound("instagram.equals=" + DEFAULT_INSTAGRAM);

        // Get all the gruposList where instagram equals to UPDATED_INSTAGRAM
        defaultGruposShouldNotBeFound("instagram.equals=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllGruposByInstagramIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where instagram in DEFAULT_INSTAGRAM or UPDATED_INSTAGRAM
        defaultGruposShouldBeFound("instagram.in=" + DEFAULT_INSTAGRAM + "," + UPDATED_INSTAGRAM);

        // Get all the gruposList where instagram equals to UPDATED_INSTAGRAM
        defaultGruposShouldNotBeFound("instagram.in=" + UPDATED_INSTAGRAM);
    }

    @Test
    @Transactional
    public void getAllGruposByInstagramIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where instagram is not null
        defaultGruposShouldBeFound("instagram.specified=true");

        // Get all the gruposList where instagram is null
        defaultGruposShouldNotBeFound("instagram.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByFacebookIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where facebook equals to DEFAULT_FACEBOOK
        defaultGruposShouldBeFound("facebook.equals=" + DEFAULT_FACEBOOK);

        // Get all the gruposList where facebook equals to UPDATED_FACEBOOK
        defaultGruposShouldNotBeFound("facebook.equals=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllGruposByFacebookIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where facebook in DEFAULT_FACEBOOK or UPDATED_FACEBOOK
        defaultGruposShouldBeFound("facebook.in=" + DEFAULT_FACEBOOK + "," + UPDATED_FACEBOOK);

        // Get all the gruposList where facebook equals to UPDATED_FACEBOOK
        defaultGruposShouldNotBeFound("facebook.in=" + UPDATED_FACEBOOK);
    }

    @Test
    @Transactional
    public void getAllGruposByFacebookIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where facebook is not null
        defaultGruposShouldBeFound("facebook.specified=true");

        // Get all the gruposList where facebook is null
        defaultGruposShouldNotBeFound("facebook.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByTwiterIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where twiter equals to DEFAULT_TWITER
        defaultGruposShouldBeFound("twiter.equals=" + DEFAULT_TWITER);

        // Get all the gruposList where twiter equals to UPDATED_TWITER
        defaultGruposShouldNotBeFound("twiter.equals=" + UPDATED_TWITER);
    }

    @Test
    @Transactional
    public void getAllGruposByTwiterIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where twiter in DEFAULT_TWITER or UPDATED_TWITER
        defaultGruposShouldBeFound("twiter.in=" + DEFAULT_TWITER + "," + UPDATED_TWITER);

        // Get all the gruposList where twiter equals to UPDATED_TWITER
        defaultGruposShouldNotBeFound("twiter.in=" + UPDATED_TWITER);
    }

    @Test
    @Transactional
    public void getAllGruposByTwiterIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where twiter is not null
        defaultGruposShouldBeFound("twiter.specified=true");

        // Get all the gruposList where twiter is null
        defaultGruposShouldNotBeFound("twiter.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByLinkedInIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where linkedIn equals to DEFAULT_LINKED_IN
        defaultGruposShouldBeFound("linkedIn.equals=" + DEFAULT_LINKED_IN);

        // Get all the gruposList where linkedIn equals to UPDATED_LINKED_IN
        defaultGruposShouldNotBeFound("linkedIn.equals=" + UPDATED_LINKED_IN);
    }

    @Test
    @Transactional
    public void getAllGruposByLinkedInIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where linkedIn in DEFAULT_LINKED_IN or UPDATED_LINKED_IN
        defaultGruposShouldBeFound("linkedIn.in=" + DEFAULT_LINKED_IN + "," + UPDATED_LINKED_IN);

        // Get all the gruposList where linkedIn equals to UPDATED_LINKED_IN
        defaultGruposShouldNotBeFound("linkedIn.in=" + UPDATED_LINKED_IN);
    }

    @Test
    @Transactional
    public void getAllGruposByLinkedInIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where linkedIn is not null
        defaultGruposShouldBeFound("linkedIn.specified=true");

        // Get all the gruposList where linkedIn is null
        defaultGruposShouldNotBeFound("linkedIn.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByTipoGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where tipoGrupo equals to DEFAULT_TIPO_GRUPO
        defaultGruposShouldBeFound("tipoGrupo.equals=" + DEFAULT_TIPO_GRUPO);

        // Get all the gruposList where tipoGrupo equals to UPDATED_TIPO_GRUPO
        defaultGruposShouldNotBeFound("tipoGrupo.equals=" + UPDATED_TIPO_GRUPO);
    }

    @Test
    @Transactional
    public void getAllGruposByTipoGrupoIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where tipoGrupo in DEFAULT_TIPO_GRUPO or UPDATED_TIPO_GRUPO
        defaultGruposShouldBeFound("tipoGrupo.in=" + DEFAULT_TIPO_GRUPO + "," + UPDATED_TIPO_GRUPO);

        // Get all the gruposList where tipoGrupo equals to UPDATED_TIPO_GRUPO
        defaultGruposShouldNotBeFound("tipoGrupo.in=" + UPDATED_TIPO_GRUPO);
    }

    @Test
    @Transactional
    public void getAllGruposByTipoGrupoIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where tipoGrupo is not null
        defaultGruposShouldBeFound("tipoGrupo.specified=true");

        // Get all the gruposList where tipoGrupo is null
        defaultGruposShouldNotBeFound("tipoGrupo.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByTipoConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where tipoConsumo equals to DEFAULT_TIPO_CONSUMO
        defaultGruposShouldBeFound("tipoConsumo.equals=" + DEFAULT_TIPO_CONSUMO);

        // Get all the gruposList where tipoConsumo equals to UPDATED_TIPO_CONSUMO
        defaultGruposShouldNotBeFound("tipoConsumo.equals=" + UPDATED_TIPO_CONSUMO);
    }

    @Test
    @Transactional
    public void getAllGruposByTipoConsumoIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where tipoConsumo in DEFAULT_TIPO_CONSUMO or UPDATED_TIPO_CONSUMO
        defaultGruposShouldBeFound("tipoConsumo.in=" + DEFAULT_TIPO_CONSUMO + "," + UPDATED_TIPO_CONSUMO);

        // Get all the gruposList where tipoConsumo equals to UPDATED_TIPO_CONSUMO
        defaultGruposShouldNotBeFound("tipoConsumo.in=" + UPDATED_TIPO_CONSUMO);
    }

    @Test
    @Transactional
    public void getAllGruposByTipoConsumoIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where tipoConsumo is not null
        defaultGruposShouldBeFound("tipoConsumo.specified=true");

        // Get all the gruposList where tipoConsumo is null
        defaultGruposShouldNotBeFound("tipoConsumo.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion equals to DEFAULT_VALOR_SUSCRIPCION
        defaultGruposShouldBeFound("valorSuscripcion.equals=" + DEFAULT_VALOR_SUSCRIPCION);

        // Get all the gruposList where valorSuscripcion equals to UPDATED_VALOR_SUSCRIPCION
        defaultGruposShouldNotBeFound("valorSuscripcion.equals=" + UPDATED_VALOR_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion in DEFAULT_VALOR_SUSCRIPCION or UPDATED_VALOR_SUSCRIPCION
        defaultGruposShouldBeFound("valorSuscripcion.in=" + DEFAULT_VALOR_SUSCRIPCION + "," + UPDATED_VALOR_SUSCRIPCION);

        // Get all the gruposList where valorSuscripcion equals to UPDATED_VALOR_SUSCRIPCION
        defaultGruposShouldNotBeFound("valorSuscripcion.in=" + UPDATED_VALOR_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion is not null
        defaultGruposShouldBeFound("valorSuscripcion.specified=true");

        // Get all the gruposList where valorSuscripcion is null
        defaultGruposShouldNotBeFound("valorSuscripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion is greater than or equal to DEFAULT_VALOR_SUSCRIPCION
        defaultGruposShouldBeFound("valorSuscripcion.greaterThanOrEqual=" + DEFAULT_VALOR_SUSCRIPCION);

        // Get all the gruposList where valorSuscripcion is greater than or equal to UPDATED_VALOR_SUSCRIPCION
        defaultGruposShouldNotBeFound("valorSuscripcion.greaterThanOrEqual=" + UPDATED_VALOR_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion is less than or equal to DEFAULT_VALOR_SUSCRIPCION
        defaultGruposShouldBeFound("valorSuscripcion.lessThanOrEqual=" + DEFAULT_VALOR_SUSCRIPCION);

        // Get all the gruposList where valorSuscripcion is less than or equal to SMALLER_VALOR_SUSCRIPCION
        defaultGruposShouldNotBeFound("valorSuscripcion.lessThanOrEqual=" + SMALLER_VALOR_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsLessThanSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion is less than DEFAULT_VALOR_SUSCRIPCION
        defaultGruposShouldNotBeFound("valorSuscripcion.lessThan=" + DEFAULT_VALOR_SUSCRIPCION);

        // Get all the gruposList where valorSuscripcion is less than UPDATED_VALOR_SUSCRIPCION
        defaultGruposShouldBeFound("valorSuscripcion.lessThan=" + UPDATED_VALOR_SUSCRIPCION);
    }

    @Test
    @Transactional
    public void getAllGruposByValorSuscripcionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where valorSuscripcion is greater than DEFAULT_VALOR_SUSCRIPCION
        defaultGruposShouldNotBeFound("valorSuscripcion.greaterThan=" + DEFAULT_VALOR_SUSCRIPCION);

        // Get all the gruposList where valorSuscripcion is greater than SMALLER_VALOR_SUSCRIPCION
        defaultGruposShouldBeFound("valorSuscripcion.greaterThan=" + SMALLER_VALOR_SUSCRIPCION);
    }


    @Test
    @Transactional
    public void getAllGruposByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where impuesto equals to DEFAULT_IMPUESTO
        defaultGruposShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the gruposList where impuesto equals to UPDATED_IMPUESTO
        defaultGruposShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllGruposByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultGruposShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the gruposList where impuesto equals to UPDATED_IMPUESTO
        defaultGruposShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllGruposByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);

        // Get all the gruposList where impuesto is not null
        defaultGruposShouldBeFound("impuesto.specified=true");

        // Get all the gruposList where impuesto is null
        defaultGruposShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllGruposByCategoriaGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        gruposRepository.saveAndFlush(grupos);
        CategoriaContenidos categoriaGrupo = CategoriaContenidosResourceIT.createEntity(em);
        em.persist(categoriaGrupo);
        em.flush();
        grupos.setCategoriaGrupo(categoriaGrupo);
        gruposRepository.saveAndFlush(grupos);
        Long categoriaGrupoId = categoriaGrupo.getId();

        // Get all the gruposList where categoriaGrupo equals to categoriaGrupoId
        defaultGruposShouldBeFound("categoriaGrupoId.equals=" + categoriaGrupoId);

        // Get all the gruposList where categoriaGrupo equals to categoriaGrupoId + 1
        defaultGruposShouldNotBeFound("categoriaGrupoId.equals=" + (categoriaGrupoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGruposShouldBeFound(String filter) throws Exception {
        restGruposMockMvc.perform(get("/api/grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreGrupo").value(hasItem(DEFAULT_NOMBRE_GRUPO)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER)))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN)))
            .andExpect(jsonPath("$.[*].tipoGrupo").value(hasItem(DEFAULT_TIPO_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valorSuscripcion").value(hasItem(DEFAULT_VALOR_SUSCRIPCION)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].reglasGrupo").value(hasItem(DEFAULT_REGLAS_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))));

        // Check, that the count call also returns 1
        restGruposMockMvc.perform(get("/api/grupos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGruposShouldNotBeFound(String filter) throws Exception {
        restGruposMockMvc.perform(get("/api/grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGruposMockMvc.perform(get("/api/grupos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGrupos() throws Exception {
        // Get the grupos
        restGruposMockMvc.perform(get("/api/grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupos() throws Exception {
        // Initialize the database
        gruposService.save(grupos);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockGruposSearchRepository);

        int databaseSizeBeforeUpdate = gruposRepository.findAll().size();

        // Update the grupos
        Grupos updatedGrupos = gruposRepository.findById(grupos.getId()).get();
        // Disconnect from session so that the updates on updatedGrupos are not directly saved in db
        em.detach(updatedGrupos);
        updatedGrupos
            .nombreGrupo(UPDATED_NOMBRE_GRUPO)
            .instagram(UPDATED_INSTAGRAM)
            .facebook(UPDATED_FACEBOOK)
            .twiter(UPDATED_TWITER)
            .linkedIn(UPDATED_LINKED_IN)
            .tipoGrupo(UPDATED_TIPO_GRUPO)
            .tipoConsumo(UPDATED_TIPO_CONSUMO)
            .valorSuscripcion(UPDATED_VALOR_SUSCRIPCION)
            .impuesto(UPDATED_IMPUESTO)
            .reglasGrupo(UPDATED_REGLAS_GRUPO)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .banner(UPDATED_BANNER)
            .bannerContentType(UPDATED_BANNER_CONTENT_TYPE);

        restGruposMockMvc.perform(put("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupos)))
            .andExpect(status().isOk());

        // Validate the Grupos in the database
        List<Grupos> gruposList = gruposRepository.findAll();
        assertThat(gruposList).hasSize(databaseSizeBeforeUpdate);
        Grupos testGrupos = gruposList.get(gruposList.size() - 1);
        assertThat(testGrupos.getNombreGrupo()).isEqualTo(UPDATED_NOMBRE_GRUPO);
        assertThat(testGrupos.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testGrupos.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testGrupos.getTwiter()).isEqualTo(UPDATED_TWITER);
        assertThat(testGrupos.getLinkedIn()).isEqualTo(UPDATED_LINKED_IN);
        assertThat(testGrupos.getTipoGrupo()).isEqualTo(UPDATED_TIPO_GRUPO);
        assertThat(testGrupos.getTipoConsumo()).isEqualTo(UPDATED_TIPO_CONSUMO);
        assertThat(testGrupos.getValorSuscripcion()).isEqualTo(UPDATED_VALOR_SUSCRIPCION);
        assertThat(testGrupos.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testGrupos.getReglasGrupo()).isEqualTo(UPDATED_REGLAS_GRUPO);
        assertThat(testGrupos.getAudio()).isEqualTo(UPDATED_AUDIO);
        assertThat(testGrupos.getAudioContentType()).isEqualTo(UPDATED_AUDIO_CONTENT_TYPE);
        assertThat(testGrupos.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testGrupos.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testGrupos.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testGrupos.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testGrupos.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testGrupos.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testGrupos.getBanner()).isEqualTo(UPDATED_BANNER);
        assertThat(testGrupos.getBannerContentType()).isEqualTo(UPDATED_BANNER_CONTENT_TYPE);

        // Validate the Grupos in Elasticsearch
        verify(mockGruposSearchRepository, times(1)).save(testGrupos);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupos() throws Exception {
        int databaseSizeBeforeUpdate = gruposRepository.findAll().size();

        // Create the Grupos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGruposMockMvc.perform(put("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupos)))
            .andExpect(status().isBadRequest());

        // Validate the Grupos in the database
        List<Grupos> gruposList = gruposRepository.findAll();
        assertThat(gruposList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Grupos in Elasticsearch
        verify(mockGruposSearchRepository, times(0)).save(grupos);
    }

    @Test
    @Transactional
    public void deleteGrupos() throws Exception {
        // Initialize the database
        gruposService.save(grupos);

        int databaseSizeBeforeDelete = gruposRepository.findAll().size();

        // Delete the grupos
        restGruposMockMvc.perform(delete("/api/grupos/{id}", grupos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grupos> gruposList = gruposRepository.findAll();
        assertThat(gruposList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Grupos in Elasticsearch
        verify(mockGruposSearchRepository, times(1)).deleteById(grupos.getId());
    }

    @Test
    @Transactional
    public void searchGrupos() throws Exception {
        // Initialize the database
        gruposService.save(grupos);
        when(mockGruposSearchRepository.search(queryStringQuery("id:" + grupos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(grupos), PageRequest.of(0, 1), 1));
        // Search the grupos
        restGruposMockMvc.perform(get("/api/_search/grupos?query=id:" + grupos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreGrupo").value(hasItem(DEFAULT_NOMBRE_GRUPO)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].twiter").value(hasItem(DEFAULT_TWITER)))
            .andExpect(jsonPath("$.[*].linkedIn").value(hasItem(DEFAULT_LINKED_IN)))
            .andExpect(jsonPath("$.[*].tipoGrupo").value(hasItem(DEFAULT_TIPO_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valorSuscripcion").value(hasItem(DEFAULT_VALOR_SUSCRIPCION)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].reglasGrupo").value(hasItem(DEFAULT_REGLAS_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grupos.class);
        Grupos grupos1 = new Grupos();
        grupos1.setId(1L);
        Grupos grupos2 = new Grupos();
        grupos2.setId(grupos1.getId());
        assertThat(grupos1).isEqualTo(grupos2);
        grupos2.setId(2L);
        assertThat(grupos1).isNotEqualTo(grupos2);
        grupos1.setId(null);
        assertThat(grupos1).isNotEqualTo(grupos2);
    }
}
