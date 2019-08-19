package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Facturacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Facturacion} entity.
 */
public interface FacturacionSearchRepository extends ElasticsearchRepository<Facturacion, Long> {
}
