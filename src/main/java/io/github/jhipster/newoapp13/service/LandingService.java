package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Landing;
import io.github.jhipster.newoapp13.repository.LandingRepository;
import io.github.jhipster.newoapp13.repository.search.LandingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Landing}.
 */
@Service
@Transactional
public class LandingService {

    private final Logger log = LoggerFactory.getLogger(LandingService.class);

    private final LandingRepository landingRepository;

    private final LandingSearchRepository landingSearchRepository;

    public LandingService(LandingRepository landingRepository, LandingSearchRepository landingSearchRepository) {
        this.landingRepository = landingRepository;
        this.landingSearchRepository = landingSearchRepository;
    }

    /**
     * Save a landing.
     *
     * @param landing the entity to save.
     * @return the persisted entity.
     */
    public Landing save(Landing landing) {
        log.debug("Request to save Landing : {}", landing);
        Landing result = landingRepository.save(landing);
        landingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the landings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Landing> findAll(Pageable pageable) {
        log.debug("Request to get all Landings");
        return landingRepository.findAll(pageable);
    }


    /**
     * Get one landing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Landing> findOne(Long id) {
        log.debug("Request to get Landing : {}", id);
        return landingRepository.findById(id);
    }

    /**
     * Delete the landing by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Landing : {}", id);
        landingRepository.deleteById(id);
        landingSearchRepository.deleteById(id);
    }

    /**
     * Search for the landing corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Landing> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Landings for query {}", query);
        return landingSearchRepository.search(queryStringQuery(query), pageable);    }
}
