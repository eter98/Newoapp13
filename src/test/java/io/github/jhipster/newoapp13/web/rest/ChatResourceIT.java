package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.NewoApp13App;
import io.github.jhipster.newoapp13.domain.Chat;
import io.github.jhipster.newoapp13.domain.ChatsListado;
import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.ChatRepository;
import io.github.jhipster.newoapp13.repository.search.ChatSearchRepository;
import io.github.jhipster.newoapp13.service.ChatService;
import io.github.jhipster.newoapp13.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.newoapp13.service.dto.ChatCriteria;
import io.github.jhipster.newoapp13.service.ChatQueryService;

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

/**
 * Integration tests for the {@link ChatResource} REST controller.
 */
@SpringBootTest(classes = NewoApp13App.class)
public class ChatResourceIT {

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SENDER = 1;
    private static final Integer UPDATED_SENDER = 2;
    private static final Integer SMALLER_SENDER = 1 - 1;

    private static final Boolean DEFAULT_READ = false;
    private static final Boolean UPDATED_READ = true;

    private static final Boolean DEFAULT_DELIVERED = false;
    private static final Boolean UPDATED_DELIVERED = true;

    private static final Boolean DEFAULT_SENT = false;
    private static final Boolean UPDATED_SENT = true;

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    /**
     * This repository is mocked in the io.github.jhipster.newoapp13.repository.search test package.
     *
     * @see io.github.jhipster.newoapp13.repository.search.ChatSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChatSearchRepository mockChatSearchRepository;

    @Autowired
    private ChatQueryService chatQueryService;

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

    private MockMvc restChatMockMvc;

    private Chat chat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChatResource chatResource = new ChatResource(chatService, chatQueryService);
        this.restChatMockMvc = MockMvcBuilders.standaloneSetup(chatResource)
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
    public static Chat createEntity(EntityManager em) {
        Chat chat = new Chat()
            .mensaje(DEFAULT_MENSAJE)
            .sender(DEFAULT_SENDER)
            .read(DEFAULT_READ)
            .delivered(DEFAULT_DELIVERED)
            .sent(DEFAULT_SENT)
            .fecha(DEFAULT_FECHA);
        return chat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createUpdatedEntity(EntityManager em) {
        Chat chat = new Chat()
            .mensaje(UPDATED_MENSAJE)
            .sender(UPDATED_SENDER)
            .read(UPDATED_READ)
            .delivered(UPDATED_DELIVERED)
            .sent(UPDATED_SENT)
            .fecha(UPDATED_FECHA);
        return chat;
    }

    @BeforeEach
    public void initTest() {
        chat = createEntity(em);
    }

    @Test
    @Transactional
    public void createChat() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat
        restChatMockMvc.perform(post("/api/chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chat)))
            .andExpect(status().isCreated());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate + 1);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
        assertThat(testChat.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testChat.isRead()).isEqualTo(DEFAULT_READ);
        assertThat(testChat.isDelivered()).isEqualTo(DEFAULT_DELIVERED);
        assertThat(testChat.isSent()).isEqualTo(DEFAULT_SENT);
        assertThat(testChat.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(1)).save(testChat);
    }

    @Test
    @Transactional
    public void createChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat with an existing ID
        chat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMockMvc.perform(post("/api/chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chat)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(0)).save(chat);
    }


    @Test
    @Transactional
    public void checkMensajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatRepository.findAll().size();
        // set the field null
        chat.setMensaje(null);

        // Create the Chat, which fails.

        restChatMockMvc.perform(post("/api/chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chat)))
            .andExpect(status().isBadRequest());

        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChats() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList
        restChatMockMvc.perform(get("/api/chats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE.toString())))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.booleanValue())))
            .andExpect(jsonPath("$.[*].sent").value(hasItem(DEFAULT_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));
    }
    
    @Test
    @Transactional
    public void getChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chat.getId().intValue()))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE.toString()))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER))
            .andExpect(jsonPath("$.read").value(DEFAULT_READ.booleanValue()))
            .andExpect(jsonPath("$.delivered").value(DEFAULT_DELIVERED.booleanValue()))
            .andExpect(jsonPath("$.sent").value(DEFAULT_SENT.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)));
    }

    @Test
    @Transactional
    public void getAllChatsByMensajeIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where mensaje equals to DEFAULT_MENSAJE
        defaultChatShouldBeFound("mensaje.equals=" + DEFAULT_MENSAJE);

        // Get all the chatList where mensaje equals to UPDATED_MENSAJE
        defaultChatShouldNotBeFound("mensaje.equals=" + UPDATED_MENSAJE);
    }

    @Test
    @Transactional
    public void getAllChatsByMensajeIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where mensaje in DEFAULT_MENSAJE or UPDATED_MENSAJE
        defaultChatShouldBeFound("mensaje.in=" + DEFAULT_MENSAJE + "," + UPDATED_MENSAJE);

        // Get all the chatList where mensaje equals to UPDATED_MENSAJE
        defaultChatShouldNotBeFound("mensaje.in=" + UPDATED_MENSAJE);
    }

    @Test
    @Transactional
    public void getAllChatsByMensajeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where mensaje is not null
        defaultChatShouldBeFound("mensaje.specified=true");

        // Get all the chatList where mensaje is null
        defaultChatShouldNotBeFound("mensaje.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender equals to DEFAULT_SENDER
        defaultChatShouldBeFound("sender.equals=" + DEFAULT_SENDER);

        // Get all the chatList where sender equals to UPDATED_SENDER
        defaultChatShouldNotBeFound("sender.equals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender in DEFAULT_SENDER or UPDATED_SENDER
        defaultChatShouldBeFound("sender.in=" + DEFAULT_SENDER + "," + UPDATED_SENDER);

        // Get all the chatList where sender equals to UPDATED_SENDER
        defaultChatShouldNotBeFound("sender.in=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender is not null
        defaultChatShouldBeFound("sender.specified=true");

        // Get all the chatList where sender is null
        defaultChatShouldNotBeFound("sender.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender is greater than or equal to DEFAULT_SENDER
        defaultChatShouldBeFound("sender.greaterThanOrEqual=" + DEFAULT_SENDER);

        // Get all the chatList where sender is greater than or equal to UPDATED_SENDER
        defaultChatShouldNotBeFound("sender.greaterThanOrEqual=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender is less than or equal to DEFAULT_SENDER
        defaultChatShouldBeFound("sender.lessThanOrEqual=" + DEFAULT_SENDER);

        // Get all the chatList where sender is less than or equal to SMALLER_SENDER
        defaultChatShouldNotBeFound("sender.lessThanOrEqual=" + SMALLER_SENDER);
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsLessThanSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender is less than DEFAULT_SENDER
        defaultChatShouldNotBeFound("sender.lessThan=" + DEFAULT_SENDER);

        // Get all the chatList where sender is less than UPDATED_SENDER
        defaultChatShouldBeFound("sender.lessThan=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllChatsBySenderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sender is greater than DEFAULT_SENDER
        defaultChatShouldNotBeFound("sender.greaterThan=" + DEFAULT_SENDER);

        // Get all the chatList where sender is greater than SMALLER_SENDER
        defaultChatShouldBeFound("sender.greaterThan=" + SMALLER_SENDER);
    }


    @Test
    @Transactional
    public void getAllChatsByReadIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where read equals to DEFAULT_READ
        defaultChatShouldBeFound("read.equals=" + DEFAULT_READ);

        // Get all the chatList where read equals to UPDATED_READ
        defaultChatShouldNotBeFound("read.equals=" + UPDATED_READ);
    }

    @Test
    @Transactional
    public void getAllChatsByReadIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where read in DEFAULT_READ or UPDATED_READ
        defaultChatShouldBeFound("read.in=" + DEFAULT_READ + "," + UPDATED_READ);

        // Get all the chatList where read equals to UPDATED_READ
        defaultChatShouldNotBeFound("read.in=" + UPDATED_READ);
    }

    @Test
    @Transactional
    public void getAllChatsByReadIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where read is not null
        defaultChatShouldBeFound("read.specified=true");

        // Get all the chatList where read is null
        defaultChatShouldNotBeFound("read.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsByDeliveredIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where delivered equals to DEFAULT_DELIVERED
        defaultChatShouldBeFound("delivered.equals=" + DEFAULT_DELIVERED);

        // Get all the chatList where delivered equals to UPDATED_DELIVERED
        defaultChatShouldNotBeFound("delivered.equals=" + UPDATED_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllChatsByDeliveredIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where delivered in DEFAULT_DELIVERED or UPDATED_DELIVERED
        defaultChatShouldBeFound("delivered.in=" + DEFAULT_DELIVERED + "," + UPDATED_DELIVERED);

        // Get all the chatList where delivered equals to UPDATED_DELIVERED
        defaultChatShouldNotBeFound("delivered.in=" + UPDATED_DELIVERED);
    }

    @Test
    @Transactional
    public void getAllChatsByDeliveredIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where delivered is not null
        defaultChatShouldBeFound("delivered.specified=true");

        // Get all the chatList where delivered is null
        defaultChatShouldNotBeFound("delivered.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsBySentIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sent equals to DEFAULT_SENT
        defaultChatShouldBeFound("sent.equals=" + DEFAULT_SENT);

        // Get all the chatList where sent equals to UPDATED_SENT
        defaultChatShouldNotBeFound("sent.equals=" + UPDATED_SENT);
    }

    @Test
    @Transactional
    public void getAllChatsBySentIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sent in DEFAULT_SENT or UPDATED_SENT
        defaultChatShouldBeFound("sent.in=" + DEFAULT_SENT + "," + UPDATED_SENT);

        // Get all the chatList where sent equals to UPDATED_SENT
        defaultChatShouldNotBeFound("sent.in=" + UPDATED_SENT);
    }

    @Test
    @Transactional
    public void getAllChatsBySentIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where sent is not null
        defaultChatShouldBeFound("sent.specified=true");

        // Get all the chatList where sent is null
        defaultChatShouldNotBeFound("sent.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha equals to DEFAULT_FECHA
        defaultChatShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the chatList where fecha equals to UPDATED_FECHA
        defaultChatShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultChatShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the chatList where fecha equals to UPDATED_FECHA
        defaultChatShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha is not null
        defaultChatShouldBeFound("fecha.specified=true");

        // Get all the chatList where fecha is null
        defaultChatShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha is greater than or equal to DEFAULT_FECHA
        defaultChatShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the chatList where fecha is greater than or equal to UPDATED_FECHA
        defaultChatShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha is less than or equal to DEFAULT_FECHA
        defaultChatShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the chatList where fecha is less than or equal to SMALLER_FECHA
        defaultChatShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha is less than DEFAULT_FECHA
        defaultChatShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the chatList where fecha is less than UPDATED_FECHA
        defaultChatShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllChatsByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where fecha is greater than DEFAULT_FECHA
        defaultChatShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the chatList where fecha is greater than SMALLER_FECHA
        defaultChatShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }


    @Test
    @Transactional
    public void getAllChatsByChatsListadoIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);
        ChatsListado chatsListado = ChatsListadoResourceIT.createEntity(em);
        em.persist(chatsListado);
        em.flush();
        chat.setChatsListado(chatsListado);
        chatRepository.saveAndFlush(chat);
        Long chatsListadoId = chatsListado.getId();

        // Get all the chatList where chatsListado equals to chatsListadoId
        defaultChatShouldBeFound("chatsListadoId.equals=" + chatsListadoId);

        // Get all the chatList where chatsListado equals to chatsListadoId + 1
        defaultChatShouldNotBeFound("chatsListadoId.equals=" + (chatsListadoId + 1));
    }


    @Test
    @Transactional
    public void getAllChatsByDeIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);
        Miembros de = MiembrosResourceIT.createEntity(em);
        em.persist(de);
        em.flush();
        chat.setDe(de);
        chatRepository.saveAndFlush(chat);
        Long deId = de.getId();

        // Get all the chatList where de equals to deId
        defaultChatShouldBeFound("deId.equals=" + deId);

        // Get all the chatList where de equals to deId + 1
        defaultChatShouldNotBeFound("deId.equals=" + (deId + 1));
    }


    @Test
    @Transactional
    public void getAllChatsByParaIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);
        Miembros para = MiembrosResourceIT.createEntity(em);
        em.persist(para);
        em.flush();
        chat.setPara(para);
        chatRepository.saveAndFlush(chat);
        Long paraId = para.getId();

        // Get all the chatList where para equals to paraId
        defaultChatShouldBeFound("paraId.equals=" + paraId);

        // Get all the chatList where para equals to paraId + 1
        defaultChatShouldNotBeFound("paraId.equals=" + (paraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChatShouldBeFound(String filter) throws Exception {
        restChatMockMvc.perform(get("/api/chats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.booleanValue())))
            .andExpect(jsonPath("$.[*].sent").value(hasItem(DEFAULT_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));

        // Check, that the count call also returns 1
        restChatMockMvc.perform(get("/api/chats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChatShouldNotBeFound(String filter) throws Exception {
        restChatMockMvc.perform(get("/api/chats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChatMockMvc.perform(get("/api/chats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChat() throws Exception {
        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChat() throws Exception {
        // Initialize the database
        chatService.save(chat);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockChatSearchRepository);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat
        Chat updatedChat = chatRepository.findById(chat.getId()).get();
        // Disconnect from session so that the updates on updatedChat are not directly saved in db
        em.detach(updatedChat);
        updatedChat
            .mensaje(UPDATED_MENSAJE)
            .sender(UPDATED_SENDER)
            .read(UPDATED_READ)
            .delivered(UPDATED_DELIVERED)
            .sent(UPDATED_SENT)
            .fecha(UPDATED_FECHA);

        restChatMockMvc.perform(put("/api/chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChat)))
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getMensaje()).isEqualTo(UPDATED_MENSAJE);
        assertThat(testChat.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testChat.isRead()).isEqualTo(UPDATED_READ);
        assertThat(testChat.isDelivered()).isEqualTo(UPDATED_DELIVERED);
        assertThat(testChat.isSent()).isEqualTo(UPDATED_SENT);
        assertThat(testChat.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(1)).save(testChat);
    }

    @Test
    @Transactional
    public void updateNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Create the Chat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatMockMvc.perform(put("/api/chats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chat)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(0)).save(chat);
    }

    @Test
    @Transactional
    public void deleteChat() throws Exception {
        // Initialize the database
        chatService.save(chat);

        int databaseSizeBeforeDelete = chatRepository.findAll().size();

        // Delete the chat
        restChatMockMvc.perform(delete("/api/chats/{id}", chat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Chat in Elasticsearch
        verify(mockChatSearchRepository, times(1)).deleteById(chat.getId());
    }

    @Test
    @Transactional
    public void searchChat() throws Exception {
        // Initialize the database
        chatService.save(chat);
        when(mockChatSearchRepository.search(queryStringQuery("id:" + chat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(chat), PageRequest.of(0, 1), 1));
        // Search the chat
        restChatMockMvc.perform(get("/api/_search/chats?query=id:" + chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.booleanValue())))
            .andExpect(jsonPath("$.[*].sent").value(hasItem(DEFAULT_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chat.class);
        Chat chat1 = new Chat();
        chat1.setId(1L);
        Chat chat2 = new Chat();
        chat2.setId(chat1.getId());
        assertThat(chat1).isEqualTo(chat2);
        chat2.setId(2L);
        assertThat(chat1).isNotEqualTo(chat2);
        chat1.setId(null);
        assertThat(chat1).isNotEqualTo(chat2);
    }
}
