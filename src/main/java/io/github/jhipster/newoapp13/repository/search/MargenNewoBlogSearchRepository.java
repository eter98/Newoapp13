package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.MargenNewoBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MargenNewoBlog} entity.
 */
public interface MargenNewoBlogSearchRepository extends ElasticsearchRepository<MargenNewoBlog, Long> {
}
