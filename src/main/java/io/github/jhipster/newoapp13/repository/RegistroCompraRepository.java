package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.RegistroCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegistroCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroCompraRepository extends JpaRepository<RegistroCompra, Long>, JpaSpecificationExecutor<RegistroCompra> {

}
