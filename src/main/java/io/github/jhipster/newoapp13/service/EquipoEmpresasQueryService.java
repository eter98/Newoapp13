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

import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.EquipoEmpresasRepository;
import io.github.jhipster.newoapp13.repository.search.EquipoEmpresasSearchRepository;
import io.github.jhipster.newoapp13.service.dto.EquipoEmpresasCriteria;

/**
 * Service for executing complex queries for {@link EquipoEmpresas} entities in the database.
 * The main input is a {@link EquipoEmpresasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EquipoEmpresas} or a {@link Page} of {@link EquipoEmpresas} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EquipoEmpresasQueryService extends QueryService<EquipoEmpresas> {

    private final Logger log = LoggerFactory.getLogger(EquipoEmpresasQueryService.class);

    private final EquipoEmpresasRepository equipoEmpresasRepository;

    private final EquipoEmpresasSearchRepository equipoEmpresasSearchRepository;

    public EquipoEmpresasQueryService(EquipoEmpresasRepository equipoEmpresasRepository, EquipoEmpresasSearchRepository equipoEmpresasSearchRepository) {
        this.equipoEmpresasRepository = equipoEmpresasRepository;
        this.equipoEmpresasSearchRepository = equipoEmpresasSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EquipoEmpresas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EquipoEmpresas> findByCriteria(EquipoEmpresasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EquipoEmpresas> specification = createSpecification(criteria);
        return equipoEmpresasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EquipoEmpresas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EquipoEmpresas> findByCriteria(EquipoEmpresasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EquipoEmpresas> specification = createSpecification(criteria);
        return equipoEmpresasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EquipoEmpresasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EquipoEmpresas> specification = createSpecification(criteria);
        return equipoEmpresasRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<EquipoEmpresas> createSpecification(EquipoEmpresasCriteria criteria) {
        Specification<EquipoEmpresas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EquipoEmpresas_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), EquipoEmpresas_.nombre));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), EquipoEmpresas_.telefono));
            }
            if (criteria.getCorreo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorreo(), EquipoEmpresas_.correo));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), EquipoEmpresas_.direccion));
            }
            if (criteria.getPaginaWeb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaginaWeb(), EquipoEmpresas_.paginaWeb));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(EquipoEmpresas_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(EquipoEmpresas_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
