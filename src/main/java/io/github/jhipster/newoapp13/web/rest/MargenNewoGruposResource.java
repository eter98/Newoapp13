package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.MargenNewoGrupos;
import io.github.jhipster.newoapp13.service.MargenNewoGruposService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.MargenNewoGruposCriteria;
import io.github.jhipster.newoapp13.service.MargenNewoGruposQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.MargenNewoGrupos}.
 */
@RestController
@RequestMapping("/api")
public class MargenNewoGruposResource {

    private final Logger log = LoggerFactory.getLogger(MargenNewoGruposResource.class);

    private static final String ENTITY_NAME = "margenNewoGrupos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MargenNewoGruposService margenNewoGruposService;

    private final MargenNewoGruposQueryService margenNewoGruposQueryService;

    public MargenNewoGruposResource(MargenNewoGruposService margenNewoGruposService, MargenNewoGruposQueryService margenNewoGruposQueryService) {
        this.margenNewoGruposService = margenNewoGruposService;
        this.margenNewoGruposQueryService = margenNewoGruposQueryService;
    }

    /**
     * {@code POST  /margen-newo-grupos} : Create a new margenNewoGrupos.
     *
     * @param margenNewoGrupos the margenNewoGrupos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new margenNewoGrupos, or with status {@code 400 (Bad Request)} if the margenNewoGrupos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/margen-newo-grupos")
    public ResponseEntity<MargenNewoGrupos> createMargenNewoGrupos(@RequestBody MargenNewoGrupos margenNewoGrupos) throws URISyntaxException {
        log.debug("REST request to save MargenNewoGrupos : {}", margenNewoGrupos);
        if (margenNewoGrupos.getId() != null) {
            throw new BadRequestAlertException("A new margenNewoGrupos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MargenNewoGrupos result = margenNewoGruposService.save(margenNewoGrupos);
        return ResponseEntity.created(new URI("/api/margen-newo-grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /margen-newo-grupos} : Updates an existing margenNewoGrupos.
     *
     * @param margenNewoGrupos the margenNewoGrupos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated margenNewoGrupos,
     * or with status {@code 400 (Bad Request)} if the margenNewoGrupos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the margenNewoGrupos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/margen-newo-grupos")
    public ResponseEntity<MargenNewoGrupos> updateMargenNewoGrupos(@RequestBody MargenNewoGrupos margenNewoGrupos) throws URISyntaxException {
        log.debug("REST request to update MargenNewoGrupos : {}", margenNewoGrupos);
        if (margenNewoGrupos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MargenNewoGrupos result = margenNewoGruposService.save(margenNewoGrupos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, margenNewoGrupos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /margen-newo-grupos} : get all the margenNewoGrupos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of margenNewoGrupos in body.
     */
    @GetMapping("/margen-newo-grupos")
    public ResponseEntity<List<MargenNewoGrupos>> getAllMargenNewoGrupos(MargenNewoGruposCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MargenNewoGrupos by criteria: {}", criteria);
        Page<MargenNewoGrupos> page = margenNewoGruposQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /margen-newo-grupos/count} : count all the margenNewoGrupos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/margen-newo-grupos/count")
    public ResponseEntity<Long> countMargenNewoGrupos(MargenNewoGruposCriteria criteria) {
        log.debug("REST request to count MargenNewoGrupos by criteria: {}", criteria);
        return ResponseEntity.ok().body(margenNewoGruposQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /margen-newo-grupos/:id} : get the "id" margenNewoGrupos.
     *
     * @param id the id of the margenNewoGrupos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the margenNewoGrupos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/margen-newo-grupos/{id}")
    public ResponseEntity<MargenNewoGrupos> getMargenNewoGrupos(@PathVariable Long id) {
        log.debug("REST request to get MargenNewoGrupos : {}", id);
        Optional<MargenNewoGrupos> margenNewoGrupos = margenNewoGruposService.findOne(id);
        return ResponseUtil.wrapOrNotFound(margenNewoGrupos);
    }

    /**
     * {@code DELETE  /margen-newo-grupos/:id} : delete the "id" margenNewoGrupos.
     *
     * @param id the id of the margenNewoGrupos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/margen-newo-grupos/{id}")
    public ResponseEntity<Void> deleteMargenNewoGrupos(@PathVariable Long id) {
        log.debug("REST request to delete MargenNewoGrupos : {}", id);
        margenNewoGruposService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/margen-newo-grupos?query=:query} : search for the margenNewoGrupos corresponding
     * to the query.
     *
     * @param query the query of the margenNewoGrupos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/margen-newo-grupos")
    public ResponseEntity<List<MargenNewoGrupos>> searchMargenNewoGrupos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MargenNewoGrupos for query {}", query);
        Page<MargenNewoGrupos> page = margenNewoGruposService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
