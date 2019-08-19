package io.github.jhipster.newoapp13.web.rest;

import io.github.jhipster.newoapp13.domain.ConsumoMarket;
import io.github.jhipster.newoapp13.service.ConsumoMarketService;
import io.github.jhipster.newoapp13.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.newoapp13.service.dto.ConsumoMarketCriteria;
import io.github.jhipster.newoapp13.service.ConsumoMarketQueryService;

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
 * REST controller for managing {@link io.github.jhipster.newoapp13.domain.ConsumoMarket}.
 */
@RestController
@RequestMapping("/api")
public class ConsumoMarketResource {

    private final Logger log = LoggerFactory.getLogger(ConsumoMarketResource.class);

    private static final String ENTITY_NAME = "consumoMarket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumoMarketService consumoMarketService;

    private final ConsumoMarketQueryService consumoMarketQueryService;

    public ConsumoMarketResource(ConsumoMarketService consumoMarketService, ConsumoMarketQueryService consumoMarketQueryService) {
        this.consumoMarketService = consumoMarketService;
        this.consumoMarketQueryService = consumoMarketQueryService;
    }

    /**
     * {@code POST  /consumo-markets} : Create a new consumoMarket.
     *
     * @param consumoMarket the consumoMarket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumoMarket, or with status {@code 400 (Bad Request)} if the consumoMarket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumo-markets")
    public ResponseEntity<ConsumoMarket> createConsumoMarket(@RequestBody ConsumoMarket consumoMarket) throws URISyntaxException {
        log.debug("REST request to save ConsumoMarket : {}", consumoMarket);
        if (consumoMarket.getId() != null) {
            throw new BadRequestAlertException("A new consumoMarket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumoMarket result = consumoMarketService.save(consumoMarket);
        return ResponseEntity.created(new URI("/api/consumo-markets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumo-markets} : Updates an existing consumoMarket.
     *
     * @param consumoMarket the consumoMarket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumoMarket,
     * or with status {@code 400 (Bad Request)} if the consumoMarket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumoMarket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumo-markets")
    public ResponseEntity<ConsumoMarket> updateConsumoMarket(@RequestBody ConsumoMarket consumoMarket) throws URISyntaxException {
        log.debug("REST request to update ConsumoMarket : {}", consumoMarket);
        if (consumoMarket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumoMarket result = consumoMarketService.save(consumoMarket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumoMarket.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumo-markets} : get all the consumoMarkets.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumoMarkets in body.
     */
    @GetMapping("/consumo-markets")
    public ResponseEntity<List<ConsumoMarket>> getAllConsumoMarkets(ConsumoMarketCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ConsumoMarkets by criteria: {}", criteria);
        Page<ConsumoMarket> page = consumoMarketQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /consumo-markets/count} : count all the consumoMarkets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/consumo-markets/count")
    public ResponseEntity<Long> countConsumoMarkets(ConsumoMarketCriteria criteria) {
        log.debug("REST request to count ConsumoMarkets by criteria: {}", criteria);
        return ResponseEntity.ok().body(consumoMarketQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consumo-markets/:id} : get the "id" consumoMarket.
     *
     * @param id the id of the consumoMarket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumoMarket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumo-markets/{id}")
    public ResponseEntity<ConsumoMarket> getConsumoMarket(@PathVariable Long id) {
        log.debug("REST request to get ConsumoMarket : {}", id);
        Optional<ConsumoMarket> consumoMarket = consumoMarketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumoMarket);
    }

    /**
     * {@code DELETE  /consumo-markets/:id} : delete the "id" consumoMarket.
     *
     * @param id the id of the consumoMarket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumo-markets/{id}")
    public ResponseEntity<Void> deleteConsumoMarket(@PathVariable Long id) {
        log.debug("REST request to delete ConsumoMarket : {}", id);
        consumoMarketService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/consumo-markets?query=:query} : search for the consumoMarket corresponding
     * to the query.
     *
     * @param query the query of the consumoMarket search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/consumo-markets")
    public ResponseEntity<List<ConsumoMarket>> searchConsumoMarkets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConsumoMarkets for query {}", query);
        Page<ConsumoMarket> page = consumoMarketService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
