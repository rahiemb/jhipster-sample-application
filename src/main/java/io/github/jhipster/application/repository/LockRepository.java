package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Lock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LockRepository extends JpaRepository<Lock, Long> {

}
