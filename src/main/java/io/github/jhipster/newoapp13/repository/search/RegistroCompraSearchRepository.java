package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.RegistroCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RegistroCompra} entity.
 */
public interface RegistroCompraSearchRepository extends ElasticsearchRepository<RegistroCompra, Long> {
}
