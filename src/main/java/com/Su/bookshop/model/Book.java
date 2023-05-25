package com.Su.bookshop.model;

import javax.persistence.*;

@Entity //to make JPA Entity
@Table(name = "Books2")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="book_name")
    private String book_name;

    @Column(name= "author_name")
    private String author_name;

    @Column(name= "genre")
    private String genre;

    @Column(name= "publisher")
    private String publisher;
    //constructor with args

    @Column(name= "borrowed")
    private boolean borrowed;
    @Column(name= "returned")
    private boolean returned;

    public boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public boolean getReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    //default constructor
    public Book() {
    }
    public Book(String book_name, String author_name, String genre) {
        this.book_name = book_name;
        this.author_name = author_name;
        this.genre = genre;
    }
    //getters setter to access the private fields
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Borrowing operation

    public void borrowBook() {
        if (!borrowed) {
            borrowed = true;
            System.out.println("Book '" + book_name + "' has been borrowed.");
        } else {
            System.out.println("Book '" + book_name + "' is already borrowed.");
        }
    }

    public void returnBook() {
        if (borrowed) {
            borrowed = false;
            System.out.println("Book '" + book_name + "' has been returned.");
        } else {
            System.out.println("Book '" + book_name + "' is not currently borrowed.");
        }
    }

}
