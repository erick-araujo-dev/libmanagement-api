package com.ea.libmanagement.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookAuthorId implements Serializable {

    @Column(name = "cd_book")
    private int cdBook;

    @Column(name = "cd_author")
    private int cdAuthor;
}
