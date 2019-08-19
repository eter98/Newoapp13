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

import io.github.jhipster.newoapp13.domain.ChatsListado;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.ChatsListadoRepository;
import io.github.jhipster.newoapp13.repository.search.ChatsListadoSearchRepository;
import io.github.jhipster.newoapp13.service.dto.ChatsListadoCriteria;

/**
 * Service for executing complex queries for {@link ChatsListado} entities in the database.
 * The main input is a {@link ChatsListadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChatsListado} or a {@link Page} of {@link ChatsListado} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChatsListadoQueryService extends QueryService<ChatsListado> {

    private final Logger log = LoggerFactory.getLogger(ChatsListadoQueryService.class);

    private final ChatsListadoRepository chatsListadoRepository;

    private final ChatsListadoSearchRepository chatsListadoSearchRepository;

    public ChatsListadoQueryService(ChatsListadoRepository chatsListadoRepository, ChatsListadoSearchRepository chatsListadoSearchRepository) {
        this.chatsListadoRepository = chatsListadoRepository;
        this.chatsListadoSearchRepository = chatsListadoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ChatsListado} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChatsListado> findByCriteria(ChatsListadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChatsListado> specification = createSpecification(criteria);
        return chatsListadoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChatsListado} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChatsListado> findByCriteria(ChatsListadoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChatsListado> specification = createSpecification(criteria);
        return chatsListadoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChatsListadoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChatsListado> specification = createSpecification(criteria);
        return chatsListadoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<ChatsListado> createSpecification(ChatsListadoCriteria criteria) {
        Specification<ChatsListado> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChatsListado_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), ChatsListado_.descripcion));
            }
            if (criteria.getEstatus() != null) {
                specification = specification.and(buildSpecification(criteria.getEstatus(), ChatsListado_.estatus));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), ChatsListado_.count));
            }
            if (criteria.getBadge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBadge(), ChatsListado_.badge));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTime(), ChatsListado_.time));
            }
            if (criteria.getSendTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSendTime(), ChatsListado_.sendTime));
            }
            if (criteria.getGrupo() != null) {
                specification = specification.and(buildSpecification(criteria.getGrupo(), ChatsListado_.grupo));
            }
            if (criteria.getPropietarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getPropietarioId(),
                    root -> root.join(ChatsListado_.propietario, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getDestinatarioId() != null) {
                specification = specification.and(buildSpecification(criteria.getDestinatarioId(),
                    root -> root.join(ChatsListado_.destinatario, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getChatId() != null) {
                specification = specification.and(buildSpecification(criteria.getChatId(),
                    root -> root.join(ChatsListado_.chats, JoinType.LEFT).get(Chat_.id)));
            }
        }
        return specification;
    }
}
