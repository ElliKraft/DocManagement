package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.DocCategory;
import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.entities.Partner;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService){
        this.documentService = documentService;
    }

    @PostMapping("/{userId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #user.id == authentication.userId)")
    public ResponseEntity<Document> addDocument(@RequestBody Document document, @PathVariable Long userId) {
        return new ResponseEntity<>(documentService.addDocument(document, userId), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Document> updateDocument(@RequestBody Document document) {
        try {
            return ResponseEntity.ok(documentService.updateDocument(document));
        } catch (EntityNotFoundException e){
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document was not found");
        }
    }

    @DeleteMapping("/{documentId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<Document> deleteDocument(@PathVariable Long documentId) {
        try {
            return ResponseEntity.ok(documentService.deleteDocument(documentId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document does not exist");
        }
      }

    @GetMapping("/{documentId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long documentId) {
        try {
            return ResponseEntity.ok(documentService.getDocumentById(documentId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document does not exist");
        }
    }

    @GetMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN')")
    /** TODO die Methode gibt keine richtige Auflistung aller Dokumente zurück,
     *   sondern verschachtelt: Dokument -> Array von allen Usern, die das Dokument bearbeiten können -> Dokumente, die diese User bearbeiten können usw
     */
    public ResponseEntity<List<Document>> getAllDocuments() {
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }

    @PostMapping("/{documentId}/{userId}")
    public ResponseEntity<Document> addUserToTheDocument(@PathVariable Long documentId, @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(documentService.addUserToTheDocument(documentId, userId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document was not found");
        } catch (RoleNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
    }

    @DeleteMapping("/{documentId}/{userId}")
    public ResponseEntity<Document> removeUserFromTheDocument(@PathVariable Long documentId, @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(documentService.deleteGivenUserFromDocument(documentId, userId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document was not found");
        } catch (RoleNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
        }
    }

    ///////////////////////////////////////// Getters for the arrays of a document /////////////////////////////////////////

    // TODO not working from here downwards
    @GetMapping("/categories/{docId}")
    public ResponseEntity<List<DocCategory>> getAllCategoriesOfTheDocument(@PathVariable Long docId) {
        return new ResponseEntity<>(documentService.getAllCategoriesOfTheDocument(docId), HttpStatus.OK);
    }

    @GetMapping("/users/{docId}")
    public ResponseEntity<List<User>> getAllUsersOfTheDocument(@PathVariable Long docId) {
        return new ResponseEntity<>(documentService.getAllUsersToTheDocument(docId), HttpStatus.OK);
    }

    @GetMapping("/partners/{docId}")
    public ResponseEntity<List<Partner>> getAllPartnersOfTheDocument(@PathVariable Long docId) {
        return new ResponseEntity<>(documentService.getAllPartnersToTheDocument(docId), HttpStatus.OK);
    }


}
