package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.RegistroCompra;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import io.github.jhipster.newoapp13.repository.RegistroCompraRepository;
import io.github.jhipster.newoapp13.repository.search.RegistroCompraSearchRepository;
import io.github.jhipster.newoapp13.service.RegistroCompraService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.RegistroCompraCriteria;
import io.github.jhipster.newoapp13.service.RegistroCompraQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link RegistroCompraResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class RegistroCompraResourceIT {

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;
    private static final Integer SMALLER_VALOR = 1 - 1;

    private static final Instant DEFAULT_FECHA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA_REGISTRO = Instant.ofEpochMilli(-1L);

    private static final Integer DEFAULT_ID_TRANSACCION = 1;
    private static final Integer UPDATED_ID_TRANSACCION = 2;
    private static final Integer SMALLER_ID_TRANSACCION = 1 - 1;

    @Autowired
    private RegistroCompraRepository registroCompraRepository;

    @Autowired
    private RegistroCompraService registroCompraService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.RegistroCompraSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegistroCompraSearchRepository mockRegistroCompraSearchRepository;

    @Autowired
    private RegistroCompraQueryService registroCompraQueryService;

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

    private MockMvc restRegistroCompraMockMvc;

    private RegistroCompra registroCompra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistroCompraResource registroCompraResource = new RegistroCompraResource(registroCompraService, registroCompraQueryService);
        this.restRegistroCompraMockMvc = MockMvcBuilders.standaloneSetup(registroCompraResource)
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
    public static RegistroCompra createEntity(EntityManager em) {
        RegistroCompra registroCompra = new RegistroCompra()
            .valor(DEFAULT_VALOR)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .idTransaccion(DEFAULT_ID_TRANSACCION);
        return registroCompra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroCompra createUpdatedEntity(EntityManager em) {
        RegistroCompra registroCompra = new RegistroCompra()
            .valor(UPDATED_VALOR)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .idTransaccion(UPDATED_ID_TRANSACCION);
        return registroCompra;
    }

    @BeforeEach
    public void initTest() {
        registroCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistroCompra() throws Exception {
        int databaseSizeBeforeCreate = registroCompraRepository.findAll().size();

        // Create the RegistroCompra
        restRegistroCompraMockMvc.perform(post("/api/registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroCompra)))
            .andExpect(status().isCreated());

        // Validate the RegistroCompra in the database
        List<RegistroCompra> registroCompraList = registroCompraRepository.findAll();
        assertThat(registroCompraList).hasSize(databaseSizeBeforeCreate + 1);
        RegistroCompra testRegistroCompra = registroCompraList.get(registroCompraList.size() - 1);
        assertThat(testRegistroCompra.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testRegistroCompra.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testRegistroCompra.getIdTransaccion()).isEqualTo(DEFAULT_ID_TRANSACCION);

        // Validate the RegistroCompra in Elasticsearch
        verify(mockRegistroCompraSearchRepository, times(1)).save(testRegistroCompra);
    }

    @Test
    @Transactional
    public void createRegistroCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registroCompraRepository.findAll().size();

        // Create the RegistroCompra with an existing ID
        registroCompra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroCompraMockMvc.perform(post("/api/registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroCompra)))
            .andExpect(status().isBadRequest());

        // Validate the RegistroCompra in the database
        List<RegistroCompra> registroCompraList = registroCompraRepository.findAll();
        assertThat(registroCompraList).hasSize(databaseSizeBeforeCreate);

        // Validate the RegistroCompra in Elasticsearch
        verify(mockRegistroCompraSearchRepository, times(0)).save(registroCompra);
    }


    @Test
    @Transactional
    public void getAllRegistroCompras() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList
        restRegistroCompraMockMvc.perform(get("/api/registro-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].idTransaccion").value(hasItem(DEFAULT_ID_TRANSACCION)));
    }
    
    @Test
    @Transactional
    public void getRegistroCompra() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get the registroCompra
        restRegistroCompraMockMvc.perform(get("/api/registro-compras/{id}", registroCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registroCompra.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.idTransaccion").value(DEFAULT_ID_TRANSACCION));
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor equals to DEFAULT_VALOR
        defaultRegistroCompraShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the registroCompraList where valor equals to UPDATED_VALOR
        defaultRegistroCompraShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultRegistroCompraShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the registroCompraList where valor equals to UPDATED_VALOR
        defaultRegistroCompraShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor is not null
        defaultRegistroCompraShouldBeFound("valor.specified=true");

        // Get all the registroCompraList where valor is null
        defaultRegistroCompraShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor is greater than or equal to DEFAULT_VALOR
        defaultRegistroCompraShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the registroCompraList where valor is greater than or equal to UPDATED_VALOR
        defaultRegistroCompraShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor is less than or equal to DEFAULT_VALOR
        defaultRegistroCompraShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the registroCompraList where valor is less than or equal to SMALLER_VALOR
        defaultRegistroCompraShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor is less than DEFAULT_VALOR
        defaultRegistroCompraShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the registroCompraList where valor is less than UPDATED_VALOR
        defaultRegistroCompraShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where valor is greater than DEFAULT_VALOR
        defaultRegistroCompraShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the registroCompraList where valor is greater than SMALLER_VALOR
        defaultRegistroCompraShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllRegistroComprasByFechaRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where fechaRegistro equals to DEFAULT_FECHA_REGISTRO
        defaultRegistroCompraShouldBeFound("fechaRegistro.equals=" + DEFAULT_FECHA_REGISTRO);

        // Get all the registroCompraList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultRegistroCompraShouldNotBeFound("fechaRegistro.equals=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByFechaRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where fechaRegistro in DEFAULT_FECHA_REGISTRO or UPDATED_FECHA_REGISTRO
        defaultRegistroCompraShouldBeFound("fechaRegistro.in=" + DEFAULT_FECHA_REGISTRO + "," + UPDATED_FECHA_REGISTRO);

        // Get all the registroCompraList where fechaRegistro equals to UPDATED_FECHA_REGISTRO
        defaultRegistroCompraShouldNotBeFound("fechaRegistro.in=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByFechaRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where fechaRegistro is not null
        defaultRegistroCompraShouldBeFound("fechaRegistro.specified=true");

        // Get all the registroCompraList where fechaRegistro is null
        defaultRegistroCompraShouldNotBeFound("fechaRegistro.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion equals to DEFAULT_ID_TRANSACCION
        defaultRegistroCompraShouldBeFound("idTransaccion.equals=" + DEFAULT_ID_TRANSACCION);

        // Get all the registroCompraList where idTransaccion equals to UPDATED_ID_TRANSACCION
        defaultRegistroCompraShouldNotBeFound("idTransaccion.equals=" + UPDATED_ID_TRANSACCION);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsInShouldWork() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion in DEFAULT_ID_TRANSACCION or UPDATED_ID_TRANSACCION
        defaultRegistroCompraShouldBeFound("idTransaccion.in=" + DEFAULT_ID_TRANSACCION + "," + UPDATED_ID_TRANSACCION);

        // Get all the registroCompraList where idTransaccion equals to UPDATED_ID_TRANSACCION
        defaultRegistroCompraShouldNotBeFound("idTransaccion.in=" + UPDATED_ID_TRANSACCION);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion is not null
        defaultRegistroCompraShouldBeFound("idTransaccion.specified=true");

        // Get all the registroCompraList where idTransaccion is null
        defaultRegistroCompraShouldNotBeFound("idTransaccion.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion is greater than or equal to DEFAULT_ID_TRANSACCION
        defaultRegistroCompraShouldBeFound("idTransaccion.greaterThanOrEqual=" + DEFAULT_ID_TRANSACCION);

        // Get all the registroCompraList where idTransaccion is greater than or equal to UPDATED_ID_TRANSACCION
        defaultRegistroCompraShouldNotBeFound("idTransaccion.greaterThanOrEqual=" + UPDATED_ID_TRANSACCION);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion is less than or equal to DEFAULT_ID_TRANSACCION
        defaultRegistroCompraShouldBeFound("idTransaccion.lessThanOrEqual=" + DEFAULT_ID_TRANSACCION);

        // Get all the registroCompraList where idTransaccion is less than or equal to SMALLER_ID_TRANSACCION
        defaultRegistroCompraShouldNotBeFound("idTransaccion.lessThanOrEqual=" + SMALLER_ID_TRANSACCION);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsLessThanSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion is less than DEFAULT_ID_TRANSACCION
        defaultRegistroCompraShouldNotBeFound("idTransaccion.lessThan=" + DEFAULT_ID_TRANSACCION);

        // Get all the registroCompraList where idTransaccion is less than UPDATED_ID_TRANSACCION
        defaultRegistroCompraShouldBeFound("idTransaccion.lessThan=" + UPDATED_ID_TRANSACCION);
    }

    @Test
    @Transactional
    public void getAllRegistroComprasByIdTransaccionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);

        // Get all the registroCompraList where idTransaccion is greater than DEFAULT_ID_TRANSACCION
        defaultRegistroCompraShouldNotBeFound("idTransaccion.greaterThan=" + DEFAULT_ID_TRANSACCION);

        // Get all the registroCompraList where idTransaccion is greater than SMALLER_ID_TRANSACCION
        defaultRegistroCompraShouldBeFound("idTransaccion.greaterThan=" + SMALLER_ID_TRANSACCION);
    }


    @Test
    @Transactional
    public void getAllRegistroComprasByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        registroCompra.setMiembro(miembro);
        registroCompraRepository.saveAndFlush(registroCompra);
        Long miembroId = miembro.getId();

        // Get all the registroCompraList where miembro equals to miembroId
        defaultRegistroCompraShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the registroCompraList where miembro equals to miembroId + 1
        defaultRegistroCompraShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }


    @Test
    @Transactional
    public void getAllRegistroComprasByTipoRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        registroCompraRepository.saveAndFlush(registroCompra);
        TipoRegistroCompra tipoRegistro = TipoRegistroCompraResourceIT.createEntity(em);
        em.persist(tipoRegistro);
        em.flush();
        registroCompra.setTipoRegistro(tipoRegistro);
        registroCompraRepository.saveAndFlush(registroCompra);
        Long tipoRegistroId = tipoRegistro.getId();

        // Get all the registroCompraList where tipoRegistro equals to tipoRegistroId
        defaultRegistroCompraShouldBeFound("tipoRegistroId.equals=" + tipoRegistroId);

        // Get all the registroCompraList where tipoRegistro equals to tipoRegistroId + 1
        defaultRegistroCompraShouldNotBeFound("tipoRegistroId.equals=" + (tipoRegistroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegistroCompraShouldBeFound(String filter) throws Exception {
        restRegistroCompraMockMvc.perform(get("/api/registro-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].idTransaccion").value(hasItem(DEFAULT_ID_TRANSACCION)));

        // Check, that the count call also returns 1
        restRegistroCompraMockMvc.perform(get("/api/registro-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegistroCompraShouldNotBeFound(String filter) throws Exception {
        restRegistroCompraMockMvc.perform(get("/api/registro-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegistroCompraMockMvc.perform(get("/api/registro-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegistroCompra() throws Exception {
        // Get the registroCompra
        restRegistroCompraMockMvc.perform(get("/api/registro-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistroCompra() throws Exception {
        // Initialize the database
        registroCompraService.save(registroCompra);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRegistroCompraSearchRepository);

        int databaseSizeBeforeUpdate = registroCompraRepository.findAll().size();

        // Update the registroCompra
        RegistroCompra updatedRegistroCompra = registroCompraRepository.findById(registroCompra.getId()).get();
        // Disconnect from session so that the updates on updatedRegistroCompra are not directly saved in db
        em.detach(updatedRegistroCompra);
        updatedRegistroCompra
            .valor(UPDATED_VALOR)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .idTransaccion(UPDATED_ID_TRANSACCION);

        restRegistroCompraMockMvc.perform(put("/api/registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistroCompra)))
            .andExpect(status().isOk());

        // Validate the RegistroCompra in the database
        List<RegistroCompra> registroCompraList = registroCompraRepository.findAll();
        assertThat(registroCompraList).hasSize(databaseSizeBeforeUpdate);
        RegistroCompra testRegistroCompra = registroCompraList.get(registroCompraList.size() - 1);
        assertThat(testRegistroCompra.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testRegistroCompra.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testRegistroCompra.getIdTransaccion()).isEqualTo(UPDATED_ID_TRANSACCION);

        // Validate the RegistroCompra in Elasticsearch
        verify(mockRegistroCompraSearchRepository, times(1)).save(testRegistroCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistroCompra() throws Exception {
        int databaseSizeBeforeUpdate = registroCompraRepository.findAll().size();

        // Create the RegistroCompra

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroCompraMockMvc.perform(put("/api/registro-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroCompra)))
            .andExpect(status().isBadRequest());

        // Validate the RegistroCompra in the database
        List<RegistroCompra> registroCompraList = registroCompraRepository.findAll();
        assertThat(registroCompraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RegistroCompra in Elasticsearch
        verify(mockRegistroCompraSearchRepository, times(0)).save(registroCompra);
    }

    @Test
    @Transactional
    public void deleteRegistroCompra() throws Exception {
        // Initialize the database
        registroCompraService.save(registroCompra);

        int databaseSizeBeforeDelete = registroCompraRepository.findAll().size();

        // Delete the registroCompra
        restRegistroCompraMockMvc.perform(delete("/api/registro-compras/{id}", registroCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistroCompra> registroCompraList = registroCompraRepository.findAll();
        assertThat(registroCompraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RegistroCompra in Elasticsearch
        verify(mockRegistroCompraSearchRepository, times(1)).deleteById(registroCompra.getId());
    }

    @Test
    @Transactional
    public void searchRegistroCompra() throws Exception {
        // Initialize the database
        registroCompraService.save(registroCompra);
        when(mockRegistroCompraSearchRepository.search(queryStringQuery("id:" + registroCompra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(registroCompra), PageRequest.of(0, 1), 1));
        // Search the registroCompra
        restRegistroCompraMockMvc.perform(get("/api/_search/registro-compras?query=id:" + registroCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].idTransaccion").value(hasItem(DEFAULT_ID_TRANSACCION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroCompra.class);
        RegistroCompra registroCompra1 = new RegistroCompra();
        registroCompra1.setId(1L);
        RegistroCompra registroCompra2 = new RegistroCompra();
        registroCompra2.setId(registroCompra1.getId());
        assertThat(registroCompra1).isEqualTo(registroCompra2);
        registroCompra2.setId(2L);
        assertThat(registroCompra1).isNotEqualTo(registroCompra2);
        registroCompra1.setId(null);
        assertThat(registroCompra1).isNotEqualTo(registroCompra2);
    }
}
