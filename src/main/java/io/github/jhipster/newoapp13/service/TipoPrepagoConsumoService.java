package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo;
import io.github.jhipster.newoapp13.repository.TipoPrepagoConsumoRepository;
import io.github.jhipster.newoapp13.repository.search.TipoPrepagoConsumoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoPrepagoConsumo}.
 */
@Service
@Transactional
public class TipoPrepagoConsumoService {

    private final Logger log = LoggerFactory.getLogger(TipoPrepagoConsumoService.class);

    private final TipoPrepagoConsumoRepository tipoPrepagoConsumoRepository;

    private final TipoPrepagoConsumoSearchRepository tipoPrepagoConsumoSearchRepository;

    public TipoPrepagoConsumoService(TipoPrepagoConsumoRepository tipoPrepagoConsumoRepository, TipoPrepagoConsumoSearchRepository tipoPrepagoConsumoSearchRepository) {
        this.tipoPrepagoConsumoRepository = tipoPrepagoConsumoRepository;
        this.tipoPrepagoConsumoSearchRepository = tipoPrepagoConsumoSearchRepository;
    }

    /**
     * Save a tipoPrepagoConsumo.
     *
     * @param tipoPrepagoConsumo the entity to save.
     * @return the persisted entity.
     */
    public TipoPrepagoConsumo save(TipoPrepagoConsumo tipoPrepagoConsumo) {
        log.debug("Request to save TipoPrepagoConsumo : {}", tipoPrepagoConsumo);
        TipoPrepagoConsumo result = tipoPrepagoConsumoRepository.save(tipoPrepagoConsumo);
        tipoPrepagoConsumoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoPrepagoConsumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoPrepagoConsumo> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPrepagoConsumos");
        return tipoPrepagoConsumoRepository.findAll(pageable);
    }


    /**
     * Get one tipoPrepagoConsumo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoPrepagoConsumo> findOne(Long id) {
        log.debug("Request to get TipoPrepagoConsumo : {}", id);
        return tipoPrepagoConsumoRepository.findById(id);
    }

    /**
     * Delete the tipoPrepagoConsumo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoPrepagoConsumo : {}", id);
        tipoPrepagoConsumoRepository.deleteById(id);
        tipoPrepagoConsumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoPrepagoConsumo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoPrepagoConsumo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoPrepagoConsumos for query {}", query);
        return tipoPrepagoConsumoSearchRepository.search(queryStringQuery(query), pageable);    }
}
