package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.RegistroFacturacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RegistroFacturacion} entity.
 */
public interface RegistroFacturacionSearchRepository extends ElasticsearchRepository<RegistroFacturacion, Long> {
}
