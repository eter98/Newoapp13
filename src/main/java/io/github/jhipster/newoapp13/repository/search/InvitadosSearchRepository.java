package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.Invitados;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Invitados} entity.
 */
public interface InvitadosSearchRepository extends ElasticsearchRepository<Invitados, Long> {
}
