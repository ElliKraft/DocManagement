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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // A set of the roles, which the current user has
    @ManyToMany
    @JoinTable(
        name = "user_userrole",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    Set<UserRole> rolesOfTheUser = new HashSet<>();

    //
    @ManyToMany
    @JoinTable(
        name = "user_document",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "document_id"))
    Set<Document> documentsOfTheUser = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public User(String name, String surname, String company, String email, String password, boolean deleted) {
        this.name = name;
        this.surname = surname;
        this.company = company;
        this.email = email;
        this.password = password;
        this.deleted = deleted;
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

    public void addRole(UserRole userRole) {
        this.rolesOfTheUser.add(userRole);
    }
    public void removeRole(UserRole userRole) {
        this.rolesOfTheUser.remove(userRole);
    }
}
