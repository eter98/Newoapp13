package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.RegistroFacturacion;
import io.github.jhipster.newoapp13.repository.RegistroFacturacionRepository;
import io.github.jhipster.newoapp13.repository.search.RegistroFacturacionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RegistroFacturacion}.
 */
@Service
@Transactional
public class RegistroFacturacionService {

    private final Logger log = LoggerFactory.getLogger(RegistroFacturacionService.class);

    private final RegistroFacturacionRepository registroFacturacionRepository;

    private final RegistroFacturacionSearchRepository registroFacturacionSearchRepository;

    public RegistroFacturacionService(RegistroFacturacionRepository registroFacturacionRepository, RegistroFacturacionSearchRepository registroFacturacionSearchRepository) {
        this.registroFacturacionRepository = registroFacturacionRepository;
        this.registroFacturacionSearchRepository = registroFacturacionSearchRepository;
    }

    /**
     * Save a registroFacturacion.
     *
     * @param registroFacturacion the entity to save.
     * @return the persisted entity.
     */
    public RegistroFacturacion save(RegistroFacturacion registroFacturacion) {
        log.debug("Request to save RegistroFacturacion : {}", registroFacturacion);
        RegistroFacturacion result = registroFacturacionRepository.save(registroFacturacion);
        registroFacturacionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the registroFacturacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroFacturacion> findAll(Pageable pageable) {
        log.debug("Request to get all RegistroFacturacions");
        return registroFacturacionRepository.findAll(pageable);
    }


    /**
     * Get one registroFacturacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegistroFacturacion> findOne(Long id) {
        log.debug("Request to get RegistroFacturacion : {}", id);
        return registroFacturacionRepository.findById(id);
    }

    /**
     * Delete the registroFacturacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RegistroFacturacion : {}", id);
        registroFacturacionRepository.deleteById(id);
        registroFacturacionSearchRepository.deleteById(id);
    }

    /**
     * Search for the registroFacturacion corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroFacturacion> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RegistroFacturacions for query {}", query);
        return registroFacturacionSearchRepository.search(queryStringQuery(query), pageable);    }
}
