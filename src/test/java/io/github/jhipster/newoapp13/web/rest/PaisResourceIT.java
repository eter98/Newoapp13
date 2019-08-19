package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Pais;
import io.github.jhipster.newoapp13.repository.PaisRepository;
import io.github.jhipster.newoapp13.repository.search.PaisSearchRepository;
import io.github.jhipster.newoapp13.service.PaisService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.PaisCriteria;
import io.github.jhipster.newoapp13.service.PaisQueryService;

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
 * Integration tests for the {@link PaisResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class PaisResourceIT {

    private static final String DEFAULT_NOMBRE_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PAIS = "BBBBBBBBBB";

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisService paisService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.PaisSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaisSearchRepository mockPaisSearchRepository;

    @Autowired
    private PaisQueryService paisQueryService;

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

    private MockMvc restPaisMockMvc;

    private Pais pais;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaisResource paisResource = new PaisResource(paisService, paisQueryService);
        this.restPaisMockMvc = MockMvcBuilders.standaloneSetup(paisResource)
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
    public static Pais createEntity(EntityManager em) {
        Pais pais = new Pais()
            .nombrePais(DEFAULT_NOMBRE_PAIS);
        return pais;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pais createUpdatedEntity(EntityManager em) {
        Pais pais = new Pais()
            .nombrePais(UPDATED_NOMBRE_PAIS);
        return pais;
    }

    @BeforeEach
    public void initTest() {
        pais = createEntity(em);
    }

    @Test
    @Transactional
    public void createPais() throws Exception {
        int databaseSizeBeforeCreate = paisRepository.findAll().size();

        // Create the Pais
        restPaisMockMvc.perform(post("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isCreated());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeCreate + 1);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getNombrePais()).isEqualTo(DEFAULT_NOMBRE_PAIS);

        // Validate the Pais in Elasticsearch
        verify(mockPaisSearchRepository, times(1)).save(testPais);
    }

    @Test
    @Transactional
    public void createPaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paisRepository.findAll().size();

        // Create the Pais with an existing ID
        pais.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaisMockMvc.perform(post("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pais in Elasticsearch
        verify(mockPaisSearchRepository, times(0)).save(pais);
    }


    @Test
    @Transactional
    public void checkNombrePaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = paisRepository.findAll().size();
        // set the field null
        pais.setNombrePais(null);

        // Create the Pais, which fails.

        restPaisMockMvc.perform(post("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isBadRequest());

        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList
        restPaisMockMvc.perform(get("/api/pais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePais").value(hasItem(DEFAULT_NOMBRE_PAIS.toString())));
    }
    
    @Test
    @Transactional
    public void getPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get the pais
        restPaisMockMvc.perform(get("/api/pais/{id}", pais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pais.getId().intValue()))
            .andExpect(jsonPath("$.nombrePais").value(DEFAULT_NOMBRE_PAIS.toString()));
    }

    @Test
    @Transactional
    public void getAllPaisByNombrePaisIsEqualToSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais equals to DEFAULT_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.equals=" + DEFAULT_NOMBRE_PAIS);

        // Get all the paisList where nombrePais equals to UPDATED_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.equals=" + UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    public void getAllPaisByNombrePaisIsInShouldWork() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais in DEFAULT_NOMBRE_PAIS or UPDATED_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.in=" + DEFAULT_NOMBRE_PAIS + "," + UPDATED_NOMBRE_PAIS);

        // Get all the paisList where nombrePais equals to UPDATED_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.in=" + UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    public void getAllPaisByNombrePaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais is not null
        defaultPaisShouldBeFound("nombrePais.specified=true");

        // Get all the paisList where nombrePais is null
        defaultPaisShouldNotBeFound("nombrePais.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaisShouldBeFound(String filter) throws Exception {
        restPaisMockMvc.perform(get("/api/pais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePais").value(hasItem(DEFAULT_NOMBRE_PAIS)));

        // Check, that the count call also returns 1
        restPaisMockMvc.perform(get("/api/pais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaisShouldNotBeFound(String filter) throws Exception {
        restPaisMockMvc.perform(get("/api/pais?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaisMockMvc.perform(get("/api/pais/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPais() throws Exception {
        // Get the pais
        restPaisMockMvc.perform(get("/api/pais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePais() throws Exception {
        // Initialize the database
        paisService.save(pais);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPaisSearchRepository);

        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Update the pais
        Pais updatedPais = paisRepository.findById(pais.getId()).get();
        // Disconnect from session so that the updates on updatedPais are not directly saved in db
        em.detach(updatedPais);
        updatedPais
            .nombrePais(UPDATED_NOMBRE_PAIS);

        restPaisMockMvc.perform(put("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPais)))
            .andExpect(status().isOk());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getNombrePais()).isEqualTo(UPDATED_NOMBRE_PAIS);

        // Validate the Pais in Elasticsearch
        verify(mockPaisSearchRepository, times(1)).save(testPais);
    }

    @Test
    @Transactional
    public void updateNonExistingPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Create the Pais

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaisMockMvc.perform(put("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pais in Elasticsearch
        verify(mockPaisSearchRepository, times(0)).save(pais);
    }

    @Test
    @Transactional
    public void deletePais() throws Exception {
        // Initialize the database
        paisService.save(pais);

        int databaseSizeBeforeDelete = paisRepository.findAll().size();

        // Delete the pais
        restPaisMockMvc.perform(delete("/api/pais/{id}", pais.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pais in Elasticsearch
        verify(mockPaisSearchRepository, times(1)).deleteById(pais.getId());
    }

    @Test
    @Transactional
    public void searchPais() throws Exception {
        // Initialize the database
        paisService.save(pais);
        when(mockPaisSearchRepository.search(queryStringQuery("id:" + pais.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pais), PageRequest.of(0, 1), 1));
        // Search the pais
        restPaisMockMvc.perform(get("/api/_search/pais?query=id:" + pais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePais").value(hasItem(DEFAULT_NOMBRE_PAIS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pais.class);
        Pais pais1 = new Pais();
        pais1.setId(1L);
        Pais pais2 = new Pais();
        pais2.setId(pais1.getId());
        assertThat(pais1).isEqualTo(pais2);
        pais2.setId(2L);
        assertThat(pais1).isNotEqualTo(pais2);
        pais1.setId(null);
        assertThat(pais1).isNotEqualTo(pais2);
    }
}
