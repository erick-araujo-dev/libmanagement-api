package com.ea.libmanagement.application.services;

import com.ea.libmanagement.domain.dtos.request.AuthorRequestDTO;
import com.ea.libmanagement.domain.dtos.request.BookCreateRequestDTO;
import com.ea.libmanagement.domain.entities.*;
import com.ea.libmanagement.domain.enums.StatusEnum;
import com.ea.libmanagement.infrastructure.repositories.AuthorRepository;
import com.ea.libmanagement.infrastructure.repositories.BookAuthorRepository;
import com.ea.libmanagement.infrastructure.repositories.BookRepository;
import com.ea.libmanagement.infrastructure.repositories.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CopyRepository copyRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CopyRepository copyRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.copyRepository = copyRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Transactional
    public void createBook(BookCreateRequestDTO bookCreateRequestDTO) {
        Book book = new Book();
        book.setTitle(bookCreateRequestDTO.title());
        book.setIsArchived(bookCreateRequestDTO.isArchived());
        book.setPages(bookCreateRequestDTO.pages());
        book.setAgeRating(bookCreateRequestDTO.ageRating());
        book.setPublisher(bookCreateRequestDTO.publisher());
        book.setEditionYear(bookCreateRequestDTO.editionYear());
        book.setPublicationYear(bookCreateRequestDTO.publicationYear());
        bookRepository.save(book);

        for ( var authorRequest : bookCreateRequestDTO.author() ) {
            Author author = getOrCreateAuthor(authorRequest);

            BookAuthorId bookAuthorId = new BookAuthorId();
            bookAuthorId.setCdBook(book.getCdBook());
            bookAuthorId.setCdAuthor(author.getCdAuthor());

            BookAuthor bookAuthor = new BookAuthor();
            bookAuthor.setBook(book);
            bookAuthor.setAuthor(author);
            bookAuthor.setId(bookAuthorId);

            bookAuthorRepository.save(bookAuthor);
        }

        if(bookCreateRequestDTO.isArchived() == 0 && bookCreateRequestDTO.numberOfCopies() > 0) {
            for (int i = 0; i < bookCreateRequestDTO.numberOfCopies(); i++) {
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
        Optional<Author> existingAuthor = authorRepository.findByName(authorRequest.name());
        return existingAuthor.orElseGet(() -> {
            Author newAuthor = new Author();
            newAuthor.setName(authorRequest.name());
            return authorRepository.save(newAuthor);
        });
    }
}
