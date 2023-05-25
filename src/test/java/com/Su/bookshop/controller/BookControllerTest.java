package com.Su.bookshop.controller;

import com.Su.bookshop.model.Book;
import com.Su.bookshop.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookById_ExistingBookId_ReturnsBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    void getBookById_NonExistingBookId_ReturnsNotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    void createBook_ValidBook_ReturnsCreatedBook() {
        // Arrange
        Book book = new Book();
        when(bookService.createBook(book)).thenReturn(book);

        // Act
        Book createdBook = bookController.createBook(book);

        // Assert
        assertEquals(book, createdBook);
        verify(bookService, times(1)).createBook(book);
    }

    @Test
    void deleteBook_ExistingBookId_ReturnsDeletedResponse() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        // Act
        ResponseEntity<Map<String, Boolean>> response = bookController.deleteBook(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("deleted"));
        verify(bookService, times(1)).getBookById(bookId);
        verify(bookService, times(1)).deleteBook(book);
    }

}
