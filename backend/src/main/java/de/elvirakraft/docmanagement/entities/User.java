package de.elvirakraft.docmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity which holds information about a user
 */
@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@JsonIdentityInfo( // to deserialize the entity in bidirectiional many-to-many relatioship
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 100)
    private String company;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column
    private boolean deleted;

    // added
    @ManyToMany
    @JoinTable(
        name = "user_userrole",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "userRole_id"))
    Set<UserRole> rolesOfTheUser;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public User(Long id, String name, String surname, String company, String email, String password, boolean deleted, Set<UserRole> rolesOfTheUser) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.company = company;
        this.email = email;
        this.password = password;
        this.deleted = deleted;
        this.rolesOfTheUser = rolesOfTheUser;
    }

    public User clone() {
        User clonedUser = new User();
        clonedUser.setName(name);
        clonedUser.setSurname(surname);
        clonedUser.setCompany(company);
        clonedUser.setEmail(email);
        clonedUser.setPassword(password);
        clonedUser.setDeleted(deleted);
        clonedUser.setRolesOfTheUser(rolesOfTheUser);
        return clonedUser;
    }

    public void addRole(UserRole userRole){
        this.rolesOfTheUser.add(userRole);
    }
}
