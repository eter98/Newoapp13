package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.MargenNewoProductos;
import io.github.jhipster.newoapp13.service.MargenNewoProductosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.MargenNewoProductosCriteria;
import io.github.jhipster.newoapp13.service.MargenNewoProductosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.MargenNewoProductos}.
 */
@RestController
@RequestMapping("/api")
public class MargenNewoProductosResource {

    private final Logger log = LoggerFactory.getLogger(MargenNewoProductosResource.class);

    private static final String ENTITY_NAME = "margenNewoProductos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MargenNewoProductosService margenNewoProductosService;

    private final MargenNewoProductosQueryService margenNewoProductosQueryService;

    public MargenNewoProductosResource(MargenNewoProductosService margenNewoProductosService, MargenNewoProductosQueryService margenNewoProductosQueryService) {
        this.margenNewoProductosService = margenNewoProductosService;
        this.margenNewoProductosQueryService = margenNewoProductosQueryService;
    }

    /**
     * {@code POST  /margen-newo-productos} : Create a new margenNewoProductos.
     *
     * @param margenNewoProductos the margenNewoProductos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new margenNewoProductos, or with status {@code 400 (Bad Request)} if the margenNewoProductos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/margen-newo-productos")
    public ResponseEntity<MargenNewoProductos> createMargenNewoProductos(@RequestBody MargenNewoProductos margenNewoProductos) throws URISyntaxException {
        log.debug("REST request to save MargenNewoProductos : {}", margenNewoProductos);
        if (margenNewoProductos.getId() != null) {
            throw new BadRequestAlertException("A new margenNewoProductos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MargenNewoProductos result = margenNewoProductosService.save(margenNewoProductos);
        return ResponseEntity.created(new URI("/api/margen-newo-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /margen-newo-productos} : Updates an existing margenNewoProductos.
     *
     * @param margenNewoProductos the margenNewoProductos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated margenNewoProductos,
     * or with status {@code 400 (Bad Request)} if the margenNewoProductos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the margenNewoProductos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/margen-newo-productos")
    public ResponseEntity<MargenNewoProductos> updateMargenNewoProductos(@RequestBody MargenNewoProductos margenNewoProductos) throws URISyntaxException {
        log.debug("REST request to update MargenNewoProductos : {}", margenNewoProductos);
        if (margenNewoProductos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MargenNewoProductos result = margenNewoProductosService.save(margenNewoProductos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, margenNewoProductos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /margen-newo-productos} : get all the margenNewoProductos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of margenNewoProductos in body.
     */
    @GetMapping("/margen-newo-productos")
    public ResponseEntity<List<MargenNewoProductos>> getAllMargenNewoProductos(MargenNewoProductosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MargenNewoProductos by criteria: {}", criteria);
        Page<MargenNewoProductos> page = margenNewoProductosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /margen-newo-productos/count} : count all the margenNewoProductos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/margen-newo-productos/count")
    public ResponseEntity<Long> countMargenNewoProductos(MargenNewoProductosCriteria criteria) {
        log.debug("REST request to count MargenNewoProductos by criteria: {}", criteria);
        return ResponseEntity.ok().body(margenNewoProductosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /margen-newo-productos/:id} : get the "id" margenNewoProductos.
     *
     * @param id the id of the margenNewoProductos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the margenNewoProductos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/margen-newo-productos/{id}")
    public ResponseEntity<MargenNewoProductos> getMargenNewoProductos(@PathVariable Long id) {
        log.debug("REST request to get MargenNewoProductos : {}", id);
        Optional<MargenNewoProductos> margenNewoProductos = margenNewoProductosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(margenNewoProductos);
    }

    /**
     * {@code DELETE  /margen-newo-productos/:id} : delete the "id" margenNewoProductos.
     *
     * @param id the id of the margenNewoProductos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/margen-newo-productos/{id}")
    public ResponseEntity<Void> deleteMargenNewoProductos(@PathVariable Long id) {
        log.debug("REST request to delete MargenNewoProductos : {}", id);
        margenNewoProductosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/margen-newo-productos?query=:query} : search for the margenNewoProductos corresponding
     * to the query.
     *
     * @param query the query of the margenNewoProductos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/margen-newo-productos")
    public ResponseEntity<List<MargenNewoProductos>> searchMargenNewoProductos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MargenNewoProductos for query {}", query);
        Page<MargenNewoProductos> page = margenNewoProductosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
