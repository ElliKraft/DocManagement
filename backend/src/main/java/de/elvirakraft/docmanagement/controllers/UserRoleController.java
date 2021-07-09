package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.models.UserDTO;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
    }


    @PostMapping("/default")
    public ResponseEntity<UserRole> createDefaultRole() {
        UserRole createdDefaultRole = userRoleService.createAnyRole("DEFAULT");
        return new ResponseEntity<>(createdDefaultRole, HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserRole> createAdminRole() {
        UserRole createdAdminRole = userRoleService.createAnyRole("ADMIN");
        return new ResponseEntity<>(createdAdminRole, HttpStatus.CREATED);
    }

    @PostMapping("/employee")
    public ResponseEntity<UserRole> createEmployeeRole() {
        UserRole createdMitarbeiterRole = userRoleService.createAnyRole("EMPLOYEE");
        return new ResponseEntity<>(createdMitarbeiterRole, HttpStatus.CREATED);
    }

    // test
    @PostMapping("/admin/{user}")
    public ResponseEntity<User> addAdminRoleToUser(@PathVariable User user) {
        User updatedUser = userRoleService.addAnyRoleToUser(user, "ADMIN");
        if (updatedUser != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
            }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/employee/{user}")
    public ResponseEntity<User> addEmployeeRoleToUser(@PathVariable User user) {
        User updatedUser = userRoleService.addAnyRoleToUser(user, "EMPLOYEE");
        if (updatedUser != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // updates the role itself and the role in the "rolesOfTheUser" array in User entity
    @PutMapping("/editRole")
    public ResponseEntity<UserRole> editAnyRole(@RequestBody UserRole userRole) {
        UserRole userRoleToEdit = userRoleService.editAnyRole(userRole);
        return new ResponseEntity<>(userRoleToEdit, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<UserRole> deleteAnyRole(@PathVariable Integer roleId) {
        Optional<UserRole> optionalUserRole = userRoleService.deleteAnyRole(roleId);
        if (optionalUserRole.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }








/*    @PostMapping("/admin/{userId}")
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
/*    @GetMapping("/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        return new ResponseEntity<>(userRoleService.findUsersByRole(role), HttpStatus.OK);
    }

 */
}
