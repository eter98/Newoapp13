package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Landing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Landing} entity.
 */
public interface LandingSearchRepository extends ElasticsearchRepository<Landing, Long> {
}
