package de.elvirakraft.docmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity, which holds the attributes of a document category
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "doc_categories")
@JsonIdentityInfo(  // to deserialize entity in bidirectiional many-to-many relatioship
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class DocCategory {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    // A group of documents, that have this category
    @ManyToMany(mappedBy = "docCategories")
    //@JsonIgnore
    Set<Document> documentsOfTheCategory = new HashSet<>();

    public Set<Document> getDocumentsOfTheCategory() {
        return this.documentsOfTheCategory;
    }
}
