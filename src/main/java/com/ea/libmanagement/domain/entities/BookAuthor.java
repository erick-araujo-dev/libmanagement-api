package com.ea.libmanagement.domain.entities;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "cd_book")
    private Book book;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "cd_author")
    private Author author;

}

