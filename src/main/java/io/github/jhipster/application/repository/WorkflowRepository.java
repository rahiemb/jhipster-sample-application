package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Workflow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Workflow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

}
