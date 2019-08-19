package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.ConsumoMarket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumoMarket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumoMarketRepository extends JpaRepository<ConsumoMarket, Long>, JpaSpecificationExecutor<ConsumoMarket> {

}
