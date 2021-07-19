package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.DocCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocCategoryRepository extends JpaRepository<DocCategory, Integer> {
}
