package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.PrepagoConsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrepagoConsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrepagoConsumoRepository extends JpaRepository<PrepagoConsumo, Long>, JpaSpecificationExecutor<PrepagoConsumo> {

}
