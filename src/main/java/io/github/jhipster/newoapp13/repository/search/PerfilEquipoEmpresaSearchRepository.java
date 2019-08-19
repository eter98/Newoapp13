package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.PerfilEquipoEmpresa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PerfilEquipoEmpresa} entity.
 */
public interface PerfilEquipoEmpresaSearchRepository extends ElasticsearchRepository<PerfilEquipoEmpresa, Long> {
}
