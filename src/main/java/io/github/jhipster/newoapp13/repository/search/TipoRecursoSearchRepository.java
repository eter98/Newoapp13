package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.TipoRecurso;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoRecurso} entity.
 */
public interface TipoRecursoSearchRepository extends ElasticsearchRepository<TipoRecurso, Long> {
}
