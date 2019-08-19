package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.RegistroFacturacion;
import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.RegistroFacturacionRepository;
import io.github.jhipster.newoapp13.repository.search.RegistroFacturacionSearchRepository;
import io.github.jhipster.newoapp13.service.RegistroFacturacionService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.RegistroFacturacionCriteria;
import io.github.jhipster.newoapp13.service.RegistroFacturacionQueryService;

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

/**
 * Integration tests for the {@link RegistroFacturacionResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class RegistroFacturacionResourceIT {

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;
    private static final Integer SMALLER_VALOR = 1 - 1;

    private static final Instant DEFAULT_FECHA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_REGISTRO = Instant.ofEpochMilli(-1L);

    private static final LocalDate DEFAULT_FECHA_FACTURACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FACTURACION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_FACTURACION = LocalDate.ofEpochDay(-1L);

    @Autowired
    private RegistroFacturacionRepository registroFacturacionRepository;

    @Autowired
    private RegistroFacturacionService registroFacturacionService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.RegistroFacturacionSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegistroFacturacionSearchRepository mockRegistroFacturacionSearchRepository;

    @Autowired
    private RegistroFacturacionQueryService registroFacturacionQueryService;

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

    private MockMvc restRegistroFacturacionMockMvc;

    private RegistroFacturacion registroFacturacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistroFacturacionResource registroFacturacionResource = new RegistroFacturacionResource(registroFacturacionService, registroFacturacionQueryService);
        this.restRegistroFacturacionMockMvc = MockMvcBuilders.standaloneSetup(registroFacturacionResource)
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
    public static RegistroFacturacion createEntity(EntityManager em) {
        RegistroFacturacion registroFacturacion = new RegistroFacturacion()
            .valor(DEFAULT_VALOR)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .fechaFacturacion(DEFAULT_FECHA_FACTURACION);
        return registroFacturacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroFacturacion createUpdatedEntity(EntityManager em) {
        RegistroFacturacion registroFacturacion = new RegistroFacturacion()
            .valor(UPDATED_VALOR)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaFacturacion(UPDATED_FECHA_FACTURACION);
        return registroFacturacion;
    }

    @BeforeEach
    public void initTest() {
        registroFacturacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistroFacturacion() throws Exception {
        int databaseSizeBeforeCreate = registroFacturacionRepository.findAll().size();

        // Create the RegistroFacturacion
        restRegistroFacturacionMockMvc.perform(post("/api/registro-facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroFacturacion)))
            .andExpect(status().isCreated());

        // Validate the RegistroFacturacion in the database
        List<RegistroFacturacion> registroFacturacionList = registroFacturacionRepository.findAll();
        assertThat(registroFacturacionList).hasSize(databaseSizeBeforeCreate + 1);
        RegistroFacturacion testRegistroFacturacion = registroFacturacionList.get(registroFacturacionList.size() - 1);
        assertThat(testRegistroFacturacion.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testRegistroFacturacion.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testRegistroFacturacion.getFechaFacturacion()).isEqualTo(DEFAULT_FECHA_FACTURACION);

        // Validate the RegistroFacturacion in Elasticsearch
        verify(mockRegistroFacturacionSearchRepository, times(1)).save(testRegistroFacturacion);
    }

    @Test
    @Transactional
    public void createRegistroFacturacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registroFacturacionRepository.findAll().size();

        // Create the RegistroFacturacion with an existing ID
        registroFacturacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroFacturacionMockMvc.perform(post("/api/registro-facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroFacturacion)))
            .andExpect(status().isBadRequest());

        // Validate the RegistroFacturacion in the database
        List<RegistroFacturacion> registroFacturacionList = registroFacturacionRepository.findAll();
        assertThat(registroFacturacionList).hasSize(databaseSizeBeforeCreate);

        // Validate the RegistroFacturacion in Elasticsearch
        verify(mockRegistroFacturacionSearchRepository, times(0)).save(registroFacturacion);
    }


    @Test
    @Transactional
    public void getAllRegistroFacturacions() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroFacturacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaFacturacion").value(hasItem(DEFAULT_FECHA_FACTURACION.toString())));
    }
    
    @Test
    @Transactional
    public void getRegistroFacturacion() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get the registroFacturacion
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions/{id}", registroFacturacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registroFacturacion.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.fechaFacturacion").value(DEFAULT_FECHA_FACTURACION.toString()));
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor equals to DEFAULT_VALOR
        defaultRegistroFacturacionShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the registroFacturacionList where valor equals to UPDATED_VALOR
        defaultRegistroFacturacionShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsInShouldWork() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultRegistroFacturacionShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the registroFacturacionList where valor equals to UPDATED_VALOR
        defaultRegistroFacturacionShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor is not null
        defaultRegistroFacturacionShouldBeFound("valor.specified=true");

        // Get all the registroFacturacionList where valor is null
        defaultRegistroFacturacionShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor is greater than or equal to DEFAULT_VALOR
        defaultRegistroFacturacionShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the registroFacturacionList where valor is greater than or equal to UPDATED_VALOR
        defaultRegistroFacturacionShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor is less than or equal to DEFAULT_VALOR
        defaultRegistroFacturacionShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the registroFacturacionList where valor is less than or equal to SMALLER_VALOR
        defaultRegistroFacturacionShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor is less than DEFAULT_VALOR
        defaultRegistroFacturacionShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the registroFacturacionList where valor is less than UPDATED_VALOR
        defaultRegistroFacturacionShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where valor is greater than DEFAULT_VALOR
        defaultRegistroFacturacionShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the registroFacturacionList where valor is greater than SMALLER_VALOR
        defaultRegistroFacturacionShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaRegistro equals to DEFAULT_FECHA_REGISTRO
        defaultRegistroFacturacionShouldBeFound("fechaRegistro.equals=" + DEFAULT_FECHA_REGISTRO);

        // Get all the registroFacturacionList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultRegistroFacturacionShouldNotBeFound("fechaRegistro.equals=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaRegistro in DEFAULT_FECHA_REGISTRO or UPDATED_FECHA_REGISTRO
        defaultRegistroFacturacionShouldBeFound("fechaRegistro.in=" + DEFAULT_FECHA_REGISTRO + "," + UPDATED_FECHA_REGISTRO);

        // Get all the registroFacturacionList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultRegistroFacturacionShouldNotBeFound("fechaRegistro.in=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaRegistro is not null
        defaultRegistroFacturacionShouldBeFound("fechaRegistro.specified=true");

        // Get all the registroFacturacionList where fechaRegistro is null
        defaultRegistroFacturacionShouldNotBeFound("fechaRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion equals to DEFAULT_FECHA_FACTURACION
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.equals=" + DEFAULT_FECHA_FACTURACION);

        // Get all the registroFacturacionList where fechaFacturacion equals to UPDATED_FECHA_FACTURACION
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.equals=" + UPDATED_FECHA_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsInShouldWork() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion in DEFAULT_FECHA_FACTURACION or UPDATED_FECHA_FACTURACION
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.in=" + DEFAULT_FECHA_FACTURACION + "," + UPDATED_FECHA_FACTURACION);

        // Get all the registroFacturacionList where fechaFacturacion equals to UPDATED_FECHA_FACTURACION
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.in=" + UPDATED_FECHA_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion is not null
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.specified=true");

        // Get all the registroFacturacionList where fechaFacturacion is null
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion is greater than or equal to DEFAULT_FECHA_FACTURACION
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.greaterThanOrEqual=" + DEFAULT_FECHA_FACTURACION);

        // Get all the registroFacturacionList where fechaFacturacion is greater than or equal to UPDATED_FECHA_FACTURACION
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.greaterThanOrEqual=" + UPDATED_FECHA_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion is less than or equal to DEFAULT_FECHA_FACTURACION
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.lessThanOrEqual=" + DEFAULT_FECHA_FACTURACION);

        // Get all the registroFacturacionList where fechaFacturacion is less than or equal to SMALLER_FECHA_FACTURACION
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.lessThanOrEqual=" + SMALLER_FECHA_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsLessThanSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion is less than DEFAULT_FECHA_FACTURACION
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.lessThan=" + DEFAULT_FECHA_FACTURACION);

        // Get all the registroFacturacionList where fechaFacturacion is less than UPDATED_FECHA_FACTURACION
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.lessThan=" + UPDATED_FECHA_FACTURACION);
    }

    @Test
    @Transactional
    public void getAllRegistroFacturacionsByFechaFacturacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);

        // Get all the registroFacturacionList where fechaFacturacion is greater than DEFAULT_FECHA_FACTURACION
        defaultRegistroFacturacionShouldNotBeFound("fechaFacturacion.greaterThan=" + DEFAULT_FECHA_FACTURACION);

        // Get all the registroFacturacionList where fechaFacturacion is greater than SMALLER_FECHA_FACTURACION
        defaultRegistroFacturacionShouldBeFound("fechaFacturacion.greaterThan=" + SMALLER_FECHA_FACTURACION);
    }


    @Test
    @Transactional
    public void getAllRegistroFacturacionsByTipoRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);
        TipoRegistroCompra tipoRegistro = TipoRegistroCompraResourceIT.createEntity(em);
        em.persist(tipoRegistro);
        em.flush();
        registroFacturacion.setTipoRegistro(tipoRegistro);
        registroFacturacionRepository.saveAndFlush(registroFacturacion);
        Long tipoRegistroId = tipoRegistro.getId();

        // Get all the registroFacturacionList where tipoRegistro equals to tipoRegistroId
        defaultRegistroFacturacionShouldBeFound("tipoRegistroId.equals=" + tipoRegistroId);

        // Get all the registroFacturacionList where tipoRegistro equals to tipoRegistroId + 1
        defaultRegistroFacturacionShouldNotBeFound("tipoRegistroId.equals=" + (tipoRegistroId + 1));
    }


    @Test
    @Transactional
    public void getAllRegistroFacturacionsByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        registroFacturacionRepository.saveAndFlush(registroFacturacion);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        registroFacturacion.setMiembro(miembro);
        registroFacturacionRepository.saveAndFlush(registroFacturacion);
        Long miembroId = miembro.getId();

        // Get all the registroFacturacionList where miembro equals to miembroId
        defaultRegistroFacturacionShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the registroFacturacionList where miembro equals to miembroId + 1
        defaultRegistroFacturacionShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegistroFacturacionShouldBeFound(String filter) throws Exception {
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroFacturacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaFacturacion").value(hasItem(DEFAULT_FECHA_FACTURACION.toString())));

        // Check, that the count call also returns 1
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegistroFacturacionShouldNotBeFound(String filter) throws Exception {
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegistroFacturacion() throws Exception {
        // Get the registroFacturacion
        restRegistroFacturacionMockMvc.perform(get("/api/registro-facturacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistroFacturacion() throws Exception {
        // Initialize the database
        registroFacturacionService.save(registroFacturacion);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRegistroFacturacionSearchRepository);

        int databaseSizeBeforeUpdate = registroFacturacionRepository.findAll().size();

        // Update the registroFacturacion
        RegistroFacturacion updatedRegistroFacturacion = registroFacturacionRepository.findById(registroFacturacion.getId()).get();
        // Disconnect from session so that the updates on updatedRegistroFacturacion are not directly saved in db
        em.detach(updatedRegistroFacturacion);
        updatedRegistroFacturacion
            .valor(UPDATED_VALOR)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaFacturacion(UPDATED_FECHA_FACTURACION);

        restRegistroFacturacionMockMvc.perform(put("/api/registro-facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistroFacturacion)))
            .andExpect(status().isOk());

        // Validate the RegistroFacturacion in the database
        List<RegistroFacturacion> registroFacturacionList = registroFacturacionRepository.findAll();
        assertThat(registroFacturacionList).hasSize(databaseSizeBeforeUpdate);
        RegistroFacturacion testRegistroFacturacion = registroFacturacionList.get(registroFacturacionList.size() - 1);
        assertThat(testRegistroFacturacion.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testRegistroFacturacion.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testRegistroFacturacion.getFechaFacturacion()).isEqualTo(UPDATED_FECHA_FACTURACION);

        // Validate the RegistroFacturacion in Elasticsearch
        verify(mockRegistroFacturacionSearchRepository, times(1)).save(testRegistroFacturacion);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistroFacturacion() throws Exception {
        int databaseSizeBeforeUpdate = registroFacturacionRepository.findAll().size();

        // Create the RegistroFacturacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroFacturacionMockMvc.perform(put("/api/registro-facturacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroFacturacion)))
            .andExpect(status().isBadRequest());

        // Validate the RegistroFacturacion in the database
        List<RegistroFacturacion> registroFacturacionList = registroFacturacionRepository.findAll();
        assertThat(registroFacturacionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RegistroFacturacion in Elasticsearch
        verify(mockRegistroFacturacionSearchRepository, times(0)).save(registroFacturacion);
    }

    @Test
    @Transactional
    public void deleteRegistroFacturacion() throws Exception {
        // Initialize the database
        registroFacturacionService.save(registroFacturacion);

        int databaseSizeBeforeDelete = registroFacturacionRepository.findAll().size();

        // Delete the registroFacturacion
        restRegistroFacturacionMockMvc.perform(delete("/api/registro-facturacions/{id}", registroFacturacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistroFacturacion> registroFacturacionList = registroFacturacionRepository.findAll();
        assertThat(registroFacturacionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RegistroFacturacion in Elasticsearch
        verify(mockRegistroFacturacionSearchRepository, times(1)).deleteById(registroFacturacion.getId());
    }

    @Test
    @Transactional
    public void searchRegistroFacturacion() throws Exception {
        // Initialize the database
        registroFacturacionService.save(registroFacturacion);
        when(mockRegistroFacturacionSearchRepository.search(queryStringQuery("id:" + registroFacturacion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(registroFacturacion), PageRequest.of(0, 1), 1));
        // Search the registroFacturacion
        restRegistroFacturacionMockMvc.perform(get("/api/_search/registro-facturacions?query=id:" + registroFacturacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroFacturacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaFacturacion").value(hasItem(DEFAULT_FECHA_FACTURACION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroFacturacion.class);
        RegistroFacturacion registroFacturacion1 = new RegistroFacturacion();
        registroFacturacion1.setId(1L);
        RegistroFacturacion registroFacturacion2 = new RegistroFacturacion();
        registroFacturacion2.setId(registroFacturacion1.getId());
        assertThat(registroFacturacion1).isEqualTo(registroFacturacion2);
        registroFacturacion2.setId(2L);
        assertThat(registroFacturacion1).isNotEqualTo(registroFacturacion2);
        registroFacturacion1.setId(null);
        assertThat(registroFacturacion1).isNotEqualTo(registroFacturacion2);
    }
}
