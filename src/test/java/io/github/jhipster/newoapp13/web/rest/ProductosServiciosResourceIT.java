package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.ProductosServicios;
import io.github.jhipster.newoapp13.repository.ProductosServiciosRepository;
import io.github.jhipster.newoapp13.repository.search.ProductosServiciosSearchRepository;
import io.github.jhipster.newoapp13.service.ProductosServiciosService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.ProductosServiciosCriteria;
import io.github.jhipster.newoapp13.service.ProductosServiciosQueryService;

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
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.newoapp13.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
/**
 * Integration tests for the {@link ProductosServiciosResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class ProductosServiciosResourceIT {

    private static final String DEFAULT_NOMBRE_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PRODUCTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INVENTARIABLES = false;
    private static final Boolean UPDATED_INVENTARIABLES = true;

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;
    private static final Integer SMALLER_VALOR = 1 - 1;

    private static final Impuestod DEFAULT_IMPUESTO = Impuestod.IVA19;
    private static final Impuestod UPDATED_IMPUESTO = Impuestod.IVA6;

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_4_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_5 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_5 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_5_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_5_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_6 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_6 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_6_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_6_CONTENT_TYPE = "image/png";

    @Autowired
    private ProductosServiciosRepository productosServiciosRepository;

    @Autowired
    private ProductosServiciosService productosServiciosService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.ProductosServiciosSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductosServiciosSearchRepository mockProductosServiciosSearchRepository;

    @Autowired
    private ProductosServiciosQueryService productosServiciosQueryService;

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

    private MockMvc restProductosServiciosMockMvc;

    private ProductosServicios productosServicios;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductosServiciosResource productosServiciosResource = new ProductosServiciosResource(productosServiciosService, productosServiciosQueryService);
        this.restProductosServiciosMockMvc = MockMvcBuilders.standaloneSetup(productosServiciosResource)
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
    public static ProductosServicios createEntity(EntityManager em) {
        ProductosServicios productosServicios = new ProductosServicios()
            .nombreProducto(DEFAULT_NOMBRE_PRODUCTO)
            .descripcion(DEFAULT_DESCRIPCION)
            .inventariables(DEFAULT_INVENTARIABLES)
            .valor(DEFAULT_VALOR)
            .impuesto(DEFAULT_IMPUESTO)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .imagen1(DEFAULT_IMAGEN_1)
            .imagen1ContentType(DEFAULT_IMAGEN_1_CONTENT_TYPE)
            .imagen2(DEFAULT_IMAGEN_2)
            .imagen2ContentType(DEFAULT_IMAGEN_2_CONTENT_TYPE)
            .imagen3(DEFAULT_IMAGEN_3)
            .imagen3ContentType(DEFAULT_IMAGEN_3_CONTENT_TYPE)
            .imagen4(DEFAULT_IMAGEN_4)
            .imagen4ContentType(DEFAULT_IMAGEN_4_CONTENT_TYPE)
            .imagen5(DEFAULT_IMAGEN_5)
            .imagen5ContentType(DEFAULT_IMAGEN_5_CONTENT_TYPE)
            .imagen6(DEFAULT_IMAGEN_6)
            .imagen6ContentType(DEFAULT_IMAGEN_6_CONTENT_TYPE);
        return productosServicios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductosServicios createUpdatedEntity(EntityManager em) {
        ProductosServicios productosServicios = new ProductosServicios()
            .nombreProducto(UPDATED_NOMBRE_PRODUCTO)
            .descripcion(UPDATED_DESCRIPCION)
            .inventariables(UPDATED_INVENTARIABLES)
            .valor(UPDATED_VALOR)
            .impuesto(UPDATED_IMPUESTO)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE);
        return productosServicios;
    }

    @BeforeEach
    public void initTest() {
        productosServicios = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductosServicios() throws Exception {
        int databaseSizeBeforeCreate = productosServiciosRepository.findAll().size();

        // Create the ProductosServicios
        restProductosServiciosMockMvc.perform(post("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isCreated());

        // Validate the ProductosServicios in the database
        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeCreate + 1);
        ProductosServicios testProductosServicios = productosServiciosList.get(productosServiciosList.size() - 1);
        assertThat(testProductosServicios.getNombreProducto()).isEqualTo(DEFAULT_NOMBRE_PRODUCTO);
        assertThat(testProductosServicios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProductosServicios.isInventariables()).isEqualTo(DEFAULT_INVENTARIABLES);
        assertThat(testProductosServicios.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testProductosServicios.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testProductosServicios.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testProductosServicios.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen1()).isEqualTo(DEFAULT_IMAGEN_1);
        assertThat(testProductosServicios.getImagen1ContentType()).isEqualTo(DEFAULT_IMAGEN_1_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen2()).isEqualTo(DEFAULT_IMAGEN_2);
        assertThat(testProductosServicios.getImagen2ContentType()).isEqualTo(DEFAULT_IMAGEN_2_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen3()).isEqualTo(DEFAULT_IMAGEN_3);
        assertThat(testProductosServicios.getImagen3ContentType()).isEqualTo(DEFAULT_IMAGEN_3_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen4()).isEqualTo(DEFAULT_IMAGEN_4);
        assertThat(testProductosServicios.getImagen4ContentType()).isEqualTo(DEFAULT_IMAGEN_4_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen5()).isEqualTo(DEFAULT_IMAGEN_5);
        assertThat(testProductosServicios.getImagen5ContentType()).isEqualTo(DEFAULT_IMAGEN_5_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen6()).isEqualTo(DEFAULT_IMAGEN_6);
        assertThat(testProductosServicios.getImagen6ContentType()).isEqualTo(DEFAULT_IMAGEN_6_CONTENT_TYPE);

        // Validate the ProductosServicios in Elasticsearch
        verify(mockProductosServiciosSearchRepository, times(1)).save(testProductosServicios);
    }

    @Test
    @Transactional
    public void createProductosServiciosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productosServiciosRepository.findAll().size();

        // Create the ProductosServicios with an existing ID
        productosServicios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductosServiciosMockMvc.perform(post("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isBadRequest());

        // Validate the ProductosServicios in the database
        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductosServicios in Elasticsearch
        verify(mockProductosServiciosSearchRepository, times(0)).save(productosServicios);
    }


    @Test
    @Transactional
    public void checkNombreProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productosServiciosRepository.findAll().size();
        // set the field null
        productosServicios.setNombreProducto(null);

        // Create the ProductosServicios, which fails.

        restProductosServiciosMockMvc.perform(post("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isBadRequest());

        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productosServiciosRepository.findAll().size();
        // set the field null
        productosServicios.setDescripcion(null);

        // Create the ProductosServicios, which fails.

        restProductosServiciosMockMvc.perform(post("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isBadRequest());

        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = productosServiciosRepository.findAll().size();
        // set the field null
        productosServicios.setValor(null);

        // Create the ProductosServicios, which fails.

        restProductosServiciosMockMvc.perform(post("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isBadRequest());

        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImpuestoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productosServiciosRepository.findAll().size();
        // set the field null
        productosServicios.setImpuesto(null);

        // Create the ProductosServicios, which fails.

        restProductosServiciosMockMvc.perform(post("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isBadRequest());

        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductosServicios() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProducto").value(hasItem(DEFAULT_NOMBRE_PRODUCTO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].inventariables").value(hasItem(DEFAULT_INVENTARIABLES.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))));
    }
    
    @Test
    @Transactional
    public void getProductosServicios() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get the productosServicios
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios/{id}", productosServicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productosServicios.getId().intValue()))
            .andExpect(jsonPath("$.nombreProducto").value(DEFAULT_NOMBRE_PRODUCTO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.inventariables").value(DEFAULT_INVENTARIABLES.booleanValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.toString()))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.imagen1ContentType").value(DEFAULT_IMAGEN_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen1").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_1)))
            .andExpect(jsonPath("$.imagen2ContentType").value(DEFAULT_IMAGEN_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen2").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_2)))
            .andExpect(jsonPath("$.imagen3ContentType").value(DEFAULT_IMAGEN_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen3").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_3)))
            .andExpect(jsonPath("$.imagen4ContentType").value(DEFAULT_IMAGEN_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen4").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_4)))
            .andExpect(jsonPath("$.imagen5ContentType").value(DEFAULT_IMAGEN_5_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen5").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_5)))
            .andExpect(jsonPath("$.imagen6ContentType").value(DEFAULT_IMAGEN_6_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen6").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_6)));
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByNombreProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where nombreProducto equals to DEFAULT_NOMBRE_PRODUCTO
        defaultProductosServiciosShouldBeFound("nombreProducto.equals=" + DEFAULT_NOMBRE_PRODUCTO);

        // Get all the productosServiciosList where nombreProducto equals to UPDATED_NOMBRE_PRODUCTO
        defaultProductosServiciosShouldNotBeFound("nombreProducto.equals=" + UPDATED_NOMBRE_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByNombreProductoIsInShouldWork() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where nombreProducto in DEFAULT_NOMBRE_PRODUCTO or UPDATED_NOMBRE_PRODUCTO
        defaultProductosServiciosShouldBeFound("nombreProducto.in=" + DEFAULT_NOMBRE_PRODUCTO + "," + UPDATED_NOMBRE_PRODUCTO);

        // Get all the productosServiciosList where nombreProducto equals to UPDATED_NOMBRE_PRODUCTO
        defaultProductosServiciosShouldNotBeFound("nombreProducto.in=" + UPDATED_NOMBRE_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByNombreProductoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where nombreProducto is not null
        defaultProductosServiciosShouldBeFound("nombreProducto.specified=true");

        // Get all the productosServiciosList where nombreProducto is null
        defaultProductosServiciosShouldNotBeFound("nombreProducto.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where descripcion equals to DEFAULT_DESCRIPCION
        defaultProductosServiciosShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the productosServiciosList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductosServiciosShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultProductosServiciosShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the productosServiciosList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductosServiciosShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where descripcion is not null
        defaultProductosServiciosShouldBeFound("descripcion.specified=true");

        // Get all the productosServiciosList where descripcion is null
        defaultProductosServiciosShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByInventariablesIsEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where inventariables equals to DEFAULT_INVENTARIABLES
        defaultProductosServiciosShouldBeFound("inventariables.equals=" + DEFAULT_INVENTARIABLES);

        // Get all the productosServiciosList where inventariables equals to UPDATED_INVENTARIABLES
        defaultProductosServiciosShouldNotBeFound("inventariables.equals=" + UPDATED_INVENTARIABLES);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByInventariablesIsInShouldWork() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where inventariables in DEFAULT_INVENTARIABLES or UPDATED_INVENTARIABLES
        defaultProductosServiciosShouldBeFound("inventariables.in=" + DEFAULT_INVENTARIABLES + "," + UPDATED_INVENTARIABLES);

        // Get all the productosServiciosList where inventariables equals to UPDATED_INVENTARIABLES
        defaultProductosServiciosShouldNotBeFound("inventariables.in=" + UPDATED_INVENTARIABLES);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByInventariablesIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where inventariables is not null
        defaultProductosServiciosShouldBeFound("inventariables.specified=true");

        // Get all the productosServiciosList where inventariables is null
        defaultProductosServiciosShouldNotBeFound("inventariables.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor equals to DEFAULT_VALOR
        defaultProductosServiciosShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the productosServiciosList where valor equals to UPDATED_VALOR
        defaultProductosServiciosShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultProductosServiciosShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the productosServiciosList where valor equals to UPDATED_VALOR
        defaultProductosServiciosShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor is not null
        defaultProductosServiciosShouldBeFound("valor.specified=true");

        // Get all the productosServiciosList where valor is null
        defaultProductosServiciosShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor is greater than or equal to DEFAULT_VALOR
        defaultProductosServiciosShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the productosServiciosList where valor is greater than or equal to UPDATED_VALOR
        defaultProductosServiciosShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor is less than or equal to DEFAULT_VALOR
        defaultProductosServiciosShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the productosServiciosList where valor is less than or equal to SMALLER_VALOR
        defaultProductosServiciosShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor is less than DEFAULT_VALOR
        defaultProductosServiciosShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the productosServiciosList where valor is less than UPDATED_VALOR
        defaultProductosServiciosShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where valor is greater than DEFAULT_VALOR
        defaultProductosServiciosShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the productosServiciosList where valor is greater than SMALLER_VALOR
        defaultProductosServiciosShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllProductosServiciosByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where impuesto equals to DEFAULT_IMPUESTO
        defaultProductosServiciosShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the productosServiciosList where impuesto equals to UPDATED_IMPUESTO
        defaultProductosServiciosShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultProductosServiciosShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the productosServiciosList where impuesto equals to UPDATED_IMPUESTO
        defaultProductosServiciosShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosServiciosByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosServiciosRepository.saveAndFlush(productosServicios);

        // Get all the productosServiciosList where impuesto is not null
        defaultProductosServiciosShouldBeFound("impuesto.specified=true");

        // Get all the productosServiciosList where impuesto is null
        defaultProductosServiciosShouldNotBeFound("impuesto.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductosServiciosShouldBeFound(String filter) throws Exception {
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProducto").value(hasItem(DEFAULT_NOMBRE_PRODUCTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].inventariables").value(hasItem(DEFAULT_INVENTARIABLES.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))));

        // Check, that the count call also returns 1
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductosServiciosShouldNotBeFound(String filter) throws Exception {
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductosServicios() throws Exception {
        // Get the productosServicios
        restProductosServiciosMockMvc.perform(get("/api/productos-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductosServicios() throws Exception {
        // Initialize the database
        productosServiciosService.save(productosServicios);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockProductosServiciosSearchRepository);

        int databaseSizeBeforeUpdate = productosServiciosRepository.findAll().size();

        // Update the productosServicios
        ProductosServicios updatedProductosServicios = productosServiciosRepository.findById(productosServicios.getId()).get();
        // Disconnect from session so that the updates on updatedProductosServicios are not directly saved in db
        em.detach(updatedProductosServicios);
        updatedProductosServicios
            .nombreProducto(UPDATED_NOMBRE_PRODUCTO)
            .descripcion(UPDATED_DESCRIPCION)
            .inventariables(UPDATED_INVENTARIABLES)
            .valor(UPDATED_VALOR)
            .impuesto(UPDATED_IMPUESTO)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagen1(UPDATED_IMAGEN_1)
            .imagen1ContentType(UPDATED_IMAGEN_1_CONTENT_TYPE)
            .imagen2(UPDATED_IMAGEN_2)
            .imagen2ContentType(UPDATED_IMAGEN_2_CONTENT_TYPE)
            .imagen3(UPDATED_IMAGEN_3)
            .imagen3ContentType(UPDATED_IMAGEN_3_CONTENT_TYPE)
            .imagen4(UPDATED_IMAGEN_4)
            .imagen4ContentType(UPDATED_IMAGEN_4_CONTENT_TYPE)
            .imagen5(UPDATED_IMAGEN_5)
            .imagen5ContentType(UPDATED_IMAGEN_5_CONTENT_TYPE)
            .imagen6(UPDATED_IMAGEN_6)
            .imagen6ContentType(UPDATED_IMAGEN_6_CONTENT_TYPE);

        restProductosServiciosMockMvc.perform(put("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductosServicios)))
            .andExpect(status().isOk());

        // Validate the ProductosServicios in the database
        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeUpdate);
        ProductosServicios testProductosServicios = productosServiciosList.get(productosServiciosList.size() - 1);
        assertThat(testProductosServicios.getNombreProducto()).isEqualTo(UPDATED_NOMBRE_PRODUCTO);
        assertThat(testProductosServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductosServicios.isInventariables()).isEqualTo(UPDATED_INVENTARIABLES);
        assertThat(testProductosServicios.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testProductosServicios.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testProductosServicios.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testProductosServicios.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen1()).isEqualTo(UPDATED_IMAGEN_1);
        assertThat(testProductosServicios.getImagen1ContentType()).isEqualTo(UPDATED_IMAGEN_1_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen2()).isEqualTo(UPDATED_IMAGEN_2);
        assertThat(testProductosServicios.getImagen2ContentType()).isEqualTo(UPDATED_IMAGEN_2_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen3()).isEqualTo(UPDATED_IMAGEN_3);
        assertThat(testProductosServicios.getImagen3ContentType()).isEqualTo(UPDATED_IMAGEN_3_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen4()).isEqualTo(UPDATED_IMAGEN_4);
        assertThat(testProductosServicios.getImagen4ContentType()).isEqualTo(UPDATED_IMAGEN_4_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen5()).isEqualTo(UPDATED_IMAGEN_5);
        assertThat(testProductosServicios.getImagen5ContentType()).isEqualTo(UPDATED_IMAGEN_5_CONTENT_TYPE);
        assertThat(testProductosServicios.getImagen6()).isEqualTo(UPDATED_IMAGEN_6);
        assertThat(testProductosServicios.getImagen6ContentType()).isEqualTo(UPDATED_IMAGEN_6_CONTENT_TYPE);

        // Validate the ProductosServicios in Elasticsearch
        verify(mockProductosServiciosSearchRepository, times(1)).save(testProductosServicios);
    }

    @Test
    @Transactional
    public void updateNonExistingProductosServicios() throws Exception {
        int databaseSizeBeforeUpdate = productosServiciosRepository.findAll().size();

        // Create the ProductosServicios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductosServiciosMockMvc.perform(put("/api/productos-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosServicios)))
            .andExpect(status().isBadRequest());

        // Validate the ProductosServicios in the database
        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductosServicios in Elasticsearch
        verify(mockProductosServiciosSearchRepository, times(0)).save(productosServicios);
    }

    @Test
    @Transactional
    public void deleteProductosServicios() throws Exception {
        // Initialize the database
        productosServiciosService.save(productosServicios);

        int databaseSizeBeforeDelete = productosServiciosRepository.findAll().size();

        // Delete the productosServicios
        restProductosServiciosMockMvc.perform(delete("/api/productos-servicios/{id}", productosServicios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductosServicios> productosServiciosList = productosServiciosRepository.findAll();
        assertThat(productosServiciosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductosServicios in Elasticsearch
        verify(mockProductosServiciosSearchRepository, times(1)).deleteById(productosServicios.getId());
    }

    @Test
    @Transactional
    public void searchProductosServicios() throws Exception {
        // Initialize the database
        productosServiciosService.save(productosServicios);
        when(mockProductosServiciosSearchRepository.search(queryStringQuery("id:" + productosServicios.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productosServicios), PageRequest.of(0, 1), 1));
        // Search the productosServicios
        restProductosServiciosMockMvc.perform(get("/api/_search/productos-servicios?query=id:" + productosServicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProducto").value(hasItem(DEFAULT_NOMBRE_PRODUCTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].inventariables").value(hasItem(DEFAULT_INVENTARIABLES.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.toString())))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagen1ContentType").value(hasItem(DEFAULT_IMAGEN_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_1))))
            .andExpect(jsonPath("$.[*].imagen2ContentType").value(hasItem(DEFAULT_IMAGEN_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_2))))
            .andExpect(jsonPath("$.[*].imagen3ContentType").value(hasItem(DEFAULT_IMAGEN_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_3))))
            .andExpect(jsonPath("$.[*].imagen4ContentType").value(hasItem(DEFAULT_IMAGEN_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_4))))
            .andExpect(jsonPath("$.[*].imagen5ContentType").value(hasItem(DEFAULT_IMAGEN_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_5))))
            .andExpect(jsonPath("$.[*].imagen6ContentType").value(hasItem(DEFAULT_IMAGEN_6_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen6").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_6))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductosServicios.class);
        ProductosServicios productosServicios1 = new ProductosServicios();
        productosServicios1.setId(1L);
        ProductosServicios productosServicios2 = new ProductosServicios();
        productosServicios2.setId(productosServicios1.getId());
        assertThat(productosServicios1).isEqualTo(productosServicios2);
        productosServicios2.setId(2L);
        assertThat(productosServicios1).isNotEqualTo(productosServicios2);
        productosServicios1.setId(null);
        assertThat(productosServicios1).isNotEqualTo(productosServicios2);
    }
}
