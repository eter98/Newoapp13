package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.EquipoEmpresas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EquipoEmpresas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipoEmpresasRepository extends JpaRepository<EquipoEmpresas, Long>, JpaSpecificationExecutor<EquipoEmpresas> {

}
