package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Invitados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Invitados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitadosRepository extends JpaRepository<Invitados, Long>, JpaSpecificationExecutor<Invitados> {

}
