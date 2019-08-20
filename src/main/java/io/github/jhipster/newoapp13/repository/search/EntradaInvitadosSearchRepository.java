package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.EntradaInvitados;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EntradaInvitados} entity.
 */
public interface EntradaInvitadosSearchRepository extends ElasticsearchRepository<EntradaInvitados, Long> {
}
