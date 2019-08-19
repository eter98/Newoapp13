package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.MiembrosGrupo;
import io.github.jhipster.newoapp13.repository.MiembrosGrupoRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosGrupoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MiembrosGrupo}.
 */
@Service
@Transactional
public class MiembrosGrupoService {

    private final Logger log = LoggerFactory.getLogger(MiembrosGrupoService.class);

    private final MiembrosGrupoRepository miembrosGrupoRepository;

    private final MiembrosGrupoSearchRepository miembrosGrupoSearchRepository;

    public MiembrosGrupoService(MiembrosGrupoRepository miembrosGrupoRepository, MiembrosGrupoSearchRepository miembrosGrupoSearchRepository) {
        this.miembrosGrupoRepository = miembrosGrupoRepository;
        this.miembrosGrupoSearchRepository = miembrosGrupoSearchRepository;
    }

    /**
     * Save a miembrosGrupo.
     *
     * @param miembrosGrupo the entity to save.
     * @return the persisted entity.
     */
    public MiembrosGrupo save(MiembrosGrupo miembrosGrupo) {
        log.debug("Request to save MiembrosGrupo : {}", miembrosGrupo);
        MiembrosGrupo result = miembrosGrupoRepository.save(miembrosGrupo);
        miembrosGrupoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the miembrosGrupos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MiembrosGrupo> findAll(Pageable pageable) {
        log.debug("Request to get all MiembrosGrupos");
        return miembrosGrupoRepository.findAll(pageable);
    }


    /**
     * Get one miembrosGrupo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MiembrosGrupo> findOne(Long id) {
        log.debug("Request to get MiembrosGrupo : {}", id);
        return miembrosGrupoRepository.findById(id);
    }

    /**
     * Delete the miembrosGrupo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MiembrosGrupo : {}", id);
        miembrosGrupoRepository.deleteById(id);
        miembrosGrupoSearchRepository.deleteById(id);
    }

    /**
     * Search for the miembrosGrupo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MiembrosGrupo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MiembrosGrupos for query {}", query);
        return miembrosGrupoSearchRepository.search(queryStringQuery(query), pageable);    }
}
