package com.library.librarymanagement.entity;

import java.time.LocalDate;

public class AuthorBookDTO {
    private long author_id;
    private String name;
    private String nationality;
    private LocalDate birth;
    private long book_id;
    private String title;
    private long isbn;
    private LocalDate publish_date;
    private double price;
    private String genre;

    public AuthorBookDTO(long author_id, String name, String nationality, LocalDate birth,
                         long book_id, String title, long isbn, LocalDate publish_date,
                         double price, String genre) {
        this.author_id = author_id;
        this.name = name;
        this.nationality = nationality;
        this.birth = birth;
        this.book_id = book_id;
        this.title = title;
        this.isbn = isbn;
        this.publish_date = publish_date;
        this.price = price;
        this.genre = genre;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDate publish_date) {
        this.publish_date = publish_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}