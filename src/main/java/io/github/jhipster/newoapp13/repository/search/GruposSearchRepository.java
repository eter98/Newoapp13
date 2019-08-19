package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Grupos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Grupos} entity.
 */
public interface GruposSearchRepository extends ElasticsearchRepository<Grupos, Long> {
}
