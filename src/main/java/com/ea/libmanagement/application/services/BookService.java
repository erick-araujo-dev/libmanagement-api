package com.ea.libmanagement.application.services;

import com.ea.libmanagement.domain.dtos.request.AuthorRequestDTO;
import com.ea.libmanagement.domain.dtos.request.BookCreateRequestDTO;
import com.ea.libmanagement.domain.entities.Author;
import com.ea.libmanagement.domain.entities.Book;
import com.ea.libmanagement.domain.entities.BookAuthor;
import com.ea.libmanagement.domain.entities.Copy;
import com.ea.libmanagement.domain.enums.StatusEnum;
import com.ea.libmanagement.infrastructure.repositories.AuthorRepository;
import com.ea.libmanagement.infrastructure.repositories.BookAuthorRepository;
import com.ea.libmanagement.infrastructure.repositories.BookRepository;
import com.ea.libmanagement.infrastructure.repositories.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CopyRepository copyRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    private BookService(BookRepository bookRepository, AuthorRepository authorRepository, CopyRepository copyRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

        this.copyRepository = copyRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Transactional
    public void createBook(BookCreateRequestDTO bookCreateRequestDTO) {
        Book book = new Book();
        book.setTitle(bookCreateRequestDTO.getTitle());
        book.setIsArchived(bookCreateRequestDTO.getIsArchived());
        book.setPages(bookCreateRequestDTO.getPages());
        book.setAgeRating(bookCreateRequestDTO.getAgeRating());
        book.setPublisher(bookCreateRequestDTO.getPublisher());
        book.setEditionYear(bookCreateRequestDTO.getEditionYear());
        book.setPublicationYear(bookCreateRequestDTO.getPublicationYear());
        bookRepository.save(book);

        for ( var authorRequest : bookCreateRequestDTO.getAuthorRequestDTO() ) {
            Author author = getOrCreateAuthor(authorRequest);

            BookAuthor bookAuthor = new BookAuthor();
            bookAuthor.setBook(book);
            bookAuthor.setAuthor(author);
            bookAuthorRepository.save(bookAuthor);
        }

        if(bookCreateRequestDTO.getIsArchived() == 0 && bookCreateRequestDTO.getNumberOfCopies() > 0) {
            for (int i = 0; i < bookCreateRequestDTO.getNumberOfCopies(); i++) {
                Copy copy = new Copy();
                copy.setBook(book);
                copy.setStatus(StatusEnum.AVAILABLE.getValue());
                copy.setCondition("NEW");
                copy.setCreateDt(new Date());
                copyRepository.save(copy);
            }
        }
    }

    private Author getOrCreateAuthor(AuthorRequestDTO authorRequest) {
        Optional<Author> existingAuthor = authorRepository.findByName(authorRequest.getName());
        return existingAuthor.orElseGet(() -> {
            Author newAuthor = new Author();
            newAuthor.setName(authorRequest.getName());
            return authorRepository.save(newAuthor);
        });
    }
}