package com.ea.libmanagement.application.services;

import com.ea.libmanagement.domain.dtos.request.AuthorRequestDTO;
import com.ea.libmanagement.domain.dtos.request.BookCreateRequestDTO;
import com.ea.libmanagement.domain.dtos.response.BookResponseDTO;
import com.ea.libmanagement.domain.entities.*;
import com.ea.libmanagement.domain.enums.StatusEnum;
import com.ea.libmanagement.infrastructure.repositories.AuthorRepository;
import com.ea.libmanagement.infrastructure.repositories.BookAuthorRepository;
import com.ea.libmanagement.infrastructure.repositories.BookRepository;
import com.ea.libmanagement.infrastructure.repositories.CopyRepository;
import com.ea.libmanagement.shared.exceptions.BusinessException;
import com.ea.libmanagement.shared.utils.FieldValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    //region Constructors
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

    //endregion

    //region Public Methods
    @Transactional
    public void createBook(BookCreateRequestDTO bookCreateRequestDTO) {
        bookCreateRequestDTO.validate();
        validateExistingBook(bookCreateRequestDTO);

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
                copy.setCreateAt(new Date());
                copyRepository.save(copy);
            }
        }
    }

    @Transactional
    public void archiveBook(int bookId) {
        Optional<Book> bookOptional =  bookRepository.findById(bookId);

        Book book = bookOptional.orElseThrow(() -> new BusinessException("Livro não encontrado"));

        if (book.getIsArchived() == 1) {
            throw new BusinessException("Livro já está arquivado");
        }
        var copyBook = copyRepository.findByBook(book);

        for (var copies : copyBook) {
           var status =  copies.getStatus();

            if (status.equals(StatusEnum.RENTED.getValue())) {
                throw new BusinessException("Não é posssivel arquivar livro que esteja alugado");
            }
            copies.setStatus(StatusEnum.ARCHIVED.getValue());

            copyRepository.save(copies);
        }

        book.setIsArchived((short) 1);
        book.setUpdateAt(new Date());

        bookRepository.save(book);
    }

    @Transactional
    public void unArchiveBook(int bookId) {
        var bookOptional =  bookRepository.findById(bookId);

        Book book = bookOptional.orElseThrow(() -> new BusinessException("Livro não encontrado"));

        if (book.getIsArchived() == 0) {
            throw new BusinessException("Livro já está desarquivado");
        }

        var copyBook = copyRepository.findByBook(book);

        for (var copies : copyBook) {
            var status =  copies.getStatus();

            if (status.equals(StatusEnum.ARCHIVED.getValue())) copies.setStatus(StatusEnum.AVAILABLE.getValue());
            copyRepository.save(copies);
        }

        book.setIsArchived((short) 0);
        book.setUpdateAt(new Date());

        bookRepository.save(book);
    }

    public List<BookResponseDTO> getAll() {
        List<Book> bookList = bookRepository.findAll();

        List<BookResponseDTO> response = new ArrayList<>();

        bookList.forEach(book -> {
            List<AuthorRequestDTO> authors = new ArrayList<>();
            List<BookAuthor> bookAuthors = bookAuthorRepository.findByBook(book);
            bookAuthors.forEach(bookAuthor -> {
                Author author = bookAuthor.getAuthor();
                authors.add(new AuthorRequestDTO(author.getName()));
            });

            BookResponseDTO bookResponseDTO = new BookResponseDTO(
                    book.getTitle(),
                    book.getPublicationYear(),
                    book.getPublisher(),
                    book.getEditionYear(),
                    book.getPages(),
                    book.getAgeRating(),
                    book.getIsArchived(),
                    authors
            );

            response.add(bookResponseDTO);
        });

        return response;
    }

    //endregion

    //region Private Methods

    private Author getOrCreateAuthor(AuthorRequestDTO authorRequest) {
        Optional<Author> existingAuthor = authorRepository.findByName(authorRequest.name());
        return existingAuthor.orElseGet(() -> {
            Author newAuthor = new Author();
            newAuthor.setName(authorRequest.name());
            return authorRepository.save(newAuthor);
        });
    }

    private void validateExistingBook(BookCreateRequestDTO bookCreateRequestDTO) {
        List<Book> existingBook = bookRepository.findByTitleAndPublisherAndEditionYearAndPublicationYear(
                bookCreateRequestDTO.title(),
                bookCreateRequestDTO.publisher(),
                bookCreateRequestDTO.editionYear(),
                bookCreateRequestDTO.publicationYear()
        );

        existingBook.forEach(book -> {
            List<BookAuthor> existingAuthors = bookAuthorRepository.findByBook(book);

            List<String> existingAuthorNames = existingAuthors.stream()
                    .map(BookAuthor::getAuthor)
                    .map(Author::getName)
                    .toList();

            List<String> newAuthorNames = bookCreateRequestDTO.author().stream()
                    .map(AuthorRequestDTO::name)
                    .toList();

            boolean authorsMatch = new HashSet<>(existingAuthorNames).containsAll(newAuthorNames);

            if (authorsMatch) {
                throw new BusinessException("Já existe um livro cadastrado com os mesmos dados");
            }
        });
    }

    //endregion


}
