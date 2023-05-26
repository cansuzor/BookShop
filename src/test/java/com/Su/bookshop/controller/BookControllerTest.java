package com.Su.bookshop.controller;

import com.Su.bookshop.model.Book;
import com.Su.bookshop.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    @Test
    void updateBook_ExistingBookId_ReturnsUpdatedBook() {
        // Arrange
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);

        Book updatedBookDetails = new Book();
        updatedBookDetails.setBook_name("Updated Book Name");
        updatedBookDetails.setAuthor_name("Updated Author Name");
        // Set other updated book details

        Optional<Book> optionalBook = Optional.of(existingBook);
        when(bookService.getBookById(bookId)).thenReturn(optionalBook);
        when(bookService.updateBook(existingBook)).thenReturn(updatedBookDetails);

        // Act
        ResponseEntity<Book> response = bookController.updateBook(bookId, updatedBookDetails);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBookDetails, response.getBody());
        verify(bookService, times(1)).getBookById(bookId);
        verify(bookService, times(1)).updateBook(existingBook);
    }

    @Test
    void getBookById_NonExistingBookId_ReturnsNotFound2() {
        // Arrange
        Long nonExistingBookId = 100L;
        when(bookService.getBookById(nonExistingBookId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Book> response = bookController.getBookById(nonExistingBookId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(nonExistingBookId);
    }
    public void setup2() {
        // Mock the behavior of the bookService's createBook method
        when(bookService.createBook(Mockito.any(Book.class))).thenReturn(new Book());
    }
    @Test
    void createbook_goodinput(){
        //Arrange
        setup2();
        Book newbook = new Book();
        newbook.setId(67);
        newbook.setBook_name("Test Book");
        newbook.setAuthor_name("Test Author");
        newbook.setReturned(false);

        //Act
        ResponseEntity<Book> response = bookController.createBook(newbook);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //assertEquals(newbook.toString(), response.getBody().toString());



    }


}
