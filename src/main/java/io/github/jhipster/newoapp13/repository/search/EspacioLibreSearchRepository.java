package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.EspacioLibre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EspacioLibre} entity.
 */
public interface EspacioLibreSearchRepository extends ElasticsearchRepository<EspacioLibre, Long> {
}
