package com.library.librarymanagement.service;

import java.util.List;
import java.time.LocalDate;

import com.library.librarymanagement.entity.Author;


public interface AuthorService {
    //Save
    public Author saveAuthor(Author author);

    //Read  
    public List<Author> fetchAuthorList();

    //Read 1
    public Author fetchAuthorByID(Long authorID);

    //Delete
    public boolean deleteAuthor(Long authorID);

    //Update
    public Author updateAuthor(Author author, Long authorID);

    //SearchAuthor
    public Author fetchAuthor(String name, String nationality, LocalDate birth);
}
