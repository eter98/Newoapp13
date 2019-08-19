package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.RegistroFacturacion;
import io.github.jhipster.newoapp13.service.RegistroFacturacionService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.RegistroFacturacionCriteria;
import io.github.jhipster.newoapp13.service.RegistroFacturacionQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.RegistroFacturacion}.
 */
@RestController
@RequestMapping("/api")
public class RegistroFacturacionResource {

    private final Logger log = LoggerFactory.getLogger(RegistroFacturacionResource.class);

    private static final String ENTITY_NAME = "registroFacturacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistroFacturacionService registroFacturacionService;

    private final RegistroFacturacionQueryService registroFacturacionQueryService;

    public RegistroFacturacionResource(RegistroFacturacionService registroFacturacionService, RegistroFacturacionQueryService registroFacturacionQueryService) {
        this.registroFacturacionService = registroFacturacionService;
        this.registroFacturacionQueryService = registroFacturacionQueryService;
    }

    /**
     * {@code POST  /registro-facturacions} : Create a new registroFacturacion.
     *
     * @param registroFacturacion the registroFacturacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registroFacturacion, or with status {@code 400 (Bad Request)} if the registroFacturacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registro-facturacions")
    public ResponseEntity<RegistroFacturacion> createRegistroFacturacion(@RequestBody RegistroFacturacion registroFacturacion) throws URISyntaxException {
        log.debug("REST request to save RegistroFacturacion : {}", registroFacturacion);
        if (registroFacturacion.getId() != null) {
            throw new BadRequestAlertException("A new registroFacturacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistroFacturacion result = registroFacturacionService.save(registroFacturacion);
        return ResponseEntity.created(new URI("/api/registro-facturacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registro-facturacions} : Updates an existing registroFacturacion.
     *
     * @param registroFacturacion the registroFacturacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroFacturacion,
     * or with status {@code 400 (Bad Request)} if the registroFacturacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registroFacturacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registro-facturacions")
    public ResponseEntity<RegistroFacturacion> updateRegistroFacturacion(@RequestBody RegistroFacturacion registroFacturacion) throws URISyntaxException {
        log.debug("REST request to update RegistroFacturacion : {}", registroFacturacion);
        if (registroFacturacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegistroFacturacion result = registroFacturacionService.save(registroFacturacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroFacturacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registro-facturacions} : get all the registroFacturacions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registroFacturacions in body.
     */
    @GetMapping("/registro-facturacions")
    public ResponseEntity<List<RegistroFacturacion>> getAllRegistroFacturacions(RegistroFacturacionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegistroFacturacions by criteria: {}", criteria);
        Page<RegistroFacturacion> page = registroFacturacionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /registro-facturacions/count} : count all the registroFacturacions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/registro-facturacions/count")
    public ResponseEntity<Long> countRegistroFacturacions(RegistroFacturacionCriteria criteria) {
        log.debug("REST request to count RegistroFacturacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(registroFacturacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /registro-facturacions/:id} : get the "id" registroFacturacion.
     *
     * @param id the id of the registroFacturacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registroFacturacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registro-facturacions/{id}")
    public ResponseEntity<RegistroFacturacion> getRegistroFacturacion(@PathVariable Long id) {
        log.debug("REST request to get RegistroFacturacion : {}", id);
        Optional<RegistroFacturacion> registroFacturacion = registroFacturacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registroFacturacion);
    }

    /**
     * {@code DELETE  /registro-facturacions/:id} : delete the "id" registroFacturacion.
     *
     * @param id the id of the registroFacturacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registro-facturacions/{id}")
    public ResponseEntity<Void> deleteRegistroFacturacion(@PathVariable Long id) {
        log.debug("REST request to delete RegistroFacturacion : {}", id);
        registroFacturacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/registro-facturacions?query=:query} : search for the registroFacturacion corresponding
     * to the query.
     *
     * @param query the query of the registroFacturacion search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/registro-facturacions")
    public ResponseEntity<List<RegistroFacturacion>> searchRegistroFacturacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RegistroFacturacions for query {}", query);
        Page<RegistroFacturacion> page = registroFacturacionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
