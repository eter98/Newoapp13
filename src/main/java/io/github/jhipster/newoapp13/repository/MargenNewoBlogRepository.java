package io.github.jhipster.newoapp13.repository;

import io.github.jhipster.newoapp13.domain.MargenNewoBlog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MargenNewoBlog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MargenNewoBlogRepository extends JpaRepository<MargenNewoBlog, Long>, JpaSpecificationExecutor<MargenNewoBlog> {

}
