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

import io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.MiembrosEquipoEmpresasRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosEquipoEmpresasSearchRepository;
import io.github.jhipster.newoapp13.service.dto.MiembrosEquipoEmpresasCriteria;

/**
 * Service for executing complex queries for {@link MiembrosEquipoEmpresas} entities in the database.
 * The main input is a {@link MiembrosEquipoEmpresasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MiembrosEquipoEmpresas} or a {@link Page} of {@link MiembrosEquipoEmpresas} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MiembrosEquipoEmpresasQueryService extends QueryService<MiembrosEquipoEmpresas> {

    private final Logger log = LoggerFactory.getLogger(MiembrosEquipoEmpresasQueryService.class);

    private final MiembrosEquipoEmpresasRepository miembrosEquipoEmpresasRepository;

    private final MiembrosEquipoEmpresasSearchRepository miembrosEquipoEmpresasSearchRepository;

    public MiembrosEquipoEmpresasQueryService(MiembrosEquipoEmpresasRepository miembrosEquipoEmpresasRepository, MiembrosEquipoEmpresasSearchRepository miembrosEquipoEmpresasSearchRepository) {
        this.miembrosEquipoEmpresasRepository = miembrosEquipoEmpresasRepository;
        this.miembrosEquipoEmpresasSearchRepository = miembrosEquipoEmpresasSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MiembrosEquipoEmpresas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MiembrosEquipoEmpresas> findByCriteria(MiembrosEquipoEmpresasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MiembrosEquipoEmpresas> specification = createSpecification(criteria);
        return miembrosEquipoEmpresasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MiembrosEquipoEmpresas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MiembrosEquipoEmpresas> findByCriteria(MiembrosEquipoEmpresasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MiembrosEquipoEmpresas> specification = createSpecification(criteria);
        return miembrosEquipoEmpresasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MiembrosEquipoEmpresasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MiembrosEquipoEmpresas> specification = createSpecification(criteria);
        return miembrosEquipoEmpresasRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<MiembrosEquipoEmpresas> createSpecification(MiembrosEquipoEmpresasCriteria criteria) {
        Specification<MiembrosEquipoEmpresas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MiembrosEquipoEmpresas_.id));
            }
            if (criteria.getEquipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEquipoId(),
                    root -> root.join(MiembrosEquipoEmpresas_.equipo, JoinType.LEFT).get(EquipoEmpresas_.id)));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(MiembrosEquipoEmpresas_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
