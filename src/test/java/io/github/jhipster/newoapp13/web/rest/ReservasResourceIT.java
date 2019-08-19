package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Reservas;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import io.github.jhipster.newoapp13.repository.ReservasRepository;
import io.github.jhipster.newoapp13.repository.search.ReservasSearchRepository;
import io.github.jhipster.newoapp13.service.ReservasService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.ReservasCriteria;
import io.github.jhipster.newoapp13.service.ReservasQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.EstadoReservad;
/**
 * Integration tests for the {@link ReservasResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class ReservasResourceIT {

    private static final ZonedDateTime DEFAULT_REGISTRO_FECHA_ENTRADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRO_FECHA_ENTRADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTRO_FECHA_ENTRADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_REGISTRO_FECHA_SALIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRO_FECHA_SALIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_REGISTRO_FECHA_SALIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final EstadoReservad DEFAULT_ESTADO_RESERVA = EstadoReservad.Cancelada;
    private static final EstadoReservad UPDATED_ESTADO_RESERVA = EstadoReservad.Activa;

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private ReservasService reservasService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.ReservasSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReservasSearchRepository mockReservasSearchRepository;

    @Autowired
    private ReservasQueryService reservasQueryService;

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

    private MockMvc restReservasMockMvc;

    private Reservas reservas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservasResource reservasResource = new ReservasResource(reservasService, reservasQueryService);
        this.restReservasMockMvc = MockMvcBuilders.standaloneSetup(reservasResource)
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
    public static Reservas createEntity(EntityManager em) {
        Reservas reservas = new Reservas()
            .registroFechaEntrada(DEFAULT_REGISTRO_FECHA_ENTRADA)
            .registroFechaSalida(DEFAULT_REGISTRO_FECHA_SALIDA)
            .estadoReserva(DEFAULT_ESTADO_RESERVA);
        return reservas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservas createUpdatedEntity(EntityManager em) {
        Reservas reservas = new Reservas()
            .registroFechaEntrada(UPDATED_REGISTRO_FECHA_ENTRADA)
            .registroFechaSalida(UPDATED_REGISTRO_FECHA_SALIDA)
            .estadoReserva(UPDATED_ESTADO_RESERVA);
        return reservas;
    }

    @BeforeEach
    public void initTest() {
        reservas = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservas() throws Exception {
        int databaseSizeBeforeCreate = reservasRepository.findAll().size();

        // Create the Reservas
        restReservasMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservas)))
            .andExpect(status().isCreated());

        // Validate the Reservas in the database
        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeCreate + 1);
        Reservas testReservas = reservasList.get(reservasList.size() - 1);
        assertThat(testReservas.getRegistroFechaEntrada()).isEqualTo(DEFAULT_REGISTRO_FECHA_ENTRADA);
        assertThat(testReservas.getRegistroFechaSalida()).isEqualTo(DEFAULT_REGISTRO_FECHA_SALIDA);
        assertThat(testReservas.getEstadoReserva()).isEqualTo(DEFAULT_ESTADO_RESERVA);

        // Validate the Reservas in Elasticsearch
        verify(mockReservasSearchRepository, times(1)).save(testReservas);
    }

    @Test
    @Transactional
    public void createReservasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservasRepository.findAll().size();

        // Create the Reservas with an existing ID
        reservas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservasMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservas)))
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeCreate);

        // Validate the Reservas in Elasticsearch
        verify(mockReservasSearchRepository, times(0)).save(reservas);
    }


    @Test
    @Transactional
    public void checkRegistroFechaEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservasRepository.findAll().size();
        // set the field null
        reservas.setRegistroFechaEntrada(null);

        // Create the Reservas, which fails.

        restReservasMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservas)))
            .andExpect(status().isBadRequest());

        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistroFechaSalidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservasRepository.findAll().size();
        // set the field null
        reservas.setRegistroFechaSalida(null);

        // Create the Reservas, which fails.

        restReservasMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservas)))
            .andExpect(status().isBadRequest());

        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReservas() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList
        restReservasMockMvc.perform(get("/api/reservas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservas.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFechaEntrada").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_ENTRADA))))
            .andExpect(jsonPath("$.[*].registroFechaSalida").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_SALIDA))))
            .andExpect(jsonPath("$.[*].estadoReserva").value(hasItem(DEFAULT_ESTADO_RESERVA.toString())));
    }
    
    @Test
    @Transactional
    public void getReservas() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get the reservas
        restReservasMockMvc.perform(get("/api/reservas/{id}", reservas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservas.getId().intValue()))
            .andExpect(jsonPath("$.registroFechaEntrada").value(sameInstant(DEFAULT_REGISTRO_FECHA_ENTRADA)))
            .andExpect(jsonPath("$.registroFechaSalida").value(sameInstant(DEFAULT_REGISTRO_FECHA_SALIDA)))
            .andExpect(jsonPath("$.estadoReserva").value(DEFAULT_ESTADO_RESERVA.toString()));
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada equals to DEFAULT_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldBeFound("registroFechaEntrada.equals=" + DEFAULT_REGISTRO_FECHA_ENTRADA);

        // Get all the reservasList where registroFechaEntrada equals to UPDATED_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldNotBeFound("registroFechaEntrada.equals=" + UPDATED_REGISTRO_FECHA_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsInShouldWork() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada in DEFAULT_REGISTRO_FECHA_ENTRADA or UPDATED_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldBeFound("registroFechaEntrada.in=" + DEFAULT_REGISTRO_FECHA_ENTRADA + "," + UPDATED_REGISTRO_FECHA_ENTRADA);

        // Get all the reservasList where registroFechaEntrada equals to UPDATED_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldNotBeFound("registroFechaEntrada.in=" + UPDATED_REGISTRO_FECHA_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada is not null
        defaultReservasShouldBeFound("registroFechaEntrada.specified=true");

        // Get all the reservasList where registroFechaEntrada is null
        defaultReservasShouldNotBeFound("registroFechaEntrada.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada is greater than or equal to DEFAULT_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldBeFound("registroFechaEntrada.greaterThanOrEqual=" + DEFAULT_REGISTRO_FECHA_ENTRADA);

        // Get all the reservasList where registroFechaEntrada is greater than or equal to UPDATED_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldNotBeFound("registroFechaEntrada.greaterThanOrEqual=" + UPDATED_REGISTRO_FECHA_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada is less than or equal to DEFAULT_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldBeFound("registroFechaEntrada.lessThanOrEqual=" + DEFAULT_REGISTRO_FECHA_ENTRADA);

        // Get all the reservasList where registroFechaEntrada is less than or equal to SMALLER_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldNotBeFound("registroFechaEntrada.lessThanOrEqual=" + SMALLER_REGISTRO_FECHA_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsLessThanSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada is less than DEFAULT_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldNotBeFound("registroFechaEntrada.lessThan=" + DEFAULT_REGISTRO_FECHA_ENTRADA);

        // Get all the reservasList where registroFechaEntrada is less than UPDATED_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldBeFound("registroFechaEntrada.lessThan=" + UPDATED_REGISTRO_FECHA_ENTRADA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaEntradaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaEntrada is greater than DEFAULT_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldNotBeFound("registroFechaEntrada.greaterThan=" + DEFAULT_REGISTRO_FECHA_ENTRADA);

        // Get all the reservasList where registroFechaEntrada is greater than SMALLER_REGISTRO_FECHA_ENTRADA
        defaultReservasShouldBeFound("registroFechaEntrada.greaterThan=" + SMALLER_REGISTRO_FECHA_ENTRADA);
    }


    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida equals to DEFAULT_REGISTRO_FECHA_SALIDA
        defaultReservasShouldBeFound("registroFechaSalida.equals=" + DEFAULT_REGISTRO_FECHA_SALIDA);

        // Get all the reservasList where registroFechaSalida equals to UPDATED_REGISTRO_FECHA_SALIDA
        defaultReservasShouldNotBeFound("registroFechaSalida.equals=" + UPDATED_REGISTRO_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsInShouldWork() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida in DEFAULT_REGISTRO_FECHA_SALIDA or UPDATED_REGISTRO_FECHA_SALIDA
        defaultReservasShouldBeFound("registroFechaSalida.in=" + DEFAULT_REGISTRO_FECHA_SALIDA + "," + UPDATED_REGISTRO_FECHA_SALIDA);

        // Get all the reservasList where registroFechaSalida equals to UPDATED_REGISTRO_FECHA_SALIDA
        defaultReservasShouldNotBeFound("registroFechaSalida.in=" + UPDATED_REGISTRO_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida is not null
        defaultReservasShouldBeFound("registroFechaSalida.specified=true");

        // Get all the reservasList where registroFechaSalida is null
        defaultReservasShouldNotBeFound("registroFechaSalida.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida is greater than or equal to DEFAULT_REGISTRO_FECHA_SALIDA
        defaultReservasShouldBeFound("registroFechaSalida.greaterThanOrEqual=" + DEFAULT_REGISTRO_FECHA_SALIDA);

        // Get all the reservasList where registroFechaSalida is greater than or equal to UPDATED_REGISTRO_FECHA_SALIDA
        defaultReservasShouldNotBeFound("registroFechaSalida.greaterThanOrEqual=" + UPDATED_REGISTRO_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida is less than or equal to DEFAULT_REGISTRO_FECHA_SALIDA
        defaultReservasShouldBeFound("registroFechaSalida.lessThanOrEqual=" + DEFAULT_REGISTRO_FECHA_SALIDA);

        // Get all the reservasList where registroFechaSalida is less than or equal to SMALLER_REGISTRO_FECHA_SALIDA
        defaultReservasShouldNotBeFound("registroFechaSalida.lessThanOrEqual=" + SMALLER_REGISTRO_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsLessThanSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida is less than DEFAULT_REGISTRO_FECHA_SALIDA
        defaultReservasShouldNotBeFound("registroFechaSalida.lessThan=" + DEFAULT_REGISTRO_FECHA_SALIDA);

        // Get all the reservasList where registroFechaSalida is less than UPDATED_REGISTRO_FECHA_SALIDA
        defaultReservasShouldBeFound("registroFechaSalida.lessThan=" + UPDATED_REGISTRO_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void getAllReservasByRegistroFechaSalidaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where registroFechaSalida is greater than DEFAULT_REGISTRO_FECHA_SALIDA
        defaultReservasShouldNotBeFound("registroFechaSalida.greaterThan=" + DEFAULT_REGISTRO_FECHA_SALIDA);

        // Get all the reservasList where registroFechaSalida is greater than SMALLER_REGISTRO_FECHA_SALIDA
        defaultReservasShouldBeFound("registroFechaSalida.greaterThan=" + SMALLER_REGISTRO_FECHA_SALIDA);
    }


    @Test
    @Transactional
    public void getAllReservasByEstadoReservaIsEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where estadoReserva equals to DEFAULT_ESTADO_RESERVA
        defaultReservasShouldBeFound("estadoReserva.equals=" + DEFAULT_ESTADO_RESERVA);

        // Get all the reservasList where estadoReserva equals to UPDATED_ESTADO_RESERVA
        defaultReservasShouldNotBeFound("estadoReserva.equals=" + UPDATED_ESTADO_RESERVA);
    }

    @Test
    @Transactional
    public void getAllReservasByEstadoReservaIsInShouldWork() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where estadoReserva in DEFAULT_ESTADO_RESERVA or UPDATED_ESTADO_RESERVA
        defaultReservasShouldBeFound("estadoReserva.in=" + DEFAULT_ESTADO_RESERVA + "," + UPDATED_ESTADO_RESERVA);

        // Get all the reservasList where estadoReserva equals to UPDATED_ESTADO_RESERVA
        defaultReservasShouldNotBeFound("estadoReserva.in=" + UPDATED_ESTADO_RESERVA);
    }

    @Test
    @Transactional
    public void getAllReservasByEstadoReservaIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);

        // Get all the reservasList where estadoReserva is not null
        defaultReservasShouldBeFound("estadoReserva.specified=true");

        // Get all the reservasList where estadoReserva is null
        defaultReservasShouldNotBeFound("estadoReserva.specified=false");
    }

    @Test
    @Transactional
    public void getAllReservasByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        reservas.setMiembro(miembro);
        reservasRepository.saveAndFlush(reservas);
        Long miembroId = miembro.getId();

        // Get all the reservasList where miembro equals to miembroId
        defaultReservasShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the reservasList where miembro equals to miembroId + 1
        defaultReservasShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }


    @Test
    @Transactional
    public void getAllReservasByEspacioIsEqualToSomething() throws Exception {
        // Initialize the database
        reservasRepository.saveAndFlush(reservas);
        EspaciosReserva espacio = EspaciosReservaResourceIT.createEntity(em);
        em.persist(espacio);
        em.flush();
        reservas.setEspacio(espacio);
        reservasRepository.saveAndFlush(reservas);
        Long espacioId = espacio.getId();

        // Get all the reservasList where espacio equals to espacioId
        defaultReservasShouldBeFound("espacioId.equals=" + espacioId);

        // Get all the reservasList where espacio equals to espacioId + 1
        defaultReservasShouldNotBeFound("espacioId.equals=" + (espacioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReservasShouldBeFound(String filter) throws Exception {
        restReservasMockMvc.perform(get("/api/reservas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservas.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFechaEntrada").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_ENTRADA))))
            .andExpect(jsonPath("$.[*].registroFechaSalida").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_SALIDA))))
            .andExpect(jsonPath("$.[*].estadoReserva").value(hasItem(DEFAULT_ESTADO_RESERVA.toString())));

        // Check, that the count call also returns 1
        restReservasMockMvc.perform(get("/api/reservas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReservasShouldNotBeFound(String filter) throws Exception {
        restReservasMockMvc.perform(get("/api/reservas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReservasMockMvc.perform(get("/api/reservas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReservas() throws Exception {
        // Get the reservas
        restReservasMockMvc.perform(get("/api/reservas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservas() throws Exception {
        // Initialize the database
        reservasService.save(reservas);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockReservasSearchRepository);

        int databaseSizeBeforeUpdate = reservasRepository.findAll().size();

        // Update the reservas
        Reservas updatedReservas = reservasRepository.findById(reservas.getId()).get();
        // Disconnect from session so that the updates on updatedReservas are not directly saved in db
        em.detach(updatedReservas);
        updatedReservas
            .registroFechaEntrada(UPDATED_REGISTRO_FECHA_ENTRADA)
            .registroFechaSalida(UPDATED_REGISTRO_FECHA_SALIDA)
            .estadoReserva(UPDATED_ESTADO_RESERVA);

        restReservasMockMvc.perform(put("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservas)))
            .andExpect(status().isOk());

        // Validate the Reservas in the database
        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeUpdate);
        Reservas testReservas = reservasList.get(reservasList.size() - 1);
        assertThat(testReservas.getRegistroFechaEntrada()).isEqualTo(UPDATED_REGISTRO_FECHA_ENTRADA);
        assertThat(testReservas.getRegistroFechaSalida()).isEqualTo(UPDATED_REGISTRO_FECHA_SALIDA);
        assertThat(testReservas.getEstadoReserva()).isEqualTo(UPDATED_ESTADO_RESERVA);

        // Validate the Reservas in Elasticsearch
        verify(mockReservasSearchRepository, times(1)).save(testReservas);
    }

    @Test
    @Transactional
    public void updateNonExistingReservas() throws Exception {
        int databaseSizeBeforeUpdate = reservasRepository.findAll().size();

        // Create the Reservas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservasMockMvc.perform(put("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservas)))
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Reservas in Elasticsearch
        verify(mockReservasSearchRepository, times(0)).save(reservas);
    }

    @Test
    @Transactional
    public void deleteReservas() throws Exception {
        // Initialize the database
        reservasService.save(reservas);

        int databaseSizeBeforeDelete = reservasRepository.findAll().size();

        // Delete the reservas
        restReservasMockMvc.perform(delete("/api/reservas/{id}", reservas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reservas> reservasList = reservasRepository.findAll();
        assertThat(reservasList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Reservas in Elasticsearch
        verify(mockReservasSearchRepository, times(1)).deleteById(reservas.getId());
    }

    @Test
    @Transactional
    public void searchReservas() throws Exception {
        // Initialize the database
        reservasService.save(reservas);
        when(mockReservasSearchRepository.search(queryStringQuery("id:" + reservas.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(reservas), PageRequest.of(0, 1), 1));
        // Search the reservas
        restReservasMockMvc.perform(get("/api/_search/reservas?query=id:" + reservas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservas.getId().intValue())))
            .andExpect(jsonPath("$.[*].registroFechaEntrada").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_ENTRADA))))
            .andExpect(jsonPath("$.[*].registroFechaSalida").value(hasItem(sameInstant(DEFAULT_REGISTRO_FECHA_SALIDA))))
            .andExpect(jsonPath("$.[*].estadoReserva").value(hasItem(DEFAULT_ESTADO_RESERVA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservas.class);
        Reservas reservas1 = new Reservas();
        reservas1.setId(1L);
        Reservas reservas2 = new Reservas();
        reservas2.setId(reservas1.getId());
        assertThat(reservas1).isEqualTo(reservas2);
        reservas2.setId(2L);
        assertThat(reservas1).isNotEqualTo(reservas2);
        reservas1.setId(null);
        assertThat(reservas1).isNotEqualTo(reservas2);
    }
}
