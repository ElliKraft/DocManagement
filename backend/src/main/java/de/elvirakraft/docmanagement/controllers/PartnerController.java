package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.entities.Partner;
import de.elvirakraft.docmanagement.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("partners")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService){
        this.partnerService = partnerService;
    }

    @PostMapping("/")
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner){
        try {
            return ResponseEntity.ok(partnerService.createPartner(partner));
        } catch (EntityExistsException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given partner already exists");
        }
    }

    @PostMapping("/{documentId}/{partnerId}")
    public ResponseEntity<Document> addPartnerToTheDocument(@PathVariable Long documentId, @PathVariable Integer partnerId) {
        try {
            return ResponseEntity.ok(partnerService.addPartnerToTheDocument(documentId, partnerId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document was not found");
        } catch (RoleNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner does not exist");
        }
    }

    @DeleteMapping("/{documentId}/{partnerId}")
    public ResponseEntity<Document> removePartnerFromTheDocument(@PathVariable Long documentId, @PathVariable Integer partnerId) {
        Document document = partnerService.deleteGivenPartnerFromDocument(documentId, partnerId);
        if (document != null) {
            return new ResponseEntity<>(document, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<Partner> updatePartner(@RequestBody Partner partner) {
        try {
            return ResponseEntity.ok(partnerService.updatePartner(partner));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner was not found");
        }
    }

    //TODO by deleting a partner it should be removed from the "currentPartners" array of a document
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<Partner> deletePartner(@PathVariable Integer partnerId) {
        try {
            return ResponseEntity.ok(partnerService.deletePartner(partnerId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner does not exist");
        }
    }


    @GetMapping("/{partnerId}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Integer partnerId) {
        try {
            return ResponseEntity.ok(partnerService.getPartnerById(partnerId));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner does not exist");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Partner>> getAllPartners(){
        return new ResponseEntity<>(partnerService.getAllPartners(), HttpStatus.OK);
    }

    // TODO not working
    @GetMapping("/allDocs/{partnerId}")
    public ResponseEntity<List<Document>> getAllDocumentsOfThePartner(@PathVariable Integer partnerId) {
        return new ResponseEntity<>(partnerService.getAllDocumentsOfThePartner(partnerId), HttpStatus.OK);
    }
}
