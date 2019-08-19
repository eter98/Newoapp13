package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.Chat;
import io.github.jhipster.newoapp13.service.ChatService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.ChatCriteria;
import io.github.jhipster.newoapp13.service.ChatQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.Chat}.
 */
@RestController
@RequestMapping("/api")
public class ChatResource {

    private final Logger log = LoggerFactory.getLogger(ChatResource.class);

    private static final String ENTITY_NAME = "chat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatService chatService;

    private final ChatQueryService chatQueryService;

    public ChatResource(ChatService chatService, ChatQueryService chatQueryService) {
        this.chatService = chatService;
        this.chatQueryService = chatQueryService;
    }

    /**
     * {@code POST  /chats} : Create a new chat.
     *
     * @param chat the chat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chat, or with status {@code 400 (Bad Request)} if the chat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chats")
    public ResponseEntity<Chat> createChat(@Valid @RequestBody Chat chat) throws URISyntaxException {
        log.debug("REST request to save Chat : {}", chat);
        if (chat.getId() != null) {
            throw new BadRequestAlertException("A new chat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chat result = chatService.save(chat);
        return ResponseEntity.created(new URI("/api/chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chats} : Updates an existing chat.
     *
     * @param chat the chat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chat,
     * or with status {@code 400 (Bad Request)} if the chat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chats")
    public ResponseEntity<Chat> updateChat(@Valid @RequestBody Chat chat) throws URISyntaxException {
        log.debug("REST request to update Chat : {}", chat);
        if (chat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chat result = chatService.save(chat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chats} : get all the chats.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chats in body.
     */
    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> getAllChats(ChatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Chats by criteria: {}", criteria);
        Page<Chat> page = chatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /chats/count} : count all the chats.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/chats/count")
    public ResponseEntity<Long> countChats(ChatCriteria criteria) {
        log.debug("REST request to count Chats by criteria: {}", criteria);
        return ResponseEntity.ok().body(chatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chats/:id} : get the "id" chat.
     *
     * @param id the id of the chat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chats/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        log.debug("REST request to get Chat : {}", id);
        Optional<Chat> chat = chatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chat);
    }

    /**
     * {@code DELETE  /chats/:id} : delete the "id" chat.
     *
     * @param id the id of the chat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chats/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        log.debug("REST request to delete Chat : {}", id);
        chatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/chats?query=:query} : search for the chat corresponding
     * to the query.
     *
     * @param query the query of the chat search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/chats")
    public ResponseEntity<List<Chat>> searchChats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Chats for query {}", query);
        Page<Chat> page = chatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
