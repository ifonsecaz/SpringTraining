package com.library.librarymanagement.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Authorsbookskey implements Serializable{
    private Long book_id;
    private Long author_id;

    //composite key

    public Authorsbookskey(){

    }

    public Authorsbookskey(Long bookID, Long authorID){
        book_id=bookID;
        author_id=authorID;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authorsbookskey)) return false;
        Authorsbookskey that = (Authorsbookskey) o;
        return Objects.equals(book_id, that.book_id) &&
               Objects.equals(author_id, that.author_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, author_id);
    }
}
