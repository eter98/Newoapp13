package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Ciudad;
import io.github.jhipster.newoapp13.repository.CiudadRepository;
import io.github.jhipster.newoapp13.repository.search.CiudadSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Ciudad}.
 */
@Service
@Transactional
public class CiudadService {

    private final Logger log = LoggerFactory.getLogger(CiudadService.class);

    private final CiudadRepository ciudadRepository;

    private final CiudadSearchRepository ciudadSearchRepository;

    public CiudadService(CiudadRepository ciudadRepository, CiudadSearchRepository ciudadSearchRepository) {
        this.ciudadRepository = ciudadRepository;
        this.ciudadSearchRepository = ciudadSearchRepository;
    }

    /**
     * Save a ciudad.
     *
     * @param ciudad the entity to save.
     * @return the persisted entity.
     */
    public Ciudad save(Ciudad ciudad) {
        log.debug("Request to save Ciudad : {}", ciudad);
        Ciudad result = ciudadRepository.save(ciudad);
        ciudadSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ciudads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ciudad> findAll(Pageable pageable) {
        log.debug("Request to get all Ciudads");
        return ciudadRepository.findAll(pageable);
    }


    /**
     * Get one ciudad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ciudad> findOne(Long id) {
        log.debug("Request to get Ciudad : {}", id);
        return ciudadRepository.findById(id);
    }

    /**
     * Delete the ciudad by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ciudad : {}", id);
        ciudadRepository.deleteById(id);
        ciudadSearchRepository.deleteById(id);
    }

    /**
     * Search for the ciudad corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ciudad> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ciudads for query {}", query);
        return ciudadSearchRepository.search(queryStringQuery(query), pageable);    }
}
