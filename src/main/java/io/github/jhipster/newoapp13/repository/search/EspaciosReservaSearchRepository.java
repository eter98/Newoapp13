package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EspaciosReserva} entity.
 */
public interface EspaciosReservaSearchRepository extends ElasticsearchRepository<EspaciosReserva, Long> {
}
