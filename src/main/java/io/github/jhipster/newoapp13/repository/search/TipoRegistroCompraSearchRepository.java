package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoRegistroCompra} entity.
 */
public interface TipoRegistroCompraSearchRepository extends ElasticsearchRepository<TipoRegistroCompra, Long> {
}
