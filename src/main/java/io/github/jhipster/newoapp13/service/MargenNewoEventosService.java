package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.MargenNewoEventos;
import io.github.jhipster.newoapp13.repository.MargenNewoEventosRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoEventosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MargenNewoEventos}.
 */
@Service
@Transactional
public class MargenNewoEventosService {

    private final Logger log = LoggerFactory.getLogger(MargenNewoEventosService.class);

    private final MargenNewoEventosRepository margenNewoEventosRepository;

    private final MargenNewoEventosSearchRepository margenNewoEventosSearchRepository;

    public MargenNewoEventosService(MargenNewoEventosRepository margenNewoEventosRepository, MargenNewoEventosSearchRepository margenNewoEventosSearchRepository) {
        this.margenNewoEventosRepository = margenNewoEventosRepository;
        this.margenNewoEventosSearchRepository = margenNewoEventosSearchRepository;
    }

    /**
     * Save a margenNewoEventos.
     *
     * @param margenNewoEventos the entity to save.
     * @return the persisted entity.
     */
    public MargenNewoEventos save(MargenNewoEventos margenNewoEventos) {
        log.debug("Request to save MargenNewoEventos : {}", margenNewoEventos);
        MargenNewoEventos result = margenNewoEventosRepository.save(margenNewoEventos);
        margenNewoEventosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the margenNewoEventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoEventos> findAll(Pageable pageable) {
        log.debug("Request to get all MargenNewoEventos");
        return margenNewoEventosRepository.findAll(pageable);
    }


    /**
     * Get one margenNewoEventos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MargenNewoEventos> findOne(Long id) {
        log.debug("Request to get MargenNewoEventos : {}", id);
        return margenNewoEventosRepository.findById(id);
    }

    /**
     * Delete the margenNewoEventos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MargenNewoEventos : {}", id);
        margenNewoEventosRepository.deleteById(id);
        margenNewoEventosSearchRepository.deleteById(id);
    }

    /**
     * Search for the margenNewoEventos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoEventos> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MargenNewoEventos for query {}", query);
        return margenNewoEventosSearchRepository.search(queryStringQuery(query), pageable);    }
}
