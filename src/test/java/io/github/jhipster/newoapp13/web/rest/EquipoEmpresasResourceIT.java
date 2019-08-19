package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.Empresa;
import io.github.jhipster.newoapp13.repository.EquipoEmpresasRepository;
import io.github.jhipster.newoapp13.repository.search.EquipoEmpresasSearchRepository;
import io.github.jhipster.newoapp13.service.EquipoEmpresasService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.EquipoEmpresasCriteria;
import io.github.jhipster.newoapp13.service.EquipoEmpresasQueryService;

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
 * Integration tests for the {@link EquipoEmpresasResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class EquipoEmpresasResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGOS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGOS_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PAGINA_WEB = "AAAAAAAAAA";
    private static final String UPDATED_PAGINA_WEB = "BBBBBBBBBB";

    @Autowired
    private EquipoEmpresasRepository equipoEmpresasRepository;

    @Autowired
    private EquipoEmpresasService equipoEmpresasService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.EquipoEmpresasSearchRepositoryMockConfiguration
     */
    @Autowired
    private EquipoEmpresasSearchRepository mockEquipoEmpresasSearchRepository;

    @Autowired
    private EquipoEmpresasQueryService equipoEmpresasQueryService;

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

    private MockMvc restEquipoEmpresasMockMvc;

    private EquipoEmpresas equipoEmpresas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipoEmpresasResource equipoEmpresasResource = new EquipoEmpresasResource(equipoEmpresasService, equipoEmpresasQueryService);
        this.restEquipoEmpresasMockMvc = MockMvcBuilders.standaloneSetup(equipoEmpresasResource)
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
    public static EquipoEmpresas createEntity(EntityManager em) {
        EquipoEmpresas equipoEmpresas = new EquipoEmpresas()
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .direccion(DEFAULT_DIRECCION)
            .descripcion(DEFAULT_DESCRIPCION)
            .logos(DEFAULT_LOGOS)
            .logosContentType(DEFAULT_LOGOS_CONTENT_TYPE)
            .paginaWeb(DEFAULT_PAGINA_WEB);
        return equipoEmpresas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipoEmpresas createUpdatedEntity(EntityManager em) {
        EquipoEmpresas equipoEmpresas = new EquipoEmpresas()
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .direccion(UPDATED_DIRECCION)
            .descripcion(UPDATED_DESCRIPCION)
            .logos(UPDATED_LOGOS)
            .logosContentType(UPDATED_LOGOS_CONTENT_TYPE)
            .paginaWeb(UPDATED_PAGINA_WEB);
        return equipoEmpresas;
    }

    @BeforeEach
    public void initTest() {
        equipoEmpresas = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipoEmpresas() throws Exception {
        int databaseSizeBeforeCreate = equipoEmpresasRepository.findAll().size();

        // Create the EquipoEmpresas
        restEquipoEmpresasMockMvc.perform(post("/api/equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipoEmpresas)))
            .andExpect(status().isCreated());

        // Validate the EquipoEmpresas in the database
        List<EquipoEmpresas> equipoEmpresasList = equipoEmpresasRepository.findAll();
        assertThat(equipoEmpresasList).hasSize(databaseSizeBeforeCreate + 1);
        EquipoEmpresas testEquipoEmpresas = equipoEmpresasList.get(equipoEmpresasList.size() - 1);
        assertThat(testEquipoEmpresas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEquipoEmpresas.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEquipoEmpresas.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testEquipoEmpresas.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEquipoEmpresas.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEquipoEmpresas.getLogos()).isEqualTo(DEFAULT_LOGOS);
        assertThat(testEquipoEmpresas.getLogosContentType()).isEqualTo(DEFAULT_LOGOS_CONTENT_TYPE);
        assertThat(testEquipoEmpresas.getPaginaWeb()).isEqualTo(DEFAULT_PAGINA_WEB);

        // Validate the EquipoEmpresas in Elasticsearch
        verify(mockEquipoEmpresasSearchRepository, times(1)).save(testEquipoEmpresas);
    }

    @Test
    @Transactional
    public void createEquipoEmpresasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipoEmpresasRepository.findAll().size();

        // Create the EquipoEmpresas with an existing ID
        equipoEmpresas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipoEmpresasMockMvc.perform(post("/api/equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipoEmpresas)))
            .andExpect(status().isBadRequest());

        // Validate the EquipoEmpresas in the database
        List<EquipoEmpresas> equipoEmpresasList = equipoEmpresasRepository.findAll();
        assertThat(equipoEmpresasList).hasSize(databaseSizeBeforeCreate);

        // Validate the EquipoEmpresas in Elasticsearch
        verify(mockEquipoEmpresasSearchRepository, times(0)).save(equipoEmpresas);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoEmpresasRepository.findAll().size();
        // set the field null
        equipoEmpresas.setNombre(null);

        // Create the EquipoEmpresas, which fails.

        restEquipoEmpresasMockMvc.perform(post("/api/equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipoEmpresas)))
            .andExpect(status().isBadRequest());

        List<EquipoEmpresas> equipoEmpresasList = equipoEmpresasRepository.findAll();
        assertThat(equipoEmpresasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresas() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipoEmpresas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].logosContentType").value(hasItem(DEFAULT_LOGOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logos").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOS))))
            .andExpect(jsonPath("$.[*].paginaWeb").value(hasItem(DEFAULT_PAGINA_WEB.toString())));
    }
    
    @Test
    @Transactional
    public void getEquipoEmpresas() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get the equipoEmpresas
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas/{id}", equipoEmpresas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipoEmpresas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.logosContentType").value(DEFAULT_LOGOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.logos").value(Base64Utils.encodeToString(DEFAULT_LOGOS)))
            .andExpect(jsonPath("$.paginaWeb").value(DEFAULT_PAGINA_WEB.toString()));
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where nombre equals to DEFAULT_NOMBRE
        defaultEquipoEmpresasShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the equipoEmpresasList where nombre equals to UPDATED_NOMBRE
        defaultEquipoEmpresasShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultEquipoEmpresasShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the equipoEmpresasList where nombre equals to UPDATED_NOMBRE
        defaultEquipoEmpresasShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where nombre is not null
        defaultEquipoEmpresasShouldBeFound("nombre.specified=true");

        // Get all the equipoEmpresasList where nombre is null
        defaultEquipoEmpresasShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where telefono equals to DEFAULT_TELEFONO
        defaultEquipoEmpresasShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the equipoEmpresasList where telefono equals to UPDATED_TELEFONO
        defaultEquipoEmpresasShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultEquipoEmpresasShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the equipoEmpresasList where telefono equals to UPDATED_TELEFONO
        defaultEquipoEmpresasShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where telefono is not null
        defaultEquipoEmpresasShouldBeFound("telefono.specified=true");

        // Get all the equipoEmpresasList where telefono is null
        defaultEquipoEmpresasShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByCorreoIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where correo equals to DEFAULT_CORREO
        defaultEquipoEmpresasShouldBeFound("correo.equals=" + DEFAULT_CORREO);

        // Get all the equipoEmpresasList where correo equals to UPDATED_CORREO
        defaultEquipoEmpresasShouldNotBeFound("correo.equals=" + UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByCorreoIsInShouldWork() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where correo in DEFAULT_CORREO or UPDATED_CORREO
        defaultEquipoEmpresasShouldBeFound("correo.in=" + DEFAULT_CORREO + "," + UPDATED_CORREO);

        // Get all the equipoEmpresasList where correo equals to UPDATED_CORREO
        defaultEquipoEmpresasShouldNotBeFound("correo.in=" + UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByCorreoIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where correo is not null
        defaultEquipoEmpresasShouldBeFound("correo.specified=true");

        // Get all the equipoEmpresasList where correo is null
        defaultEquipoEmpresasShouldNotBeFound("correo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where direccion equals to DEFAULT_DIRECCION
        defaultEquipoEmpresasShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the equipoEmpresasList where direccion equals to UPDATED_DIRECCION
        defaultEquipoEmpresasShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultEquipoEmpresasShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the equipoEmpresasList where direccion equals to UPDATED_DIRECCION
        defaultEquipoEmpresasShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where direccion is not null
        defaultEquipoEmpresasShouldBeFound("direccion.specified=true");

        // Get all the equipoEmpresasList where direccion is null
        defaultEquipoEmpresasShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByPaginaWebIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where paginaWeb equals to DEFAULT_PAGINA_WEB
        defaultEquipoEmpresasShouldBeFound("paginaWeb.equals=" + DEFAULT_PAGINA_WEB);

        // Get all the equipoEmpresasList where paginaWeb equals to UPDATED_PAGINA_WEB
        defaultEquipoEmpresasShouldNotBeFound("paginaWeb.equals=" + UPDATED_PAGINA_WEB);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByPaginaWebIsInShouldWork() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where paginaWeb in DEFAULT_PAGINA_WEB or UPDATED_PAGINA_WEB
        defaultEquipoEmpresasShouldBeFound("paginaWeb.in=" + DEFAULT_PAGINA_WEB + "," + UPDATED_PAGINA_WEB);

        // Get all the equipoEmpresasList where paginaWeb equals to UPDATED_PAGINA_WEB
        defaultEquipoEmpresasShouldNotBeFound("paginaWeb.in=" + UPDATED_PAGINA_WEB);
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByPaginaWebIsNullOrNotNull() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);

        // Get all the equipoEmpresasList where paginaWeb is not null
        defaultEquipoEmpresasShouldBeFound("paginaWeb.specified=true");

        // Get all the equipoEmpresasList where paginaWeb is null
        defaultEquipoEmpresasShouldNotBeFound("paginaWeb.specified=false");
    }

    @Test
    @Transactional
    public void getAllEquipoEmpresasByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        equipoEmpresas.setMiembro(miembro);
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);
        Long miembroId = miembro.getId();

        // Get all the equipoEmpresasList where miembro equals to miembroId
        defaultEquipoEmpresasShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the equipoEmpresasList where miembro equals to miembroId + 1
        defaultEquipoEmpresasShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }


    @Test
    @Transactional
    public void getAllEquipoEmpresasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        equipoEmpresas.setEmpresa(empresa);
        equipoEmpresasRepository.saveAndFlush(equipoEmpresas);
        Long empresaId = empresa.getId();

        // Get all the equipoEmpresasList where empresa equals to empresaId
        defaultEquipoEmpresasShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the equipoEmpresasList where empresa equals to empresaId + 1
        defaultEquipoEmpresasShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEquipoEmpresasShouldBeFound(String filter) throws Exception {
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipoEmpresas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].logosContentType").value(hasItem(DEFAULT_LOGOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logos").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOS))))
            .andExpect(jsonPath("$.[*].paginaWeb").value(hasItem(DEFAULT_PAGINA_WEB)));

        // Check, that the count call also returns 1
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEquipoEmpresasShouldNotBeFound(String filter) throws Exception {
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEquipoEmpresas() throws Exception {
        // Get the equipoEmpresas
        restEquipoEmpresasMockMvc.perform(get("/api/equipo-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipoEmpresas() throws Exception {
        // Initialize the database
        equipoEmpresasService.save(equipoEmpresas);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEquipoEmpresasSearchRepository);

        int databaseSizeBeforeUpdate = equipoEmpresasRepository.findAll().size();

        // Update the equipoEmpresas
        EquipoEmpresas updatedEquipoEmpresas = equipoEmpresasRepository.findById(equipoEmpresas.getId()).get();
        // Disconnect from session so that the updates on updatedEquipoEmpresas are not directly saved in db
        em.detach(updatedEquipoEmpresas);
        updatedEquipoEmpresas
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .direccion(UPDATED_DIRECCION)
            .descripcion(UPDATED_DESCRIPCION)
            .logos(UPDATED_LOGOS)
            .logosContentType(UPDATED_LOGOS_CONTENT_TYPE)
            .paginaWeb(UPDATED_PAGINA_WEB);

        restEquipoEmpresasMockMvc.perform(put("/api/equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipoEmpresas)))
            .andExpect(status().isOk());

        // Validate the EquipoEmpresas in the database
        List<EquipoEmpresas> equipoEmpresasList = equipoEmpresasRepository.findAll();
        assertThat(equipoEmpresasList).hasSize(databaseSizeBeforeUpdate);
        EquipoEmpresas testEquipoEmpresas = equipoEmpresasList.get(equipoEmpresasList.size() - 1);
        assertThat(testEquipoEmpresas.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEquipoEmpresas.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEquipoEmpresas.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testEquipoEmpresas.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEquipoEmpresas.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEquipoEmpresas.getLogos()).isEqualTo(UPDATED_LOGOS);
        assertThat(testEquipoEmpresas.getLogosContentType()).isEqualTo(UPDATED_LOGOS_CONTENT_TYPE);
        assertThat(testEquipoEmpresas.getPaginaWeb()).isEqualTo(UPDATED_PAGINA_WEB);

        // Validate the EquipoEmpresas in Elasticsearch
        verify(mockEquipoEmpresasSearchRepository, times(1)).save(testEquipoEmpresas);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipoEmpresas() throws Exception {
        int databaseSizeBeforeUpdate = equipoEmpresasRepository.findAll().size();

        // Create the EquipoEmpresas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipoEmpresasMockMvc.perform(put("/api/equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipoEmpresas)))
            .andExpect(status().isBadRequest());

        // Validate the EquipoEmpresas in the database
        List<EquipoEmpresas> equipoEmpresasList = equipoEmpresasRepository.findAll();
        assertThat(equipoEmpresasList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EquipoEmpresas in Elasticsearch
        verify(mockEquipoEmpresasSearchRepository, times(0)).save(equipoEmpresas);
    }

    @Test
    @Transactional
    public void deleteEquipoEmpresas() throws Exception {
        // Initialize the database
        equipoEmpresasService.save(equipoEmpresas);

        int databaseSizeBeforeDelete = equipoEmpresasRepository.findAll().size();

        // Delete the equipoEmpresas
        restEquipoEmpresasMockMvc.perform(delete("/api/equipo-empresas/{id}", equipoEmpresas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EquipoEmpresas> equipoEmpresasList = equipoEmpresasRepository.findAll();
        assertThat(equipoEmpresasList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EquipoEmpresas in Elasticsearch
        verify(mockEquipoEmpresasSearchRepository, times(1)).deleteById(equipoEmpresas.getId());
    }

    @Test
    @Transactional
    public void searchEquipoEmpresas() throws Exception {
        // Initialize the database
        equipoEmpresasService.save(equipoEmpresas);
        when(mockEquipoEmpresasSearchRepository.search(queryStringQuery("id:" + equipoEmpresas.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(equipoEmpresas), PageRequest.of(0, 1), 1));
        // Search the equipoEmpresas
        restEquipoEmpresasMockMvc.perform(get("/api/_search/equipo-empresas?query=id:" + equipoEmpresas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipoEmpresas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].logosContentType").value(hasItem(DEFAULT_LOGOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logos").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOS))))
            .andExpect(jsonPath("$.[*].paginaWeb").value(hasItem(DEFAULT_PAGINA_WEB)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipoEmpresas.class);
        EquipoEmpresas equipoEmpresas1 = new EquipoEmpresas();
        equipoEmpresas1.setId(1L);
        EquipoEmpresas equipoEmpresas2 = new EquipoEmpresas();
        equipoEmpresas2.setId(equipoEmpresas1.getId());
        assertThat(equipoEmpresas1).isEqualTo(equipoEmpresas2);
        equipoEmpresas2.setId(2L);
        assertThat(equipoEmpresas1).isNotEqualTo(equipoEmpresas2);
        equipoEmpresas1.setId(null);
        assertThat(equipoEmpresas1).isNotEqualTo(equipoEmpresas2);
    }
}
