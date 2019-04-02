package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DocumentTemplatePart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentTemplatePart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTemplatePartRepository extends JpaRepository<DocumentTemplatePart, Long> {

}
