package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.service.MiembrosService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.MiembrosCriteria;
import io.github.jhipster.newoapp13.service.MiembrosQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.Miembros}.
 */
@RestController
@RequestMapping("/api")
public class MiembrosResource {

    private final Logger log = LoggerFactory.getLogger(MiembrosResource.class);

    private static final String ENTITY_NAME = "miembros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MiembrosService miembrosService;

    private final MiembrosQueryService miembrosQueryService;

    public MiembrosResource(MiembrosService miembrosService, MiembrosQueryService miembrosQueryService) {
        this.miembrosService = miembrosService;
        this.miembrosQueryService = miembrosQueryService;
    }

    /**
     * {@code POST  /miembros} : Create a new miembros.
     *
     * @param miembros the miembros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new miembros, or with status {@code 400 (Bad Request)} if the miembros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/miembros")
    public ResponseEntity<Miembros> createMiembros(@Valid @RequestBody Miembros miembros) throws URISyntaxException {
        log.debug("REST request to save Miembros : {}", miembros);
        if (miembros.getId() != null) {
            throw new BadRequestAlertException("A new miembros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Miembros result = miembrosService.save(miembros);
        return ResponseEntity.created(new URI("/api/miembros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /miembros} : Updates an existing miembros.
     *
     * @param miembros the miembros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated miembros,
     * or with status {@code 400 (Bad Request)} if the miembros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the miembros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/miembros")
    public ResponseEntity<Miembros> updateMiembros(@Valid @RequestBody Miembros miembros) throws URISyntaxException {
        log.debug("REST request to update Miembros : {}", miembros);
        if (miembros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Miembros result = miembrosService.save(miembros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, miembros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /miembros} : get all the miembros.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of miembros in body.
     */
    @GetMapping("/miembros")
    public ResponseEntity<List<Miembros>> getAllMiembros(MiembrosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Miembros by criteria: {}", criteria);
        Page<Miembros> page = miembrosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /miembros/count} : count all the miembros.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/miembros/count")
    public ResponseEntity<Long> countMiembros(MiembrosCriteria criteria) {
        log.debug("REST request to count Miembros by criteria: {}", criteria);
        return ResponseEntity.ok().body(miembrosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /miembros/:id} : get the "id" miembros.
     *
     * @param id the id of the miembros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the miembros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/miembros/{id}")
    public ResponseEntity<Miembros> getMiembros(@PathVariable Long id) {
        log.debug("REST request to get Miembros : {}", id);
        Optional<Miembros> miembros = miembrosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(miembros);
    }

    /**
     * {@code DELETE  /miembros/:id} : delete the "id" miembros.
     *
     * @param id the id of the miembros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/miembros/{id}")
    public ResponseEntity<Void> deleteMiembros(@PathVariable Long id) {
        log.debug("REST request to delete Miembros : {}", id);
        miembrosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/miembros?query=:query} : search for the miembros corresponding
     * to the query.
     *
     * @param query the query of the miembros search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/miembros")
    public ResponseEntity<List<Miembros>> searchMiembros(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Miembros for query {}", query);
        Page<Miembros> page = miembrosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
