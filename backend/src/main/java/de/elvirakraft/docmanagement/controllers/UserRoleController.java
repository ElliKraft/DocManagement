package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.models.UserDTO;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
    }

    @PostMapping("/admin/{userId}")
    public ResponseEntity<UserRole> addAdminRole(@PathVariable Long userId) {
        UserRole userRole = userRoleService.addAdminRole(userId);
        if (userRole != null) {
            return new ResponseEntity<>(userRole, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/mitarbeiter/{userId}")
    public ResponseEntity<UserRole> addMitarbeiterRole(@PathVariable Long userId) {
        UserRole userRole = userRoleService.addMitarbeiterRole(userId);
        if (userRole != null) {
            return new ResponseEntity<>(userRole, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/admin/{userId}")
    //@PreAuthorize("isAllowed('ADMIN')")
    public ResponseEntity<UserRole> deleteAdminRole(@PathVariable Long userId) {
        UserRole userRole = userRoleService.deleteAdminRole(userId);
        if (userRole != null) {
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/mitarbeiter/{userId}")
    //@PreAuthorize("isAllowed('MITARBEITER')")
    public ResponseEntity<UserRole> deleteMitarbeiterRole(@PathVariable Long userId) {
        UserRole userRole = userRoleService.deleteMitarbeiterRole(userId);
        if (userRole != null) {
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint which returns a list of users for the given role.
     * @param role The given role.
     * @return list of users
     */
    @GetMapping("/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        return new ResponseEntity<>(userRoleService.findUsersByRole(role), HttpStatus.OK);
    }
}
