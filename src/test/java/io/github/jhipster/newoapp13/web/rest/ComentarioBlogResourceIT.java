package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.ComentarioBlog;
import io.github.jhipster.newoapp13.domain.Blog;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.ComentarioBlogRepository;
import io.github.jhipster.newoapp13.repository.search.ComentarioBlogSearchRepository;
import io.github.jhipster.newoapp13.service.ComentarioBlogService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.ComentarioBlogCriteria;
import io.github.jhipster.newoapp13.service.ComentarioBlogQueryService;

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
 * Integration tests for the {@link ComentarioBlogResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class ComentarioBlogResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA = Instant.ofEpochMilli(-1L);

    private static final Boolean DEFAULT_ME_GUSTA = false;
    private static final Boolean UPDATED_ME_GUSTA = true;

    private static final Boolean DEFAULT_SEGUIR = false;
    private static final Boolean UPDATED_SEGUIR = true;

    @Autowired
    private ComentarioBlogRepository comentarioBlogRepository;

    @Autowired
    private ComentarioBlogService comentarioBlogService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.ComentarioBlogSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComentarioBlogSearchRepository mockComentarioBlogSearchRepository;

    @Autowired
    private ComentarioBlogQueryService comentarioBlogQueryService;

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

    private MockMvc restComentarioBlogMockMvc;

    private ComentarioBlog comentarioBlog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComentarioBlogResource comentarioBlogResource = new ComentarioBlogResource(comentarioBlogService, comentarioBlogQueryService);
        this.restComentarioBlogMockMvc = MockMvcBuilders.standaloneSetup(comentarioBlogResource)
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
    public static ComentarioBlog createEntity(EntityManager em) {
        ComentarioBlog comentarioBlog = new ComentarioBlog()
            .comentario(DEFAULT_COMENTARIO)
            .fecha(DEFAULT_FECHA)
            .meGusta(DEFAULT_ME_GUSTA)
            .seguir(DEFAULT_SEGUIR);
        return comentarioBlog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentarioBlog createUpdatedEntity(EntityManager em) {
        ComentarioBlog comentarioBlog = new ComentarioBlog()
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .meGusta(UPDATED_ME_GUSTA)
            .seguir(UPDATED_SEGUIR);
        return comentarioBlog;
    }

    @BeforeEach
    public void initTest() {
        comentarioBlog = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarioBlog() throws Exception {
        int databaseSizeBeforeCreate = comentarioBlogRepository.findAll().size();

        // Create the ComentarioBlog
        restComentarioBlogMockMvc.perform(post("/api/comentario-blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioBlog)))
            .andExpect(status().isCreated());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeCreate + 1);
        ComentarioBlog testComentarioBlog = comentarioBlogList.get(comentarioBlogList.size() - 1);
        assertThat(testComentarioBlog.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarioBlog.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testComentarioBlog.isMeGusta()).isEqualTo(DEFAULT_ME_GUSTA);
        assertThat(testComentarioBlog.isSeguir()).isEqualTo(DEFAULT_SEGUIR);

        // Validate the ComentarioBlog in Elasticsearch
        verify(mockComentarioBlogSearchRepository, times(1)).save(testComentarioBlog);
    }

    @Test
    @Transactional
    public void createComentarioBlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioBlogRepository.findAll().size();

        // Create the ComentarioBlog with an existing ID
        comentarioBlog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioBlogMockMvc.perform(post("/api/comentario-blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioBlog)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeCreate);

        // Validate the ComentarioBlog in Elasticsearch
        verify(mockComentarioBlogSearchRepository, times(0)).save(comentarioBlog);
    }


    @Test
    @Transactional
    public void getAllComentarioBlogs() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioBlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())))
            .andExpect(jsonPath("$.[*].seguir").value(hasItem(DEFAULT_SEGUIR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get the comentarioBlog
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/{id}", comentarioBlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarioBlog.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.meGusta").value(DEFAULT_ME_GUSTA.booleanValue()))
            .andExpect(jsonPath("$.seguir").value(DEFAULT_SEGUIR.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha equals to DEFAULT_FECHA
        defaultComentarioBlogShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the comentarioBlogList where fecha equals to UPDATED_FECHA
        defaultComentarioBlogShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultComentarioBlogShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the comentarioBlogList where fecha equals to UPDATED_FECHA
        defaultComentarioBlogShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha is not null
        defaultComentarioBlogShouldBeFound("fecha.specified=true");

        // Get all the comentarioBlogList where fecha is null
        defaultComentarioBlogShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByMeGustaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where meGusta equals to DEFAULT_ME_GUSTA
        defaultComentarioBlogShouldBeFound("meGusta.equals=" + DEFAULT_ME_GUSTA);

        // Get all the comentarioBlogList where meGusta equals to UPDATED_ME_GUSTA
        defaultComentarioBlogShouldNotBeFound("meGusta.equals=" + UPDATED_ME_GUSTA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByMeGustaIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where meGusta in DEFAULT_ME_GUSTA or UPDATED_ME_GUSTA
        defaultComentarioBlogShouldBeFound("meGusta.in=" + DEFAULT_ME_GUSTA + "," + UPDATED_ME_GUSTA);

        // Get all the comentarioBlogList where meGusta equals to UPDATED_ME_GUSTA
        defaultComentarioBlogShouldNotBeFound("meGusta.in=" + UPDATED_ME_GUSTA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByMeGustaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where meGusta is not null
        defaultComentarioBlogShouldBeFound("meGusta.specified=true");

        // Get all the comentarioBlogList where meGusta is null
        defaultComentarioBlogShouldNotBeFound("meGusta.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsBySeguirIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where seguir equals to DEFAULT_SEGUIR
        defaultComentarioBlogShouldBeFound("seguir.equals=" + DEFAULT_SEGUIR);

        // Get all the comentarioBlogList where seguir equals to UPDATED_SEGUIR
        defaultComentarioBlogShouldNotBeFound("seguir.equals=" + UPDATED_SEGUIR);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsBySeguirIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where seguir in DEFAULT_SEGUIR or UPDATED_SEGUIR
        defaultComentarioBlogShouldBeFound("seguir.in=" + DEFAULT_SEGUIR + "," + UPDATED_SEGUIR);

        // Get all the comentarioBlogList where seguir equals to UPDATED_SEGUIR
        defaultComentarioBlogShouldNotBeFound("seguir.in=" + UPDATED_SEGUIR);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsBySeguirIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where seguir is not null
        defaultComentarioBlogShouldBeFound("seguir.specified=true");

        // Get all the comentarioBlogList where seguir is null
        defaultComentarioBlogShouldNotBeFound("seguir.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);
        Blog blog = BlogResourceIT.createEntity(em);
        em.persist(blog);
        em.flush();
        comentarioBlog.setBlog(blog);
        comentarioBlogRepository.saveAndFlush(comentarioBlog);
        Long blogId = blog.getId();

        // Get all the comentarioBlogList where blog equals to blogId
        defaultComentarioBlogShouldBeFound("blogId.equals=" + blogId);

        // Get all the comentarioBlogList where blog equals to blogId + 1
        defaultComentarioBlogShouldNotBeFound("blogId.equals=" + (blogId + 1));
    }


    @Test
    @Transactional
    public void getAllComentarioBlogsByMiembroComentaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);
        Miembros miembroComenta = MiembrosResourceIT.createEntity(em);
        em.persist(miembroComenta);
        em.flush();
        comentarioBlog.setMiembroComenta(miembroComenta);
        comentarioBlogRepository.saveAndFlush(comentarioBlog);
        Long miembroComentaId = miembroComenta.getId();

        // Get all the comentarioBlogList where miembroComenta equals to miembroComentaId
        defaultComentarioBlogShouldBeFound("miembroComentaId.equals=" + miembroComentaId);

        // Get all the comentarioBlogList where miembroComenta equals to miembroComentaId + 1
        defaultComentarioBlogShouldNotBeFound("miembroComentaId.equals=" + (miembroComentaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComentarioBlogShouldBeFound(String filter) throws Exception {
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioBlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())))
            .andExpect(jsonPath("$.[*].seguir").value(hasItem(DEFAULT_SEGUIR.booleanValue())));

        // Check, that the count call also returns 1
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComentarioBlogShouldNotBeFound(String filter) throws Exception {
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComentarioBlog() throws Exception {
        // Get the comentarioBlog
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogService.save(comentarioBlog);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockComentarioBlogSearchRepository);

        int databaseSizeBeforeUpdate = comentarioBlogRepository.findAll().size();

        // Update the comentarioBlog
        ComentarioBlog updatedComentarioBlog = comentarioBlogRepository.findById(comentarioBlog.getId()).get();
        // Disconnect from session so that the updates on updatedComentarioBlog are not directly saved in db
        em.detach(updatedComentarioBlog);
        updatedComentarioBlog
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .meGusta(UPDATED_ME_GUSTA)
            .seguir(UPDATED_SEGUIR);

        restComentarioBlogMockMvc.perform(put("/api/comentario-blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentarioBlog)))
            .andExpect(status().isOk());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeUpdate);
        ComentarioBlog testComentarioBlog = comentarioBlogList.get(comentarioBlogList.size() - 1);
        assertThat(testComentarioBlog.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarioBlog.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testComentarioBlog.isMeGusta()).isEqualTo(UPDATED_ME_GUSTA);
        assertThat(testComentarioBlog.isSeguir()).isEqualTo(UPDATED_SEGUIR);

        // Validate the ComentarioBlog in Elasticsearch
        verify(mockComentarioBlogSearchRepository, times(1)).save(testComentarioBlog);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarioBlog() throws Exception {
        int databaseSizeBeforeUpdate = comentarioBlogRepository.findAll().size();

        // Create the ComentarioBlog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentarioBlogMockMvc.perform(put("/api/comentario-blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarioBlog)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ComentarioBlog in Elasticsearch
        verify(mockComentarioBlogSearchRepository, times(0)).save(comentarioBlog);
    }

    @Test
    @Transactional
    public void deleteComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogService.save(comentarioBlog);

        int databaseSizeBeforeDelete = comentarioBlogRepository.findAll().size();

        // Delete the comentarioBlog
        restComentarioBlogMockMvc.perform(delete("/api/comentario-blogs/{id}", comentarioBlog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ComentarioBlog in Elasticsearch
        verify(mockComentarioBlogSearchRepository, times(1)).deleteById(comentarioBlog.getId());
    }

    @Test
    @Transactional
    public void searchComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogService.save(comentarioBlog);
        when(mockComentarioBlogSearchRepository.search(queryStringQuery("id:" + comentarioBlog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(comentarioBlog), PageRequest.of(0, 1), 1));
        // Search the comentarioBlog
        restComentarioBlogMockMvc.perform(get("/api/_search/comentario-blogs?query=id:" + comentarioBlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioBlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())))
            .andExpect(jsonPath("$.[*].seguir").value(hasItem(DEFAULT_SEGUIR.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarioBlog.class);
        ComentarioBlog comentarioBlog1 = new ComentarioBlog();
        comentarioBlog1.setId(1L);
        ComentarioBlog comentarioBlog2 = new ComentarioBlog();
        comentarioBlog2.setId(comentarioBlog1.getId());
        assertThat(comentarioBlog1).isEqualTo(comentarioBlog2);
        comentarioBlog2.setId(2L);
        assertThat(comentarioBlog1).isNotEqualTo(comentarioBlog2);
        comentarioBlog1.setId(null);
        assertThat(comentarioBlog1).isNotEqualTo(comentarioBlog2);
    }
}
