package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.EspacioLibre;
import io.github.jhipster.newoapp13.repository.EspacioLibreRepository;
import io.github.jhipster.newoapp13.repository.search.EspacioLibreSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EspacioLibre}.
 */
@Service
@Transactional
public class EspacioLibreService {

    private final Logger log = LoggerFactory.getLogger(EspacioLibreService.class);

    private final EspacioLibreRepository espacioLibreRepository;

    private final EspacioLibreSearchRepository espacioLibreSearchRepository;

    public EspacioLibreService(EspacioLibreRepository espacioLibreRepository, EspacioLibreSearchRepository espacioLibreSearchRepository) {
        this.espacioLibreRepository = espacioLibreRepository;
        this.espacioLibreSearchRepository = espacioLibreSearchRepository;
    }

    /**
     * Save a espacioLibre.
     *
     * @param espacioLibre the entity to save.
     * @return the persisted entity.
     */
    public EspacioLibre save(EspacioLibre espacioLibre) {
        log.debug("Request to save EspacioLibre : {}", espacioLibre);
        EspacioLibre result = espacioLibreRepository.save(espacioLibre);
        espacioLibreSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the espacioLibres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspacioLibre> findAll(Pageable pageable) {
        log.debug("Request to get all EspacioLibres");
        return espacioLibreRepository.findAll(pageable);
    }


    /**
     * Get one espacioLibre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EspacioLibre> findOne(Long id) {
        log.debug("Request to get EspacioLibre : {}", id);
        return espacioLibreRepository.findById(id);
    }

    /**
     * Delete the espacioLibre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EspacioLibre : {}", id);
        espacioLibreRepository.deleteById(id);
        espacioLibreSearchRepository.deleteById(id);
    }

    /**
     * Search for the espacioLibre corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspacioLibre> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EspacioLibres for query {}", query);
        return espacioLibreSearchRepository.search(queryStringQuery(query), pageable);    }
}
