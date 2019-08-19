package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.TipoEspacio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoEspacio} entity.
 */
public interface TipoEspacioSearchRepository extends ElasticsearchRepository<TipoEspacio, Long> {
}
