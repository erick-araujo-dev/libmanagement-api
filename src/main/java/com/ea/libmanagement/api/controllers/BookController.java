package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.application.services.BookService;
import com.ea.libmanagement.domain.dtos.request.BookCreateRequestDTO;
import com.ea.libmanagement.shared.exceptions.BusinessException;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de negócio: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<?> archiveBook(@PathVariable int id) {
        try {
            bookService.archiveBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livro arquivado");

        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de negócio: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @PostMapping("/un-archive/{id}")
    public ResponseEntity<?> unArchiveBook(@PathVariable int id) {

        try {
            bookService.unArchiveBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livro desarquivado");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de negócio: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }
}
