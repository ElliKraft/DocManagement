package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.DocCategory;
import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.entities.Partner;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.repositories.DocumentRepository;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DocumentService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Adds the given document to the database and adds the current user to the array of users, who edit the document.
     *
     * @param document The given document
     * @return The added document.
     */
    public Document addDocument(Document document, Long userId) {
        User userToAdd = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        userToAdd.getDocumentsOfTheUser().add(document);
        document.getUsersWhoEditTheDocument().add(userRepository.getById(userId));
        return documentRepository.save(document);
    }

    /**
     * Edits the given document.
     *
     * @param document The given document.
     * @return The edited document.
     */
    public Document updateDocument(Document document) throws EntityNotFoundException{
        Document documentToUpdate = documentRepository.findById(document.getId()).orElseThrow(EntityNotFoundException::new);
        if (document.getTitle() != null) documentToUpdate.setTitle(document.getTitle());
        if (document.getNumber() != null) documentToUpdate.setNumber(document.getNumber());
        if (document.getConclusionDate() != null) documentToUpdate.setConclusionDate(document.getConclusionDate());
        if (document.getTerminationDate() != null) documentToUpdate.setTerminationDate(document.getTerminationDate());
        if (document.getImageUrl() != null) documentToUpdate.setImageUrl(document.getImageUrl());
        if (document.getTextNote() != null) documentToUpdate.setTextNote(document.getTextNote());

        return documentRepository.save(documentToUpdate);
    }

    /**
     * Deletes the document with the given ID.
     *
     * @param id The given ID.
     * @return The deleted document.
     */
    public Document deleteDocument(Long id) {
        Document documentToDelete = documentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        documentToDelete.setDeleted(true);
        return documentRepository.save(documentToDelete);
    }

    /**
     * Returns the document with the given ID, removes from the view the categories, which have true on deleted property
     *
     * @param id The given ID.
     * @return The document.
     */
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Returns all documents.
     *
     * @return The documents.
     */
    public List<Document> getAllDocuments() {
       return documentRepository.findAll();
    }

    /**
     * Returns all the categories of the given document
     *
     * @param docId The id of the given document
     * @return All the categories of the document
     */
    public List<DocCategory> getAllCategoriesOfTheDocument(Long docId) {
        Document document = documentRepository.getById(docId);
        Set<DocCategory> allCats = document.getDocCategories();
        return new ArrayList<>(allCats);
    }

    /**
     * Returns all the users, who may edit the document
     *
     * @param docId The id of the given document
     * @return The list of the users
     */
    public List<User> getAllUsersToTheDocument(Long docId) {
        Set<User> allUsers = documentRepository.getById(docId).getUsersWhoEditTheDocument();
        allUsers.removeIf(User::isDeleted);
        return new ArrayList<>(allUsers);
    }

    /**
     * Returns all the partners to the document
     * @param docId The id of the given document
     * @return The list of the partners
     */
    public List<Partner> getAllPartnersToTheDocument(Long docId) {
        Set<Partner> allPartners = documentRepository.getById(docId).getCurrentPartners();
        allPartners.removeIf(Partner::isDeleted);
        return new ArrayList<>(allPartners);
    }

    /**
     * Adds the given user to the given document
     *
     * @param docId The id of the given document
     * @param userId The id of the a user to be added to the document
     * @return Updated document
     */
    public Document addUserToTheDocument(Long docId, Long userId) throws RoleNotFoundException {
        Document document = documentRepository.findById(docId).orElseThrow(EntityNotFoundException::new);
        document.getUsersWhoEditTheDocument().add(userRepository.findById(userId).orElseThrow(RoleNotFoundException::new));
        return documentRepository.save(document);
    }

    /**
     * Removes the given user from the given document
     *
     * @param documentId The id of the given document
     * @param userId The id of a user to be removed from the document
     * @return The updated document
     */
    public Document deleteGivenUserFromDocument(Long documentId, Long userId) throws RoleNotFoundException {
        Document document = documentRepository.findById(documentId).orElseThrow(EntityNotFoundException::new);
        document.getUsersWhoEditTheDocument().add(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
        return documentRepository.save(document);
    }

}
