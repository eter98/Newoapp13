package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.UsoRecursoFisico;
import io.github.jhipster.newoapp13.service.UsoRecursoFisicoService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.UsoRecursoFisicoCriteria;
import io.github.jhipster.newoapp13.service.UsoRecursoFisicoQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.UsoRecursoFisico}.
 */
@RestController
@RequestMapping("/api")
public class UsoRecursoFisicoResource {

    private final Logger log = LoggerFactory.getLogger(UsoRecursoFisicoResource.class);

    private static final String ENTITY_NAME = "usoRecursoFisico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsoRecursoFisicoService usoRecursoFisicoService;

    private final UsoRecursoFisicoQueryService usoRecursoFisicoQueryService;

    public UsoRecursoFisicoResource(UsoRecursoFisicoService usoRecursoFisicoService, UsoRecursoFisicoQueryService usoRecursoFisicoQueryService) {
        this.usoRecursoFisicoService = usoRecursoFisicoService;
        this.usoRecursoFisicoQueryService = usoRecursoFisicoQueryService;
    }

    /**
     * {@code POST  /uso-recurso-fisicos} : Create a new usoRecursoFisico.
     *
     * @param usoRecursoFisico the usoRecursoFisico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usoRecursoFisico, or with status {@code 400 (Bad Request)} if the usoRecursoFisico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uso-recurso-fisicos")
    public ResponseEntity<UsoRecursoFisico> createUsoRecursoFisico(@RequestBody UsoRecursoFisico usoRecursoFisico) throws URISyntaxException {
        log.debug("REST request to save UsoRecursoFisico : {}", usoRecursoFisico);
        if (usoRecursoFisico.getId() != null) {
            throw new BadRequestAlertException("A new usoRecursoFisico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsoRecursoFisico result = usoRecursoFisicoService.save(usoRecursoFisico);
        return ResponseEntity.created(new URI("/api/uso-recurso-fisicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uso-recurso-fisicos} : Updates an existing usoRecursoFisico.
     *
     * @param usoRecursoFisico the usoRecursoFisico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usoRecursoFisico,
     * or with status {@code 400 (Bad Request)} if the usoRecursoFisico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usoRecursoFisico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uso-recurso-fisicos")
    public ResponseEntity<UsoRecursoFisico> updateUsoRecursoFisico(@RequestBody UsoRecursoFisico usoRecursoFisico) throws URISyntaxException {
        log.debug("REST request to update UsoRecursoFisico : {}", usoRecursoFisico);
        if (usoRecursoFisico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsoRecursoFisico result = usoRecursoFisicoService.save(usoRecursoFisico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usoRecursoFisico.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uso-recurso-fisicos} : get all the usoRecursoFisicos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usoRecursoFisicos in body.
     */
    @GetMapping("/uso-recurso-fisicos")
    public ResponseEntity<List<UsoRecursoFisico>> getAllUsoRecursoFisicos(UsoRecursoFisicoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UsoRecursoFisicos by criteria: {}", criteria);
        Page<UsoRecursoFisico> page = usoRecursoFisicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /uso-recurso-fisicos/count} : count all the usoRecursoFisicos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/uso-recurso-fisicos/count")
    public ResponseEntity<Long> countUsoRecursoFisicos(UsoRecursoFisicoCriteria criteria) {
        log.debug("REST request to count UsoRecursoFisicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(usoRecursoFisicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /uso-recurso-fisicos/:id} : get the "id" usoRecursoFisico.
     *
     * @param id the id of the usoRecursoFisico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usoRecursoFisico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uso-recurso-fisicos/{id}")
    public ResponseEntity<UsoRecursoFisico> getUsoRecursoFisico(@PathVariable Long id) {
        log.debug("REST request to get UsoRecursoFisico : {}", id);
        Optional<UsoRecursoFisico> usoRecursoFisico = usoRecursoFisicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usoRecursoFisico);
    }

    /**
     * {@code DELETE  /uso-recurso-fisicos/:id} : delete the "id" usoRecursoFisico.
     *
     * @param id the id of the usoRecursoFisico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uso-recurso-fisicos/{id}")
    public ResponseEntity<Void> deleteUsoRecursoFisico(@PathVariable Long id) {
        log.debug("REST request to delete UsoRecursoFisico : {}", id);
        usoRecursoFisicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/uso-recurso-fisicos?query=:query} : search for the usoRecursoFisico corresponding
     * to the query.
     *
     * @param query the query of the usoRecursoFisico search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/uso-recurso-fisicos")
    public ResponseEntity<List<UsoRecursoFisico>> searchUsoRecursoFisicos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UsoRecursoFisicos for query {}", query);
        Page<UsoRecursoFisico> page = usoRecursoFisicoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
