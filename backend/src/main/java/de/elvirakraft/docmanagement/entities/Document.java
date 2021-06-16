package de.elvirakraft.docmanagement.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity, which holds the attributes of a document
 */

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String number;

    @Column
    private LocalDateTime conclusionDate;
    private LocalDateTime terminationDate;

    @Column
    private String imageUrl;

    @Column
    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn (name = "categoryId")
    private DocumentCategory category;

    @ManyToOne
    @JoinColumn (name = "partnerId")
    private Partner partner;

    @ManyToOne
    @JoinColumn (name = "createdByUser")
    private User user;
}
