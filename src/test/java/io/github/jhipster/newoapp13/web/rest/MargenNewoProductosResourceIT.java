package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.MargenNewoProductos;
import io.github.jhipster.newoapp13.domain.ProductosServicios;
import io.github.jhipster.newoapp13.repository.MargenNewoProductosRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoProductosSearchRepository;
import io.github.jhipster.newoapp13.service.MargenNewoProductosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.MargenNewoProductosCriteria;
import io.github.jhipster.newoapp13.service.MargenNewoProductosQueryService;

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
 * Integration tests for the {@link MargenNewoProductosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class MargenNewoProductosResourceIT {

    private static final Integer DEFAULT_PORCENTAJE_MARGEN = 1;
    private static final Integer UPDATED_PORCENTAJE_MARGEN = 2;
    private static final Integer SMALLER_PORCENTAJE_MARGEN = 1 - 1;

    @Autowired
    private MargenNewoProductosRepository margenNewoProductosRepository;

    @Autowired
    private MargenNewoProductosService margenNewoProductosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.MargenNewoProductosSearchRepositoryMockConfiguration
     */
    @Autowired
    private MargenNewoProductosSearchRepository mockMargenNewoProductosSearchRepository;

    @Autowired
    private MargenNewoProductosQueryService margenNewoProductosQueryService;

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

    private MockMvc restMargenNewoProductosMockMvc;

    private MargenNewoProductos margenNewoProductos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MargenNewoProductosResource margenNewoProductosResource = new MargenNewoProductosResource(margenNewoProductosService, margenNewoProductosQueryService);
        this.restMargenNewoProductosMockMvc = MockMvcBuilders.standaloneSetup(margenNewoProductosResource)
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
    public static MargenNewoProductos createEntity(EntityManager em) {
        MargenNewoProductos margenNewoProductos = new MargenNewoProductos()
            .porcentajeMargen(DEFAULT_PORCENTAJE_MARGEN);
        return margenNewoProductos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MargenNewoProductos createUpdatedEntity(EntityManager em) {
        MargenNewoProductos margenNewoProductos = new MargenNewoProductos()
            .porcentajeMargen(UPDATED_PORCENTAJE_MARGEN);
        return margenNewoProductos;
    }

    @BeforeEach
    public void initTest() {
        margenNewoProductos = createEntity(em);
    }

    @Test
    @Transactional
    public void createMargenNewoProductos() throws Exception {
        int databaseSizeBeforeCreate = margenNewoProductosRepository.findAll().size();

        // Create the MargenNewoProductos
        restMargenNewoProductosMockMvc.perform(post("/api/margen-newo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(margenNewoProductos)))
            .andExpect(status().isCreated());

        // Validate the MargenNewoProductos in the database
        List<MargenNewoProductos> margenNewoProductosList = margenNewoProductosRepository.findAll();
        assertThat(margenNewoProductosList).hasSize(databaseSizeBeforeCreate + 1);
        MargenNewoProductos testMargenNewoProductos = margenNewoProductosList.get(margenNewoProductosList.size() - 1);
        assertThat(testMargenNewoProductos.getPorcentajeMargen()).isEqualTo(DEFAULT_PORCENTAJE_MARGEN);

        // Validate the MargenNewoProductos in Elasticsearch
        verify(mockMargenNewoProductosSearchRepository, times(1)).save(testMargenNewoProductos);
    }

    @Test
    @Transactional
    public void createMargenNewoProductosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = margenNewoProductosRepository.findAll().size();

        // Create the MargenNewoProductos with an existing ID
        margenNewoProductos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMargenNewoProductosMockMvc.perform(post("/api/margen-newo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(margenNewoProductos)))
            .andExpect(status().isBadRequest());

        // Validate the MargenNewoProductos in the database
        List<MargenNewoProductos> margenNewoProductosList = margenNewoProductosRepository.findAll();
        assertThat(margenNewoProductosList).hasSize(databaseSizeBeforeCreate);

        // Validate the MargenNewoProductos in Elasticsearch
        verify(mockMargenNewoProductosSearchRepository, times(0)).save(margenNewoProductos);
    }


    @Test
    @Transactional
    public void getAllMargenNewoProductos() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(margenNewoProductos.getId().intValue())))
            .andExpect(jsonPath("$.[*].porcentajeMargen").value(hasItem(DEFAULT_PORCENTAJE_MARGEN)));
    }
    
    @Test
    @Transactional
    public void getMargenNewoProductos() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get the margenNewoProductos
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos/{id}", margenNewoProductos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(margenNewoProductos.getId().intValue()))
            .andExpect(jsonPath("$.porcentajeMargen").value(DEFAULT_PORCENTAJE_MARGEN));
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen equals to DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.equals=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoProductosList where porcentajeMargen equals to UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.equals=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsInShouldWork() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen in DEFAULT_PORCENTAJE_MARGEN or UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.in=" + DEFAULT_PORCENTAJE_MARGEN + "," + UPDATED_PORCENTAJE_MARGEN);

        // Get all the margenNewoProductosList where porcentajeMargen equals to UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.in=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsNullOrNotNull() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen is not null
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.specified=true");

        // Get all the margenNewoProductosList where porcentajeMargen is null
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.specified=false");
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen is greater than or equal to DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.greaterThanOrEqual=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoProductosList where porcentajeMargen is greater than or equal to UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.greaterThanOrEqual=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen is less than or equal to DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.lessThanOrEqual=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoProductosList where porcentajeMargen is less than or equal to SMALLER_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.lessThanOrEqual=" + SMALLER_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsLessThanSomething() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen is less than DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.lessThan=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoProductosList where porcentajeMargen is less than UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.lessThan=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoProductosByPorcentajeMargenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);

        // Get all the margenNewoProductosList where porcentajeMargen is greater than DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldNotBeFound("porcentajeMargen.greaterThan=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoProductosList where porcentajeMargen is greater than SMALLER_PORCENTAJE_MARGEN
        defaultMargenNewoProductosShouldBeFound("porcentajeMargen.greaterThan=" + SMALLER_PORCENTAJE_MARGEN);
    }


    @Test
    @Transactional
    public void getAllMargenNewoProductosByProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);
        ProductosServicios producto = ProductosServiciosResourceIT.createEntity(em);
        em.persist(producto);
        em.flush();
        margenNewoProductos.setProducto(producto);
        margenNewoProductosRepository.saveAndFlush(margenNewoProductos);
        Long productoId = producto.getId();

        // Get all the margenNewoProductosList where producto equals to productoId
        defaultMargenNewoProductosShouldBeFound("productoId.equals=" + productoId);

        // Get all the margenNewoProductosList where producto equals to productoId + 1
        defaultMargenNewoProductosShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMargenNewoProductosShouldBeFound(String filter) throws Exception {
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(margenNewoProductos.getId().intValue())))
            .andExpect(jsonPath("$.[*].porcentajeMargen").value(hasItem(DEFAULT_PORCENTAJE_MARGEN)));

        // Check, that the count call also returns 1
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMargenNewoProductosShouldNotBeFound(String filter) throws Exception {
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMargenNewoProductos() throws Exception {
        // Get the margenNewoProductos
        restMargenNewoProductosMockMvc.perform(get("/api/margen-newo-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMargenNewoProductos() throws Exception {
        // Initialize the database
        margenNewoProductosService.save(margenNewoProductos);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMargenNewoProductosSearchRepository);

        int databaseSizeBeforeUpdate = margenNewoProductosRepository.findAll().size();

        // Update the margenNewoProductos
        MargenNewoProductos updatedMargenNewoProductos = margenNewoProductosRepository.findById(margenNewoProductos.getId()).get();
        // Disconnect from session so that the updates on updatedMargenNewoProductos are not directly saved in db
        em.detach(updatedMargenNewoProductos);
        updatedMargenNewoProductos
            .porcentajeMargen(UPDATED_PORCENTAJE_MARGEN);

        restMargenNewoProductosMockMvc.perform(put("/api/margen-newo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMargenNewoProductos)))
            .andExpect(status().isOk());

        // Validate the MargenNewoProductos in the database
        List<MargenNewoProductos> margenNewoProductosList = margenNewoProductosRepository.findAll();
        assertThat(margenNewoProductosList).hasSize(databaseSizeBeforeUpdate);
        MargenNewoProductos testMargenNewoProductos = margenNewoProductosList.get(margenNewoProductosList.size() - 1);
        assertThat(testMargenNewoProductos.getPorcentajeMargen()).isEqualTo(UPDATED_PORCENTAJE_MARGEN);

        // Validate the MargenNewoProductos in Elasticsearch
        verify(mockMargenNewoProductosSearchRepository, times(1)).save(testMargenNewoProductos);
    }

    @Test
    @Transactional
    public void updateNonExistingMargenNewoProductos() throws Exception {
        int databaseSizeBeforeUpdate = margenNewoProductosRepository.findAll().size();

        // Create the MargenNewoProductos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMargenNewoProductosMockMvc.perform(put("/api/margen-newo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(margenNewoProductos)))
            .andExpect(status().isBadRequest());

        // Validate the MargenNewoProductos in the database
        List<MargenNewoProductos> margenNewoProductosList = margenNewoProductosRepository.findAll();
        assertThat(margenNewoProductosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MargenNewoProductos in Elasticsearch
        verify(mockMargenNewoProductosSearchRepository, times(0)).save(margenNewoProductos);
    }

    @Test
    @Transactional
    public void deleteMargenNewoProductos() throws Exception {
        // Initialize the database
        margenNewoProductosService.save(margenNewoProductos);

        int databaseSizeBeforeDelete = margenNewoProductosRepository.findAll().size();

        // Delete the margenNewoProductos
        restMargenNewoProductosMockMvc.perform(delete("/api/margen-newo-productos/{id}", margenNewoProductos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MargenNewoProductos> margenNewoProductosList = margenNewoProductosRepository.findAll();
        assertThat(margenNewoProductosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MargenNewoProductos in Elasticsearch
        verify(mockMargenNewoProductosSearchRepository, times(1)).deleteById(margenNewoProductos.getId());
    }

    @Test
    @Transactional
    public void searchMargenNewoProductos() throws Exception {
        // Initialize the database
        margenNewoProductosService.save(margenNewoProductos);
        when(mockMargenNewoProductosSearchRepository.search(queryStringQuery("id:" + margenNewoProductos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(margenNewoProductos), PageRequest.of(0, 1), 1));
        // Search the margenNewoProductos
        restMargenNewoProductosMockMvc.perform(get("/api/_search/margen-newo-productos?query=id:" + margenNewoProductos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(margenNewoProductos.getId().intValue())))
            .andExpect(jsonPath("$.[*].porcentajeMargen").value(hasItem(DEFAULT_PORCENTAJE_MARGEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MargenNewoProductos.class);
        MargenNewoProductos margenNewoProductos1 = new MargenNewoProductos();
        margenNewoProductos1.setId(1L);
        MargenNewoProductos margenNewoProductos2 = new MargenNewoProductos();
        margenNewoProductos2.setId(margenNewoProductos1.getId());
        assertThat(margenNewoProductos1).isEqualTo(margenNewoProductos2);
        margenNewoProductos2.setId(2L);
        assertThat(margenNewoProductos1).isNotEqualTo(margenNewoProductos2);
        margenNewoProductos1.setId(null);
        assertThat(margenNewoProductos1).isNotEqualTo(margenNewoProductos2);
    }
}
