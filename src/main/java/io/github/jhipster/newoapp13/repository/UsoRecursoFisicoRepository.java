package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.UsoRecursoFisico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UsoRecursoFisico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsoRecursoFisicoRepository extends JpaRepository<UsoRecursoFisico, Long>, JpaSpecificationExecutor<UsoRecursoFisico> {

}
