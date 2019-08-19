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

import io.github.jhipster.newoapp13.domain.Sedes;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.SedesRepository;
import io.github.jhipster.newoapp13.repository.search.SedesSearchRepository;
import io.github.jhipster.newoapp13.service.dto.SedesCriteria;

/**
 * Service for executing complex queries for {@link Sedes} entities in the database.
 * The main input is a {@link SedesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Sedes} or a {@link Page} of {@link Sedes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SedesQueryService extends QueryService<Sedes> {

    private final Logger log = LoggerFactory.getLogger(SedesQueryService.class);

    private final SedesRepository sedesRepository;

    private final SedesSearchRepository sedesSearchRepository;

    public SedesQueryService(SedesRepository sedesRepository, SedesSearchRepository sedesSearchRepository) {
        this.sedesRepository = sedesRepository;
        this.sedesSearchRepository = sedesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Sedes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Sedes> findByCriteria(SedesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sedes> specification = createSpecification(criteria);
        return sedesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Sedes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Sedes> findByCriteria(SedesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sedes> specification = createSpecification(criteria);
        return sedesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SedesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sedes> specification = createSpecification(criteria);
        return sedesRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Sedes> createSpecification(SedesCriteria criteria) {
        Specification<Sedes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Sedes_.id));
            }
            if (criteria.getNombreSede() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreSede(), Sedes_.nombreSede));
            }
            if (criteria.getCoordenadaX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoordenadaX(), Sedes_.coordenadaX));
            }
            if (criteria.getCoordenadaY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoordenadaY(), Sedes_.coordenadaY));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), Sedes_.direccion));
            }
            if (criteria.getTelefonoComunidad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoComunidad(), Sedes_.telefonoComunidad));
            }
            if (criteria.getTelefonoNegocio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonoNegocio(), Sedes_.telefonoNegocio));
            }
            if (criteria.getHorario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHorario(), Sedes_.horario));
            }
            if (criteria.getVideo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideo(), Sedes_.video));
            }
            if (criteria.getCiudadId() != null) {
                specification = specification.and(buildSpecification(criteria.getCiudadId(),
                    root -> root.join(Sedes_.ciudad, JoinType.LEFT).get(Ciudad_.id)));
            }
        }
        return specification;
    }
}
