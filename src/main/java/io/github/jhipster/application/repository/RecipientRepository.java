package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Recipient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recipient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

}
