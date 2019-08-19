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

import io.github.jhipster.newoapp13.domain.Reservas;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.ReservasRepository;
import io.github.jhipster.newoapp13.repository.search.ReservasSearchRepository;
import io.github.jhipster.newoapp13.service.dto.ReservasCriteria;

/**
 * Service for executing complex queries for {@link Reservas} entities in the database.
 * The main input is a {@link ReservasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Reservas} or a {@link Page} of {@link Reservas} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReservasQueryService extends QueryService<Reservas> {

    private final Logger log = LoggerFactory.getLogger(ReservasQueryService.class);

    private final ReservasRepository reservasRepository;

    private final ReservasSearchRepository reservasSearchRepository;

    public ReservasQueryService(ReservasRepository reservasRepository, ReservasSearchRepository reservasSearchRepository) {
        this.reservasRepository = reservasRepository;
        this.reservasSearchRepository = reservasSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Reservas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Reservas> findByCriteria(ReservasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reservas> specification = createSpecification(criteria);
        return reservasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Reservas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Reservas> findByCriteria(ReservasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reservas> specification = createSpecification(criteria);
        return reservasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReservasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reservas> specification = createSpecification(criteria);
        return reservasRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Reservas> createSpecification(ReservasCriteria criteria) {
        Specification<Reservas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Reservas_.id));
            }
            if (criteria.getRegistroFechaEntrada() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistroFechaEntrada(), Reservas_.registroFechaEntrada));
            }
            if (criteria.getRegistroFechaSalida() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistroFechaSalida(), Reservas_.registroFechaSalida));
            }
            if (criteria.getEstadoReserva() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoReserva(), Reservas_.estadoReserva));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(Reservas_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getEspacioId() != null) {
                specification = specification.and(buildSpecification(criteria.getEspacioId(),
                    root -> root.join(Reservas_.espacio, JoinType.LEFT).get(EspaciosReserva_.id)));
            }
        }
        return specification;
    }
}
