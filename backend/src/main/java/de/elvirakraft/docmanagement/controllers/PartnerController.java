package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.Partner;
import de.elvirakraft.docmanagement.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Partner> addPartner(@RequestBody Partner partner){
        return new ResponseEntity<>(partnerService.addPartner(partner), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Partner> updatePartner(@RequestBody Partner partner){
        Partner updatedPartner = partnerService.updatePartner(partner);
        if(updatedPartner != null){
            return new ResponseEntity<>(updatedPartner, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{partnerId}")
    public ResponseEntity<Partner> deletePartner(@PathVariable Integer partnerId){
        Partner deletedPartner = partnerService.deletePartner(partnerId);
        if(deletedPartner != null){
            return new ResponseEntity<>(deletedPartner, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<Partner>> getAllPartners(){
        return new ResponseEntity<>(partnerService.getAllPartners(), HttpStatus.OK);
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Integer partnerId){
        Optional<Partner> optionalPartner = partnerService.getPartnerById(partnerId);
        return optionalPartner.map(partner -> new ResponseEntity<>(partner, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
