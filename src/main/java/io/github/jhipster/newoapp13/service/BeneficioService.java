package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Beneficio;
import io.github.jhipster.newoapp13.repository.BeneficioRepository;
import io.github.jhipster.newoapp13.repository.search.BeneficioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Beneficio}.
 */
@Service
@Transactional
public class BeneficioService {

    private final Logger log = LoggerFactory.getLogger(BeneficioService.class);

    private final BeneficioRepository beneficioRepository;

    private final BeneficioSearchRepository beneficioSearchRepository;

    public BeneficioService(BeneficioRepository beneficioRepository, BeneficioSearchRepository beneficioSearchRepository) {
        this.beneficioRepository = beneficioRepository;
        this.beneficioSearchRepository = beneficioSearchRepository;
    }

    /**
     * Save a beneficio.
     *
     * @param beneficio the entity to save.
     * @return the persisted entity.
     */
    public Beneficio save(Beneficio beneficio) {
        log.debug("Request to save Beneficio : {}", beneficio);
        Beneficio result = beneficioRepository.save(beneficio);
        beneficioSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the beneficios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Beneficio> findAll(Pageable pageable) {
        log.debug("Request to get all Beneficios");
        return beneficioRepository.findAll(pageable);
    }


    /**
     * Get one beneficio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Beneficio> findOne(Long id) {
        log.debug("Request to get Beneficio : {}", id);
        return beneficioRepository.findById(id);
    }

    /**
     * Delete the beneficio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Beneficio : {}", id);
        beneficioRepository.deleteById(id);
        beneficioSearchRepository.deleteById(id);
    }

    /**
     * Search for the beneficio corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Beneficio> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Beneficios for query {}", query);
        return beneficioSearchRepository.search(queryStringQuery(query), pageable);    }
}
