package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
