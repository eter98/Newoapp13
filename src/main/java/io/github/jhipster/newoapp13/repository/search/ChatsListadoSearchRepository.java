package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.ChatsListado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ChatsListado} entity.
 */
public interface ChatsListadoSearchRepository extends ElasticsearchRepository<ChatsListado, Long> {
}
