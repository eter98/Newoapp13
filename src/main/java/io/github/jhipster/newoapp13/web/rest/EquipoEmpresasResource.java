package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import io.github.jhipster.newoapp13.service.EquipoEmpresasService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.EquipoEmpresasCriteria;
import io.github.jhipster.newoapp13.service.EquipoEmpresasQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.EquipoEmpresas}.
 */
@RestController
@RequestMapping("/api")
public class EquipoEmpresasResource {

    private final Logger log = LoggerFactory.getLogger(EquipoEmpresasResource.class);

    private static final String ENTITY_NAME = "equipoEmpresas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipoEmpresasService equipoEmpresasService;

    private final EquipoEmpresasQueryService equipoEmpresasQueryService;

    public EquipoEmpresasResource(EquipoEmpresasService equipoEmpresasService, EquipoEmpresasQueryService equipoEmpresasQueryService) {
        this.equipoEmpresasService = equipoEmpresasService;
        this.equipoEmpresasQueryService = equipoEmpresasQueryService;
    }

    /**
     * {@code POST  /equipo-empresas} : Create a new equipoEmpresas.
     *
     * @param equipoEmpresas the equipoEmpresas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipoEmpresas, or with status {@code 400 (Bad Request)} if the equipoEmpresas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipo-empresas")
    public ResponseEntity<EquipoEmpresas> createEquipoEmpresas(@Valid @RequestBody EquipoEmpresas equipoEmpresas) throws URISyntaxException {
        log.debug("REST request to save EquipoEmpresas : {}", equipoEmpresas);
        if (equipoEmpresas.getId() != null) {
            throw new BadRequestAlertException("A new equipoEmpresas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipoEmpresas result = equipoEmpresasService.save(equipoEmpresas);
        return ResponseEntity.created(new URI("/api/equipo-empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipo-empresas} : Updates an existing equipoEmpresas.
     *
     * @param equipoEmpresas the equipoEmpresas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipoEmpresas,
     * or with status {@code 400 (Bad Request)} if the equipoEmpresas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipoEmpresas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipo-empresas")
    public ResponseEntity<EquipoEmpresas> updateEquipoEmpresas(@Valid @RequestBody EquipoEmpresas equipoEmpresas) throws URISyntaxException {
        log.debug("REST request to update EquipoEmpresas : {}", equipoEmpresas);
        if (equipoEmpresas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquipoEmpresas result = equipoEmpresasService.save(equipoEmpresas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipoEmpresas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipo-empresas} : get all the equipoEmpresas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipoEmpresas in body.
     */
    @GetMapping("/equipo-empresas")
    public ResponseEntity<List<EquipoEmpresas>> getAllEquipoEmpresas(EquipoEmpresasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EquipoEmpresas by criteria: {}", criteria);
        Page<EquipoEmpresas> page = equipoEmpresasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /equipo-empresas/count} : count all the equipoEmpresas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/equipo-empresas/count")
    public ResponseEntity<Long> countEquipoEmpresas(EquipoEmpresasCriteria criteria) {
        log.debug("REST request to count EquipoEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(equipoEmpresasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /equipo-empresas/:id} : get the "id" equipoEmpresas.
     *
     * @param id the id of the equipoEmpresas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipoEmpresas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipo-empresas/{id}")
    public ResponseEntity<EquipoEmpresas> getEquipoEmpresas(@PathVariable Long id) {
        log.debug("REST request to get EquipoEmpresas : {}", id);
        Optional<EquipoEmpresas> equipoEmpresas = equipoEmpresasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipoEmpresas);
    }

    /**
     * {@code DELETE  /equipo-empresas/:id} : delete the "id" equipoEmpresas.
     *
     * @param id the id of the equipoEmpresas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipo-empresas/{id}")
    public ResponseEntity<Void> deleteEquipoEmpresas(@PathVariable Long id) {
        log.debug("REST request to delete EquipoEmpresas : {}", id);
        equipoEmpresasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/equipo-empresas?query=:query} : search for the equipoEmpresas corresponding
     * to the query.
     *
     * @param query the query of the equipoEmpresas search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/equipo-empresas")
    public ResponseEntity<List<EquipoEmpresas>> searchEquipoEmpresas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EquipoEmpresas for query {}", query);
        Page<EquipoEmpresas> page = equipoEmpresasService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
