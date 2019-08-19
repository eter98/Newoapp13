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

import io.github.jhipster.newoapp13.domain.PerfilEquipoEmpresa;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.PerfilEquipoEmpresaRepository;
import io.github.jhipster.newoapp13.repository.search.PerfilEquipoEmpresaSearchRepository;
import io.github.jhipster.newoapp13.service.dto.PerfilEquipoEmpresaCriteria;

/**
 * Service for executing complex queries for {@link PerfilEquipoEmpresa} entities in the database.
 * The main input is a {@link PerfilEquipoEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerfilEquipoEmpresa} or a {@link Page} of {@link PerfilEquipoEmpresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilEquipoEmpresaQueryService extends QueryService<PerfilEquipoEmpresa> {

    private final Logger log = LoggerFactory.getLogger(PerfilEquipoEmpresaQueryService.class);

    private final PerfilEquipoEmpresaRepository perfilEquipoEmpresaRepository;

    private final PerfilEquipoEmpresaSearchRepository perfilEquipoEmpresaSearchRepository;

    public PerfilEquipoEmpresaQueryService(PerfilEquipoEmpresaRepository perfilEquipoEmpresaRepository, PerfilEquipoEmpresaSearchRepository perfilEquipoEmpresaSearchRepository) {
        this.perfilEquipoEmpresaRepository = perfilEquipoEmpresaRepository;
        this.perfilEquipoEmpresaSearchRepository = perfilEquipoEmpresaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PerfilEquipoEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerfilEquipoEmpresa> findByCriteria(PerfilEquipoEmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerfilEquipoEmpresa> specification = createSpecification(criteria);
        return perfilEquipoEmpresaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PerfilEquipoEmpresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilEquipoEmpresa> findByCriteria(PerfilEquipoEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerfilEquipoEmpresa> specification = createSpecification(criteria);
        return perfilEquipoEmpresaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilEquipoEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerfilEquipoEmpresa> specification = createSpecification(criteria);
        return perfilEquipoEmpresaRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<PerfilEquipoEmpresa> createSpecification(PerfilEquipoEmpresaCriteria criteria) {
        Specification<PerfilEquipoEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PerfilEquipoEmpresa_.id));
            }
            if (criteria.getConocimientosQueDomina() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConocimientosQueDomina(), PerfilEquipoEmpresa_.conocimientosQueDomina));
            }
            if (criteria.getTemasDeInteres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemasDeInteres(), PerfilEquipoEmpresa_.temasDeInteres));
            }
            if (criteria.getFacebook() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacebook(), PerfilEquipoEmpresa_.facebook));
            }
            if (criteria.getInstagram() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstagram(), PerfilEquipoEmpresa_.instagram));
            }
            if (criteria.getIdGoogle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdGoogle(), PerfilEquipoEmpresa_.idGoogle));
            }
            if (criteria.getTwiter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTwiter(), PerfilEquipoEmpresa_.twiter));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(PerfilEquipoEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
