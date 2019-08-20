package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Miembros;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Miembros} entity.
 */
public interface MiembrosSearchRepository extends ElasticsearchRepository<Miembros, Long> {
}
