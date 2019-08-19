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

import io.github.jhipster.newoapp13.domain.RegistroCompra;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.RegistroCompraRepository;
import io.github.jhipster.newoapp13.repository.search.RegistroCompraSearchRepository;
import io.github.jhipster.newoapp13.service.dto.RegistroCompraCriteria;

/**
 * Service for executing complex queries for {@link RegistroCompra} entities in the database.
 * The main input is a {@link RegistroCompraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegistroCompra} or a {@link Page} of {@link RegistroCompra} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegistroCompraQueryService extends QueryService<RegistroCompra> {

    private final Logger log = LoggerFactory.getLogger(RegistroCompraQueryService.class);

    private final RegistroCompraRepository registroCompraRepository;

    private final RegistroCompraSearchRepository registroCompraSearchRepository;

    public RegistroCompraQueryService(RegistroCompraRepository registroCompraRepository, RegistroCompraSearchRepository registroCompraSearchRepository) {
        this.registroCompraRepository = registroCompraRepository;
        this.registroCompraSearchRepository = registroCompraSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RegistroCompra} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegistroCompra> findByCriteria(RegistroCompraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegistroCompra> specification = createSpecification(criteria);
        return registroCompraRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RegistroCompra} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroCompra> findByCriteria(RegistroCompraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegistroCompra> specification = createSpecification(criteria);
        return registroCompraRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegistroCompraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegistroCompra> specification = createSpecification(criteria);
        return registroCompraRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<RegistroCompra> createSpecification(RegistroCompraCriteria criteria) {
        Specification<RegistroCompra> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RegistroCompra_.id));
            }
            if (criteria.getConsumoMarket() != null) {
                specification = specification.and(buildSpecification(criteria.getConsumoMarket(), RegistroCompra_.consumoMarket));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), RegistroCompra_.valor));
            }
            if (criteria.getMiembroId() != null) {
                specification = specification.and(buildSpecification(criteria.getMiembroId(),
                    root -> root.join(RegistroCompra_.miembro, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
