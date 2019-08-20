package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.Facturacion;
import io.github.jhipster.newoapp13.service.FacturacionService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.FacturacionCriteria;
import io.github.jhipster.newoapp13.service.FacturacionQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.Facturacion}.
 */
@RestController
@RequestMapping("/api")
public class FacturacionResource {

    private final Logger log = LoggerFactory.getLogger(FacturacionResource.class);

    private static final String ENTITY_NAME = "facturacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacturacionService facturacionService;

    private final FacturacionQueryService facturacionQueryService;

    public FacturacionResource(FacturacionService facturacionService, FacturacionQueryService facturacionQueryService) {
        this.facturacionService = facturacionService;
        this.facturacionQueryService = facturacionQueryService;
    }

    /**
     * {@code POST  /facturacions} : Create a new facturacion.
     *
     * @param facturacion the facturacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facturacion, or with status {@code 400 (Bad Request)} if the facturacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facturacions")
    public ResponseEntity<Facturacion> createFacturacion(@RequestBody Facturacion facturacion) throws URISyntaxException {
        log.debug("REST request to save Facturacion : {}", facturacion);
        if (facturacion.getId() != null) {
            throw new BadRequestAlertException("A new facturacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facturacion result = facturacionService.save(facturacion);
        return ResponseEntity.created(new URI("/api/facturacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facturacions} : Updates an existing facturacion.
     *
     * @param facturacion the facturacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facturacion,
     * or with status {@code 400 (Bad Request)} if the facturacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facturacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facturacions")
    public ResponseEntity<Facturacion> updateFacturacion(@RequestBody Facturacion facturacion) throws URISyntaxException {
        log.debug("REST request to update Facturacion : {}", facturacion);
        if (facturacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Facturacion result = facturacionService.save(facturacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facturacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facturacions} : get all the facturacions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facturacions in body.
     */
    @GetMapping("/facturacions")
    public ResponseEntity<List<Facturacion>> getAllFacturacions(FacturacionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Facturacions by criteria: {}", criteria);
        Page<Facturacion> page = facturacionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /facturacions/count} : count all the facturacions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/facturacions/count")
    public ResponseEntity<Long> countFacturacions(FacturacionCriteria criteria) {
        log.debug("REST request to count Facturacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(facturacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /facturacions/:id} : get the "id" facturacion.
     *
     * @param id the id of the facturacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facturacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facturacions/{id}")
    public ResponseEntity<Facturacion> getFacturacion(@PathVariable Long id) {
        log.debug("REST request to get Facturacion : {}", id);
        Optional<Facturacion> facturacion = facturacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facturacion);
    }

    /**
     * {@code DELETE  /facturacions/:id} : delete the "id" facturacion.
     *
     * @param id the id of the facturacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facturacions/{id}")
    public ResponseEntity<Void> deleteFacturacion(@PathVariable Long id) {
        log.debug("REST request to delete Facturacion : {}", id);
        facturacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/facturacions?query=:query} : search for the facturacion corresponding
     * to the query.
     *
     * @param query the query of the facturacion search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/facturacions")
    public ResponseEntity<List<Facturacion>> searchFacturacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Facturacions for query {}", query);
        Page<Facturacion> page = facturacionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
