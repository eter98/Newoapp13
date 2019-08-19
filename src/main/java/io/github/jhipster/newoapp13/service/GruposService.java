package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.repository.GruposRepository;
import io.github.jhipster.newoapp13.repository.search.GruposSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Grupos}.
 */
@Service
@Transactional
public class GruposService {

    private final Logger log = LoggerFactory.getLogger(GruposService.class);

    private final GruposRepository gruposRepository;

    private final GruposSearchRepository gruposSearchRepository;

    public GruposService(GruposRepository gruposRepository, GruposSearchRepository gruposSearchRepository) {
        this.gruposRepository = gruposRepository;
        this.gruposSearchRepository = gruposSearchRepository;
    }

    /**
     * Save a grupos.
     *
     * @param grupos the entity to save.
     * @return the persisted entity.
     */
    public Grupos save(Grupos grupos) {
        log.debug("Request to save Grupos : {}", grupos);
        Grupos result = gruposRepository.save(grupos);
        gruposSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the grupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Grupos> findAll(Pageable pageable) {
        log.debug("Request to get all Grupos");
        return gruposRepository.findAll(pageable);
    }


    /**
     * Get one grupos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Grupos> findOne(Long id) {
        log.debug("Request to get Grupos : {}", id);
        return gruposRepository.findById(id);
    }

    /**
     * Delete the grupos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Grupos : {}", id);
        gruposRepository.deleteById(id);
        gruposSearchRepository.deleteById(id);
    }

    /**
     * Search for the grupos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Grupos> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Grupos for query {}", query);
        return gruposSearchRepository.search(queryStringQuery(query), pageable);    }
}
