package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.application.services.BookService;
import com.ea.libmanagement.domain.dtos.request.BookCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookCreateRequestDTO bookRequestDTO) {
        try {
            bookService.createBook(bookRequestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
