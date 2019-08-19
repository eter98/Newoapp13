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

import io.github.jhipster.newoapp13.domain.Beneficio;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.BeneficioRepository;
import io.github.jhipster.newoapp13.repository.search.BeneficioSearchRepository;
import io.github.jhipster.newoapp13.service.dto.BeneficioCriteria;

/**
 * Service for executing complex queries for {@link Beneficio} entities in the database.
 * The main input is a {@link BeneficioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Beneficio} or a {@link Page} of {@link Beneficio} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BeneficioQueryService extends QueryService<Beneficio> {

    private final Logger log = LoggerFactory.getLogger(BeneficioQueryService.class);

    private final BeneficioRepository beneficioRepository;

    private final BeneficioSearchRepository beneficioSearchRepository;

    public BeneficioQueryService(BeneficioRepository beneficioRepository, BeneficioSearchRepository beneficioSearchRepository) {
        this.beneficioRepository = beneficioRepository;
        this.beneficioSearchRepository = beneficioSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Beneficio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Beneficio> findByCriteria(BeneficioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Beneficio> specification = createSpecification(criteria);
        return beneficioRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Beneficio} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Beneficio> findByCriteria(BeneficioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Beneficio> specification = createSpecification(criteria);
        return beneficioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BeneficioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Beneficio> specification = createSpecification(criteria);
        return beneficioRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Beneficio> createSpecification(BeneficioCriteria criteria) {
        Specification<Beneficio> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Beneficio_.id));
            }
            if (criteria.getTipoBeneficio() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoBeneficio(), Beneficio_.tipoBeneficio));
            }
            if (criteria.getDescuento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDescuento(), Beneficio_.descuento));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(Beneficio_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
