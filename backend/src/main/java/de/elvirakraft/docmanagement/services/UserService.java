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
        //User userToSave = user.clone();
        User savedUser = userRepository.save(user);
        userRoleRepository.save(new UserRole(savedUser,"USER"));
        return savedUser;
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
        if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
        if (user.getPassword() != null) userToUpdate.setPassword(user.getPassword());

        return userRepository.save(userToUpdate);
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
     * Returns all users.
     *
     * @return The users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
