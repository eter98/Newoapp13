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

import io.github.jhipster.newoapp13.domain.TipoEspacio;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.TipoEspacioRepository;
import io.github.jhipster.newoapp13.repository.search.TipoEspacioSearchRepository;
import io.github.jhipster.newoapp13.service.dto.TipoEspacioCriteria;

/**
 * Service for executing complex queries for {@link TipoEspacio} entities in the database.
 * The main input is a {@link TipoEspacioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoEspacio} or a {@link Page} of {@link TipoEspacio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoEspacioQueryService extends QueryService<TipoEspacio> {

    private final Logger log = LoggerFactory.getLogger(TipoEspacioQueryService.class);

    private final TipoEspacioRepository tipoEspacioRepository;

    private final TipoEspacioSearchRepository tipoEspacioSearchRepository;

    public TipoEspacioQueryService(TipoEspacioRepository tipoEspacioRepository, TipoEspacioSearchRepository tipoEspacioSearchRepository) {
        this.tipoEspacioRepository = tipoEspacioRepository;
        this.tipoEspacioSearchRepository = tipoEspacioSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TipoEspacio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoEspacio> findByCriteria(TipoEspacioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoEspacio> specification = createSpecification(criteria);
        return tipoEspacioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoEspacio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoEspacio> findByCriteria(TipoEspacioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoEspacio> specification = createSpecification(criteria);
        return tipoEspacioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoEspacioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoEspacio> specification = createSpecification(criteria);
        return tipoEspacioRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<TipoEspacio> createSpecification(TipoEspacioCriteria criteria) {
        Specification<TipoEspacio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoEspacio_.id));
            }
            if (criteria.getTipoEspacio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoEspacio(), TipoEspacio_.tipoEspacio));
            }
        }
        return specification;
    }
}
