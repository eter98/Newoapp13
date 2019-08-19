package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Reservas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reservas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long>, JpaSpecificationExecutor<Reservas> {

}
