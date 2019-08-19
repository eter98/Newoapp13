package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.MargenNewoEventos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MargenNewoEventos} entity.
 */
public interface MargenNewoEventosSearchRepository extends ElasticsearchRepository<MargenNewoEventos, Long> {
}
