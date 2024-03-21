package com.ea.libmanagement.application.services;

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

import java.util.Optional;

public class BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;


    private  CopyRepository copyRepository;
    private BookAuthorRepository bookAuthorRepository;

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

        for ( var author : bookCreateRequestDTO.getAuthorRequestDTO() ) {

            Optional<Author> authorExited = authorRepository.findByName(author.getName());

            if(authorExited.isEmpty() ) {
                Author authorNew = new Author();

                authorNew.setName(author.getName());

                authorRepository.save(authorNew);

                BookAuthor bookAuthor = new BookAuthor();
                bookAuthor.setBook(book);
                bookAuthor.setAuthor(authorNew);

                bookAuthorRepository.save(bookAuthor);
            }


        }

        if(bookCreateRequestDTO.getIsArchived() == 0 && bookCreateRequestDTO.getNumberOfCopies() > 0) {
            for (int i = 0; i < bookCreateRequestDTO.getNumberOfCopies(); i++) {
                Copy copy = new Copy();

                copy.setBook(book);
                copy.setStatus(StatusEnum.AVAILABLE.getValue());

                copy.setCondition("NEW");
            }
        }



    }





}
