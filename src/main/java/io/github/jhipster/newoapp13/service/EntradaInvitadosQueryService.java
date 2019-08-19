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

import io.github.jhipster.newoapp13.domain.EntradaInvitados;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.EntradaInvitadosRepository;
import io.github.jhipster.newoapp13.repository.search.EntradaInvitadosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.EntradaInvitadosCriteria;

/**
 * Service for executing complex queries for {@link EntradaInvitados} entities in the database.
 * The main input is a {@link EntradaInvitadosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EntradaInvitados} or a {@link Page} of {@link EntradaInvitados} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EntradaInvitadosQueryService extends QueryService<EntradaInvitados> {

    private final Logger log = LoggerFactory.getLogger(EntradaInvitadosQueryService.class);

    private final EntradaInvitadosRepository entradaInvitadosRepository;

    private final EntradaInvitadosSearchRepository entradaInvitadosSearchRepository;

    public EntradaInvitadosQueryService(EntradaInvitadosRepository entradaInvitadosRepository, EntradaInvitadosSearchRepository entradaInvitadosSearchRepository) {
        this.entradaInvitadosRepository = entradaInvitadosRepository;
        this.entradaInvitadosSearchRepository = entradaInvitadosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EntradaInvitados} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EntradaInvitados> findByCriteria(EntradaInvitadosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EntradaInvitados> specification = createSpecification(criteria);
        return entradaInvitadosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EntradaInvitados} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaInvitados> findByCriteria(EntradaInvitadosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EntradaInvitados> specification = createSpecification(criteria);
        return entradaInvitadosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EntradaInvitadosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EntradaInvitados> specification = createSpecification(criteria);
        return entradaInvitadosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<EntradaInvitados> createSpecification(EntradaInvitadosCriteria criteria) {
        Specification<EntradaInvitados> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EntradaInvitados_.id));
            }
            if (criteria.getRegistroFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistroFecha(), EntradaInvitados_.registroFecha));
            }
            if (criteria.getTipoEntrada() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEntrada(), EntradaInvitados_.tipoEntrada));
            }
            if (criteria.getTipoIngreso() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoIngreso(), EntradaInvitados_.tipoIngreso));
            }
            if (criteria.getTiempoMaximo() != null) {
                specification = specification.and(buildSpecification(criteria.getTiempoMaximo(), EntradaInvitados_.tiempoMaximo));
            }
            if (criteria.getEspacioId() != null) {
                specification = specification.and(buildSpecification(criteria.getEspacioId(),
                    root -> root.join(EntradaInvitados_.espacio, JoinType.LEFT).get(EspacioLibre_.id)));
            }
            if (criteria.getEspacioReservaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEspacioReservaId(),
                    root -> root.join(EntradaInvitados_.espacioReserva, JoinType.LEFT).get(EspaciosReserva_.id)));
            }
            if (criteria.getInvitadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvitadoId(),
                    root -> root.join(EntradaInvitados_.invitado, JoinType.LEFT).get(Invitados_.id)));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(EntradaInvitados_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
