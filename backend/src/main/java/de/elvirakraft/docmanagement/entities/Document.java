package de.elvirakraft.docmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
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
    private Long docId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String number;

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date conclusionDate;

    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date terminationDate;

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

    public Document(Long docId, String title, String number, Date conclusionDate,
                    Date terminationDate, String imageUrl, String textNote,boolean isDeleted, Set<User> usersWhoEditTheDocument){
        this.docId = docId;
        this.title = title;
        this.conclusionDate = conclusionDate;
        this.terminationDate = terminationDate;
        this.imageUrl = imageUrl;
        this.textNote = textNote;
        this.isDeleted = isDeleted;
        this.usersWhoEditTheDocument = usersWhoEditTheDocument;
    }

    // Removes the given user from the group of the users, who can edit the given document
    public void deleteUserFromEditorsSet(User user){
        this.usersWhoEditTheDocument.remove(user);
    }

    // Returns all users, who can edit the given document
    public Set<User> getUsersWhoEditTheDocument(Long docId){
        return this.getUsersWhoEditTheDocument();
    }

    @ManyToOne
    @JoinColumn (name = "category_id")
    private DocCategory category;

    @ManyToOne
    @JoinColumn (name = "partner_id")
    private Partner partner;
}
