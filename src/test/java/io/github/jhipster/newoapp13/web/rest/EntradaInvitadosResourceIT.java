package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.EntradaInvitados;
import io.github.jhipster.newoapp13.domain.EspacioLibre;
import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import io.github.jhipster.newoapp13.domain.Invitados;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.EntradaInvitadosRepository;
import io.github.jhipster.newoapp13.repository.search.EntradaInvitadosSearchRepository;
import io.github.jhipster.newoapp13.service.EntradaInvitadosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.EntradaInvitadosCriteria;
import io.github.jhipster.newoapp13.service.EntradaInvitadosQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.newoapp13.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.newoapp13.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.newoapp13.domain.enumeration.TipoEntradad;
import io.github.jhipster.newoapp13.domain.enumeration.TipoIngresod;
/**
 * Integration tests for the {@link EntradaInvitadosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class EntradaInvitadosResourceIT {

    private static final ZonedDateTime DEFAULT_REGISTRO_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRO_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTRO_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final TipoEntradad DEFAULT_TIPO_ENTRADA = TipoEntradad.INGRESO;
    private static final TipoEntradad UPDATED_TIPO_ENTRADA = TipoEntradad.SALIDA;

    private static final TipoIngresod DEFAULT_TIPO_INGRESO = TipoIngresod.Espacio_Libre;
    private static final TipoIngresod UPDATED_TIPO_INGRESO = TipoIngresod.Reserva;

    private static final Boolean DEFAULT_TIEMPO_MAXIMO = false;
    private static final Boolean UPDATED_TIEMPO_MAXIMO = true;

    @Autowired
    private EntradaInvitadosRepository entradaInvitadosRepository;

    @Autowired
    private EntradaInvitadosService entradaInvitadosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.EntradaInvitadosSearchRepositoryMockConfiguration
     */
    @Autowired
    private EntradaInvitadosSearchRepository mockEntradaInvitadosSearchRepository;

    @Autowired
    private EntradaInvitadosQueryService entradaInvitadosQueryService;

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

    private MockMvc restEntradaInvitadosMockMvc;

    private EntradaInvitados entradaInvitados;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntradaInvitadosResource entradaInvitadosResource = new EntradaInvitadosResource(entradaInvitadosService, entradaInvitadosQueryService);
        this.restEntradaInvitadosMockMvc = MockMvcBuilders.standaloneSetup(entradaInvitadosResource)
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
    public static EntradaInvitados createEntity(EntityManager em) {
        EntradaInvitados entradaInvitados = new EntradaInvitados()
            .registroFecha(DEFAULT_REGISTRO_FECHA)
            .tipoEntrada(DEFAULT_TIPO_ENTRADA)
            .tipoIngreso(DEFAULT_TIPO_INGRESO)
            .tiempoMaximo(DEFAULT_TIEMPO_MAXIMO);
        return entradaInvitados;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntradaInvitados createUpdatedEntity(EntityManager em) {
        EntradaInvitados entradaInvitados = new EntradaInvitados()
            .registroFecha(UPDATED_REGISTRO_FECHA)
            .tipoEntrada(UPDATED_TIPO_ENTRADA)
            .tipoIngreso(UPDATED_TIPO_INGRESO)
            .tiempoMaximo(UPDATED_TIEMPO_MAXIMO);
        return entradaInvitados;
    }

    @BeforeEach
    public void initTest() {
        entradaInvitados = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntradaInvitados() throws Exception {
        int databaseSizeBeforeCreate = entradaInvitadosRepository.findAll().size();

        // Create the EntradaInvitados
        restEntradaInvitadosMockMvc.perform(post("/api/entrada-invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaInvitados)))
            .andExpect(status().isCreated());

        // Validate the EntradaInvitados in the database
        List<EntradaInvitados> entradaInvitadosList = entradaInvitadosRepository.findAll();
        assertThat(entradaInvitadosList).hasSize(databaseSizeBeforeCreate + 1);
        EntradaInvitados testEntradaInvitados = entradaInvitadosList.get(entradaInvitadosList.size() - 1);
        assertThat(testEntradaInvitados.getRegistroFecha()).isEqualTo(DEFAULT_REGISTRO_FECHA);
        assertThat(testEntradaInvitados.getTipoEntrada()).isEqualTo(DEFAULT_TIPO_ENTRADA);
        assertThat(testEntradaInvitados.getTipoIngreso()).isEqualTo(DEFAULT_TIPO_INGRESO);
        assertThat(testEntradaInvitados.isTiempoMaximo()).isEqualTo(DEFAULT_TIEMPO_MAXIMO);

        // Validate the EntradaInvitados in Elasticsearch
        verify(mockEntradaInvitadosSearchRepository, times(1)).save(testEntradaInvitados);
    }

    @Test
    @Transactional
    public void createEntradaInvitadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entradaInvitadosRepository.findAll().size();

        // Create the EntradaInvitados with an existing ID
        entradaInvitados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntradaInvitadosMockMvc.perform(post("/api/entrada-invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaInvitados)))
            .andExpect(status().isBadRequest());

        // Validate the EntradaInvitados in the database
        List<EntradaInvitados> entradaInvitadosList = entradaInvitadosRepository.findAll();
        assertThat(entradaInvitadosList).hasSize(databaseSizeBeforeCreate);

        // Validate the EntradaInvitados in Elasticsearch
        verify(mockEntradaInvitadosSearchRepository, times(0)).save(entradaInvitados);
    }


    @Test
    @Transactional
    public void checkRegistroFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = entradaInvitadosRepository.findAll().size();
        // set the field null
        entradaInvitados.setRegistroFecha(null);

        // Create the EntradaInvitados, which fails.

        restEntradaInvitadosMockMvc.perform(post("/api/entrada-invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaInvitados)))
            .andExpect(status().isBadRequest());

        List<EntradaInvitados> entradaInvitadosList = entradaInvitadosRepository.findAll();
        assertThat(entradaInvitadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitados() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entradaInvitados.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFecha").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA))))
            .andExpect(jsonPath("$.[*].tipoEntrada").value(hasItem(DEFAULT_TIPO_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].tipoIngreso").value(hasItem(DEFAULT_TIPO_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].tiempoMaximo").value(hasItem(DEFAULT_TIEMPO_MAXIMO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEntradaInvitados() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get the entradaInvitados
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados/{id}", entradaInvitados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entradaInvitados.getId().intValue()))
            .andExpect(jsonPath("$.registroFecha").value(sameInstant(DEFAULT_REGISTRO_FECHA)))
            .andExpect(jsonPath("$.tipoEntrada").value(DEFAULT_TIPO_ENTRADA.toString()))
            .andExpect(jsonPath("$.tipoIngreso").value(DEFAULT_TIPO_INGRESO.toString()))
            .andExpect(jsonPath("$.tiempoMaximo").value(DEFAULT_TIEMPO_MAXIMO.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha equals to DEFAULT_REGISTRO_FECHA
        defaultEntradaInvitadosShouldBeFound("registroFecha.equals=" + DEFAULT_REGISTRO_FECHA);

        // Get all the entradaInvitadosList where registroFecha equals to UPDATED_REGISTRO_FECHA
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.equals=" + UPDATED_REGISTRO_FECHA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsInShouldWork() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha in DEFAULT_REGISTRO_FECHA or UPDATED_REGISTRO_FECHA
        defaultEntradaInvitadosShouldBeFound("registroFecha.in=" + DEFAULT_REGISTRO_FECHA + "," + UPDATED_REGISTRO_FECHA);

        // Get all the entradaInvitadosList where registroFecha equals to UPDATED_REGISTRO_FECHA
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.in=" + UPDATED_REGISTRO_FECHA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha is not null
        defaultEntradaInvitadosShouldBeFound("registroFecha.specified=true");

        // Get all the entradaInvitadosList where registroFecha is null
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha is greater than or equal to DEFAULT_REGISTRO_FECHA
        defaultEntradaInvitadosShouldBeFound("registroFecha.greaterThanOrEqual=" + DEFAULT_REGISTRO_FECHA);

        // Get all the entradaInvitadosList where registroFecha is greater than or equal to UPDATED_REGISTRO_FECHA
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.greaterThanOrEqual=" + UPDATED_REGISTRO_FECHA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha is less than or equal to DEFAULT_REGISTRO_FECHA
        defaultEntradaInvitadosShouldBeFound("registroFecha.lessThanOrEqual=" + DEFAULT_REGISTRO_FECHA);

        // Get all the entradaInvitadosList where registroFecha is less than or equal to SMALLER_REGISTRO_FECHA
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.lessThanOrEqual=" + SMALLER_REGISTRO_FECHA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha is less than DEFAULT_REGISTRO_FECHA
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.lessThan=" + DEFAULT_REGISTRO_FECHA);

        // Get all the entradaInvitadosList where registroFecha is less than UPDATED_REGISTRO_FECHA
        defaultEntradaInvitadosShouldBeFound("registroFecha.lessThan=" + UPDATED_REGISTRO_FECHA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByRegistroFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where registroFecha is greater than DEFAULT_REGISTRO_FECHA
        defaultEntradaInvitadosShouldNotBeFound("registroFecha.greaterThan=" + DEFAULT_REGISTRO_FECHA);

        // Get all the entradaInvitadosList where registroFecha is greater than SMALLER_REGISTRO_FECHA
        defaultEntradaInvitadosShouldBeFound("registroFecha.greaterThan=" + SMALLER_REGISTRO_FECHA);
    }


    @Test
    @Transactional
    public void getAllEntradaInvitadosByTipoEntradaIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tipoEntrada equals to DEFAULT_TIPO_ENTRADA
        defaultEntradaInvitadosShouldBeFound("tipoEntrada.equals=" + DEFAULT_TIPO_ENTRADA);

        // Get all the entradaInvitadosList where tipoEntrada equals to UPDATED_TIPO_ENTRADA
        defaultEntradaInvitadosShouldNotBeFound("tipoEntrada.equals=" + UPDATED_TIPO_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTipoEntradaIsInShouldWork() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tipoEntrada in DEFAULT_TIPO_ENTRADA or UPDATED_TIPO_ENTRADA
        defaultEntradaInvitadosShouldBeFound("tipoEntrada.in=" + DEFAULT_TIPO_ENTRADA + "," + UPDATED_TIPO_ENTRADA);

        // Get all the entradaInvitadosList where tipoEntrada equals to UPDATED_TIPO_ENTRADA
        defaultEntradaInvitadosShouldNotBeFound("tipoEntrada.in=" + UPDATED_TIPO_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTipoEntradaIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tipoEntrada is not null
        defaultEntradaInvitadosShouldBeFound("tipoEntrada.specified=true");

        // Get all the entradaInvitadosList where tipoEntrada is null
        defaultEntradaInvitadosShouldNotBeFound("tipoEntrada.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTipoIngresoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tipoIngreso equals to DEFAULT_TIPO_INGRESO
        defaultEntradaInvitadosShouldBeFound("tipoIngreso.equals=" + DEFAULT_TIPO_INGRESO);

        // Get all the entradaInvitadosList where tipoIngreso equals to UPDATED_TIPO_INGRESO
        defaultEntradaInvitadosShouldNotBeFound("tipoIngreso.equals=" + UPDATED_TIPO_INGRESO);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTipoIngresoIsInShouldWork() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tipoIngreso in DEFAULT_TIPO_INGRESO or UPDATED_TIPO_INGRESO
        defaultEntradaInvitadosShouldBeFound("tipoIngreso.in=" + DEFAULT_TIPO_INGRESO + "," + UPDATED_TIPO_INGRESO);

        // Get all the entradaInvitadosList where tipoIngreso equals to UPDATED_TIPO_INGRESO
        defaultEntradaInvitadosShouldNotBeFound("tipoIngreso.in=" + UPDATED_TIPO_INGRESO);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTipoIngresoIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tipoIngreso is not null
        defaultEntradaInvitadosShouldBeFound("tipoIngreso.specified=true");

        // Get all the entradaInvitadosList where tipoIngreso is null
        defaultEntradaInvitadosShouldNotBeFound("tipoIngreso.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTiempoMaximoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tiempoMaximo equals to DEFAULT_TIEMPO_MAXIMO
        defaultEntradaInvitadosShouldBeFound("tiempoMaximo.equals=" + DEFAULT_TIEMPO_MAXIMO);

        // Get all the entradaInvitadosList where tiempoMaximo equals to UPDATED_TIEMPO_MAXIMO
        defaultEntradaInvitadosShouldNotBeFound("tiempoMaximo.equals=" + UPDATED_TIEMPO_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTiempoMaximoIsInShouldWork() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tiempoMaximo in DEFAULT_TIEMPO_MAXIMO or UPDATED_TIEMPO_MAXIMO
        defaultEntradaInvitadosShouldBeFound("tiempoMaximo.in=" + DEFAULT_TIEMPO_MAXIMO + "," + UPDATED_TIEMPO_MAXIMO);

        // Get all the entradaInvitadosList where tiempoMaximo equals to UPDATED_TIEMPO_MAXIMO
        defaultEntradaInvitadosShouldNotBeFound("tiempoMaximo.in=" + UPDATED_TIEMPO_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByTiempoMaximoIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);

        // Get all the entradaInvitadosList where tiempoMaximo is not null
        defaultEntradaInvitadosShouldBeFound("tiempoMaximo.specified=true");

        // Get all the entradaInvitadosList where tiempoMaximo is null
        defaultEntradaInvitadosShouldNotBeFound("tiempoMaximo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntradaInvitadosByEspacioIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        EspacioLibre espacio = EspacioLibreResourceIT.createEntity(em);
        em.persist(espacio);
        em.flush();
        entradaInvitados.setEspacio(espacio);
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        Long espacioId = espacio.getId();

        // Get all the entradaInvitadosList where espacio equals to espacioId
        defaultEntradaInvitadosShouldBeFound("espacioId.equals=" + espacioId);

        // Get all the entradaInvitadosList where espacio equals to espacioId + 1
        defaultEntradaInvitadosShouldNotBeFound("espacioId.equals=" + (espacioId + 1));
    }


    @Test
    @Transactional
    public void getAllEntradaInvitadosByEspacioReservaIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        EspaciosReserva espacioReserva = EspaciosReservaResourceIT.createEntity(em);
        em.persist(espacioReserva);
        em.flush();
        entradaInvitados.setEspacioReserva(espacioReserva);
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        Long espacioReservaId = espacioReserva.getId();

        // Get all the entradaInvitadosList where espacioReserva equals to espacioReservaId
        defaultEntradaInvitadosShouldBeFound("espacioReservaId.equals=" + espacioReservaId);

        // Get all the entradaInvitadosList where espacioReserva equals to espacioReservaId + 1
        defaultEntradaInvitadosShouldNotBeFound("espacioReservaId.equals=" + (espacioReservaId + 1));
    }


    @Test
    @Transactional
    public void getAllEntradaInvitadosByInvitadoIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        Invitados invitado = InvitadosResourceIT.createEntity(em);
        em.persist(invitado);
        em.flush();
        entradaInvitados.setInvitado(invitado);
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        Long invitadoId = invitado.getId();

        // Get all the entradaInvitadosList where invitado equals to invitadoId
        defaultEntradaInvitadosShouldBeFound("invitadoId.equals=" + invitadoId);

        // Get all the entradaInvitadosList where invitado equals to invitadoId + 1
        defaultEntradaInvitadosShouldNotBeFound("invitadoId.equals=" + (invitadoId + 1));
    }


    @Test
    @Transactional
    public void getAllEntradaInvitadosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        entradaInvitados.setMiembro(miembro);
        entradaInvitadosRepository.saveAndFlush(entradaInvitados);
        Long miembroId = miembro.getId();

        // Get all the entradaInvitadosList where miembro equals to miembroId
        defaultEntradaInvitadosShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the entradaInvitadosList where miembro equals to miembroId + 1
        defaultEntradaInvitadosShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEntradaInvitadosShouldBeFound(String filter) throws Exception {
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entradaInvitados.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFecha").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA))))
            .andExpect(jsonPath("$.[*].tipoEntrada").value(hasItem(DEFAULT_TIPO_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].tipoIngreso").value(hasItem(DEFAULT_TIPO_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].tiempoMaximo").value(hasItem(DEFAULT_TIEMPO_MAXIMO.booleanValue())));

        // Check, that the count call also returns 1
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEntradaInvitadosShouldNotBeFound(String filter) throws Exception {
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEntradaInvitados() throws Exception {
        // Get the entradaInvitados
        restEntradaInvitadosMockMvc.perform(get("/api/entrada-invitados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntradaInvitados() throws Exception {
        // Initialize the database
        entradaInvitadosService.save(entradaInvitados);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEntradaInvitadosSearchRepository);

        int databaseSizeBeforeUpdate = entradaInvitadosRepository.findAll().size();

        // Update the entradaInvitados
        EntradaInvitados updatedEntradaInvitados = entradaInvitadosRepository.findById(entradaInvitados.getId()).get();
        // Disconnect from session so that the updates on updatedEntradaInvitados are not directly saved in db
        em.detach(updatedEntradaInvitados);
        updatedEntradaInvitados
            .registroFecha(UPDATED_REGISTRO_FECHA)
            .tipoEntrada(UPDATED_TIPO_ENTRADA)
            .tipoIngreso(UPDATED_TIPO_INGRESO)
            .tiempoMaximo(UPDATED_TIEMPO_MAXIMO);

        restEntradaInvitadosMockMvc.perform(put("/api/entrada-invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntradaInvitados)))
            .andExpect(status().isOk());

        // Validate the EntradaInvitados in the database
        List<EntradaInvitados> entradaInvitadosList = entradaInvitadosRepository.findAll();
        assertThat(entradaInvitadosList).hasSize(databaseSizeBeforeUpdate);
        EntradaInvitados testEntradaInvitados = entradaInvitadosList.get(entradaInvitadosList.size() - 1);
        assertThat(testEntradaInvitados.getRegistroFecha()).isEqualTo(UPDATED_REGISTRO_FECHA);
        assertThat(testEntradaInvitados.getTipoEntrada()).isEqualTo(UPDATED_TIPO_ENTRADA);
        assertThat(testEntradaInvitados.getTipoIngreso()).isEqualTo(UPDATED_TIPO_INGRESO);
        assertThat(testEntradaInvitados.isTiempoMaximo()).isEqualTo(UPDATED_TIEMPO_MAXIMO);

        // Validate the EntradaInvitados in Elasticsearch
        verify(mockEntradaInvitadosSearchRepository, times(1)).save(testEntradaInvitados);
    }

    @Test
    @Transactional
    public void updateNonExistingEntradaInvitados() throws Exception {
        int databaseSizeBeforeUpdate = entradaInvitadosRepository.findAll().size();

        // Create the EntradaInvitados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntradaInvitadosMockMvc.perform(put("/api/entrada-invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entradaInvitados)))
            .andExpect(status().isBadRequest());

        // Validate the EntradaInvitados in the database
        List<EntradaInvitados> entradaInvitadosList = entradaInvitadosRepository.findAll();
        assertThat(entradaInvitadosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EntradaInvitados in Elasticsearch
        verify(mockEntradaInvitadosSearchRepository, times(0)).save(entradaInvitados);
    }

    @Test
    @Transactional
    public void deleteEntradaInvitados() throws Exception {
        // Initialize the database
        entradaInvitadosService.save(entradaInvitados);

        int databaseSizeBeforeDelete = entradaInvitadosRepository.findAll().size();

        // Delete the entradaInvitados
        restEntradaInvitadosMockMvc.perform(delete("/api/entrada-invitados/{id}", entradaInvitados.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntradaInvitados> entradaInvitadosList = entradaInvitadosRepository.findAll();
        assertThat(entradaInvitadosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EntradaInvitados in Elasticsearch
        verify(mockEntradaInvitadosSearchRepository, times(1)).deleteById(entradaInvitados.getId());
    }

    @Test
    @Transactional
    public void searchEntradaInvitados() throws Exception {
        // Initialize the database
        entradaInvitadosService.save(entradaInvitados);
        when(mockEntradaInvitadosSearchRepository.search(queryStringQuery("id:" + entradaInvitados.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(entradaInvitados), PageRequest.of(0, 1), 1));
        // Search the entradaInvitados
        restEntradaInvitadosMockMvc.perform(get("/api/_search/entrada-invitados?query=id:" + entradaInvitados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entradaInvitados.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFecha").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA))))
            .andExpect(jsonPath("$.[*].tipoEntrada").value(hasItem(DEFAULT_TIPO_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].tipoIngreso").value(hasItem(DEFAULT_TIPO_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].tiempoMaximo").value(hasItem(DEFAULT_TIEMPO_MAXIMO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntradaInvitados.class);
        EntradaInvitados entradaInvitados1 = new EntradaInvitados();
        entradaInvitados1.setId(1L);
        EntradaInvitados entradaInvitados2 = new EntradaInvitados();
        entradaInvitados2.setId(entradaInvitados1.getId());
        assertThat(entradaInvitados1).isEqualTo(entradaInvitados2);
        entradaInvitados2.setId(2L);
        assertThat(entradaInvitados1).isNotEqualTo(entradaInvitados2);
        entradaInvitados1.setId(null);
        assertThat(entradaInvitados1).isNotEqualTo(entradaInvitados2);
    }
}
