package com.library.librarymanagement.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long book_id;
    private String title;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //si se quiere imprimir con autores, se usa DTO
    private Set<Authorsbooks> authors=new HashSet<>();
    @Column(nullable = false, unique=true)
    private long isbn;
    @Column(name = "publish_date", columnDefinition = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publish_date;
    private double price;
    private String genre;

    public Book() {
    }

    public Book(String title, int isbn, LocalDate publish_date, double price, String genre) {
        this.title = title;
        this.isbn = isbn;
        this.publish_date = publish_date;
        this.price=price;
        this.genre=genre;
    }

    public boolean addAuthor(Author author){
        for (Authorsbooks ab : authors) {
            if (ab.getAuthor().equals(author)) {
                return false;
            }
        }
        Authorsbooks nuevo=new Authorsbooks(author,this);
        authors.add(nuevo);
        author.addRelBook(nuevo);
        return true;
    }

    public boolean removeAuthor(Long author_id){
        boolean res=false;
        Authorsbooks aux=null;
        Author author=null;
        for (Authorsbooks ab : authors) {
            if (ab.getAuthor().getAuthor_id()==author_id) {
                res=true;
                aux=ab;
                author=ab.getAuthor();
                break;
            }
        }
        if(aux!=null&&author!=null){
            authors.remove(aux);
            author.removeRelBook(aux);
        }
        return res;
    }

    public void removeAllAuthors(){
        Authorsbooks aux=null;
        Iterator<Authorsbooks> it=authors.iterator();
        
        while(it.hasNext()){
            aux=it.next();
            aux.getAuthor().removeRelBook(aux);
            it.remove();
        }
    }

    public void addRelAuthor(Authorsbooks nuevo){
        authors.add(nuevo);
    }

    public long getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Authorsbooks> getAuthors() {
        return authors;
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

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre=genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Book book = (Book) o;
        return book_id == book.book_id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(book_id);
    }
}
