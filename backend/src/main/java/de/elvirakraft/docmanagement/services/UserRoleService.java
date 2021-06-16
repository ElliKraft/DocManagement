package de.elvirakraft.docmanagement.services;

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
    public UserRole addAdminRole(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            return null;
        }
        return userRoleRepository.save(new UserRole(optionalUser.get(), "ADMIN"));
    }

    /**
     * Adds the 'MITARBEITER' role to the user with the given userId.
     *
     * @param userId The given userId.
     * @return The added UserRole.
     */
    public UserRole addMitarbeiterRole(Long userId){
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
    public UserRole deleteAdminRole(Long userId) {
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
    public UserRole deleteMitarbeiterRole(Long userId) {
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
    public List<UserDTO> findUsersByRole(String role) {
        // Return a List of freshly mapped UserDTOs
        return userRoleRepository.findUsersByRole(role)
            .stream()
            .map(usr -> new UserDTO(
                usr.getUser().getName(),
                usr.getUser().getSurname(),
                usr.getUser().getEmail()))
            .collect(Collectors.toList());
    }

}
