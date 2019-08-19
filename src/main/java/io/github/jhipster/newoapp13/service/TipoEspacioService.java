package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.TipoEspacio;
import io.github.jhipster.newoapp13.repository.TipoEspacioRepository;
import io.github.jhipster.newoapp13.repository.search.TipoEspacioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoEspacio}.
 */
@Service
@Transactional
public class TipoEspacioService {

    private final Logger log = LoggerFactory.getLogger(TipoEspacioService.class);

    private final TipoEspacioRepository tipoEspacioRepository;

    private final TipoEspacioSearchRepository tipoEspacioSearchRepository;

    public TipoEspacioService(TipoEspacioRepository tipoEspacioRepository, TipoEspacioSearchRepository tipoEspacioSearchRepository) {
        this.tipoEspacioRepository = tipoEspacioRepository;
        this.tipoEspacioSearchRepository = tipoEspacioSearchRepository;
    }

    /**
     * Save a tipoEspacio.
     *
     * @param tipoEspacio the entity to save.
     * @return the persisted entity.
     */
    public TipoEspacio save(TipoEspacio tipoEspacio) {
        log.debug("Request to save TipoEspacio : {}", tipoEspacio);
        TipoEspacio result = tipoEspacioRepository.save(tipoEspacio);
        tipoEspacioSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoEspacios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEspacio> findAll(Pageable pageable) {
        log.debug("Request to get all TipoEspacios");
        return tipoEspacioRepository.findAll(pageable);
    }


    /**
     * Get one tipoEspacio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoEspacio> findOne(Long id) {
        log.debug("Request to get TipoEspacio : {}", id);
        return tipoEspacioRepository.findById(id);
    }

    /**
     * Delete the tipoEspacio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoEspacio : {}", id);
        tipoEspacioRepository.deleteById(id);
        tipoEspacioSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoEspacio corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEspacio> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoEspacios for query {}", query);
        return tipoEspacioSearchRepository.search(queryStringQuery(query), pageable);    }
}
