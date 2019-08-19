package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import io.github.jhipster.newoapp13.repository.EspaciosReservaRepository;
import io.github.jhipster.newoapp13.repository.search.EspaciosReservaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EspaciosReserva}.
 */
@Service
@Transactional
public class EspaciosReservaService {

    private final Logger log = LoggerFactory.getLogger(EspaciosReservaService.class);

    private final EspaciosReservaRepository espaciosReservaRepository;

    private final EspaciosReservaSearchRepository espaciosReservaSearchRepository;

    public EspaciosReservaService(EspaciosReservaRepository espaciosReservaRepository, EspaciosReservaSearchRepository espaciosReservaSearchRepository) {
        this.espaciosReservaRepository = espaciosReservaRepository;
        this.espaciosReservaSearchRepository = espaciosReservaSearchRepository;
    }

    /**
     * Save a espaciosReserva.
     *
     * @param espaciosReserva the entity to save.
     * @return the persisted entity.
     */
    public EspaciosReserva save(EspaciosReserva espaciosReserva) {
        log.debug("Request to save EspaciosReserva : {}", espaciosReserva);
        EspaciosReserva result = espaciosReservaRepository.save(espaciosReserva);
        espaciosReservaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the espaciosReservas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspaciosReserva> findAll(Pageable pageable) {
        log.debug("Request to get all EspaciosReservas");
        return espaciosReservaRepository.findAll(pageable);
    }


    /**
     * Get one espaciosReserva by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EspaciosReserva> findOne(Long id) {
        log.debug("Request to get EspaciosReserva : {}", id);
        return espaciosReservaRepository.findById(id);
    }

    /**
     * Delete the espaciosReserva by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EspaciosReserva : {}", id);
        espaciosReservaRepository.deleteById(id);
        espaciosReservaSearchRepository.deleteById(id);
    }

    /**
     * Search for the espaciosReserva corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspaciosReserva> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EspaciosReservas for query {}", query);
        return espaciosReservaSearchRepository.search(queryStringQuery(query), pageable);    }
}
