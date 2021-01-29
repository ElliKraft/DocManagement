package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.helpers.CRUDService;
import de.elvirakraft.docmanagement.models.Admin;
import de.elvirakraft.docmanagement.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    public Admin createAdmin(Admin admin){
        adminRepository.save(admin);
        return adminRepository.findById(admin.getId()).get();
    }

    public Admin getAdminByID(Long id){
        return adminRepository.findById(id).get();
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Admin updateAdmin(Admin admin){
        adminRepository.save(admin);
        return admin;
    }

    public void deleteAdmin(Admin admin){
        adminRepository.deleteById(admin.getId());
    }

}
