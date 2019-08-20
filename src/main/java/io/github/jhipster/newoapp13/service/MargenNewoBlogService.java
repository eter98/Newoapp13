package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.MargenNewoBlog;
import io.github.jhipster.newoapp13.repository.MargenNewoBlogRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoBlogSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MargenNewoBlog}.
 */
@Service
@Transactional
public class MargenNewoBlogService {

    private final Logger log = LoggerFactory.getLogger(MargenNewoBlogService.class);

    private final MargenNewoBlogRepository margenNewoBlogRepository;

    private final MargenNewoBlogSearchRepository margenNewoBlogSearchRepository;

    public MargenNewoBlogService(MargenNewoBlogRepository margenNewoBlogRepository, MargenNewoBlogSearchRepository margenNewoBlogSearchRepository) {
        this.margenNewoBlogRepository = margenNewoBlogRepository;
        this.margenNewoBlogSearchRepository = margenNewoBlogSearchRepository;
    }

    /**
     * Save a margenNewoBlog.
     *
     * @param margenNewoBlog the entity to save.
     * @return the persisted entity.
     */
    public MargenNewoBlog save(MargenNewoBlog margenNewoBlog) {
        log.debug("Request to save MargenNewoBlog : {}", margenNewoBlog);
        MargenNewoBlog result = margenNewoBlogRepository.save(margenNewoBlog);
        margenNewoBlogSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the margenNewoBlogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoBlog> findAll(Pageable pageable) {
        log.debug("Request to get all MargenNewoBlogs");
        return margenNewoBlogRepository.findAll(pageable);
    }


    /**
     * Get one margenNewoBlog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MargenNewoBlog> findOne(Long id) {
        log.debug("Request to get MargenNewoBlog : {}", id);
        return margenNewoBlogRepository.findById(id);
    }

    /**
     * Delete the margenNewoBlog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MargenNewoBlog : {}", id);
        margenNewoBlogRepository.deleteById(id);
        margenNewoBlogSearchRepository.deleteById(id);
    }

    /**
     * Search for the margenNewoBlog corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoBlog> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MargenNewoBlogs for query {}", query);
        return margenNewoBlogSearchRepository.search(queryStringQuery(query), pageable);    }
}
