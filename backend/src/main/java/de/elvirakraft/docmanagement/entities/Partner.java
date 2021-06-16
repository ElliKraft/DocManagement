package de.elvirakraft.docmanagement.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Entity which holds the information about a contract partner or other business relationship participant
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
