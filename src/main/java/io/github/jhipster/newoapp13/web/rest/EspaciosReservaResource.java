package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import io.github.jhipster.newoapp13.service.EspaciosReservaService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.EspaciosReservaCriteria;
import io.github.jhipster.newoapp13.service.EspaciosReservaQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.EspaciosReserva}.
 */
@RestController
@RequestMapping("/api")
public class EspaciosReservaResource {

    private final Logger log = LoggerFactory.getLogger(EspaciosReservaResource.class);

    private static final String ENTITY_NAME = "espaciosReserva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspaciosReservaService espaciosReservaService;

    private final EspaciosReservaQueryService espaciosReservaQueryService;

    public EspaciosReservaResource(EspaciosReservaService espaciosReservaService, EspaciosReservaQueryService espaciosReservaQueryService) {
        this.espaciosReservaService = espaciosReservaService;
        this.espaciosReservaQueryService = espaciosReservaQueryService;
    }

    /**
     * {@code POST  /espacios-reservas} : Create a new espaciosReserva.
     *
     * @param espaciosReserva the espaciosReserva to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new espaciosReserva, or with status {@code 400 (Bad Request)} if the espaciosReserva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/espacios-reservas")
    public ResponseEntity<EspaciosReserva> createEspaciosReserva(@Valid @RequestBody EspaciosReserva espaciosReserva) throws URISyntaxException {
        log.debug("REST request to save EspaciosReserva : {}", espaciosReserva);
        if (espaciosReserva.getId() != null) {
            throw new BadRequestAlertException("A new espaciosReserva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspaciosReserva result = espaciosReservaService.save(espaciosReserva);
        return ResponseEntity.created(new URI("/api/espacios-reservas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /espacios-reservas} : Updates an existing espaciosReserva.
     *
     * @param espaciosReserva the espaciosReserva to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espaciosReserva,
     * or with status {@code 400 (Bad Request)} if the espaciosReserva is not valid,
     * or with status {@code 500 (Internal Server Error)} if the espaciosReserva couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/espacios-reservas")
    public ResponseEntity<EspaciosReserva> updateEspaciosReserva(@Valid @RequestBody EspaciosReserva espaciosReserva) throws URISyntaxException {
        log.debug("REST request to update EspaciosReserva : {}", espaciosReserva);
        if (espaciosReserva.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EspaciosReserva result = espaciosReservaService.save(espaciosReserva);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espaciosReserva.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /espacios-reservas} : get all the espaciosReservas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of espaciosReservas in body.
     */
    @GetMapping("/espacios-reservas")
    public ResponseEntity<List<EspaciosReserva>> getAllEspaciosReservas(EspaciosReservaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EspaciosReservas by criteria: {}", criteria);
        Page<EspaciosReserva> page = espaciosReservaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /espacios-reservas/count} : count all the espaciosReservas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/espacios-reservas/count")
    public ResponseEntity<Long> countEspaciosReservas(EspaciosReservaCriteria criteria) {
        log.debug("REST request to count EspaciosReservas by criteria: {}", criteria);
        return ResponseEntity.ok().body(espaciosReservaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /espacios-reservas/:id} : get the "id" espaciosReserva.
     *
     * @param id the id of the espaciosReserva to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the espaciosReserva, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/espacios-reservas/{id}")
    public ResponseEntity<EspaciosReserva> getEspaciosReserva(@PathVariable Long id) {
        log.debug("REST request to get EspaciosReserva : {}", id);
        Optional<EspaciosReserva> espaciosReserva = espaciosReservaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(espaciosReserva);
    }

    /**
     * {@code DELETE  /espacios-reservas/:id} : delete the "id" espaciosReserva.
     *
     * @param id the id of the espaciosReserva to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/espacios-reservas/{id}")
    public ResponseEntity<Void> deleteEspaciosReserva(@PathVariable Long id) {
        log.debug("REST request to delete EspaciosReserva : {}", id);
        espaciosReservaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/espacios-reservas?query=:query} : search for the espaciosReserva corresponding
     * to the query.
     *
     * @param query the query of the espaciosReserva search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/espacios-reservas")
    public ResponseEntity<List<EspaciosReserva>> searchEspaciosReservas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EspaciosReservas for query {}", query);
        Page<EspaciosReserva> page = espaciosReservaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
