package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas;
import io.github.jhipster.newoapp13.service.MiembrosEquipoEmpresasService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.MiembrosEquipoEmpresasCriteria;
import io.github.jhipster.newoapp13.service.MiembrosEquipoEmpresasQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas}.
 */
@RestController
@RequestMapping("/api")
public class MiembrosEquipoEmpresasResource {

    private final Logger log = LoggerFactory.getLogger(MiembrosEquipoEmpresasResource.class);

    private static final String ENTITY_NAME = "miembrosEquipoEmpresas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MiembrosEquipoEmpresasService miembrosEquipoEmpresasService;

    private final MiembrosEquipoEmpresasQueryService miembrosEquipoEmpresasQueryService;

    public MiembrosEquipoEmpresasResource(MiembrosEquipoEmpresasService miembrosEquipoEmpresasService, MiembrosEquipoEmpresasQueryService miembrosEquipoEmpresasQueryService) {
        this.miembrosEquipoEmpresasService = miembrosEquipoEmpresasService;
        this.miembrosEquipoEmpresasQueryService = miembrosEquipoEmpresasQueryService;
    }

    /**
     * {@code POST  /miembros-equipo-empresas} : Create a new miembrosEquipoEmpresas.
     *
     * @param miembrosEquipoEmpresas the miembrosEquipoEmpresas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new miembrosEquipoEmpresas, or with status {@code 400 (Bad Request)} if the miembrosEquipoEmpresas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/miembros-equipo-empresas")
    public ResponseEntity<MiembrosEquipoEmpresas> createMiembrosEquipoEmpresas(@RequestBody MiembrosEquipoEmpresas miembrosEquipoEmpresas) throws URISyntaxException {
        log.debug("REST request to save MiembrosEquipoEmpresas : {}", miembrosEquipoEmpresas);
        if (miembrosEquipoEmpresas.getId() != null) {
            throw new BadRequestAlertException("A new miembrosEquipoEmpresas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MiembrosEquipoEmpresas result = miembrosEquipoEmpresasService.save(miembrosEquipoEmpresas);
        return ResponseEntity.created(new URI("/api/miembros-equipo-empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /miembros-equipo-empresas} : Updates an existing miembrosEquipoEmpresas.
     *
     * @param miembrosEquipoEmpresas the miembrosEquipoEmpresas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated miembrosEquipoEmpresas,
     * or with status {@code 400 (Bad Request)} if the miembrosEquipoEmpresas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the miembrosEquipoEmpresas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/miembros-equipo-empresas")
    public ResponseEntity<MiembrosEquipoEmpresas> updateMiembrosEquipoEmpresas(@RequestBody MiembrosEquipoEmpresas miembrosEquipoEmpresas) throws URISyntaxException {
        log.debug("REST request to update MiembrosEquipoEmpresas : {}", miembrosEquipoEmpresas);
        if (miembrosEquipoEmpresas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MiembrosEquipoEmpresas result = miembrosEquipoEmpresasService.save(miembrosEquipoEmpresas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, miembrosEquipoEmpresas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /miembros-equipo-empresas} : get all the miembrosEquipoEmpresas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of miembrosEquipoEmpresas in body.
     */
    @GetMapping("/miembros-equipo-empresas")
    public ResponseEntity<List<MiembrosEquipoEmpresas>> getAllMiembrosEquipoEmpresas(MiembrosEquipoEmpresasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MiembrosEquipoEmpresas by criteria: {}", criteria);
        Page<MiembrosEquipoEmpresas> page = miembrosEquipoEmpresasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /miembros-equipo-empresas/count} : count all the miembrosEquipoEmpresas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/miembros-equipo-empresas/count")
    public ResponseEntity<Long> countMiembrosEquipoEmpresas(MiembrosEquipoEmpresasCriteria criteria) {
        log.debug("REST request to count MiembrosEquipoEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(miembrosEquipoEmpresasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /miembros-equipo-empresas/:id} : get the "id" miembrosEquipoEmpresas.
     *
     * @param id the id of the miembrosEquipoEmpresas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the miembrosEquipoEmpresas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/miembros-equipo-empresas/{id}")
    public ResponseEntity<MiembrosEquipoEmpresas> getMiembrosEquipoEmpresas(@PathVariable Long id) {
        log.debug("REST request to get MiembrosEquipoEmpresas : {}", id);
        Optional<MiembrosEquipoEmpresas> miembrosEquipoEmpresas = miembrosEquipoEmpresasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(miembrosEquipoEmpresas);
    }

    /**
     * {@code DELETE  /miembros-equipo-empresas/:id} : delete the "id" miembrosEquipoEmpresas.
     *
     * @param id the id of the miembrosEquipoEmpresas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/miembros-equipo-empresas/{id}")
    public ResponseEntity<Void> deleteMiembrosEquipoEmpresas(@PathVariable Long id) {
        log.debug("REST request to delete MiembrosEquipoEmpresas : {}", id);
        miembrosEquipoEmpresasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/miembros-equipo-empresas?query=:query} : search for the miembrosEquipoEmpresas corresponding
     * to the query.
     *
     * @param query the query of the miembrosEquipoEmpresas search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/miembros-equipo-empresas")
    public ResponseEntity<List<MiembrosEquipoEmpresas>> searchMiembrosEquipoEmpresas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MiembrosEquipoEmpresas for query {}", query);
        Page<MiembrosEquipoEmpresas> page = miembrosEquipoEmpresasService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
