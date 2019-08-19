package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Empresa;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.EmpresaRepository;
import io.github.jhipster.newoapp13.repository.search.EmpresaSearchRepository;
import io.github.jhipster.newoapp13.service.EmpresaService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.EmpresaCriteria;
import io.github.jhipster.newoapp13.service.EmpresaQueryService;

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

/**
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class EmpresaResourceIT {

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NIT = "AAAAAAAAAA";
    private static final String UPDATED_NIT = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.EmpresaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmpresaSearchRepository mockEmpresaSearchRepository;

    @Autowired
    private EmpresaQueryService empresaQueryService;

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

    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresaResource empresaResource = new EmpresaResource(empresaService, empresaQueryService);
        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource)
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
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .nit(DEFAULT_NIT)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .celular(DEFAULT_CELULAR);
        return empresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .nit(UPDATED_NIT)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .celular(UPDATED_CELULAR);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testEmpresa.getNit()).isEqualTo(DEFAULT_NIT);
        assertThat(testEmpresa.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEmpresa.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpresa.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testEmpresa.getCelular()).isEqualTo(DEFAULT_CELULAR);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(1)).save(testEmpresa);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(0)).save(empresa);
    }


    @Test
    @Transactional
    public void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setRazonSocial(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNitIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNit(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.nit").value(DEFAULT_NIT.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()));
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial equals to DEFAULT_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.equals=" + DEFAULT_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial equals to UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.equals=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial in DEFAULT_RAZON_SOCIAL or UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldBeFound("razonSocial.in=" + DEFAULT_RAZON_SOCIAL + "," + UPDATED_RAZON_SOCIAL);

        // Get all the empresaList where razonSocial equals to UPDATED_RAZON_SOCIAL
        defaultEmpresaShouldNotBeFound("razonSocial.in=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByRazonSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razonSocial is not null
        defaultEmpresaShouldBeFound("razonSocial.specified=true");

        // Get all the empresaList where razonSocial is null
        defaultEmpresaShouldNotBeFound("razonSocial.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByNitIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nit equals to DEFAULT_NIT
        defaultEmpresaShouldBeFound("nit.equals=" + DEFAULT_NIT);

        // Get all the empresaList where nit equals to UPDATED_NIT
        defaultEmpresaShouldNotBeFound("nit.equals=" + UPDATED_NIT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNitIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nit in DEFAULT_NIT or UPDATED_NIT
        defaultEmpresaShouldBeFound("nit.in=" + DEFAULT_NIT + "," + UPDATED_NIT);

        // Get all the empresaList where nit equals to UPDATED_NIT
        defaultEmpresaShouldNotBeFound("nit.in=" + UPDATED_NIT);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNitIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nit is not null
        defaultEmpresaShouldBeFound("nit.specified=true");

        // Get all the empresaList where nit is null
        defaultEmpresaShouldNotBeFound("nit.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where direccion equals to DEFAULT_DIRECCION
        defaultEmpresaShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the empresaList where direccion equals to UPDATED_DIRECCION
        defaultEmpresaShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultEmpresaShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the empresaList where direccion equals to UPDATED_DIRECCION
        defaultEmpresaShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where direccion is not null
        defaultEmpresaShouldBeFound("direccion.specified=true");

        // Get all the empresaList where direccion is null
        defaultEmpresaShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono equals to DEFAULT_TELEFONO
        defaultEmpresaShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the empresaList where telefono equals to UPDATED_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultEmpresaShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the empresaList where telefono equals to UPDATED_TELEFONO
        defaultEmpresaShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where telefono is not null
        defaultEmpresaShouldBeFound("telefono.specified=true");

        // Get all the empresaList where telefono is null
        defaultEmpresaShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByCorreoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where correo equals to DEFAULT_CORREO
        defaultEmpresaShouldBeFound("correo.equals=" + DEFAULT_CORREO);

        // Get all the empresaList where correo equals to UPDATED_CORREO
        defaultEmpresaShouldNotBeFound("correo.equals=" + UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCorreoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where correo in DEFAULT_CORREO or UPDATED_CORREO
        defaultEmpresaShouldBeFound("correo.in=" + DEFAULT_CORREO + "," + UPDATED_CORREO);

        // Get all the empresaList where correo equals to UPDATED_CORREO
        defaultEmpresaShouldNotBeFound("correo.in=" + UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCorreoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where correo is not null
        defaultEmpresaShouldBeFound("correo.specified=true");

        // Get all the empresaList where correo is null
        defaultEmpresaShouldNotBeFound("correo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where celular equals to DEFAULT_CELULAR
        defaultEmpresaShouldBeFound("celular.equals=" + DEFAULT_CELULAR);

        // Get all the empresaList where celular equals to UPDATED_CELULAR
        defaultEmpresaShouldNotBeFound("celular.equals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCelularIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where celular in DEFAULT_CELULAR or UPDATED_CELULAR
        defaultEmpresaShouldBeFound("celular.in=" + DEFAULT_CELULAR + "," + UPDATED_CELULAR);

        // Get all the empresaList where celular equals to UPDATED_CELULAR
        defaultEmpresaShouldNotBeFound("celular.in=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where celular is not null
        defaultEmpresaShouldBeFound("celular.specified=true");

        // Get all the empresaList where celular is null
        defaultEmpresaShouldNotBeFound("celular.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByAliadoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Miembros aliado = MiembrosResourceIT.createEntity(em);
        em.persist(aliado);
        em.flush();
        empresa.setAliado(aliado);
        empresaRepository.saveAndFlush(empresa);
        Long aliadoId = aliado.getId();

        // Get all the empresaList where aliado equals to aliadoId
        defaultEmpresaShouldBeFound("aliadoId.equals=" + aliadoId);

        // Get all the empresaList where aliado equals to aliadoId + 1
        defaultEmpresaShouldNotBeFound("aliadoId.equals=" + (aliadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaShouldBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)));

        // Check, that the count call also returns 1
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaShouldNotBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaService.save(empresa);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEmpresaSearchRepository);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .nit(UPDATED_NIT)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .celular(UPDATED_CELULAR);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpresa)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testEmpresa.getNit()).isEqualTo(UPDATED_NIT);
        assertThat(testEmpresa.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEmpresa.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpresa.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testEmpresa.getCelular()).isEqualTo(UPDATED_CELULAR);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(1)).save(testEmpresa);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Create the Empresa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(0)).save(empresa);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaService.save(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Empresa in Elasticsearch
        verify(mockEmpresaSearchRepository, times(1)).deleteById(empresa.getId());
    }

    @Test
    @Transactional
    public void searchEmpresa() throws Exception {
        // Initialize the database
        empresaService.save(empresa);
        when(mockEmpresaSearchRepository.search(queryStringQuery("id:" + empresa.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(empresa), PageRequest.of(0, 1), 1));
        // Search the empresa
        restEmpresaMockMvc.perform(get("/api/_search/empresas?query=id:" + empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empresa.class);
        Empresa empresa1 = new Empresa();
        empresa1.setId(1L);
        Empresa empresa2 = new Empresa();
        empresa2.setId(empresa1.getId());
        assertThat(empresa1).isEqualTo(empresa2);
        empresa2.setId(2L);
        assertThat(empresa1).isNotEqualTo(empresa2);
        empresa1.setId(null);
        assertThat(empresa1).isNotEqualTo(empresa2);
    }
}
