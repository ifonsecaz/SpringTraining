package com.library.librarymanagement.entity;


import java.time.LocalDate;

public class AuthorDTO {
    private Long author_id;
    private String name;
    private String nationality;
    private LocalDate birth;
    
    public AuthorDTO(Author author) {
        this.author_id = author.getAuthor_id();
        this.name = author.getName();
        this.nationality = author.getNationality();
        this.birth = author.getBirth();
    }
    
    
    public Long getAuthor_id() { return author_id; }
    
    public String getName() { return name; }

    public String getNationality(){ return nationality;}

    public LocalDate getBirth(){ return birth; }
}