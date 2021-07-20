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
import java.util.Date;
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
@JsonIdentityInfo(  // to deserialize entity in bidirectiional many-to-many relatioship
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
    @JsonIgnore
    Set<User> usersWhoEditTheDocument = new HashSet<>();

    public Document(String title, String number, LocalDate conclusionDate,
                    LocalDate terminationDate, String imageUrl, String textNote, boolean isDeleted){
        this.number = number;
        this.title = title;
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

    // Removes the given user from the group of the users, who can edit the given document
    public void deleteUserFromEditorsSet(User user){
        this.usersWhoEditTheDocument.remove(user);
    }

    public void addUserToEditorsSet(User user) {
        usersWhoEditTheDocument.add(user);
    }

    /*@ManyToOne
    @JoinColumn (name = "category_id")
    private DocCategory category;

    @ManyToOne
    @JoinColumn (name = "partner_id")
    private Partner partner;

     */
}
