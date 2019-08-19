package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.HostSede;
import io.github.jhipster.newoapp13.repository.HostSedeRepository;
import io.github.jhipster.newoapp13.repository.search.HostSedeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HostSede}.
 */
@Service
@Transactional
public class HostSedeService {

    private final Logger log = LoggerFactory.getLogger(HostSedeService.class);

    private final HostSedeRepository hostSedeRepository;

    private final HostSedeSearchRepository hostSedeSearchRepository;

    public HostSedeService(HostSedeRepository hostSedeRepository, HostSedeSearchRepository hostSedeSearchRepository) {
        this.hostSedeRepository = hostSedeRepository;
        this.hostSedeSearchRepository = hostSedeSearchRepository;
    }

    /**
     * Save a hostSede.
     *
     * @param hostSede the entity to save.
     * @return the persisted entity.
     */
    public HostSede save(HostSede hostSede) {
        log.debug("Request to save HostSede : {}", hostSede);
        HostSede result = hostSedeRepository.save(hostSede);
        hostSedeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hostSedes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HostSede> findAll(Pageable pageable) {
        log.debug("Request to get all HostSedes");
        return hostSedeRepository.findAll(pageable);
    }


    /**
     * Get one hostSede by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HostSede> findOne(Long id) {
        log.debug("Request to get HostSede : {}", id);
        return hostSedeRepository.findById(id);
    }

    /**
     * Delete the hostSede by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HostSede : {}", id);
        hostSedeRepository.deleteById(id);
        hostSedeSearchRepository.deleteById(id);
    }

    /**
     * Search for the hostSede corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HostSede> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HostSedes for query {}", query);
        return hostSedeSearchRepository.search(queryStringQuery(query), pageable);    }
}
