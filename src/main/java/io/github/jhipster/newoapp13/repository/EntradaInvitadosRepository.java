package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.EntradaInvitados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntradaInvitados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntradaInvitadosRepository extends JpaRepository<EntradaInvitados, Long>, JpaSpecificationExecutor<EntradaInvitados> {

}
