package com.Su.bookshop.service;

import com.Su.bookshop.model.Book;
import com.Su.bookshop.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BooksRepository booksRepository;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<Book> getBookById(Long bookId) {
        return booksRepository.findById(bookId);
    }

    public Book createBook(Book book) {
        return booksRepository.save(book);
    }

    public Book updateBook(Book book) {
        return booksRepository.save(book);
    }

    public void deleteBook(Book book) {
        booksRepository.delete(book);
    }
}
