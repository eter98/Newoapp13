package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.ConsumoMarket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ConsumoMarket} entity.
 */
public interface ConsumoMarketSearchRepository extends ElasticsearchRepository<ConsumoMarket, Long> {
}
