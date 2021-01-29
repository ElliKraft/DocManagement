package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.helpers.CRUDService;
import de.elvirakraft.docmanagement.helpers.RESTController;
import de.elvirakraft.docmanagement.models.User;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import de.elvirakraft.docmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
//@Api ()
@CrossOrigin()
public class UserController extends RESTController<User, UserRepository, UserService> {

    @Autowired
    private UserService userService;

    @PostMapping(value ="/api/createUser", produces = {"application/json"})
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        System.out.println(user);
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok().body(createdUser);
    }

    @GetMapping(value = "/api/getUserByID", produces = {"application/json"})
    public ResponseEntity getUserByID(@RequestBody Long id) {
        User foundUser = userService.getUserByID(id);
        return ResponseEntity.ok().body(foundUser);
    }

    @GetMapping(value = "/api/getAllUsers", produces = {"application/json"})
    public ResponseEntity getAllUsers() {
        List<User> usersList = userService.getAllUsers();
        return ResponseEntity.ok().body(usersList);
    }

    @PutMapping(value = "/api/updateUser", produces = {"application/json"})
    public ResponseEntity updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping(value = "/api/deleteUser", produces = {"application/json"})
    public ResponseEntity deleteUser(@RequestBody Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("{\"deletedUser\":\"true\"}");
    }


}
