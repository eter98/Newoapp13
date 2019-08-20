package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.RecursosFisicos;
import io.github.jhipster.newoapp13.service.RecursosFisicosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.RecursosFisicosCriteria;
import io.github.jhipster.newoapp13.service.RecursosFisicosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.RecursosFisicos}.
 */
@RestController
@RequestMapping("/api")
public class RecursosFisicosResource {

    private final Logger log = LoggerFactory.getLogger(RecursosFisicosResource.class);

    private static final String ENTITY_NAME = "recursosFisicos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursosFisicosService recursosFisicosService;

    private final RecursosFisicosQueryService recursosFisicosQueryService;

    public RecursosFisicosResource(RecursosFisicosService recursosFisicosService, RecursosFisicosQueryService recursosFisicosQueryService) {
        this.recursosFisicosService = recursosFisicosService;
        this.recursosFisicosQueryService = recursosFisicosQueryService;
    }

    /**
     * {@code POST  /recursos-fisicos} : Create a new recursosFisicos.
     *
     * @param recursosFisicos the recursosFisicos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursosFisicos, or with status {@code 400 (Bad Request)} if the recursosFisicos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recursos-fisicos")
    public ResponseEntity<RecursosFisicos> createRecursosFisicos(@Valid @RequestBody RecursosFisicos recursosFisicos) throws URISyntaxException {
        log.debug("REST request to save RecursosFisicos : {}", recursosFisicos);
        if (recursosFisicos.getId() != null) {
            throw new BadRequestAlertException("A new recursosFisicos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecursosFisicos result = recursosFisicosService.save(recursosFisicos);
        return ResponseEntity.created(new URI("/api/recursos-fisicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recursos-fisicos} : Updates an existing recursosFisicos.
     *
     * @param recursosFisicos the recursosFisicos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursosFisicos,
     * or with status {@code 400 (Bad Request)} if the recursosFisicos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursosFisicos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recursos-fisicos")
    public ResponseEntity<RecursosFisicos> updateRecursosFisicos(@Valid @RequestBody RecursosFisicos recursosFisicos) throws URISyntaxException {
        log.debug("REST request to update RecursosFisicos : {}", recursosFisicos);
        if (recursosFisicos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecursosFisicos result = recursosFisicosService.save(recursosFisicos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursosFisicos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recursos-fisicos} : get all the recursosFisicos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursosFisicos in body.
     */
    @GetMapping("/recursos-fisicos")
    public ResponseEntity<List<RecursosFisicos>> getAllRecursosFisicos(RecursosFisicosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RecursosFisicos by criteria: {}", criteria);
        Page<RecursosFisicos> page = recursosFisicosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /recursos-fisicos/count} : count all the recursosFisicos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/recursos-fisicos/count")
    public ResponseEntity<Long> countRecursosFisicos(RecursosFisicosCriteria criteria) {
        log.debug("REST request to count RecursosFisicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(recursosFisicosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recursos-fisicos/:id} : get the "id" recursosFisicos.
     *
     * @param id the id of the recursosFisicos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursosFisicos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recursos-fisicos/{id}")
    public ResponseEntity<RecursosFisicos> getRecursosFisicos(@PathVariable Long id) {
        log.debug("REST request to get RecursosFisicos : {}", id);
        Optional<RecursosFisicos> recursosFisicos = recursosFisicosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recursosFisicos);
    }

    /**
     * {@code DELETE  /recursos-fisicos/:id} : delete the "id" recursosFisicos.
     *
     * @param id the id of the recursosFisicos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recursos-fisicos/{id}")
    public ResponseEntity<Void> deleteRecursosFisicos(@PathVariable Long id) {
        log.debug("REST request to delete RecursosFisicos : {}", id);
        recursosFisicosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/recursos-fisicos?query=:query} : search for the recursosFisicos corresponding
     * to the query.
     *
     * @param query the query of the recursosFisicos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/recursos-fisicos")
    public ResponseEntity<List<RecursosFisicos>> searchRecursosFisicos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RecursosFisicos for query {}", query);
        Page<RecursosFisicos> page = recursosFisicosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
