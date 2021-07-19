package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findUsersByDeletedTrue();
    List<User> findUsersByDeletedFalse();
    boolean existsUserByEmail(String email);
    List<User> findUsersByRolesOfTheUserEquals(Integer roleId);
}
