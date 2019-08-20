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

import io.github.jhipster.newoapp13.domain.Chat;
import io.github.jhipster.newoapp13.domain.*; // for static metamodels
import io.github.jhipster.newoapp13.repository.ChatRepository;
import io.github.jhipster.newoapp13.repository.search.ChatSearchRepository;
import io.github.jhipster.newoapp13.service.dto.ChatCriteria;

/**
 * Service for executing complex queries for {@link Chat} entities in the database.
 * The main input is a {@link ChatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Chat} or a {@link Page} of {@link Chat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChatQueryService extends QueryService<Chat> {

    private final Logger log = LoggerFactory.getLogger(ChatQueryService.class);

    private final ChatRepository chatRepository;

    private final ChatSearchRepository chatSearchRepository;

    public ChatQueryService(ChatRepository chatRepository, ChatSearchRepository chatSearchRepository) {
        this.chatRepository = chatRepository;
        this.chatSearchRepository = chatSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Chat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Chat> findByCriteria(ChatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chat> specification = createSpecification(criteria);
        return chatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Chat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Chat> findByCriteria(ChatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chat> specification = createSpecification(criteria);
        return chatRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chat> specification = createSpecification(criteria);
        return chatRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Chat> createSpecification(ChatCriteria criteria) {
        Specification<Chat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Chat_.id));
            }
            if (criteria.getMensaje() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMensaje(), Chat_.mensaje));
            }
            if (criteria.getSender() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSender(), Chat_.sender));
            }
            if (criteria.getRead() != null) {
                specification = specification.and(buildSpecification(criteria.getRead(), Chat_.read));
            }
            if (criteria.getDelivered() != null) {
                specification = specification.and(buildSpecification(criteria.getDelivered(), Chat_.delivered));
            }
            if (criteria.getSent() != null) {
                specification = specification.and(buildSpecification(criteria.getSent(), Chat_.sent));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Chat_.fecha));
            }
            if (criteria.getChatsListadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getChatsListadoId(),
                    root -> root.join(Chat_.chatsListado, JoinType.LEFT).get(ChatsListado_.id)));
            }
            if (criteria.getDeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeId(),
                    root -> root.join(Chat_.de, JoinType.LEFT).get(Miembros_.id)));
            }
            if (criteria.getParaId() != null) {
                specification = specification.and(buildSpecification(criteria.getParaId(),
                    root -> root.join(Chat_.para, JoinType.LEFT).get(Miembros_.id)));
            }
        }
        return specification;
    }
}
