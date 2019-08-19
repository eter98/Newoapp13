package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.MiembrosGrupo;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.MiembrosGrupoRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosGrupoSearchRepository;
import io.github.jhipster.newoapp13.service.MiembrosGrupoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.MiembrosGrupoCriteria;
import io.github.jhipster.newoapp13.service.MiembrosGrupoQueryService;

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
 * Integration tests for the {@link MiembrosGrupoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class MiembrosGrupoResourceIT {

    @Autowired
    private MiembrosGrupoRepository miembrosGrupoRepository;

    @Autowired
    private MiembrosGrupoService miembrosGrupoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.MiembrosGrupoSearchRepositoryMockConfiguration
     */
    @Autowired
    private MiembrosGrupoSearchRepository mockMiembrosGrupoSearchRepository;

    @Autowired
    private MiembrosGrupoQueryService miembrosGrupoQueryService;

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

    private MockMvc restMiembrosGrupoMockMvc;

    private MiembrosGrupo miembrosGrupo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MiembrosGrupoResource miembrosGrupoResource = new MiembrosGrupoResource(miembrosGrupoService, miembrosGrupoQueryService);
        this.restMiembrosGrupoMockMvc = MockMvcBuilders.standaloneSetup(miembrosGrupoResource)
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
    public static MiembrosGrupo createEntity(EntityManager em) {
        MiembrosGrupo miembrosGrupo = new MiembrosGrupo();
        return miembrosGrupo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiembrosGrupo createUpdatedEntity(EntityManager em) {
        MiembrosGrupo miembrosGrupo = new MiembrosGrupo();
        return miembrosGrupo;
    }

    @BeforeEach
    public void initTest() {
        miembrosGrupo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMiembrosGrupo() throws Exception {
        int databaseSizeBeforeCreate = miembrosGrupoRepository.findAll().size();

        // Create the MiembrosGrupo
        restMiembrosGrupoMockMvc.perform(post("/api/miembros-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembrosGrupo)))
            .andExpect(status().isCreated());

        // Validate the MiembrosGrupo in the database
        List<MiembrosGrupo> miembrosGrupoList = miembrosGrupoRepository.findAll();
        assertThat(miembrosGrupoList).hasSize(databaseSizeBeforeCreate + 1);
        MiembrosGrupo testMiembrosGrupo = miembrosGrupoList.get(miembrosGrupoList.size() - 1);

        // Validate the MiembrosGrupo in Elasticsearch
        verify(mockMiembrosGrupoSearchRepository, times(1)).save(testMiembrosGrupo);
    }

    @Test
    @Transactional
    public void createMiembrosGrupoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = miembrosGrupoRepository.findAll().size();

        // Create the MiembrosGrupo with an existing ID
        miembrosGrupo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMiembrosGrupoMockMvc.perform(post("/api/miembros-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembrosGrupo)))
            .andExpect(status().isBadRequest());

        // Validate the MiembrosGrupo in the database
        List<MiembrosGrupo> miembrosGrupoList = miembrosGrupoRepository.findAll();
        assertThat(miembrosGrupoList).hasSize(databaseSizeBeforeCreate);

        // Validate the MiembrosGrupo in Elasticsearch
        verify(mockMiembrosGrupoSearchRepository, times(0)).save(miembrosGrupo);
    }


    @Test
    @Transactional
    public void getAllMiembrosGrupos() throws Exception {
        // Initialize the database
        miembrosGrupoRepository.saveAndFlush(miembrosGrupo);

        // Get all the miembrosGrupoList
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembrosGrupo.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMiembrosGrupo() throws Exception {
        // Initialize the database
        miembrosGrupoRepository.saveAndFlush(miembrosGrupo);

        // Get the miembrosGrupo
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos/{id}", miembrosGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(miembrosGrupo.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllMiembrosGruposByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosGrupoRepository.saveAndFlush(miembrosGrupo);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        miembrosGrupo.setMiembro(miembro);
        miembrosGrupoRepository.saveAndFlush(miembrosGrupo);
        Long miembroId = miembro.getId();

        // Get all the miembrosGrupoList where miembro equals to miembroId
        defaultMiembrosGrupoShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the miembrosGrupoList where miembro equals to miembroId + 1
        defaultMiembrosGrupoShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMiembrosGrupoShouldBeFound(String filter) throws Exception {
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembrosGrupo.getId().intValue())));

        // Check, that the count call also returns 1
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMiembrosGrupoShouldNotBeFound(String filter) throws Exception {
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMiembrosGrupo() throws Exception {
        // Get the miembrosGrupo
        restMiembrosGrupoMockMvc.perform(get("/api/miembros-grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMiembrosGrupo() throws Exception {
        // Initialize the database
        miembrosGrupoService.save(miembrosGrupo);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMiembrosGrupoSearchRepository);

        int databaseSizeBeforeUpdate = miembrosGrupoRepository.findAll().size();

        // Update the miembrosGrupo
        MiembrosGrupo updatedMiembrosGrupo = miembrosGrupoRepository.findById(miembrosGrupo.getId()).get();
        // Disconnect from session so that the updates on updatedMiembrosGrupo are not directly saved in db
        em.detach(updatedMiembrosGrupo);

        restMiembrosGrupoMockMvc.perform(put("/api/miembros-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMiembrosGrupo)))
            .andExpect(status().isOk());

        // Validate the MiembrosGrupo in the database
        List<MiembrosGrupo> miembrosGrupoList = miembrosGrupoRepository.findAll();
        assertThat(miembrosGrupoList).hasSize(databaseSizeBeforeUpdate);
        MiembrosGrupo testMiembrosGrupo = miembrosGrupoList.get(miembrosGrupoList.size() - 1);

        // Validate the MiembrosGrupo in Elasticsearch
        verify(mockMiembrosGrupoSearchRepository, times(1)).save(testMiembrosGrupo);
    }

    @Test
    @Transactional
    public void updateNonExistingMiembrosGrupo() throws Exception {
        int databaseSizeBeforeUpdate = miembrosGrupoRepository.findAll().size();

        // Create the MiembrosGrupo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiembrosGrupoMockMvc.perform(put("/api/miembros-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembrosGrupo)))
            .andExpect(status().isBadRequest());

        // Validate the MiembrosGrupo in the database
        List<MiembrosGrupo> miembrosGrupoList = miembrosGrupoRepository.findAll();
        assertThat(miembrosGrupoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MiembrosGrupo in Elasticsearch
        verify(mockMiembrosGrupoSearchRepository, times(0)).save(miembrosGrupo);
    }

    @Test
    @Transactional
    public void deleteMiembrosGrupo() throws Exception {
        // Initialize the database
        miembrosGrupoService.save(miembrosGrupo);

        int databaseSizeBeforeDelete = miembrosGrupoRepository.findAll().size();

        // Delete the miembrosGrupo
        restMiembrosGrupoMockMvc.perform(delete("/api/miembros-grupos/{id}", miembrosGrupo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MiembrosGrupo> miembrosGrupoList = miembrosGrupoRepository.findAll();
        assertThat(miembrosGrupoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MiembrosGrupo in Elasticsearch
        verify(mockMiembrosGrupoSearchRepository, times(1)).deleteById(miembrosGrupo.getId());
    }

    @Test
    @Transactional
    public void searchMiembrosGrupo() throws Exception {
        // Initialize the database
        miembrosGrupoService.save(miembrosGrupo);
        when(mockMiembrosGrupoSearchRepository.search(queryStringQuery("id:" + miembrosGrupo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(miembrosGrupo), PageRequest.of(0, 1), 1));
        // Search the miembrosGrupo
        restMiembrosGrupoMockMvc.perform(get("/api/_search/miembros-grupos?query=id:" + miembrosGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembrosGrupo.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiembrosGrupo.class);
        MiembrosGrupo miembrosGrupo1 = new MiembrosGrupo();
        miembrosGrupo1.setId(1L);
        MiembrosGrupo miembrosGrupo2 = new MiembrosGrupo();
        miembrosGrupo2.setId(miembrosGrupo1.getId());
        assertThat(miembrosGrupo1).isEqualTo(miembrosGrupo2);
        miembrosGrupo2.setId(2L);
        assertThat(miembrosGrupo1).isNotEqualTo(miembrosGrupo2);
        miembrosGrupo1.setId(null);
        assertThat(miembrosGrupo1).isNotEqualTo(miembrosGrupo2);
    }
}
