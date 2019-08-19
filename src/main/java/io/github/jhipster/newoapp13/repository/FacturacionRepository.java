package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Facturacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Facturacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long>, JpaSpecificationExecutor<Facturacion> {

}
