package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Miembros;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Miembros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MiembrosRepository extends JpaRepository<Miembros, Long>, JpaSpecificationExecutor<Miembros> {

}
