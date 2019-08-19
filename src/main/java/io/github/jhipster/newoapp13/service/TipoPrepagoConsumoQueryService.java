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

import io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.TipoPrepagoConsumoRepository;
import io.github.jhipster.newoapp13.repository.search.TipoPrepagoConsumoSearchRepository;
import io.github.jhipster.newoapp13.service.dto.TipoPrepagoConsumoCriteria;

/**
 * Service for executing complex queries for {@link TipoPrepagoConsumo} entities in the database.
 * The main input is a {@link TipoPrepagoConsumoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoPrepagoConsumo} or a {@link Page} of {@link TipoPrepagoConsumo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoPrepagoConsumoQueryService extends QueryService<TipoPrepagoConsumo> {

    private final Logger log = LoggerFactory.getLogger(TipoPrepagoConsumoQueryService.class);

    private final TipoPrepagoConsumoRepository tipoPrepagoConsumoRepository;

    private final TipoPrepagoConsumoSearchRepository tipoPrepagoConsumoSearchRepository;

    public TipoPrepagoConsumoQueryService(TipoPrepagoConsumoRepository tipoPrepagoConsumoRepository, TipoPrepagoConsumoSearchRepository tipoPrepagoConsumoSearchRepository) {
        this.tipoPrepagoConsumoRepository = tipoPrepagoConsumoRepository;
        this.tipoPrepagoConsumoSearchRepository = tipoPrepagoConsumoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TipoPrepagoConsumo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoPrepagoConsumo> findByCriteria(TipoPrepagoConsumoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoPrepagoConsumo> specification = createSpecification(criteria);
        return tipoPrepagoConsumoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoPrepagoConsumo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoPrepagoConsumo> findByCriteria(TipoPrepagoConsumoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoPrepagoConsumo> specification = createSpecification(criteria);
        return tipoPrepagoConsumoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoPrepagoConsumoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoPrepagoConsumo> specification = createSpecification(criteria);
        return tipoPrepagoConsumoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<TipoPrepagoConsumo> createSpecification(TipoPrepagoConsumoCriteria criteria) {
        Specification<TipoPrepagoConsumo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoPrepagoConsumo_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), TipoPrepagoConsumo_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), TipoPrepagoConsumo_.descripcion));
            }
            if (criteria.getValorMinimo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorMinimo(), TipoPrepagoConsumo_.valorMinimo));
            }
            if (criteria.getValorMaximo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorMaximo(), TipoPrepagoConsumo_.valorMaximo));
            }
            if (criteria.getTipoBeneficioId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoBeneficioId(),
                    root -> root.join(TipoPrepagoConsumo_.tipoBeneficio, JoinType.LEFT).get(Beneficio_.id)));
            }
        }
        return specification;
    }
}
