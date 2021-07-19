package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.Partner;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository){
        this.partnerRepository = partnerRepository;
    }

    /**
     * Adds the given partner to the database.
     *
     * @param partner The given partner.
     * @return The added partner.
     */
    public Partner addPartner(Partner partner) {
        //Partner savedPartner = partnerRepository.save(partner);
        //partnerRepository.save(new Partner(savedPartner));
        return partnerRepository.save(partner);
    }

    /**
     * Edits the given partner.
     *
     * @param partner The given partner.
     * @return The edited partner.
     */
    public Partner updatePartner(Partner partner) {
        Optional<Partner> optionalPartner = partnerRepository.findById(partner.getId());
        if (optionalPartner.isEmpty()) {
            return null;
        }

        Partner partnerToUpdate = optionalPartner.get();
        if (partner.getName() != null) partnerToUpdate.setName(partner.getName());
        if (partner.getPhone() != null) partnerToUpdate.setPhone(partner.getPhone());
        if (partner.getEmail() != null) partnerToUpdate.setEmail(partner.getEmail());
        if (partner.getCity() != null) partnerToUpdate.setCity(partner.getCity());
        if (partner.getPostalCode() != null) partnerToUpdate.setPostalCode(partner.getPostalCode());
        if (partner.getStreet() != null) partnerToUpdate.setStreet(partner.getStreet());
        if (partner.getHouseNumber() != null) partnerToUpdate.setHouseNumber(partner.getHouseNumber());

        return partnerRepository.save(partnerToUpdate);
    }

    /**
     * Deletes the partner with the given ID.
     *
     * @param id The given ID.
     * @return The deleted partner.
     */
    public Partner deletePartner(Integer id) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        if (optionalPartner.isEmpty()) {
            return null;
        }

        Partner partnerToDelete = optionalPartner.get();
        partnerToDelete.setDeleted(true);
        return partnerRepository.save(partnerToDelete);
    }

    /**
     * Returns the partner with the given ID.
     *
     * @param id The given ID.
     * @return The partner.
     */
    public Optional<Partner> getPartnerById(Integer id) {
        return partnerRepository.findById(id);
    }

    /**
     * Returns all partners.
     *
     * @return The partners.
     */
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }


}
