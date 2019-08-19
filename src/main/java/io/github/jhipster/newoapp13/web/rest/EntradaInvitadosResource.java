package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.EntradaInvitados;
import io.github.jhipster.newoapp13.service.EntradaInvitadosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.EntradaInvitadosCriteria;
import io.github.jhipster.newoapp13.service.EntradaInvitadosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.EntradaInvitados}.
 */
@RestController
@RequestMapping("/api")
public class EntradaInvitadosResource {

    private final Logger log = LoggerFactory.getLogger(EntradaInvitadosResource.class);

    private static final String ENTITY_NAME = "entradaInvitados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntradaInvitadosService entradaInvitadosService;

    private final EntradaInvitadosQueryService entradaInvitadosQueryService;

    public EntradaInvitadosResource(EntradaInvitadosService entradaInvitadosService, EntradaInvitadosQueryService entradaInvitadosQueryService) {
        this.entradaInvitadosService = entradaInvitadosService;
        this.entradaInvitadosQueryService = entradaInvitadosQueryService;
    }

    /**
     * {@code POST  /entrada-invitados} : Create a new entradaInvitados.
     *
     * @param entradaInvitados the entradaInvitados to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entradaInvitados, or with status {@code 400 (Bad Request)} if the entradaInvitados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrada-invitados")
    public ResponseEntity<EntradaInvitados> createEntradaInvitados(@Valid @RequestBody EntradaInvitados entradaInvitados) throws URISyntaxException {
        log.debug("REST request to save EntradaInvitados : {}", entradaInvitados);
        if (entradaInvitados.getId() != null) {
            throw new BadRequestAlertException("A new entradaInvitados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntradaInvitados result = entradaInvitadosService.save(entradaInvitados);
        return ResponseEntity.created(new URI("/api/entrada-invitados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrada-invitados} : Updates an existing entradaInvitados.
     *
     * @param entradaInvitados the entradaInvitados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entradaInvitados,
     * or with status {@code 400 (Bad Request)} if the entradaInvitados is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entradaInvitados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entrada-invitados")
    public ResponseEntity<EntradaInvitados> updateEntradaInvitados(@Valid @RequestBody EntradaInvitados entradaInvitados) throws URISyntaxException {
        log.debug("REST request to update EntradaInvitados : {}", entradaInvitados);
        if (entradaInvitados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntradaInvitados result = entradaInvitadosService.save(entradaInvitados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entradaInvitados.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entrada-invitados} : get all the entradaInvitados.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entradaInvitados in body.
     */
    @GetMapping("/entrada-invitados")
    public ResponseEntity<List<EntradaInvitados>> getAllEntradaInvitados(EntradaInvitadosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EntradaInvitados by criteria: {}", criteria);
        Page<EntradaInvitados> page = entradaInvitadosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /entrada-invitados/count} : count all the entradaInvitados.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/entrada-invitados/count")
    public ResponseEntity<Long> countEntradaInvitados(EntradaInvitadosCriteria criteria) {
        log.debug("REST request to count EntradaInvitados by criteria: {}", criteria);
        return ResponseEntity.ok().body(entradaInvitadosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /entrada-invitados/:id} : get the "id" entradaInvitados.
     *
     * @param id the id of the entradaInvitados to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entradaInvitados, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entrada-invitados/{id}")
    public ResponseEntity<EntradaInvitados> getEntradaInvitados(@PathVariable Long id) {
        log.debug("REST request to get EntradaInvitados : {}", id);
        Optional<EntradaInvitados> entradaInvitados = entradaInvitadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entradaInvitados);
    }

    /**
     * {@code DELETE  /entrada-invitados/:id} : delete the "id" entradaInvitados.
     *
     * @param id the id of the entradaInvitados to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entrada-invitados/{id}")
    public ResponseEntity<Void> deleteEntradaInvitados(@PathVariable Long id) {
        log.debug("REST request to delete EntradaInvitados : {}", id);
        entradaInvitadosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/entrada-invitados?query=:query} : search for the entradaInvitados corresponding
     * to the query.
     *
     * @param query the query of the entradaInvitados search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/entrada-invitados")
    public ResponseEntity<List<EntradaInvitados>> searchEntradaInvitados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EntradaInvitados for query {}", query);
        Page<EntradaInvitados> page = entradaInvitadosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
