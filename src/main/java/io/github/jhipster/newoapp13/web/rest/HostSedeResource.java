package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.HostSede;
import io.github.jhipster.newoapp13.service.HostSedeService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.HostSedeCriteria;
import io.github.jhipster.newoapp13.service.HostSedeQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.HostSede}.
 */
@RestController
@RequestMapping("/api")
public class HostSedeResource {

    private final Logger log = LoggerFactory.getLogger(HostSedeResource.class);

    private static final String ENTITY_NAME = "hostSede";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HostSedeService hostSedeService;

    private final HostSedeQueryService hostSedeQueryService;

    public HostSedeResource(HostSedeService hostSedeService, HostSedeQueryService hostSedeQueryService) {
        this.hostSedeService = hostSedeService;
        this.hostSedeQueryService = hostSedeQueryService;
    }

    /**
     * {@code POST  /host-sedes} : Create a new hostSede.
     *
     * @param hostSede the hostSede to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hostSede, or with status {@code 400 (Bad Request)} if the hostSede has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/host-sedes")
    public ResponseEntity<HostSede> createHostSede(@RequestBody HostSede hostSede) throws URISyntaxException {
        log.debug("REST request to save HostSede : {}", hostSede);
        if (hostSede.getId() != null) {
            throw new BadRequestAlertException("A new hostSede cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HostSede result = hostSedeService.save(hostSede);
        return ResponseEntity.created(new URI("/api/host-sedes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /host-sedes} : Updates an existing hostSede.
     *
     * @param hostSede the hostSede to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hostSede,
     * or with status {@code 400 (Bad Request)} if the hostSede is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hostSede couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/host-sedes")
    public ResponseEntity<HostSede> updateHostSede(@RequestBody HostSede hostSede) throws URISyntaxException {
        log.debug("REST request to update HostSede : {}", hostSede);
        if (hostSede.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HostSede result = hostSedeService.save(hostSede);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hostSede.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /host-sedes} : get all the hostSedes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hostSedes in body.
     */
    @GetMapping("/host-sedes")
    public ResponseEntity<List<HostSede>> getAllHostSedes(HostSedeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get HostSedes by criteria: {}", criteria);
        Page<HostSede> page = hostSedeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /host-sedes/count} : count all the hostSedes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/host-sedes/count")
    public ResponseEntity<Long> countHostSedes(HostSedeCriteria criteria) {
        log.debug("REST request to count HostSedes by criteria: {}", criteria);
        return ResponseEntity.ok().body(hostSedeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /host-sedes/:id} : get the "id" hostSede.
     *
     * @param id the id of the hostSede to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hostSede, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/host-sedes/{id}")
    public ResponseEntity<HostSede> getHostSede(@PathVariable Long id) {
        log.debug("REST request to get HostSede : {}", id);
        Optional<HostSede> hostSede = hostSedeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hostSede);
    }

    /**
     * {@code DELETE  /host-sedes/:id} : delete the "id" hostSede.
     *
     * @param id the id of the hostSede to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/host-sedes/{id}")
    public ResponseEntity<Void> deleteHostSede(@PathVariable Long id) {
        log.debug("REST request to delete HostSede : {}", id);
        hostSedeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/host-sedes?query=:query} : search for the hostSede corresponding
     * to the query.
     *
     * @param query the query of the hostSede search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/host-sedes")
    public ResponseEntity<List<HostSede>> searchHostSedes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HostSedes for query {}", query);
        Page<HostSede> page = hostSedeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
