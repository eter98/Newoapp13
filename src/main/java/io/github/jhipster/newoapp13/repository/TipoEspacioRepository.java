package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.TipoEspacio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoEspacio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoEspacioRepository extends JpaRepository<TipoEspacio, Long>, JpaSpecificationExecutor<TipoEspacio> {

}
