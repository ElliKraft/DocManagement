package de.elvirakraft.docmanagement.controllers;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #user.id == authentication.userId)")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User userToAdd = userService.addUser(user);
        if (userToAdd != null) {
            return new ResponseEntity<>(userToAdd, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        User deletedUser = userService.deleteUser(userId);
        if (deletedUser != null) {
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    //@PreAuthorize("isAllowed(null, 'ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    //@PreAuthorize("isAllowed(null, 'ADMIN') or (isAllowed(null, 'USER') and #userId == authentication.userId)")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/allDeleted")
    public ResponseEntity<List<User>> getAllDeletedUsers() {
        List<User> deletedUsers = userService.getAllDeletedUsers();
        if (deletedUsers.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedUsers, HttpStatus.OK);
    }

    @GetMapping("/allAlive")
    public ResponseEntity<List<User>> getAllAliveUsers() {
        List<User> aliveUsers = userService.getAllAliveUsers();
        if (aliveUsers.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(aliveUsers, HttpStatus.OK);
    }
}
