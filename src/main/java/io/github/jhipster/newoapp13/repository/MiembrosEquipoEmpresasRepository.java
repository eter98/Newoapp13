package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MiembrosEquipoEmpresas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MiembrosEquipoEmpresasRepository extends JpaRepository<MiembrosEquipoEmpresas, Long>, JpaSpecificationExecutor<MiembrosEquipoEmpresas> {

}
