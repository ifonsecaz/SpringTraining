package com.library.librarymanagement.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class BookDTO {
    private Long book_id;
    private String title;
    private Long isbn;
    private LocalDate publish_date;
    private Double price;
    private String genre;
    private Set<AuthorDTO> authorsList;
    
    public BookDTO(Book book) {
        this.book_id = book.getBook_id();
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.publish_date = book.getPublish_date();
        this.price = book.getPrice();
        this.genre = book.getGenre();

        authorsList=new HashSet<>();
        
        Set<Authorsbooks> aux=book.getAuthors();
        Iterator<Authorsbooks> it =aux.iterator();
        AuthorDTO transferA;
        
        while(it.hasNext()){
            transferA=new AuthorDTO(it.next().getAuthor());
            authorsList.add(transferA);
        }
    }
    
    public Long getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public Long getIsbn() {
        return isbn;
    }

    public LocalDate getPublish_date() {
        return publish_date;
    }

    public Double getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    public Set<AuthorDTO> getAuthors() {
        return authorsList;
    }
}