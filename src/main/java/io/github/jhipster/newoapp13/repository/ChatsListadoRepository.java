package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.ChatsListado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChatsListado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatsListadoRepository extends JpaRepository<ChatsListado, Long>, JpaSpecificationExecutor<ChatsListado> {

}
