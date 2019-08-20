package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.MargenNewoEventos;
import io.github.jhipster.newoapp13.service.MargenNewoEventosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.MargenNewoEventosCriteria;
import io.github.jhipster.newoapp13.service.MargenNewoEventosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.MargenNewoEventos}.
 */
@RestController
@RequestMapping("/api")
public class MargenNewoEventosResource {

    private final Logger log = LoggerFactory.getLogger(MargenNewoEventosResource.class);

    private static final String ENTITY_NAME = "margenNewoEventos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MargenNewoEventosService margenNewoEventosService;

    private final MargenNewoEventosQueryService margenNewoEventosQueryService;

    public MargenNewoEventosResource(MargenNewoEventosService margenNewoEventosService, MargenNewoEventosQueryService margenNewoEventosQueryService) {
        this.margenNewoEventosService = margenNewoEventosService;
        this.margenNewoEventosQueryService = margenNewoEventosQueryService;
    }

    /**
     * {@code POST  /margen-newo-eventos} : Create a new margenNewoEventos.
     *
     * @param margenNewoEventos the margenNewoEventos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new margenNewoEventos, or with status {@code 400 (Bad Request)} if the margenNewoEventos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/margen-newo-eventos")
    public ResponseEntity<MargenNewoEventos> createMargenNewoEventos(@RequestBody MargenNewoEventos margenNewoEventos) throws URISyntaxException {
        log.debug("REST request to save MargenNewoEventos : {}", margenNewoEventos);
        if (margenNewoEventos.getId() != null) {
            throw new BadRequestAlertException("A new margenNewoEventos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MargenNewoEventos result = margenNewoEventosService.save(margenNewoEventos);
        return ResponseEntity.created(new URI("/api/margen-newo-eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /margen-newo-eventos} : Updates an existing margenNewoEventos.
     *
     * @param margenNewoEventos the margenNewoEventos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated margenNewoEventos,
     * or with status {@code 400 (Bad Request)} if the margenNewoEventos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the margenNewoEventos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/margen-newo-eventos")
    public ResponseEntity<MargenNewoEventos> updateMargenNewoEventos(@RequestBody MargenNewoEventos margenNewoEventos) throws URISyntaxException {
        log.debug("REST request to update MargenNewoEventos : {}", margenNewoEventos);
        if (margenNewoEventos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MargenNewoEventos result = margenNewoEventosService.save(margenNewoEventos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, margenNewoEventos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /margen-newo-eventos} : get all the margenNewoEventos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of margenNewoEventos in body.
     */
    @GetMapping("/margen-newo-eventos")
    public ResponseEntity<List<MargenNewoEventos>> getAllMargenNewoEventos(MargenNewoEventosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MargenNewoEventos by criteria: {}", criteria);
        Page<MargenNewoEventos> page = margenNewoEventosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /margen-newo-eventos/count} : count all the margenNewoEventos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/margen-newo-eventos/count")
    public ResponseEntity<Long> countMargenNewoEventos(MargenNewoEventosCriteria criteria) {
        log.debug("REST request to count MargenNewoEventos by criteria: {}", criteria);
        return ResponseEntity.ok().body(margenNewoEventosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /margen-newo-eventos/:id} : get the "id" margenNewoEventos.
     *
     * @param id the id of the margenNewoEventos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the margenNewoEventos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/margen-newo-eventos/{id}")
    public ResponseEntity<MargenNewoEventos> getMargenNewoEventos(@PathVariable Long id) {
        log.debug("REST request to get MargenNewoEventos : {}", id);
        Optional<MargenNewoEventos> margenNewoEventos = margenNewoEventosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(margenNewoEventos);
    }

    /**
     * {@code DELETE  /margen-newo-eventos/:id} : delete the "id" margenNewoEventos.
     *
     * @param id the id of the margenNewoEventos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/margen-newo-eventos/{id}")
    public ResponseEntity<Void> deleteMargenNewoEventos(@PathVariable Long id) {
        log.debug("REST request to delete MargenNewoEventos : {}", id);
        margenNewoEventosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/margen-newo-eventos?query=:query} : search for the margenNewoEventos corresponding
     * to the query.
     *
     * @param query the query of the margenNewoEventos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/margen-newo-eventos")
    public ResponseEntity<List<MargenNewoEventos>> searchMargenNewoEventos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MargenNewoEventos for query {}", query);
        Page<MargenNewoEventos> page = margenNewoEventosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
