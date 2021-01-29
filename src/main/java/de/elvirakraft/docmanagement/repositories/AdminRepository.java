package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
