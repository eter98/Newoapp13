package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.PrepagoConsumo;
import io.github.jhipster.newoapp13.repository.PrepagoConsumoRepository;
import io.github.jhipster.newoapp13.repository.search.PrepagoConsumoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrepagoConsumo}.
 */
@Service
@Transactional
public class PrepagoConsumoService {

    private final Logger log = LoggerFactory.getLogger(PrepagoConsumoService.class);

    private final PrepagoConsumoRepository prepagoConsumoRepository;

    private final PrepagoConsumoSearchRepository prepagoConsumoSearchRepository;

    public PrepagoConsumoService(PrepagoConsumoRepository prepagoConsumoRepository, PrepagoConsumoSearchRepository prepagoConsumoSearchRepository) {
        this.prepagoConsumoRepository = prepagoConsumoRepository;
        this.prepagoConsumoSearchRepository = prepagoConsumoSearchRepository;
    }

    /**
     * Save a prepagoConsumo.
     *
     * @param prepagoConsumo the entity to save.
     * @return the persisted entity.
     */
    public PrepagoConsumo save(PrepagoConsumo prepagoConsumo) {
        log.debug("Request to save PrepagoConsumo : {}", prepagoConsumo);
        PrepagoConsumo result = prepagoConsumoRepository.save(prepagoConsumo);
        prepagoConsumoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the prepagoConsumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrepagoConsumo> findAll(Pageable pageable) {
        log.debug("Request to get all PrepagoConsumos");
        return prepagoConsumoRepository.findAll(pageable);
    }


    /**
     * Get one prepagoConsumo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrepagoConsumo> findOne(Long id) {
        log.debug("Request to get PrepagoConsumo : {}", id);
        return prepagoConsumoRepository.findById(id);
    }

    /**
     * Delete the prepagoConsumo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrepagoConsumo : {}", id);
        prepagoConsumoRepository.deleteById(id);
        prepagoConsumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the prepagoConsumo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrepagoConsumo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrepagoConsumos for query {}", query);
        return prepagoConsumoSearchRepository.search(queryStringQuery(query), pageable);    }
}
