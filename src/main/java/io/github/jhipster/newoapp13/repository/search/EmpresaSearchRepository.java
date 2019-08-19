package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Empresa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Empresa} entity.
 */
public interface EmpresaSearchRepository extends ElasticsearchRepository<Empresa, Long> {
}
