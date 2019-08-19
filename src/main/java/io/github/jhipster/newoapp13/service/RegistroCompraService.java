package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.RegistroCompra;
import io.github.jhipster.newoapp13.repository.RegistroCompraRepository;
import io.github.jhipster.newoapp13.repository.search.RegistroCompraSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RegistroCompra}.
 */
@Service
@Transactional
public class RegistroCompraService {

    private final Logger log = LoggerFactory.getLogger(RegistroCompraService.class);

    private final RegistroCompraRepository registroCompraRepository;

    private final RegistroCompraSearchRepository registroCompraSearchRepository;

    public RegistroCompraService(RegistroCompraRepository registroCompraRepository, RegistroCompraSearchRepository registroCompraSearchRepository) {
        this.registroCompraRepository = registroCompraRepository;
        this.registroCompraSearchRepository = registroCompraSearchRepository;
    }

    /**
     * Save a registroCompra.
     *
     * @param registroCompra the entity to save.
     * @return the persisted entity.
     */
    public RegistroCompra save(RegistroCompra registroCompra) {
        log.debug("Request to save RegistroCompra : {}", registroCompra);
        RegistroCompra result = registroCompraRepository.save(registroCompra);
        registroCompraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the registroCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroCompra> findAll(Pageable pageable) {
        log.debug("Request to get all RegistroCompras");
        return registroCompraRepository.findAll(pageable);
    }


    /**
     * Get one registroCompra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegistroCompra> findOne(Long id) {
        log.debug("Request to get RegistroCompra : {}", id);
        return registroCompraRepository.findById(id);
    }

    /**
     * Delete the registroCompra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RegistroCompra : {}", id);
        registroCompraRepository.deleteById(id);
        registroCompraSearchRepository.deleteById(id);
    }

    /**
     * Search for the registroCompra corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroCompra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RegistroCompras for query {}", query);
        return registroCompraSearchRepository.search(queryStringQuery(query), pageable);    }
}
