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

import io.github.jhipster.newoapp13.domain.Feed;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.FeedRepository;
import io.github.jhipster.newoapp13.repository.search.FeedSearchRepository;
import io.github.jhipster.newoapp13.service.dto.FeedCriteria;

/**
 * Service for executing complex queries for {@link Feed} entities in the database.
 * The main input is a {@link FeedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Feed} or a {@link Page} of {@link Feed} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeedQueryService extends QueryService<Feed> {

    private final Logger log = LoggerFactory.getLogger(FeedQueryService.class);

    private final FeedRepository feedRepository;

    private final FeedSearchRepository feedSearchRepository;

    public FeedQueryService(FeedRepository feedRepository, FeedSearchRepository feedSearchRepository) {
        this.feedRepository = feedRepository;
        this.feedSearchRepository = feedSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Feed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Feed> findByCriteria(FeedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Feed> specification = createSpecification(criteria);
        return feedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Feed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Feed> findByCriteria(FeedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Feed> specification = createSpecification(criteria);
        return feedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Feed> specification = createSpecification(criteria);
        return feedRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Feed> createSpecification(FeedCriteria criteria) {
        Specification<Feed> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Feed_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Feed_.titulo));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Feed_.descripcion));
            }
            if (criteria.getTipoContenido() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoContenido(), Feed_.tipoContenido));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Feed_.fecha));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(Feed_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getCategoriaFeedId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaFeedId(),
                    root -> root.join(Feed_.categoriaFeed, JoinType.LEFT).get(CategoriaContenidos_.id)));
            }
        }
        return specification;
    }
}
