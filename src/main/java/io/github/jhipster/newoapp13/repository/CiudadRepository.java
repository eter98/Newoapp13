package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Ciudad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ciudad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long>, JpaSpecificationExecutor<Ciudad> {

}
