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

import io.github.jhipster.newoapp13.domain.Facturacion;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.FacturacionRepository;
import io.github.jhipster.newoapp13.repository.search.FacturacionSearchRepository;
import io.github.jhipster.newoapp13.service.dto.FacturacionCriteria;

/**
 * Service for executing complex queries for {@link Facturacion} entities in the database.
 * The main input is a {@link FacturacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Facturacion} or a {@link Page} of {@link Facturacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacturacionQueryService extends QueryService<Facturacion> {

    private final Logger log = LoggerFactory.getLogger(FacturacionQueryService.class);

    private final FacturacionRepository facturacionRepository;

    private final FacturacionSearchRepository facturacionSearchRepository;

    public FacturacionQueryService(FacturacionRepository facturacionRepository, FacturacionSearchRepository facturacionSearchRepository) {
        this.facturacionRepository = facturacionRepository;
        this.facturacionSearchRepository = facturacionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Facturacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Facturacion> findByCriteria(FacturacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Facturacion> specification = createSpecification(criteria);
        return facturacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Facturacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Facturacion> findByCriteria(FacturacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Facturacion> specification = createSpecification(criteria);
        return facturacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacturacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Facturacion> specification = createSpecification(criteria);
        return facturacionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Facturacion> createSpecification(FacturacionCriteria criteria) {
        Specification<Facturacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Facturacion_.id));
            }
            if (criteria.getTitularFactura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitularFactura(), Facturacion_.titularFactura));
            }
            if (criteria.getTipoPersona() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoPersona(), Facturacion_.tipoPersona));
            }
            if (criteria.getPeriodicidadFacturacion() != null) {
                specification = specification.and(buildSpecification(criteria.getPeriodicidadFacturacion(), Facturacion_.periodicidadFacturacion));
            }
            if (criteria.getMaximoMonto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaximoMonto(), Facturacion_.maximoMonto));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Facturacion_.valor));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Facturacion_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getIdentificacionId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdentificacionId(),
                    root -> root.join(Facturacion_.identificacion, JoinType.LEFT).get(CuentaAsociada_.id)));
            }
        }
        return specification;
    }
}
