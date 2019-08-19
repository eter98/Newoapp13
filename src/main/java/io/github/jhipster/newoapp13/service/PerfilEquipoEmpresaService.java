package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.PerfilEquipoEmpresa;
import io.github.jhipster.newoapp13.repository.PerfilEquipoEmpresaRepository;
import io.github.jhipster.newoapp13.repository.search.PerfilEquipoEmpresaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PerfilEquipoEmpresa}.
 */
@Service
@Transactional
public class PerfilEquipoEmpresaService {

    private final Logger log = LoggerFactory.getLogger(PerfilEquipoEmpresaService.class);

    private final PerfilEquipoEmpresaRepository perfilEquipoEmpresaRepository;

    private final PerfilEquipoEmpresaSearchRepository perfilEquipoEmpresaSearchRepository;

    public PerfilEquipoEmpresaService(PerfilEquipoEmpresaRepository perfilEquipoEmpresaRepository, PerfilEquipoEmpresaSearchRepository perfilEquipoEmpresaSearchRepository) {
        this.perfilEquipoEmpresaRepository = perfilEquipoEmpresaRepository;
        this.perfilEquipoEmpresaSearchRepository = perfilEquipoEmpresaSearchRepository;
    }

    /**
     * Save a perfilEquipoEmpresa.
     *
     * @param perfilEquipoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public PerfilEquipoEmpresa save(PerfilEquipoEmpresa perfilEquipoEmpresa) {
        log.debug("Request to save PerfilEquipoEmpresa : {}", perfilEquipoEmpresa);
        PerfilEquipoEmpresa result = perfilEquipoEmpresaRepository.save(perfilEquipoEmpresa);
        perfilEquipoEmpresaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the perfilEquipoEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilEquipoEmpresa> findAll(Pageable pageable) {
        log.debug("Request to get all PerfilEquipoEmpresas");
        return perfilEquipoEmpresaRepository.findAll(pageable);
    }


    /**
     * Get one perfilEquipoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilEquipoEmpresa> findOne(Long id) {
        log.debug("Request to get PerfilEquipoEmpresa : {}", id);
        return perfilEquipoEmpresaRepository.findById(id);
    }

    /**
     * Delete the perfilEquipoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilEquipoEmpresa : {}", id);
        perfilEquipoEmpresaRepository.deleteById(id);
        perfilEquipoEmpresaSearchRepository.deleteById(id);
    }

    /**
     * Search for the perfilEquipoEmpresa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilEquipoEmpresa> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PerfilEquipoEmpresas for query {}", query);
        return perfilEquipoEmpresaSearchRepository.search(queryStringQuery(query), pageable);    }
}
