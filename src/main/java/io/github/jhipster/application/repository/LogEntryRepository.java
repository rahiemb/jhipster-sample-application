package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.LogEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LogEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

}
