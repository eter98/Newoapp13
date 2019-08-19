package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.EspaciosReserva;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EspaciosReserva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspaciosReservaRepository extends JpaRepository<EspaciosReserva, Long>, JpaSpecificationExecutor<EspaciosReserva> {

}
