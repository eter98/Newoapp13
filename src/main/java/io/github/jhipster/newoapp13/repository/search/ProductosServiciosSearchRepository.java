package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.ProductosServicios;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProductosServicios} entity.
 */
public interface ProductosServiciosSearchRepository extends ElasticsearchRepository<ProductosServicios, Long> {
}
