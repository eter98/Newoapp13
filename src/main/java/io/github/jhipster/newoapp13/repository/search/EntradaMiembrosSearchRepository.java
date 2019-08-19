package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.EntradaMiembros;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EntradaMiembros} entity.
 */
public interface EntradaMiembrosSearchRepository extends ElasticsearchRepository<EntradaMiembros, Long> {
}
