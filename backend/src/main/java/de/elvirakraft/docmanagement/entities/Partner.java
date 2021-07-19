package de.elvirakraft.docmanagement.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Entity which holds the information about a business relationship partner (contract, etc.)
 */
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String city;

    @Column
    private String postalCode;
    private String street;
    private String houseNumber;

    @Column
    private boolean isDeleted;
}
