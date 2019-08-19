package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.MargenNewoGrupos;
import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.repository.MargenNewoGruposRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoGruposSearchRepository;
import io.github.jhipster.newoapp13.service.MargenNewoGruposService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.MargenNewoGruposCriteria;
import io.github.jhipster.newoapp13.service.MargenNewoGruposQueryService;

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
 * Integration tests for the {@link MargenNewoGruposResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class MargenNewoGruposResourceIT {

    private static final Integer DEFAULT_PORCENTAJE_MARGEN = 1;
    private static final Integer UPDATED_PORCENTAJE_MARGEN = 2;
    private static final Integer SMALLER_PORCENTAJE_MARGEN = 1 - 1;

    @Autowired
    private MargenNewoGruposRepository margenNewoGruposRepository;

    @Autowired
    private MargenNewoGruposService margenNewoGruposService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.MargenNewoGruposSearchRepositoryMockConfiguration
     */
    @Autowired
    private MargenNewoGruposSearchRepository mockMargenNewoGruposSearchRepository;

    @Autowired
    private MargenNewoGruposQueryService margenNewoGruposQueryService;

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

    private MockMvc restMargenNewoGruposMockMvc;

    private MargenNewoGrupos margenNewoGrupos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MargenNewoGruposResource margenNewoGruposResource = new MargenNewoGruposResource(margenNewoGruposService, margenNewoGruposQueryService);
        this.restMargenNewoGruposMockMvc = MockMvcBuilders.standaloneSetup(margenNewoGruposResource)
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
    public static MargenNewoGrupos createEntity(EntityManager em) {
        MargenNewoGrupos margenNewoGrupos = new MargenNewoGrupos()
            .porcentajeMargen(DEFAULT_PORCENTAJE_MARGEN);
        return margenNewoGrupos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MargenNewoGrupos createUpdatedEntity(EntityManager em) {
        MargenNewoGrupos margenNewoGrupos = new MargenNewoGrupos()
            .porcentajeMargen(UPDATED_PORCENTAJE_MARGEN);
        return margenNewoGrupos;
    }

    @BeforeEach
    public void initTest() {
        margenNewoGrupos = createEntity(em);
    }

    @Test
    @Transactional
    public void createMargenNewoGrupos() throws Exception {
        int databaseSizeBeforeCreate = margenNewoGruposRepository.findAll().size();

        // Create the MargenNewoGrupos
        restMargenNewoGruposMockMvc.perform(post("/api/margen-newo-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(margenNewoGrupos)))
            .andExpect(status().isCreated());

        // Validate the MargenNewoGrupos in the database
        List<MargenNewoGrupos> margenNewoGruposList = margenNewoGruposRepository.findAll();
        assertThat(margenNewoGruposList).hasSize(databaseSizeBeforeCreate + 1);
        MargenNewoGrupos testMargenNewoGrupos = margenNewoGruposList.get(margenNewoGruposList.size() - 1);
        assertThat(testMargenNewoGrupos.getPorcentajeMargen()).isEqualTo(DEFAULT_PORCENTAJE_MARGEN);

        // Validate the MargenNewoGrupos in Elasticsearch
        verify(mockMargenNewoGruposSearchRepository, times(1)).save(testMargenNewoGrupos);
    }

    @Test
    @Transactional
    public void createMargenNewoGruposWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = margenNewoGruposRepository.findAll().size();

        // Create the MargenNewoGrupos with an existing ID
        margenNewoGrupos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMargenNewoGruposMockMvc.perform(post("/api/margen-newo-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(margenNewoGrupos)))
            .andExpect(status().isBadRequest());

        // Validate the MargenNewoGrupos in the database
        List<MargenNewoGrupos> margenNewoGruposList = margenNewoGruposRepository.findAll();
        assertThat(margenNewoGruposList).hasSize(databaseSizeBeforeCreate);

        // Validate the MargenNewoGrupos in Elasticsearch
        verify(mockMargenNewoGruposSearchRepository, times(0)).save(margenNewoGrupos);
    }


    @Test
    @Transactional
    public void getAllMargenNewoGrupos() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(margenNewoGrupos.getId().intValue())))
            .andExpect(jsonPath("$.[*].porcentajeMargen").value(hasItem(DEFAULT_PORCENTAJE_MARGEN)));
    }
    
    @Test
    @Transactional
    public void getMargenNewoGrupos() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get the margenNewoGrupos
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos/{id}", margenNewoGrupos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(margenNewoGrupos.getId().intValue()))
            .andExpect(jsonPath("$.porcentajeMargen").value(DEFAULT_PORCENTAJE_MARGEN));
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen equals to DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.equals=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoGruposList where porcentajeMargen equals to UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.equals=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsInShouldWork() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen in DEFAULT_PORCENTAJE_MARGEN or UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.in=" + DEFAULT_PORCENTAJE_MARGEN + "," + UPDATED_PORCENTAJE_MARGEN);

        // Get all the margenNewoGruposList where porcentajeMargen equals to UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.in=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsNullOrNotNull() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen is not null
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.specified=true");

        // Get all the margenNewoGruposList where porcentajeMargen is null
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.specified=false");
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen is greater than or equal to DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.greaterThanOrEqual=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoGruposList where porcentajeMargen is greater than or equal to UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.greaterThanOrEqual=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen is less than or equal to DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.lessThanOrEqual=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoGruposList where porcentajeMargen is less than or equal to SMALLER_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.lessThanOrEqual=" + SMALLER_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsLessThanSomething() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen is less than DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.lessThan=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoGruposList where porcentajeMargen is less than UPDATED_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.lessThan=" + UPDATED_PORCENTAJE_MARGEN);
    }

    @Test
    @Transactional
    public void getAllMargenNewoGruposByPorcentajeMargenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);

        // Get all the margenNewoGruposList where porcentajeMargen is greater than DEFAULT_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldNotBeFound("porcentajeMargen.greaterThan=" + DEFAULT_PORCENTAJE_MARGEN);

        // Get all the margenNewoGruposList where porcentajeMargen is greater than SMALLER_PORCENTAJE_MARGEN
        defaultMargenNewoGruposShouldBeFound("porcentajeMargen.greaterThan=" + SMALLER_PORCENTAJE_MARGEN);
    }


    @Test
    @Transactional
    public void getAllMargenNewoGruposByGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);
        Grupos grupo = GruposResourceIT.createEntity(em);
        em.persist(grupo);
        em.flush();
        margenNewoGrupos.setGrupo(grupo);
        margenNewoGruposRepository.saveAndFlush(margenNewoGrupos);
        Long grupoId = grupo.getId();

        // Get all the margenNewoGruposList where grupo equals to grupoId
        defaultMargenNewoGruposShouldBeFound("grupoId.equals=" + grupoId);

        // Get all the margenNewoGruposList where grupo equals to grupoId + 1
        defaultMargenNewoGruposShouldNotBeFound("grupoId.equals=" + (grupoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMargenNewoGruposShouldBeFound(String filter) throws Exception {
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(margenNewoGrupos.getId().intValue())))
            .andExpect(jsonPath("$.[*].porcentajeMargen").value(hasItem(DEFAULT_PORCENTAJE_MARGEN)));

        // Check, that the count call also returns 1
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMargenNewoGruposShouldNotBeFound(String filter) throws Exception {
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMargenNewoGrupos() throws Exception {
        // Get the margenNewoGrupos
        restMargenNewoGruposMockMvc.perform(get("/api/margen-newo-grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMargenNewoGrupos() throws Exception {
        // Initialize the database
        margenNewoGruposService.save(margenNewoGrupos);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMargenNewoGruposSearchRepository);

        int databaseSizeBeforeUpdate = margenNewoGruposRepository.findAll().size();

        // Update the margenNewoGrupos
        MargenNewoGrupos updatedMargenNewoGrupos = margenNewoGruposRepository.findById(margenNewoGrupos.getId()).get();
        // Disconnect from session so that the updates on updatedMargenNewoGrupos are not directly saved in db
        em.detach(updatedMargenNewoGrupos);
        updatedMargenNewoGrupos
            .porcentajeMargen(UPDATED_PORCENTAJE_MARGEN);

        restMargenNewoGruposMockMvc.perform(put("/api/margen-newo-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMargenNewoGrupos)))
            .andExpect(status().isOk());

        // Validate the MargenNewoGrupos in the database
        List<MargenNewoGrupos> margenNewoGruposList = margenNewoGruposRepository.findAll();
        assertThat(margenNewoGruposList).hasSize(databaseSizeBeforeUpdate);
        MargenNewoGrupos testMargenNewoGrupos = margenNewoGruposList.get(margenNewoGruposList.size() - 1);
        assertThat(testMargenNewoGrupos.getPorcentajeMargen()).isEqualTo(UPDATED_PORCENTAJE_MARGEN);

        // Validate the MargenNewoGrupos in Elasticsearch
        verify(mockMargenNewoGruposSearchRepository, times(1)).save(testMargenNewoGrupos);
    }

    @Test
    @Transactional
    public void updateNonExistingMargenNewoGrupos() throws Exception {
        int databaseSizeBeforeUpdate = margenNewoGruposRepository.findAll().size();

        // Create the MargenNewoGrupos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMargenNewoGruposMockMvc.perform(put("/api/margen-newo-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(margenNewoGrupos)))
            .andExpect(status().isBadRequest());

        // Validate the MargenNewoGrupos in the database
        List<MargenNewoGrupos> margenNewoGruposList = margenNewoGruposRepository.findAll();
        assertThat(margenNewoGruposList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MargenNewoGrupos in Elasticsearch
        verify(mockMargenNewoGruposSearchRepository, times(0)).save(margenNewoGrupos);
    }

    @Test
    @Transactional
    public void deleteMargenNewoGrupos() throws Exception {
        // Initialize the database
        margenNewoGruposService.save(margenNewoGrupos);

        int databaseSizeBeforeDelete = margenNewoGruposRepository.findAll().size();

        // Delete the margenNewoGrupos
        restMargenNewoGruposMockMvc.perform(delete("/api/margen-newo-grupos/{id}", margenNewoGrupos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MargenNewoGrupos> margenNewoGruposList = margenNewoGruposRepository.findAll();
        assertThat(margenNewoGruposList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MargenNewoGrupos in Elasticsearch
        verify(mockMargenNewoGruposSearchRepository, times(1)).deleteById(margenNewoGrupos.getId());
    }

    @Test
    @Transactional
    public void searchMargenNewoGrupos() throws Exception {
        // Initialize the database
        margenNewoGruposService.save(margenNewoGrupos);
        when(mockMargenNewoGruposSearchRepository.search(queryStringQuery("id:" + margenNewoGrupos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(margenNewoGrupos), PageRequest.of(0, 1), 1));
        // Search the margenNewoGrupos
        restMargenNewoGruposMockMvc.perform(get("/api/_search/margen-newo-grupos?query=id:" + margenNewoGrupos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(margenNewoGrupos.getId().intValue())))
            .andExpect(jsonPath("$.[*].porcentajeMargen").value(hasItem(DEFAULT_PORCENTAJE_MARGEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MargenNewoGrupos.class);
        MargenNewoGrupos margenNewoGrupos1 = new MargenNewoGrupos();
        margenNewoGrupos1.setId(1L);
        MargenNewoGrupos margenNewoGrupos2 = new MargenNewoGrupos();
        margenNewoGrupos2.setId(margenNewoGrupos1.getId());
        assertThat(margenNewoGrupos1).isEqualTo(margenNewoGrupos2);
        margenNewoGrupos2.setId(2L);
        assertThat(margenNewoGrupos1).isNotEqualTo(margenNewoGrupos2);
        margenNewoGrupos1.setId(null);
        assertThat(margenNewoGrupos1).isNotEqualTo(margenNewoGrupos2);
    }
}
