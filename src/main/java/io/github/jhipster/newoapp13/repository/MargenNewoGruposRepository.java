package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.MargenNewoGrupos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MargenNewoGrupos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MargenNewoGruposRepository extends JpaRepository<MargenNewoGrupos, Long>, JpaSpecificationExecutor<MargenNewoGrupos> {

}
