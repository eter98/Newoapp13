package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.ComentarioFeed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ComentarioFeed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentarioFeedRepository extends JpaRepository<ComentarioFeed, Long>, JpaSpecificationExecutor<ComentarioFeed> {

}
