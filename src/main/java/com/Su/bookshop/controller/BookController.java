package com.Su.bookshop.controller;


import com.Su.bookshop.model.Book;
import com.Su.bookshop.repository.BooksRepository;
import com.Su.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/allBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //get books by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
       // Optional<Book> may or may not return a null value

        if (book.isPresent()) {
            return ResponseEntity.ok().body(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //create a new book
    @PostMapping("/newBook")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId,
                                       @RequestBody Book bookdetails) {
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
}

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable(value = "id") Long bookId) {
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
    }


}
