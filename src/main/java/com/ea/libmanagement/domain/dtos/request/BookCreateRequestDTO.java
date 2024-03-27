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

    public void validate() {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }
        if (publicationYear < 1900 || publicationYear > 2050) {
            throw new IllegalArgumentException("Ano de publicação inválido");
        }
        if (publisher == null || publisher.isEmpty()) {
            throw new IllegalArgumentException("Editora não pode ser nula ou vazia");
        }
        if (editionYear < 1900 || editionYear > 2050) {
            throw new IllegalArgumentException("Ano de edição inválido");
        }
        if (pages <= 0) {
            throw new IllegalArgumentException("Número de páginas deve ser maior que 0");
        }
        if (ageRating < 0 || ageRating > 18) {
            throw new IllegalArgumentException("Classificação indicativa deve estar entre 0 e 18");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Autor não pode ser nulo ou vazio");
        }
        if (numberOfCopies > 0) {
            throw new IllegalArgumentException("Número de cópias deve ser maior ou igual a 0");
        }
    }
}




