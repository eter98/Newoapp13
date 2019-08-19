package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo;
import io.github.jhipster.newoapp13.service.TipoPrepagoConsumoService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.TipoPrepagoConsumoCriteria;
import io.github.jhipster.newoapp13.service.TipoPrepagoConsumoQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo}.
 */
@RestController
@RequestMapping("/api")
public class TipoPrepagoConsumoResource {

    private final Logger log = LoggerFactory.getLogger(TipoPrepagoConsumoResource.class);

    private static final String ENTITY_NAME = "tipoPrepagoConsumo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPrepagoConsumoService tipoPrepagoConsumoService;

    private final TipoPrepagoConsumoQueryService tipoPrepagoConsumoQueryService;

    public TipoPrepagoConsumoResource(TipoPrepagoConsumoService tipoPrepagoConsumoService, TipoPrepagoConsumoQueryService tipoPrepagoConsumoQueryService) {
        this.tipoPrepagoConsumoService = tipoPrepagoConsumoService;
        this.tipoPrepagoConsumoQueryService = tipoPrepagoConsumoQueryService;
    }

    /**
     * {@code POST  /tipo-prepago-consumos} : Create a new tipoPrepagoConsumo.
     *
     * @param tipoPrepagoConsumo the tipoPrepagoConsumo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPrepagoConsumo, or with status {@code 400 (Bad Request)} if the tipoPrepagoConsumo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-prepago-consumos")
    public ResponseEntity<TipoPrepagoConsumo> createTipoPrepagoConsumo(@Valid @RequestBody TipoPrepagoConsumo tipoPrepagoConsumo) throws URISyntaxException {
        log.debug("REST request to save TipoPrepagoConsumo : {}", tipoPrepagoConsumo);
        if (tipoPrepagoConsumo.getId() != null) {
            throw new BadRequestAlertException("A new tipoPrepagoConsumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPrepagoConsumo result = tipoPrepagoConsumoService.save(tipoPrepagoConsumo);
        return ResponseEntity.created(new URI("/api/tipo-prepago-consumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-prepago-consumos} : Updates an existing tipoPrepagoConsumo.
     *
     * @param tipoPrepagoConsumo the tipoPrepagoConsumo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPrepagoConsumo,
     * or with status {@code 400 (Bad Request)} if the tipoPrepagoConsumo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPrepagoConsumo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-prepago-consumos")
    public ResponseEntity<TipoPrepagoConsumo> updateTipoPrepagoConsumo(@Valid @RequestBody TipoPrepagoConsumo tipoPrepagoConsumo) throws URISyntaxException {
        log.debug("REST request to update TipoPrepagoConsumo : {}", tipoPrepagoConsumo);
        if (tipoPrepagoConsumo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoPrepagoConsumo result = tipoPrepagoConsumoService.save(tipoPrepagoConsumo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPrepagoConsumo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-prepago-consumos} : get all the tipoPrepagoConsumos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPrepagoConsumos in body.
     */
    @GetMapping("/tipo-prepago-consumos")
    public ResponseEntity<List<TipoPrepagoConsumo>> getAllTipoPrepagoConsumos(TipoPrepagoConsumoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoPrepagoConsumos by criteria: {}", criteria);
        Page<TipoPrepagoConsumo> page = tipoPrepagoConsumoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-prepago-consumos/count} : count all the tipoPrepagoConsumos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-prepago-consumos/count")
    public ResponseEntity<Long> countTipoPrepagoConsumos(TipoPrepagoConsumoCriteria criteria) {
        log.debug("REST request to count TipoPrepagoConsumos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoPrepagoConsumoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-prepago-consumos/:id} : get the "id" tipoPrepagoConsumo.
     *
     * @param id the id of the tipoPrepagoConsumo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPrepagoConsumo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-prepago-consumos/{id}")
    public ResponseEntity<TipoPrepagoConsumo> getTipoPrepagoConsumo(@PathVariable Long id) {
        log.debug("REST request to get TipoPrepagoConsumo : {}", id);
        Optional<TipoPrepagoConsumo> tipoPrepagoConsumo = tipoPrepagoConsumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPrepagoConsumo);
    }

    /**
     * {@code DELETE  /tipo-prepago-consumos/:id} : delete the "id" tipoPrepagoConsumo.
     *
     * @param id the id of the tipoPrepagoConsumo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-prepago-consumos/{id}")
    public ResponseEntity<Void> deleteTipoPrepagoConsumo(@PathVariable Long id) {
        log.debug("REST request to delete TipoPrepagoConsumo : {}", id);
        tipoPrepagoConsumoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-prepago-consumos?query=:query} : search for the tipoPrepagoConsumo corresponding
     * to the query.
     *
     * @param query the query of the tipoPrepagoConsumo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-prepago-consumos")
    public ResponseEntity<List<TipoPrepagoConsumo>> searchTipoPrepagoConsumos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoPrepagoConsumos for query {}", query);
        Page<TipoPrepagoConsumo> page = tipoPrepagoConsumoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
