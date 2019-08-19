package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.ChatsListado;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.Chat;
import io.github.jhipster.newoapp13.repository.ChatsListadoRepository;
import io.github.jhipster.newoapp13.repository.search.ChatsListadoSearchRepository;
import io.github.jhipster.newoapp13.service.ChatsListadoService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.ChatsListadoCriteria;
import io.github.jhipster.newoapp13.service.ChatsListadoQueryService;

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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.newoapp13.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.newoapp13.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.newoapp13.domain.enumeration.Estatusd;
/**
 * Integration tests for the {@link ChatsListadoResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class ChatsListadoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Estatusd DEFAULT_ESTATUS = Estatusd.EnLinea;
    private static final Estatusd UPDATED_ESTATUS = Estatusd.Desconectado;

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;
    private static final Integer SMALLER_COUNT = 1 - 1;

    private static final Integer DEFAULT_BADGE = 1;
    private static final Integer UPDATED_BADGE = 2;
    private static final Integer SMALLER_BADGE = 1 - 1;

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SEND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SEND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SEND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_GRUPO = false;
    private static final Boolean UPDATED_GRUPO = true;

    @Autowired
    private ChatsListadoRepository chatsListadoRepository;

    @Autowired
    private ChatsListadoService chatsListadoService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.ChatsListadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChatsListadoSearchRepository mockChatsListadoSearchRepository;

    @Autowired
    private ChatsListadoQueryService chatsListadoQueryService;

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

    private MockMvc restChatsListadoMockMvc;

    private ChatsListado chatsListado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChatsListadoResource chatsListadoResource = new ChatsListadoResource(chatsListadoService, chatsListadoQueryService);
        this.restChatsListadoMockMvc = MockMvcBuilders.standaloneSetup(chatsListadoResource)
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
    public static ChatsListado createEntity(EntityManager em) {
        ChatsListado chatsListado = new ChatsListado()
            .descripcion(DEFAULT_DESCRIPCION)
            .estatus(DEFAULT_ESTATUS)
            .count(DEFAULT_COUNT)
            .badge(DEFAULT_BADGE)
            .time(DEFAULT_TIME)
            .sendTime(DEFAULT_SEND_TIME)
            .grupo(DEFAULT_GRUPO);
        return chatsListado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChatsListado createUpdatedEntity(EntityManager em) {
        ChatsListado chatsListado = new ChatsListado()
            .descripcion(UPDATED_DESCRIPCION)
            .estatus(UPDATED_ESTATUS)
            .count(UPDATED_COUNT)
            .badge(UPDATED_BADGE)
            .time(UPDATED_TIME)
            .sendTime(UPDATED_SEND_TIME)
            .grupo(UPDATED_GRUPO);
        return chatsListado;
    }

    @BeforeEach
    public void initTest() {
        chatsListado = createEntity(em);
    }

    @Test
    @Transactional
    public void createChatsListado() throws Exception {
        int databaseSizeBeforeCreate = chatsListadoRepository.findAll().size();

        // Create the ChatsListado
        restChatsListadoMockMvc.perform(post("/api/chats-listados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatsListado)))
            .andExpect(status().isCreated());

        // Validate the ChatsListado in the database
        List<ChatsListado> chatsListadoList = chatsListadoRepository.findAll();
        assertThat(chatsListadoList).hasSize(databaseSizeBeforeCreate + 1);
        ChatsListado testChatsListado = chatsListadoList.get(chatsListadoList.size() - 1);
        assertThat(testChatsListado.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testChatsListado.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testChatsListado.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testChatsListado.getBadge()).isEqualTo(DEFAULT_BADGE);
        assertThat(testChatsListado.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testChatsListado.getSendTime()).isEqualTo(DEFAULT_SEND_TIME);
        assertThat(testChatsListado.isGrupo()).isEqualTo(DEFAULT_GRUPO);

        // Validate the ChatsListado in Elasticsearch
        verify(mockChatsListadoSearchRepository, times(1)).save(testChatsListado);
    }

    @Test
    @Transactional
    public void createChatsListadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatsListadoRepository.findAll().size();

        // Create the ChatsListado with an existing ID
        chatsListado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatsListadoMockMvc.perform(post("/api/chats-listados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatsListado)))
            .andExpect(status().isBadRequest());

        // Validate the ChatsListado in the database
        List<ChatsListado> chatsListadoList = chatsListadoRepository.findAll();
        assertThat(chatsListadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChatsListado in Elasticsearch
        verify(mockChatsListadoSearchRepository, times(0)).save(chatsListado);
    }


    @Test
    @Transactional
    public void getAllChatsListados() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList
        restChatsListadoMockMvc.perform(get("/api/chats-listados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatsListado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].badge").value(hasItem(DEFAULT_BADGE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].sendTime").value(hasItem(sameInstant(DEFAULT_SEND_TIME))))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getChatsListado() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get the chatsListado
        restChatsListadoMockMvc.perform(get("/api/chats-listados/{id}", chatsListado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chatsListado.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.badge").value(DEFAULT_BADGE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.sendTime").value(sameInstant(DEFAULT_SEND_TIME)))
            .andExpect(jsonPath("$.grupo").value(DEFAULT_GRUPO.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllChatsListadosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultChatsListadoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the chatsListadoList where descripcion equals to UPDATED_DESCRIPCION
        defaultChatsListadoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultChatsListadoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the chatsListadoList where descripcion equals to UPDATED_DESCRIPCION
        defaultChatsListadoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where descripcion is not null
        defaultChatsListadoShouldBeFound("descripcion.specified=true");

        // Get all the chatsListadoList where descripcion is null
        defaultChatsListadoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosByEstatusIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where estatus equals to DEFAULT_ESTATUS
        defaultChatsListadoShouldBeFound("estatus.equals=" + DEFAULT_ESTATUS);

        // Get all the chatsListadoList where estatus equals to UPDATED_ESTATUS
        defaultChatsListadoShouldNotBeFound("estatus.equals=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByEstatusIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where estatus in DEFAULT_ESTATUS or UPDATED_ESTATUS
        defaultChatsListadoShouldBeFound("estatus.in=" + DEFAULT_ESTATUS + "," + UPDATED_ESTATUS);

        // Get all the chatsListadoList where estatus equals to UPDATED_ESTATUS
        defaultChatsListadoShouldNotBeFound("estatus.in=" + UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByEstatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where estatus is not null
        defaultChatsListadoShouldBeFound("estatus.specified=true");

        // Get all the chatsListadoList where estatus is null
        defaultChatsListadoShouldNotBeFound("estatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count equals to DEFAULT_COUNT
        defaultChatsListadoShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the chatsListadoList where count equals to UPDATED_COUNT
        defaultChatsListadoShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultChatsListadoShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the chatsListadoList where count equals to UPDATED_COUNT
        defaultChatsListadoShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count is not null
        defaultChatsListadoShouldBeFound("count.specified=true");

        // Get all the chatsListadoList where count is null
        defaultChatsListadoShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count is greater than or equal to DEFAULT_COUNT
        defaultChatsListadoShouldBeFound("count.greaterThanOrEqual=" + DEFAULT_COUNT);

        // Get all the chatsListadoList where count is greater than or equal to UPDATED_COUNT
        defaultChatsListadoShouldNotBeFound("count.greaterThanOrEqual=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count is less than or equal to DEFAULT_COUNT
        defaultChatsListadoShouldBeFound("count.lessThanOrEqual=" + DEFAULT_COUNT);

        // Get all the chatsListadoList where count is less than or equal to SMALLER_COUNT
        defaultChatsListadoShouldNotBeFound("count.lessThanOrEqual=" + SMALLER_COUNT);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsLessThanSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count is less than DEFAULT_COUNT
        defaultChatsListadoShouldNotBeFound("count.lessThan=" + DEFAULT_COUNT);

        // Get all the chatsListadoList where count is less than UPDATED_COUNT
        defaultChatsListadoShouldBeFound("count.lessThan=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where count is greater than DEFAULT_COUNT
        defaultChatsListadoShouldNotBeFound("count.greaterThan=" + DEFAULT_COUNT);

        // Get all the chatsListadoList where count is greater than SMALLER_COUNT
        defaultChatsListadoShouldBeFound("count.greaterThan=" + SMALLER_COUNT);
    }


    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge equals to DEFAULT_BADGE
        defaultChatsListadoShouldBeFound("badge.equals=" + DEFAULT_BADGE);

        // Get all the chatsListadoList where badge equals to UPDATED_BADGE
        defaultChatsListadoShouldNotBeFound("badge.equals=" + UPDATED_BADGE);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge in DEFAULT_BADGE or UPDATED_BADGE
        defaultChatsListadoShouldBeFound("badge.in=" + DEFAULT_BADGE + "," + UPDATED_BADGE);

        // Get all the chatsListadoList where badge equals to UPDATED_BADGE
        defaultChatsListadoShouldNotBeFound("badge.in=" + UPDATED_BADGE);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge is not null
        defaultChatsListadoShouldBeFound("badge.specified=true");

        // Get all the chatsListadoList where badge is null
        defaultChatsListadoShouldNotBeFound("badge.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge is greater than or equal to DEFAULT_BADGE
        defaultChatsListadoShouldBeFound("badge.greaterThanOrEqual=" + DEFAULT_BADGE);

        // Get all the chatsListadoList where badge is greater than or equal to UPDATED_BADGE
        defaultChatsListadoShouldNotBeFound("badge.greaterThanOrEqual=" + UPDATED_BADGE);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge is less than or equal to DEFAULT_BADGE
        defaultChatsListadoShouldBeFound("badge.lessThanOrEqual=" + DEFAULT_BADGE);

        // Get all the chatsListadoList where badge is less than or equal to SMALLER_BADGE
        defaultChatsListadoShouldNotBeFound("badge.lessThanOrEqual=" + SMALLER_BADGE);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsLessThanSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge is less than DEFAULT_BADGE
        defaultChatsListadoShouldNotBeFound("badge.lessThan=" + DEFAULT_BADGE);

        // Get all the chatsListadoList where badge is less than UPDATED_BADGE
        defaultChatsListadoShouldBeFound("badge.lessThan=" + UPDATED_BADGE);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByBadgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where badge is greater than DEFAULT_BADGE
        defaultChatsListadoShouldNotBeFound("badge.greaterThan=" + DEFAULT_BADGE);

        // Get all the chatsListadoList where badge is greater than SMALLER_BADGE
        defaultChatsListadoShouldBeFound("badge.greaterThan=" + SMALLER_BADGE);
    }


    @Test
    @Transactional
    public void getAllChatsListadosByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where time equals to DEFAULT_TIME
        defaultChatsListadoShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the chatsListadoList where time equals to UPDATED_TIME
        defaultChatsListadoShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where time in DEFAULT_TIME or UPDATED_TIME
        defaultChatsListadoShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the chatsListadoList where time equals to UPDATED_TIME
        defaultChatsListadoShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where time is not null
        defaultChatsListadoShouldBeFound("time.specified=true");

        // Get all the chatsListadoList where time is null
        defaultChatsListadoShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime equals to DEFAULT_SEND_TIME
        defaultChatsListadoShouldBeFound("sendTime.equals=" + DEFAULT_SEND_TIME);

        // Get all the chatsListadoList where sendTime equals to UPDATED_SEND_TIME
        defaultChatsListadoShouldNotBeFound("sendTime.equals=" + UPDATED_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime in DEFAULT_SEND_TIME or UPDATED_SEND_TIME
        defaultChatsListadoShouldBeFound("sendTime.in=" + DEFAULT_SEND_TIME + "," + UPDATED_SEND_TIME);

        // Get all the chatsListadoList where sendTime equals to UPDATED_SEND_TIME
        defaultChatsListadoShouldNotBeFound("sendTime.in=" + UPDATED_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime is not null
        defaultChatsListadoShouldBeFound("sendTime.specified=true");

        // Get all the chatsListadoList where sendTime is null
        defaultChatsListadoShouldNotBeFound("sendTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime is greater than or equal to DEFAULT_SEND_TIME
        defaultChatsListadoShouldBeFound("sendTime.greaterThanOrEqual=" + DEFAULT_SEND_TIME);

        // Get all the chatsListadoList where sendTime is greater than or equal to UPDATED_SEND_TIME
        defaultChatsListadoShouldNotBeFound("sendTime.greaterThanOrEqual=" + UPDATED_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime is less than or equal to DEFAULT_SEND_TIME
        defaultChatsListadoShouldBeFound("sendTime.lessThanOrEqual=" + DEFAULT_SEND_TIME);

        // Get all the chatsListadoList where sendTime is less than or equal to SMALLER_SEND_TIME
        defaultChatsListadoShouldNotBeFound("sendTime.lessThanOrEqual=" + SMALLER_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime is less than DEFAULT_SEND_TIME
        defaultChatsListadoShouldNotBeFound("sendTime.lessThan=" + DEFAULT_SEND_TIME);

        // Get all the chatsListadoList where sendTime is less than UPDATED_SEND_TIME
        defaultChatsListadoShouldBeFound("sendTime.lessThan=" + UPDATED_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllChatsListadosBySendTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where sendTime is greater than DEFAULT_SEND_TIME
        defaultChatsListadoShouldNotBeFound("sendTime.greaterThan=" + DEFAULT_SEND_TIME);

        // Get all the chatsListadoList where sendTime is greater than SMALLER_SEND_TIME
        defaultChatsListadoShouldBeFound("sendTime.greaterThan=" + SMALLER_SEND_TIME);
    }


    @Test
    @Transactional
    public void getAllChatsListadosByGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where grupo equals to DEFAULT_GRUPO
        defaultChatsListadoShouldBeFound("grupo.equals=" + DEFAULT_GRUPO);

        // Get all the chatsListadoList where grupo equals to UPDATED_GRUPO
        defaultChatsListadoShouldNotBeFound("grupo.equals=" + UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByGrupoIsInShouldWork() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where grupo in DEFAULT_GRUPO or UPDATED_GRUPO
        defaultChatsListadoShouldBeFound("grupo.in=" + DEFAULT_GRUPO + "," + UPDATED_GRUPO);

        // Get all the chatsListadoList where grupo equals to UPDATED_GRUPO
        defaultChatsListadoShouldNotBeFound("grupo.in=" + UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void getAllChatsListadosByGrupoIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);

        // Get all the chatsListadoList where grupo is not null
        defaultChatsListadoShouldBeFound("grupo.specified=true");

        // Get all the chatsListadoList where grupo is null
        defaultChatsListadoShouldNotBeFound("grupo.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsListadosByPropietarioIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);
        Miembros propietario = MiembrosResourceIT.createEntity(em);
        em.persist(propietario);
        em.flush();
        chatsListado.setPropietario(propietario);
        chatsListadoRepository.saveAndFlush(chatsListado);
        Long propietarioId = propietario.getId();

        // Get all the chatsListadoList where propietario equals to propietarioId
        defaultChatsListadoShouldBeFound("propietarioId.equals=" + propietarioId);

        // Get all the chatsListadoList where propietario equals to propietarioId + 1
        defaultChatsListadoShouldNotBeFound("propietarioId.equals=" + (propietarioId + 1));
    }


    @Test
    @Transactional
    public void getAllChatsListadosByDestinatarioIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);
        Miembros destinatario = MiembrosResourceIT.createEntity(em);
        em.persist(destinatario);
        em.flush();
        chatsListado.setDestinatario(destinatario);
        chatsListadoRepository.saveAndFlush(chatsListado);
        Long destinatarioId = destinatario.getId();

        // Get all the chatsListadoList where destinatario equals to destinatarioId
        defaultChatsListadoShouldBeFound("destinatarioId.equals=" + destinatarioId);

        // Get all the chatsListadoList where destinatario equals to destinatarioId + 1
        defaultChatsListadoShouldNotBeFound("destinatarioId.equals=" + (destinatarioId + 1));
    }


    @Test
    @Transactional
    public void getAllChatsListadosByChatIsEqualToSomething() throws Exception {
        // Initialize the database
        chatsListadoRepository.saveAndFlush(chatsListado);
        Chat chat = ChatResourceIT.createEntity(em);
        em.persist(chat);
        em.flush();
        chatsListado.addChat(chat);
        chatsListadoRepository.saveAndFlush(chatsListado);
        Long chatId = chat.getId();

        // Get all the chatsListadoList where chat equals to chatId
        defaultChatsListadoShouldBeFound("chatId.equals=" + chatId);

        // Get all the chatsListadoList where chat equals to chatId + 1
        defaultChatsListadoShouldNotBeFound("chatId.equals=" + (chatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChatsListadoShouldBeFound(String filter) throws Exception {
        restChatsListadoMockMvc.perform(get("/api/chats-listados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatsListado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].badge").value(hasItem(DEFAULT_BADGE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].sendTime").value(hasItem(sameInstant(DEFAULT_SEND_TIME))))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO.booleanValue())));

        // Check, that the count call also returns 1
        restChatsListadoMockMvc.perform(get("/api/chats-listados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChatsListadoShouldNotBeFound(String filter) throws Exception {
        restChatsListadoMockMvc.perform(get("/api/chats-listados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChatsListadoMockMvc.perform(get("/api/chats-listados/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChatsListado() throws Exception {
        // Get the chatsListado
        restChatsListadoMockMvc.perform(get("/api/chats-listados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChatsListado() throws Exception {
        // Initialize the database
        chatsListadoService.save(chatsListado);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockChatsListadoSearchRepository);

        int databaseSizeBeforeUpdate = chatsListadoRepository.findAll().size();

        // Update the chatsListado
        ChatsListado updatedChatsListado = chatsListadoRepository.findById(chatsListado.getId()).get();
        // Disconnect from session so that the updates on updatedChatsListado are not directly saved in db
        em.detach(updatedChatsListado);
        updatedChatsListado
            .descripcion(UPDATED_DESCRIPCION)
            .estatus(UPDATED_ESTATUS)
            .count(UPDATED_COUNT)
            .badge(UPDATED_BADGE)
            .time(UPDATED_TIME)
            .sendTime(UPDATED_SEND_TIME)
            .grupo(UPDATED_GRUPO);

        restChatsListadoMockMvc.perform(put("/api/chats-listados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChatsListado)))
            .andExpect(status().isOk());

        // Validate the ChatsListado in the database
        List<ChatsListado> chatsListadoList = chatsListadoRepository.findAll();
        assertThat(chatsListadoList).hasSize(databaseSizeBeforeUpdate);
        ChatsListado testChatsListado = chatsListadoList.get(chatsListadoList.size() - 1);
        assertThat(testChatsListado.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testChatsListado.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testChatsListado.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testChatsListado.getBadge()).isEqualTo(UPDATED_BADGE);
        assertThat(testChatsListado.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testChatsListado.getSendTime()).isEqualTo(UPDATED_SEND_TIME);
        assertThat(testChatsListado.isGrupo()).isEqualTo(UPDATED_GRUPO);

        // Validate the ChatsListado in Elasticsearch
        verify(mockChatsListadoSearchRepository, times(1)).save(testChatsListado);
    }

    @Test
    @Transactional
    public void updateNonExistingChatsListado() throws Exception {
        int databaseSizeBeforeUpdate = chatsListadoRepository.findAll().size();

        // Create the ChatsListado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatsListadoMockMvc.perform(put("/api/chats-listados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chatsListado)))
            .andExpect(status().isBadRequest());

        // Validate the ChatsListado in the database
        List<ChatsListado> chatsListadoList = chatsListadoRepository.findAll();
        assertThat(chatsListadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChatsListado in Elasticsearch
        verify(mockChatsListadoSearchRepository, times(0)).save(chatsListado);
    }

    @Test
    @Transactional
    public void deleteChatsListado() throws Exception {
        // Initialize the database
        chatsListadoService.save(chatsListado);

        int databaseSizeBeforeDelete = chatsListadoRepository.findAll().size();

        // Delete the chatsListado
        restChatsListadoMockMvc.perform(delete("/api/chats-listados/{id}", chatsListado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChatsListado> chatsListadoList = chatsListadoRepository.findAll();
        assertThat(chatsListadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChatsListado in Elasticsearch
        verify(mockChatsListadoSearchRepository, times(1)).deleteById(chatsListado.getId());
    }

    @Test
    @Transactional
    public void searchChatsListado() throws Exception {
        // Initialize the database
        chatsListadoService.save(chatsListado);
        when(mockChatsListadoSearchRepository.search(queryStringQuery("id:" + chatsListado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(chatsListado), PageRequest.of(0, 1), 1));
        // Search the chatsListado
        restChatsListadoMockMvc.perform(get("/api/_search/chats-listados?query=id:" + chatsListado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chatsListado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].badge").value(hasItem(DEFAULT_BADGE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].sendTime").value(hasItem(sameInstant(DEFAULT_SEND_TIME))))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatsListado.class);
        ChatsListado chatsListado1 = new ChatsListado();
        chatsListado1.setId(1L);
        ChatsListado chatsListado2 = new ChatsListado();
        chatsListado2.setId(chatsListado1.getId());
        assertThat(chatsListado1).isEqualTo(chatsListado2);
        chatsListado2.setId(2L);
        assertThat(chatsListado1).isNotEqualTo(chatsListado2);
        chatsListado1.setId(null);
        assertThat(chatsListado1).isNotEqualTo(chatsListado2);
    }
}
