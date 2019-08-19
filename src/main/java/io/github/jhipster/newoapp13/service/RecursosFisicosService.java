package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.RecursosFisicos;
import io.github.jhipster.newoapp13.repository.RecursosFisicosRepository;
import io.github.jhipster.newoapp13.repository.search.RecursosFisicosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RecursosFisicos}.
 */
@Service
@Transactional
public class RecursosFisicosService {

    private final Logger log = LoggerFactory.getLogger(RecursosFisicosService.class);

    private final RecursosFisicosRepository recursosFisicosRepository;

    private final RecursosFisicosSearchRepository recursosFisicosSearchRepository;

    public RecursosFisicosService(RecursosFisicosRepository recursosFisicosRepository, RecursosFisicosSearchRepository recursosFisicosSearchRepository) {
        this.recursosFisicosRepository = recursosFisicosRepository;
        this.recursosFisicosSearchRepository = recursosFisicosSearchRepository;
    }

    /**
     * Save a recursosFisicos.
     *
     * @param recursosFisicos the entity to save.
     * @return the persisted entity.
     */
    public RecursosFisicos save(RecursosFisicos recursosFisicos) {
        log.debug("Request to save RecursosFisicos : {}", recursosFisicos);
        RecursosFisicos result = recursosFisicosRepository.save(recursosFisicos);
        recursosFisicosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the recursosFisicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecursosFisicos> findAll(Pageable pageable) {
        log.debug("Request to get all RecursosFisicos");
        return recursosFisicosRepository.findAll(pageable);
    }


    /**
     * Get one recursosFisicos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecursosFisicos> findOne(Long id) {
        log.debug("Request to get RecursosFisicos : {}", id);
        return recursosFisicosRepository.findById(id);
    }

    /**
     * Delete the recursosFisicos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecursosFisicos : {}", id);
        recursosFisicosRepository.deleteById(id);
        recursosFisicosSearchRepository.deleteById(id);
    }

    /**
     * Search for the recursosFisicos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecursosFisicos> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RecursosFisicos for query {}", query);
        return recursosFisicosSearchRepository.search(queryStringQuery(query), pageable);    }
}
