package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.ChatsListado;
import io.github.jhipster.newoapp13.service.ChatsListadoService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.ChatsListadoCriteria;
import io.github.jhipster.newoapp13.service.ChatsListadoQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.ChatsListado}.
 */
@RestController
@RequestMapping("/api")
public class ChatsListadoResource {

    private final Logger log = LoggerFactory.getLogger(ChatsListadoResource.class);

    private static final String ENTITY_NAME = "chatsListado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatsListadoService chatsListadoService;

    private final ChatsListadoQueryService chatsListadoQueryService;

    public ChatsListadoResource(ChatsListadoService chatsListadoService, ChatsListadoQueryService chatsListadoQueryService) {
        this.chatsListadoService = chatsListadoService;
        this.chatsListadoQueryService = chatsListadoQueryService;
    }

    /**
     * {@code POST  /chats-listados} : Create a new chatsListado.
     *
     * @param chatsListado the chatsListado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chatsListado, or with status {@code 400 (Bad Request)} if the chatsListado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chats-listados")
    public ResponseEntity<ChatsListado> createChatsListado(@RequestBody ChatsListado chatsListado) throws URISyntaxException {
        log.debug("REST request to save ChatsListado : {}", chatsListado);
        if (chatsListado.getId() != null) {
            throw new BadRequestAlertException("A new chatsListado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatsListado result = chatsListadoService.save(chatsListado);
        return ResponseEntity.created(new URI("/api/chats-listados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chats-listados} : Updates an existing chatsListado.
     *
     * @param chatsListado the chatsListado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatsListado,
     * or with status {@code 400 (Bad Request)} if the chatsListado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatsListado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chats-listados")
    public ResponseEntity<ChatsListado> updateChatsListado(@RequestBody ChatsListado chatsListado) throws URISyntaxException {
        log.debug("REST request to update ChatsListado : {}", chatsListado);
        if (chatsListado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChatsListado result = chatsListadoService.save(chatsListado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatsListado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chats-listados} : get all the chatsListados.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chatsListados in body.
     */
    @GetMapping("/chats-listados")
    public ResponseEntity<List<ChatsListado>> getAllChatsListados(ChatsListadoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ChatsListados by criteria: {}", criteria);
        Page<ChatsListado> page = chatsListadoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /chats-listados/count} : count all the chatsListados.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/chats-listados/count")
    public ResponseEntity<Long> countChatsListados(ChatsListadoCriteria criteria) {
        log.debug("REST request to count ChatsListados by criteria: {}", criteria);
        return ResponseEntity.ok().body(chatsListadoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chats-listados/:id} : get the "id" chatsListado.
     *
     * @param id the id of the chatsListado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chatsListado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chats-listados/{id}")
    public ResponseEntity<ChatsListado> getChatsListado(@PathVariable Long id) {
        log.debug("REST request to get ChatsListado : {}", id);
        Optional<ChatsListado> chatsListado = chatsListadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chatsListado);
    }

    /**
     * {@code DELETE  /chats-listados/:id} : delete the "id" chatsListado.
     *
     * @param id the id of the chatsListado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chats-listados/{id}")
    public ResponseEntity<Void> deleteChatsListado(@PathVariable Long id) {
        log.debug("REST request to delete ChatsListado : {}", id);
        chatsListadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/chats-listados?query=:query} : search for the chatsListado corresponding
     * to the query.
     *
     * @param query the query of the chatsListado search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/chats-listados")
    public ResponseEntity<List<ChatsListado>> searchChatsListados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChatsListados for query {}", query);
        Page<ChatsListado> page = chatsListadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
