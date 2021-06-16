package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.Role;
import de.elvirakraft.docmanagement.repositories.RoleRepository;
import de.elvirakraft.docmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository = roleRepository;
    }

    /**
     * Adds the given role to the database.
     *
     * @param role The given role.
     * @return The added role.
     */
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Deletes the role with the given ID.
     *
     * @param id The given ID.
     * @return The deleted role.
     */
    public Role deleteRole(Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            return null;
        }
        roleRepository.delete(optionalRole.get());
        return optionalRole.get();
    }

    /**
     * Returns a role with the given ID.
     *
     * @param id The given ID.
     * @return The role.
     */
    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }

     /**
     * Returns all roles.
     *
     * @return The roles.
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
