package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.ProductosServicios;
import io.github.jhipster.newoapp13.repository.ProductosServiciosRepository;
import io.github.jhipster.newoapp13.repository.search.ProductosServiciosSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ProductosServicios}.
 */
@Service
@Transactional
public class ProductosServiciosService {

    private final Logger log = LoggerFactory.getLogger(ProductosServiciosService.class);

    private final ProductosServiciosRepository productosServiciosRepository;

    private final ProductosServiciosSearchRepository productosServiciosSearchRepository;

    public ProductosServiciosService(ProductosServiciosRepository productosServiciosRepository, ProductosServiciosSearchRepository productosServiciosSearchRepository) {
        this.productosServiciosRepository = productosServiciosRepository;
        this.productosServiciosSearchRepository = productosServiciosSearchRepository;
    }

    /**
     * Save a productosServicios.
     *
     * @param productosServicios the entity to save.
     * @return the persisted entity.
     */
    public ProductosServicios save(ProductosServicios productosServicios) {
        log.debug("Request to save ProductosServicios : {}", productosServicios);
        ProductosServicios result = productosServiciosRepository.save(productosServicios);
        productosServiciosSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the productosServicios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductosServicios> findAll(Pageable pageable) {
        log.debug("Request to get all ProductosServicios");
        return productosServiciosRepository.findAll(pageable);
    }


    /**
     * Get one productosServicios by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductosServicios> findOne(Long id) {
        log.debug("Request to get ProductosServicios : {}", id);
        return productosServiciosRepository.findById(id);
    }

    /**
     * Delete the productosServicios by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductosServicios : {}", id);
        productosServiciosRepository.deleteById(id);
        productosServiciosSearchRepository.deleteById(id);
    }

    /**
     * Search for the productosServicios corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductosServicios> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductosServicios for query {}", query);
        return productosServiciosSearchRepository.search(queryStringQuery(query), pageable);    }
}
