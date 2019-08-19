package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Invitados;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.InvitadosRepository;
import io.github.jhipster.newoapp13.repository.search.InvitadosSearchRepository;
import io.github.jhipster.newoapp13.service.InvitadosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.InvitadosCriteria;
import io.github.jhipster.newoapp13.service.InvitadosQueryService;

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
/**
 * Integration tests for the {@link InvitadosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class InvitadosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final TipoDocumentod DEFAULT_TIPO_DOCUMENTO = TipoDocumentod.Cedula;
    private static final TipoDocumentod UPDATED_TIPO_DOCUMENTO = TipoDocumentod.Cedula_Extranjeria;

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    @Autowired
    private InvitadosRepository invitadosRepository;

    @Autowired
    private InvitadosService invitadosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.InvitadosSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvitadosSearchRepository mockInvitadosSearchRepository;

    @Autowired
    private InvitadosQueryService invitadosQueryService;

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

    private MockMvc restInvitadosMockMvc;

    private Invitados invitados;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvitadosResource invitadosResource = new InvitadosResource(invitadosService, invitadosQueryService);
        this.restInvitadosMockMvc = MockMvcBuilders.standaloneSetup(invitadosResource)
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
    public static Invitados createEntity(EntityManager em) {
        Invitados invitados = new Invitados()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .identificacion(DEFAULT_IDENTIFICACION)
            .correo(DEFAULT_CORREO)
            .telefono(DEFAULT_TELEFONO);
        return invitados;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invitados createUpdatedEntity(EntityManager em) {
        Invitados invitados = new Invitados()
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .identificacion(UPDATED_IDENTIFICACION)
            .correo(UPDATED_CORREO)
            .telefono(UPDATED_TELEFONO);
        return invitados;
    }

    @BeforeEach
    public void initTest() {
        invitados = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitados() throws Exception {
        int databaseSizeBeforeCreate = invitadosRepository.findAll().size();

        // Create the Invitados
        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isCreated());

        // Validate the Invitados in the database
        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeCreate + 1);
        Invitados testInvitados = invitadosList.get(invitadosList.size() - 1);
        assertThat(testInvitados.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInvitados.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testInvitados.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testInvitados.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testInvitados.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testInvitados.getTelefono()).isEqualTo(DEFAULT_TELEFONO);

        // Validate the Invitados in Elasticsearch
        verify(mockInvitadosSearchRepository, times(1)).save(testInvitados);
    }

    @Test
    @Transactional
    public void createInvitadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitadosRepository.findAll().size();

        // Create the Invitados with an existing ID
        invitados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        // Validate the Invitados in the database
        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Invitados in Elasticsearch
        verify(mockInvitadosSearchRepository, times(0)).save(invitados);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitadosRepository.findAll().size();
        // set the field null
        invitados.setNombre(null);

        // Create the Invitados, which fails.

        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitadosRepository.findAll().size();
        // set the field null
        invitados.setApellido(null);

        // Create the Invitados, which fails.

        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitadosRepository.findAll().size();
        // set the field null
        invitados.setTipoDocumento(null);

        // Create the Invitados, which fails.

        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitadosRepository.findAll().size();
        // set the field null
        invitados.setIdentificacion(null);

        // Create the Invitados, which fails.

        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitadosRepository.findAll().size();
        // set the field null
        invitados.setCorreo(null);

        // Create the Invitados, which fails.

        restInvitadosMockMvc.perform(post("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitados() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList
        restInvitadosMockMvc.perform(get("/api/invitados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }
    
    @Test
    @Transactional
    public void getInvitados() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get the invitados
        restInvitadosMockMvc.perform(get("/api/invitados/{id}", invitados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invitados.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getAllInvitadosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where nombre equals to DEFAULT_NOMBRE
        defaultInvitadosShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the invitadosList where nombre equals to UPDATED_NOMBRE
        defaultInvitadosShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllInvitadosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultInvitadosShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the invitadosList where nombre equals to UPDATED_NOMBRE
        defaultInvitadosShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllInvitadosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where nombre is not null
        defaultInvitadosShouldBeFound("nombre.specified=true");

        // Get all the invitadosList where nombre is null
        defaultInvitadosShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitadosByApellidoIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where apellido equals to DEFAULT_APELLIDO
        defaultInvitadosShouldBeFound("apellido.equals=" + DEFAULT_APELLIDO);

        // Get all the invitadosList where apellido equals to UPDATED_APELLIDO
        defaultInvitadosShouldNotBeFound("apellido.equals=" + UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByApellidoIsInShouldWork() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where apellido in DEFAULT_APELLIDO or UPDATED_APELLIDO
        defaultInvitadosShouldBeFound("apellido.in=" + DEFAULT_APELLIDO + "," + UPDATED_APELLIDO);

        // Get all the invitadosList where apellido equals to UPDATED_APELLIDO
        defaultInvitadosShouldNotBeFound("apellido.in=" + UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByApellidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where apellido is not null
        defaultInvitadosShouldBeFound("apellido.specified=true");

        // Get all the invitadosList where apellido is null
        defaultInvitadosShouldNotBeFound("apellido.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitadosByTipoDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where tipoDocumento equals to DEFAULT_TIPO_DOCUMENTO
        defaultInvitadosShouldBeFound("tipoDocumento.equals=" + DEFAULT_TIPO_DOCUMENTO);

        // Get all the invitadosList where tipoDocumento equals to UPDATED_TIPO_DOCUMENTO
        defaultInvitadosShouldNotBeFound("tipoDocumento.equals=" + UPDATED_TIPO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByTipoDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where tipoDocumento in DEFAULT_TIPO_DOCUMENTO or UPDATED_TIPO_DOCUMENTO
        defaultInvitadosShouldBeFound("tipoDocumento.in=" + DEFAULT_TIPO_DOCUMENTO + "," + UPDATED_TIPO_DOCUMENTO);

        // Get all the invitadosList where tipoDocumento equals to UPDATED_TIPO_DOCUMENTO
        defaultInvitadosShouldNotBeFound("tipoDocumento.in=" + UPDATED_TIPO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByTipoDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where tipoDocumento is not null
        defaultInvitadosShouldBeFound("tipoDocumento.specified=true");

        // Get all the invitadosList where tipoDocumento is null
        defaultInvitadosShouldNotBeFound("tipoDocumento.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitadosByIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where identificacion equals to DEFAULT_IDENTIFICACION
        defaultInvitadosShouldBeFound("identificacion.equals=" + DEFAULT_IDENTIFICACION);

        // Get all the invitadosList where identificacion equals to UPDATED_IDENTIFICACION
        defaultInvitadosShouldNotBeFound("identificacion.equals=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllInvitadosByIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where identificacion in DEFAULT_IDENTIFICACION or UPDATED_IDENTIFICACION
        defaultInvitadosShouldBeFound("identificacion.in=" + DEFAULT_IDENTIFICACION + "," + UPDATED_IDENTIFICACION);

        // Get all the invitadosList where identificacion equals to UPDATED_IDENTIFICACION
        defaultInvitadosShouldNotBeFound("identificacion.in=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllInvitadosByIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where identificacion is not null
        defaultInvitadosShouldBeFound("identificacion.specified=true");

        // Get all the invitadosList where identificacion is null
        defaultInvitadosShouldNotBeFound("identificacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitadosByCorreoIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where correo equals to DEFAULT_CORREO
        defaultInvitadosShouldBeFound("correo.equals=" + DEFAULT_CORREO);

        // Get all the invitadosList where correo equals to UPDATED_CORREO
        defaultInvitadosShouldNotBeFound("correo.equals=" + UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByCorreoIsInShouldWork() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where correo in DEFAULT_CORREO or UPDATED_CORREO
        defaultInvitadosShouldBeFound("correo.in=" + DEFAULT_CORREO + "," + UPDATED_CORREO);

        // Get all the invitadosList where correo equals to UPDATED_CORREO
        defaultInvitadosShouldNotBeFound("correo.in=" + UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByCorreoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where correo is not null
        defaultInvitadosShouldBeFound("correo.specified=true");

        // Get all the invitadosList where correo is null
        defaultInvitadosShouldNotBeFound("correo.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitadosByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where telefono equals to DEFAULT_TELEFONO
        defaultInvitadosShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the invitadosList where telefono equals to UPDATED_TELEFONO
        defaultInvitadosShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultInvitadosShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the invitadosList where telefono equals to UPDATED_TELEFONO
        defaultInvitadosShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllInvitadosByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);

        // Get all the invitadosList where telefono is not null
        defaultInvitadosShouldBeFound("telefono.specified=true");

        // Get all the invitadosList where telefono is null
        defaultInvitadosShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitadosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        invitadosRepository.saveAndFlush(invitados);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        invitados.setMiembro(miembro);
        invitadosRepository.saveAndFlush(invitados);
        Long miembroId = miembro.getId();

        // Get all the invitadosList where miembro equals to miembroId
        defaultInvitadosShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the invitadosList where miembro equals to miembroId + 1
        defaultInvitadosShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvitadosShouldBeFound(String filter) throws Exception {
        restInvitadosMockMvc.perform(get("/api/invitados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));

        // Check, that the count call also returns 1
        restInvitadosMockMvc.perform(get("/api/invitados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvitadosShouldNotBeFound(String filter) throws Exception {
        restInvitadosMockMvc.perform(get("/api/invitados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvitadosMockMvc.perform(get("/api/invitados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvitados() throws Exception {
        // Get the invitados
        restInvitadosMockMvc.perform(get("/api/invitados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitados() throws Exception {
        // Initialize the database
        invitadosService.save(invitados);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInvitadosSearchRepository);

        int databaseSizeBeforeUpdate = invitadosRepository.findAll().size();

        // Update the invitados
        Invitados updatedInvitados = invitadosRepository.findById(invitados.getId()).get();
        // Disconnect from session so that the updates on updatedInvitados are not directly saved in db
        em.detach(updatedInvitados);
        updatedInvitados
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .identificacion(UPDATED_IDENTIFICACION)
            .correo(UPDATED_CORREO)
            .telefono(UPDATED_TELEFONO);

        restInvitadosMockMvc.perform(put("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvitados)))
            .andExpect(status().isOk());

        // Validate the Invitados in the database
        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeUpdate);
        Invitados testInvitados = invitadosList.get(invitadosList.size() - 1);
        assertThat(testInvitados.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInvitados.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testInvitados.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testInvitados.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testInvitados.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testInvitados.getTelefono()).isEqualTo(UPDATED_TELEFONO);

        // Validate the Invitados in Elasticsearch
        verify(mockInvitadosSearchRepository, times(1)).save(testInvitados);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitados() throws Exception {
        int databaseSizeBeforeUpdate = invitadosRepository.findAll().size();

        // Create the Invitados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitadosMockMvc.perform(put("/api/invitados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitados)))
            .andExpect(status().isBadRequest());

        // Validate the Invitados in the database
        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Invitados in Elasticsearch
        verify(mockInvitadosSearchRepository, times(0)).save(invitados);
    }

    @Test
    @Transactional
    public void deleteInvitados() throws Exception {
        // Initialize the database
        invitadosService.save(invitados);

        int databaseSizeBeforeDelete = invitadosRepository.findAll().size();

        // Delete the invitados
        restInvitadosMockMvc.perform(delete("/api/invitados/{id}", invitados.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invitados> invitadosList = invitadosRepository.findAll();
        assertThat(invitadosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Invitados in Elasticsearch
        verify(mockInvitadosSearchRepository, times(1)).deleteById(invitados.getId());
    }

    @Test
    @Transactional
    public void searchInvitados() throws Exception {
        // Initialize the database
        invitadosService.save(invitados);
        when(mockInvitadosSearchRepository.search(queryStringQuery("id:" + invitados.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(invitados), PageRequest.of(0, 1), 1));
        // Search the invitados
        restInvitadosMockMvc.perform(get("/api/_search/invitados?query=id:" + invitados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invitados.class);
        Invitados invitados1 = new Invitados();
        invitados1.setId(1L);
        Invitados invitados2 = new Invitados();
        invitados2.setId(invitados1.getId());
        assertThat(invitados1).isEqualTo(invitados2);
        invitados2.setId(2L);
        assertThat(invitados1).isNotEqualTo(invitados2);
        invitados1.setId(null);
        assertThat(invitados1).isNotEqualTo(invitados2);
    }
}
