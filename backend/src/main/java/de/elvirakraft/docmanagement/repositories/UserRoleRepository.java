package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    // checks, if a user role with the given name exists in the database
    Optional<UserRole> findByRoleName(String roleName);

    UserRole findUserRoleByRoleName(String roleName);

}
