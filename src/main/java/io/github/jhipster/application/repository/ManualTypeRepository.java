package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ManualType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ManualType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManualTypeRepository extends JpaRepository<ManualType, Long> {

}
