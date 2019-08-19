package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Facturacion;
import io.github.jhipster.newoapp13.repository.FacturacionRepository;
import io.github.jhipster.newoapp13.repository.search.FacturacionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Facturacion}.
 */
@Service
@Transactional
public class FacturacionService {

    private final Logger log = LoggerFactory.getLogger(FacturacionService.class);

    private final FacturacionRepository facturacionRepository;

    private final FacturacionSearchRepository facturacionSearchRepository;

    public FacturacionService(FacturacionRepository facturacionRepository, FacturacionSearchRepository facturacionSearchRepository) {
        this.facturacionRepository = facturacionRepository;
        this.facturacionSearchRepository = facturacionSearchRepository;
    }

    /**
     * Save a facturacion.
     *
     * @param facturacion the entity to save.
     * @return the persisted entity.
     */
    public Facturacion save(Facturacion facturacion) {
        log.debug("Request to save Facturacion : {}", facturacion);
        Facturacion result = facturacionRepository.save(facturacion);
        facturacionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the facturacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Facturacion> findAll(Pageable pageable) {
        log.debug("Request to get all Facturacions");
        return facturacionRepository.findAll(pageable);
    }


    /**
     * Get one facturacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Facturacion> findOne(Long id) {
        log.debug("Request to get Facturacion : {}", id);
        return facturacionRepository.findById(id);
    }

    /**
     * Delete the facturacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Facturacion : {}", id);
        facturacionRepository.deleteById(id);
        facturacionSearchRepository.deleteById(id);
    }

    /**
     * Search for the facturacion corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Facturacion> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Facturacions for query {}", query);
        return facturacionSearchRepository.search(queryStringQuery(query), pageable);    }
}
