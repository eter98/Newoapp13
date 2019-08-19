package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.ComentarioBlog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ComentarioBlog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentarioBlogRepository extends JpaRepository<ComentarioBlog, Long>, JpaSpecificationExecutor<ComentarioBlog> {

}
