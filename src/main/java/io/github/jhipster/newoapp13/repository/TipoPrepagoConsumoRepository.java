package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoPrepagoConsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPrepagoConsumoRepository extends JpaRepository<TipoPrepagoConsumo, Long>, JpaSpecificationExecutor<TipoPrepagoConsumo> {

}
