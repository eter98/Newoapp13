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

import io.github.jhipster.newoapp13.domain.EntradaMiembros;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.EntradaMiembrosRepository;
import io.github.jhipster.newoapp13.repository.search.EntradaMiembrosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.EntradaMiembrosCriteria;

/**
 * Service for executing complex queries for {@link EntradaMiembros} entities in the database.
 * The main input is a {@link EntradaMiembrosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EntradaMiembros} or a {@link Page} of {@link EntradaMiembros} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EntradaMiembrosQueryService extends QueryService<EntradaMiembros> {

    private final Logger log = LoggerFactory.getLogger(EntradaMiembrosQueryService.class);

    private final EntradaMiembrosRepository entradaMiembrosRepository;

    private final EntradaMiembrosSearchRepository entradaMiembrosSearchRepository;

    public EntradaMiembrosQueryService(EntradaMiembrosRepository entradaMiembrosRepository, EntradaMiembrosSearchRepository entradaMiembrosSearchRepository) {
        this.entradaMiembrosRepository = entradaMiembrosRepository;
        this.entradaMiembrosSearchRepository = entradaMiembrosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EntradaMiembros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EntradaMiembros> findByCriteria(EntradaMiembrosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EntradaMiembros> specification = createSpecification(criteria);
        return entradaMiembrosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EntradaMiembros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EntradaMiembros> findByCriteria(EntradaMiembrosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EntradaMiembros> specification = createSpecification(criteria);
        return entradaMiembrosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EntradaMiembrosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EntradaMiembros> specification = createSpecification(criteria);
        return entradaMiembrosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<EntradaMiembros> createSpecification(EntradaMiembrosCriteria criteria) {
        Specification<EntradaMiembros> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EntradaMiembros_.id));
            }
            if (criteria.getRegistroFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistroFecha(), EntradaMiembros_.registroFecha));
            }
            if (criteria.getTipoEntrada() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEntrada(), EntradaMiembros_.tipoEntrada));
            }
            if (criteria.getTipoIngreso() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoIngreso(), EntradaMiembros_.tipoIngreso));
            }
            if (criteria.getTiempoMaximo() != null) {
                specification = specification.and(buildSpecification(criteria.getTiempoMaximo(), EntradaMiembros_.tiempoMaximo));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(EntradaMiembros_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getEspacioId() != null) {
                specification = specification.and(buildSpecification(criteria.getEspacioId(),
                    root -> root.join(EntradaMiembros_.espacio, JoinType.LEFT).get(EspacioLibre_.id)));
            }
            if (criteria.getOficinaId() != null) {
                specification = specification.and(buildSpecification(criteria.getOficinaId(),
                    root -> root.join(EntradaMiembros_.oficina, JoinType.LEFT).get(Landing_.id)));
            }
            if (criteria.getEspacioReservaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEspacioReservaId(),
                    root -> root.join(EntradaMiembros_.espacioReserva, JoinType.LEFT).get(EspaciosReserva_.id)));
            }
        }
        return specification;
    }
}
