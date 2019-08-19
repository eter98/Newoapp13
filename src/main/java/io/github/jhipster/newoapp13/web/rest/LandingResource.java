package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.Landing;
import io.github.jhipster.newoapp13.service.LandingService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.LandingCriteria;
import io.github.jhipster.newoapp13.service.LandingQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.Landing}.
 */
@RestController
@RequestMapping("/api")
public class LandingResource {

    private final Logger log = LoggerFactory.getLogger(LandingResource.class);

    private static final String ENTITY_NAME = "landing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandingService landingService;

    private final LandingQueryService landingQueryService;

    public LandingResource(LandingService landingService, LandingQueryService landingQueryService) {
        this.landingService = landingService;
        this.landingQueryService = landingQueryService;
    }

    /**
     * {@code POST  /landings} : Create a new landing.
     *
     * @param landing the landing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landing, or with status {@code 400 (Bad Request)} if the landing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/landings")
    public ResponseEntity<Landing> createLanding(@Valid @RequestBody Landing landing) throws URISyntaxException {
        log.debug("REST request to save Landing : {}", landing);
        if (landing.getId() != null) {
            throw new BadRequestAlertException("A new landing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Landing result = landingService.save(landing);
        return ResponseEntity.created(new URI("/api/landings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /landings} : Updates an existing landing.
     *
     * @param landing the landing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landing,
     * or with status {@code 400 (Bad Request)} if the landing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/landings")
    public ResponseEntity<Landing> updateLanding(@Valid @RequestBody Landing landing) throws URISyntaxException {
        log.debug("REST request to update Landing : {}", landing);
        if (landing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Landing result = landingService.save(landing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landing.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /landings} : get all the landings.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landings in body.
     */
    @GetMapping("/landings")
    public ResponseEntity<List<Landing>> getAllLandings(LandingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Landings by criteria: {}", criteria);
        Page<Landing> page = landingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /landings/count} : count all the landings.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/landings/count")
    public ResponseEntity<Long> countLandings(LandingCriteria criteria) {
        log.debug("REST request to count Landings by criteria: {}", criteria);
        return ResponseEntity.ok().body(landingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /landings/:id} : get the "id" landing.
     *
     * @param id the id of the landing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/landings/{id}")
    public ResponseEntity<Landing> getLanding(@PathVariable Long id) {
        log.debug("REST request to get Landing : {}", id);
        Optional<Landing> landing = landingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(landing);
    }

    /**
     * {@code DELETE  /landings/:id} : delete the "id" landing.
     *
     * @param id the id of the landing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/landings/{id}")
    public ResponseEntity<Void> deleteLanding(@PathVariable Long id) {
        log.debug("REST request to delete Landing : {}", id);
        landingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/landings?query=:query} : search for the landing corresponding
     * to the query.
     *
     * @param query the query of the landing search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/landings")
    public ResponseEntity<List<Landing>> searchLandings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Landings for query {}", query);
        Page<Landing> page = landingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
