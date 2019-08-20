package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.EntradaMiembros;
import io.github.jhipster.newoapp13.service.EntradaMiembrosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.EntradaMiembrosCriteria;
import io.github.jhipster.newoapp13.service.EntradaMiembrosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.EntradaMiembros}.
 */
@RestController
@RequestMapping("/api")
public class EntradaMiembrosResource {

    private final Logger log = LoggerFactory.getLogger(EntradaMiembrosResource.class);

    private static final String ENTITY_NAME = "entradaMiembros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntradaMiembrosService entradaMiembrosService;

    private final EntradaMiembrosQueryService entradaMiembrosQueryService;

    public EntradaMiembrosResource(EntradaMiembrosService entradaMiembrosService, EntradaMiembrosQueryService entradaMiembrosQueryService) {
        this.entradaMiembrosService = entradaMiembrosService;
        this.entradaMiembrosQueryService = entradaMiembrosQueryService;
    }

    /**
     * {@code POST  /entrada-miembros} : Create a new entradaMiembros.
     *
     * @param entradaMiembros the entradaMiembros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entradaMiembros, or with status {@code 400 (Bad Request)} if the entradaMiembros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrada-miembros")
    public ResponseEntity<EntradaMiembros> createEntradaMiembros(@RequestBody EntradaMiembros entradaMiembros) throws URISyntaxException {
        log.debug("REST request to save EntradaMiembros : {}", entradaMiembros);
        if (entradaMiembros.getId() != null) {
            throw new BadRequestAlertException("A new entradaMiembros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntradaMiembros result = entradaMiembrosService.save(entradaMiembros);
        return ResponseEntity.created(new URI("/api/entrada-miembros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrada-miembros} : Updates an existing entradaMiembros.
     *
     * @param entradaMiembros the entradaMiembros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entradaMiembros,
     * or with status {@code 400 (Bad Request)} if the entradaMiembros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entradaMiembros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entrada-miembros")
    public ResponseEntity<EntradaMiembros> updateEntradaMiembros(@RequestBody EntradaMiembros entradaMiembros) throws URISyntaxException {
        log.debug("REST request to update EntradaMiembros : {}", entradaMiembros);
        if (entradaMiembros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntradaMiembros result = entradaMiembrosService.save(entradaMiembros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entradaMiembros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entrada-miembros} : get all the entradaMiembros.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entradaMiembros in body.
     */
    @GetMapping("/entrada-miembros")
    public ResponseEntity<List<EntradaMiembros>> getAllEntradaMiembros(EntradaMiembrosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EntradaMiembros by criteria: {}", criteria);
        Page<EntradaMiembros> page = entradaMiembrosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /entrada-miembros/count} : count all the entradaMiembros.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/entrada-miembros/count")
    public ResponseEntity<Long> countEntradaMiembros(EntradaMiembrosCriteria criteria) {
        log.debug("REST request to count EntradaMiembros by criteria: {}", criteria);
        return ResponseEntity.ok().body(entradaMiembrosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /entrada-miembros/:id} : get the "id" entradaMiembros.
     *
     * @param id the id of the entradaMiembros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entradaMiembros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entrada-miembros/{id}")
    public ResponseEntity<EntradaMiembros> getEntradaMiembros(@PathVariable Long id) {
        log.debug("REST request to get EntradaMiembros : {}", id);
        Optional<EntradaMiembros> entradaMiembros = entradaMiembrosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entradaMiembros);
    }

    /**
     * {@code DELETE  /entrada-miembros/:id} : delete the "id" entradaMiembros.
     *
     * @param id the id of the entradaMiembros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entrada-miembros/{id}")
    public ResponseEntity<Void> deleteEntradaMiembros(@PathVariable Long id) {
        log.debug("REST request to delete EntradaMiembros : {}", id);
        entradaMiembrosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/entrada-miembros?query=:query} : search for the entradaMiembros corresponding
     * to the query.
     *
     * @param query the query of the entradaMiembros search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/entrada-miembros")
    public ResponseEntity<List<EntradaMiembros>> searchEntradaMiembros(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EntradaMiembros for query {}", query);
        Page<EntradaMiembros> page = entradaMiembrosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
