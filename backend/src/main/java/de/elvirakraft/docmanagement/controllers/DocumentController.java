package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService){
        this.documentService = documentService;
    }

    @PostMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #user.id == authentication.userId)")
    public ResponseEntity<Document> addDocument(@RequestBody Document document) {
        return new ResponseEntity<>(documentService.addDocument(document), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Document> updateDocument(@RequestBody Document document) {
        Document updatedDocument = documentService.updateDocument(document);
        if (updatedDocument != null) {
            return new ResponseEntity<>(updatedDocument, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
/*
    @DeleteMapping("/{documentId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<Document> deleteDocument(@PathVariable Long documentId) {
        Document deletedDocument= documentService.deleteDocument(documentId);
        if (deletedDocument != null) {
            return new ResponseEntity<>(deletedDocument, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN')")
    public ResponseEntity<List<Document>> getAllDocuments() {
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }

    @GetMapping("/{documentId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long documentId) {
        Optional<Document> optionalDocument = documentService.getDocumentById(documentId);
        return optionalDocument.map(document -> new ResponseEntity<>(document, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

     */
}
