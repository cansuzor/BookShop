package com.Su.bookshop.repository;

import com.Su.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.atomic.LongAccumulator;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
