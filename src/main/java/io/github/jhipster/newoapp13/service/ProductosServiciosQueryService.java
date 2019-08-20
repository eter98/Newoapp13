package io.github.jhipster.newoapp13.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.newoapp13.domain.ProductosServicios;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.ProductosServiciosRepository;
import io.github.jhipster.newoapp13.repository.search.ProductosServiciosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.ProductosServiciosCriteria;

/**
 * Service for executing complex queries for {@link ProductosServicios} entities in the database.
 * The main input is a {@link ProductosServiciosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductosServicios} or a {@link Page} of {@link ProductosServicios} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductosServiciosQueryService extends QueryService<ProductosServicios> {

    private final Logger log = LoggerFactory.getLogger(ProductosServiciosQueryService.class);

    private final ProductosServiciosRepository productosServiciosRepository;

    private final ProductosServiciosSearchRepository productosServiciosSearchRepository;

    public ProductosServiciosQueryService(ProductosServiciosRepository productosServiciosRepository, ProductosServiciosSearchRepository productosServiciosSearchRepository) {
        this.productosServiciosRepository = productosServiciosRepository;
        this.productosServiciosSearchRepository = productosServiciosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProductosServicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductosServicios> findByCriteria(ProductosServiciosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductosServicios> specification = createSpecification(criteria);
        return productosServiciosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductosServicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductosServicios> findByCriteria(ProductosServiciosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductosServicios> specification = createSpecification(criteria);
        return productosServiciosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductosServiciosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductosServicios> specification = createSpecification(criteria);
        return productosServiciosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<ProductosServicios> createSpecification(ProductosServiciosCriteria criteria) {
        Specification<ProductosServicios> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductosServicios_.id));
            }
            if (criteria.getNombreProducto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreProducto(), ProductosServicios_.nombreProducto));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), ProductosServicios_.descripcion));
            }
            if (criteria.getInventariables() != null) {
                specification = specification.and(buildSpecification(criteria.getInventariables(), ProductosServicios_.inventariables));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), ProductosServicios_.valor));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), ProductosServicios_.impuesto));
            }
            if (criteria.getVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideo(), ProductosServicios_.video));
            }
            if (criteria.getWeb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeb(), ProductosServicios_.web));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(ProductosServicios_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
