package com.library.librarymanagement.service;

import java.util.List;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.entity.AuthorBookDTO;

public interface BookService {
    //Save
    public Book saveBook(Book book);

    public Book saveBook(Book book, Author author);

    //Add author
    public Book saveBookAuthor(Long bookID, Author author);

    public Book saveBookAuthor(Long bookID, Long authorID);

    //Remove author
    public boolean deleteBookAuthor(Long bookID, Long author_id);

    //Read  
    public List<Book> fetchBookList();

    public List<AuthorBookDTO> fetchAll();

    //Read 1
    public Book fetchBookByID(Long bookID);

    //Read by title
    public List<Book> fetchBookByTitle(String title);

    //Read by author
    public List<Book> fetchBookByAuthor(String authorName);

    //Delete
    public boolean deleteBook(Long bookID);

    //Delete
    public boolean deleteAuthorRefs(Long authorID);

    //Update
    public Book updateBook(Book book, Long bookID);
}
