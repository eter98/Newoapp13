package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.TipoEspacio;
import io.github.jhipster.newoapp13.service.TipoEspacioService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.TipoEspacioCriteria;
import io.github.jhipster.newoapp13.service.TipoEspacioQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.TipoEspacio}.
 */
@RestController
@RequestMapping("/api")
public class TipoEspacioResource {

    private final Logger log = LoggerFactory.getLogger(TipoEspacioResource.class);

    private static final String ENTITY_NAME = "tipoEspacio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEspacioService tipoEspacioService;

    private final TipoEspacioQueryService tipoEspacioQueryService;

    public TipoEspacioResource(TipoEspacioService tipoEspacioService, TipoEspacioQueryService tipoEspacioQueryService) {
        this.tipoEspacioService = tipoEspacioService;
        this.tipoEspacioQueryService = tipoEspacioQueryService;
    }

    /**
     * {@code POST  /tipo-espacios} : Create a new tipoEspacio.
     *
     * @param tipoEspacio the tipoEspacio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEspacio, or with status {@code 400 (Bad Request)} if the tipoEspacio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-espacios")
    public ResponseEntity<TipoEspacio> createTipoEspacio(@Valid @RequestBody TipoEspacio tipoEspacio) throws URISyntaxException {
        log.debug("REST request to save TipoEspacio : {}", tipoEspacio);
        if (tipoEspacio.getId() != null) {
            throw new BadRequestAlertException("A new tipoEspacio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEspacio result = tipoEspacioService.save(tipoEspacio);
        return ResponseEntity.created(new URI("/api/tipo-espacios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-espacios} : Updates an existing tipoEspacio.
     *
     * @param tipoEspacio the tipoEspacio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEspacio,
     * or with status {@code 400 (Bad Request)} if the tipoEspacio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEspacio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-espacios")
    public ResponseEntity<TipoEspacio> updateTipoEspacio(@Valid @RequestBody TipoEspacio tipoEspacio) throws URISyntaxException {
        log.debug("REST request to update TipoEspacio : {}", tipoEspacio);
        if (tipoEspacio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEspacio result = tipoEspacioService.save(tipoEspacio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoEspacio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-espacios} : get all the tipoEspacios.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEspacios in body.
     */
    @GetMapping("/tipo-espacios")
    public ResponseEntity<List<TipoEspacio>> getAllTipoEspacios(TipoEspacioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoEspacios by criteria: {}", criteria);
        Page<TipoEspacio> page = tipoEspacioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-espacios/count} : count all the tipoEspacios.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-espacios/count")
    public ResponseEntity<Long> countTipoEspacios(TipoEspacioCriteria criteria) {
        log.debug("REST request to count TipoEspacios by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoEspacioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-espacios/:id} : get the "id" tipoEspacio.
     *
     * @param id the id of the tipoEspacio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEspacio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-espacios/{id}")
    public ResponseEntity<TipoEspacio> getTipoEspacio(@PathVariable Long id) {
        log.debug("REST request to get TipoEspacio : {}", id);
        Optional<TipoEspacio> tipoEspacio = tipoEspacioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoEspacio);
    }

    /**
     * {@code DELETE  /tipo-espacios/:id} : delete the "id" tipoEspacio.
     *
     * @param id the id of the tipoEspacio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-espacios/{id}")
    public ResponseEntity<Void> deleteTipoEspacio(@PathVariable Long id) {
        log.debug("REST request to delete TipoEspacio : {}", id);
        tipoEspacioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-espacios?query=:query} : search for the tipoEspacio corresponding
     * to the query.
     *
     * @param query the query of the tipoEspacio search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-espacios")
    public ResponseEntity<List<TipoEspacio>> searchTipoEspacios(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoEspacios for query {}", query);
        Page<TipoEspacio> page = tipoEspacioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
