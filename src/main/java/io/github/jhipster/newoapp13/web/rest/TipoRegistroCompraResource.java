package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import io.github.jhipster.newoapp13.service.TipoRegistroCompraService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.TipoRegistroCompraCriteria;
import io.github.jhipster.newoapp13.service.TipoRegistroCompraQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.TipoRegistroCompra}.
 */
@RestController
@RequestMapping("/api")
public class TipoRegistroCompraResource {

    private final Logger log = LoggerFactory.getLogger(TipoRegistroCompraResource.class);

    private static final String ENTITY_NAME = "tipoRegistroCompra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoRegistroCompraService tipoRegistroCompraService;

    private final TipoRegistroCompraQueryService tipoRegistroCompraQueryService;

    public TipoRegistroCompraResource(TipoRegistroCompraService tipoRegistroCompraService, TipoRegistroCompraQueryService tipoRegistroCompraQueryService) {
        this.tipoRegistroCompraService = tipoRegistroCompraService;
        this.tipoRegistroCompraQueryService = tipoRegistroCompraQueryService;
    }

    /**
     * {@code POST  /tipo-registro-compras} : Create a new tipoRegistroCompra.
     *
     * @param tipoRegistroCompra the tipoRegistroCompra to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoRegistroCompra, or with status {@code 400 (Bad Request)} if the tipoRegistroCompra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-registro-compras")
    public ResponseEntity<TipoRegistroCompra> createTipoRegistroCompra(@RequestBody TipoRegistroCompra tipoRegistroCompra) throws URISyntaxException {
        log.debug("REST request to save TipoRegistroCompra : {}", tipoRegistroCompra);
        if (tipoRegistroCompra.getId() != null) {
            throw new BadRequestAlertException("A new tipoRegistroCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoRegistroCompra result = tipoRegistroCompraService.save(tipoRegistroCompra);
        return ResponseEntity.created(new URI("/api/tipo-registro-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-registro-compras} : Updates an existing tipoRegistroCompra.
     *
     * @param tipoRegistroCompra the tipoRegistroCompra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRegistroCompra,
     * or with status {@code 400 (Bad Request)} if the tipoRegistroCompra is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoRegistroCompra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-registro-compras")
    public ResponseEntity<TipoRegistroCompra> updateTipoRegistroCompra(@RequestBody TipoRegistroCompra tipoRegistroCompra) throws URISyntaxException {
        log.debug("REST request to update TipoRegistroCompra : {}", tipoRegistroCompra);
        if (tipoRegistroCompra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoRegistroCompra result = tipoRegistroCompraService.save(tipoRegistroCompra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoRegistroCompra.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-registro-compras} : get all the tipoRegistroCompras.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRegistroCompras in body.
     */
    @GetMapping("/tipo-registro-compras")
    public ResponseEntity<List<TipoRegistroCompra>> getAllTipoRegistroCompras(TipoRegistroCompraCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoRegistroCompras by criteria: {}", criteria);
        Page<TipoRegistroCompra> page = tipoRegistroCompraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tipo-registro-compras/count} : count all the tipoRegistroCompras.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tipo-registro-compras/count")
    public ResponseEntity<Long> countTipoRegistroCompras(TipoRegistroCompraCriteria criteria) {
        log.debug("REST request to count TipoRegistroCompras by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoRegistroCompraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-registro-compras/:id} : get the "id" tipoRegistroCompra.
     *
     * @param id the id of the tipoRegistroCompra to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoRegistroCompra, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-registro-compras/{id}")
    public ResponseEntity<TipoRegistroCompra> getTipoRegistroCompra(@PathVariable Long id) {
        log.debug("REST request to get TipoRegistroCompra : {}", id);
        Optional<TipoRegistroCompra> tipoRegistroCompra = tipoRegistroCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoRegistroCompra);
    }

    /**
     * {@code DELETE  /tipo-registro-compras/:id} : delete the "id" tipoRegistroCompra.
     *
     * @param id the id of the tipoRegistroCompra to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-registro-compras/{id}")
    public ResponseEntity<Void> deleteTipoRegistroCompra(@PathVariable Long id) {
        log.debug("REST request to delete TipoRegistroCompra : {}", id);
        tipoRegistroCompraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-registro-compras?query=:query} : search for the tipoRegistroCompra corresponding
     * to the query.
     *
     * @param query the query of the tipoRegistroCompra search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-registro-compras")
    public ResponseEntity<List<TipoRegistroCompra>> searchTipoRegistroCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoRegistroCompras for query {}", query);
        Page<TipoRegistroCompra> page = tipoRegistroCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
