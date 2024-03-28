package com.ea.libmanagement.domain.dtos.response;

import com.ea.libmanagement.domain.dtos.request.AuthorRequestDTO;


import java.util.List;
public record BookResponseDTO(
        String title,
        int publicationYear,
        String publisher,
        int editionYear,
        int pages,
        int ageRating,
        short isArchived,
        List<AuthorRequestDTO> author) {
}
