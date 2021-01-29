package de.elvirakraft.docmanagement.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "admins")
public class Admin {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 50)
        private String name;
        private String surname;

        @Column(nullable = false, length = 100)
        private String company;

        @Column(nullable = false, length = 100)
        private String emailAddress;

        @CreationTimestamp
        @Column(updatable = false)
        private LocalDateTime creationDate;

        @UpdateTimestamp
        private LocalDateTime lastModifiedDate;
}
