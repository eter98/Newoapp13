package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.ChatsListado;
import io.github.jhipster.newoapp13.repository.ChatsListadoRepository;
import io.github.jhipster.newoapp13.repository.search.ChatsListadoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ChatsListado}.
 */
@Service
@Transactional
public class ChatsListadoService {

    private final Logger log = LoggerFactory.getLogger(ChatsListadoService.class);

    private final ChatsListadoRepository chatsListadoRepository;

    private final ChatsListadoSearchRepository chatsListadoSearchRepository;

    public ChatsListadoService(ChatsListadoRepository chatsListadoRepository, ChatsListadoSearchRepository chatsListadoSearchRepository) {
        this.chatsListadoRepository = chatsListadoRepository;
        this.chatsListadoSearchRepository = chatsListadoSearchRepository;
    }

    /**
     * Save a chatsListado.
     *
     * @param chatsListado the entity to save.
     * @return the persisted entity.
     */
    public ChatsListado save(ChatsListado chatsListado) {
        log.debug("Request to save ChatsListado : {}", chatsListado);
        ChatsListado result = chatsListadoRepository.save(chatsListado);
        chatsListadoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the chatsListados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChatsListado> findAll(Pageable pageable) {
        log.debug("Request to get all ChatsListados");
        return chatsListadoRepository.findAll(pageable);
    }


    /**
     * Get one chatsListado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChatsListado> findOne(Long id) {
        log.debug("Request to get ChatsListado : {}", id);
        return chatsListadoRepository.findById(id);
    }

    /**
     * Delete the chatsListado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChatsListado : {}", id);
        chatsListadoRepository.deleteById(id);
        chatsListadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the chatsListado corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChatsListado> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChatsListados for query {}", query);
        return chatsListadoSearchRepository.search(queryStringQuery(query), pageable);    }
}
