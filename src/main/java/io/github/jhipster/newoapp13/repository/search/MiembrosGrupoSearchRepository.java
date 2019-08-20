package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.MiembrosGrupo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MiembrosGrupo} entity.
 */
public interface MiembrosGrupoSearchRepository extends ElasticsearchRepository<MiembrosGrupo, Long> {
}
