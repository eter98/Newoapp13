package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas;
import io.github.jhipster.newoapp13.domain.Empresa;
import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.MiembrosEquipoEmpresasRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosEquipoEmpresasSearchRepository;
import io.github.jhipster.newoapp13.service.MiembrosEquipoEmpresasService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.MiembrosEquipoEmpresasCriteria;
import io.github.jhipster.newoapp13.service.MiembrosEquipoEmpresasQueryService;

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
 * Integration tests for the {@link MiembrosEquipoEmpresasResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class MiembrosEquipoEmpresasResourceIT {

    @Autowired
    private MiembrosEquipoEmpresasRepository miembrosEquipoEmpresasRepository;

    @Autowired
    private MiembrosEquipoEmpresasService miembrosEquipoEmpresasService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.MiembrosEquipoEmpresasSearchRepositoryMockConfiguration
     */
    @Autowired
    private MiembrosEquipoEmpresasSearchRepository mockMiembrosEquipoEmpresasSearchRepository;

    @Autowired
    private MiembrosEquipoEmpresasQueryService miembrosEquipoEmpresasQueryService;

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

    private MockMvc restMiembrosEquipoEmpresasMockMvc;

    private MiembrosEquipoEmpresas miembrosEquipoEmpresas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MiembrosEquipoEmpresasResource miembrosEquipoEmpresasResource = new MiembrosEquipoEmpresasResource(miembrosEquipoEmpresasService, miembrosEquipoEmpresasQueryService);
        this.restMiembrosEquipoEmpresasMockMvc = MockMvcBuilders.standaloneSetup(miembrosEquipoEmpresasResource)
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
    public static MiembrosEquipoEmpresas createEntity(EntityManager em) {
        MiembrosEquipoEmpresas miembrosEquipoEmpresas = new MiembrosEquipoEmpresas();
        return miembrosEquipoEmpresas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiembrosEquipoEmpresas createUpdatedEntity(EntityManager em) {
        MiembrosEquipoEmpresas miembrosEquipoEmpresas = new MiembrosEquipoEmpresas();
        return miembrosEquipoEmpresas;
    }

    @BeforeEach
    public void initTest() {
        miembrosEquipoEmpresas = createEntity(em);
    }

    @Test
    @Transactional
    public void createMiembrosEquipoEmpresas() throws Exception {
        int databaseSizeBeforeCreate = miembrosEquipoEmpresasRepository.findAll().size();

        // Create the MiembrosEquipoEmpresas
        restMiembrosEquipoEmpresasMockMvc.perform(post("/api/miembros-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembrosEquipoEmpresas)))
            .andExpect(status().isCreated());

        // Validate the MiembrosEquipoEmpresas in the database
        List<MiembrosEquipoEmpresas> miembrosEquipoEmpresasList = miembrosEquipoEmpresasRepository.findAll();
        assertThat(miembrosEquipoEmpresasList).hasSize(databaseSizeBeforeCreate + 1);
        MiembrosEquipoEmpresas testMiembrosEquipoEmpresas = miembrosEquipoEmpresasList.get(miembrosEquipoEmpresasList.size() - 1);

        // Validate the MiembrosEquipoEmpresas in Elasticsearch
        verify(mockMiembrosEquipoEmpresasSearchRepository, times(1)).save(testMiembrosEquipoEmpresas);
    }

    @Test
    @Transactional
    public void createMiembrosEquipoEmpresasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = miembrosEquipoEmpresasRepository.findAll().size();

        // Create the MiembrosEquipoEmpresas with an existing ID
        miembrosEquipoEmpresas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMiembrosEquipoEmpresasMockMvc.perform(post("/api/miembros-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembrosEquipoEmpresas)))
            .andExpect(status().isBadRequest());

        // Validate the MiembrosEquipoEmpresas in the database
        List<MiembrosEquipoEmpresas> miembrosEquipoEmpresasList = miembrosEquipoEmpresasRepository.findAll();
        assertThat(miembrosEquipoEmpresasList).hasSize(databaseSizeBeforeCreate);

        // Validate the MiembrosEquipoEmpresas in Elasticsearch
        verify(mockMiembrosEquipoEmpresasSearchRepository, times(0)).save(miembrosEquipoEmpresas);
    }


    @Test
    @Transactional
    public void getAllMiembrosEquipoEmpresas() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);

        // Get all the miembrosEquipoEmpresasList
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembrosEquipoEmpresas.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMiembrosEquipoEmpresas() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);

        // Get the miembrosEquipoEmpresas
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas/{id}", miembrosEquipoEmpresas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(miembrosEquipoEmpresas.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllMiembrosEquipoEmpresasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        miembrosEquipoEmpresas.setEmpresa(empresa);
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);
        Long empresaId = empresa.getId();

        // Get all the miembrosEquipoEmpresasList where empresa equals to empresaId
        defaultMiembrosEquipoEmpresasShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the miembrosEquipoEmpresasList where empresa equals to empresaId + 1
        defaultMiembrosEquipoEmpresasShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllMiembrosEquipoEmpresasByEquipoIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);
        EquipoEmpresas equipo = EquipoEmpresasResourceIT.createEntity(em);
        em.persist(equipo);
        em.flush();
        miembrosEquipoEmpresas.setEquipo(equipo);
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);
        Long equipoId = equipo.getId();

        // Get all the miembrosEquipoEmpresasList where equipo equals to equipoId
        defaultMiembrosEquipoEmpresasShouldBeFound("equipoId.equals=" + equipoId);

        // Get all the miembrosEquipoEmpresasList where equipo equals to equipoId + 1
        defaultMiembrosEquipoEmpresasShouldNotBeFound("equipoId.equals=" + (equipoId + 1));
    }


    @Test
    @Transactional
    public void getAllMiembrosEquipoEmpresasByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        miembrosEquipoEmpresas.setMiembro(miembro);
        miembrosEquipoEmpresasRepository.saveAndFlush(miembrosEquipoEmpresas);
        Long miembroId = miembro.getId();

        // Get all the miembrosEquipoEmpresasList where miembro equals to miembroId
        defaultMiembrosEquipoEmpresasShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the miembrosEquipoEmpresasList where miembro equals to miembroId + 1
        defaultMiembrosEquipoEmpresasShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMiembrosEquipoEmpresasShouldBeFound(String filter) throws Exception {
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembrosEquipoEmpresas.getId().intValue())));

        // Check, that the count call also returns 1
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMiembrosEquipoEmpresasShouldNotBeFound(String filter) throws Exception {
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMiembrosEquipoEmpresas() throws Exception {
        // Get the miembrosEquipoEmpresas
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/miembros-equipo-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMiembrosEquipoEmpresas() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasService.save(miembrosEquipoEmpresas);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMiembrosEquipoEmpresasSearchRepository);

        int databaseSizeBeforeUpdate = miembrosEquipoEmpresasRepository.findAll().size();

        // Update the miembrosEquipoEmpresas
        MiembrosEquipoEmpresas updatedMiembrosEquipoEmpresas = miembrosEquipoEmpresasRepository.findById(miembrosEquipoEmpresas.getId()).get();
        // Disconnect from session so that the updates on updatedMiembrosEquipoEmpresas are not directly saved in db
        em.detach(updatedMiembrosEquipoEmpresas);

        restMiembrosEquipoEmpresasMockMvc.perform(put("/api/miembros-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMiembrosEquipoEmpresas)))
            .andExpect(status().isOk());

        // Validate the MiembrosEquipoEmpresas in the database
        List<MiembrosEquipoEmpresas> miembrosEquipoEmpresasList = miembrosEquipoEmpresasRepository.findAll();
        assertThat(miembrosEquipoEmpresasList).hasSize(databaseSizeBeforeUpdate);
        MiembrosEquipoEmpresas testMiembrosEquipoEmpresas = miembrosEquipoEmpresasList.get(miembrosEquipoEmpresasList.size() - 1);

        // Validate the MiembrosEquipoEmpresas in Elasticsearch
        verify(mockMiembrosEquipoEmpresasSearchRepository, times(1)).save(testMiembrosEquipoEmpresas);
    }

    @Test
    @Transactional
    public void updateNonExistingMiembrosEquipoEmpresas() throws Exception {
        int databaseSizeBeforeUpdate = miembrosEquipoEmpresasRepository.findAll().size();

        // Create the MiembrosEquipoEmpresas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiembrosEquipoEmpresasMockMvc.perform(put("/api/miembros-equipo-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(miembrosEquipoEmpresas)))
            .andExpect(status().isBadRequest());

        // Validate the MiembrosEquipoEmpresas in the database
        List<MiembrosEquipoEmpresas> miembrosEquipoEmpresasList = miembrosEquipoEmpresasRepository.findAll();
        assertThat(miembrosEquipoEmpresasList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MiembrosEquipoEmpresas in Elasticsearch
        verify(mockMiembrosEquipoEmpresasSearchRepository, times(0)).save(miembrosEquipoEmpresas);
    }

    @Test
    @Transactional
    public void deleteMiembrosEquipoEmpresas() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasService.save(miembrosEquipoEmpresas);

        int databaseSizeBeforeDelete = miembrosEquipoEmpresasRepository.findAll().size();

        // Delete the miembrosEquipoEmpresas
        restMiembrosEquipoEmpresasMockMvc.perform(delete("/api/miembros-equipo-empresas/{id}", miembrosEquipoEmpresas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MiembrosEquipoEmpresas> miembrosEquipoEmpresasList = miembrosEquipoEmpresasRepository.findAll();
        assertThat(miembrosEquipoEmpresasList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MiembrosEquipoEmpresas in Elasticsearch
        verify(mockMiembrosEquipoEmpresasSearchRepository, times(1)).deleteById(miembrosEquipoEmpresas.getId());
    }

    @Test
    @Transactional
    public void searchMiembrosEquipoEmpresas() throws Exception {
        // Initialize the database
        miembrosEquipoEmpresasService.save(miembrosEquipoEmpresas);
        when(mockMiembrosEquipoEmpresasSearchRepository.search(queryStringQuery("id:" + miembrosEquipoEmpresas.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(miembrosEquipoEmpresas), PageRequest.of(0, 1), 1));
        // Search the miembrosEquipoEmpresas
        restMiembrosEquipoEmpresasMockMvc.perform(get("/api/_search/miembros-equipo-empresas?query=id:" + miembrosEquipoEmpresas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miembrosEquipoEmpresas.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiembrosEquipoEmpresas.class);
        MiembrosEquipoEmpresas miembrosEquipoEmpresas1 = new MiembrosEquipoEmpresas();
        miembrosEquipoEmpresas1.setId(1L);
        MiembrosEquipoEmpresas miembrosEquipoEmpresas2 = new MiembrosEquipoEmpresas();
        miembrosEquipoEmpresas2.setId(miembrosEquipoEmpresas1.getId());
        assertThat(miembrosEquipoEmpresas1).isEqualTo(miembrosEquipoEmpresas2);
        miembrosEquipoEmpresas2.setId(2L);
        assertThat(miembrosEquipoEmpresas1).isNotEqualTo(miembrosEquipoEmpresas2);
        miembrosEquipoEmpresas1.setId(null);
        assertThat(miembrosEquipoEmpresas1).isNotEqualTo(miembrosEquipoEmpresas2);
    }
}
