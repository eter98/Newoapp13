package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Pais;
import io.github.jhipster.newoapp13.repository.PaisRepository;
import io.github.jhipster.newoapp13.repository.search.PaisSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Pais}.
 */
@Service
@Transactional
public class PaisService {

    private final Logger log = LoggerFactory.getLogger(PaisService.class);

    private final PaisRepository paisRepository;

    private final PaisSearchRepository paisSearchRepository;

    public PaisService(PaisRepository paisRepository, PaisSearchRepository paisSearchRepository) {
        this.paisRepository = paisRepository;
        this.paisSearchRepository = paisSearchRepository;
    }

    /**
     * Save a pais.
     *
     * @param pais the entity to save.
     * @return the persisted entity.
     */
    public Pais save(Pais pais) {
        log.debug("Request to save Pais : {}", pais);
        Pais result = paisRepository.save(pais);
        paisSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pais.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Pais> findAll(Pageable pageable) {
        log.debug("Request to get all Pais");
        return paisRepository.findAll(pageable);
    }


    /**
     * Get one pais by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Pais> findOne(Long id) {
        log.debug("Request to get Pais : {}", id);
        return paisRepository.findById(id);
    }

    /**
     * Delete the pais by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pais : {}", id);
        paisRepository.deleteById(id);
        paisSearchRepository.deleteById(id);
    }

    /**
     * Search for the pais corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Pais> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pais for query {}", query);
        return paisSearchRepository.search(queryStringQuery(query), pageable);    }
}
