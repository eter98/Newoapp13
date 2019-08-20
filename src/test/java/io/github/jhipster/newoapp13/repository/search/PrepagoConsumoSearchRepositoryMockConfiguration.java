package io.github.jhipster.newoapp13.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PrepagoConsumoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PrepagoConsumoSearchRepositoryMockConfiguration {

    @MockBean
    private PrepagoConsumoSearchRepository mockPrepagoConsumoSearchRepository;

}
