package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.entities.Partner;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.repositories.DocumentRepository;
import de.elvirakraft.docmanagement.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, DocumentRepository documentRepository){
        this.partnerRepository = partnerRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Adds the given partner to the database.
     *
     * @param partner The given partner.
     * @return The added partner.
     */
    public Partner createPartner(Partner partner) throws EntityExistsException {
        if (partnerRepository.existsPartnerByNameAndCity(partner.getName(), partner.getCity())) {
            throw new EntityExistsException();
        }
        return partnerRepository.save(partner);
    }

    /**
     * Adds the given partner to the given document
     *
     * @param documentId The id of the given document
     * @param partnerId The id of a partner to be added to the document
     * @return The updated document
     */
    public Document addPartnerToTheDocument(Long documentId, Integer partnerId) throws RoleNotFoundException {
        Document document = documentRepository.findById(documentId).orElseThrow(EntityNotFoundException::new);
        document.getCurrentPartners().add(partnerRepository.findById(partnerId).orElseThrow(RoleNotFoundException::new));
        return documentRepository.save(document);
    }

    /**
     * Removes the given partner from the given document
     *
     * @param documentId The id of the given document
     * @param partnerId The id of a partner to be added to the document
     * @return The updated document
     */
    public Document deleteGivenPartnerFromDocument(Long documentId, Integer partnerId) {
        Document document = documentRepository.findById(documentId).orElseThrow(EntityNotFoundException::new);
        document.getCurrentPartners().remove(partnerRepository.getById(partnerId));
        return documentRepository.save(document);
    }

    /**
     * Edits the given partner.
     *
     * @param partner The given partner.
     * @return The edited partner.
     */
    public Partner updatePartner(Partner partner) throws EntityNotFoundException {
        Partner partnerToUpdate = partnerRepository.findById(partner.getId()).orElseThrow(EntityNotFoundException::new);
        if (partner.getName() != null) partnerToUpdate.setName(partner.getName());
        if (partner.getPhone() != null) partnerToUpdate.setPhone(partner.getPhone());
        if (partner.getEmail() != null) partnerToUpdate.setEmail(partner.getEmail());
        if (partner.getCity() != null) partnerToUpdate.setCity(partner.getCity());
        if (partner.getPostalCode() != null) partnerToUpdate.setPostalCode(partner.getPostalCode());
        if (partner.getStreet() != null) partnerToUpdate.setStreet(partner.getStreet());
        if (partner.getHouseNumber() != null) partnerToUpdate.setHouseNumber(partner.getHouseNumber());
        if (partner.getContactPerson() != null) partnerToUpdate.setContactPerson(partner.getContactPerson());

        return partnerRepository.save(partnerToUpdate);
    }

    /**
     * Deletes the partner with the given ID.
     *
     * @param id The given ID.
     * @return The deleted partner.
     */
    public Partner deletePartner(Integer id) {
        Partner partnerToDelete = partnerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        partnerToDelete.setDeleted(true);
        return partnerRepository.save(partnerToDelete);
    }

    /**
     * Returns the partner with the given ID.
     *
     * @param id The given ID.
     * @return The partner.
     */
    public Partner getPartnerById(Integer id) {
        return partnerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Returns all partners.
     *
     * @return The partners.
     */
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    /**
     * Returns all the documents of the given partner, which are not deleted
     *
     * @param partnerId The id of the given partner
     * @return All the documents of the partner
     */
    public List<Document> getAllDocumentsOfThePartner(Integer partnerId) {
        Set<Document> allDocs = partnerRepository.getById(partnerId).getDocsOfThePartner();
        allDocs.removeIf(Document::isDeleted);
        return new ArrayList<>(allDocs);
    }
}
