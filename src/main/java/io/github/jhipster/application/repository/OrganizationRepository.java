package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "select distinct organization from Organization organization left join fetch organization.groups left join fetch organization.roles",
        countQuery = "select count(distinct organization) from Organization organization")
    Page<Organization> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct organization from Organization organization left join fetch organization.groups left join fetch organization.roles")
    List<Organization> findAllWithEagerRelationships();

    @Query("select organization from Organization organization left join fetch organization.groups left join fetch organization.roles where organization.id =:id")
    Optional<Organization> findOneWithEagerRelationships(@Param("id") Long id);

}
