package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Ciudad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ciudad} entity.
 */
public interface CiudadSearchRepository extends ElasticsearchRepository<Ciudad, Long> {
}
