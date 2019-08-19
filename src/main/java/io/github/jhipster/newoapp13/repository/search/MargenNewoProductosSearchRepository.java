package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.MargenNewoProductos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MargenNewoProductos} entity.
 */
public interface MargenNewoProductosSearchRepository extends ElasticsearchRepository<MargenNewoProductos, Long> {
}
