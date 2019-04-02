package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Keyword;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Keyword entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

}
