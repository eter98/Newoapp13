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

import io.github.jhipster.newoapp13.domain.RegistroFacturacion;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.RegistroFacturacionRepository;
import io.github.jhipster.newoapp13.repository.search.RegistroFacturacionSearchRepository;
import io.github.jhipster.newoapp13.service.dto.RegistroFacturacionCriteria;

/**
 * Service for executing complex queries for {@link RegistroFacturacion} entities in the database.
 * The main input is a {@link RegistroFacturacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegistroFacturacion} or a {@link Page} of {@link RegistroFacturacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegistroFacturacionQueryService extends QueryService<RegistroFacturacion> {

    private final Logger log = LoggerFactory.getLogger(RegistroFacturacionQueryService.class);

    private final RegistroFacturacionRepository registroFacturacionRepository;

    private final RegistroFacturacionSearchRepository registroFacturacionSearchRepository;

    public RegistroFacturacionQueryService(RegistroFacturacionRepository registroFacturacionRepository, RegistroFacturacionSearchRepository registroFacturacionSearchRepository) {
        this.registroFacturacionRepository = registroFacturacionRepository;
        this.registroFacturacionSearchRepository = registroFacturacionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RegistroFacturacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegistroFacturacion> findByCriteria(RegistroFacturacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegistroFacturacion> specification = createSpecification(criteria);
        return registroFacturacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RegistroFacturacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroFacturacion> findByCriteria(RegistroFacturacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegistroFacturacion> specification = createSpecification(criteria);
        return registroFacturacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegistroFacturacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegistroFacturacion> specification = createSpecification(criteria);
        return registroFacturacionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RegistroFacturacion> createSpecification(RegistroFacturacionCriteria criteria) {
        Specification<RegistroFacturacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RegistroFacturacion_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), RegistroFacturacion_.valor));
            }
            if (criteria.getFechaRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaRegistro(), RegistroFacturacion_.fechaRegistro));
            }
            if (criteria.getFechaFacturacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaFacturacion(), RegistroFacturacion_.fechaFacturacion));
            }
            if (criteria.getTipoRegistroId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoRegistroId(),
                    root -> root.join(RegistroFacturacion_.tipoRegistro, JoinType.LEFT).get(TipoRegistroCompra_.id)));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(RegistroFacturacion_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
