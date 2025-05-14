package com.library.librarymanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{
    @Autowired 
    private AuthorRepository authorRepository;

    //Save
    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    //Read  
    public List<Author> fetchAuthorList(){
        return authorRepository.findAll();
    }

    //Read 1
    public Author fetchAuthorByID(Long authorID){
        Optional<Author> res= authorRepository.findById(authorID);
        if(res.isPresent()){
            return res.get();
        }
        return null;
    }

    public Author fetchAuthor(String name, String nationality, LocalDate birth){
        Optional<Author> res=authorRepository.findByNameNationalityAndBirth(name, nationality, birth);
        if(res.isPresent()){
            return res.get();
        }
        return null;
    }


    //Delete
    public boolean deleteAuthor(Long authorID){
        boolean res=false;
        if(authorRepository.findById(authorID).isPresent()){
            res=true;
            authorRepository.deleteById(authorID);
        }
        return res;
    }

    //Update
    public Author updateAuthor(Author author, Long authorID){
        if(authorRepository.findById(authorID).isPresent()){
            Author aux=authorRepository.findById(authorID).get();

            if(!aux.getName().equals(author.getName()))
                aux.setName(author.getName());
            if(!aux.getNationality().equals(author.getNationality()))
                aux.setNationality(author.getNationality());
            if(aux.getBirth().compareTo(author.getBirth())!=0)
                aux.setBirth(author.getBirth());
            return authorRepository.save(aux);


        }
        return null;
    }

}
