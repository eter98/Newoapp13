package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.ComentarioFeed;
import io.github.jhipster.newoapp13.domain.Feed;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.ComentarioFeedRepository;
import io.github.jhipster.newoapp13.repository.search.ComentarioFeedSearchRepository;
import io.github.jhipster.newoapp13.service.ComentarioFeedService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.ComentarioFeedCriteria;
import io.github.jhipster.newoapp13.service.ComentarioFeedQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ComentarioFeedResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class ComentarioFeedResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_ME_GUSTA = false;
    private static final Boolean UPDATED_ME_GUSTA = true;

    private static final Boolean DEFAULT_SEGUIR = false;
    private static final Boolean UPDATED_SEGUIR = true;

    @Autowired
    private ComentarioFeedRepository comentarioFeedRepository;

    @Autowired
    private ComentarioFeedService comentarioFeedService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.ComentarioFeedSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComentarioFeedSearchRepository mockComentarioFeedSearchRepository;

    @Autowired
    private ComentarioFeedQueryService comentarioFeedQueryService;

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

    private MockMvc restComentarioFeedMockMvc;

    private ComentarioFeed comentarioFeed;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComentarioFeedResource comentarioFeedResource = new ComentarioFeedResource(comentarioFeedService, comentarioFeedQueryService);
        this.restComentarioFeedMockMvc = MockMvcBuilders.standaloneSetup(comentarioFeedResource)
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
    public static ComentarioFeed createEntity(EntityManager em) {
        ComentarioFeed comentarioFeed = new ComentarioFeed()
            .comentario(DEFAULT_COMENTARIO)
            .fecha(DEFAULT_FECHA)
            .meGusta(DEFAULT_ME_GUSTA)
            .seguir(DEFAULT_SEGUIR);
        return comentarioFeed;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentarioFeed createUpdatedEntity(EntityManager em) {
        ComentarioFeed comentarioFeed = new ComentarioFeed()
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .meGusta(UPDATED_ME_GUSTA)
            .seguir(UPDATED_SEGUIR);
        return comentarioFeed;
    }

    @BeforeEach
    public void initTest() {
        comentarioFeed = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarioFeed() throws Exception {
        int databaseSizeBeforeCreate = comentarioFeedRepository.findAll().size();

        // Create the ComentarioFeed
        restComentarioFeedMockMvc.perform(post("/api/comentario-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioFeed)))
            .andExpect(status().isCreated());

        // Validate the ComentarioFeed in the database
        List<ComentarioFeed> comentarioFeedList = comentarioFeedRepository.findAll();
        assertThat(comentarioFeedList).hasSize(databaseSizeBeforeCreate + 1);
        ComentarioFeed testComentarioFeed = comentarioFeedList.get(comentarioFeedList.size() - 1);
        assertThat(testComentarioFeed.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarioFeed.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testComentarioFeed.isMeGusta()).isEqualTo(DEFAULT_ME_GUSTA);
        assertThat(testComentarioFeed.isSeguir()).isEqualTo(DEFAULT_SEGUIR);

        // Validate the ComentarioFeed in Elasticsearch
        verify(mockComentarioFeedSearchRepository, times(1)).save(testComentarioFeed);
    }

    @Test
    @Transactional
    public void createComentarioFeedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioFeedRepository.findAll().size();

        // Create the ComentarioFeed with an existing ID
        comentarioFeed.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioFeedMockMvc.perform(post("/api/comentario-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioFeed)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarioFeed in the database
        List<ComentarioFeed> comentarioFeedList = comentarioFeedRepository.findAll();
        assertThat(comentarioFeedList).hasSize(databaseSizeBeforeCreate);

        // Validate the ComentarioFeed in Elasticsearch
        verify(mockComentarioFeedSearchRepository, times(0)).save(comentarioFeed);
    }


    @Test
    @Transactional
    public void getAllComentarioFeeds() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())))
            .andExpect(jsonPath("$.[*].seguir").value(hasItem(DEFAULT_SEGUIR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComentarioFeed() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get the comentarioFeed
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds/{id}", comentarioFeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarioFeed.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.meGusta").value(DEFAULT_ME_GUSTA.booleanValue()))
            .andExpect(jsonPath("$.seguir").value(DEFAULT_SEGUIR.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha equals to DEFAULT_FECHA
        defaultComentarioFeedShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the comentarioFeedList where fecha equals to UPDATED_FECHA
        defaultComentarioFeedShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultComentarioFeedShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the comentarioFeedList where fecha equals to UPDATED_FECHA
        defaultComentarioFeedShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha is not null
        defaultComentarioFeedShouldBeFound("fecha.specified=true");

        // Get all the comentarioFeedList where fecha is null
        defaultComentarioFeedShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha is greater than or equal to DEFAULT_FECHA
        defaultComentarioFeedShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the comentarioFeedList where fecha is greater than or equal to UPDATED_FECHA
        defaultComentarioFeedShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha is less than or equal to DEFAULT_FECHA
        defaultComentarioFeedShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the comentarioFeedList where fecha is less than or equal to SMALLER_FECHA
        defaultComentarioFeedShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha is less than DEFAULT_FECHA
        defaultComentarioFeedShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the comentarioFeedList where fecha is less than UPDATED_FECHA
        defaultComentarioFeedShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where fecha is greater than DEFAULT_FECHA
        defaultComentarioFeedShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the comentarioFeedList where fecha is greater than SMALLER_FECHA
        defaultComentarioFeedShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }


    @Test
    @Transactional
    public void getAllComentarioFeedsByMeGustaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where meGusta equals to DEFAULT_ME_GUSTA
        defaultComentarioFeedShouldBeFound("meGusta.equals=" + DEFAULT_ME_GUSTA);

        // Get all the comentarioFeedList where meGusta equals to UPDATED_ME_GUSTA
        defaultComentarioFeedShouldNotBeFound("meGusta.equals=" + UPDATED_ME_GUSTA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByMeGustaIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where meGusta in DEFAULT_ME_GUSTA or UPDATED_ME_GUSTA
        defaultComentarioFeedShouldBeFound("meGusta.in=" + DEFAULT_ME_GUSTA + "," + UPDATED_ME_GUSTA);

        // Get all the comentarioFeedList where meGusta equals to UPDATED_ME_GUSTA
        defaultComentarioFeedShouldNotBeFound("meGusta.in=" + UPDATED_ME_GUSTA);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByMeGustaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where meGusta is not null
        defaultComentarioFeedShouldBeFound("meGusta.specified=true");

        // Get all the comentarioFeedList where meGusta is null
        defaultComentarioFeedShouldNotBeFound("meGusta.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsBySeguirIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where seguir equals to DEFAULT_SEGUIR
        defaultComentarioFeedShouldBeFound("seguir.equals=" + DEFAULT_SEGUIR);

        // Get all the comentarioFeedList where seguir equals to UPDATED_SEGUIR
        defaultComentarioFeedShouldNotBeFound("seguir.equals=" + UPDATED_SEGUIR);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsBySeguirIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where seguir in DEFAULT_SEGUIR or UPDATED_SEGUIR
        defaultComentarioFeedShouldBeFound("seguir.in=" + DEFAULT_SEGUIR + "," + UPDATED_SEGUIR);

        // Get all the comentarioFeedList where seguir equals to UPDATED_SEGUIR
        defaultComentarioFeedShouldNotBeFound("seguir.in=" + UPDATED_SEGUIR);
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsBySeguirIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);

        // Get all the comentarioFeedList where seguir is not null
        defaultComentarioFeedShouldBeFound("seguir.specified=true");

        // Get all the comentarioFeedList where seguir is null
        defaultComentarioFeedShouldNotBeFound("seguir.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioFeedsByFeedIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);
        Feed feed = FeedResourceIT.createEntity(em);
        em.persist(feed);
        em.flush();
        comentarioFeed.setFeed(feed);
        comentarioFeedRepository.saveAndFlush(comentarioFeed);
        Long feedId = feed.getId();

        // Get all the comentarioFeedList where feed equals to feedId
        defaultComentarioFeedShouldBeFound("feedId.equals=" + feedId);

        // Get all the comentarioFeedList where feed equals to feedId + 1
        defaultComentarioFeedShouldNotBeFound("feedId.equals=" + (feedId + 1));
    }


    @Test
    @Transactional
    public void getAllComentarioFeedsByMiembroComentaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioFeedRepository.saveAndFlush(comentarioFeed);
        Miembros miembroComenta = MiembrosResourceIT.createEntity(em);
        em.persist(miembroComenta);
        em.flush();
        comentarioFeed.setMiembroComenta(miembroComenta);
        comentarioFeedRepository.saveAndFlush(comentarioFeed);
        Long miembroComentaId = miembroComenta.getId();

        // Get all the comentarioFeedList where miembroComenta equals to miembroComentaId
        defaultComentarioFeedShouldBeFound("miembroComentaId.equals=" + miembroComentaId);

        // Get all the comentarioFeedList where miembroComenta equals to miembroComentaId + 1
        defaultComentarioFeedShouldNotBeFound("miembroComentaId.equals=" + (miembroComentaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComentarioFeedShouldBeFound(String filter) throws Exception {
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())))
            .andExpect(jsonPath("$.[*].seguir").value(hasItem(DEFAULT_SEGUIR.booleanValue())));

        // Check, that the count call also returns 1
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComentarioFeedShouldNotBeFound(String filter) throws Exception {
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComentarioFeed() throws Exception {
        // Get the comentarioFeed
        restComentarioFeedMockMvc.perform(get("/api/comentario-feeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarioFeed() throws Exception {
        // Initialize the database
        comentarioFeedService.save(comentarioFeed);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockComentarioFeedSearchRepository);

        int databaseSizeBeforeUpdate = comentarioFeedRepository.findAll().size();

        // Update the comentarioFeed
        ComentarioFeed updatedComentarioFeed = comentarioFeedRepository.findById(comentarioFeed.getId()).get();
        // Disconnect from session so that the updates on updatedComentarioFeed are not directly saved in db
        em.detach(updatedComentarioFeed);
        updatedComentarioFeed
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .meGusta(UPDATED_ME_GUSTA)
            .seguir(UPDATED_SEGUIR);

        restComentarioFeedMockMvc.perform(put("/api/comentario-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentarioFeed)))
            .andExpect(status().isOk());

        // Validate the ComentarioFeed in the database
        List<ComentarioFeed> comentarioFeedList = comentarioFeedRepository.findAll();
        assertThat(comentarioFeedList).hasSize(databaseSizeBeforeUpdate);
        ComentarioFeed testComentarioFeed = comentarioFeedList.get(comentarioFeedList.size() - 1);
        assertThat(testComentarioFeed.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarioFeed.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testComentarioFeed.isMeGusta()).isEqualTo(UPDATED_ME_GUSTA);
        assertThat(testComentarioFeed.isSeguir()).isEqualTo(UPDATED_SEGUIR);

        // Validate the ComentarioFeed in Elasticsearch
        verify(mockComentarioFeedSearchRepository, times(1)).save(testComentarioFeed);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarioFeed() throws Exception {
        int databaseSizeBeforeUpdate = comentarioFeedRepository.findAll().size();

        // Create the ComentarioFeed

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentarioFeedMockMvc.perform(put("/api/comentario-feeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioFeed)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarioFeed in the database
        List<ComentarioFeed> comentarioFeedList = comentarioFeedRepository.findAll();
        assertThat(comentarioFeedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ComentarioFeed in Elasticsearch
        verify(mockComentarioFeedSearchRepository, times(0)).save(comentarioFeed);
    }

    @Test
    @Transactional
    public void deleteComentarioFeed() throws Exception {
        // Initialize the database
        comentarioFeedService.save(comentarioFeed);

        int databaseSizeBeforeDelete = comentarioFeedRepository.findAll().size();

        // Delete the comentarioFeed
        restComentarioFeedMockMvc.perform(delete("/api/comentario-feeds/{id}", comentarioFeed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComentarioFeed> comentarioFeedList = comentarioFeedRepository.findAll();
        assertThat(comentarioFeedList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ComentarioFeed in Elasticsearch
        verify(mockComentarioFeedSearchRepository, times(1)).deleteById(comentarioFeed.getId());
    }

    @Test
    @Transactional
    public void searchComentarioFeed() throws Exception {
        // Initialize the database
        comentarioFeedService.save(comentarioFeed);
        when(mockComentarioFeedSearchRepository.search(queryStringQuery("id:" + comentarioFeed.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(comentarioFeed), PageRequest.of(0, 1), 1));
        // Search the comentarioFeed
        restComentarioFeedMockMvc.perform(get("/api/_search/comentario-feeds?query=id:" + comentarioFeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioFeed.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())))
            .andExpect(jsonPath("$.[*].seguir").value(hasItem(DEFAULT_SEGUIR.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarioFeed.class);
        ComentarioFeed comentarioFeed1 = new ComentarioFeed();
        comentarioFeed1.setId(1L);
        ComentarioFeed comentarioFeed2 = new ComentarioFeed();
        comentarioFeed2.setId(comentarioFeed1.getId());
        assertThat(comentarioFeed1).isEqualTo(comentarioFeed2);
        comentarioFeed2.setId(2L);
        assertThat(comentarioFeed1).isNotEqualTo(comentarioFeed2);
        comentarioFeed1.setId(null);
        assertThat(comentarioFeed1).isNotEqualTo(comentarioFeed2);
    }
}
