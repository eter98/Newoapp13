package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.MargenNewoGrupos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MargenNewoGrupos} entity.
 */
public interface MargenNewoGruposSearchRepository extends ElasticsearchRepository<MargenNewoGrupos, Long> {
}
