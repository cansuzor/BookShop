package com.Su.bookshop.controller;

import com.Su.bookshop.model.Book;
import com.Su.bookshop.repository.BooksRepository;
import com.Su.bookshop.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/allBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            if (books.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(books);
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //get books by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") @Min(1) Long bookId) {
        try{
        Optional<Book> book = bookService.getBookById(bookId);
       // Optional<Book> may or may not return a null value

        if (book.isPresent()) {
            return ResponseEntity.ok().body(book.get());
        } else {
            return ResponseEntity.notFound().build();
          }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    //create a new book
    @PostMapping("/newBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book createdBook = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId,
                                           @RequestBody @Valid Book bookdetails) {
        try {
            Optional<Book> optionalBook = bookService.getBookById(bookId);

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                book.setBook_name(bookdetails.getBook_name());
                book.setAuthor_name(bookdetails.getAuthor_name());
                book.setGenre(bookdetails.getGenre());
                book.setBorrowed(bookdetails.getBorrowed());
                book.setReturned(bookdetails.getReturned());
                book.setPublisher(bookdetails.getPublisher());

                final Book updatedBook = bookService.updateBook(book);
                return ResponseEntity.ok(updatedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable(value = "id") @Min(1) Long bookId) {
        try {
            Optional<Book> optionalBook = bookService.getBookById(bookId);

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                bookService.deleteBook(book);

                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

