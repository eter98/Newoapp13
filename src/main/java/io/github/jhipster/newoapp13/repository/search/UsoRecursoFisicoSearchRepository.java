package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.UsoRecursoFisico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UsoRecursoFisico} entity.
 */
public interface UsoRecursoFisicoSearchRepository extends ElasticsearchRepository<UsoRecursoFisico, Long> {
}
