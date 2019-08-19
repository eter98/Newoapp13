package io.github.jhipster.newoapp13.service;

import io.github.jhipster.newoapp13.domain.Empresa;
import io.github.jhipster.newoapp13.repository.EmpresaRepository;
import io.github.jhipster.newoapp13.repository.search.EmpresaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Empresa}.
 */
@Service
@Transactional
public class EmpresaService {

    private final Logger log = LoggerFactory.getLogger(EmpresaService.class);

    private final EmpresaRepository empresaRepository;

    private final EmpresaSearchRepository empresaSearchRepository;

    public EmpresaService(EmpresaRepository empresaRepository, EmpresaSearchRepository empresaSearchRepository) {
        this.empresaRepository = empresaRepository;
        this.empresaSearchRepository = empresaSearchRepository;
    }

    /**
     * Save a empresa.
     *
     * @param empresa the entity to save.
     * @return the persisted entity.
     */
    public Empresa save(Empresa empresa) {
        log.debug("Request to save Empresa : {}", empresa);
        Empresa result = empresaRepository.save(empresa);
        empresaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the empresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Empresa> findAll(Pageable pageable) {
        log.debug("Request to get all Empresas");
        return empresaRepository.findAll(pageable);
    }


    /**
     * Get one empresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Empresa> findOne(Long id) {
        log.debug("Request to get Empresa : {}", id);
        return empresaRepository.findById(id);
    }

    /**
     * Delete the empresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Empresa : {}", id);
        empresaRepository.deleteById(id);
        empresaSearchRepository.deleteById(id);
    }

    /**
     * Search for the empresa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Empresa> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Empresas for query {}", query);
        return empresaSearchRepository.search(queryStringQuery(query), pageable);    }
}
