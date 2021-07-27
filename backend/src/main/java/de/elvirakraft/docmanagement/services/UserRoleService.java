package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import de.elvirakraft.docmanagement.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserRoleService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRepository userRepository, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * Creates a user role with the given name.
     *
     * @param userRole The new user role instance.
     * @return The new user role.
     */
    public UserRole createAnyRole(UserRole userRole){
        if (userRoleRepository.findByRoleName(userRole.getRoleName()).isPresent()){
            return null;
        }
        return userRoleRepository.save(new UserRole(userRole.getRoleName()));
    }

    /**
     * Edits a user role with the given name.
     *
     * @param userRole The user role to be edited.
     */
    public UserRole editAnyRole(UserRole userRole) {
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRole.getId());
        if (optionalUserRole.isEmpty()) {
            return null;
        }

        UserRole userRoleToEdit = optionalUserRole.get();
        if (userRole.getRoleName() != null && userRoleRepository.findByRoleName(userRole.getRoleName()).isEmpty()) {
            userRoleToEdit.setRoleName(userRole.getRoleName());
        }
        return userRoleRepository.save(userRoleToEdit);
    }

    /**
     * Removes an association between a user and a user role for the following deletion of the user role
     *
     * @param roleId The id of the user role to be deleted.
     */
    public void removeUserroleUserAssociation(Integer roleId) {
        UserRole userRoleInAssoc = userRoleRepository.getById(roleId);
        for (User user: userRoleInAssoc.getUsers()) {
            user.removeRole(userRoleInAssoc);
        }
    }

    /**
     * Deletes a user role with the given id.
     *
     * @param roleId The id of the user role to be deleted.
     * @return The deleted user role.
     */
    public UserRole deleteGivenRole(Integer roleId){
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(roleId);
        if (optionalUserRole.isEmpty()) {
            return null;
        }
        UserRole userRoleToDelete = optionalUserRole.get();
        removeUserroleUserAssociation(roleId);
        userRoleRepository.delete(userRoleToDelete);
        return userRoleToDelete;
    }

    /**
     * Adds the given role to the given user.
     *
     * @param userId The id of the given user
     * @param roleId The id of the role to be given to the user.
     * @return The user with the added user role.
     */
    public User addAnyRoleToUser(Long userId, Integer roleId) throws RoleNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.addRole(userRoleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new));
        return userRepository.save(user);
    }

    /**
     * Deletes the given role from the given user
     *
     * @param userId The id of the given user
     * @return The updated user
     */
    public User deleteGivenRoleFromUser(Integer roleId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.removeRole(userRoleRepository.getById(roleId));
        return userRepository.save(user);
    }

    /**
     * Gets a user role by the given id.
     *
     * @param roleId The id of the user role to return.
     * @return The found user role.
     */
    public Optional<UserRole> getUserRoleById(Integer roleId) {
        return userRoleRepository.findById(roleId);
    }

    /**
     * Gets all user roles from the database.
     *
     * @return The list of all user roles.
     */
    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }

    /**
     * Returns a list of users, which are not deleted, for given role.
     *
     * @param roleId The id of the given role.
     * @return List of users
     */
    public List<User> findUsersByRole(Integer roleId) {
        Set<User> allUsersSet = userRoleRepository.getById(roleId).getAllUsersWithThisRole(roleId);
        allUsersSet.removeIf(User::isDeleted);
        return new ArrayList<>(allUsersSet);
    }
}
