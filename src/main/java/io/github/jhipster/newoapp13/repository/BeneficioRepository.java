package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Beneficio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Beneficio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long>, JpaSpecificationExecutor<Beneficio> {

}
