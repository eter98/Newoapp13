package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.CuentaAsociada;
import io.github.jhipster.newoapp13.repository.CuentaAsociadaRepository;
import io.github.jhipster.newoapp13.repository.search.CuentaAsociadaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CuentaAsociada}.
 */
@Service
@Transactional
public class CuentaAsociadaService {

    private final Logger log = LoggerFactory.getLogger(CuentaAsociadaService.class);

    private final CuentaAsociadaRepository cuentaAsociadaRepository;

    private final CuentaAsociadaSearchRepository cuentaAsociadaSearchRepository;

    public CuentaAsociadaService(CuentaAsociadaRepository cuentaAsociadaRepository, CuentaAsociadaSearchRepository cuentaAsociadaSearchRepository) {
        this.cuentaAsociadaRepository = cuentaAsociadaRepository;
        this.cuentaAsociadaSearchRepository = cuentaAsociadaSearchRepository;
    }

    /**
     * Save a cuentaAsociada.
     *
     * @param cuentaAsociada the entity to save.
     * @return the persisted entity.
     */
    public CuentaAsociada save(CuentaAsociada cuentaAsociada) {
        log.debug("Request to save CuentaAsociada : {}", cuentaAsociada);
        CuentaAsociada result = cuentaAsociadaRepository.save(cuentaAsociada);
        cuentaAsociadaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the cuentaAsociadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CuentaAsociada> findAll(Pageable pageable) {
        log.debug("Request to get all CuentaAsociadas");
        return cuentaAsociadaRepository.findAll(pageable);
    }


    /**
     * Get one cuentaAsociada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CuentaAsociada> findOne(Long id) {
        log.debug("Request to get CuentaAsociada : {}", id);
        return cuentaAsociadaRepository.findById(id);
    }

    /**
     * Delete the cuentaAsociada by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CuentaAsociada : {}", id);
        cuentaAsociadaRepository.deleteById(id);
        cuentaAsociadaSearchRepository.deleteById(id);
    }

    /**
     * Search for the cuentaAsociada corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CuentaAsociada> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CuentaAsociadas for query {}", query);
        return cuentaAsociadaSearchRepository.search(queryStringQuery(query), pageable);    }
}
