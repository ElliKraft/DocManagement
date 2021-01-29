package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.models.Admin;
import de.elvirakraft.docmanagement.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping(value ="/api/createAdmin", produces = {"application/json"})
    public ResponseEntity createAdmin(@Valid @RequestBody Admin admin) {
        System.out.println(admin);
        Admin createdAdmin = adminService.createAdmin(admin);
        return ResponseEntity.ok().body(createdAdmin);
    }

    @GetMapping(value = "/api/getAdminByID", produces = {"application/json"})
    public ResponseEntity getAdminByID(@RequestBody Long id) {
        Admin foundAdmin = adminService.getAdminByID(id);
        return ResponseEntity.ok().body(foundAdmin);
    }

    @GetMapping(value = "/api/getAllAdmins", produces = {"application/json"})
    public ResponseEntity getAllAdmins() {
        List<Admin> AdminsList = adminService.getAllAdmins();
        return ResponseEntity.ok().body(AdminsList);
    }

    @PutMapping(value = "/api/updateAdmin", produces = {"application/json"})
    public ResponseEntity updateAdmin(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.updateAdmin(admin);
        return ResponseEntity.ok().body(updatedAdmin);
    }

    @DeleteMapping(value = "/api/deleteAdmin", produces = {"application/json"})
    public ResponseEntity deleteAdmin(@RequestBody Admin admin) {
        adminService.deleteAdmin(admin);
        return ResponseEntity.ok().body("{\"deletedAdmin\":\"true\"}");
    }
}
