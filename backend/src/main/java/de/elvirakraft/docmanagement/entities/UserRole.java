package de.elvirakraft.docmanagement.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "userRoles")
@JsonIdentityInfo(  // to deserialize entity in bidirectiional many-to-many relatioship
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    // test
    @ManyToMany(mappedBy = "rolesOfTheUser")
    @JsonIgnore
    Set<User> users;

    //@Column(nullable = false, length = 50)
    private String roleName;

    public UserRole(String roleName){
        this.users = new HashSet<>();
        this.roleName = roleName;
    }

    public void deleteUserFromUsers(User user){
        this.users.remove(user);
    }
}
