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

import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.CategoriaContenidosRepository;
import io.github.jhipster.newoapp13.repository.search.CategoriaContenidosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.CategoriaContenidosCriteria;

/**
 * Service for executing complex queries for {@link CategoriaContenidos} entities in the database.
 * The main input is a {@link CategoriaContenidosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaContenidos} or a {@link Page} of {@link CategoriaContenidos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaContenidosQueryService extends QueryService<CategoriaContenidos> {

    private final Logger log = LoggerFactory.getLogger(CategoriaContenidosQueryService.class);

    private final CategoriaContenidosRepository categoriaContenidosRepository;

    private final CategoriaContenidosSearchRepository categoriaContenidosSearchRepository;

    public CategoriaContenidosQueryService(CategoriaContenidosRepository categoriaContenidosRepository, CategoriaContenidosSearchRepository categoriaContenidosSearchRepository) {
        this.categoriaContenidosRepository = categoriaContenidosRepository;
        this.categoriaContenidosSearchRepository = categoriaContenidosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CategoriaContenidos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaContenidos> findByCriteria(CategoriaContenidosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaContenidos> specification = createSpecification(criteria);
        return categoriaContenidosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoriaContenidos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaContenidos> findByCriteria(CategoriaContenidosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaContenidos> specification = createSpecification(criteria);
        return categoriaContenidosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaContenidosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaContenidos> specification = createSpecification(criteria);
        return categoriaContenidosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<CategoriaContenidos> createSpecification(CategoriaContenidosCriteria criteria) {
        Specification<CategoriaContenidos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CategoriaContenidos_.id));
            }
            if (criteria.getCategoria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoria(), CategoriaContenidos_.categoria));
            }
        }
        return specification;
    }
}
