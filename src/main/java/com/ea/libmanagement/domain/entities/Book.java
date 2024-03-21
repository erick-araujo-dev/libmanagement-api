package com.ea.libmanagement.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Entity(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_book")
    private int cdBook;

    @Column(name = "title")
    private String title;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "edition_year")
    private Integer editionYear;

    @Column(name = "pages")
    private int pages;

    @Column(name = "age_rating")
    private int ageRating;

    @Column(name = "is_archived")
    private short isArchived;

    @Column(name = "update_dt")
    private Date updateAt;
}
