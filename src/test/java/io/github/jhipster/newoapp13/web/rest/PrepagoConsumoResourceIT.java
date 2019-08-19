package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.PrepagoConsumo;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo;
import io.github.jhipster.newoapp13.repository.PrepagoConsumoRepository;
import io.github.jhipster.newoapp13.repository.search.PrepagoConsumoSearchRepository;
import io.github.jhipster.newoapp13.service.PrepagoConsumoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.PrepagoConsumoCriteria;
import io.github.jhipster.newoapp13.service.PrepagoConsumoQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PrepagoConsumoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class PrepagoConsumoResourceIT {

    private static final Integer DEFAULT_APORTE = 1;
    private static final Integer UPDATED_APORTE = 2;
    private static final Integer SMALLER_APORTE = 1 - 1;

    private static final Integer DEFAULT_SALDO_ACTUAL = 1;
    private static final Integer UPDATED_SALDO_ACTUAL = 2;
    private static final Integer SMALLER_SALDO_ACTUAL = 1 - 1;

    private static final LocalDate DEFAULT_FECHA_REGISTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_REGISTRO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_REGISTRO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_SALDO_ACTUAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALDO_ACTUAL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_SALDO_ACTUAL = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PrepagoConsumoRepository prepagoConsumoRepository;

    @Autowired
    private PrepagoConsumoService prepagoConsumoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.PrepagoConsumoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrepagoConsumoSearchRepository mockPrepagoConsumoSearchRepository;

    @Autowired
    private PrepagoConsumoQueryService prepagoConsumoQueryService;

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

    private MockMvc restPrepagoConsumoMockMvc;

    private PrepagoConsumo prepagoConsumo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrepagoConsumoResource prepagoConsumoResource = new PrepagoConsumoResource(prepagoConsumoService, prepagoConsumoQueryService);
        this.restPrepagoConsumoMockMvc = MockMvcBuilders.standaloneSetup(prepagoConsumoResource)
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
    public static PrepagoConsumo createEntity(EntityManager em) {
        PrepagoConsumo prepagoConsumo = new PrepagoConsumo()
            .aporte(DEFAULT_APORTE)
            .saldoActual(DEFAULT_SALDO_ACTUAL)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .fechaSaldoActual(DEFAULT_FECHA_SALDO_ACTUAL);
        return prepagoConsumo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrepagoConsumo createUpdatedEntity(EntityManager em) {
        PrepagoConsumo prepagoConsumo = new PrepagoConsumo()
            .aporte(UPDATED_APORTE)
            .saldoActual(UPDATED_SALDO_ACTUAL)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaSaldoActual(UPDATED_FECHA_SALDO_ACTUAL);
        return prepagoConsumo;
    }

    @BeforeEach
    public void initTest() {
        prepagoConsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrepagoConsumo() throws Exception {
        int databaseSizeBeforeCreate = prepagoConsumoRepository.findAll().size();

        // Create the PrepagoConsumo
        restPrepagoConsumoMockMvc.perform(post("/api/prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepagoConsumo)))
            .andExpect(status().isCreated());

        // Validate the PrepagoConsumo in the database
        List<PrepagoConsumo> prepagoConsumoList = prepagoConsumoRepository.findAll();
        assertThat(prepagoConsumoList).hasSize(databaseSizeBeforeCreate + 1);
        PrepagoConsumo testPrepagoConsumo = prepagoConsumoList.get(prepagoConsumoList.size() - 1);
        assertThat(testPrepagoConsumo.getAporte()).isEqualTo(DEFAULT_APORTE);
        assertThat(testPrepagoConsumo.getSaldoActual()).isEqualTo(DEFAULT_SALDO_ACTUAL);
        assertThat(testPrepagoConsumo.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testPrepagoConsumo.getFechaSaldoActual()).isEqualTo(DEFAULT_FECHA_SALDO_ACTUAL);

        // Validate the PrepagoConsumo in Elasticsearch
        verify(mockPrepagoConsumoSearchRepository, times(1)).save(testPrepagoConsumo);
    }

    @Test
    @Transactional
    public void createPrepagoConsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prepagoConsumoRepository.findAll().size();

        // Create the PrepagoConsumo with an existing ID
        prepagoConsumo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrepagoConsumoMockMvc.perform(post("/api/prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepagoConsumo)))
            .andExpect(status().isBadRequest());

        // Validate the PrepagoConsumo in the database
        List<PrepagoConsumo> prepagoConsumoList = prepagoConsumoRepository.findAll();
        assertThat(prepagoConsumoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrepagoConsumo in Elasticsearch
        verify(mockPrepagoConsumoSearchRepository, times(0)).save(prepagoConsumo);
    }


    @Test
    @Transactional
    public void getAllPrepagoConsumos() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepagoConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].aporte").value(hasItem(DEFAULT_APORTE)))
            .andExpect(jsonPath("$.[*].saldoActual").value(hasItem(DEFAULT_SALDO_ACTUAL)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaSaldoActual").value(hasItem(DEFAULT_FECHA_SALDO_ACTUAL.toString())));
    }
    
    @Test
    @Transactional
    public void getPrepagoConsumo() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get the prepagoConsumo
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos/{id}", prepagoConsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prepagoConsumo.getId().intValue()))
            .andExpect(jsonPath("$.aporte").value(DEFAULT_APORTE))
            .andExpect(jsonPath("$.saldoActual").value(DEFAULT_SALDO_ACTUAL))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.fechaSaldoActual").value(DEFAULT_FECHA_SALDO_ACTUAL.toString()));
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte equals to DEFAULT_APORTE
        defaultPrepagoConsumoShouldBeFound("aporte.equals=" + DEFAULT_APORTE);

        // Get all the prepagoConsumoList where aporte equals to UPDATED_APORTE
        defaultPrepagoConsumoShouldNotBeFound("aporte.equals=" + UPDATED_APORTE);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsInShouldWork() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte in DEFAULT_APORTE or UPDATED_APORTE
        defaultPrepagoConsumoShouldBeFound("aporte.in=" + DEFAULT_APORTE + "," + UPDATED_APORTE);

        // Get all the prepagoConsumoList where aporte equals to UPDATED_APORTE
        defaultPrepagoConsumoShouldNotBeFound("aporte.in=" + UPDATED_APORTE);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte is not null
        defaultPrepagoConsumoShouldBeFound("aporte.specified=true");

        // Get all the prepagoConsumoList where aporte is null
        defaultPrepagoConsumoShouldNotBeFound("aporte.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte is greater than or equal to DEFAULT_APORTE
        defaultPrepagoConsumoShouldBeFound("aporte.greaterThanOrEqual=" + DEFAULT_APORTE);

        // Get all the prepagoConsumoList where aporte is greater than or equal to UPDATED_APORTE
        defaultPrepagoConsumoShouldNotBeFound("aporte.greaterThanOrEqual=" + UPDATED_APORTE);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte is less than or equal to DEFAULT_APORTE
        defaultPrepagoConsumoShouldBeFound("aporte.lessThanOrEqual=" + DEFAULT_APORTE);

        // Get all the prepagoConsumoList where aporte is less than or equal to SMALLER_APORTE
        defaultPrepagoConsumoShouldNotBeFound("aporte.lessThanOrEqual=" + SMALLER_APORTE);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsLessThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte is less than DEFAULT_APORTE
        defaultPrepagoConsumoShouldNotBeFound("aporte.lessThan=" + DEFAULT_APORTE);

        // Get all the prepagoConsumoList where aporte is less than UPDATED_APORTE
        defaultPrepagoConsumoShouldBeFound("aporte.lessThan=" + UPDATED_APORTE);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByAporteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where aporte is greater than DEFAULT_APORTE
        defaultPrepagoConsumoShouldNotBeFound("aporte.greaterThan=" + DEFAULT_APORTE);

        // Get all the prepagoConsumoList where aporte is greater than SMALLER_APORTE
        defaultPrepagoConsumoShouldBeFound("aporte.greaterThan=" + SMALLER_APORTE);
    }


    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual equals to DEFAULT_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("saldoActual.equals=" + DEFAULT_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where saldoActual equals to UPDATED_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.equals=" + UPDATED_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsInShouldWork() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual in DEFAULT_SALDO_ACTUAL or UPDATED_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("saldoActual.in=" + DEFAULT_SALDO_ACTUAL + "," + UPDATED_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where saldoActual equals to UPDATED_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.in=" + UPDATED_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual is not null
        defaultPrepagoConsumoShouldBeFound("saldoActual.specified=true");

        // Get all the prepagoConsumoList where saldoActual is null
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual is greater than or equal to DEFAULT_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("saldoActual.greaterThanOrEqual=" + DEFAULT_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where saldoActual is greater than or equal to UPDATED_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.greaterThanOrEqual=" + UPDATED_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual is less than or equal to DEFAULT_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("saldoActual.lessThanOrEqual=" + DEFAULT_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where saldoActual is less than or equal to SMALLER_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.lessThanOrEqual=" + SMALLER_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsLessThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual is less than DEFAULT_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.lessThan=" + DEFAULT_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where saldoActual is less than UPDATED_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("saldoActual.lessThan=" + UPDATED_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosBySaldoActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where saldoActual is greater than DEFAULT_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("saldoActual.greaterThan=" + DEFAULT_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where saldoActual is greater than SMALLER_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("saldoActual.greaterThan=" + SMALLER_SALDO_ACTUAL);
    }


    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro equals to DEFAULT_FECHA_REGISTRO
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.equals=" + DEFAULT_FECHA_REGISTRO);

        // Get all the prepagoConsumoList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.equals=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro in DEFAULT_FECHA_REGISTRO or UPDATED_FECHA_REGISTRO
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.in=" + DEFAULT_FECHA_REGISTRO + "," + UPDATED_FECHA_REGISTRO);

        // Get all the prepagoConsumoList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.in=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro is not null
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.specified=true");

        // Get all the prepagoConsumoList where fechaRegistro is null
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro is greater than or equal to DEFAULT_FECHA_REGISTRO
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.greaterThanOrEqual=" + DEFAULT_FECHA_REGISTRO);

        // Get all the prepagoConsumoList where fechaRegistro is greater than or equal to UPDATED_FECHA_REGISTRO
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.greaterThanOrEqual=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro is less than or equal to DEFAULT_FECHA_REGISTRO
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.lessThanOrEqual=" + DEFAULT_FECHA_REGISTRO);

        // Get all the prepagoConsumoList where fechaRegistro is less than or equal to SMALLER_FECHA_REGISTRO
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.lessThanOrEqual=" + SMALLER_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsLessThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro is less than DEFAULT_FECHA_REGISTRO
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.lessThan=" + DEFAULT_FECHA_REGISTRO);

        // Get all the prepagoConsumoList where fechaRegistro is less than UPDATED_FECHA_REGISTRO
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.lessThan=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaRegistroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaRegistro is greater than DEFAULT_FECHA_REGISTRO
        defaultPrepagoConsumoShouldNotBeFound("fechaRegistro.greaterThan=" + DEFAULT_FECHA_REGISTRO);

        // Get all the prepagoConsumoList where fechaRegistro is greater than SMALLER_FECHA_REGISTRO
        defaultPrepagoConsumoShouldBeFound("fechaRegistro.greaterThan=" + SMALLER_FECHA_REGISTRO);
    }


    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual equals to DEFAULT_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.equals=" + DEFAULT_FECHA_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where fechaSaldoActual equals to UPDATED_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.equals=" + UPDATED_FECHA_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsInShouldWork() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual in DEFAULT_FECHA_SALDO_ACTUAL or UPDATED_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.in=" + DEFAULT_FECHA_SALDO_ACTUAL + "," + UPDATED_FECHA_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where fechaSaldoActual equals to UPDATED_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.in=" + UPDATED_FECHA_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual is not null
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.specified=true");

        // Get all the prepagoConsumoList where fechaSaldoActual is null
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual is greater than or equal to DEFAULT_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.greaterThanOrEqual=" + DEFAULT_FECHA_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where fechaSaldoActual is greater than or equal to UPDATED_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.greaterThanOrEqual=" + UPDATED_FECHA_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual is less than or equal to DEFAULT_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.lessThanOrEqual=" + DEFAULT_FECHA_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where fechaSaldoActual is less than or equal to SMALLER_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.lessThanOrEqual=" + SMALLER_FECHA_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsLessThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual is less than DEFAULT_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.lessThan=" + DEFAULT_FECHA_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where fechaSaldoActual is less than UPDATED_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.lessThan=" + UPDATED_FECHA_SALDO_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllPrepagoConsumosByFechaSaldoActualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);

        // Get all the prepagoConsumoList where fechaSaldoActual is greater than DEFAULT_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldNotBeFound("fechaSaldoActual.greaterThan=" + DEFAULT_FECHA_SALDO_ACTUAL);

        // Get all the prepagoConsumoList where fechaSaldoActual is greater than SMALLER_FECHA_SALDO_ACTUAL
        defaultPrepagoConsumoShouldBeFound("fechaSaldoActual.greaterThan=" + SMALLER_FECHA_SALDO_ACTUAL);
    }


    @Test
    @Transactional
    public void getAllPrepagoConsumosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        prepagoConsumo.setMiembro(miembro);
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);
        Long miembroId = miembro.getId();

        // Get all the prepagoConsumoList where miembro equals to miembroId
        defaultPrepagoConsumoShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the prepagoConsumoList where miembro equals to miembroId + 1
        defaultPrepagoConsumoShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }


    @Test
    @Transactional
    public void getAllPrepagoConsumosByTipoPrepagoIsEqualToSomething() throws Exception {
        // Initialize the database
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);
        TipoPrepagoConsumo tipoPrepago = TipoPrepagoConsumoResourceIT.createEntity(em);
        em.persist(tipoPrepago);
        em.flush();
        prepagoConsumo.setTipoPrepago(tipoPrepago);
        prepagoConsumoRepository.saveAndFlush(prepagoConsumo);
        Long tipoPrepagoId = tipoPrepago.getId();

        // Get all the prepagoConsumoList where tipoPrepago equals to tipoPrepagoId
        defaultPrepagoConsumoShouldBeFound("tipoPrepagoId.equals=" + tipoPrepagoId);

        // Get all the prepagoConsumoList where tipoPrepago equals to tipoPrepagoId + 1
        defaultPrepagoConsumoShouldNotBeFound("tipoPrepagoId.equals=" + (tipoPrepagoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrepagoConsumoShouldBeFound(String filter) throws Exception {
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepagoConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].aporte").value(hasItem(DEFAULT_APORTE)))
            .andExpect(jsonPath("$.[*].saldoActual").value(hasItem(DEFAULT_SALDO_ACTUAL)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaSaldoActual").value(hasItem(DEFAULT_FECHA_SALDO_ACTUAL.toString())));

        // Check, that the count call also returns 1
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrepagoConsumoShouldNotBeFound(String filter) throws Exception {
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPrepagoConsumo() throws Exception {
        // Get the prepagoConsumo
        restPrepagoConsumoMockMvc.perform(get("/api/prepago-consumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrepagoConsumo() throws Exception {
        // Initialize the database
        prepagoConsumoService.save(prepagoConsumo);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPrepagoConsumoSearchRepository);

        int databaseSizeBeforeUpdate = prepagoConsumoRepository.findAll().size();

        // Update the prepagoConsumo
        PrepagoConsumo updatedPrepagoConsumo = prepagoConsumoRepository.findById(prepagoConsumo.getId()).get();
        // Disconnect from session so that the updates on updatedPrepagoConsumo are not directly saved in db
        em.detach(updatedPrepagoConsumo);
        updatedPrepagoConsumo
            .aporte(UPDATED_APORTE)
            .saldoActual(UPDATED_SALDO_ACTUAL)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaSaldoActual(UPDATED_FECHA_SALDO_ACTUAL);

        restPrepagoConsumoMockMvc.perform(put("/api/prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrepagoConsumo)))
            .andExpect(status().isOk());

        // Validate the PrepagoConsumo in the database
        List<PrepagoConsumo> prepagoConsumoList = prepagoConsumoRepository.findAll();
        assertThat(prepagoConsumoList).hasSize(databaseSizeBeforeUpdate);
        PrepagoConsumo testPrepagoConsumo = prepagoConsumoList.get(prepagoConsumoList.size() - 1);
        assertThat(testPrepagoConsumo.getAporte()).isEqualTo(UPDATED_APORTE);
        assertThat(testPrepagoConsumo.getSaldoActual()).isEqualTo(UPDATED_SALDO_ACTUAL);
        assertThat(testPrepagoConsumo.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testPrepagoConsumo.getFechaSaldoActual()).isEqualTo(UPDATED_FECHA_SALDO_ACTUAL);

        // Validate the PrepagoConsumo in Elasticsearch
        verify(mockPrepagoConsumoSearchRepository, times(1)).save(testPrepagoConsumo);
    }

    @Test
    @Transactional
    public void updateNonExistingPrepagoConsumo() throws Exception {
        int databaseSizeBeforeUpdate = prepagoConsumoRepository.findAll().size();

        // Create the PrepagoConsumo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrepagoConsumoMockMvc.perform(put("/api/prepago-consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepagoConsumo)))
            .andExpect(status().isBadRequest());

        // Validate the PrepagoConsumo in the database
        List<PrepagoConsumo> prepagoConsumoList = prepagoConsumoRepository.findAll();
        assertThat(prepagoConsumoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrepagoConsumo in Elasticsearch
        verify(mockPrepagoConsumoSearchRepository, times(0)).save(prepagoConsumo);
    }

    @Test
    @Transactional
    public void deletePrepagoConsumo() throws Exception {
        // Initialize the database
        prepagoConsumoService.save(prepagoConsumo);

        int databaseSizeBeforeDelete = prepagoConsumoRepository.findAll().size();

        // Delete the prepagoConsumo
        restPrepagoConsumoMockMvc.perform(delete("/api/prepago-consumos/{id}", prepagoConsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrepagoConsumo> prepagoConsumoList = prepagoConsumoRepository.findAll();
        assertThat(prepagoConsumoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrepagoConsumo in Elasticsearch
        verify(mockPrepagoConsumoSearchRepository, times(1)).deleteById(prepagoConsumo.getId());
    }

    @Test
    @Transactional
    public void searchPrepagoConsumo() throws Exception {
        // Initialize the database
        prepagoConsumoService.save(prepagoConsumo);
        when(mockPrepagoConsumoSearchRepository.search(queryStringQuery("id:" + prepagoConsumo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prepagoConsumo), PageRequest.of(0, 1), 1));
        // Search the prepagoConsumo
        restPrepagoConsumoMockMvc.perform(get("/api/_search/prepago-consumos?query=id:" + prepagoConsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepagoConsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].aporte").value(hasItem(DEFAULT_APORTE)))
            .andExpect(jsonPath("$.[*].saldoActual").value(hasItem(DEFAULT_SALDO_ACTUAL)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaSaldoActual").value(hasItem(DEFAULT_FECHA_SALDO_ACTUAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrepagoConsumo.class);
        PrepagoConsumo prepagoConsumo1 = new PrepagoConsumo();
        prepagoConsumo1.setId(1L);
        PrepagoConsumo prepagoConsumo2 = new PrepagoConsumo();
        prepagoConsumo2.setId(prepagoConsumo1.getId());
        assertThat(prepagoConsumo1).isEqualTo(prepagoConsumo2);
        prepagoConsumo2.setId(2L);
        assertThat(prepagoConsumo1).isNotEqualTo(prepagoConsumo2);
        prepagoConsumo1.setId(null);
        assertThat(prepagoConsumo1).isNotEqualTo(prepagoConsumo2);
    }
}
