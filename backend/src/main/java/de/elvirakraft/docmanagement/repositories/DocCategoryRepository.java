package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.DocCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.SimpleTimeZone;

public interface DocCategoryRepository extends JpaRepository<DocCategory, Integer> {
    Optional<DocCategory> findDocCategoryByName(String name);
}
