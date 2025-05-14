package com.library.librarymanagement.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long author_id;
    private String name;
    private String nationality;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Authorsbooks> books=new HashSet<>();

    public Author() {
    }

    public Author(String name, String nationality, LocalDate birth) {
        this.name = name;
        this.nationality = nationality;
        this.birth = birth;
    }

    public Set<Authorsbooks> getBooks(){
        return books;
    }

    public boolean addBook(Book book) {
        for (Authorsbooks ab : books) {
            if (ab.getBook().equals(book)) {
                return false;
            }
        }
        Authorsbooks nuevo=new Authorsbooks();
        nuevo.setAuthor(this);
        nuevo.setBook(book);    
        books.add(nuevo);
        book.addRelAuthor(nuevo);
        return true;
    }

    public void addRelBook(Authorsbooks nuevo){
        books.add(nuevo);
    }

    public void removeRelBook(Authorsbooks nuevo){
        books.remove(nuevo);
    }

    public long getAuthor_id() {
        return author_id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return author_id == author.author_id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(author_id);
    }
}
