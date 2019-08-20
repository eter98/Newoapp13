package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.Landing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Landing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandingRepository extends JpaRepository<Landing, Long>, JpaSpecificationExecutor<Landing> {

}
