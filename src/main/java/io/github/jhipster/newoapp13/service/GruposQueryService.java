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

import io.github.jhipster.newoapp13.domain.Grupos;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.GruposRepository;
import io.github.jhipster.newoapp13.repository.search.GruposSearchRepository;
import io.github.jhipster.newoapp13.service.dto.GruposCriteria;

/**
 * Service for executing complex queries for {@link Grupos} entities in the database.
 * The main input is a {@link GruposCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Grupos} or a {@link Page} of {@link Grupos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GruposQueryService extends QueryService<Grupos> {

    private final Logger log = LoggerFactory.getLogger(GruposQueryService.class);

    private final GruposRepository gruposRepository;

    private final GruposSearchRepository gruposSearchRepository;

    public GruposQueryService(GruposRepository gruposRepository, GruposSearchRepository gruposSearchRepository) {
        this.gruposRepository = gruposRepository;
        this.gruposSearchRepository = gruposSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Grupos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Grupos> findByCriteria(GruposCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Grupos> specification = createSpecification(criteria);
        return gruposRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Grupos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Grupos> findByCriteria(GruposCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Grupos> specification = createSpecification(criteria);
        return gruposRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GruposCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Grupos> specification = createSpecification(criteria);
        return gruposRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Grupos> createSpecification(GruposCriteria criteria) {
        Specification<Grupos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Grupos_.id));
            }
            if (criteria.getNombreGrupo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreGrupo(), Grupos_.nombreGrupo));
            }
            if (criteria.getInstagram() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstagram(), Grupos_.instagram));
            }
            if (criteria.getFacebook() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacebook(), Grupos_.facebook));
            }
            if (criteria.getTwiter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTwiter(), Grupos_.twiter));
            }
            if (criteria.getLinkedIn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkedIn(), Grupos_.linkedIn));
            }
            if (criteria.getTipoGrupo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoGrupo(), Grupos_.tipoGrupo));
            }
            if (criteria.getTipoConsumo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoConsumo(), Grupos_.tipoConsumo));
            }
            if (criteria.getValorSuscripcion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorSuscripcion(), Grupos_.valorSuscripcion));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildSpecification(criteria.getImpuesto(), Grupos_.impuesto));
            }
            if (criteria.getCategoriaGrupoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaGrupoId(),
                    root -> root.join(Grupos_.categoriaGrupo, JoinType.LEFT).get(CategoriaContenidos_.id)));
            }
        }
        return specification;
    }
}
