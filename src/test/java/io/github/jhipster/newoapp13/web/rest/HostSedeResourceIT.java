package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.HostSede;
import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.HostSedeRepository;
import io.github.jhipster.newoapp13.repository.search.HostSedeSearchRepository;
import io.github.jhipster.newoapp13.service.HostSedeService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.HostSedeCriteria;
import io.github.jhipster.newoapp13.service.HostSedeQueryService;

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
 * Integration tests for the {@link HostSedeResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class HostSedeResourceIT {

    @Autowired
    private HostSedeRepository hostSedeRepository;

    @Autowired
    private HostSedeService hostSedeService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.HostSedeSearchRepositoryMockConfiguration
     */
    @Autowired
    private HostSedeSearchRepository mockHostSedeSearchRepository;

    @Autowired
    private HostSedeQueryService hostSedeQueryService;

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

    private MockMvc restHostSedeMockMvc;

    private HostSede hostSede;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HostSedeResource hostSedeResource = new HostSedeResource(hostSedeService, hostSedeQueryService);
        this.restHostSedeMockMvc = MockMvcBuilders.standaloneSetup(hostSedeResource)
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
    public static HostSede createEntity(EntityManager em) {
        HostSede hostSede = new HostSede();
        return hostSede;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HostSede createUpdatedEntity(EntityManager em) {
        HostSede hostSede = new HostSede();
        return hostSede;
    }

    @BeforeEach
    public void initTest() {
        hostSede = createEntity(em);
    }

    @Test
    @Transactional
    public void createHostSede() throws Exception {
        int databaseSizeBeforeCreate = hostSedeRepository.findAll().size();

        // Create the HostSede
        restHostSedeMockMvc.perform(post("/api/host-sedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostSede)))
            .andExpect(status().isCreated());

        // Validate the HostSede in the database
        List<HostSede> hostSedeList = hostSedeRepository.findAll();
        assertThat(hostSedeList).hasSize(databaseSizeBeforeCreate + 1);
        HostSede testHostSede = hostSedeList.get(hostSedeList.size() - 1);

        // Validate the HostSede in Elasticsearch
        verify(mockHostSedeSearchRepository, times(1)).save(testHostSede);
    }

    @Test
    @Transactional
    public void createHostSedeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hostSedeRepository.findAll().size();

        // Create the HostSede with an existing ID
        hostSede.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHostSedeMockMvc.perform(post("/api/host-sedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostSede)))
            .andExpect(status().isBadRequest());

        // Validate the HostSede in the database
        List<HostSede> hostSedeList = hostSedeRepository.findAll();
        assertThat(hostSedeList).hasSize(databaseSizeBeforeCreate);

        // Validate the HostSede in Elasticsearch
        verify(mockHostSedeSearchRepository, times(0)).save(hostSede);
    }


    @Test
    @Transactional
    public void getAllHostSedes() throws Exception {
        // Initialize the database
        hostSedeRepository.saveAndFlush(hostSede);

        // Get all the hostSedeList
        restHostSedeMockMvc.perform(get("/api/host-sedes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hostSede.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getHostSede() throws Exception {
        // Initialize the database
        hostSedeRepository.saveAndFlush(hostSede);

        // Get the hostSede
        restHostSedeMockMvc.perform(get("/api/host-sedes/{id}", hostSede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hostSede.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllHostSedesBySedeIsEqualToSomething() throws Exception {
        // Initialize the database
        hostSedeRepository.saveAndFlush(hostSede);
        Sedes sede = SedesResourceIT.createEntity(em);
        em.persist(sede);
        em.flush();
        hostSede.setSede(sede);
        hostSedeRepository.saveAndFlush(hostSede);
        Long sedeId = sede.getId();

        // Get all the hostSedeList where sede equals to sedeId
        defaultHostSedeShouldBeFound("sedeId.equals=" + sedeId);

        // Get all the hostSedeList where sede equals to sedeId + 1
        defaultHostSedeShouldNotBeFound("sedeId.equals=" + (sedeId + 1));
    }


    @Test
    @Transactional
    public void getAllHostSedesByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        hostSedeRepository.saveAndFlush(hostSede);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        hostSede.setMiembro(miembro);
        hostSedeRepository.saveAndFlush(hostSede);
        Long miembroId = miembro.getId();

        // Get all the hostSedeList where miembro equals to miembroId
        defaultHostSedeShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the hostSedeList where miembro equals to miembroId + 1
        defaultHostSedeShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHostSedeShouldBeFound(String filter) throws Exception {
        restHostSedeMockMvc.perform(get("/api/host-sedes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hostSede.getId().intValue())));

        // Check, that the count call also returns 1
        restHostSedeMockMvc.perform(get("/api/host-sedes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHostSedeShouldNotBeFound(String filter) throws Exception {
        restHostSedeMockMvc.perform(get("/api/host-sedes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHostSedeMockMvc.perform(get("/api/host-sedes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHostSede() throws Exception {
        // Get the hostSede
        restHostSedeMockMvc.perform(get("/api/host-sedes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHostSede() throws Exception {
        // Initialize the database
        hostSedeService.save(hostSede);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockHostSedeSearchRepository);

        int databaseSizeBeforeUpdate = hostSedeRepository.findAll().size();

        // Update the hostSede
        HostSede updatedHostSede = hostSedeRepository.findById(hostSede.getId()).get();
        // Disconnect from session so that the updates on updatedHostSede are not directly saved in db
        em.detach(updatedHostSede);

        restHostSedeMockMvc.perform(put("/api/host-sedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHostSede)))
            .andExpect(status().isOk());

        // Validate the HostSede in the database
        List<HostSede> hostSedeList = hostSedeRepository.findAll();
        assertThat(hostSedeList).hasSize(databaseSizeBeforeUpdate);
        HostSede testHostSede = hostSedeList.get(hostSedeList.size() - 1);

        // Validate the HostSede in Elasticsearch
        verify(mockHostSedeSearchRepository, times(1)).save(testHostSede);
    }

    @Test
    @Transactional
    public void updateNonExistingHostSede() throws Exception {
        int databaseSizeBeforeUpdate = hostSedeRepository.findAll().size();

        // Create the HostSede

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHostSedeMockMvc.perform(put("/api/host-sedes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostSede)))
            .andExpect(status().isBadRequest());

        // Validate the HostSede in the database
        List<HostSede> hostSedeList = hostSedeRepository.findAll();
        assertThat(hostSedeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HostSede in Elasticsearch
        verify(mockHostSedeSearchRepository, times(0)).save(hostSede);
    }

    @Test
    @Transactional
    public void deleteHostSede() throws Exception {
        // Initialize the database
        hostSedeService.save(hostSede);

        int databaseSizeBeforeDelete = hostSedeRepository.findAll().size();

        // Delete the hostSede
        restHostSedeMockMvc.perform(delete("/api/host-sedes/{id}", hostSede.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HostSede> hostSedeList = hostSedeRepository.findAll();
        assertThat(hostSedeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HostSede in Elasticsearch
        verify(mockHostSedeSearchRepository, times(1)).deleteById(hostSede.getId());
    }

    @Test
    @Transactional
    public void searchHostSede() throws Exception {
        // Initialize the database
        hostSedeService.save(hostSede);
        when(mockHostSedeSearchRepository.search(queryStringQuery("id:" + hostSede.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(hostSede), PageRequest.of(0, 1), 1));
        // Search the hostSede
        restHostSedeMockMvc.perform(get("/api/_search/host-sedes?query=id:" + hostSede.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hostSede.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HostSede.class);
        HostSede hostSede1 = new HostSede();
        hostSede1.setId(1L);
        HostSede hostSede2 = new HostSede();
        hostSede2.setId(hostSede1.getId());
        assertThat(hostSede1).isEqualTo(hostSede2);
        hostSede2.setId(2L);
        assertThat(hostSede1).isNotEqualTo(hostSede2);
        hostSede1.setId(null);
        assertThat(hostSede1).isNotEqualTo(hostSede2);
    }
}
