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

import io.github.jhipster.newoapp13.domain.MargenNewoBlog;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.MargenNewoBlogRepository;
import io.github.jhipster.newoapp13.repository.search.MargenNewoBlogSearchRepository;
import io.github.jhipster.newoapp13.service.dto.MargenNewoBlogCriteria;

/**
 * Service for executing complex queries for {@link MargenNewoBlog} entities in the database.
 * The main input is a {@link MargenNewoBlogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MargenNewoBlog} or a {@link Page} of {@link MargenNewoBlog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MargenNewoBlogQueryService extends QueryService<MargenNewoBlog> {

    private final Logger log = LoggerFactory.getLogger(MargenNewoBlogQueryService.class);

    private final MargenNewoBlogRepository margenNewoBlogRepository;

    private final MargenNewoBlogSearchRepository margenNewoBlogSearchRepository;

    public MargenNewoBlogQueryService(MargenNewoBlogRepository margenNewoBlogRepository, MargenNewoBlogSearchRepository margenNewoBlogSearchRepository) {
        this.margenNewoBlogRepository = margenNewoBlogRepository;
        this.margenNewoBlogSearchRepository = margenNewoBlogSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MargenNewoBlog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MargenNewoBlog> findByCriteria(MargenNewoBlogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MargenNewoBlog> specification = createSpecification(criteria);
        return margenNewoBlogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MargenNewoBlog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MargenNewoBlog> findByCriteria(MargenNewoBlogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MargenNewoBlog> specification = createSpecification(criteria);
        return margenNewoBlogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MargenNewoBlogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MargenNewoBlog> specification = createSpecification(criteria);
        return margenNewoBlogRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<MargenNewoBlog> createSpecification(MargenNewoBlogCriteria criteria) {
        Specification<MargenNewoBlog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MargenNewoBlog_.id));
            }
            if (criteria.getPorcentajeMargen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorcentajeMargen(), MargenNewoBlog_.porcentajeMargen));
            }
            if (criteria.getBlogId() != null) {
                specification = specification.and(buildSpecification(criteria.getBlogId(),
                    root -> root.join(MargenNewoBlog_.blog, JoinType.LEFT).get(Blog_.id)));
            }
        }
        return specification;
    }
}
