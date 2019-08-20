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

import io.github.jhipster.newoapp13.domain.MargenNewoGrupos;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.MargenNewoGruposRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoGruposSearchRepository;
import io.github.jhipster.newoapp13.service.dto.MargenNewoGruposCriteria;

/**
 * Service for executing complex queries for {@link MargenNewoGrupos} entities in the database.
 * The main input is a {@link MargenNewoGruposCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MargenNewoGrupos} or a {@link Page} of {@link MargenNewoGrupos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MargenNewoGruposQueryService extends QueryService<MargenNewoGrupos> {

    private final Logger log = LoggerFactory.getLogger(MargenNewoGruposQueryService.class);

    private final MargenNewoGruposRepository margenNewoGruposRepository;

    private final MargenNewoGruposSearchRepository margenNewoGruposSearchRepository;

    public MargenNewoGruposQueryService(MargenNewoGruposRepository margenNewoGruposRepository, MargenNewoGruposSearchRepository margenNewoGruposSearchRepository) {
        this.margenNewoGruposRepository = margenNewoGruposRepository;
        this.margenNewoGruposSearchRepository = margenNewoGruposSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MargenNewoGrupos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MargenNewoGrupos> findByCriteria(MargenNewoGruposCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MargenNewoGrupos> specification = createSpecification(criteria);
        return margenNewoGruposRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MargenNewoGrupos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoGrupos> findByCriteria(MargenNewoGruposCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MargenNewoGrupos> specification = createSpecification(criteria);
        return margenNewoGruposRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MargenNewoGruposCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MargenNewoGrupos> specification = createSpecification(criteria);
        return margenNewoGruposRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<MargenNewoGrupos> createSpecification(MargenNewoGruposCriteria criteria) {
        Specification<MargenNewoGrupos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MargenNewoGrupos_.id));
            }
            if (criteria.getPorcentajeMargen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorcentajeMargen(), MargenNewoGrupos_.porcentajeMargen));
            }
            if (criteria.getGrupoId() != null) {
                specification = specification.and(buildSpecification(criteria.getGrupoId(),
                    root -> root.join(MargenNewoGrupos_.grupo, JoinType.LEFT).get(Grupos_.id)));
            }
        }
        return specification;
    }
}
