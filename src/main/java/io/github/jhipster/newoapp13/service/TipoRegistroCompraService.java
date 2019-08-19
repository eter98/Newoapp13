package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import io.github.jhipster.newoapp13.repository.TipoRegistroCompraRepository;
import io.github.jhipster.newoapp13.repository.search.TipoRegistroCompraSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoRegistroCompra}.
 */
@Service
@Transactional
public class TipoRegistroCompraService {

    private final Logger log = LoggerFactory.getLogger(TipoRegistroCompraService.class);

    private final TipoRegistroCompraRepository tipoRegistroCompraRepository;

    private final TipoRegistroCompraSearchRepository tipoRegistroCompraSearchRepository;

    public TipoRegistroCompraService(TipoRegistroCompraRepository tipoRegistroCompraRepository, TipoRegistroCompraSearchRepository tipoRegistroCompraSearchRepository) {
        this.tipoRegistroCompraRepository = tipoRegistroCompraRepository;
        this.tipoRegistroCompraSearchRepository = tipoRegistroCompraSearchRepository;
    }

    /**
     * Save a tipoRegistroCompra.
     *
     * @param tipoRegistroCompra the entity to save.
     * @return the persisted entity.
     */
    public TipoRegistroCompra save(TipoRegistroCompra tipoRegistroCompra) {
        log.debug("Request to save TipoRegistroCompra : {}", tipoRegistroCompra);
        TipoRegistroCompra result = tipoRegistroCompraRepository.save(tipoRegistroCompra);
        tipoRegistroCompraSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoRegistroCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoRegistroCompra> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRegistroCompras");
        return tipoRegistroCompraRepository.findAll(pageable);
    }


    /**
     * Get one tipoRegistroCompra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoRegistroCompra> findOne(Long id) {
        log.debug("Request to get TipoRegistroCompra : {}", id);
        return tipoRegistroCompraRepository.findById(id);
    }

    /**
     * Delete the tipoRegistroCompra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoRegistroCompra : {}", id);
        tipoRegistroCompraRepository.deleteById(id);
        tipoRegistroCompraSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoRegistroCompra corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoRegistroCompra> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoRegistroCompras for query {}", query);
        return tipoRegistroCompraSearchRepository.search(queryStringQuery(query), pageable);    }
}
