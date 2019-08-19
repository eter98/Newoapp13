package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.EntradaMiembros;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntradaMiembros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntradaMiembrosRepository extends JpaRepository<EntradaMiembros, Long>, JpaSpecificationExecutor<EntradaMiembros> {

}
