package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.MargenNewoBlog;
import io.github.jhipster.newoapp13.service.MargenNewoBlogService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.MargenNewoBlogCriteria;
import io.github.jhipster.newoapp13.service.MargenNewoBlogQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.MargenNewoBlog}.
 */
@RestController
@RequestMapping("/api")
public class MargenNewoBlogResource {

    private final Logger log = LoggerFactory.getLogger(MargenNewoBlogResource.class);

    private static final String ENTITY_NAME = "margenNewoBlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MargenNewoBlogService margenNewoBlogService;

    private final MargenNewoBlogQueryService margenNewoBlogQueryService;

    public MargenNewoBlogResource(MargenNewoBlogService margenNewoBlogService, MargenNewoBlogQueryService margenNewoBlogQueryService) {
        this.margenNewoBlogService = margenNewoBlogService;
        this.margenNewoBlogQueryService = margenNewoBlogQueryService;
    }

    /**
     * {@code POST  /margen-newo-blogs} : Create a new margenNewoBlog.
     *
     * @param margenNewoBlog the margenNewoBlog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new margenNewoBlog, or with status {@code 400 (Bad Request)} if the margenNewoBlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/margen-newo-blogs")
    public ResponseEntity<MargenNewoBlog> createMargenNewoBlog(@RequestBody MargenNewoBlog margenNewoBlog) throws URISyntaxException {
        log.debug("REST request to save MargenNewoBlog : {}", margenNewoBlog);
        if (margenNewoBlog.getId() != null) {
            throw new BadRequestAlertException("A new margenNewoBlog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MargenNewoBlog result = margenNewoBlogService.save(margenNewoBlog);
        return ResponseEntity.created(new URI("/api/margen-newo-blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /margen-newo-blogs} : Updates an existing margenNewoBlog.
     *
     * @param margenNewoBlog the margenNewoBlog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated margenNewoBlog,
     * or with status {@code 400 (Bad Request)} if the margenNewoBlog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the margenNewoBlog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/margen-newo-blogs")
    public ResponseEntity<MargenNewoBlog> updateMargenNewoBlog(@RequestBody MargenNewoBlog margenNewoBlog) throws URISyntaxException {
        log.debug("REST request to update MargenNewoBlog : {}", margenNewoBlog);
        if (margenNewoBlog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MargenNewoBlog result = margenNewoBlogService.save(margenNewoBlog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, margenNewoBlog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /margen-newo-blogs} : get all the margenNewoBlogs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of margenNewoBlogs in body.
     */
    @GetMapping("/margen-newo-blogs")
    public ResponseEntity<List<MargenNewoBlog>> getAllMargenNewoBlogs(MargenNewoBlogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MargenNewoBlogs by criteria: {}", criteria);
        Page<MargenNewoBlog> page = margenNewoBlogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /margen-newo-blogs/count} : count all the margenNewoBlogs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/margen-newo-blogs/count")
    public ResponseEntity<Long> countMargenNewoBlogs(MargenNewoBlogCriteria criteria) {
        log.debug("REST request to count MargenNewoBlogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(margenNewoBlogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /margen-newo-blogs/:id} : get the "id" margenNewoBlog.
     *
     * @param id the id of the margenNewoBlog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the margenNewoBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/margen-newo-blogs/{id}")
    public ResponseEntity<MargenNewoBlog> getMargenNewoBlog(@PathVariable Long id) {
        log.debug("REST request to get MargenNewoBlog : {}", id);
        Optional<MargenNewoBlog> margenNewoBlog = margenNewoBlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(margenNewoBlog);
    }

    /**
     * {@code DELETE  /margen-newo-blogs/:id} : delete the "id" margenNewoBlog.
     *
     * @param id the id of the margenNewoBlog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/margen-newo-blogs/{id}")
    public ResponseEntity<Void> deleteMargenNewoBlog(@PathVariable Long id) {
        log.debug("REST request to delete MargenNewoBlog : {}", id);
        margenNewoBlogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/margen-newo-blogs?query=:query} : search for the margenNewoBlog corresponding
     * to the query.
     *
     * @param query the query of the margenNewoBlog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/margen-newo-blogs")
    public ResponseEntity<List<MargenNewoBlog>> searchMargenNewoBlogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MargenNewoBlogs for query {}", query);
        Page<MargenNewoBlog> page = margenNewoBlogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
