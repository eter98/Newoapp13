package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.repository.MiembrosRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Miembros}.
 */
@Service
@Transactional
public class MiembrosService {

    private final Logger log = LoggerFactory.getLogger(MiembrosService.class);

    private final MiembrosRepository miembrosRepository;

    private final MiembrosSearchRepository miembrosSearchRepository;

    public MiembrosService(MiembrosRepository miembrosRepository, MiembrosSearchRepository miembrosSearchRepository) {
        this.miembrosRepository = miembrosRepository;
        this.miembrosSearchRepository = miembrosSearchRepository;
    }

    /**
     * Save a miembros.
     *
     * @param miembros the entity to save.
     * @return the persisted entity.
     */
    public Miembros save(Miembros miembros) {
        log.debug("Request to save Miembros : {}", miembros);
        Miembros result = miembrosRepository.save(miembros);
        miembrosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the miembros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Miembros> findAll(Pageable pageable) {
        log.debug("Request to get all Miembros");
        return miembrosRepository.findAll(pageable);
    }


    /**
     * Get one miembros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Miembros> findOne(Long id) {
        log.debug("Request to get Miembros : {}", id);
        return miembrosRepository.findById(id);
    }

    /**
     * Delete the miembros by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Miembros : {}", id);
        miembrosRepository.deleteById(id);
        miembrosSearchRepository.deleteById(id);
    }

    /**
     * Search for the miembros corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Miembros> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Miembros for query {}", query);
        return miembrosSearchRepository.search(queryStringQuery(query), pageable);    }
}
