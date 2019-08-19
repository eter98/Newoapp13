package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.repository.CategoriaContenidosRepository;
import io.github.jhipster.newoapp13.repository.search.CategoriaContenidosSearchRepository;
import io.github.jhipster.newoapp13.service.CategoriaContenidosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.CategoriaContenidosCriteria;
import io.github.jhipster.newoapp13.service.CategoriaContenidosQueryService;

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
 * Integration tests for the {@link CategoriaContenidosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class CategoriaContenidosResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    @Autowired
    private CategoriaContenidosRepository categoriaContenidosRepository;

    @Autowired
    private CategoriaContenidosService categoriaContenidosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.CategoriaContenidosSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoriaContenidosSearchRepository mockCategoriaContenidosSearchRepository;

    @Autowired
    private CategoriaContenidosQueryService categoriaContenidosQueryService;

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

    private MockMvc restCategoriaContenidosMockMvc;

    private CategoriaContenidos categoriaContenidos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaContenidosResource categoriaContenidosResource = new CategoriaContenidosResource(categoriaContenidosService, categoriaContenidosQueryService);
        this.restCategoriaContenidosMockMvc = MockMvcBuilders.standaloneSetup(categoriaContenidosResource)
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
    public static CategoriaContenidos createEntity(EntityManager em) {
        CategoriaContenidos categoriaContenidos = new CategoriaContenidos()
            .categoria(DEFAULT_CATEGORIA);
        return categoriaContenidos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaContenidos createUpdatedEntity(EntityManager em) {
        CategoriaContenidos categoriaContenidos = new CategoriaContenidos()
            .categoria(UPDATED_CATEGORIA);
        return categoriaContenidos;
    }

    @BeforeEach
    public void initTest() {
        categoriaContenidos = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaContenidos() throws Exception {
        int databaseSizeBeforeCreate = categoriaContenidosRepository.findAll().size();

        // Create the CategoriaContenidos
        restCategoriaContenidosMockMvc.perform(post("/api/categoria-contenidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaContenidos)))
            .andExpect(status().isCreated());

        // Validate the CategoriaContenidos in the database
        List<CategoriaContenidos> categoriaContenidosList = categoriaContenidosRepository.findAll();
        assertThat(categoriaContenidosList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaContenidos testCategoriaContenidos = categoriaContenidosList.get(categoriaContenidosList.size() - 1);
        assertThat(testCategoriaContenidos.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);

        // Validate the CategoriaContenidos in Elasticsearch
        verify(mockCategoriaContenidosSearchRepository, times(1)).save(testCategoriaContenidos);
    }

    @Test
    @Transactional
    public void createCategoriaContenidosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaContenidosRepository.findAll().size();

        // Create the CategoriaContenidos with an existing ID
        categoriaContenidos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaContenidosMockMvc.perform(post("/api/categoria-contenidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaContenidos)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaContenidos in the database
        List<CategoriaContenidos> categoriaContenidosList = categoriaContenidosRepository.findAll();
        assertThat(categoriaContenidosList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoriaContenidos in Elasticsearch
        verify(mockCategoriaContenidosSearchRepository, times(0)).save(categoriaContenidos);
    }


    @Test
    @Transactional
    public void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaContenidosRepository.findAll().size();
        // set the field null
        categoriaContenidos.setCategoria(null);

        // Create the CategoriaContenidos, which fails.

        restCategoriaContenidosMockMvc.perform(post("/api/categoria-contenidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaContenidos)))
            .andExpect(status().isBadRequest());

        List<CategoriaContenidos> categoriaContenidosList = categoriaContenidosRepository.findAll();
        assertThat(categoriaContenidosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriaContenidos() throws Exception {
        // Initialize the database
        categoriaContenidosRepository.saveAndFlush(categoriaContenidos);

        // Get all the categoriaContenidosList
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaContenidos.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaContenidos() throws Exception {
        // Initialize the database
        categoriaContenidosRepository.saveAndFlush(categoriaContenidos);

        // Get the categoriaContenidos
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos/{id}", categoriaContenidos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaContenidos.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getAllCategoriaContenidosByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaContenidosRepository.saveAndFlush(categoriaContenidos);

        // Get all the categoriaContenidosList where categoria equals to DEFAULT_CATEGORIA
        defaultCategoriaContenidosShouldBeFound("categoria.equals=" + DEFAULT_CATEGORIA);

        // Get all the categoriaContenidosList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaContenidosShouldNotBeFound("categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaContenidosByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaContenidosRepository.saveAndFlush(categoriaContenidos);

        // Get all the categoriaContenidosList where categoria in DEFAULT_CATEGORIA or UPDATED_CATEGORIA
        defaultCategoriaContenidosShouldBeFound("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA);

        // Get all the categoriaContenidosList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaContenidosShouldNotBeFound("categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaContenidosByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaContenidosRepository.saveAndFlush(categoriaContenidos);

        // Get all the categoriaContenidosList where categoria is not null
        defaultCategoriaContenidosShouldBeFound("categoria.specified=true");

        // Get all the categoriaContenidosList where categoria is null
        defaultCategoriaContenidosShouldNotBeFound("categoria.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaContenidosShouldBeFound(String filter) throws Exception {
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaContenidos.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));

        // Check, that the count call also returns 1
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaContenidosShouldNotBeFound(String filter) throws Exception {
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoriaContenidos() throws Exception {
        // Get the categoriaContenidos
        restCategoriaContenidosMockMvc.perform(get("/api/categoria-contenidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaContenidos() throws Exception {
        // Initialize the database
        categoriaContenidosService.save(categoriaContenidos);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCategoriaContenidosSearchRepository);

        int databaseSizeBeforeUpdate = categoriaContenidosRepository.findAll().size();

        // Update the categoriaContenidos
        CategoriaContenidos updatedCategoriaContenidos = categoriaContenidosRepository.findById(categoriaContenidos.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaContenidos are not directly saved in db
        em.detach(updatedCategoriaContenidos);
        updatedCategoriaContenidos
            .categoria(UPDATED_CATEGORIA);

        restCategoriaContenidosMockMvc.perform(put("/api/categoria-contenidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaContenidos)))
            .andExpect(status().isOk());

        // Validate the CategoriaContenidos in the database
        List<CategoriaContenidos> categoriaContenidosList = categoriaContenidosRepository.findAll();
        assertThat(categoriaContenidosList).hasSize(databaseSizeBeforeUpdate);
        CategoriaContenidos testCategoriaContenidos = categoriaContenidosList.get(categoriaContenidosList.size() - 1);
        assertThat(testCategoriaContenidos.getCategoria()).isEqualTo(UPDATED_CATEGORIA);

        // Validate the CategoriaContenidos in Elasticsearch
        verify(mockCategoriaContenidosSearchRepository, times(1)).save(testCategoriaContenidos);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaContenidos() throws Exception {
        int databaseSizeBeforeUpdate = categoriaContenidosRepository.findAll().size();

        // Create the CategoriaContenidos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaContenidosMockMvc.perform(put("/api/categoria-contenidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaContenidos)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaContenidos in the database
        List<CategoriaContenidos> categoriaContenidosList = categoriaContenidosRepository.findAll();
        assertThat(categoriaContenidosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoriaContenidos in Elasticsearch
        verify(mockCategoriaContenidosSearchRepository, times(0)).save(categoriaContenidos);
    }

    @Test
    @Transactional
    public void deleteCategoriaContenidos() throws Exception {
        // Initialize the database
        categoriaContenidosService.save(categoriaContenidos);

        int databaseSizeBeforeDelete = categoriaContenidosRepository.findAll().size();

        // Delete the categoriaContenidos
        restCategoriaContenidosMockMvc.perform(delete("/api/categoria-contenidos/{id}", categoriaContenidos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaContenidos> categoriaContenidosList = categoriaContenidosRepository.findAll();
        assertThat(categoriaContenidosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoriaContenidos in Elasticsearch
        verify(mockCategoriaContenidosSearchRepository, times(1)).deleteById(categoriaContenidos.getId());
    }

    @Test
    @Transactional
    public void searchCategoriaContenidos() throws Exception {
        // Initialize the database
        categoriaContenidosService.save(categoriaContenidos);
        when(mockCategoriaContenidosSearchRepository.search(queryStringQuery("id:" + categoriaContenidos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(categoriaContenidos), PageRequest.of(0, 1), 1));
        // Search the categoriaContenidos
        restCategoriaContenidosMockMvc.perform(get("/api/_search/categoria-contenidos?query=id:" + categoriaContenidos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaContenidos.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaContenidos.class);
        CategoriaContenidos categoriaContenidos1 = new CategoriaContenidos();
        categoriaContenidos1.setId(1L);
        CategoriaContenidos categoriaContenidos2 = new CategoriaContenidos();
        categoriaContenidos2.setId(categoriaContenidos1.getId());
        assertThat(categoriaContenidos1).isEqualTo(categoriaContenidos2);
        categoriaContenidos2.setId(2L);
        assertThat(categoriaContenidos1).isNotEqualTo(categoriaContenidos2);
        categoriaContenidos1.setId(null);
        assertThat(categoriaContenidos1).isNotEqualTo(categoriaContenidos2);
    }
}
