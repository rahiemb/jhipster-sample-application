package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.VersionSummary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VersionSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersionSummaryRepository extends JpaRepository<VersionSummary, Long> {

}
