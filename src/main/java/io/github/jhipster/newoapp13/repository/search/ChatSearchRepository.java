package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Chat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Chat} entity.
 */
public interface ChatSearchRepository extends ElasticsearchRepository<Chat, Long> {
}
