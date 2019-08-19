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

import io.github.jhipster.newoapp13.domain.RecursosFisicos;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.RecursosFisicosRepository;
import io.github.jhipster.newoapp13.repository.search.RecursosFisicosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.RecursosFisicosCriteria;

/**
 * Service for executing complex queries for {@link RecursosFisicos} entities in the database.
 * The main input is a {@link RecursosFisicosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecursosFisicos} or a {@link Page} of {@link RecursosFisicos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecursosFisicosQueryService extends QueryService<RecursosFisicos> {

    private final Logger log = LoggerFactory.getLogger(RecursosFisicosQueryService.class);

    private final RecursosFisicosRepository recursosFisicosRepository;

    private final RecursosFisicosSearchRepository recursosFisicosSearchRepository;

    public RecursosFisicosQueryService(RecursosFisicosRepository recursosFisicosRepository, RecursosFisicosSearchRepository recursosFisicosSearchRepository) {
        this.recursosFisicosRepository = recursosFisicosRepository;
        this.recursosFisicosSearchRepository = recursosFisicosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RecursosFisicos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecursosFisicos> findByCriteria(RecursosFisicosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecursosFisicos> specification = createSpecification(criteria);
        return recursosFisicosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RecursosFisicos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecursosFisicos> findByCriteria(RecursosFisicosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecursosFisicos> specification = createSpecification(criteria);
        return recursosFisicosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecursosFisicosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecursosFisicos> specification = createSpecification(criteria);
        return recursosFisicosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RecursosFisicos> createSpecification(RecursosFisicosCriteria criteria) {
        Specification<RecursosFisicos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RecursosFisicos_.id));
            }
            if (criteria.getRecurso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecurso(), RecursosFisicos_.recurso));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), RecursosFisicos_.tipo));
            }
            if (criteria.getUnidadMedida() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnidadMedida(), RecursosFisicos_.unidadMedida));
            }
            if (criteria.getValorUnitario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorUnitario(), RecursosFisicos_.valorUnitario));
            }
            if (criteria.getValor1H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor1H(), RecursosFisicos_.valor1H));
            }
            if (criteria.getValor2H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor2H(), RecursosFisicos_.valor2H));
            }
            if (criteria.getValor3H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor3H(), RecursosFisicos_.valor3H));
            }
            if (criteria.getValor4H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor4H(), RecursosFisicos_.valor4H));
            }
            if (criteria.getValor5H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor5H(), RecursosFisicos_.valor5H));
            }
            if (criteria.getValor6H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor6H(), RecursosFisicos_.valor6H));
            }
            if (criteria.getValor7H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor7H(), RecursosFisicos_.valor7H));
            }
            if (criteria.getValor8H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor8H(), RecursosFisicos_.valor8H));
            }
            if (criteria.getValor9H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor9H(), RecursosFisicos_.valor9H));
            }
            if (criteria.getValor10H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor10H(), RecursosFisicos_.valor10H));
            }
            if (criteria.getValor11H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor11H(), RecursosFisicos_.valor11H));
            }
            if (criteria.getValor12H() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor12H(), RecursosFisicos_.valor12H));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), RecursosFisicos_.impuesto));
            }
            if (criteria.getSedeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSedeId(),
                    root -> root.join(RecursosFisicos_.sede, JoinType.LEFT).get(Sedes_.id)));
            }
            if (criteria.getTipoRecursoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoRecursoId(),
                    root -> root.join(RecursosFisicos_.tipoRecurso, JoinType.LEFT).get(TipoRecurso_.id)));
            }
        }
        return specification;
    }
}
