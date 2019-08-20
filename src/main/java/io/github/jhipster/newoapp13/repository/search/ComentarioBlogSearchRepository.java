package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.ComentarioBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ComentarioBlog} entity.
 */
public interface ComentarioBlogSearchRepository extends ElasticsearchRepository<ComentarioBlog, Long> {
}
