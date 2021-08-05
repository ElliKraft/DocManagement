package de.elvirakraft.docmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity, which holds the attributes of a document
 */

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "documents")
@JsonIdentityInfo(  // to deserialize entity in bidirectional many-to-many relationship
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String number;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate conclusionDate;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate terminationDate;

    @Column
    private String imageUrl;

    @Column
    private String textNote;

    @Column
    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // A group of users, who create/edit the document
    @ManyToMany(mappedBy = "documentsOfTheUser")
    Set<User> usersWhoEditTheDocument = new HashSet<>();

    // A group of categories, which the document has
    @ManyToMany
    @JoinTable(
        name = "document_doccategory",
        joinColumns = @JoinColumn(name = "document_id"),
        inverseJoinColumns = @JoinColumn(name = "doc_category_id"))
    Set<DocCategory> docCategories = new HashSet<>();

    // A group of partners, which take part on this document
    @ManyToMany
    @JoinTable(
        name = "document_partner",
        joinColumns = @JoinColumn(name = "document_id"),
        inverseJoinColumns = @JoinColumn(name = "partner_id"))
    Set<Partner> currentPartners = new HashSet<>();

    public Document(String title, String number, LocalDate conclusionDate,
                    LocalDate terminationDate, String imageUrl, String textNote, boolean isDeleted){
        this.title = title;
        this.number = number;
        this.conclusionDate = conclusionDate;
        this.terminationDate = terminationDate;
        this.imageUrl = imageUrl;
        this.textNote = textNote;
        this.isDeleted = isDeleted;
    }

    public LocalDate getConclusionDate() {
        return this.conclusionDate;
    }

    public LocalDate getTerminationDate() {
        return this.terminationDate;
    }

    public void removeDocCategory(DocCategory category) {
        this.docCategories.remove(category);
    }

}
