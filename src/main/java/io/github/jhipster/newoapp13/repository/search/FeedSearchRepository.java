package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Feed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Feed} entity.
 */
public interface FeedSearchRepository extends ElasticsearchRepository<Feed, Long> {
}
