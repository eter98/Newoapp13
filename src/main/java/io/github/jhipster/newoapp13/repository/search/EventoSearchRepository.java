package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Evento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Evento} entity.
 */
public interface EventoSearchRepository extends ElasticsearchRepository<Evento, Long> {
}
