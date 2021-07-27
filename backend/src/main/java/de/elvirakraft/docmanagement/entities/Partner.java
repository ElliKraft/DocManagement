package de.elvirakraft.docmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private String contactPerson;

    @Column
    private boolean isDeleted;

    // A set of documents, which this partner takes part on
    @ManyToMany(mappedBy = "currentPartners")
    Set<Document> docsOfThePartner = new HashSet<>();

    public Partner(String name, String phone, String email,String city, String postalCode,
                   String street, String houseNumber, String contactPerson, boolean isDeleted) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.contactPerson = contactPerson;
        this.isDeleted = isDeleted;
    }

    public Set<Document> getDocsOfThePartner() {
        return this.docsOfThePartner;
    }
}
