package de.elvirakraft.docmanagement.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_roles")
@JsonIdentityInfo(  // to deserialize entity in bidirectiional many-to-many relatioship
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // A group of users, having the current role
    @ManyToMany(mappedBy = "rolesOfTheUser")
    @JsonIgnore
    Set<User> users;

    @Column(nullable = false, length = 50)
    private String roleName;

    public UserRole(String roleName){
        this.users = new HashSet<>();
        this.roleName = roleName;
    }

    // Removes the current role from the given user
    public void deleteUserFromUsers(User user){
        this.users.remove(user);
    }

    // Returns all users, having the current role
    public Set<User> getAllUsersWithThisRole(Integer roleId){
        return this.getUsers();
    }
}
