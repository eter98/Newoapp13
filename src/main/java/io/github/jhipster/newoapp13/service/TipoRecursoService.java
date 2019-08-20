package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.TipoRecurso;
import io.github.jhipster.newoapp13.repository.TipoRecursoRepository;
import io.github.jhipster.newoapp13.repository.search.TipoRecursoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoRecurso}.
 */
@Service
@Transactional
public class TipoRecursoService {

    private final Logger log = LoggerFactory.getLogger(TipoRecursoService.class);

    private final TipoRecursoRepository tipoRecursoRepository;

    private final TipoRecursoSearchRepository tipoRecursoSearchRepository;

    public TipoRecursoService(TipoRecursoRepository tipoRecursoRepository, TipoRecursoSearchRepository tipoRecursoSearchRepository) {
        this.tipoRecursoRepository = tipoRecursoRepository;
        this.tipoRecursoSearchRepository = tipoRecursoSearchRepository;
    }

    /**
     * Save a tipoRecurso.
     *
     * @param tipoRecurso the entity to save.
     * @return the persisted entity.
     */
    public TipoRecurso save(TipoRecurso tipoRecurso) {
        log.debug("Request to save TipoRecurso : {}", tipoRecurso);
        TipoRecurso result = tipoRecursoRepository.save(tipoRecurso);
        tipoRecursoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoRecursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoRecurso> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRecursos");
        return tipoRecursoRepository.findAll(pageable);
    }


    /**
     * Get one tipoRecurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoRecurso> findOne(Long id) {
        log.debug("Request to get TipoRecurso : {}", id);
        return tipoRecursoRepository.findById(id);
    }

    /**
     * Delete the tipoRecurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoRecurso : {}", id);
        tipoRecursoRepository.deleteById(id);
        tipoRecursoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoRecurso corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoRecurso> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoRecursos for query {}", query);
        return tipoRecursoSearchRepository.search(queryStringQuery(query), pageable);    }
}
