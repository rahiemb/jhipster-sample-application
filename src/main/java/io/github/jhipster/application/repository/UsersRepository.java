package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "select distinct users from Users users left join fetch users.groups left join fetch users.groups left join fetch users.roles left join fetch users.abilities",
        countQuery = "select count(distinct users) from Users users")
    Page<Users> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct users from Users users left join fetch users.groups left join fetch users.groups left join fetch users.roles left join fetch users.abilities")
    List<Users> findAllWithEagerRelationships();

    @Query("select users from Users users left join fetch users.groups left join fetch users.groups left join fetch users.roles left join fetch users.abilities where users.id =:id")
    Optional<Users> findOneWithEagerRelationships(@Param("id") Long id);

}
