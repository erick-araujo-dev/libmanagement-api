package com.ea.libmanagement.domain.entities;
import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;

@Data
@Getter
@Entity
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "cdBook", column = @Column(nullable = false)),
            @AttributeOverride(name = "cdAuthor", column = @Column(nullable = false))
    })
    private BookAuthorId id;

    @ManyToOne
    @MapsId("cdBook")
    @JoinColumn(name = "cd_book")
    private Book book;

    @Getter
    @ManyToOne
    @MapsId("cdAuthor")
    @JoinColumn(name = "cd_author")
    private Author author;

}

