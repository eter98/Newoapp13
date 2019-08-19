package io.github.jhipster.newoapp13.repository.search;

import io.github.jhipster.newoapp13.domain.HostSede;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link HostSede} entity.
 */
public interface HostSedeSearchRepository extends ElasticsearchRepository<HostSede, Long> {
}
