package de.elvirakraft.docmanagement.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private boolean isDeleted;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public User(Long id, String name, String surname, String company, String email, String password, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.company = company;
        this.email = email;
        this.password = password;
        this.isDeleted = isDeleted;
    }

    public User clone() {
        User clonedUser = new User();
        clonedUser.setName(name);
        clonedUser.setSurname(surname);
        clonedUser.setCompany(company);
        clonedUser.setEmail(email);
        clonedUser.setPassword(password);
        clonedUser.setDeleted(isDeleted);
        return clonedUser;
    }
}
