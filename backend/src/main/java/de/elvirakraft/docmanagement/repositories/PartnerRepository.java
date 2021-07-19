package de.elvirakraft.docmanagement.repositories;

import de.elvirakraft.docmanagement.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}
