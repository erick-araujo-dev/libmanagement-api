package com.ea.libmanagement.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookAuthorId implements Serializable {

    @Column(name = "cd_book")
    private Long bookId;

    @Column(name = "cd_author")
    private Long authorId;
}
