package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Beneficio;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.BeneficioRepository;
import io.github.jhipster.newoapp13.repository.search.BeneficioSearchRepository;
import io.github.jhipster.newoapp13.service.BeneficioService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.BeneficioCriteria;
import io.github.jhipster.newoapp13.service.BeneficioQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.Beneficiosd;
/**
 * Integration tests for the {@link BeneficioResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class BeneficioResourceIT {

    private static final Beneficiosd DEFAULT_TIPO_BENEFICIO = Beneficiosd.Market;
    private static final Beneficiosd UPDATED_TIPO_BENEFICIO = Beneficiosd.Entrada_Miembro;

    private static final Integer DEFAULT_DESCUENTO = 1;
    private static final Integer UPDATED_DESCUENTO = 2;
    private static final Integer SMALLER_DESCUENTO = 1 - 1;

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private BeneficioService beneficioService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.BeneficioSearchRepositoryMockConfiguration
     */
    @Autowired
    private BeneficioSearchRepository mockBeneficioSearchRepository;

    @Autowired
    private BeneficioQueryService beneficioQueryService;

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

    private MockMvc restBeneficioMockMvc;

    private Beneficio beneficio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficioResource beneficioResource = new BeneficioResource(beneficioService, beneficioQueryService);
        this.restBeneficioMockMvc = MockMvcBuilders.standaloneSetup(beneficioResource)
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
    public static Beneficio createEntity(EntityManager em) {
        Beneficio beneficio = new Beneficio()
            .tipoBeneficio(DEFAULT_TIPO_BENEFICIO)
            .descuento(DEFAULT_DESCUENTO);
        return beneficio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficio createUpdatedEntity(EntityManager em) {
        Beneficio beneficio = new Beneficio()
            .tipoBeneficio(UPDATED_TIPO_BENEFICIO)
            .descuento(UPDATED_DESCUENTO);
        return beneficio;
    }

    @BeforeEach
    public void initTest() {
        beneficio = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficio() throws Exception {
        int databaseSizeBeforeCreate = beneficioRepository.findAll().size();

        // Create the Beneficio
        restBeneficioMockMvc.perform(post("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isCreated());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficio testBeneficio = beneficioList.get(beneficioList.size() - 1);
        assertThat(testBeneficio.getTipoBeneficio()).isEqualTo(DEFAULT_TIPO_BENEFICIO);
        assertThat(testBeneficio.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);

        // Validate the Beneficio in Elasticsearch
        verify(mockBeneficioSearchRepository, times(1)).save(testBeneficio);
    }

    @Test
    @Transactional
    public void createBeneficioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficioRepository.findAll().size();

        // Create the Beneficio with an existing ID
        beneficio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficioMockMvc.perform(post("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeCreate);

        // Validate the Beneficio in Elasticsearch
        verify(mockBeneficioSearchRepository, times(0)).save(beneficio);
    }


    @Test
    @Transactional
    public void checkTipoBeneficioIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficioRepository.findAll().size();
        // set the field null
        beneficio.setTipoBeneficio(null);

        // Create the Beneficio, which fails.

        restBeneficioMockMvc.perform(post("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isBadRequest());

        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescuentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficioRepository.findAll().size();
        // set the field null
        beneficio.setDescuento(null);

        // Create the Beneficio, which fails.

        restBeneficioMockMvc.perform(post("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isBadRequest());

        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficios() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList
        restBeneficioMockMvc.perform(get("/api/beneficios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoBeneficio").value(hasItem(DEFAULT_TIPO_BENEFICIO.toString())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)));
    }
    
    @Test
    @Transactional
    public void getBeneficio() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get the beneficio
        restBeneficioMockMvc.perform(get("/api/beneficios/{id}", beneficio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficio.getId().intValue()))
            .andExpect(jsonPath("$.tipoBeneficio").value(DEFAULT_TIPO_BENEFICIO.toString()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO));
    }

    @Test
    @Transactional
    public void getAllBeneficiosByTipoBeneficioIsEqualToSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where tipoBeneficio equals to DEFAULT_TIPO_BENEFICIO
        defaultBeneficioShouldBeFound("tipoBeneficio.equals=" + DEFAULT_TIPO_BENEFICIO);

        // Get all the beneficioList where tipoBeneficio equals to UPDATED_TIPO_BENEFICIO
        defaultBeneficioShouldNotBeFound("tipoBeneficio.equals=" + UPDATED_TIPO_BENEFICIO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByTipoBeneficioIsInShouldWork() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where tipoBeneficio in DEFAULT_TIPO_BENEFICIO or UPDATED_TIPO_BENEFICIO
        defaultBeneficioShouldBeFound("tipoBeneficio.in=" + DEFAULT_TIPO_BENEFICIO + "," + UPDATED_TIPO_BENEFICIO);

        // Get all the beneficioList where tipoBeneficio equals to UPDATED_TIPO_BENEFICIO
        defaultBeneficioShouldNotBeFound("tipoBeneficio.in=" + UPDATED_TIPO_BENEFICIO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByTipoBeneficioIsNullOrNotNull() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where tipoBeneficio is not null
        defaultBeneficioShouldBeFound("tipoBeneficio.specified=true");

        // Get all the beneficioList where tipoBeneficio is null
        defaultBeneficioShouldNotBeFound("tipoBeneficio.specified=false");
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsEqualToSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento equals to DEFAULT_DESCUENTO
        defaultBeneficioShouldBeFound("descuento.equals=" + DEFAULT_DESCUENTO);

        // Get all the beneficioList where descuento equals to UPDATED_DESCUENTO
        defaultBeneficioShouldNotBeFound("descuento.equals=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsInShouldWork() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento in DEFAULT_DESCUENTO or UPDATED_DESCUENTO
        defaultBeneficioShouldBeFound("descuento.in=" + DEFAULT_DESCUENTO + "," + UPDATED_DESCUENTO);

        // Get all the beneficioList where descuento equals to UPDATED_DESCUENTO
        defaultBeneficioShouldNotBeFound("descuento.in=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento is not null
        defaultBeneficioShouldBeFound("descuento.specified=true");

        // Get all the beneficioList where descuento is null
        defaultBeneficioShouldNotBeFound("descuento.specified=false");
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento is greater than or equal to DEFAULT_DESCUENTO
        defaultBeneficioShouldBeFound("descuento.greaterThanOrEqual=" + DEFAULT_DESCUENTO);

        // Get all the beneficioList where descuento is greater than or equal to UPDATED_DESCUENTO
        defaultBeneficioShouldNotBeFound("descuento.greaterThanOrEqual=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento is less than or equal to DEFAULT_DESCUENTO
        defaultBeneficioShouldBeFound("descuento.lessThanOrEqual=" + DEFAULT_DESCUENTO);

        // Get all the beneficioList where descuento is less than or equal to SMALLER_DESCUENTO
        defaultBeneficioShouldNotBeFound("descuento.lessThanOrEqual=" + SMALLER_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsLessThanSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento is less than DEFAULT_DESCUENTO
        defaultBeneficioShouldNotBeFound("descuento.lessThan=" + DEFAULT_DESCUENTO);

        // Get all the beneficioList where descuento is less than UPDATED_DESCUENTO
        defaultBeneficioShouldBeFound("descuento.lessThan=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllBeneficiosByDescuentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);

        // Get all the beneficioList where descuento is greater than DEFAULT_DESCUENTO
        defaultBeneficioShouldNotBeFound("descuento.greaterThan=" + DEFAULT_DESCUENTO);

        // Get all the beneficioList where descuento is greater than SMALLER_DESCUENTO
        defaultBeneficioShouldBeFound("descuento.greaterThan=" + SMALLER_DESCUENTO);
    }


    @Test
    @Transactional
    public void getAllBeneficiosByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        beneficioRepository.saveAndFlush(beneficio);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        beneficio.setMiembro(miembro);
        beneficioRepository.saveAndFlush(beneficio);
        Long miembroId = miembro.getId();

        // Get all the beneficioList where miembro equals to miembroId
        defaultBeneficioShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the beneficioList where miembro equals to miembroId + 1
        defaultBeneficioShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBeneficioShouldBeFound(String filter) throws Exception {
        restBeneficioMockMvc.perform(get("/api/beneficios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoBeneficio").value(hasItem(DEFAULT_TIPO_BENEFICIO.toString())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)));

        // Check, that the count call also returns 1
        restBeneficioMockMvc.perform(get("/api/beneficios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBeneficioShouldNotBeFound(String filter) throws Exception {
        restBeneficioMockMvc.perform(get("/api/beneficios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBeneficioMockMvc.perform(get("/api/beneficios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBeneficio() throws Exception {
        // Get the beneficio
        restBeneficioMockMvc.perform(get("/api/beneficios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficio() throws Exception {
        // Initialize the database
        beneficioService.save(beneficio);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBeneficioSearchRepository);

        int databaseSizeBeforeUpdate = beneficioRepository.findAll().size();

        // Update the beneficio
        Beneficio updatedBeneficio = beneficioRepository.findById(beneficio.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficio are not directly saved in db
        em.detach(updatedBeneficio);
        updatedBeneficio
            .tipoBeneficio(UPDATED_TIPO_BENEFICIO)
            .descuento(UPDATED_DESCUENTO);

        restBeneficioMockMvc.perform(put("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeneficio)))
            .andExpect(status().isOk());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeUpdate);
        Beneficio testBeneficio = beneficioList.get(beneficioList.size() - 1);
        assertThat(testBeneficio.getTipoBeneficio()).isEqualTo(UPDATED_TIPO_BENEFICIO);
        assertThat(testBeneficio.getDescuento()).isEqualTo(UPDATED_DESCUENTO);

        // Validate the Beneficio in Elasticsearch
        verify(mockBeneficioSearchRepository, times(1)).save(testBeneficio);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficio() throws Exception {
        int databaseSizeBeforeUpdate = beneficioRepository.findAll().size();

        // Create the Beneficio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficioMockMvc.perform(put("/api/beneficios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficio)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficio in the database
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Beneficio in Elasticsearch
        verify(mockBeneficioSearchRepository, times(0)).save(beneficio);
    }

    @Test
    @Transactional
    public void deleteBeneficio() throws Exception {
        // Initialize the database
        beneficioService.save(beneficio);

        int databaseSizeBeforeDelete = beneficioRepository.findAll().size();

        // Delete the beneficio
        restBeneficioMockMvc.perform(delete("/api/beneficios/{id}", beneficio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beneficio> beneficioList = beneficioRepository.findAll();
        assertThat(beneficioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Beneficio in Elasticsearch
        verify(mockBeneficioSearchRepository, times(1)).deleteById(beneficio.getId());
    }

    @Test
    @Transactional
    public void searchBeneficio() throws Exception {
        // Initialize the database
        beneficioService.save(beneficio);
        when(mockBeneficioSearchRepository.search(queryStringQuery("id:" + beneficio.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(beneficio), PageRequest.of(0, 1), 1));
        // Search the beneficio
        restBeneficioMockMvc.perform(get("/api/_search/beneficios?query=id:" + beneficio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoBeneficio").value(hasItem(DEFAULT_TIPO_BENEFICIO.toString())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beneficio.class);
        Beneficio beneficio1 = new Beneficio();
        beneficio1.setId(1L);
        Beneficio beneficio2 = new Beneficio();
        beneficio2.setId(beneficio1.getId());
        assertThat(beneficio1).isEqualTo(beneficio2);
        beneficio2.setId(2L);
        assertThat(beneficio1).isNotEqualTo(beneficio2);
        beneficio1.setId(null);
        assertThat(beneficio1).isNotEqualTo(beneficio2);
    }
}
