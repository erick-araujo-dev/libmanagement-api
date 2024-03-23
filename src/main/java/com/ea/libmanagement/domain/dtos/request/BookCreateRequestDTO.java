package com.ea.libmanagement.domain.dtos.request;


import java.util.List;

public record BookCreateRequestDTO(
        String title,
        int publicationYear,
        String publisher,
        int editionYear,
        int pages,
        int ageRating,
        short isArchived,
        List<AuthorRequestDTO> author,
        int numberOfCopies) {

}




