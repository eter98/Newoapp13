package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Feed;
import io.github.jhipster.newoapp13.repository.FeedRepository;
import io.github.jhipster.newoapp13.repository.search.FeedSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Feed}.
 */
@Service
@Transactional
public class FeedService {

    private final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final FeedRepository feedRepository;

    private final FeedSearchRepository feedSearchRepository;

    public FeedService(FeedRepository feedRepository, FeedSearchRepository feedSearchRepository) {
        this.feedRepository = feedRepository;
        this.feedSearchRepository = feedSearchRepository;
    }

    /**
     * Save a feed.
     *
     * @param feed the entity to save.
     * @return the persisted entity.
     */
    public Feed save(Feed feed) {
        log.debug("Request to save Feed : {}", feed);
        Feed result = feedRepository.save(feed);
        feedSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the feeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Feed> findAll(Pageable pageable) {
        log.debug("Request to get all Feeds");
        return feedRepository.findAll(pageable);
    }


    /**
     * Get one feed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Feed> findOne(Long id) {
        log.debug("Request to get Feed : {}", id);
        return feedRepository.findById(id);
    }

    /**
     * Delete the feed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Feed : {}", id);
        feedRepository.deleteById(id);
        feedSearchRepository.deleteById(id);
    }

    /**
     * Search for the feed corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Feed> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Feeds for query {}", query);
        return feedSearchRepository.search(queryStringQuery(query), pageable);    }
}
