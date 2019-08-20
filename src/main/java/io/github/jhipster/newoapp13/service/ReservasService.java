package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Reservas;
import io.github.jhipster.newoapp13.repository.ReservasRepository;
import io.github.jhipster.newoapp13.repository.search.ReservasSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Reservas}.
 */
@Service
@Transactional
public class ReservasService {

    private final Logger log = LoggerFactory.getLogger(ReservasService.class);

    private final ReservasRepository reservasRepository;

    private final ReservasSearchRepository reservasSearchRepository;

    public ReservasService(ReservasRepository reservasRepository, ReservasSearchRepository reservasSearchRepository) {
        this.reservasRepository = reservasRepository;
        this.reservasSearchRepository = reservasSearchRepository;
    }

    /**
     * Save a reservas.
     *
     * @param reservas the entity to save.
     * @return the persisted entity.
     */
    public Reservas save(Reservas reservas) {
        log.debug("Request to save Reservas : {}", reservas);
        Reservas result = reservasRepository.save(reservas);
        reservasSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the reservas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Reservas> findAll(Pageable pageable) {
        log.debug("Request to get all Reservas");
        return reservasRepository.findAll(pageable);
    }


    /**
     * Get one reservas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Reservas> findOne(Long id) {
        log.debug("Request to get Reservas : {}", id);
        return reservasRepository.findById(id);
    }

    /**
     * Delete the reservas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reservas : {}", id);
        reservasRepository.deleteById(id);
        reservasSearchRepository.deleteById(id);
    }

    /**
     * Search for the reservas corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Reservas> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Reservas for query {}", query);
        return reservasSearchRepository.search(queryStringQuery(query), pageable);    }
}
