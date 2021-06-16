package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findUserRolesByUser(User user);
    List<UserRole> findUsersByRole(String role);
    Optional<UserRole> findByUserIdAndRole(Long userId, String role);

}
