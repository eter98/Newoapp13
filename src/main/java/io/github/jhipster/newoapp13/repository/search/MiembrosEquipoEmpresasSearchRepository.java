package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MiembrosEquipoEmpresas} entity.
 */
public interface MiembrosEquipoEmpresasSearchRepository extends ElasticsearchRepository<MiembrosEquipoEmpresas, Long> {
}
