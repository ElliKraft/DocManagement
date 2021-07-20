package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<Document>> getAllDocuments() {
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }

}
