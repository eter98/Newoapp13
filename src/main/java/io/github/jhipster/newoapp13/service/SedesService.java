package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.repository.SedesRepository;
import io.github.jhipster.newoapp13.repository.search.SedesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Sedes}.
 */
@Service
@Transactional
public class SedesService {

    private final Logger log = LoggerFactory.getLogger(SedesService.class);

    private final SedesRepository sedesRepository;

    private final SedesSearchRepository sedesSearchRepository;

    public SedesService(SedesRepository sedesRepository, SedesSearchRepository sedesSearchRepository) {
        this.sedesRepository = sedesRepository;
        this.sedesSearchRepository = sedesSearchRepository;
    }

    /**
     * Save a sedes.
     *
     * @param sedes the entity to save.
     * @return the persisted entity.
     */
    public Sedes save(Sedes sedes) {
        log.debug("Request to save Sedes : {}", sedes);
        Sedes result = sedesRepository.save(sedes);
        sedesSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the sedes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Sedes> findAll(Pageable pageable) {
        log.debug("Request to get all Sedes");
        return sedesRepository.findAll(pageable);
    }


    /**
     * Get one sedes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sedes> findOne(Long id) {
        log.debug("Request to get Sedes : {}", id);
        return sedesRepository.findById(id);
    }

    /**
     * Delete the sedes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sedes : {}", id);
        sedesRepository.deleteById(id);
        sedesSearchRepository.deleteById(id);
    }

    /**
     * Search for the sedes corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Sedes> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sedes for query {}", query);
        return sedesSearchRepository.search(queryStringQuery(query), pageable);    }
}
