package de.elvirakraft.docmanagement.services;

import com.sun.xml.bind.v2.TODO;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.models.UserDTO;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import de.elvirakraft.docmanagement.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Adds the 'ADMIN' role to the user with the given userId.
     *
     * @param userId The given userId.
     * @return The added UserRole.
     */
    /*public UserRole addAdminRole(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            return null;
        }
        //return userRoleRepository.save(new UserRole(optionalUser.get(), "ADMIN"));
        return userRoleRepository.save(new UserRole(optionalUser.get(), "ADMIN"));
    }*/

    // test
   /* public UserRole addAdminRoleToUser(User user){
        UserRole userRoleToEdit = userRoleRepository.findUserRoleByUser(user);
        userRoleToEdit.getUsers().add(user);
        return userRoleToEdit;
    }*/

    public User addAnyRoleToUser(User user, String roleName){
        user.addRole(userRoleRepository.findUserRoleByRoleName(roleName));
        return userRepository.save(user);
        //return user.addRole(userRoleRepository.findUserRoleByRoleName("ADMIN"));
    }

    public UserRole createAnyRole(String roleName){
        if (userRoleRepository.findByRoleName(roleName).isPresent()){
            return null;
        }
        return userRoleRepository.save(new UserRole(roleName));
    }

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
        /**
         * TODO return a string with an error message, that the role already exists
         */
    }

    public void removeUserroleUserAssociation(Integer userRoleId) {
        UserRole userRoleInAssoc = userRoleRepository.getById(userRoleId);
        for (User user: userRoleInAssoc.getUsers()) {
            userRoleInAssoc.deleteUserFromUsers(user);
        }
    }

    public Optional<UserRole> deleteAnyRole(Integer roleId){
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(roleId);
        if (optionalUserRole.isEmpty()) {
            return optionalUserRole;
        }
        UserRole userRoleToDelete = optionalUserRole.get();
        removeUserroleUserAssociation(roleId);
        userRoleRepository.delete(userRoleToDelete);
        return userRoleRepository.findById(userRoleToDelete.getId());
    }



    /**
     * Adds the 'MITARBEITER' role to the user with the given userId.
     *
     * @param userId The given userId.
     * @return The added UserRole.
     */
 /*   public UserRole addMitarbeiterRole(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            return null;
        }
        return userRoleRepository.save(new UserRole(optionalUser.get(), "MITARBEITER"));
    }

    /**
     * Deletes ADMIN role from the user with the given userId, when the user is deleted.
     *
     * @param userId The given userId.
     * @return The deleted UserRole.
     */
 /*   public UserRole deleteAdminRole(Long userId) {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRole(userId, "ADMIN");
        if (optionalUserRole.isEmpty()) {
            return null;
        }
        userRoleRepository.delete(optionalUserRole.get());
        return optionalUserRole.get();
    }

    /**
     * Deletes MITARBEITER role from the user with the given userId, when the user is deleted.
     *
     * @param userId The given userId.
     * @return The deleted UserRole.
     */
 /*   public UserRole deleteMitarbeiterRole(Long userId) {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRole(userId, "MITARBEITER");
        if (optionalUserRole.isEmpty()) {
            return null;
        }
        userRoleRepository.delete(optionalUserRole.get());
        return optionalUserRole.get();
    }

    /**
     * Returns a list of users for given role.
     *
     * @param role The given role.
     * @return List of users
     */
 /*   public List<UserDTO> findUsersByRole(String role) {
        // Return a List of freshly mapped UserDTOs
        return userRoleRepository.findUsersByRole(role)
            .stream()
            .map(usr -> new UserDTO(
                usr.getUser().getName(),
                usr.getUser().getSurname(),
                usr.getUser().getEmail()))
            .collect(Collectors.toList());
    }
*/
}
