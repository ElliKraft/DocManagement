package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
