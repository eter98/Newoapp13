package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Invitados;
import io.github.jhipster.newoapp13.repository.InvitadosRepository;
import io.github.jhipster.newoapp13.repository.search.InvitadosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Invitados}.
 */
@Service
@Transactional
public class InvitadosService {

    private final Logger log = LoggerFactory.getLogger(InvitadosService.class);

    private final InvitadosRepository invitadosRepository;

    private final InvitadosSearchRepository invitadosSearchRepository;

    public InvitadosService(InvitadosRepository invitadosRepository, InvitadosSearchRepository invitadosSearchRepository) {
        this.invitadosRepository = invitadosRepository;
        this.invitadosSearchRepository = invitadosSearchRepository;
    }

    /**
     * Save a invitados.
     *
     * @param invitados the entity to save.
     * @return the persisted entity.
     */
    public Invitados save(Invitados invitados) {
        log.debug("Request to save Invitados : {}", invitados);
        Invitados result = invitadosRepository.save(invitados);
        invitadosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the invitados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Invitados> findAll(Pageable pageable) {
        log.debug("Request to get all Invitados");
        return invitadosRepository.findAll(pageable);
    }


    /**
     * Get one invitados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Invitados> findOne(Long id) {
        log.debug("Request to get Invitados : {}", id);
        return invitadosRepository.findById(id);
    }

    /**
     * Delete the invitados by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Invitados : {}", id);
        invitadosRepository.deleteById(id);
        invitadosSearchRepository.deleteById(id);
    }

    /**
     * Search for the invitados corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Invitados> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Invitados for query {}", query);
        return invitadosSearchRepository.search(queryStringQuery(query), pageable);    }
}
