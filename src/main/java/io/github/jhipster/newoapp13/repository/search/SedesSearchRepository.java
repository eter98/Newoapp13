package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Sedes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Sedes} entity.
 */
public interface SedesSearchRepository extends ElasticsearchRepository<Sedes, Long> {
}
