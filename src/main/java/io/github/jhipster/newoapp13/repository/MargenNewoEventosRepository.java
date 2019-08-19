package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.MargenNewoEventos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MargenNewoEventos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MargenNewoEventosRepository extends JpaRepository<MargenNewoEventos, Long>, JpaSpecificationExecutor<MargenNewoEventos> {

}
