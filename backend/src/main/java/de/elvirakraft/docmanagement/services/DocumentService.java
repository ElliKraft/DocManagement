package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.repositories.DocumentRepository;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Document addDocument(Document document) {
        return documentRepository.save(document);
    }

    /**
     * Edits the given document.
     *
     * @param document The given document.
     * @return The edited document.
     */
    public Document updateDocument(Document document) {
        Optional<Document> optionalDocument = documentRepository.findById(document.getDocId());
        if (optionalDocument.isEmpty()) {
            return null;
        }

        Document documentToUpdate = optionalDocument.get();
        if (document.getTitle() != null) documentToUpdate.setTitle(document.getTitle());
        if (document.getNumber() != null) documentToUpdate.setNumber(document.getNumber());
        if (document.getConclusionDate() != null) documentToUpdate.setConclusionDate(document.getConclusionDate());
        if (document.getTerminationDate() != null) documentToUpdate.setTerminationDate(document.getTerminationDate());
        if (document.getImageUrl() != null) documentToUpdate.setImageUrl(document.getImageUrl());
        if (document.getTextNote() != null) documentToUpdate.setTextNote(document.getTextNote());

        return documentRepository.save(documentToUpdate);
    }
/*
    /**
     * Deletes the document with the given ID.
     *
     * @param id The given ID.
     * @return The deleted document.
     */
    public Document deleteDocument(Long id) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isEmpty()) {
            return null;
        }

        Document documentToDelete = optionalDocument.get();
        documentToDelete.setDeleted(true);
        return documentRepository.save(documentToDelete);
    }

    /**
     * Returns the document with the given ID.
     *
     * @param id The given ID.
     * @return The document.
     */
    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    /**
     * Returns all documents.
     *
     * @return The documents.
     */
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

}
