package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.CuentaAsociada;
import io.github.jhipster.newoapp13.service.CuentaAsociadaService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.CuentaAsociadaCriteria;
import io.github.jhipster.newoapp13.service.CuentaAsociadaQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.CuentaAsociada}.
 */
@RestController
@RequestMapping("/api")
public class CuentaAsociadaResource {

    private final Logger log = LoggerFactory.getLogger(CuentaAsociadaResource.class);

    private static final String ENTITY_NAME = "cuentaAsociada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuentaAsociadaService cuentaAsociadaService;

    private final CuentaAsociadaQueryService cuentaAsociadaQueryService;

    public CuentaAsociadaResource(CuentaAsociadaService cuentaAsociadaService, CuentaAsociadaQueryService cuentaAsociadaQueryService) {
        this.cuentaAsociadaService = cuentaAsociadaService;
        this.cuentaAsociadaQueryService = cuentaAsociadaQueryService;
    }

    /**
     * {@code POST  /cuenta-asociadas} : Create a new cuentaAsociada.
     *
     * @param cuentaAsociada the cuentaAsociada to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuentaAsociada, or with status {@code 400 (Bad Request)} if the cuentaAsociada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cuenta-asociadas")
    public ResponseEntity<CuentaAsociada> createCuentaAsociada(@Valid @RequestBody CuentaAsociada cuentaAsociada) throws URISyntaxException {
        log.debug("REST request to save CuentaAsociada : {}", cuentaAsociada);
        if (cuentaAsociada.getId() != null) {
            throw new BadRequestAlertException("A new cuentaAsociada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CuentaAsociada result = cuentaAsociadaService.save(cuentaAsociada);
        return ResponseEntity.created(new URI("/api/cuenta-asociadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cuenta-asociadas} : Updates an existing cuentaAsociada.
     *
     * @param cuentaAsociada the cuentaAsociada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuentaAsociada,
     * or with status {@code 400 (Bad Request)} if the cuentaAsociada is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuentaAsociada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cuenta-asociadas")
    public ResponseEntity<CuentaAsociada> updateCuentaAsociada(@Valid @RequestBody CuentaAsociada cuentaAsociada) throws URISyntaxException {
        log.debug("REST request to update CuentaAsociada : {}", cuentaAsociada);
        if (cuentaAsociada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CuentaAsociada result = cuentaAsociadaService.save(cuentaAsociada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuentaAsociada.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cuenta-asociadas} : get all the cuentaAsociadas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuentaAsociadas in body.
     */
    @GetMapping("/cuenta-asociadas")
    public ResponseEntity<List<CuentaAsociada>> getAllCuentaAsociadas(CuentaAsociadaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CuentaAsociadas by criteria: {}", criteria);
        Page<CuentaAsociada> page = cuentaAsociadaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /cuenta-asociadas/count} : count all the cuentaAsociadas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cuenta-asociadas/count")
    public ResponseEntity<Long> countCuentaAsociadas(CuentaAsociadaCriteria criteria) {
        log.debug("REST request to count CuentaAsociadas by criteria: {}", criteria);
        return ResponseEntity.ok().body(cuentaAsociadaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cuenta-asociadas/:id} : get the "id" cuentaAsociada.
     *
     * @param id the id of the cuentaAsociada to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuentaAsociada, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cuenta-asociadas/{id}")
    public ResponseEntity<CuentaAsociada> getCuentaAsociada(@PathVariable Long id) {
        log.debug("REST request to get CuentaAsociada : {}", id);
        Optional<CuentaAsociada> cuentaAsociada = cuentaAsociadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuentaAsociada);
    }

    /**
     * {@code DELETE  /cuenta-asociadas/:id} : delete the "id" cuentaAsociada.
     *
     * @param id the id of the cuentaAsociada to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cuenta-asociadas/{id}")
    public ResponseEntity<Void> deleteCuentaAsociada(@PathVariable Long id) {
        log.debug("REST request to delete CuentaAsociada : {}", id);
        cuentaAsociadaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cuenta-asociadas?query=:query} : search for the cuentaAsociada corresponding
     * to the query.
     *
     * @param query the query of the cuentaAsociada search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cuenta-asociadas")
    public ResponseEntity<List<CuentaAsociada>> searchCuentaAsociadas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CuentaAsociadas for query {}", query);
        Page<CuentaAsociada> page = cuentaAsociadaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
