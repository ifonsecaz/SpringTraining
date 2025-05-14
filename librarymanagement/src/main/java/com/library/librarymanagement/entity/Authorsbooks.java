package com.library.librarymanagement.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Authorsbooks {
    @EmbeddedId
    Authorsbookskey id;

    @ManyToOne
    @MapsId("author_id")
    @JoinColumn(name = "author_id")
    Author author; 

    @ManyToOne
    @MapsId("book_id")
    @JoinColumn(name = "book_id")
    Book book;

    public Authorsbooks() {
    }

    public Authorsbooks(Author author, Book book) {
        this.author = author;
        this.book = book;
        this.id = new Authorsbookskey(author.getAuthor_id(), book.getBook_id());
    }

    public Authorsbooks(Authorsbookskey key, Author author, Book book) {
        this.author = author;
        this.book = book;
        this.id = key;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authorsbooks that = (Authorsbooks) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
