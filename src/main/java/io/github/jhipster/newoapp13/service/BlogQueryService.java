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

import io.github.jhipster.newoapp13.domain.Blog;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.BlogRepository;
import io.github.jhipster.newoapp13.repository.search.BlogSearchRepository;
import io.github.jhipster.newoapp13.service.dto.BlogCriteria;

/**
 * Service for executing complex queries for {@link Blog} entities in the database.
 * The main input is a {@link BlogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Blog} or a {@link Page} of {@link Blog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BlogQueryService extends QueryService<Blog> {

    private final Logger log = LoggerFactory.getLogger(BlogQueryService.class);

    private final BlogRepository blogRepository;

    private final BlogSearchRepository blogSearchRepository;

    public BlogQueryService(BlogRepository blogRepository, BlogSearchRepository blogSearchRepository) {
        this.blogRepository = blogRepository;
        this.blogSearchRepository = blogSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Blog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Blog> findByCriteria(BlogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Blog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Blog> findByCriteria(BlogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BlogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Blog> createSpecification(BlogCriteria criteria) {
        Specification<Blog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Blog_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Blog_.titulo));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Blog_.descripcion));
            }
            if (criteria.getTipoContenido() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoContenido(), Blog_.tipoContenido));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Blog_.fecha));
            }
            if (criteria.getVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideo(), Blog_.video));
            }
            if (criteria.getEstadoPublicacion() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoPublicacion(), Blog_.estadoPublicacion));
            }
            if (criteria.getTipoConsumo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoConsumo(), Blog_.tipoConsumo));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Blog_.valor));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), Blog_.impuesto));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(Blog_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getCategoriaBlogId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaBlogId(),
                    root -> root.join(Blog_.categoriaBlog, JoinType.LEFT).get(CategoriaContenidos_.id)));
            }
            if (criteria.getGruposId() != null) {
                specification = specification.and(buildSpecification(criteria.getGruposId(),
                    root -> root.join(Blog_.grupos, JoinType.LEFT).get(Grupos_.id)));
            }
        }
        return specification;
    }
}
