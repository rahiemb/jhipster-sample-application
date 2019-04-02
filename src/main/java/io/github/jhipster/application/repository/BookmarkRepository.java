package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Bookmark;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bookmark entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
