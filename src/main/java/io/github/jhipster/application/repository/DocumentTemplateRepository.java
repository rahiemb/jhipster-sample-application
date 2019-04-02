package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DocumentTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTemplateRepository extends JpaRepository<DocumentTemplate, Long> {

}
