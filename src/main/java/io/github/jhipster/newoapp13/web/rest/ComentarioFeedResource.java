package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.ComentarioFeed;
import io.github.jhipster.newoapp13.service.ComentarioFeedService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.ComentarioFeedCriteria;
import io.github.jhipster.newoapp13.service.ComentarioFeedQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.ComentarioFeed}.
 */
@RestController
@RequestMapping("/api")
public class ComentarioFeedResource {

    private final Logger log = LoggerFactory.getLogger(ComentarioFeedResource.class);

    private static final String ENTITY_NAME = "comentarioFeed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComentarioFeedService comentarioFeedService;

    private final ComentarioFeedQueryService comentarioFeedQueryService;

    public ComentarioFeedResource(ComentarioFeedService comentarioFeedService, ComentarioFeedQueryService comentarioFeedQueryService) {
        this.comentarioFeedService = comentarioFeedService;
        this.comentarioFeedQueryService = comentarioFeedQueryService;
    }

    /**
     * {@code POST  /comentario-feeds} : Create a new comentarioFeed.
     *
     * @param comentarioFeed the comentarioFeed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comentarioFeed, or with status {@code 400 (Bad Request)} if the comentarioFeed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comentario-feeds")
    public ResponseEntity<ComentarioFeed> createComentarioFeed(@Valid @RequestBody ComentarioFeed comentarioFeed) throws URISyntaxException {
        log.debug("REST request to save ComentarioFeed : {}", comentarioFeed);
        if (comentarioFeed.getId() != null) {
            throw new BadRequestAlertException("A new comentarioFeed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentarioFeed result = comentarioFeedService.save(comentarioFeed);
        return ResponseEntity.created(new URI("/api/comentario-feeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comentario-feeds} : Updates an existing comentarioFeed.
     *
     * @param comentarioFeed the comentarioFeed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comentarioFeed,
     * or with status {@code 400 (Bad Request)} if the comentarioFeed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comentarioFeed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comentario-feeds")
    public ResponseEntity<ComentarioFeed> updateComentarioFeed(@Valid @RequestBody ComentarioFeed comentarioFeed) throws URISyntaxException {
        log.debug("REST request to update ComentarioFeed : {}", comentarioFeed);
        if (comentarioFeed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComentarioFeed result = comentarioFeedService.save(comentarioFeed);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comentarioFeed.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comentario-feeds} : get all the comentarioFeeds.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comentarioFeeds in body.
     */
    @GetMapping("/comentario-feeds")
    public ResponseEntity<List<ComentarioFeed>> getAllComentarioFeeds(ComentarioFeedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ComentarioFeeds by criteria: {}", criteria);
        Page<ComentarioFeed> page = comentarioFeedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /comentario-feeds/count} : count all the comentarioFeeds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/comentario-feeds/count")
    public ResponseEntity<Long> countComentarioFeeds(ComentarioFeedCriteria criteria) {
        log.debug("REST request to count ComentarioFeeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(comentarioFeedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comentario-feeds/:id} : get the "id" comentarioFeed.
     *
     * @param id the id of the comentarioFeed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comentarioFeed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comentario-feeds/{id}")
    public ResponseEntity<ComentarioFeed> getComentarioFeed(@PathVariable Long id) {
        log.debug("REST request to get ComentarioFeed : {}", id);
        Optional<ComentarioFeed> comentarioFeed = comentarioFeedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comentarioFeed);
    }

    /**
     * {@code DELETE  /comentario-feeds/:id} : delete the "id" comentarioFeed.
     *
     * @param id the id of the comentarioFeed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comentario-feeds/{id}")
    public ResponseEntity<Void> deleteComentarioFeed(@PathVariable Long id) {
        log.debug("REST request to delete ComentarioFeed : {}", id);
        comentarioFeedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/comentario-feeds?query=:query} : search for the comentarioFeed corresponding
     * to the query.
     *
     * @param query the query of the comentarioFeed search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/comentario-feeds")
    public ResponseEntity<List<ComentarioFeed>> searchComentarioFeeds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ComentarioFeeds for query {}", query);
        Page<ComentarioFeed> page = comentarioFeedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
