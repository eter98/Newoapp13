package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EquipoEmpresas} entity.
 */
public interface EquipoEmpresasSearchRepository extends ElasticsearchRepository<EquipoEmpresas, Long> {
}
