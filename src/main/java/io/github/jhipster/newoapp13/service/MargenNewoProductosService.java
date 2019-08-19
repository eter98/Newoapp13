package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.MargenNewoProductos;
import io.github.jhipster.newoapp13.repository.MargenNewoProductosRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoProductosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MargenNewoProductos}.
 */
@Service
@Transactional
public class MargenNewoProductosService {

    private final Logger log = LoggerFactory.getLogger(MargenNewoProductosService.class);

    private final MargenNewoProductosRepository margenNewoProductosRepository;

    private final MargenNewoProductosSearchRepository margenNewoProductosSearchRepository;

    public MargenNewoProductosService(MargenNewoProductosRepository margenNewoProductosRepository, MargenNewoProductosSearchRepository margenNewoProductosSearchRepository) {
        this.margenNewoProductosRepository = margenNewoProductosRepository;
        this.margenNewoProductosSearchRepository = margenNewoProductosSearchRepository;
    }

    /**
     * Save a margenNewoProductos.
     *
     * @param margenNewoProductos the entity to save.
     * @return the persisted entity.
     */
    public MargenNewoProductos save(MargenNewoProductos margenNewoProductos) {
        log.debug("Request to save MargenNewoProductos : {}", margenNewoProductos);
        MargenNewoProductos result = margenNewoProductosRepository.save(margenNewoProductos);
        margenNewoProductosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the margenNewoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoProductos> findAll(Pageable pageable) {
        log.debug("Request to get all MargenNewoProductos");
        return margenNewoProductosRepository.findAll(pageable);
    }


    /**
     * Get one margenNewoProductos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MargenNewoProductos> findOne(Long id) {
        log.debug("Request to get MargenNewoProductos : {}", id);
        return margenNewoProductosRepository.findById(id);
    }

    /**
     * Delete the margenNewoProductos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MargenNewoProductos : {}", id);
        margenNewoProductosRepository.deleteById(id);
        margenNewoProductosSearchRepository.deleteById(id);
    }

    /**
     * Search for the margenNewoProductos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoProductos> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MargenNewoProductos for query {}", query);
        return margenNewoProductosSearchRepository.search(queryStringQuery(query), pageable);    }
}
