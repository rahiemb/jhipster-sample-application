package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Manual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Manual entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManualRepository extends JpaRepository<Manual, Long> {

    @Query(value = "select distinct manual from Manual manual left join fetch manual.manualTypes",
        countQuery = "select count(distinct manual) from Manual manual")
    Page<Manual> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct manual from Manual manual left join fetch manual.manualTypes")
    List<Manual> findAllWithEagerRelationships();

    @Query("select manual from Manual manual left join fetch manual.manualTypes where manual.id =:id")
    Optional<Manual> findOneWithEagerRelationships(@Param("id") Long id);

}
