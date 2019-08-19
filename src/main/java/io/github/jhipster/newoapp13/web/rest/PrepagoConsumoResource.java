package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.PrepagoConsumo;
import io.github.jhipster.newoapp13.service.PrepagoConsumoService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.PrepagoConsumoCriteria;
import io.github.jhipster.newoapp13.service.PrepagoConsumoQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.PrepagoConsumo}.
 */
@RestController
@RequestMapping("/api")
public class PrepagoConsumoResource {

    private final Logger log = LoggerFactory.getLogger(PrepagoConsumoResource.class);

    private static final String ENTITY_NAME = "prepagoConsumo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrepagoConsumoService prepagoConsumoService;

    private final PrepagoConsumoQueryService prepagoConsumoQueryService;

    public PrepagoConsumoResource(PrepagoConsumoService prepagoConsumoService, PrepagoConsumoQueryService prepagoConsumoQueryService) {
        this.prepagoConsumoService = prepagoConsumoService;
        this.prepagoConsumoQueryService = prepagoConsumoQueryService;
    }

    /**
     * {@code POST  /prepago-consumos} : Create a new prepagoConsumo.
     *
     * @param prepagoConsumo the prepagoConsumo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prepagoConsumo, or with status {@code 400 (Bad Request)} if the prepagoConsumo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prepago-consumos")
    public ResponseEntity<PrepagoConsumo> createPrepagoConsumo(@RequestBody PrepagoConsumo prepagoConsumo) throws URISyntaxException {
        log.debug("REST request to save PrepagoConsumo : {}", prepagoConsumo);
        if (prepagoConsumo.getId() != null) {
            throw new BadRequestAlertException("A new prepagoConsumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrepagoConsumo result = prepagoConsumoService.save(prepagoConsumo);
        return ResponseEntity.created(new URI("/api/prepago-consumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prepago-consumos} : Updates an existing prepagoConsumo.
     *
     * @param prepagoConsumo the prepagoConsumo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prepagoConsumo,
     * or with status {@code 400 (Bad Request)} if the prepagoConsumo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prepagoConsumo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prepago-consumos")
    public ResponseEntity<PrepagoConsumo> updatePrepagoConsumo(@RequestBody PrepagoConsumo prepagoConsumo) throws URISyntaxException {
        log.debug("REST request to update PrepagoConsumo : {}", prepagoConsumo);
        if (prepagoConsumo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrepagoConsumo result = prepagoConsumoService.save(prepagoConsumo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prepagoConsumo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prepago-consumos} : get all the prepagoConsumos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prepagoConsumos in body.
     */
    @GetMapping("/prepago-consumos")
    public ResponseEntity<List<PrepagoConsumo>> getAllPrepagoConsumos(PrepagoConsumoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PrepagoConsumos by criteria: {}", criteria);
        Page<PrepagoConsumo> page = prepagoConsumoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /prepago-consumos/count} : count all the prepagoConsumos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/prepago-consumos/count")
    public ResponseEntity<Long> countPrepagoConsumos(PrepagoConsumoCriteria criteria) {
        log.debug("REST request to count PrepagoConsumos by criteria: {}", criteria);
        return ResponseEntity.ok().body(prepagoConsumoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prepago-consumos/:id} : get the "id" prepagoConsumo.
     *
     * @param id the id of the prepagoConsumo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prepagoConsumo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prepago-consumos/{id}")
    public ResponseEntity<PrepagoConsumo> getPrepagoConsumo(@PathVariable Long id) {
        log.debug("REST request to get PrepagoConsumo : {}", id);
        Optional<PrepagoConsumo> prepagoConsumo = prepagoConsumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prepagoConsumo);
    }

    /**
     * {@code DELETE  /prepago-consumos/:id} : delete the "id" prepagoConsumo.
     *
     * @param id the id of the prepagoConsumo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prepago-consumos/{id}")
    public ResponseEntity<Void> deletePrepagoConsumo(@PathVariable Long id) {
        log.debug("REST request to delete PrepagoConsumo : {}", id);
        prepagoConsumoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prepago-consumos?query=:query} : search for the prepagoConsumo corresponding
     * to the query.
     *
     * @param query the query of the prepagoConsumo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prepago-consumos")
    public ResponseEntity<List<PrepagoConsumo>> searchPrepagoConsumos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrepagoConsumos for query {}", query);
        Page<PrepagoConsumo> page = prepagoConsumoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
