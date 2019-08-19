package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.ConsumoMarket;
import io.github.jhipster.newoapp13.repository.ConsumoMarketRepository;
import io.github.jhipster.newoapp13.repository.search.ConsumoMarketSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ConsumoMarket}.
 */
@Service
@Transactional
public class ConsumoMarketService {

    private final Logger log = LoggerFactory.getLogger(ConsumoMarketService.class);

    private final ConsumoMarketRepository consumoMarketRepository;

    private final ConsumoMarketSearchRepository consumoMarketSearchRepository;

    public ConsumoMarketService(ConsumoMarketRepository consumoMarketRepository, ConsumoMarketSearchRepository consumoMarketSearchRepository) {
        this.consumoMarketRepository = consumoMarketRepository;
        this.consumoMarketSearchRepository = consumoMarketSearchRepository;
    }

    /**
     * Save a consumoMarket.
     *
     * @param consumoMarket the entity to save.
     * @return the persisted entity.
     */
    public ConsumoMarket save(ConsumoMarket consumoMarket) {
        log.debug("Request to save ConsumoMarket : {}", consumoMarket);
        ConsumoMarket result = consumoMarketRepository.save(consumoMarket);
        consumoMarketSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the consumoMarkets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumoMarket> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumoMarkets");
        return consumoMarketRepository.findAll(pageable);
    }


    /**
     * Get one consumoMarket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsumoMarket> findOne(Long id) {
        log.debug("Request to get ConsumoMarket : {}", id);
        return consumoMarketRepository.findById(id);
    }

    /**
     * Delete the consumoMarket by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsumoMarket : {}", id);
        consumoMarketRepository.deleteById(id);
        consumoMarketSearchRepository.deleteById(id);
    }

    /**
     * Search for the consumoMarket corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumoMarket> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConsumoMarkets for query {}", query);
        return consumoMarketSearchRepository.search(queryStringQuery(query), pageable);    }
}
