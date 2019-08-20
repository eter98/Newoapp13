package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.ComentarioFeed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ComentarioFeed} entity.
 */
public interface ComentarioFeedSearchRepository extends ElasticsearchRepository<ComentarioFeed, Long> {
}
