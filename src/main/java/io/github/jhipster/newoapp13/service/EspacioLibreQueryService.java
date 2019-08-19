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

import io.github.jhipster.newoapp13.domain.EspacioLibre;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.EspacioLibreRepository;
import io.github.jhipster.newoapp13.repository.search.EspacioLibreSearchRepository;
import io.github.jhipster.newoapp13.service.dto.EspacioLibreCriteria;

/**
 * Service for executing complex queries for {@link EspacioLibre} entities in the database.
 * The main input is a {@link EspacioLibreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EspacioLibre} or a {@link Page} of {@link EspacioLibre} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EspacioLibreQueryService extends QueryService<EspacioLibre> {

    private final Logger log = LoggerFactory.getLogger(EspacioLibreQueryService.class);

    private final EspacioLibreRepository espacioLibreRepository;

    private final EspacioLibreSearchRepository espacioLibreSearchRepository;

    public EspacioLibreQueryService(EspacioLibreRepository espacioLibreRepository, EspacioLibreSearchRepository espacioLibreSearchRepository) {
        this.espacioLibreRepository = espacioLibreRepository;
        this.espacioLibreSearchRepository = espacioLibreSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EspacioLibre} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EspacioLibre> findByCriteria(EspacioLibreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EspacioLibre> specification = createSpecification(criteria);
        return espacioLibreRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EspacioLibre} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EspacioLibre> findByCriteria(EspacioLibreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EspacioLibre> specification = createSpecification(criteria);
        return espacioLibreRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EspacioLibreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EspacioLibre> specification = createSpecification(criteria);
        return espacioLibreRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<EspacioLibre> createSpecification(EspacioLibreCriteria criteria) {
        Specification<EspacioLibre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EspacioLibre_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), EspacioLibre_.nombre));
            }
            if (criteria.getCapacidadInstalada() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapacidadInstalada(), EspacioLibre_.capacidadInstalada));
            }
            if (criteria.getWifi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWifi(), EspacioLibre_.wifi));
            }
            if (criteria.getTarifa1hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa1hMiembro(), EspacioLibre_.tarifa1hMiembro));
            }
            if (criteria.getTarifa2hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa2hMiembro(), EspacioLibre_.tarifa2hMiembro));
            }
            if (criteria.getTarifa3hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa3hMiembro(), EspacioLibre_.tarifa3hMiembro));
            }
            if (criteria.getTarifa4hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa4hMiembro(), EspacioLibre_.tarifa4hMiembro));
            }
            if (criteria.getTarifa5hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa5hMiembro(), EspacioLibre_.tarifa5hMiembro));
            }
            if (criteria.getTarifa6hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa6hMiembro(), EspacioLibre_.tarifa6hMiembro));
            }
            if (criteria.getTarifa7hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa7hMiembro(), EspacioLibre_.tarifa7hMiembro));
            }
            if (criteria.getTarifa8hMiembro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa8hMiembro(), EspacioLibre_.tarifa8hMiembro));
            }
            if (criteria.getTarifa1hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa1hInvitado(), EspacioLibre_.tarifa1hInvitado));
            }
            if (criteria.getTarifa2hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa2hInvitado(), EspacioLibre_.tarifa2hInvitado));
            }
            if (criteria.getTarifa3hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa3hInvitado(), EspacioLibre_.tarifa3hInvitado));
            }
            if (criteria.getTarifa4hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa4hInvitado(), EspacioLibre_.tarifa4hInvitado));
            }
            if (criteria.getTarifa5hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa5hInvitado(), EspacioLibre_.tarifa5hInvitado));
            }
            if (criteria.getTarifa6hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa6hInvitado(), EspacioLibre_.tarifa6hInvitado));
            }
            if (criteria.getTarifa7hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa7hInvitado(), EspacioLibre_.tarifa7hInvitado));
            }
            if (criteria.getTarifa8hInvitado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifa8hInvitado(), EspacioLibre_.tarifa8hInvitado));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), EspacioLibre_.impuesto));
            }
            if (criteria.getVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideo(), EspacioLibre_.video));
            }
            if (criteria.getSedeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSedeId(),
                    root -> root.join(EspacioLibre_.sede, JoinType.LEFT).get(Sedes_.id)));
            }
            if (criteria.getTipoEspacioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoEspacioId(),
                    root -> root.join(EspacioLibre_.tipoEspacio, JoinType.LEFT).get(TipoEspacio_.id)));
            }
        }
        return specification;
    }
}
