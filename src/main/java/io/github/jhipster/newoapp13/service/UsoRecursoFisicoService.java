package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.UsoRecursoFisico;
import io.github.jhipster.newoapp13.repository.UsoRecursoFisicoRepository;
import io.github.jhipster.newoapp13.repository.search.UsoRecursoFisicoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UsoRecursoFisico}.
 */
@Service
@Transactional
public class UsoRecursoFisicoService {

    private final Logger log = LoggerFactory.getLogger(UsoRecursoFisicoService.class);

    private final UsoRecursoFisicoRepository usoRecursoFisicoRepository;

    private final UsoRecursoFisicoSearchRepository usoRecursoFisicoSearchRepository;

    public UsoRecursoFisicoService(UsoRecursoFisicoRepository usoRecursoFisicoRepository, UsoRecursoFisicoSearchRepository usoRecursoFisicoSearchRepository) {
        this.usoRecursoFisicoRepository = usoRecursoFisicoRepository;
        this.usoRecursoFisicoSearchRepository = usoRecursoFisicoSearchRepository;
    }

    /**
     * Save a usoRecursoFisico.
     *
     * @param usoRecursoFisico the entity to save.
     * @return the persisted entity.
     */
    public UsoRecursoFisico save(UsoRecursoFisico usoRecursoFisico) {
        log.debug("Request to save UsoRecursoFisico : {}", usoRecursoFisico);
        UsoRecursoFisico result = usoRecursoFisicoRepository.save(usoRecursoFisico);
        usoRecursoFisicoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the usoRecursoFisicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsoRecursoFisico> findAll(Pageable pageable) {
        log.debug("Request to get all UsoRecursoFisicos");
        return usoRecursoFisicoRepository.findAll(pageable);
    }


    /**
     * Get one usoRecursoFisico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsoRecursoFisico> findOne(Long id) {
        log.debug("Request to get UsoRecursoFisico : {}", id);
        return usoRecursoFisicoRepository.findById(id);
    }

    /**
     * Delete the usoRecursoFisico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsoRecursoFisico : {}", id);
        usoRecursoFisicoRepository.deleteById(id);
        usoRecursoFisicoSearchRepository.deleteById(id);
    }

    /**
     * Search for the usoRecursoFisico corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsoRecursoFisico> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UsoRecursoFisicos for query {}", query);
        return usoRecursoFisicoSearchRepository.search(queryStringQuery(query), pageable);    }
}
