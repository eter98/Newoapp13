package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import io.github.jhipster.newoapp13.repository.TipoRegistroCompraRepository;
import io.github.jhipster.newoapp13.repository.search.TipoRegistroCompraSearchRepository;
import io.github.jhipster.newoapp13.service.TipoRegistroCompraService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.TipoRegistroCompraCriteria;
import io.github.jhipster.newoapp13.service.TipoRegistroCompraQueryService;

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
 * Integration tests for the {@link TipoRegistroCompraResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class TipoRegistroCompraResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoRegistroCompraRepository tipoRegistroCompraRepository;

    @Autowired
    private TipoRegistroCompraService tipoRegistroCompraService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.TipoRegistroCompraSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoRegistroCompraSearchRepository mockTipoRegistroCompraSearchRepository;

    @Autowired
    private TipoRegistroCompraQueryService tipoRegistroCompraQueryService;

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

    private MockMvc restTipoRegistroCompraMockMvc;

    private TipoRegistroCompra tipoRegistroCompra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoRegistroCompraResource tipoRegistroCompraResource = new TipoRegistroCompraResource(tipoRegistroCompraService, tipoRegistroCompraQueryService);
        this.restTipoRegistroCompraMockMvc = MockMvcBuilders.standaloneSetup(tipoRegistroCompraResource)
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
    public static TipoRegistroCompra createEntity(EntityManager em) {
        TipoRegistroCompra tipoRegistroCompra = new TipoRegistroCompra()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoRegistroCompra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRegistroCompra createUpdatedEntity(EntityManager em) {
        TipoRegistroCompra tipoRegistroCompra = new TipoRegistroCompra()
            .descripcion(UPDATED_DESCRIPCION);
        return tipoRegistroCompra;
    }

    @BeforeEach
    public void initTest() {
        tipoRegistroCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRegistroCompra() throws Exception {
        int databaseSizeBeforeCreate = tipoRegistroCompraRepository.findAll().size();

        // Create the TipoRegistroCompra
        restTipoRegistroCompraMockMvc.perform(post("/api/tipo-registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRegistroCompra)))
            .andExpect(status().isCreated());

        // Validate the TipoRegistroCompra in the database
        List<TipoRegistroCompra> tipoRegistroCompraList = tipoRegistroCompraRepository.findAll();
        assertThat(tipoRegistroCompraList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRegistroCompra testTipoRegistroCompra = tipoRegistroCompraList.get(tipoRegistroCompraList.size() - 1);
        assertThat(testTipoRegistroCompra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the TipoRegistroCompra in Elasticsearch
        verify(mockTipoRegistroCompraSearchRepository, times(1)).save(testTipoRegistroCompra);
    }

    @Test
    @Transactional
    public void createTipoRegistroCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRegistroCompraRepository.findAll().size();

        // Create the TipoRegistroCompra with an existing ID
        tipoRegistroCompra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRegistroCompraMockMvc.perform(post("/api/tipo-registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRegistroCompra)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRegistroCompra in the database
        List<TipoRegistroCompra> tipoRegistroCompraList = tipoRegistroCompraRepository.findAll();
        assertThat(tipoRegistroCompraList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoRegistroCompra in Elasticsearch
        verify(mockTipoRegistroCompraSearchRepository, times(0)).save(tipoRegistroCompra);
    }


    @Test
    @Transactional
    public void getAllTipoRegistroCompras() throws Exception {
        // Initialize the database
        tipoRegistroCompraRepository.saveAndFlush(tipoRegistroCompra);

        // Get all the tipoRegistroCompraList
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRegistroCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoRegistroCompra() throws Exception {
        // Initialize the database
        tipoRegistroCompraRepository.saveAndFlush(tipoRegistroCompra);

        // Get the tipoRegistroCompra
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras/{id}", tipoRegistroCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRegistroCompra.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoRegistroComprasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoRegistroCompraRepository.saveAndFlush(tipoRegistroCompra);

        // Get all the tipoRegistroCompraList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTipoRegistroCompraShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoRegistroCompraList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoRegistroCompraShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoRegistroComprasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tipoRegistroCompraRepository.saveAndFlush(tipoRegistroCompra);

        // Get all the tipoRegistroCompraList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTipoRegistroCompraShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tipoRegistroCompraList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoRegistroCompraShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoRegistroComprasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoRegistroCompraRepository.saveAndFlush(tipoRegistroCompra);

        // Get all the tipoRegistroCompraList where descripcion is not null
        defaultTipoRegistroCompraShouldBeFound("descripcion.specified=true");

        // Get all the tipoRegistroCompraList where descripcion is null
        defaultTipoRegistroCompraShouldNotBeFound("descripcion.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoRegistroCompraShouldBeFound(String filter) throws Exception {
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRegistroCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoRegistroCompraShouldNotBeFound(String filter) throws Exception {
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoRegistroCompra() throws Exception {
        // Get the tipoRegistroCompra
        restTipoRegistroCompraMockMvc.perform(get("/api/tipo-registro-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRegistroCompra() throws Exception {
        // Initialize the database
        tipoRegistroCompraService.save(tipoRegistroCompra);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTipoRegistroCompraSearchRepository);

        int databaseSizeBeforeUpdate = tipoRegistroCompraRepository.findAll().size();

        // Update the tipoRegistroCompra
        TipoRegistroCompra updatedTipoRegistroCompra = tipoRegistroCompraRepository.findById(tipoRegistroCompra.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRegistroCompra are not directly saved in db
        em.detach(updatedTipoRegistroCompra);
        updatedTipoRegistroCompra
            .descripcion(UPDATED_DESCRIPCION);

        restTipoRegistroCompraMockMvc.perform(put("/api/tipo-registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoRegistroCompra)))
            .andExpect(status().isOk());

        // Validate the TipoRegistroCompra in the database
        List<TipoRegistroCompra> tipoRegistroCompraList = tipoRegistroCompraRepository.findAll();
        assertThat(tipoRegistroCompraList).hasSize(databaseSizeBeforeUpdate);
        TipoRegistroCompra testTipoRegistroCompra = tipoRegistroCompraList.get(tipoRegistroCompraList.size() - 1);
        assertThat(testTipoRegistroCompra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the TipoRegistroCompra in Elasticsearch
        verify(mockTipoRegistroCompraSearchRepository, times(1)).save(testTipoRegistroCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRegistroCompra() throws Exception {
        int databaseSizeBeforeUpdate = tipoRegistroCompraRepository.findAll().size();

        // Create the TipoRegistroCompra

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRegistroCompraMockMvc.perform(put("/api/tipo-registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRegistroCompra)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRegistroCompra in the database
        List<TipoRegistroCompra> tipoRegistroCompraList = tipoRegistroCompraRepository.findAll();
        assertThat(tipoRegistroCompraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoRegistroCompra in Elasticsearch
        verify(mockTipoRegistroCompraSearchRepository, times(0)).save(tipoRegistroCompra);
    }

    @Test
    @Transactional
    public void deleteTipoRegistroCompra() throws Exception {
        // Initialize the database
        tipoRegistroCompraService.save(tipoRegistroCompra);

        int databaseSizeBeforeDelete = tipoRegistroCompraRepository.findAll().size();

        // Delete the tipoRegistroCompra
        restTipoRegistroCompraMockMvc.perform(delete("/api/tipo-registro-compras/{id}", tipoRegistroCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRegistroCompra> tipoRegistroCompraList = tipoRegistroCompraRepository.findAll();
        assertThat(tipoRegistroCompraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoRegistroCompra in Elasticsearch
        verify(mockTipoRegistroCompraSearchRepository, times(1)).deleteById(tipoRegistroCompra.getId());
    }

    @Test
    @Transactional
    public void searchTipoRegistroCompra() throws Exception {
        // Initialize the database
        tipoRegistroCompraService.save(tipoRegistroCompra);
        when(mockTipoRegistroCompraSearchRepository.search(queryStringQuery("id:" + tipoRegistroCompra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoRegistroCompra), PageRequest.of(0, 1), 1));
        // Search the tipoRegistroCompra
        restTipoRegistroCompraMockMvc.perform(get("/api/_search/tipo-registro-compras?query=id:" + tipoRegistroCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRegistroCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRegistroCompra.class);
        TipoRegistroCompra tipoRegistroCompra1 = new TipoRegistroCompra();
        tipoRegistroCompra1.setId(1L);
        TipoRegistroCompra tipoRegistroCompra2 = new TipoRegistroCompra();
        tipoRegistroCompra2.setId(tipoRegistroCompra1.getId());
        assertThat(tipoRegistroCompra1).isEqualTo(tipoRegistroCompra2);
        tipoRegistroCompra2.setId(2L);
        assertThat(tipoRegistroCompra1).isNotEqualTo(tipoRegistroCompra2);
        tipoRegistroCompra1.setId(null);
        assertThat(tipoRegistroCompra1).isNotEqualTo(tipoRegistroCompra2);
    }
}
