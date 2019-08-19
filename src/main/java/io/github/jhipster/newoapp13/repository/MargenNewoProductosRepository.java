package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.MargenNewoProductos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MargenNewoProductos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MargenNewoProductosRepository extends JpaRepository<MargenNewoProductos, Long>, JpaSpecificationExecutor<MargenNewoProductos> {

}
