package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.TipoRegistroCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoRegistroCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRegistroCompraRepository extends JpaRepository<TipoRegistroCompra, Long>, JpaSpecificationExecutor<TipoRegistroCompra> {

}
