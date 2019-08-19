package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import io.github.jhipster.newoapp13.repository.EquipoEmpresasRepository;
import io.github.jhipster.newoapp13.repository.search.EquipoEmpresasSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EquipoEmpresas}.
 */
@Service
@Transactional
public class EquipoEmpresasService {

    private final Logger log = LoggerFactory.getLogger(EquipoEmpresasService.class);

    private final EquipoEmpresasRepository equipoEmpresasRepository;

    private final EquipoEmpresasSearchRepository equipoEmpresasSearchRepository;

    public EquipoEmpresasService(EquipoEmpresasRepository equipoEmpresasRepository, EquipoEmpresasSearchRepository equipoEmpresasSearchRepository) {
        this.equipoEmpresasRepository = equipoEmpresasRepository;
        this.equipoEmpresasSearchRepository = equipoEmpresasSearchRepository;
    }

    /**
     * Save a equipoEmpresas.
     *
     * @param equipoEmpresas the entity to save.
     * @return the persisted entity.
     */
    public EquipoEmpresas save(EquipoEmpresas equipoEmpresas) {
        log.debug("Request to save EquipoEmpresas : {}", equipoEmpresas);
        EquipoEmpresas result = equipoEmpresasRepository.save(equipoEmpresas);
        equipoEmpresasSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the equipoEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EquipoEmpresas> findAll(Pageable pageable) {
        log.debug("Request to get all EquipoEmpresas");
        return equipoEmpresasRepository.findAll(pageable);
    }


    /**
     * Get one equipoEmpresas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EquipoEmpresas> findOne(Long id) {
        log.debug("Request to get EquipoEmpresas : {}", id);
        return equipoEmpresasRepository.findById(id);
    }

    /**
     * Delete the equipoEmpresas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EquipoEmpresas : {}", id);
        equipoEmpresasRepository.deleteById(id);
        equipoEmpresasSearchRepository.deleteById(id);
    }

    /**
     * Search for the equipoEmpresas corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EquipoEmpresas> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EquipoEmpresas for query {}", query);
        return equipoEmpresasSearchRepository.search(queryStringQuery(query), pageable);    }
}
