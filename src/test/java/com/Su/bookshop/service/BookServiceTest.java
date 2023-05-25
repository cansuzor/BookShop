package com.Su.bookshop.service;
import com.Su.bookshop.model.Book;
import com.Su.bookshop.repository.BooksRepository;
import com.Su.bookshop.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    private BookService bookService;

    @Mock
    private BooksRepository booksRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(booksRepository);
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() {
        // Arrange
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        when(booksRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(books.size(), result.size());
        assertEquals(books.get(0), result.get(0));
        assertEquals(books.get(1), result.get(1));
        verify(booksRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ExistingBookId_ReturnsOptionalContainingBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();

        when(booksRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Optional<Book> result = bookService.getBookById(bookId);

        // Assert
        assertEquals(Optional.of(book), result);
        verify(booksRepository, times(1)).findById(bookId);
    }

    @Test
    void getBookById_NonExistingBookId_ReturnsEmptyOptional() {
        // Arrange
        Long nonExistingBookId = 100L;

        when(booksRepository.findById(nonExistingBookId)).thenReturn(Optional.empty());

        // Act
        Optional<Book> result = bookService.getBookById(nonExistingBookId);

        // Assert
        assertEquals(Optional.empty(), result);
        verify(booksRepository, times(1)).findById(nonExistingBookId);
    }

    @Test
    void createBook_ReturnsCreatedBook() {
        // Arrange
        Book book = new Book();

        when(booksRepository.save(book)).thenReturn(book);

        // Act
        Book result = bookService.createBook(book);

        // Assert
        assertEquals(book, result);
        verify(booksRepository, times(1)).save(book);
    }

    @Test
    void updateBook_ReturnsUpdatedBook() {
        // Arrange
        Book book = new Book();

        when(booksRepository.save(book)).thenReturn(book);

        // Act
        Book result = bookService.updateBook(book);

        // Assert
        assertEquals(book, result);
        verify(booksRepository, times(1)).save(book);
    }

    @Test
    void deleteBook_CallsRepositoryDeleteMethod() {
        // Arrange
        Book book = new Book();

        // Act
        bookService.deleteBook(book);

        // Assert
        verify(booksRepository, times(1)).delete(book);
    }
}
