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

import io.github.jhipster.newoapp13.domain.Landing;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.LandingRepository;
import io.github.jhipster.newoapp13.repository.search.LandingSearchRepository;
import io.github.jhipster.newoapp13.service.dto.LandingCriteria;

/**
 * Service for executing complex queries for {@link Landing} entities in the database.
 * The main input is a {@link LandingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Landing} or a {@link Page} of {@link Landing} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LandingQueryService extends QueryService<Landing> {

    private final Logger log = LoggerFactory.getLogger(LandingQueryService.class);

    private final LandingRepository landingRepository;

    private final LandingSearchRepository landingSearchRepository;

    public LandingQueryService(LandingRepository landingRepository, LandingSearchRepository landingSearchRepository) {
        this.landingRepository = landingRepository;
        this.landingSearchRepository = landingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Landing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Landing> findByCriteria(LandingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Landing> specification = createSpecification(criteria);
        return landingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Landing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Landing> findByCriteria(LandingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Landing> specification = createSpecification(criteria);
        return landingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LandingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Landing> specification = createSpecification(criteria);
        return landingRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Landing> createSpecification(LandingCriteria criteria) {
        Specification<Landing> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Landing_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Landing_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Landing_.descripcion));
            }
            if (criteria.getFacilidades() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacilidades(), Landing_.facilidades));
            }
            if (criteria.getTelefonoNegocio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoNegocio(), Landing_.telefonoNegocio));
            }
            if (criteria.getNumeroPuestos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroPuestos(), Landing_.numeroPuestos));
            }
            if (criteria.getTarifaMensual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarifaMensual(), Landing_.tarifaMensual));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), Landing_.impuesto));
            }
            if (criteria.getSedeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSedeId(),
                    root -> root.join(Landing_.sede, JoinType.LEFT).get(Sedes_.id)));
            }
        }
        return specification;
    }
}
