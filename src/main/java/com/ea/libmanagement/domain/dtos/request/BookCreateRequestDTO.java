package com.ea.libmanagement.domain.dtos.request;

import jakarta.persistence.Column;

public class BookCreateRequestDTO {
    private String title;
    private int publicationYear;
    private String publisher;
    private Integer editionYear;
    private int pages;
    private int ageRating;
    private boolean archived;
}
