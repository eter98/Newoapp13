package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.EntradaInvitados;
import io.github.jhipster.newoapp13.repository.EntradaInvitadosRepository;
import io.github.jhipster.newoapp13.repository.search.EntradaInvitadosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EntradaInvitados}.
 */
@Service
@Transactional
public class EntradaInvitadosService {

    private final Logger log = LoggerFactory.getLogger(EntradaInvitadosService.class);

    private final EntradaInvitadosRepository entradaInvitadosRepository;

    private final EntradaInvitadosSearchRepository entradaInvitadosSearchRepository;

    public EntradaInvitadosService(EntradaInvitadosRepository entradaInvitadosRepository, EntradaInvitadosSearchRepository entradaInvitadosSearchRepository) {
        this.entradaInvitadosRepository = entradaInvitadosRepository;
        this.entradaInvitadosSearchRepository = entradaInvitadosSearchRepository;
    }

    /**
     * Save a entradaInvitados.
     *
     * @param entradaInvitados the entity to save.
     * @return the persisted entity.
     */
    public EntradaInvitados save(EntradaInvitados entradaInvitados) {
        log.debug("Request to save EntradaInvitados : {}", entradaInvitados);
        EntradaInvitados result = entradaInvitadosRepository.save(entradaInvitados);
        entradaInvitadosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the entradaInvitados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaInvitados> findAll(Pageable pageable) {
        log.debug("Request to get all EntradaInvitados");
        return entradaInvitadosRepository.findAll(pageable);
    }


    /**
     * Get one entradaInvitados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntradaInvitados> findOne(Long id) {
        log.debug("Request to get EntradaInvitados : {}", id);
        return entradaInvitadosRepository.findById(id);
    }

    /**
     * Delete the entradaInvitados by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EntradaInvitados : {}", id);
        entradaInvitadosRepository.deleteById(id);
        entradaInvitadosSearchRepository.deleteById(id);
    }

    /**
     * Search for the entradaInvitados corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaInvitados> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EntradaInvitados for query {}", query);
        return entradaInvitadosSearchRepository.search(queryStringQuery(query), pageable);    }
}
