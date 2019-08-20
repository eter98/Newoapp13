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

import io.github.jhipster.newoapp13.domain.Miembros;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.MiembrosRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosSearchRepository;
import io.github.jhipster.newoapp13.service.dto.MiembrosCriteria;

/**
 * Service for executing complex queries for {@link Miembros} entities in the database.
 * The main input is a {@link MiembrosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Miembros} or a {@link Page} of {@link Miembros} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MiembrosQueryService extends QueryService<Miembros> {

    private final Logger log = LoggerFactory.getLogger(MiembrosQueryService.class);

    private final MiembrosRepository miembrosRepository;

    private final MiembrosSearchRepository miembrosSearchRepository;

    public MiembrosQueryService(MiembrosRepository miembrosRepository, MiembrosSearchRepository miembrosSearchRepository) {
        this.miembrosRepository = miembrosRepository;
        this.miembrosSearchRepository = miembrosSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Miembros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Miembros> findByCriteria(MiembrosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Miembros> specification = createSpecification(criteria);
        return miembrosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Miembros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Miembros> findByCriteria(MiembrosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Miembros> specification = createSpecification(criteria);
        return miembrosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MiembrosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Miembros> specification = createSpecification(criteria);
        return miembrosRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Miembros> createSpecification(MiembrosCriteria criteria) {
        Specification<Miembros> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Miembros_.id));
            }
            if (criteria.getTipoDocumento() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoDocumento(), Miembros_.tipoDocumento));
            }
            if (criteria.getIdentificacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdentificacion(), Miembros_.identificacion));
            }
            if (criteria.getFechaNacimiento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaNacimiento(), Miembros_.fechaNacimiento));
            }
            if (criteria.getFechaRegistro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaRegistro(), Miembros_.fechaRegistro));
            }
            if (criteria.getGenero() != null) {
                specification = specification.and(buildSpecification(criteria.getGenero(), Miembros_.genero));
            }
            if (criteria.getCelular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCelular(), Miembros_.celular));
            }
            if (criteria.getConocimientosQueDomina() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConocimientosQueDomina(), Miembros_.conocimientosQueDomina));
            }
            if (criteria.getTemasDeInteres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemasDeInteres(), Miembros_.temasDeInteres));
            }
            if (criteria.getFacebook() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacebook(), Miembros_.facebook));
            }
            if (criteria.getInstagram() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstagram(), Miembros_.instagram));
            }
            if (criteria.getIdGoogle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdGoogle(), Miembros_.idGoogle));
            }
            if (criteria.getTwiter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTwiter(), Miembros_.twiter));
            }
            if (criteria.getDerechosDeCompra() != null) {
                specification = specification.and(buildSpecification(criteria.getDerechosDeCompra(), Miembros_.derechosDeCompra));
            }
            if (criteria.getAccesoIlimitado() != null) {
                specification = specification.and(buildSpecification(criteria.getAccesoIlimitado(), Miembros_.accesoIlimitado));
            }
            if (criteria.getAliado() != null) {
                specification = specification.and(buildSpecification(criteria.getAliado(), Miembros_.aliado));
            }
            if (criteria.getHost() != null) {
                specification = specification.and(buildSpecification(criteria.getHost(), Miembros_.host));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(Miembros_.miembro, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getNacionalidadId() != null) {
                specification = specification.and(buildSpecification(criteria.getNacionalidadId(),
                    root -> root.join(Miembros_.nacionalidad, JoinType.LEFT).get(Pais_.id)));
            }
        }
        return specification;
    }
}
