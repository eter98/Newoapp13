package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.EntradaMiembros;
import io.github.jhipster.newoapp13.repository.EntradaMiembrosRepository;
import io.github.jhipster.newoapp13.repository.search.EntradaMiembrosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EntradaMiembros}.
 */
@Service
@Transactional
public class EntradaMiembrosService {

    private final Logger log = LoggerFactory.getLogger(EntradaMiembrosService.class);

    private final EntradaMiembrosRepository entradaMiembrosRepository;

    private final EntradaMiembrosSearchRepository entradaMiembrosSearchRepository;

    public EntradaMiembrosService(EntradaMiembrosRepository entradaMiembrosRepository, EntradaMiembrosSearchRepository entradaMiembrosSearchRepository) {
        this.entradaMiembrosRepository = entradaMiembrosRepository;
        this.entradaMiembrosSearchRepository = entradaMiembrosSearchRepository;
    }

    /**
     * Save a entradaMiembros.
     *
     * @param entradaMiembros the entity to save.
     * @return the persisted entity.
     */
    public EntradaMiembros save(EntradaMiembros entradaMiembros) {
        log.debug("Request to save EntradaMiembros : {}", entradaMiembros);
        EntradaMiembros result = entradaMiembrosRepository.save(entradaMiembros);
        entradaMiembrosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the entradaMiembros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaMiembros> findAll(Pageable pageable) {
        log.debug("Request to get all EntradaMiembros");
        return entradaMiembrosRepository.findAll(pageable);
    }


    /**
     * Get one entradaMiembros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntradaMiembros> findOne(Long id) {
        log.debug("Request to get EntradaMiembros : {}", id);
        return entradaMiembrosRepository.findById(id);
    }

    /**
     * Delete the entradaMiembros by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EntradaMiembros : {}", id);
        entradaMiembrosRepository.deleteById(id);
        entradaMiembrosSearchRepository.deleteById(id);
    }

    /**
     * Search for the entradaMiembros corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaMiembros> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EntradaMiembros for query {}", query);
        return entradaMiembrosSearchRepository.search(queryStringQuery(query), pageable);    }
}
