package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.DocCategory;
import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.services.DocCategoryService;
import de.elvirakraft.docmanagement.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class DocCategoryController {

    private final DocCategoryService docCategoryService;
    private final DocumentService documentService;

    @Autowired
    public DocCategoryController(DocCategoryService docCategoryService, DocumentService documentService){
        this.docCategoryService = docCategoryService;
        this.documentService = documentService;
    }

    @PostMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #user.id == authentication.userId)")
    public ResponseEntity<DocCategory> createDocCategory(@RequestBody DocCategory docCategory) {
        return new ResponseEntity<>(docCategoryService.createDocCategory(docCategory), HttpStatus.CREATED);
    }

    @PostMapping("/{documentId}/{docCategoryId}")
    public ResponseEntity<Document> addCategoryToTheDocument(@PathVariable Long documentId, @PathVariable Integer docCategoryId) {
        try {
            return new ResponseEntity<>(docCategoryService.addCategoryToTheDocument(documentService.getDocumentById(documentId), docCategoryId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The category was not found");
        }
    }

    @DeleteMapping("/{documentId}/{docCategoryId}")
    public ResponseEntity<Document> deleteCategoryFromTheDocument(@PathVariable Long documentId, @PathVariable Integer docCategoryId) {
        try {
            return new ResponseEntity<>(docCategoryService.deleteCategoryFromTheDocument(documentId, docCategoryId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The category was not found");
        }
    }

    @PutMapping("/")
    public ResponseEntity<DocCategory> updateDocCategory(@RequestBody DocCategory docCategory) {
        try {
            return ResponseEntity.ok(docCategoryService.updateDocCategory(docCategory));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document was not found");
        }
    }

    @DeleteMapping("/{docCategoryId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<DocCategory> deleteDocCategory(@PathVariable Integer docCategoryId) {
        try {
            return ResponseEntity.ok(docCategoryService.deleteDocCategory(docCategoryId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document category was not found");
        }
    }

    @GetMapping("/{docCategoryId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<DocCategory> getDocCategoryById(@PathVariable Integer docCategoryId) throws EntityNotFoundException {
        try {
            return ResponseEntity.ok(docCategoryService.getDocCategoryById(docCategoryId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document category does not exist");
        }
    }

    @GetMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN')")
    public ResponseEntity<List<DocCategory>> getAllDocCategories() {
        return new ResponseEntity<>(docCategoryService.getAllDocCategories(), HttpStatus.OK);
    }

    // TODO gets all the documents of the given category, but only the first document properties are shown, the others are listed by IDs without properties
    @GetMapping("/allDocs/{categoryId}")
    public ResponseEntity<List<Document>> getAllDocsOfTheCategory(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(docCategoryService.getAllDocsOfTheCategory(categoryId), HttpStatus.OK);
    }
}
