package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Blog;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.repository.BlogRepository;
import io.github.jhipster.newoapp13.repository.search.BlogSearchRepository;
import io.github.jhipster.newoapp13.service.BlogService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.BlogCriteria;
import io.github.jhipster.newoapp13.service.BlogQueryService;

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

import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;
import io.github.jhipster.newoapp13.domain.enumeration.EstadoPublicaciond;
import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
/**
 * Integration tests for the {@link BlogResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class BlogResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Categoriad DEFAULT_TIPO_CONTENIDO = Categoriad.GENERAL;
    private static final Categoriad UPDATED_TIPO_CONTENIDO = Categoriad.DE_GRUPO;

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA = Instant.ofEpochMilli(-1L);

    private static final byte[] DEFAULT_AUDIO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AUDIO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AUDIO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AUDIO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BANNER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_CONTENT_TYPE = "image/png";

    private static final EstadoPublicaciond DEFAULT_ESTADO_PUBLICACION = EstadoPublicaciond.BORRADOR;
    private static final EstadoPublicaciond UPDATED_ESTADO_PUBLICACION = EstadoPublicaciond.EN_REVISION;

    private static final TipoConsumod DEFAULT_TIPO_CONSUMO = TipoConsumod.GRATIS;
    private static final TipoConsumod UPDATED_TIPO_CONSUMO = TipoConsumod.PAGO;

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;
    private static final Float SMALLER_VALOR = 1F - 1F;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.BlogSearchRepositoryMockConfiguration
     */
    @Autowired
    private BlogSearchRepository mockBlogSearchRepository;

    @Autowired
    private BlogQueryService blogQueryService;

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

    private MockMvc restBlogMockMvc;

    private Blog blog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlogResource blogResource = new BlogResource(blogService, blogQueryService);
        this.restBlogMockMvc = MockMvcBuilders.standaloneSetup(blogResource)
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
    public static Blog createEntity(EntityManager em) {
        Blog blog = new Blog()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .tipoContenido(DEFAULT_TIPO_CONTENIDO)
            .contenido(DEFAULT_CONTENIDO)
            .fecha(DEFAULT_FECHA)
            .audio(DEFAULT_AUDIO)
            .audioContentType(DEFAULT_AUDIO_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .banner(DEFAULT_BANNER)
            .bannerContentType(DEFAULT_BANNER_CONTENT_TYPE)
            .estadoPublicacion(DEFAULT_ESTADO_PUBLICACION)
            .tipoConsumo(DEFAULT_TIPO_CONSUMO)
            .valor(DEFAULT_VALOR)
            .impuesto(DEFAULT_IMPUESTO);
        return blog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blog createUpdatedEntity(EntityManager em) {
        Blog blog = new Blog()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoContenido(UPDATED_TIPO_CONTENIDO)
            .contenido(UPDATED_CONTENIDO)
            .fecha(UPDATED_FECHA)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .banner(UPDATED_BANNER)
            .bannerContentType(UPDATED_BANNER_CONTENT_TYPE)
            .estadoPublicacion(UPDATED_ESTADO_PUBLICACION)
            .tipoConsumo(UPDATED_TIPO_CONSUMO)
            .valor(UPDATED_VALOR)
            .impuesto(UPDATED_IMPUESTO);
        return blog;
    }

    @BeforeEach
    public void initTest() {
        blog = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlog() throws Exception {
        int databaseSizeBeforeCreate = blogRepository.findAll().size();

        // Create the Blog
        restBlogMockMvc.perform(post("/api/blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isCreated());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeCreate + 1);
        Blog testBlog = blogList.get(blogList.size() - 1);
        assertThat(testBlog.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testBlog.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testBlog.getTipoContenido()).isEqualTo(DEFAULT_TIPO_CONTENIDO);
        assertThat(testBlog.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testBlog.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testBlog.getAudio()).isEqualTo(DEFAULT_AUDIO);
        assertThat(testBlog.getAudioContentType()).isEqualTo(DEFAULT_AUDIO_CONTENT_TYPE);
        assertThat(testBlog.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testBlog.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testBlog.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testBlog.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testBlog.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testBlog.getBanner()).isEqualTo(DEFAULT_BANNER);
        assertThat(testBlog.getBannerContentType()).isEqualTo(DEFAULT_BANNER_CONTENT_TYPE);
        assertThat(testBlog.getEstadoPublicacion()).isEqualTo(DEFAULT_ESTADO_PUBLICACION);
        assertThat(testBlog.getTipoConsumo()).isEqualTo(DEFAULT_TIPO_CONSUMO);
        assertThat(testBlog.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testBlog.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);

        // Validate the Blog in Elasticsearch
        verify(mockBlogSearchRepository, times(1)).save(testBlog);
    }

    @Test
    @Transactional
    public void createBlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogRepository.findAll().size();

        // Create the Blog with an existing ID
        blog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogMockMvc.perform(post("/api/blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeCreate);

        // Validate the Blog in Elasticsearch
        verify(mockBlogSearchRepository, times(0)).save(blog);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogRepository.findAll().size();
        // set the field null
        blog.setTitulo(null);

        // Create the Blog, which fails.

        restBlogMockMvc.perform(post("/api/blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isBadRequest());

        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogRepository.findAll().size();
        // set the field null
        blog.setDescripcion(null);

        // Create the Blog, which fails.

        restBlogMockMvc.perform(post("/api/blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isBadRequest());

        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBlogs() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].tipoContenido").value(hasItem(DEFAULT_TIPO_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO.toString())))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))))
            .andExpect(jsonPath("$.[*].estadoPublicacion").value(hasItem(DEFAULT_ESTADO_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())));
    }
    
    @Test
    @Transactional
    public void getBlog() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", blog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blog.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.tipoContenido").value(DEFAULT_TIPO_CONTENIDO.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.audioContentType").value(DEFAULT_AUDIO_CONTENT_TYPE))
            .andExpect(jsonPath("$.audio").value(Base64Utils.encodeToString(DEFAULT_AUDIO)))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO.toString()))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.bannerContentType").value(DEFAULT_BANNER_CONTENT_TYPE))
            .andExpect(jsonPath("$.banner").value(Base64Utils.encodeToString(DEFAULT_BANNER)))
            .andExpect(jsonPath("$.estadoPublicacion").value(DEFAULT_ESTADO_PUBLICACION.toString()))
            .andExpect(jsonPath("$.tipoConsumo").value(DEFAULT_TIPO_CONSUMO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()));
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo equals to DEFAULT_TITULO
        defaultBlogShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the blogList where titulo equals to UPDATED_TITULO
        defaultBlogShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultBlogShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the blogList where titulo equals to UPDATED_TITULO
        defaultBlogShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo is not null
        defaultBlogShouldBeFound("titulo.specified=true");

        // Get all the blogList where titulo is null
        defaultBlogShouldNotBeFound("titulo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where descripcion equals to DEFAULT_DESCRIPCION
        defaultBlogShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the blogList where descripcion equals to UPDATED_DESCRIPCION
        defaultBlogShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllBlogsByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultBlogShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the blogList where descripcion equals to UPDATED_DESCRIPCION
        defaultBlogShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllBlogsByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where descripcion is not null
        defaultBlogShouldBeFound("descripcion.specified=true");

        // Get all the blogList where descripcion is null
        defaultBlogShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByTipoContenidoIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where tipoContenido equals to DEFAULT_TIPO_CONTENIDO
        defaultBlogShouldBeFound("tipoContenido.equals=" + DEFAULT_TIPO_CONTENIDO);

        // Get all the blogList where tipoContenido equals to UPDATED_TIPO_CONTENIDO
        defaultBlogShouldNotBeFound("tipoContenido.equals=" + UPDATED_TIPO_CONTENIDO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTipoContenidoIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where tipoContenido in DEFAULT_TIPO_CONTENIDO or UPDATED_TIPO_CONTENIDO
        defaultBlogShouldBeFound("tipoContenido.in=" + DEFAULT_TIPO_CONTENIDO + "," + UPDATED_TIPO_CONTENIDO);

        // Get all the blogList where tipoContenido equals to UPDATED_TIPO_CONTENIDO
        defaultBlogShouldNotBeFound("tipoContenido.in=" + UPDATED_TIPO_CONTENIDO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTipoContenidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where tipoContenido is not null
        defaultBlogShouldBeFound("tipoContenido.specified=true");

        // Get all the blogList where tipoContenido is null
        defaultBlogShouldNotBeFound("tipoContenido.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where fecha equals to DEFAULT_FECHA
        defaultBlogShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the blogList where fecha equals to UPDATED_FECHA
        defaultBlogShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllBlogsByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultBlogShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the blogList where fecha equals to UPDATED_FECHA
        defaultBlogShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllBlogsByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where fecha is not null
        defaultBlogShouldBeFound("fecha.specified=true");

        // Get all the blogList where fecha is null
        defaultBlogShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video equals to DEFAULT_VIDEO
        defaultBlogShouldBeFound("video.equals=" + DEFAULT_VIDEO);

        // Get all the blogList where video equals to UPDATED_VIDEO
        defaultBlogShouldNotBeFound("video.equals=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video in DEFAULT_VIDEO or UPDATED_VIDEO
        defaultBlogShouldBeFound("video.in=" + DEFAULT_VIDEO + "," + UPDATED_VIDEO);

        // Get all the blogList where video equals to UPDATED_VIDEO
        defaultBlogShouldNotBeFound("video.in=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video is not null
        defaultBlogShouldBeFound("video.specified=true");

        // Get all the blogList where video is null
        defaultBlogShouldNotBeFound("video.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByEstadoPublicacionIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where estadoPublicacion equals to DEFAULT_ESTADO_PUBLICACION
        defaultBlogShouldBeFound("estadoPublicacion.equals=" + DEFAULT_ESTADO_PUBLICACION);

        // Get all the blogList where estadoPublicacion equals to UPDATED_ESTADO_PUBLICACION
        defaultBlogShouldNotBeFound("estadoPublicacion.equals=" + UPDATED_ESTADO_PUBLICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByEstadoPublicacionIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where estadoPublicacion in DEFAULT_ESTADO_PUBLICACION or UPDATED_ESTADO_PUBLICACION
        defaultBlogShouldBeFound("estadoPublicacion.in=" + DEFAULT_ESTADO_PUBLICACION + "," + UPDATED_ESTADO_PUBLICACION);

        // Get all the blogList where estadoPublicacion equals to UPDATED_ESTADO_PUBLICACION
        defaultBlogShouldNotBeFound("estadoPublicacion.in=" + UPDATED_ESTADO_PUBLICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByEstadoPublicacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where estadoPublicacion is not null
        defaultBlogShouldBeFound("estadoPublicacion.specified=true");

        // Get all the blogList where estadoPublicacion is null
        defaultBlogShouldNotBeFound("estadoPublicacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByTipoConsumoIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where tipoConsumo equals to DEFAULT_TIPO_CONSUMO
        defaultBlogShouldBeFound("tipoConsumo.equals=" + DEFAULT_TIPO_CONSUMO);

        // Get all the blogList where tipoConsumo equals to UPDATED_TIPO_CONSUMO
        defaultBlogShouldNotBeFound("tipoConsumo.equals=" + UPDATED_TIPO_CONSUMO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTipoConsumoIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where tipoConsumo in DEFAULT_TIPO_CONSUMO or UPDATED_TIPO_CONSUMO
        defaultBlogShouldBeFound("tipoConsumo.in=" + DEFAULT_TIPO_CONSUMO + "," + UPDATED_TIPO_CONSUMO);

        // Get all the blogList where tipoConsumo equals to UPDATED_TIPO_CONSUMO
        defaultBlogShouldNotBeFound("tipoConsumo.in=" + UPDATED_TIPO_CONSUMO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTipoConsumoIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where tipoConsumo is not null
        defaultBlogShouldBeFound("tipoConsumo.specified=true");

        // Get all the blogList where tipoConsumo is null
        defaultBlogShouldNotBeFound("tipoConsumo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor equals to DEFAULT_VALOR
        defaultBlogShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the blogList where valor equals to UPDATED_VALOR
        defaultBlogShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultBlogShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the blogList where valor equals to UPDATED_VALOR
        defaultBlogShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor is not null
        defaultBlogShouldBeFound("valor.specified=true");

        // Get all the blogList where valor is null
        defaultBlogShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor is greater than or equal to DEFAULT_VALOR
        defaultBlogShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the blogList where valor is greater than or equal to UPDATED_VALOR
        defaultBlogShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor is less than or equal to DEFAULT_VALOR
        defaultBlogShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the blogList where valor is less than or equal to SMALLER_VALOR
        defaultBlogShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor is less than DEFAULT_VALOR
        defaultBlogShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the blogList where valor is less than UPDATED_VALOR
        defaultBlogShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllBlogsByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where valor is greater than DEFAULT_VALOR
        defaultBlogShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the blogList where valor is greater than SMALLER_VALOR
        defaultBlogShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllBlogsByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where impuesto equals to DEFAULT_IMPUESTO
        defaultBlogShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the blogList where impuesto equals to UPDATED_IMPUESTO
        defaultBlogShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllBlogsByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultBlogShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the blogList where impuesto equals to UPDATED_IMPUESTO
        defaultBlogShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllBlogsByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where impuesto is not null
        defaultBlogShouldBeFound("impuesto.specified=true");

        // Get all the blogList where impuesto is null
        defaultBlogShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByMiembroIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);
        Miembros miembro = MiembrosResourceIT.createEntity(em);
        em.persist(miembro);
        em.flush();
        blog.setMiembro(miembro);
        blogRepository.saveAndFlush(blog);
        Long miembroId = miembro.getId();

        // Get all the blogList where miembro equals to miembroId
        defaultBlogShouldBeFound("miembroId.equals=" + miembroId);

        // Get all the blogList where miembro equals to miembroId + 1
        defaultBlogShouldNotBeFound("miembroId.equals=" + (miembroId + 1));
    }


    @Test
    @Transactional
    public void getAllBlogsByCategoriaBlogIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);
        CategoriaContenidos categoriaBlog = CategoriaContenidosResourceIT.createEntity(em);
        em.persist(categoriaBlog);
        em.flush();
        blog.setCategoriaBlog(categoriaBlog);
        blogRepository.saveAndFlush(blog);
        Long categoriaBlogId = categoriaBlog.getId();

        // Get all the blogList where categoriaBlog equals to categoriaBlogId
        defaultBlogShouldBeFound("categoriaBlogId.equals=" + categoriaBlogId);

        // Get all the blogList where categoriaBlog equals to categoriaBlogId + 1
        defaultBlogShouldNotBeFound("categoriaBlogId.equals=" + (categoriaBlogId + 1));
    }


    @Test
    @Transactional
    public void getAllBlogsByGruposIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);
        Grupos grupos = GruposResourceIT.createEntity(em);
        em.persist(grupos);
        em.flush();
        blog.setGrupos(grupos);
        blogRepository.saveAndFlush(blog);
        Long gruposId = grupos.getId();

        // Get all the blogList where grupos equals to gruposId
        defaultBlogShouldBeFound("gruposId.equals=" + gruposId);

        // Get all the blogList where grupos equals to gruposId + 1
        defaultBlogShouldNotBeFound("gruposId.equals=" + (gruposId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBlogShouldBeFound(String filter) throws Exception {
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].tipoContenido").value(hasItem(DEFAULT_TIPO_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))))
            .andExpect(jsonPath("$.[*].estadoPublicacion").value(hasItem(DEFAULT_ESTADO_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())));

        // Check, that the count call also returns 1
        restBlogMockMvc.perform(get("/api/blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBlogShouldNotBeFound(String filter) throws Exception {
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBlogMockMvc.perform(get("/api/blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBlog() throws Exception {
        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlog() throws Exception {
        // Initialize the database
        blogService.save(blog);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBlogSearchRepository);

        int databaseSizeBeforeUpdate = blogRepository.findAll().size();

        // Update the blog
        Blog updatedBlog = blogRepository.findById(blog.getId()).get();
        // Disconnect from session so that the updates on updatedBlog are not directly saved in db
        em.detach(updatedBlog);
        updatedBlog
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoContenido(UPDATED_TIPO_CONTENIDO)
            .contenido(UPDATED_CONTENIDO)
            .fecha(UPDATED_FECHA)
            .audio(UPDATED_AUDIO)
            .audioContentType(UPDATED_AUDIO_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .banner(UPDATED_BANNER)
            .bannerContentType(UPDATED_BANNER_CONTENT_TYPE)
            .estadoPublicacion(UPDATED_ESTADO_PUBLICACION)
            .tipoConsumo(UPDATED_TIPO_CONSUMO)
            .valor(UPDATED_VALOR)
            .impuesto(UPDATED_IMPUESTO);

        restBlogMockMvc.perform(put("/api/blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlog)))
            .andExpect(status().isOk());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeUpdate);
        Blog testBlog = blogList.get(blogList.size() - 1);
        assertThat(testBlog.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testBlog.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testBlog.getTipoContenido()).isEqualTo(UPDATED_TIPO_CONTENIDO);
        assertThat(testBlog.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testBlog.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testBlog.getAudio()).isEqualTo(UPDATED_AUDIO);
        assertThat(testBlog.getAudioContentType()).isEqualTo(UPDATED_AUDIO_CONTENT_TYPE);
        assertThat(testBlog.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testBlog.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testBlog.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testBlog.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testBlog.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testBlog.getBanner()).isEqualTo(UPDATED_BANNER);
        assertThat(testBlog.getBannerContentType()).isEqualTo(UPDATED_BANNER_CONTENT_TYPE);
        assertThat(testBlog.getEstadoPublicacion()).isEqualTo(UPDATED_ESTADO_PUBLICACION);
        assertThat(testBlog.getTipoConsumo()).isEqualTo(UPDATED_TIPO_CONSUMO);
        assertThat(testBlog.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testBlog.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);

        // Validate the Blog in Elasticsearch
        verify(mockBlogSearchRepository, times(1)).save(testBlog);
    }

    @Test
    @Transactional
    public void updateNonExistingBlog() throws Exception {
        int databaseSizeBeforeUpdate = blogRepository.findAll().size();

        // Create the Blog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogMockMvc.perform(put("/api/blogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Blog in Elasticsearch
        verify(mockBlogSearchRepository, times(0)).save(blog);
    }

    @Test
    @Transactional
    public void deleteBlog() throws Exception {
        // Initialize the database
        blogService.save(blog);

        int databaseSizeBeforeDelete = blogRepository.findAll().size();

        // Delete the blog
        restBlogMockMvc.perform(delete("/api/blogs/{id}", blog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Blog in Elasticsearch
        verify(mockBlogSearchRepository, times(1)).deleteById(blog.getId());
    }

    @Test
    @Transactional
    public void searchBlog() throws Exception {
        // Initialize the database
        blogService.save(blog);
        when(mockBlogSearchRepository.search(queryStringQuery("id:" + blog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(blog), PageRequest.of(0, 1), 1));
        // Search the blog
        restBlogMockMvc.perform(get("/api/_search/blogs?query=id:" + blog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].tipoContenido").value(hasItem(DEFAULT_TIPO_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].audioContentType").value(hasItem(DEFAULT_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].audio").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO))))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].bannerContentType").value(hasItem(DEFAULT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER))))
            .andExpect(jsonPath("$.[*].estadoPublicacion").value(hasItem(DEFAULT_ESTADO_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].tipoConsumo").value(hasItem(DEFAULT_TIPO_CONSUMO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blog.class);
        Blog blog1 = new Blog();
        blog1.setId(1L);
        Blog blog2 = new Blog();
        blog2.setId(blog1.getId());
        assertThat(blog1).isEqualTo(blog2);
        blog2.setId(2L);
        assertThat(blog1).isNotEqualTo(blog2);
        blog1.setId(null);
        assertThat(blog1).isNotEqualTo(blog2);
    }
}
