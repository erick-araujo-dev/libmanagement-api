package com.ea.libmanagement.domain.dtos.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class BookCreateRequestDTO {
    private String title;
    private int publicationYear;
    private String publisher;
    private int editionYear;
    private int pages;
    private int ageRating;
    private short isArchived;

    private List<AuthorRequestDTO> authorRequestDTO;

    private int numberOfCopies;

}
