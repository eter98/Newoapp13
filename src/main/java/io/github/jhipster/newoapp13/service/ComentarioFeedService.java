package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.ComentarioFeed;
import io.github.jhipster.newoapp13.repository.ComentarioFeedRepository;
import io.github.jhipster.newoapp13.repository.search.ComentarioFeedSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ComentarioFeed}.
 */
@Service
@Transactional
public class ComentarioFeedService {

    private final Logger log = LoggerFactory.getLogger(ComentarioFeedService.class);

    private final ComentarioFeedRepository comentarioFeedRepository;

    private final ComentarioFeedSearchRepository comentarioFeedSearchRepository;

    public ComentarioFeedService(ComentarioFeedRepository comentarioFeedRepository, ComentarioFeedSearchRepository comentarioFeedSearchRepository) {
        this.comentarioFeedRepository = comentarioFeedRepository;
        this.comentarioFeedSearchRepository = comentarioFeedSearchRepository;
    }

    /**
     * Save a comentarioFeed.
     *
     * @param comentarioFeed the entity to save.
     * @return the persisted entity.
     */
    public ComentarioFeed save(ComentarioFeed comentarioFeed) {
        log.debug("Request to save ComentarioFeed : {}", comentarioFeed);
        ComentarioFeed result = comentarioFeedRepository.save(comentarioFeed);
        comentarioFeedSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the comentarioFeeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioFeed> findAll(Pageable pageable) {
        log.debug("Request to get all ComentarioFeeds");
        return comentarioFeedRepository.findAll(pageable);
    }


    /**
     * Get one comentarioFeed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComentarioFeed> findOne(Long id) {
        log.debug("Request to get ComentarioFeed : {}", id);
        return comentarioFeedRepository.findById(id);
    }

    /**
     * Delete the comentarioFeed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComentarioFeed : {}", id);
        comentarioFeedRepository.deleteById(id);
        comentarioFeedSearchRepository.deleteById(id);
    }

    /**
     * Search for the comentarioFeed corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioFeed> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ComentarioFeeds for query {}", query);
        return comentarioFeedSearchRepository.search(queryStringQuery(query), pageable);    }
}
