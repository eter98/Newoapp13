package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.repository.CategoriaContenidosRepository;
import io.github.jhipster.newoapp13.repository.search.CategoriaContenidosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CategoriaContenidos}.
 */
@Service
@Transactional
public class CategoriaContenidosService {

    private final Logger log = LoggerFactory.getLogger(CategoriaContenidosService.class);

    private final CategoriaContenidosRepository categoriaContenidosRepository;

    private final CategoriaContenidosSearchRepository categoriaContenidosSearchRepository;

    public CategoriaContenidosService(CategoriaContenidosRepository categoriaContenidosRepository, CategoriaContenidosSearchRepository categoriaContenidosSearchRepository) {
        this.categoriaContenidosRepository = categoriaContenidosRepository;
        this.categoriaContenidosSearchRepository = categoriaContenidosSearchRepository;
    }

    /**
     * Save a categoriaContenidos.
     *
     * @param categoriaContenidos the entity to save.
     * @return the persisted entity.
     */
    public CategoriaContenidos save(CategoriaContenidos categoriaContenidos) {
        log.debug("Request to save CategoriaContenidos : {}", categoriaContenidos);
        CategoriaContenidos result = categoriaContenidosRepository.save(categoriaContenidos);
        categoriaContenidosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the categoriaContenidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaContenidos> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaContenidos");
        return categoriaContenidosRepository.findAll(pageable);
    }


    /**
     * Get one categoriaContenidos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaContenidos> findOne(Long id) {
        log.debug("Request to get CategoriaContenidos : {}", id);
        return categoriaContenidosRepository.findById(id);
    }

    /**
     * Delete the categoriaContenidos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaContenidos : {}", id);
        categoriaContenidosRepository.deleteById(id);
        categoriaContenidosSearchRepository.deleteById(id);
    }

    /**
     * Search for the categoriaContenidos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaContenidos> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CategoriaContenidos for query {}", query);
        return categoriaContenidosSearchRepository.search(queryStringQuery(query), pageable);    }
}
