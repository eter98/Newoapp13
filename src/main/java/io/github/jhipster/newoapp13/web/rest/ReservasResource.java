package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.Reservas;
import io.github.jhipster.newoapp13.service.ReservasService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.ReservasCriteria;
import io.github.jhipster.newoapp13.service.ReservasQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.Reservas}.
 */
@RestController
@RequestMapping("/api")
public class ReservasResource {

    private final Logger log = LoggerFactory.getLogger(ReservasResource.class);

    private static final String ENTITY_NAME = "reservas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservasService reservasService;

    private final ReservasQueryService reservasQueryService;

    public ReservasResource(ReservasService reservasService, ReservasQueryService reservasQueryService) {
        this.reservasService = reservasService;
        this.reservasQueryService = reservasQueryService;
    }

    /**
     * {@code POST  /reservas} : Create a new reservas.
     *
     * @param reservas the reservas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservas, or with status {@code 400 (Bad Request)} if the reservas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservas")
    public ResponseEntity<Reservas> createReservas(@Valid @RequestBody Reservas reservas) throws URISyntaxException {
        log.debug("REST request to save Reservas : {}", reservas);
        if (reservas.getId() != null) {
            throw new BadRequestAlertException("A new reservas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reservas result = reservasService.save(reservas);
        return ResponseEntity.created(new URI("/api/reservas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservas} : Updates an existing reservas.
     *
     * @param reservas the reservas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservas,
     * or with status {@code 400 (Bad Request)} if the reservas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservas")
    public ResponseEntity<Reservas> updateReservas(@Valid @RequestBody Reservas reservas) throws URISyntaxException {
        log.debug("REST request to update Reservas : {}", reservas);
        if (reservas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reservas result = reservasService.save(reservas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reservas} : get all the reservas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservas in body.
     */
    @GetMapping("/reservas")
    public ResponseEntity<List<Reservas>> getAllReservas(ReservasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Reservas by criteria: {}", criteria);
        Page<Reservas> page = reservasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /reservas/count} : count all the reservas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/reservas/count")
    public ResponseEntity<Long> countReservas(ReservasCriteria criteria) {
        log.debug("REST request to count Reservas by criteria: {}", criteria);
        return ResponseEntity.ok().body(reservasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reservas/:id} : get the "id" reservas.
     *
     * @param id the id of the reservas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservas/{id}")
    public ResponseEntity<Reservas> getReservas(@PathVariable Long id) {
        log.debug("REST request to get Reservas : {}", id);
        Optional<Reservas> reservas = reservasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservas);
    }

    /**
     * {@code DELETE  /reservas/:id} : delete the "id" reservas.
     *
     * @param id the id of the reservas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> deleteReservas(@PathVariable Long id) {
        log.debug("REST request to delete Reservas : {}", id);
        reservasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/reservas?query=:query} : search for the reservas corresponding
     * to the query.
     *
     * @param query the query of the reservas search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reservas")
    public ResponseEntity<List<Reservas>> searchReservas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Reservas for query {}", query);
        Page<Reservas> page = reservasService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
