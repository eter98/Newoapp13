package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.TipoRecurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRecursoRepository extends JpaRepository<TipoRecurso, Long>, JpaSpecificationExecutor<TipoRecurso> {

}
