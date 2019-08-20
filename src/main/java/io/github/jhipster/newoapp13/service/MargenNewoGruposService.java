package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.MargenNewoGrupos;
import io.github.jhipster.newoapp13.repository.MargenNewoGruposRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoGruposSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MargenNewoGrupos}.
 */
@Service
@Transactional
public class MargenNewoGruposService {

    private final Logger log = LoggerFactory.getLogger(MargenNewoGruposService.class);

    private final MargenNewoGruposRepository margenNewoGruposRepository;

    private final MargenNewoGruposSearchRepository margenNewoGruposSearchRepository;

    public MargenNewoGruposService(MargenNewoGruposRepository margenNewoGruposRepository, MargenNewoGruposSearchRepository margenNewoGruposSearchRepository) {
        this.margenNewoGruposRepository = margenNewoGruposRepository;
        this.margenNewoGruposSearchRepository = margenNewoGruposSearchRepository;
    }

    /**
     * Save a margenNewoGrupos.
     *
     * @param margenNewoGrupos the entity to save.
     * @return the persisted entity.
     */
    public MargenNewoGrupos save(MargenNewoGrupos margenNewoGrupos) {
        log.debug("Request to save MargenNewoGrupos : {}", margenNewoGrupos);
        MargenNewoGrupos result = margenNewoGruposRepository.save(margenNewoGrupos);
        margenNewoGruposSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the margenNewoGrupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoGrupos> findAll(Pageable pageable) {
        log.debug("Request to get all MargenNewoGrupos");
        return margenNewoGruposRepository.findAll(pageable);
    }


    /**
     * Get one margenNewoGrupos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MargenNewoGrupos> findOne(Long id) {
        log.debug("Request to get MargenNewoGrupos : {}", id);
        return margenNewoGruposRepository.findById(id);
    }

    /**
     * Delete the margenNewoGrupos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MargenNewoGrupos : {}", id);
        margenNewoGruposRepository.deleteById(id);
        margenNewoGruposSearchRepository.deleteById(id);
    }

    /**
     * Search for the margenNewoGrupos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoGrupos> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MargenNewoGrupos for query {}", query);
        return margenNewoGruposSearchRepository.search(queryStringQuery(query), pageable);    }
}
