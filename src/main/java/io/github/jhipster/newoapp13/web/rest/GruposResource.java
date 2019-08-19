package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.service.GruposService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.GruposCriteria;
import io.github.jhipster.newoapp13.service.GruposQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.Grupos}.
 */
@RestController
@RequestMapping("/api")
public class GruposResource {

    private final Logger log = LoggerFactory.getLogger(GruposResource.class);

    private static final String ENTITY_NAME = "grupos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GruposService gruposService;

    private final GruposQueryService gruposQueryService;

    public GruposResource(GruposService gruposService, GruposQueryService gruposQueryService) {
        this.gruposService = gruposService;
        this.gruposQueryService = gruposQueryService;
    }

    /**
     * {@code POST  /grupos} : Create a new grupos.
     *
     * @param grupos the grupos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupos, or with status {@code 400 (Bad Request)} if the grupos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupos")
    public ResponseEntity<Grupos> createGrupos(@Valid @RequestBody Grupos grupos) throws URISyntaxException {
        log.debug("REST request to save Grupos : {}", grupos);
        if (grupos.getId() != null) {
            throw new BadRequestAlertException("A new grupos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grupos result = gruposService.save(grupos);
        return ResponseEntity.created(new URI("/api/grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupos} : Updates an existing grupos.
     *
     * @param grupos the grupos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupos,
     * or with status {@code 400 (Bad Request)} if the grupos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupos")
    public ResponseEntity<Grupos> updateGrupos(@Valid @RequestBody Grupos grupos) throws URISyntaxException {
        log.debug("REST request to update Grupos : {}", grupos);
        if (grupos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Grupos result = gruposService.save(grupos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupos} : get all the grupos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupos in body.
     */
    @GetMapping("/grupos")
    public ResponseEntity<List<Grupos>> getAllGrupos(GruposCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Grupos by criteria: {}", criteria);
        Page<Grupos> page = gruposQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /grupos/count} : count all the grupos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/grupos/count")
    public ResponseEntity<Long> countGrupos(GruposCriteria criteria) {
        log.debug("REST request to count Grupos by criteria: {}", criteria);
        return ResponseEntity.ok().body(gruposQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /grupos/:id} : get the "id" grupos.
     *
     * @param id the id of the grupos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupos/{id}")
    public ResponseEntity<Grupos> getGrupos(@PathVariable Long id) {
        log.debug("REST request to get Grupos : {}", id);
        Optional<Grupos> grupos = gruposService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupos);
    }

    /**
     * {@code DELETE  /grupos/:id} : delete the "id" grupos.
     *
     * @param id the id of the grupos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupos/{id}")
    public ResponseEntity<Void> deleteGrupos(@PathVariable Long id) {
        log.debug("REST request to delete Grupos : {}", id);
        gruposService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grupos?query=:query} : search for the grupos corresponding
     * to the query.
     *
     * @param query the query of the grupos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grupos")
    public ResponseEntity<List<Grupos>> searchGrupos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Grupos for query {}", query);
        Page<Grupos> page = gruposService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
