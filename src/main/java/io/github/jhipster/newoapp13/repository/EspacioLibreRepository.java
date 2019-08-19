package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.EspacioLibre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EspacioLibre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspacioLibreRepository extends JpaRepository<EspacioLibre, Long>, JpaSpecificationExecutor<EspacioLibre> {

}
