package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.service.CategoriaContenidosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.CategoriaContenidosCriteria;
import io.github.jhipster.newoapp13.service.CategoriaContenidosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.CategoriaContenidos}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaContenidosResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaContenidosResource.class);

    private static final String ENTITY_NAME = "categoriaContenidos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaContenidosService categoriaContenidosService;

    private final CategoriaContenidosQueryService categoriaContenidosQueryService;

    public CategoriaContenidosResource(CategoriaContenidosService categoriaContenidosService, CategoriaContenidosQueryService categoriaContenidosQueryService) {
        this.categoriaContenidosService = categoriaContenidosService;
        this.categoriaContenidosQueryService = categoriaContenidosQueryService;
    }

    /**
     * {@code POST  /categoria-contenidos} : Create a new categoriaContenidos.
     *
     * @param categoriaContenidos the categoriaContenidos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaContenidos, or with status {@code 400 (Bad Request)} if the categoriaContenidos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-contenidos")
    public ResponseEntity<CategoriaContenidos> createCategoriaContenidos(@Valid @RequestBody CategoriaContenidos categoriaContenidos) throws URISyntaxException {
        log.debug("REST request to save CategoriaContenidos : {}", categoriaContenidos);
        if (categoriaContenidos.getId() != null) {
            throw new BadRequestAlertException("A new categoriaContenidos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaContenidos result = categoriaContenidosService.save(categoriaContenidos);
        return ResponseEntity.created(new URI("/api/categoria-contenidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-contenidos} : Updates an existing categoriaContenidos.
     *
     * @param categoriaContenidos the categoriaContenidos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaContenidos,
     * or with status {@code 400 (Bad Request)} if the categoriaContenidos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaContenidos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-contenidos")
    public ResponseEntity<CategoriaContenidos> updateCategoriaContenidos(@Valid @RequestBody CategoriaContenidos categoriaContenidos) throws URISyntaxException {
        log.debug("REST request to update CategoriaContenidos : {}", categoriaContenidos);
        if (categoriaContenidos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaContenidos result = categoriaContenidosService.save(categoriaContenidos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaContenidos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-contenidos} : get all the categoriaContenidos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaContenidos in body.
     */
    @GetMapping("/categoria-contenidos")
    public ResponseEntity<List<CategoriaContenidos>> getAllCategoriaContenidos(CategoriaContenidosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategoriaContenidos by criteria: {}", criteria);
        Page<CategoriaContenidos> page = categoriaContenidosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /categoria-contenidos/count} : count all the categoriaContenidos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/categoria-contenidos/count")
    public ResponseEntity<Long> countCategoriaContenidos(CategoriaContenidosCriteria criteria) {
        log.debug("REST request to count CategoriaContenidos by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaContenidosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-contenidos/:id} : get the "id" categoriaContenidos.
     *
     * @param id the id of the categoriaContenidos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaContenidos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-contenidos/{id}")
    public ResponseEntity<CategoriaContenidos> getCategoriaContenidos(@PathVariable Long id) {
        log.debug("REST request to get CategoriaContenidos : {}", id);
        Optional<CategoriaContenidos> categoriaContenidos = categoriaContenidosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaContenidos);
    }

    /**
     * {@code DELETE  /categoria-contenidos/:id} : delete the "id" categoriaContenidos.
     *
     * @param id the id of the categoriaContenidos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-contenidos/{id}")
    public ResponseEntity<Void> deleteCategoriaContenidos(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaContenidos : {}", id);
        categoriaContenidosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/categoria-contenidos?query=:query} : search for the categoriaContenidos corresponding
     * to the query.
     *
     * @param query the query of the categoriaContenidos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/categoria-contenidos")
    public ResponseEntity<List<CategoriaContenidos>> searchCategoriaContenidos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoriaContenidos for query {}", query);
        Page<CategoriaContenidos> page = categoriaContenidosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
