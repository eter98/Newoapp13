package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.RecursosFisicos;
import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.domain.TipoRecurso;
import io.github.jhipster.newoapp13.repository.RecursosFisicosRepository;
import io.github.jhipster.newoapp13.repository.search.RecursosFisicosSearchRepository;
import io.github.jhipster.newoapp13.service.RecursosFisicosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.RecursosFisicosCriteria;
import io.github.jhipster.newoapp13.service.RecursosFisicosQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.TipoRecursod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
/**
 * Integration tests for the {@link RecursosFisicosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class RecursosFisicosResourceIT {

    private static final String DEFAULT_RECURSO = "AAAAAAAAAA";
    private static final String UPDATED_RECURSO = "BBBBBBBBBB";

    private static final TipoRecursod DEFAULT_TIPO = TipoRecursod.Tiempo;
    private static final TipoRecursod UPDATED_TIPO = TipoRecursod.Cantidad;

    private static final String DEFAULT_UNIDAD_MEDIDA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDAD_MEDIDA = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR_UNITARIO = 1;
    private static final Integer UPDATED_VALOR_UNITARIO = 2;
    private static final Integer SMALLER_VALOR_UNITARIO = 1 - 1;

    private static final Integer DEFAULT_VALOR_1_H = 1;
    private static final Integer UPDATED_VALOR_1_H = 2;
    private static final Integer SMALLER_VALOR_1_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_2_H = 1;
    private static final Integer UPDATED_VALOR_2_H = 2;
    private static final Integer SMALLER_VALOR_2_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_3_H = 1;
    private static final Integer UPDATED_VALOR_3_H = 2;
    private static final Integer SMALLER_VALOR_3_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_4_H = 1;
    private static final Integer UPDATED_VALOR_4_H = 2;
    private static final Integer SMALLER_VALOR_4_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_5_H = 1;
    private static final Integer UPDATED_VALOR_5_H = 2;
    private static final Integer SMALLER_VALOR_5_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_6_H = 1;
    private static final Integer UPDATED_VALOR_6_H = 2;
    private static final Integer SMALLER_VALOR_6_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_7_H = 1;
    private static final Integer UPDATED_VALOR_7_H = 2;
    private static final Integer SMALLER_VALOR_7_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_8_H = 1;
    private static final Integer UPDATED_VALOR_8_H = 2;
    private static final Integer SMALLER_VALOR_8_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_9_H = 1;
    private static final Integer UPDATED_VALOR_9_H = 2;
    private static final Integer SMALLER_VALOR_9_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_10_H = 1;
    private static final Integer UPDATED_VALOR_10_H = 2;
    private static final Integer SMALLER_VALOR_10_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_11_H = 1;
    private static final Integer UPDATED_VALOR_11_H = 2;
    private static final Integer SMALLER_VALOR_11_H = 1 - 1;

    private static final Integer DEFAULT_VALOR_12_H = 1;
    private static final Integer UPDATED_VALOR_12_H = 2;
    private static final Integer SMALLER_VALOR_12_H = 1 - 1;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

    @Autowired
    private RecursosFisicosRepository recursosFisicosRepository;

    @Autowired
    private RecursosFisicosService recursosFisicosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.RecursosFisicosSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecursosFisicosSearchRepository mockRecursosFisicosSearchRepository;

    @Autowired
    private RecursosFisicosQueryService recursosFisicosQueryService;

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

    private MockMvc restRecursosFisicosMockMvc;

    private RecursosFisicos recursosFisicos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecursosFisicosResource recursosFisicosResource = new RecursosFisicosResource(recursosFisicosService, recursosFisicosQueryService);
        this.restRecursosFisicosMockMvc = MockMvcBuilders.standaloneSetup(recursosFisicosResource)
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
    public static RecursosFisicos createEntity(EntityManager em) {
        RecursosFisicos recursosFisicos = new RecursosFisicos()
            .recurso(DEFAULT_RECURSO)
            .tipo(DEFAULT_TIPO)
            .unidadMedida(DEFAULT_UNIDAD_MEDIDA)
            .valorUnitario(DEFAULT_VALOR_UNITARIO)
            .valor1H(DEFAULT_VALOR_1_H)
            .valor2H(DEFAULT_VALOR_2_H)
            .valor3H(DEFAULT_VALOR_3_H)
            .valor4H(DEFAULT_VALOR_4_H)
            .valor5H(DEFAULT_VALOR_5_H)
            .valor6H(DEFAULT_VALOR_6_H)
            .valor7H(DEFAULT_VALOR_7_H)
            .valor8H(DEFAULT_VALOR_8_H)
            .valor9H(DEFAULT_VALOR_9_H)
            .valor10H(DEFAULT_VALOR_10_H)
            .valor11H(DEFAULT_VALOR_11_H)
            .valor12H(DEFAULT_VALOR_12_H)
            .impuesto(DEFAULT_IMPUESTO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE);
        return recursosFisicos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursosFisicos createUpdatedEntity(EntityManager em) {
        RecursosFisicos recursosFisicos = new RecursosFisicos()
            .recurso(UPDATED_RECURSO)
            .tipo(UPDATED_TIPO)
            .unidadMedida(UPDATED_UNIDAD_MEDIDA)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valor1H(UPDATED_VALOR_1_H)
            .valor2H(UPDATED_VALOR_2_H)
            .valor3H(UPDATED_VALOR_3_H)
            .valor4H(UPDATED_VALOR_4_H)
            .valor5H(UPDATED_VALOR_5_H)
            .valor6H(UPDATED_VALOR_6_H)
            .valor7H(UPDATED_VALOR_7_H)
            .valor8H(UPDATED_VALOR_8_H)
            .valor9H(UPDATED_VALOR_9_H)
            .valor10H(UPDATED_VALOR_10_H)
            .valor11H(UPDATED_VALOR_11_H)
            .valor12H(UPDATED_VALOR_12_H)
            .impuesto(UPDATED_IMPUESTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE);
        return recursosFisicos;
    }

    @BeforeEach
    public void initTest() {
        recursosFisicos = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecursosFisicos() throws Exception {
        int databaseSizeBeforeCreate = recursosFisicosRepository.findAll().size();

        // Create the RecursosFisicos
        restRecursosFisicosMockMvc.perform(post("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isCreated());

        // Validate the RecursosFisicos in the database
        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeCreate + 1);
        RecursosFisicos testRecursosFisicos = recursosFisicosList.get(recursosFisicosList.size() - 1);
        assertThat(testRecursosFisicos.getRecurso()).isEqualTo(DEFAULT_RECURSO);
        assertThat(testRecursosFisicos.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testRecursosFisicos.getUnidadMedida()).isEqualTo(DEFAULT_UNIDAD_MEDIDA);
        assertThat(testRecursosFisicos.getValorUnitario()).isEqualTo(DEFAULT_VALOR_UNITARIO);
        assertThat(testRecursosFisicos.getValor1H()).isEqualTo(DEFAULT_VALOR_1_H);
        assertThat(testRecursosFisicos.getValor2H()).isEqualTo(DEFAULT_VALOR_2_H);
        assertThat(testRecursosFisicos.getValor3H()).isEqualTo(DEFAULT_VALOR_3_H);
        assertThat(testRecursosFisicos.getValor4H()).isEqualTo(DEFAULT_VALOR_4_H);
        assertThat(testRecursosFisicos.getValor5H()).isEqualTo(DEFAULT_VALOR_5_H);
        assertThat(testRecursosFisicos.getValor6H()).isEqualTo(DEFAULT_VALOR_6_H);
        assertThat(testRecursosFisicos.getValor7H()).isEqualTo(DEFAULT_VALOR_7_H);
        assertThat(testRecursosFisicos.getValor8H()).isEqualTo(DEFAULT_VALOR_8_H);
        assertThat(testRecursosFisicos.getValor9H()).isEqualTo(DEFAULT_VALOR_9_H);
        assertThat(testRecursosFisicos.getValor10H()).isEqualTo(DEFAULT_VALOR_10_H);
        assertThat(testRecursosFisicos.getValor11H()).isEqualTo(DEFAULT_VALOR_11_H);
        assertThat(testRecursosFisicos.getValor12H()).isEqualTo(DEFAULT_VALOR_12_H);
        assertThat(testRecursosFisicos.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testRecursosFisicos.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testRecursosFisicos.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testRecursosFisicos.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testRecursosFisicos.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);

        // Validate the RecursosFisicos in Elasticsearch
        verify(mockRecursosFisicosSearchRepository, times(1)).save(testRecursosFisicos);
    }

    @Test
    @Transactional
    public void createRecursosFisicosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recursosFisicosRepository.findAll().size();

        // Create the RecursosFisicos with an existing ID
        recursosFisicos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursosFisicosMockMvc.perform(post("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isBadRequest());

        // Validate the RecursosFisicos in the database
        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeCreate);

        // Validate the RecursosFisicos in Elasticsearch
        verify(mockRecursosFisicosSearchRepository, times(0)).save(recursosFisicos);
    }


    @Test
    @Transactional
    public void checkRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursosFisicosRepository.findAll().size();
        // set the field null
        recursosFisicos.setRecurso(null);

        // Create the RecursosFisicos, which fails.

        restRecursosFisicosMockMvc.perform(post("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isBadRequest());

        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursosFisicosRepository.findAll().size();
        // set the field null
        recursosFisicos.setTipo(null);

        // Create the RecursosFisicos, which fails.

        restRecursosFisicosMockMvc.perform(post("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isBadRequest());

        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadMedidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursosFisicosRepository.findAll().size();
        // set the field null
        recursosFisicos.setUnidadMedida(null);

        // Create the RecursosFisicos, which fails.

        restRecursosFisicosMockMvc.perform(post("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isBadRequest());

        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursosFisicosRepository.findAll().size();
        // set the field null
        recursosFisicos.setValorUnitario(null);

        // Create the RecursosFisicos, which fails.

        restRecursosFisicosMockMvc.perform(post("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isBadRequest());

        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicos() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursosFisicos.getId().intValue())))
            .andExpect(jsonPath("$.[*].recurso").value(hasItem(DEFAULT_RECURSO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].unidadMedida").value(hasItem(DEFAULT_UNIDAD_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO)))
            .andExpect(jsonPath("$.[*].valor1H").value(hasItem(DEFAULT_VALOR_1_H)))
            .andExpect(jsonPath("$.[*].valor2H").value(hasItem(DEFAULT_VALOR_2_H)))
            .andExpect(jsonPath("$.[*].valor3H").value(hasItem(DEFAULT_VALOR_3_H)))
            .andExpect(jsonPath("$.[*].valor4H").value(hasItem(DEFAULT_VALOR_4_H)))
            .andExpect(jsonPath("$.[*].valor5H").value(hasItem(DEFAULT_VALOR_5_H)))
            .andExpect(jsonPath("$.[*].valor6H").value(hasItem(DEFAULT_VALOR_6_H)))
            .andExpect(jsonPath("$.[*].valor7H").value(hasItem(DEFAULT_VALOR_7_H)))
            .andExpect(jsonPath("$.[*].valor8H").value(hasItem(DEFAULT_VALOR_8_H)))
            .andExpect(jsonPath("$.[*].valor9H").value(hasItem(DEFAULT_VALOR_9_H)))
            .andExpect(jsonPath("$.[*].valor10H").value(hasItem(DEFAULT_VALOR_10_H)))
            .andExpect(jsonPath("$.[*].valor11H").value(hasItem(DEFAULT_VALOR_11_H)))
            .andExpect(jsonPath("$.[*].valor12H").value(hasItem(DEFAULT_VALOR_12_H)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))));
    }
    
    @Test
    @Transactional
    public void getRecursosFisicos() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get the recursosFisicos
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos/{id}", recursosFisicos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recursosFisicos.getId().intValue()))
            .andExpect(jsonPath("$.recurso").value(DEFAULT_RECURSO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.unidadMedida").value(DEFAULT_UNIDAD_MEDIDA.toString()))
            .andExpect(jsonPath("$.valorUnitario").value(DEFAULT_VALOR_UNITARIO))
            .andExpect(jsonPath("$.valor1H").value(DEFAULT_VALOR_1_H))
            .andExpect(jsonPath("$.valor2H").value(DEFAULT_VALOR_2_H))
            .andExpect(jsonPath("$.valor3H").value(DEFAULT_VALOR_3_H))
            .andExpect(jsonPath("$.valor4H").value(DEFAULT_VALOR_4_H))
            .andExpect(jsonPath("$.valor5H").value(DEFAULT_VALOR_5_H))
            .andExpect(jsonPath("$.valor6H").value(DEFAULT_VALOR_6_H))
            .andExpect(jsonPath("$.valor7H").value(DEFAULT_VALOR_7_H))
            .andExpect(jsonPath("$.valor8H").value(DEFAULT_VALOR_8_H))
            .andExpect(jsonPath("$.valor9H").value(DEFAULT_VALOR_9_H))
            .andExpect(jsonPath("$.valor10H").value(DEFAULT_VALOR_10_H))
            .andExpect(jsonPath("$.valor11H").value(DEFAULT_VALOR_11_H))
            .andExpect(jsonPath("$.valor12H").value(DEFAULT_VALOR_12_H))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)));
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where recurso equals to DEFAULT_RECURSO
        defaultRecursosFisicosShouldBeFound("recurso.equals=" + DEFAULT_RECURSO);

        // Get all the recursosFisicosList where recurso equals to UPDATED_RECURSO
        defaultRecursosFisicosShouldNotBeFound("recurso.equals=" + UPDATED_RECURSO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByRecursoIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where recurso in DEFAULT_RECURSO or UPDATED_RECURSO
        defaultRecursosFisicosShouldBeFound("recurso.in=" + DEFAULT_RECURSO + "," + UPDATED_RECURSO);

        // Get all the recursosFisicosList where recurso equals to UPDATED_RECURSO
        defaultRecursosFisicosShouldNotBeFound("recurso.in=" + UPDATED_RECURSO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByRecursoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where recurso is not null
        defaultRecursosFisicosShouldBeFound("recurso.specified=true");

        // Get all the recursosFisicosList where recurso is null
        defaultRecursosFisicosShouldNotBeFound("recurso.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where tipo equals to DEFAULT_TIPO
        defaultRecursosFisicosShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the recursosFisicosList where tipo equals to UPDATED_TIPO
        defaultRecursosFisicosShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultRecursosFisicosShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the recursosFisicosList where tipo equals to UPDATED_TIPO
        defaultRecursosFisicosShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where tipo is not null
        defaultRecursosFisicosShouldBeFound("tipo.specified=true");

        // Get all the recursosFisicosList where tipo is null
        defaultRecursosFisicosShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByUnidadMedidaIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where unidadMedida equals to DEFAULT_UNIDAD_MEDIDA
        defaultRecursosFisicosShouldBeFound("unidadMedida.equals=" + DEFAULT_UNIDAD_MEDIDA);

        // Get all the recursosFisicosList where unidadMedida equals to UPDATED_UNIDAD_MEDIDA
        defaultRecursosFisicosShouldNotBeFound("unidadMedida.equals=" + UPDATED_UNIDAD_MEDIDA);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByUnidadMedidaIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where unidadMedida in DEFAULT_UNIDAD_MEDIDA or UPDATED_UNIDAD_MEDIDA
        defaultRecursosFisicosShouldBeFound("unidadMedida.in=" + DEFAULT_UNIDAD_MEDIDA + "," + UPDATED_UNIDAD_MEDIDA);

        // Get all the recursosFisicosList where unidadMedida equals to UPDATED_UNIDAD_MEDIDA
        defaultRecursosFisicosShouldNotBeFound("unidadMedida.in=" + UPDATED_UNIDAD_MEDIDA);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByUnidadMedidaIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where unidadMedida is not null
        defaultRecursosFisicosShouldBeFound("unidadMedida.specified=true");

        // Get all the recursosFisicosList where unidadMedida is null
        defaultRecursosFisicosShouldNotBeFound("unidadMedida.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario equals to DEFAULT_VALOR_UNITARIO
        defaultRecursosFisicosShouldBeFound("valorUnitario.equals=" + DEFAULT_VALOR_UNITARIO);

        // Get all the recursosFisicosList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.equals=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario in DEFAULT_VALOR_UNITARIO or UPDATED_VALOR_UNITARIO
        defaultRecursosFisicosShouldBeFound("valorUnitario.in=" + DEFAULT_VALOR_UNITARIO + "," + UPDATED_VALOR_UNITARIO);

        // Get all the recursosFisicosList where valorUnitario equals to UPDATED_VALOR_UNITARIO
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.in=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario is not null
        defaultRecursosFisicosShouldBeFound("valorUnitario.specified=true");

        // Get all the recursosFisicosList where valorUnitario is null
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario is greater than or equal to DEFAULT_VALOR_UNITARIO
        defaultRecursosFisicosShouldBeFound("valorUnitario.greaterThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the recursosFisicosList where valorUnitario is greater than or equal to UPDATED_VALOR_UNITARIO
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.greaterThanOrEqual=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario is less than or equal to DEFAULT_VALOR_UNITARIO
        defaultRecursosFisicosShouldBeFound("valorUnitario.lessThanOrEqual=" + DEFAULT_VALOR_UNITARIO);

        // Get all the recursosFisicosList where valorUnitario is less than or equal to SMALLER_VALOR_UNITARIO
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.lessThanOrEqual=" + SMALLER_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario is less than DEFAULT_VALOR_UNITARIO
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.lessThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the recursosFisicosList where valorUnitario is less than UPDATED_VALOR_UNITARIO
        defaultRecursosFisicosShouldBeFound("valorUnitario.lessThan=" + UPDATED_VALOR_UNITARIO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValorUnitarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valorUnitario is greater than DEFAULT_VALOR_UNITARIO
        defaultRecursosFisicosShouldNotBeFound("valorUnitario.greaterThan=" + DEFAULT_VALOR_UNITARIO);

        // Get all the recursosFisicosList where valorUnitario is greater than SMALLER_VALOR_UNITARIO
        defaultRecursosFisicosShouldBeFound("valorUnitario.greaterThan=" + SMALLER_VALOR_UNITARIO);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H equals to DEFAULT_VALOR_1_H
        defaultRecursosFisicosShouldBeFound("valor1H.equals=" + DEFAULT_VALOR_1_H);

        // Get all the recursosFisicosList where valor1H equals to UPDATED_VALOR_1_H
        defaultRecursosFisicosShouldNotBeFound("valor1H.equals=" + UPDATED_VALOR_1_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H in DEFAULT_VALOR_1_H or UPDATED_VALOR_1_H
        defaultRecursosFisicosShouldBeFound("valor1H.in=" + DEFAULT_VALOR_1_H + "," + UPDATED_VALOR_1_H);

        // Get all the recursosFisicosList where valor1H equals to UPDATED_VALOR_1_H
        defaultRecursosFisicosShouldNotBeFound("valor1H.in=" + UPDATED_VALOR_1_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H is not null
        defaultRecursosFisicosShouldBeFound("valor1H.specified=true");

        // Get all the recursosFisicosList where valor1H is null
        defaultRecursosFisicosShouldNotBeFound("valor1H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H is greater than or equal to DEFAULT_VALOR_1_H
        defaultRecursosFisicosShouldBeFound("valor1H.greaterThanOrEqual=" + DEFAULT_VALOR_1_H);

        // Get all the recursosFisicosList where valor1H is greater than or equal to UPDATED_VALOR_1_H
        defaultRecursosFisicosShouldNotBeFound("valor1H.greaterThanOrEqual=" + UPDATED_VALOR_1_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H is less than or equal to DEFAULT_VALOR_1_H
        defaultRecursosFisicosShouldBeFound("valor1H.lessThanOrEqual=" + DEFAULT_VALOR_1_H);

        // Get all the recursosFisicosList where valor1H is less than or equal to SMALLER_VALOR_1_H
        defaultRecursosFisicosShouldNotBeFound("valor1H.lessThanOrEqual=" + SMALLER_VALOR_1_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H is less than DEFAULT_VALOR_1_H
        defaultRecursosFisicosShouldNotBeFound("valor1H.lessThan=" + DEFAULT_VALOR_1_H);

        // Get all the recursosFisicosList where valor1H is less than UPDATED_VALOR_1_H
        defaultRecursosFisicosShouldBeFound("valor1H.lessThan=" + UPDATED_VALOR_1_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor1HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor1H is greater than DEFAULT_VALOR_1_H
        defaultRecursosFisicosShouldNotBeFound("valor1H.greaterThan=" + DEFAULT_VALOR_1_H);

        // Get all the recursosFisicosList where valor1H is greater than SMALLER_VALOR_1_H
        defaultRecursosFisicosShouldBeFound("valor1H.greaterThan=" + SMALLER_VALOR_1_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H equals to DEFAULT_VALOR_2_H
        defaultRecursosFisicosShouldBeFound("valor2H.equals=" + DEFAULT_VALOR_2_H);

        // Get all the recursosFisicosList where valor2H equals to UPDATED_VALOR_2_H
        defaultRecursosFisicosShouldNotBeFound("valor2H.equals=" + UPDATED_VALOR_2_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H in DEFAULT_VALOR_2_H or UPDATED_VALOR_2_H
        defaultRecursosFisicosShouldBeFound("valor2H.in=" + DEFAULT_VALOR_2_H + "," + UPDATED_VALOR_2_H);

        // Get all the recursosFisicosList where valor2H equals to UPDATED_VALOR_2_H
        defaultRecursosFisicosShouldNotBeFound("valor2H.in=" + UPDATED_VALOR_2_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H is not null
        defaultRecursosFisicosShouldBeFound("valor2H.specified=true");

        // Get all the recursosFisicosList where valor2H is null
        defaultRecursosFisicosShouldNotBeFound("valor2H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H is greater than or equal to DEFAULT_VALOR_2_H
        defaultRecursosFisicosShouldBeFound("valor2H.greaterThanOrEqual=" + DEFAULT_VALOR_2_H);

        // Get all the recursosFisicosList where valor2H is greater than or equal to UPDATED_VALOR_2_H
        defaultRecursosFisicosShouldNotBeFound("valor2H.greaterThanOrEqual=" + UPDATED_VALOR_2_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H is less than or equal to DEFAULT_VALOR_2_H
        defaultRecursosFisicosShouldBeFound("valor2H.lessThanOrEqual=" + DEFAULT_VALOR_2_H);

        // Get all the recursosFisicosList where valor2H is less than or equal to SMALLER_VALOR_2_H
        defaultRecursosFisicosShouldNotBeFound("valor2H.lessThanOrEqual=" + SMALLER_VALOR_2_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H is less than DEFAULT_VALOR_2_H
        defaultRecursosFisicosShouldNotBeFound("valor2H.lessThan=" + DEFAULT_VALOR_2_H);

        // Get all the recursosFisicosList where valor2H is less than UPDATED_VALOR_2_H
        defaultRecursosFisicosShouldBeFound("valor2H.lessThan=" + UPDATED_VALOR_2_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor2HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor2H is greater than DEFAULT_VALOR_2_H
        defaultRecursosFisicosShouldNotBeFound("valor2H.greaterThan=" + DEFAULT_VALOR_2_H);

        // Get all the recursosFisicosList where valor2H is greater than SMALLER_VALOR_2_H
        defaultRecursosFisicosShouldBeFound("valor2H.greaterThan=" + SMALLER_VALOR_2_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H equals to DEFAULT_VALOR_3_H
        defaultRecursosFisicosShouldBeFound("valor3H.equals=" + DEFAULT_VALOR_3_H);

        // Get all the recursosFisicosList where valor3H equals to UPDATED_VALOR_3_H
        defaultRecursosFisicosShouldNotBeFound("valor3H.equals=" + UPDATED_VALOR_3_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H in DEFAULT_VALOR_3_H or UPDATED_VALOR_3_H
        defaultRecursosFisicosShouldBeFound("valor3H.in=" + DEFAULT_VALOR_3_H + "," + UPDATED_VALOR_3_H);

        // Get all the recursosFisicosList where valor3H equals to UPDATED_VALOR_3_H
        defaultRecursosFisicosShouldNotBeFound("valor3H.in=" + UPDATED_VALOR_3_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H is not null
        defaultRecursosFisicosShouldBeFound("valor3H.specified=true");

        // Get all the recursosFisicosList where valor3H is null
        defaultRecursosFisicosShouldNotBeFound("valor3H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H is greater than or equal to DEFAULT_VALOR_3_H
        defaultRecursosFisicosShouldBeFound("valor3H.greaterThanOrEqual=" + DEFAULT_VALOR_3_H);

        // Get all the recursosFisicosList where valor3H is greater than or equal to UPDATED_VALOR_3_H
        defaultRecursosFisicosShouldNotBeFound("valor3H.greaterThanOrEqual=" + UPDATED_VALOR_3_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H is less than or equal to DEFAULT_VALOR_3_H
        defaultRecursosFisicosShouldBeFound("valor3H.lessThanOrEqual=" + DEFAULT_VALOR_3_H);

        // Get all the recursosFisicosList where valor3H is less than or equal to SMALLER_VALOR_3_H
        defaultRecursosFisicosShouldNotBeFound("valor3H.lessThanOrEqual=" + SMALLER_VALOR_3_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H is less than DEFAULT_VALOR_3_H
        defaultRecursosFisicosShouldNotBeFound("valor3H.lessThan=" + DEFAULT_VALOR_3_H);

        // Get all the recursosFisicosList where valor3H is less than UPDATED_VALOR_3_H
        defaultRecursosFisicosShouldBeFound("valor3H.lessThan=" + UPDATED_VALOR_3_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor3HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor3H is greater than DEFAULT_VALOR_3_H
        defaultRecursosFisicosShouldNotBeFound("valor3H.greaterThan=" + DEFAULT_VALOR_3_H);

        // Get all the recursosFisicosList where valor3H is greater than SMALLER_VALOR_3_H
        defaultRecursosFisicosShouldBeFound("valor3H.greaterThan=" + SMALLER_VALOR_3_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H equals to DEFAULT_VALOR_4_H
        defaultRecursosFisicosShouldBeFound("valor4H.equals=" + DEFAULT_VALOR_4_H);

        // Get all the recursosFisicosList where valor4H equals to UPDATED_VALOR_4_H
        defaultRecursosFisicosShouldNotBeFound("valor4H.equals=" + UPDATED_VALOR_4_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H in DEFAULT_VALOR_4_H or UPDATED_VALOR_4_H
        defaultRecursosFisicosShouldBeFound("valor4H.in=" + DEFAULT_VALOR_4_H + "," + UPDATED_VALOR_4_H);

        // Get all the recursosFisicosList where valor4H equals to UPDATED_VALOR_4_H
        defaultRecursosFisicosShouldNotBeFound("valor4H.in=" + UPDATED_VALOR_4_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H is not null
        defaultRecursosFisicosShouldBeFound("valor4H.specified=true");

        // Get all the recursosFisicosList where valor4H is null
        defaultRecursosFisicosShouldNotBeFound("valor4H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H is greater than or equal to DEFAULT_VALOR_4_H
        defaultRecursosFisicosShouldBeFound("valor4H.greaterThanOrEqual=" + DEFAULT_VALOR_4_H);

        // Get all the recursosFisicosList where valor4H is greater than or equal to UPDATED_VALOR_4_H
        defaultRecursosFisicosShouldNotBeFound("valor4H.greaterThanOrEqual=" + UPDATED_VALOR_4_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H is less than or equal to DEFAULT_VALOR_4_H
        defaultRecursosFisicosShouldBeFound("valor4H.lessThanOrEqual=" + DEFAULT_VALOR_4_H);

        // Get all the recursosFisicosList where valor4H is less than or equal to SMALLER_VALOR_4_H
        defaultRecursosFisicosShouldNotBeFound("valor4H.lessThanOrEqual=" + SMALLER_VALOR_4_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H is less than DEFAULT_VALOR_4_H
        defaultRecursosFisicosShouldNotBeFound("valor4H.lessThan=" + DEFAULT_VALOR_4_H);

        // Get all the recursosFisicosList where valor4H is less than UPDATED_VALOR_4_H
        defaultRecursosFisicosShouldBeFound("valor4H.lessThan=" + UPDATED_VALOR_4_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor4HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor4H is greater than DEFAULT_VALOR_4_H
        defaultRecursosFisicosShouldNotBeFound("valor4H.greaterThan=" + DEFAULT_VALOR_4_H);

        // Get all the recursosFisicosList where valor4H is greater than SMALLER_VALOR_4_H
        defaultRecursosFisicosShouldBeFound("valor4H.greaterThan=" + SMALLER_VALOR_4_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H equals to DEFAULT_VALOR_5_H
        defaultRecursosFisicosShouldBeFound("valor5H.equals=" + DEFAULT_VALOR_5_H);

        // Get all the recursosFisicosList where valor5H equals to UPDATED_VALOR_5_H
        defaultRecursosFisicosShouldNotBeFound("valor5H.equals=" + UPDATED_VALOR_5_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H in DEFAULT_VALOR_5_H or UPDATED_VALOR_5_H
        defaultRecursosFisicosShouldBeFound("valor5H.in=" + DEFAULT_VALOR_5_H + "," + UPDATED_VALOR_5_H);

        // Get all the recursosFisicosList where valor5H equals to UPDATED_VALOR_5_H
        defaultRecursosFisicosShouldNotBeFound("valor5H.in=" + UPDATED_VALOR_5_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H is not null
        defaultRecursosFisicosShouldBeFound("valor5H.specified=true");

        // Get all the recursosFisicosList where valor5H is null
        defaultRecursosFisicosShouldNotBeFound("valor5H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H is greater than or equal to DEFAULT_VALOR_5_H
        defaultRecursosFisicosShouldBeFound("valor5H.greaterThanOrEqual=" + DEFAULT_VALOR_5_H);

        // Get all the recursosFisicosList where valor5H is greater than or equal to UPDATED_VALOR_5_H
        defaultRecursosFisicosShouldNotBeFound("valor5H.greaterThanOrEqual=" + UPDATED_VALOR_5_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H is less than or equal to DEFAULT_VALOR_5_H
        defaultRecursosFisicosShouldBeFound("valor5H.lessThanOrEqual=" + DEFAULT_VALOR_5_H);

        // Get all the recursosFisicosList where valor5H is less than or equal to SMALLER_VALOR_5_H
        defaultRecursosFisicosShouldNotBeFound("valor5H.lessThanOrEqual=" + SMALLER_VALOR_5_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H is less than DEFAULT_VALOR_5_H
        defaultRecursosFisicosShouldNotBeFound("valor5H.lessThan=" + DEFAULT_VALOR_5_H);

        // Get all the recursosFisicosList where valor5H is less than UPDATED_VALOR_5_H
        defaultRecursosFisicosShouldBeFound("valor5H.lessThan=" + UPDATED_VALOR_5_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor5HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor5H is greater than DEFAULT_VALOR_5_H
        defaultRecursosFisicosShouldNotBeFound("valor5H.greaterThan=" + DEFAULT_VALOR_5_H);

        // Get all the recursosFisicosList where valor5H is greater than SMALLER_VALOR_5_H
        defaultRecursosFisicosShouldBeFound("valor5H.greaterThan=" + SMALLER_VALOR_5_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H equals to DEFAULT_VALOR_6_H
        defaultRecursosFisicosShouldBeFound("valor6H.equals=" + DEFAULT_VALOR_6_H);

        // Get all the recursosFisicosList where valor6H equals to UPDATED_VALOR_6_H
        defaultRecursosFisicosShouldNotBeFound("valor6H.equals=" + UPDATED_VALOR_6_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H in DEFAULT_VALOR_6_H or UPDATED_VALOR_6_H
        defaultRecursosFisicosShouldBeFound("valor6H.in=" + DEFAULT_VALOR_6_H + "," + UPDATED_VALOR_6_H);

        // Get all the recursosFisicosList where valor6H equals to UPDATED_VALOR_6_H
        defaultRecursosFisicosShouldNotBeFound("valor6H.in=" + UPDATED_VALOR_6_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H is not null
        defaultRecursosFisicosShouldBeFound("valor6H.specified=true");

        // Get all the recursosFisicosList where valor6H is null
        defaultRecursosFisicosShouldNotBeFound("valor6H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H is greater than or equal to DEFAULT_VALOR_6_H
        defaultRecursosFisicosShouldBeFound("valor6H.greaterThanOrEqual=" + DEFAULT_VALOR_6_H);

        // Get all the recursosFisicosList where valor6H is greater than or equal to UPDATED_VALOR_6_H
        defaultRecursosFisicosShouldNotBeFound("valor6H.greaterThanOrEqual=" + UPDATED_VALOR_6_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H is less than or equal to DEFAULT_VALOR_6_H
        defaultRecursosFisicosShouldBeFound("valor6H.lessThanOrEqual=" + DEFAULT_VALOR_6_H);

        // Get all the recursosFisicosList where valor6H is less than or equal to SMALLER_VALOR_6_H
        defaultRecursosFisicosShouldNotBeFound("valor6H.lessThanOrEqual=" + SMALLER_VALOR_6_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H is less than DEFAULT_VALOR_6_H
        defaultRecursosFisicosShouldNotBeFound("valor6H.lessThan=" + DEFAULT_VALOR_6_H);

        // Get all the recursosFisicosList where valor6H is less than UPDATED_VALOR_6_H
        defaultRecursosFisicosShouldBeFound("valor6H.lessThan=" + UPDATED_VALOR_6_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor6HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor6H is greater than DEFAULT_VALOR_6_H
        defaultRecursosFisicosShouldNotBeFound("valor6H.greaterThan=" + DEFAULT_VALOR_6_H);

        // Get all the recursosFisicosList where valor6H is greater than SMALLER_VALOR_6_H
        defaultRecursosFisicosShouldBeFound("valor6H.greaterThan=" + SMALLER_VALOR_6_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H equals to DEFAULT_VALOR_7_H
        defaultRecursosFisicosShouldBeFound("valor7H.equals=" + DEFAULT_VALOR_7_H);

        // Get all the recursosFisicosList where valor7H equals to UPDATED_VALOR_7_H
        defaultRecursosFisicosShouldNotBeFound("valor7H.equals=" + UPDATED_VALOR_7_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H in DEFAULT_VALOR_7_H or UPDATED_VALOR_7_H
        defaultRecursosFisicosShouldBeFound("valor7H.in=" + DEFAULT_VALOR_7_H + "," + UPDATED_VALOR_7_H);

        // Get all the recursosFisicosList where valor7H equals to UPDATED_VALOR_7_H
        defaultRecursosFisicosShouldNotBeFound("valor7H.in=" + UPDATED_VALOR_7_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H is not null
        defaultRecursosFisicosShouldBeFound("valor7H.specified=true");

        // Get all the recursosFisicosList where valor7H is null
        defaultRecursosFisicosShouldNotBeFound("valor7H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H is greater than or equal to DEFAULT_VALOR_7_H
        defaultRecursosFisicosShouldBeFound("valor7H.greaterThanOrEqual=" + DEFAULT_VALOR_7_H);

        // Get all the recursosFisicosList where valor7H is greater than or equal to UPDATED_VALOR_7_H
        defaultRecursosFisicosShouldNotBeFound("valor7H.greaterThanOrEqual=" + UPDATED_VALOR_7_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H is less than or equal to DEFAULT_VALOR_7_H
        defaultRecursosFisicosShouldBeFound("valor7H.lessThanOrEqual=" + DEFAULT_VALOR_7_H);

        // Get all the recursosFisicosList where valor7H is less than or equal to SMALLER_VALOR_7_H
        defaultRecursosFisicosShouldNotBeFound("valor7H.lessThanOrEqual=" + SMALLER_VALOR_7_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H is less than DEFAULT_VALOR_7_H
        defaultRecursosFisicosShouldNotBeFound("valor7H.lessThan=" + DEFAULT_VALOR_7_H);

        // Get all the recursosFisicosList where valor7H is less than UPDATED_VALOR_7_H
        defaultRecursosFisicosShouldBeFound("valor7H.lessThan=" + UPDATED_VALOR_7_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor7HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor7H is greater than DEFAULT_VALOR_7_H
        defaultRecursosFisicosShouldNotBeFound("valor7H.greaterThan=" + DEFAULT_VALOR_7_H);

        // Get all the recursosFisicosList where valor7H is greater than SMALLER_VALOR_7_H
        defaultRecursosFisicosShouldBeFound("valor7H.greaterThan=" + SMALLER_VALOR_7_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H equals to DEFAULT_VALOR_8_H
        defaultRecursosFisicosShouldBeFound("valor8H.equals=" + DEFAULT_VALOR_8_H);

        // Get all the recursosFisicosList where valor8H equals to UPDATED_VALOR_8_H
        defaultRecursosFisicosShouldNotBeFound("valor8H.equals=" + UPDATED_VALOR_8_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H in DEFAULT_VALOR_8_H or UPDATED_VALOR_8_H
        defaultRecursosFisicosShouldBeFound("valor8H.in=" + DEFAULT_VALOR_8_H + "," + UPDATED_VALOR_8_H);

        // Get all the recursosFisicosList where valor8H equals to UPDATED_VALOR_8_H
        defaultRecursosFisicosShouldNotBeFound("valor8H.in=" + UPDATED_VALOR_8_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H is not null
        defaultRecursosFisicosShouldBeFound("valor8H.specified=true");

        // Get all the recursosFisicosList where valor8H is null
        defaultRecursosFisicosShouldNotBeFound("valor8H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H is greater than or equal to DEFAULT_VALOR_8_H
        defaultRecursosFisicosShouldBeFound("valor8H.greaterThanOrEqual=" + DEFAULT_VALOR_8_H);

        // Get all the recursosFisicosList where valor8H is greater than or equal to UPDATED_VALOR_8_H
        defaultRecursosFisicosShouldNotBeFound("valor8H.greaterThanOrEqual=" + UPDATED_VALOR_8_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H is less than or equal to DEFAULT_VALOR_8_H
        defaultRecursosFisicosShouldBeFound("valor8H.lessThanOrEqual=" + DEFAULT_VALOR_8_H);

        // Get all the recursosFisicosList where valor8H is less than or equal to SMALLER_VALOR_8_H
        defaultRecursosFisicosShouldNotBeFound("valor8H.lessThanOrEqual=" + SMALLER_VALOR_8_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H is less than DEFAULT_VALOR_8_H
        defaultRecursosFisicosShouldNotBeFound("valor8H.lessThan=" + DEFAULT_VALOR_8_H);

        // Get all the recursosFisicosList where valor8H is less than UPDATED_VALOR_8_H
        defaultRecursosFisicosShouldBeFound("valor8H.lessThan=" + UPDATED_VALOR_8_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor8HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor8H is greater than DEFAULT_VALOR_8_H
        defaultRecursosFisicosShouldNotBeFound("valor8H.greaterThan=" + DEFAULT_VALOR_8_H);

        // Get all the recursosFisicosList where valor8H is greater than SMALLER_VALOR_8_H
        defaultRecursosFisicosShouldBeFound("valor8H.greaterThan=" + SMALLER_VALOR_8_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H equals to DEFAULT_VALOR_9_H
        defaultRecursosFisicosShouldBeFound("valor9H.equals=" + DEFAULT_VALOR_9_H);

        // Get all the recursosFisicosList where valor9H equals to UPDATED_VALOR_9_H
        defaultRecursosFisicosShouldNotBeFound("valor9H.equals=" + UPDATED_VALOR_9_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H in DEFAULT_VALOR_9_H or UPDATED_VALOR_9_H
        defaultRecursosFisicosShouldBeFound("valor9H.in=" + DEFAULT_VALOR_9_H + "," + UPDATED_VALOR_9_H);

        // Get all the recursosFisicosList where valor9H equals to UPDATED_VALOR_9_H
        defaultRecursosFisicosShouldNotBeFound("valor9H.in=" + UPDATED_VALOR_9_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H is not null
        defaultRecursosFisicosShouldBeFound("valor9H.specified=true");

        // Get all the recursosFisicosList where valor9H is null
        defaultRecursosFisicosShouldNotBeFound("valor9H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H is greater than or equal to DEFAULT_VALOR_9_H
        defaultRecursosFisicosShouldBeFound("valor9H.greaterThanOrEqual=" + DEFAULT_VALOR_9_H);

        // Get all the recursosFisicosList where valor9H is greater than or equal to UPDATED_VALOR_9_H
        defaultRecursosFisicosShouldNotBeFound("valor9H.greaterThanOrEqual=" + UPDATED_VALOR_9_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H is less than or equal to DEFAULT_VALOR_9_H
        defaultRecursosFisicosShouldBeFound("valor9H.lessThanOrEqual=" + DEFAULT_VALOR_9_H);

        // Get all the recursosFisicosList where valor9H is less than or equal to SMALLER_VALOR_9_H
        defaultRecursosFisicosShouldNotBeFound("valor9H.lessThanOrEqual=" + SMALLER_VALOR_9_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H is less than DEFAULT_VALOR_9_H
        defaultRecursosFisicosShouldNotBeFound("valor9H.lessThan=" + DEFAULT_VALOR_9_H);

        // Get all the recursosFisicosList where valor9H is less than UPDATED_VALOR_9_H
        defaultRecursosFisicosShouldBeFound("valor9H.lessThan=" + UPDATED_VALOR_9_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor9HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor9H is greater than DEFAULT_VALOR_9_H
        defaultRecursosFisicosShouldNotBeFound("valor9H.greaterThan=" + DEFAULT_VALOR_9_H);

        // Get all the recursosFisicosList where valor9H is greater than SMALLER_VALOR_9_H
        defaultRecursosFisicosShouldBeFound("valor9H.greaterThan=" + SMALLER_VALOR_9_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H equals to DEFAULT_VALOR_10_H
        defaultRecursosFisicosShouldBeFound("valor10H.equals=" + DEFAULT_VALOR_10_H);

        // Get all the recursosFisicosList where valor10H equals to UPDATED_VALOR_10_H
        defaultRecursosFisicosShouldNotBeFound("valor10H.equals=" + UPDATED_VALOR_10_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H in DEFAULT_VALOR_10_H or UPDATED_VALOR_10_H
        defaultRecursosFisicosShouldBeFound("valor10H.in=" + DEFAULT_VALOR_10_H + "," + UPDATED_VALOR_10_H);

        // Get all the recursosFisicosList where valor10H equals to UPDATED_VALOR_10_H
        defaultRecursosFisicosShouldNotBeFound("valor10H.in=" + UPDATED_VALOR_10_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H is not null
        defaultRecursosFisicosShouldBeFound("valor10H.specified=true");

        // Get all the recursosFisicosList where valor10H is null
        defaultRecursosFisicosShouldNotBeFound("valor10H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H is greater than or equal to DEFAULT_VALOR_10_H
        defaultRecursosFisicosShouldBeFound("valor10H.greaterThanOrEqual=" + DEFAULT_VALOR_10_H);

        // Get all the recursosFisicosList where valor10H is greater than or equal to UPDATED_VALOR_10_H
        defaultRecursosFisicosShouldNotBeFound("valor10H.greaterThanOrEqual=" + UPDATED_VALOR_10_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H is less than or equal to DEFAULT_VALOR_10_H
        defaultRecursosFisicosShouldBeFound("valor10H.lessThanOrEqual=" + DEFAULT_VALOR_10_H);

        // Get all the recursosFisicosList where valor10H is less than or equal to SMALLER_VALOR_10_H
        defaultRecursosFisicosShouldNotBeFound("valor10H.lessThanOrEqual=" + SMALLER_VALOR_10_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H is less than DEFAULT_VALOR_10_H
        defaultRecursosFisicosShouldNotBeFound("valor10H.lessThan=" + DEFAULT_VALOR_10_H);

        // Get all the recursosFisicosList where valor10H is less than UPDATED_VALOR_10_H
        defaultRecursosFisicosShouldBeFound("valor10H.lessThan=" + UPDATED_VALOR_10_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor10HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor10H is greater than DEFAULT_VALOR_10_H
        defaultRecursosFisicosShouldNotBeFound("valor10H.greaterThan=" + DEFAULT_VALOR_10_H);

        // Get all the recursosFisicosList where valor10H is greater than SMALLER_VALOR_10_H
        defaultRecursosFisicosShouldBeFound("valor10H.greaterThan=" + SMALLER_VALOR_10_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H equals to DEFAULT_VALOR_11_H
        defaultRecursosFisicosShouldBeFound("valor11H.equals=" + DEFAULT_VALOR_11_H);

        // Get all the recursosFisicosList where valor11H equals to UPDATED_VALOR_11_H
        defaultRecursosFisicosShouldNotBeFound("valor11H.equals=" + UPDATED_VALOR_11_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H in DEFAULT_VALOR_11_H or UPDATED_VALOR_11_H
        defaultRecursosFisicosShouldBeFound("valor11H.in=" + DEFAULT_VALOR_11_H + "," + UPDATED_VALOR_11_H);

        // Get all the recursosFisicosList where valor11H equals to UPDATED_VALOR_11_H
        defaultRecursosFisicosShouldNotBeFound("valor11H.in=" + UPDATED_VALOR_11_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H is not null
        defaultRecursosFisicosShouldBeFound("valor11H.specified=true");

        // Get all the recursosFisicosList where valor11H is null
        defaultRecursosFisicosShouldNotBeFound("valor11H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H is greater than or equal to DEFAULT_VALOR_11_H
        defaultRecursosFisicosShouldBeFound("valor11H.greaterThanOrEqual=" + DEFAULT_VALOR_11_H);

        // Get all the recursosFisicosList where valor11H is greater than or equal to UPDATED_VALOR_11_H
        defaultRecursosFisicosShouldNotBeFound("valor11H.greaterThanOrEqual=" + UPDATED_VALOR_11_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H is less than or equal to DEFAULT_VALOR_11_H
        defaultRecursosFisicosShouldBeFound("valor11H.lessThanOrEqual=" + DEFAULT_VALOR_11_H);

        // Get all the recursosFisicosList where valor11H is less than or equal to SMALLER_VALOR_11_H
        defaultRecursosFisicosShouldNotBeFound("valor11H.lessThanOrEqual=" + SMALLER_VALOR_11_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H is less than DEFAULT_VALOR_11_H
        defaultRecursosFisicosShouldNotBeFound("valor11H.lessThan=" + DEFAULT_VALOR_11_H);

        // Get all the recursosFisicosList where valor11H is less than UPDATED_VALOR_11_H
        defaultRecursosFisicosShouldBeFound("valor11H.lessThan=" + UPDATED_VALOR_11_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor11HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor11H is greater than DEFAULT_VALOR_11_H
        defaultRecursosFisicosShouldNotBeFound("valor11H.greaterThan=" + DEFAULT_VALOR_11_H);

        // Get all the recursosFisicosList where valor11H is greater than SMALLER_VALOR_11_H
        defaultRecursosFisicosShouldBeFound("valor11H.greaterThan=" + SMALLER_VALOR_11_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H equals to DEFAULT_VALOR_12_H
        defaultRecursosFisicosShouldBeFound("valor12H.equals=" + DEFAULT_VALOR_12_H);

        // Get all the recursosFisicosList where valor12H equals to UPDATED_VALOR_12_H
        defaultRecursosFisicosShouldNotBeFound("valor12H.equals=" + UPDATED_VALOR_12_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H in DEFAULT_VALOR_12_H or UPDATED_VALOR_12_H
        defaultRecursosFisicosShouldBeFound("valor12H.in=" + DEFAULT_VALOR_12_H + "," + UPDATED_VALOR_12_H);

        // Get all the recursosFisicosList where valor12H equals to UPDATED_VALOR_12_H
        defaultRecursosFisicosShouldNotBeFound("valor12H.in=" + UPDATED_VALOR_12_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H is not null
        defaultRecursosFisicosShouldBeFound("valor12H.specified=true");

        // Get all the recursosFisicosList where valor12H is null
        defaultRecursosFisicosShouldNotBeFound("valor12H.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H is greater than or equal to DEFAULT_VALOR_12_H
        defaultRecursosFisicosShouldBeFound("valor12H.greaterThanOrEqual=" + DEFAULT_VALOR_12_H);

        // Get all the recursosFisicosList where valor12H is greater than or equal to UPDATED_VALOR_12_H
        defaultRecursosFisicosShouldNotBeFound("valor12H.greaterThanOrEqual=" + UPDATED_VALOR_12_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H is less than or equal to DEFAULT_VALOR_12_H
        defaultRecursosFisicosShouldBeFound("valor12H.lessThanOrEqual=" + DEFAULT_VALOR_12_H);

        // Get all the recursosFisicosList where valor12H is less than or equal to SMALLER_VALOR_12_H
        defaultRecursosFisicosShouldNotBeFound("valor12H.lessThanOrEqual=" + SMALLER_VALOR_12_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsLessThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H is less than DEFAULT_VALOR_12_H
        defaultRecursosFisicosShouldNotBeFound("valor12H.lessThan=" + DEFAULT_VALOR_12_H);

        // Get all the recursosFisicosList where valor12H is less than UPDATED_VALOR_12_H
        defaultRecursosFisicosShouldBeFound("valor12H.lessThan=" + UPDATED_VALOR_12_H);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByValor12HIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where valor12H is greater than DEFAULT_VALOR_12_H
        defaultRecursosFisicosShouldNotBeFound("valor12H.greaterThan=" + DEFAULT_VALOR_12_H);

        // Get all the recursosFisicosList where valor12H is greater than SMALLER_VALOR_12_H
        defaultRecursosFisicosShouldBeFound("valor12H.greaterThan=" + SMALLER_VALOR_12_H);
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where impuesto equals to DEFAULT_IMPUESTO
        defaultRecursosFisicosShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the recursosFisicosList where impuesto equals to UPDATED_IMPUESTO
        defaultRecursosFisicosShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultRecursosFisicosShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the recursosFisicosList where impuesto equals to UPDATED_IMPUESTO
        defaultRecursosFisicosShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);

        // Get all the recursosFisicosList where impuesto is not null
        defaultRecursosFisicosShouldBeFound("impuesto.specified=true");

        // Get all the recursosFisicosList where impuesto is null
        defaultRecursosFisicosShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecursosFisicosBySedeIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);
        Sedes sede = SedesResourceIT.createEntity(em);
        em.persist(sede);
        em.flush();
        recursosFisicos.setSede(sede);
        recursosFisicosRepository.saveAndFlush(recursosFisicos);
        Long sedeId = sede.getId();

        // Get all the recursosFisicosList where sede equals to sedeId
        defaultRecursosFisicosShouldBeFound("sedeId.equals=" + sedeId);

        // Get all the recursosFisicosList where sede equals to sedeId + 1
        defaultRecursosFisicosShouldNotBeFound("sedeId.equals=" + (sedeId + 1));
    }


    @Test
    @Transactional
    public void getAllRecursosFisicosByTipoRecursoIsEqualToSomething() throws Exception {
        // Initialize the database
        recursosFisicosRepository.saveAndFlush(recursosFisicos);
        TipoRecurso tipoRecurso = TipoRecursoResourceIT.createEntity(em);
        em.persist(tipoRecurso);
        em.flush();
        recursosFisicos.setTipoRecurso(tipoRecurso);
        recursosFisicosRepository.saveAndFlush(recursosFisicos);
        Long tipoRecursoId = tipoRecurso.getId();

        // Get all the recursosFisicosList where tipoRecurso equals to tipoRecursoId
        defaultRecursosFisicosShouldBeFound("tipoRecursoId.equals=" + tipoRecursoId);

        // Get all the recursosFisicosList where tipoRecurso equals to tipoRecursoId + 1
        defaultRecursosFisicosShouldNotBeFound("tipoRecursoId.equals=" + (tipoRecursoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecursosFisicosShouldBeFound(String filter) throws Exception {
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursosFisicos.getId().intValue())))
            .andExpect(jsonPath("$.[*].recurso").value(hasItem(DEFAULT_RECURSO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].unidadMedida").value(hasItem(DEFAULT_UNIDAD_MEDIDA)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO)))
            .andExpect(jsonPath("$.[*].valor1H").value(hasItem(DEFAULT_VALOR_1_H)))
            .andExpect(jsonPath("$.[*].valor2H").value(hasItem(DEFAULT_VALOR_2_H)))
            .andExpect(jsonPath("$.[*].valor3H").value(hasItem(DEFAULT_VALOR_3_H)))
            .andExpect(jsonPath("$.[*].valor4H").value(hasItem(DEFAULT_VALOR_4_H)))
            .andExpect(jsonPath("$.[*].valor5H").value(hasItem(DEFAULT_VALOR_5_H)))
            .andExpect(jsonPath("$.[*].valor6H").value(hasItem(DEFAULT_VALOR_6_H)))
            .andExpect(jsonPath("$.[*].valor7H").value(hasItem(DEFAULT_VALOR_7_H)))
            .andExpect(jsonPath("$.[*].valor8H").value(hasItem(DEFAULT_VALOR_8_H)))
            .andExpect(jsonPath("$.[*].valor9H").value(hasItem(DEFAULT_VALOR_9_H)))
            .andExpect(jsonPath("$.[*].valor10H").value(hasItem(DEFAULT_VALOR_10_H)))
            .andExpect(jsonPath("$.[*].valor11H").value(hasItem(DEFAULT_VALOR_11_H)))
            .andExpect(jsonPath("$.[*].valor12H").value(hasItem(DEFAULT_VALOR_12_H)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))));

        // Check, that the count call also returns 1
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecursosFisicosShouldNotBeFound(String filter) throws Exception {
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRecursosFisicos() throws Exception {
        // Get the recursosFisicos
        restRecursosFisicosMockMvc.perform(get("/api/recursos-fisicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecursosFisicos() throws Exception {
        // Initialize the database
        recursosFisicosService.save(recursosFisicos);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRecursosFisicosSearchRepository);

        int databaseSizeBeforeUpdate = recursosFisicosRepository.findAll().size();

        // Update the recursosFisicos
        RecursosFisicos updatedRecursosFisicos = recursosFisicosRepository.findById(recursosFisicos.getId()).get();
        // Disconnect from session so that the updates on updatedRecursosFisicos are not directly saved in db
        em.detach(updatedRecursosFisicos);
        updatedRecursosFisicos
            .recurso(UPDATED_RECURSO)
            .tipo(UPDATED_TIPO)
            .unidadMedida(UPDATED_UNIDAD_MEDIDA)
            .valorUnitario(UPDATED_VALOR_UNITARIO)
            .valor1H(UPDATED_VALOR_1_H)
            .valor2H(UPDATED_VALOR_2_H)
            .valor3H(UPDATED_VALOR_3_H)
            .valor4H(UPDATED_VALOR_4_H)
            .valor5H(UPDATED_VALOR_5_H)
            .valor6H(UPDATED_VALOR_6_H)
            .valor7H(UPDATED_VALOR_7_H)
            .valor8H(UPDATED_VALOR_8_H)
            .valor9H(UPDATED_VALOR_9_H)
            .valor10H(UPDATED_VALOR_10_H)
            .valor11H(UPDATED_VALOR_11_H)
            .valor12H(UPDATED_VALOR_12_H)
            .impuesto(UPDATED_IMPUESTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE);

        restRecursosFisicosMockMvc.perform(put("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecursosFisicos)))
            .andExpect(status().isOk());

        // Validate the RecursosFisicos in the database
        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeUpdate);
        RecursosFisicos testRecursosFisicos = recursosFisicosList.get(recursosFisicosList.size() - 1);
        assertThat(testRecursosFisicos.getRecurso()).isEqualTo(UPDATED_RECURSO);
        assertThat(testRecursosFisicos.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testRecursosFisicos.getUnidadMedida()).isEqualTo(UPDATED_UNIDAD_MEDIDA);
        assertThat(testRecursosFisicos.getValorUnitario()).isEqualTo(UPDATED_VALOR_UNITARIO);
        assertThat(testRecursosFisicos.getValor1H()).isEqualTo(UPDATED_VALOR_1_H);
        assertThat(testRecursosFisicos.getValor2H()).isEqualTo(UPDATED_VALOR_2_H);
        assertThat(testRecursosFisicos.getValor3H()).isEqualTo(UPDATED_VALOR_3_H);
        assertThat(testRecursosFisicos.getValor4H()).isEqualTo(UPDATED_VALOR_4_H);
        assertThat(testRecursosFisicos.getValor5H()).isEqualTo(UPDATED_VALOR_5_H);
        assertThat(testRecursosFisicos.getValor6H()).isEqualTo(UPDATED_VALOR_6_H);
        assertThat(testRecursosFisicos.getValor7H()).isEqualTo(UPDATED_VALOR_7_H);
        assertThat(testRecursosFisicos.getValor8H()).isEqualTo(UPDATED_VALOR_8_H);
        assertThat(testRecursosFisicos.getValor9H()).isEqualTo(UPDATED_VALOR_9_H);
        assertThat(testRecursosFisicos.getValor10H()).isEqualTo(UPDATED_VALOR_10_H);
        assertThat(testRecursosFisicos.getValor11H()).isEqualTo(UPDATED_VALOR_11_H);
        assertThat(testRecursosFisicos.getValor12H()).isEqualTo(UPDATED_VALOR_12_H);
        assertThat(testRecursosFisicos.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testRecursosFisicos.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testRecursosFisicos.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testRecursosFisicos.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testRecursosFisicos.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);

        // Validate the RecursosFisicos in Elasticsearch
        verify(mockRecursosFisicosSearchRepository, times(1)).save(testRecursosFisicos);
    }

    @Test
    @Transactional
    public void updateNonExistingRecursosFisicos() throws Exception {
        int databaseSizeBeforeUpdate = recursosFisicosRepository.findAll().size();

        // Create the RecursosFisicos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursosFisicosMockMvc.perform(put("/api/recursos-fisicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recursosFisicos)))
            .andExpect(status().isBadRequest());

        // Validate the RecursosFisicos in the database
        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RecursosFisicos in Elasticsearch
        verify(mockRecursosFisicosSearchRepository, times(0)).save(recursosFisicos);
    }

    @Test
    @Transactional
    public void deleteRecursosFisicos() throws Exception {
        // Initialize the database
        recursosFisicosService.save(recursosFisicos);

        int databaseSizeBeforeDelete = recursosFisicosRepository.findAll().size();

        // Delete the recursosFisicos
        restRecursosFisicosMockMvc.perform(delete("/api/recursos-fisicos/{id}", recursosFisicos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecursosFisicos> recursosFisicosList = recursosFisicosRepository.findAll();
        assertThat(recursosFisicosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RecursosFisicos in Elasticsearch
        verify(mockRecursosFisicosSearchRepository, times(1)).deleteById(recursosFisicos.getId());
    }

    @Test
    @Transactional
    public void searchRecursosFisicos() throws Exception {
        // Initialize the database
        recursosFisicosService.save(recursosFisicos);
        when(mockRecursosFisicosSearchRepository.search(queryStringQuery("id:" + recursosFisicos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(recursosFisicos), PageRequest.of(0, 1), 1));
        // Search the recursosFisicos
        restRecursosFisicosMockMvc.perform(get("/api/_search/recursos-fisicos?query=id:" + recursosFisicos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursosFisicos.getId().intValue())))
            .andExpect(jsonPath("$.[*].recurso").value(hasItem(DEFAULT_RECURSO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].unidadMedida").value(hasItem(DEFAULT_UNIDAD_MEDIDA)))
            .andExpect(jsonPath("$.[*].valorUnitario").value(hasItem(DEFAULT_VALOR_UNITARIO)))
            .andExpect(jsonPath("$.[*].valor1H").value(hasItem(DEFAULT_VALOR_1_H)))
            .andExpect(jsonPath("$.[*].valor2H").value(hasItem(DEFAULT_VALOR_2_H)))
            .andExpect(jsonPath("$.[*].valor3H").value(hasItem(DEFAULT_VALOR_3_H)))
            .andExpect(jsonPath("$.[*].valor4H").value(hasItem(DEFAULT_VALOR_4_H)))
            .andExpect(jsonPath("$.[*].valor5H").value(hasItem(DEFAULT_VALOR_5_H)))
            .andExpect(jsonPath("$.[*].valor6H").value(hasItem(DEFAULT_VALOR_6_H)))
            .andExpect(jsonPath("$.[*].valor7H").value(hasItem(DEFAULT_VALOR_7_H)))
            .andExpect(jsonPath("$.[*].valor8H").value(hasItem(DEFAULT_VALOR_8_H)))
            .andExpect(jsonPath("$.[*].valor9H").value(hasItem(DEFAULT_VALOR_9_H)))
            .andExpect(jsonPath("$.[*].valor10H").value(hasItem(DEFAULT_VALOR_10_H)))
            .andExpect(jsonPath("$.[*].valor11H").value(hasItem(DEFAULT_VALOR_11_H)))
            .andExpect(jsonPath("$.[*].valor12H").value(hasItem(DEFAULT_VALOR_12_H)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursosFisicos.class);
        RecursosFisicos recursosFisicos1 = new RecursosFisicos();
        recursosFisicos1.setId(1L);
        RecursosFisicos recursosFisicos2 = new RecursosFisicos();
        recursosFisicos2.setId(recursosFisicos1.getId());
        assertThat(recursosFisicos1).isEqualTo(recursosFisicos2);
        recursosFisicos2.setId(2L);
        assertThat(recursosFisicos1).isNotEqualTo(recursosFisicos2);
        recursosFisicos1.setId(null);
        assertThat(recursosFisicos1).isNotEqualTo(recursosFisicos2);
    }
}
