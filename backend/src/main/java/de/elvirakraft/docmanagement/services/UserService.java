package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import de.elvirakraft.docmanagement.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * Adds the given user to the database.
     *
     * @param user The given user.
     * @return The added user.
     */
    public User addUser(User user) {
        UserRole userRoleToAdd = userRoleRepository.findUserRoleByRoleName("DEFAULT");

        if (!userRepository.existsUserByEmail(user.getEmail())) {
            user.addRole(userRoleToAdd);
            user.setDeleted(false);
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Edits the given user.
     *
     * @param user The given user.
     * @return The edited user.
     */
    public User updateUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isEmpty()) {
            return null;
        }

        User userToUpdate = optionalUser.get();
        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getSurname() != null) userToUpdate.setSurname(user.getSurname());
        if (user.getCompany() != null) userToUpdate.setCompany(user.getCompany());
        if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
        if (user.getPassword() != null) userToUpdate.setPassword(user.getPassword());

        return userRepository.save(userToUpdate);
    }

    /**
     * Deletes the user with the given ID.
     *
     * @param id The given ID.
     * @return The deleted user.
     */
    public User deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User userToDelete = optionalUser.get();
        userToDelete.setDeleted(true);
        return userRepository.save(userToDelete);
    }

    /**
     * Returns a user with the given ID.
     *
     * @param id The given ID.
     * @return The user.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Returns all users: deleted + alive.
     *
     * @return The users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns all deleted users
     *
     * @return The deleted users
     */
    public List<User> getAllDeletedUsers() {
        return userRepository.findUsersByDeletedTrue();
    }

    /**
     * Returns all the users, that are not deleted
     *
     * @return The alive users
     */
    public List<User> getAllAliveUsers() {
        return userRepository.findUsersByDeletedFalse();
    }
}
