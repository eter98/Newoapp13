package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.CategoriaContenidos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaContenidos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaContenidosRepository extends JpaRepository<CategoriaContenidos, Long>, JpaSpecificationExecutor<CategoriaContenidos> {

}
