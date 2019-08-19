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

import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.EspaciosReservaRepository;
import io.github.jhipster.newoapp13.repository.search.EspaciosReservaSearchRepository;
import io.github.jhipster.newoapp13.service.dto.EspaciosReservaCriteria;

/**
 * Service for executing complex queries for {@link EspaciosReserva} entities in the database.
 * The main input is a {@link EspaciosReservaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspaciosReserva} or a {@link Page} of {@link EspaciosReserva} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspaciosReservaQueryService extends QueryService<EspaciosReserva> {

    private final Logger log = LoggerFactory.getLogger(EspaciosReservaQueryService.class);

    private final EspaciosReservaRepository espaciosReservaRepository;

    private final EspaciosReservaSearchRepository espaciosReservaSearchRepository;

    public EspaciosReservaQueryService(EspaciosReservaRepository espaciosReservaRepository, EspaciosReservaSearchRepository espaciosReservaSearchRepository) {
        this.espaciosReservaRepository = espaciosReservaRepository;
        this.espaciosReservaSearchRepository = espaciosReservaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EspaciosReserva} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspaciosReserva> findByCriteria(EspaciosReservaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EspaciosReserva> specification = createSpecification(criteria);
        return espaciosReservaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EspaciosReserva} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspaciosReserva> findByCriteria(EspaciosReservaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EspaciosReserva> specification = createSpecification(criteria);
        return espaciosReservaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspaciosReservaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EspaciosReserva> specification = createSpecification(criteria);
        return espaciosReservaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<EspaciosReserva> createSpecification(EspaciosReservaCriteria criteria) {
        Specification<EspaciosReserva> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EspaciosReserva_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), EspaciosReserva_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), EspaciosReserva_.descripcion));
            }
            if (criteria.getFacilidades() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacilidades(), EspaciosReserva_.facilidades));
            }
            if (criteria.getCapacidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapacidad(), EspaciosReserva_.capacidad));
            }
            if (criteria.getApertura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApertura(), EspaciosReserva_.apertura));
            }
            if (criteria.getCierre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCierre(), EspaciosReserva_.cierre));
            }
            if (criteria.getWifi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWifi(), EspaciosReserva_.wifi));
            }
            if (criteria.getTarifa1Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa1Hora(), EspaciosReserva_.tarifa1Hora));
            }
            if (criteria.getTarifa2Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa2Hora(), EspaciosReserva_.tarifa2Hora));
            }
            if (criteria.getTarifa3Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa3Hora(), EspaciosReserva_.tarifa3Hora));
            }
            if (criteria.getTarifa4Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa4Hora(), EspaciosReserva_.tarifa4Hora));
            }
            if (criteria.getTarifa5Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa5Hora(), EspaciosReserva_.tarifa5Hora));
            }
            if (criteria.getTarifa6Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa6Hora(), EspaciosReserva_.tarifa6Hora));
            }
            if (criteria.getTarifa7Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa7Hora(), EspaciosReserva_.tarifa7Hora));
            }
            if (criteria.getTarifa8Hora() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa8Hora(), EspaciosReserva_.tarifa8Hora));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), EspaciosReserva_.impuesto));
            }
            if (criteria.getSedeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSedeId(),
                    root -> root.join(EspaciosReserva_.sede, JoinType.LEFT).get(Sedes_.id)));
            }
        }
        return specification;
    }
}
