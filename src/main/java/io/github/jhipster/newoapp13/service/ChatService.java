package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Chat;
import io.github.jhipster.newoapp13.repository.ChatRepository;
import io.github.jhipster.newoapp13.repository.search.ChatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Chat}.
 */
@Service
@Transactional
public class ChatService {

    private final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatRepository chatRepository;

    private final ChatSearchRepository chatSearchRepository;

    public ChatService(ChatRepository chatRepository, ChatSearchRepository chatSearchRepository) {
        this.chatRepository = chatRepository;
        this.chatSearchRepository = chatSearchRepository;
    }

    /**
     * Save a chat.
     *
     * @param chat the entity to save.
     * @return the persisted entity.
     */
    public Chat save(Chat chat) {
        log.debug("Request to save Chat : {}", chat);
        Chat result = chatRepository.save(chat);
        chatSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the chats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Chat> findAll(Pageable pageable) {
        log.debug("Request to get all Chats");
        return chatRepository.findAll(pageable);
    }


    /**
     * Get one chat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Chat> findOne(Long id) {
        log.debug("Request to get Chat : {}", id);
        return chatRepository.findById(id);
    }

    /**
     * Delete the chat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Chat : {}", id);
        chatRepository.deleteById(id);
        chatSearchRepository.deleteById(id);
    }

    /**
     * Search for the chat corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Chat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Chats for query {}", query);
        return chatSearchRepository.search(queryStringQuery(query), pageable);    }
}
