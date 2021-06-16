package de.elvirakraft.docmanagement.entities;

import de.elvirakraft.docmanagement.entities.User;
import lombok.*;

import javax.persistence.*;
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "userRoles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role;

    public UserRole(User user, String role){
        this.user = user;
        this.role = role;
    }
}
