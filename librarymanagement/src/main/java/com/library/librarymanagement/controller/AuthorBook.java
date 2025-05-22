package com.library.librarymanagement.controller;

import com.library.librarymanagement.entity.*;

public class AuthorBook {
    private Author author;
    private Book book;

    public AuthorBook(Author a, Book b){
        author=a;
        book=b;
    }

    public Author getAuthor(){
        return author;
    }

    public Book getBook(){
        return book;
    }
}
