package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.CuentaAsociada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CuentaAsociada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuentaAsociadaRepository extends JpaRepository<CuentaAsociada, Long>, JpaSpecificationExecutor<CuentaAsociada> {

}
