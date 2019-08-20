package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.RegistroFacturacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegistroFacturacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroFacturacionRepository extends JpaRepository<RegistroFacturacion, Long>, JpaSpecificationExecutor<RegistroFacturacion> {

}
