package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Pais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pais} entity.
 */
public interface PaisSearchRepository extends ElasticsearchRepository<Pais, Long> {
}
