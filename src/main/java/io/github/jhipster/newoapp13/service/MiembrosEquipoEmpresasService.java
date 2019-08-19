package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas;
import io.github.jhipster.newoapp13.repository.MiembrosEquipoEmpresasRepository;
import io.github.jhipster.newoapp13.repository.search.MiembrosEquipoEmpresasSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MiembrosEquipoEmpresas}.
 */
@Service
@Transactional
public class MiembrosEquipoEmpresasService {

    private final Logger log = LoggerFactory.getLogger(MiembrosEquipoEmpresasService.class);

    private final MiembrosEquipoEmpresasRepository miembrosEquipoEmpresasRepository;

    private final MiembrosEquipoEmpresasSearchRepository miembrosEquipoEmpresasSearchRepository;

    public MiembrosEquipoEmpresasService(MiembrosEquipoEmpresasRepository miembrosEquipoEmpresasRepository, MiembrosEquipoEmpresasSearchRepository miembrosEquipoEmpresasSearchRepository) {
        this.miembrosEquipoEmpresasRepository = miembrosEquipoEmpresasRepository;
        this.miembrosEquipoEmpresasSearchRepository = miembrosEquipoEmpresasSearchRepository;
    }

    /**
     * Save a miembrosEquipoEmpresas.
     *
     * @param miembrosEquipoEmpresas the entity to save.
     * @return the persisted entity.
     */
    public MiembrosEquipoEmpresas save(MiembrosEquipoEmpresas miembrosEquipoEmpresas) {
        log.debug("Request to save MiembrosEquipoEmpresas : {}", miembrosEquipoEmpresas);
        MiembrosEquipoEmpresas result = miembrosEquipoEmpresasRepository.save(miembrosEquipoEmpresas);
        miembrosEquipoEmpresasSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the miembrosEquipoEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MiembrosEquipoEmpresas> findAll(Pageable pageable) {
        log.debug("Request to get all MiembrosEquipoEmpresas");
        return miembrosEquipoEmpresasRepository.findAll(pageable);
    }


    /**
     * Get one miembrosEquipoEmpresas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MiembrosEquipoEmpresas> findOne(Long id) {
        log.debug("Request to get MiembrosEquipoEmpresas : {}", id);
        return miembrosEquipoEmpresasRepository.findById(id);
    }

    /**
     * Delete the miembrosEquipoEmpresas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MiembrosEquipoEmpresas : {}", id);
        miembrosEquipoEmpresasRepository.deleteById(id);
        miembrosEquipoEmpresasSearchRepository.deleteById(id);
    }

    /**
     * Search for the miembrosEquipoEmpresas corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MiembrosEquipoEmpresas> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MiembrosEquipoEmpresas for query {}", query);
        return miembrosEquipoEmpresasSearchRepository.search(queryStringQuery(query), pageable);    }
}
