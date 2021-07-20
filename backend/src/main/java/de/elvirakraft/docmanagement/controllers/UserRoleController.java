package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityNotFoundException;
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

    @PostMapping("/")
    public ResponseEntity<UserRole> createAnyRole(@RequestBody UserRole userRole) {
        UserRole createdUserRole = userRoleService.createAnyRole(userRole);
        return new ResponseEntity<>(createdUserRole, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/{roleId}")
    public ResponseEntity<User> addGivenRoleToUser(@PathVariable Integer roleId, @PathVariable Long userId) {
        try{
            return ResponseEntity.ok(userRoleService.addAnyRoleToUser(userId,roleId));
        }catch (EntityNotFoundException e){
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User was not found");
        }catch (RoleNotFoundException e){
            System.err.println(e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role does not exist");
        }
    }

    // updates the role itself and the role in the "rolesOfTheUser" array in User entity
    @PutMapping("/editRole")
    public ResponseEntity<UserRole> editAnyRole(@RequestBody UserRole userRole) {
        UserRole userRoleToEdit = userRoleService.editAnyRole(userRole);
        if (userRoleToEdit != null) {
            return new ResponseEntity<>(userRoleToEdit, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userRoleId}")
    public ResponseEntity<UserRole> deleteGivenRole(@PathVariable Integer userRoleId) {
        UserRole deletedUserRole = userRoleService.deleteGivenRole(userRoleId);
        if (deletedUserRole != null) {
            return new ResponseEntity<>(deletedUserRole, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}/{roleId}")
    public ResponseEntity<User> deleteGivenRoleFromUser(@PathVariable Long userId, @PathVariable Integer roleId) {
        User user = userRoleService.deleteGivenRoleFromUser(roleId, userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userRoleId}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable Integer userRoleId) {
        Optional<UserRole> optionalUserRole = userRoleService.getUserRoleById(userRoleId);
        return optionalUserRole.map(userRole -> new ResponseEntity<>(userRole, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/allRoles")
    public ResponseEntity<List<UserRole>> getAllUserRoles(){
        return new ResponseEntity<>(userRoleService.getAllUserRoles(), HttpStatus.OK);
    }

    @GetMapping("/users/{roleId}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Integer roleId) {
        return new ResponseEntity<>(userRoleService.findUsersByRole(roleId), HttpStatus.OK);
    }
}
