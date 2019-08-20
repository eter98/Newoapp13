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

import io.github.jhipster.newoapp13.domain.MargenNewoEventos;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.MargenNewoEventosRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoEventosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.MargenNewoEventosCriteria;

/**
 * Service for executing complex queries for {@link MargenNewoEventos} entities in the database.
 * The main input is a {@link MargenNewoEventosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MargenNewoEventos} or a {@link Page} of {@link MargenNewoEventos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MargenNewoEventosQueryService extends QueryService<MargenNewoEventos> {

    private final Logger log = LoggerFactory.getLogger(MargenNewoEventosQueryService.class);

    private final MargenNewoEventosRepository margenNewoEventosRepository;

    private final MargenNewoEventosSearchRepository margenNewoEventosSearchRepository;

    public MargenNewoEventosQueryService(MargenNewoEventosRepository margenNewoEventosRepository, MargenNewoEventosSearchRepository margenNewoEventosSearchRepository) {
        this.margenNewoEventosRepository = margenNewoEventosRepository;
        this.margenNewoEventosSearchRepository = margenNewoEventosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MargenNewoEventos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MargenNewoEventos> findByCriteria(MargenNewoEventosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MargenNewoEventos> specification = createSpecification(criteria);
        return margenNewoEventosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MargenNewoEventos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoEventos> findByCriteria(MargenNewoEventosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MargenNewoEventos> specification = createSpecification(criteria);
        return margenNewoEventosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MargenNewoEventosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MargenNewoEventos> specification = createSpecification(criteria);
        return margenNewoEventosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<MargenNewoEventos> createSpecification(MargenNewoEventosCriteria criteria) {
        Specification<MargenNewoEventos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MargenNewoEventos_.id));
            }
            if (criteria.getPorcentajeMargen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorcentajeMargen(), MargenNewoEventos_.porcentajeMargen));
            }
            if (criteria.getEventoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEventoId(),
                    root -> root.join(MargenNewoEventos_.evento, JoinType.LEFT).get(Evento_.id)));
            }
        }
        return specification;
    }
}
