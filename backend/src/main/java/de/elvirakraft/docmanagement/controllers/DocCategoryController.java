package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.DocCategory;
import de.elvirakraft.docmanagement.services.DocCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class DocCategoryController {

    private final DocCategoryService docCategoryService;

    @Autowired
    public DocCategoryController(DocCategoryService docCategoryService){
        this.docCategoryService = docCategoryService;
    }

    @PostMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #user.id == authentication.userId)")
    public ResponseEntity<DocCategory> addDocCategory(@RequestBody DocCategory docCategory) {
        return new ResponseEntity<>(docCategoryService.addDocCategory(docCategory), HttpStatus.CREATED);
    }

    //wenn eine Category vor dem Update bereits deleted war, bleibt nach dem Update auch gelöscht.
    // deleted-Status mitaktualisieren?
    @PutMapping("/")
    public ResponseEntity<DocCategory> updateDocCategory(@RequestBody DocCategory docCategory) {
        DocCategory updatedDocCategory = docCategoryService.updateDocCategory(docCategory);
        if (updatedDocCategory != null) {
            return new ResponseEntity<>(updatedDocCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{docCategoryId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<DocCategory> deleteDocCategory(@PathVariable Integer docCategoryId) {
        DocCategory deletedDocCategory= docCategoryService.deleteDocCategory(docCategoryId);
        if (deletedDocCategory != null) {
            return new ResponseEntity<>(deletedDocCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN')")
    public ResponseEntity<List<DocCategory>> getAllDocCategories() {
        return new ResponseEntity<>(docCategoryService.getAllDocCategories(), HttpStatus.OK);
    }

    @GetMapping("/{docCategoryId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<DocCategory> getDocCategoryById(@PathVariable Integer docCategoryId) {
        Optional<DocCategory> optionalDocCategory = docCategoryService.getDocCategoryById(docCategoryId);
        return optionalDocCategory.map(docCategory -> new ResponseEntity<>(docCategory, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
