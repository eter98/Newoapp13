package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.ComentarioBlog;
import io.github.jhipster.newoapp13.repository.ComentarioBlogRepository;
import io.github.jhipster.newoapp13.repository.search.ComentarioBlogSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ComentarioBlog}.
 */
@Service
@Transactional
public class ComentarioBlogService {

    private final Logger log = LoggerFactory.getLogger(ComentarioBlogService.class);

    private final ComentarioBlogRepository comentarioBlogRepository;

    private final ComentarioBlogSearchRepository comentarioBlogSearchRepository;

    public ComentarioBlogService(ComentarioBlogRepository comentarioBlogRepository, ComentarioBlogSearchRepository comentarioBlogSearchRepository) {
        this.comentarioBlogRepository = comentarioBlogRepository;
        this.comentarioBlogSearchRepository = comentarioBlogSearchRepository;
    }

    /**
     * Save a comentarioBlog.
     *
     * @param comentarioBlog the entity to save.
     * @return the persisted entity.
     */
    public ComentarioBlog save(ComentarioBlog comentarioBlog) {
        log.debug("Request to save ComentarioBlog : {}", comentarioBlog);
        ComentarioBlog result = comentarioBlogRepository.save(comentarioBlog);
        comentarioBlogSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the comentarioBlogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioBlog> findAll(Pageable pageable) {
        log.debug("Request to get all ComentarioBlogs");
        return comentarioBlogRepository.findAll(pageable);
    }


    /**
     * Get one comentarioBlog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComentarioBlog> findOne(Long id) {
        log.debug("Request to get ComentarioBlog : {}", id);
        return comentarioBlogRepository.findById(id);
    }

    /**
     * Delete the comentarioBlog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComentarioBlog : {}", id);
        comentarioBlogRepository.deleteById(id);
        comentarioBlogSearchRepository.deleteById(id);
    }

    /**
     * Search for the comentarioBlog corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioBlog> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ComentarioBlogs for query {}", query);
        return comentarioBlogSearchRepository.search(queryStringQuery(query), pageable);    }
}
