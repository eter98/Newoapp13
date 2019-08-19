package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.RecursosFisicos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecursosFisicos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecursosFisicosRepository extends JpaRepository<RecursosFisicos, Long>, JpaSpecificationExecutor<RecursosFisicos> {

}
