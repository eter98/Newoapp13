package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Evento;
import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.EventoRepository;
import io.github.jhipster.newoapp13.repository.search.EventoSearchRepository;
import io.github.jhipster.newoapp13.service.EventoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.EventoCriteria;
import io.github.jhipster.newoapp13.service.EventoQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;
/**
 * Integration tests for the {@link EventoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class EventoResourceIT {

    private static final String DEFAULT_NOMBRE_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EVENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

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

    private static final TipoConsumod DEFAULT_TIPO_CONSUMO = TipoConsumod.GRATIS;
    private static final TipoConsumod UPDATED_TIPO_CONSUMO = TipoConsumod.PAGO;

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;
    private static final Float SMALLER_VALOR = 1F - 1F;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    private static final Categoriad DEFAULT_TIPO_EVENTO = Categoriad.GENERAL;
    private static final Categoriad UPDATED_TIPO_EVENTO = Categoriad.DE_GRUPO;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.EventoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EventoSearchRepository mockEventoSearchRepository;

    @Autowired
    private EventoQueryService eventoQueryService;

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

    private MockMvc restEventoMockMvc;

    private Evento evento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventoResource eventoResource = new EventoResource(eventoService, eventoQueryService);
        this.restEventoMockMvc = MockMvcBuilders.standaloneSetup(eventoResource)
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
    public static Evento createEntity(EntityManager em) {
        Evento evento = new Evento()
            .nombreEvento(DEFAULT_NOMBRE_EVENTO)
            .descripcion(DEFAULT_DESCRIPCION)
            .contenido(DEFAULT_CONTENIDO)
            .audio(DEFAULT_AUDIO)
            .audioContentType(DEFAULT_AUDIO_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .banner(DEFAULT_BANNER)
            .bannerContentType(DEFAULT_BANNER_CONTENT_TYPE)
            .tipoConsumo(DEFAULT_TIPO_CONSUMO)
            .valor(DEFAULT_VALOR)
            .impuesto(DEFAULT_IMPUESTO)
            .tipoEvento(DEFAULT_TIPO_EVENTO);
        return evento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evento createUpdatedEntity(EntityManager em) {
        Evento evento = new Evento()
            .nombreEvento(UPDATED_NOMBRE_EVENTO)
            .descripcion(UPDATED_DESCRIPCION)
            .contenido(UPDATED_CONTENIDO)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .banner(UPDATED_BANNER)
            .bannerContentType(UPDATED_BANNER_CONTENT_TYPE)
            .tipoConsumo(UPDATED_TIPO_CONSUMO)
            .valor(UPDATED_VALOR)
            .impuesto(UPDATED_IMPUESTO)
            .tipoEvento(UPDATED_TIPO_EVENTO);
        return evento;
    }

    @BeforeEach
    public void initTest() {
        evento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvento() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento
        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isCreated());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate + 1);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getNombreEvento()).isEqualTo(DEFAULT_NOMBRE_EVENTO);
        assertThat(testEvento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEvento.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testEvento.getAudio()).isEqualTo(DEFAULT_AUDIO);
        assertThat(testEvento.getAudioContentType()).isEqualTo(DEFAULT_AUDIO_CONTENT_TYPE);
        assertThat(testEvento.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testEvento.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testEvento.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testEvento.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testEvento.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testEvento.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testEvento.getBanner()).isEqualTo(DEFAULT_BANNER);
        assertThat(testEvento.getBannerContentType()).isEqualTo(DEFAULT_BANNER_CONTENT_TYPE);
        assertThat(testEvento.getTipoConsumo()).isEqualTo(DEFAULT_TIPO_CONSUMO);
        assertThat(testEvento.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testEvento.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testEvento.getTipoEvento()).isEqualTo(DEFAULT_TIPO_EVENTO);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(1)).save(testEvento);
    }

    @Test
    @Transactional
    public void createEventoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento with an existing ID
        evento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(0)).save(evento);
    }


    @Test
    @Transactional
    public void checkNombreEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setNombreEvento(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setDescripcion(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventos() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList
        restEventoMockMvc.perform(get("/api/eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEvento").value(hasItem(DEFAULT_NOMBRE_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEvento").value(hasItem(DEFAULT_TIPO_EVENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evento.getId().intValue()))
            .andExpect(jsonPath("$.nombreEvento").value(DEFAULT_NOMBRE_EVENTO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.audioContentType").value(DEFAULT_AUDIO_CONTENT_TYPE))
            .andExpect(jsonPath("$.audio").value(Base64Utils.encodeToString(DEFAULT_AUDIO)))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.bannerContentType").value(DEFAULT_BANNER_CONTENT_TYPE))
            .andExpect(jsonPath("$.banner").value(Base64Utils.encodeToString(DEFAULT_BANNER)))
            .andExpect(jsonPath("$.tipoConsumo").value(DEFAULT_TIPO_CONSUMO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()))
            .andExpect(jsonPath("$.tipoEvento").value(DEFAULT_TIPO_EVENTO.toString()));
    }

    @Test
    @Transactional
    public void getAllEventosByNombreEventoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where nombreEvento equals to DEFAULT_NOMBRE_EVENTO
        defaultEventoShouldBeFound("nombreEvento.equals=" + DEFAULT_NOMBRE_EVENTO);

        // Get all the eventoList where nombreEvento equals to UPDATED_NOMBRE_EVENTO
        defaultEventoShouldNotBeFound("nombreEvento.equals=" + UPDATED_NOMBRE_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventosByNombreEventoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where nombreEvento in DEFAULT_NOMBRE_EVENTO or UPDATED_NOMBRE_EVENTO
        defaultEventoShouldBeFound("nombreEvento.in=" + DEFAULT_NOMBRE_EVENTO + "," + UPDATED_NOMBRE_EVENTO);

        // Get all the eventoList where nombreEvento equals to UPDATED_NOMBRE_EVENTO
        defaultEventoShouldNotBeFound("nombreEvento.in=" + UPDATED_NOMBRE_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventosByNombreEventoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where nombreEvento is not null
        defaultEventoShouldBeFound("nombreEvento.specified=true");

        // Get all the eventoList where nombreEvento is null
        defaultEventoShouldNotBeFound("nombreEvento.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultEventoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the eventoList where descripcion equals to UPDATED_DESCRIPCION
        defaultEventoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllEventosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultEventoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the eventoList where descripcion equals to UPDATED_DESCRIPCION
        defaultEventoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllEventosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where descripcion is not null
        defaultEventoShouldBeFound("descripcion.specified=true");

        // Get all the eventoList where descripcion is null
        defaultEventoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventosByTipoConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where tipoConsumo equals to DEFAULT_TIPO_CONSUMO
        defaultEventoShouldBeFound("tipoConsumo.equals=" + DEFAULT_TIPO_CONSUMO);

        // Get all the eventoList where tipoConsumo equals to UPDATED_TIPO_CONSUMO
        defaultEventoShouldNotBeFound("tipoConsumo.equals=" + UPDATED_TIPO_CONSUMO);
    }

    @Test
    @Transactional
    public void getAllEventosByTipoConsumoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where tipoConsumo in DEFAULT_TIPO_CONSUMO or UPDATED_TIPO_CONSUMO
        defaultEventoShouldBeFound("tipoConsumo.in=" + DEFAULT_TIPO_CONSUMO + "," + UPDATED_TIPO_CONSUMO);

        // Get all the eventoList where tipoConsumo equals to UPDATED_TIPO_CONSUMO
        defaultEventoShouldNotBeFound("tipoConsumo.in=" + UPDATED_TIPO_CONSUMO);
    }

    @Test
    @Transactional
    public void getAllEventosByTipoConsumoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where tipoConsumo is not null
        defaultEventoShouldBeFound("tipoConsumo.specified=true");

        // Get all the eventoList where tipoConsumo is null
        defaultEventoShouldNotBeFound("tipoConsumo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor equals to DEFAULT_VALOR
        defaultEventoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the eventoList where valor equals to UPDATED_VALOR
        defaultEventoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultEventoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the eventoList where valor equals to UPDATED_VALOR
        defaultEventoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor is not null
        defaultEventoShouldBeFound("valor.specified=true");

        // Get all the eventoList where valor is null
        defaultEventoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor is greater than or equal to DEFAULT_VALOR
        defaultEventoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the eventoList where valor is greater than or equal to UPDATED_VALOR
        defaultEventoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor is less than or equal to DEFAULT_VALOR
        defaultEventoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the eventoList where valor is less than or equal to SMALLER_VALOR
        defaultEventoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor is less than DEFAULT_VALOR
        defaultEventoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the eventoList where valor is less than UPDATED_VALOR
        defaultEventoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEventosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where valor is greater than DEFAULT_VALOR
        defaultEventoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the eventoList where valor is greater than SMALLER_VALOR
        defaultEventoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllEventosByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where impuesto equals to DEFAULT_IMPUESTO
        defaultEventoShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the eventoList where impuesto equals to UPDATED_IMPUESTO
        defaultEventoShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllEventosByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultEventoShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the eventoList where impuesto equals to UPDATED_IMPUESTO
        defaultEventoShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllEventosByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where impuesto is not null
        defaultEventoShouldBeFound("impuesto.specified=true");

        // Get all the eventoList where impuesto is null
        defaultEventoShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventosByTipoEventoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where tipoEvento equals to DEFAULT_TIPO_EVENTO
        defaultEventoShouldBeFound("tipoEvento.equals=" + DEFAULT_TIPO_EVENTO);

        // Get all the eventoList where tipoEvento equals to UPDATED_TIPO_EVENTO
        defaultEventoShouldNotBeFound("tipoEvento.equals=" + UPDATED_TIPO_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventosByTipoEventoIsInShouldWork() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where tipoEvento in DEFAULT_TIPO_EVENTO or UPDATED_TIPO_EVENTO
        defaultEventoShouldBeFound("tipoEvento.in=" + DEFAULT_TIPO_EVENTO + "," + UPDATED_TIPO_EVENTO);

        // Get all the eventoList where tipoEvento equals to UPDATED_TIPO_EVENTO
        defaultEventoShouldNotBeFound("tipoEvento.in=" + UPDATED_TIPO_EVENTO);
    }

    @Test
    @Transactional
    public void getAllEventosByTipoEventoIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList where tipoEvento is not null
        defaultEventoShouldBeFound("tipoEvento.specified=true");

        // Get all the eventoList where tipoEvento is null
        defaultEventoShouldNotBeFound("tipoEvento.specified=false");
    }

    @Test
    @Transactional
    public void getAllEventosByCategoriaEventoIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);
        CategoriaContenidos categoriaEvento = CategoriaContenidosResourceIT.createEntity(em);
        em.persist(categoriaEvento);
        em.flush();
        evento.setCategoriaEvento(categoriaEvento);
        eventoRepository.saveAndFlush(evento);
        Long categoriaEventoId = categoriaEvento.getId();

        // Get all the eventoList where categoriaEvento equals to categoriaEventoId
        defaultEventoShouldBeFound("categoriaEventoId.equals=" + categoriaEventoId);

        // Get all the eventoList where categoriaEvento equals to categoriaEventoId + 1
        defaultEventoShouldNotBeFound("categoriaEventoId.equals=" + (categoriaEventoId + 1));
    }


    @Test
    @Transactional
    public void getAllEventosByGruposIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);
        Grupos grupos = GruposResourceIT.createEntity(em);
        em.persist(grupos);
        em.flush();
        evento.setGrupos(grupos);
        eventoRepository.saveAndFlush(evento);
        Long gruposId = grupos.getId();

        // Get all the eventoList where grupos equals to gruposId
        defaultEventoShouldBeFound("gruposId.equals=" + gruposId);

        // Get all the eventoList where grupos equals to gruposId + 1
        defaultEventoShouldNotBeFound("gruposId.equals=" + (gruposId + 1));
    }


    @Test
    @Transactional
    public void getAllEventosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        evento.setMiembro(miembro);
        eventoRepository.saveAndFlush(evento);
        Long miembroId = miembro.getId();

        // Get all the eventoList where miembro equals to miembroId
        defaultEventoShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the eventoList where miembro equals to miembroId + 1
        defaultEventoShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventoShouldBeFound(String filter) throws Exception {
        restEventoMockMvc.perform(get("/api/eventos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEvento").value(hasItem(DEFAULT_NOMBRE_EVENTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEvento").value(hasItem(DEFAULT_TIPO_EVENTO.toString())));

        // Check, that the count call also returns 1
        restEventoMockMvc.perform(get("/api/eventos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventoShouldNotBeFound(String filter) throws Exception {
        restEventoMockMvc.perform(get("/api/eventos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventoMockMvc.perform(get("/api/eventos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEvento() throws Exception {
        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvento() throws Exception {
        // Initialize the database
        eventoService.save(evento);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEventoSearchRepository);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento
        Evento updatedEvento = eventoRepository.findById(evento.getId()).get();
        // Disconnect from session so that the updates on updatedEvento are not directly saved in db
        em.detach(updatedEvento);
        updatedEvento
            .nombreEvento(UPDATED_NOMBRE_EVENTO)
            .descripcion(UPDATED_DESCRIPCION)
            .contenido(UPDATED_CONTENIDO)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .banner(UPDATED_BANNER)
            .bannerContentType(UPDATED_BANNER_CONTENT_TYPE)
            .tipoConsumo(UPDATED_TIPO_CONSUMO)
            .valor(UPDATED_VALOR)
            .impuesto(UPDATED_IMPUESTO)
            .tipoEvento(UPDATED_TIPO_EVENTO);

        restEventoMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvento)))
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getNombreEvento()).isEqualTo(UPDATED_NOMBRE_EVENTO);
        assertThat(testEvento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEvento.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testEvento.getAudio()).isEqualTo(UPDATED_AUDIO);
        assertThat(testEvento.getAudioContentType()).isEqualTo(UPDATED_AUDIO_CONTENT_TYPE);
        assertThat(testEvento.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testEvento.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testEvento.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testEvento.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testEvento.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testEvento.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testEvento.getBanner()).isEqualTo(UPDATED_BANNER);
        assertThat(testEvento.getBannerContentType()).isEqualTo(UPDATED_BANNER_CONTENT_TYPE);
        assertThat(testEvento.getTipoConsumo()).isEqualTo(UPDATED_TIPO_CONSUMO);
        assertThat(testEvento.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testEvento.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testEvento.getTipoEvento()).isEqualTo(UPDATED_TIPO_EVENTO);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(1)).save(testEvento);
    }

    @Test
    @Transactional
    public void updateNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Create the Evento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(0)).save(evento);
    }

    @Test
    @Transactional
    public void deleteEvento() throws Exception {
        // Initialize the database
        eventoService.save(evento);

        int databaseSizeBeforeDelete = eventoRepository.findAll().size();

        // Delete the evento
        restEventoMockMvc.perform(delete("/api/eventos/{id}", evento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(1)).deleteById(evento.getId());
    }

    @Test
    @Transactional
    public void searchEvento() throws Exception {
        // Initialize the database
        eventoService.save(evento);
        when(mockEventoSearchRepository.search(queryStringQuery("id:" + evento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(evento), PageRequest.of(0, 1), 1));
        // Search the evento
        restEventoMockMvc.perform(get("/api/_search/eventos?query=id:" + evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEvento").value(hasItem(DEFAULT_NOMBRE_EVENTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEvento").value(hasItem(DEFAULT_TIPO_EVENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evento.class);
        Evento evento1 = new Evento();
        evento1.setId(1L);
        Evento evento2 = new Evento();
        evento2.setId(evento1.getId());
        assertThat(evento1).isEqualTo(evento2);
        evento2.setId(2L);
        assertThat(evento1).isNotEqualTo(evento2);
        evento1.setId(null);
        assertThat(evento1).isNotEqualTo(evento2);
    }
}
