package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.CuentaAsociada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CuentaAsociada} entity.
 */
public interface CuentaAsociadaSearchRepository extends ElasticsearchRepository<CuentaAsociada, Long> {
}
