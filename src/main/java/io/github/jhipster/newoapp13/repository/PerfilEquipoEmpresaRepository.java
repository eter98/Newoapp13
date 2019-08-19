package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.PerfilEquipoEmpresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PerfilEquipoEmpresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilEquipoEmpresaRepository extends JpaRepository<PerfilEquipoEmpresa, Long>, JpaSpecificationExecutor<PerfilEquipoEmpresa> {

}
