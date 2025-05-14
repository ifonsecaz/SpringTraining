package com.library.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.entity.AuthorBookDTO;
import com.library.librarymanagement.repository.BookRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
    @Autowired 
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

        //Save
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Transactional
    public Book saveBook(Book book, Author author){
        Author aux=authorService.fetchAuthor(author.getName(), author.getNationality(), author.getBirth());
        if(aux==null){
            aux=authorService.saveAuthor(author);
        }
        book=bookRepository.save(book);
        
        book.addAuthor(aux);
        
        return book;
    }

    //Add author
    @Transactional
    public Book saveBookAuthor(Long bookID, Author author){
        Optional<Book> res= bookRepository.findById(bookID);
        if(res.isPresent()&&author!=null){
            Book aux=res.get();
            Author aux2=authorService.fetchAuthor(author.getName(), author.getNationality(), author.getBirth());
            if(aux2==null){
                aux2=authorService.saveAuthor(author);
            }
            aux.addAuthor(aux2);
            return aux;
        }
        return null;
    }

    //
    @Transactional
    public Book saveBookAuthor(Long bookID, Long authorID){
        Optional<Book> res= bookRepository.findById(bookID);
        Author res2=authorService.fetchAuthorByID(authorID);
        if(res.isPresent()&&res2!=null){
            Book aux=res.get();
            aux.addAuthor(res2);
            //bookRepository.save(aux);
            return aux;
        }
        return null;
    }

    //Remove author
    @Transactional
    public boolean deleteBookAuthor(Long bookID, Long author_id){
        Optional<Book> res= bookRepository.findById(bookID);
        if(res.isPresent()){
            Book aux=res.get();
            return aux.removeAuthor(author_id);
        }
        return false;
    }

    @Transactional
    public boolean deleteAuthorRefs(Long author_id){
        Author aux=authorService.fetchAuthorByID(author_id);
        if(aux!=null){
            List<Book> rem= fetchBookByAuthor(aux.getName());
            for(Book book : rem){
                book.removeAuthor(author_id);
            }
            authorService.deleteAuthor(author_id);
            return true;
        }
        return false;
    }


    //Read  
    public List<Book> fetchBookList(){
        return bookRepository.findAll();
    }

    public List<AuthorBookDTO> fetchAll(){
        return bookRepository.findAllInfo();
    }

    //Read 1
    public Book fetchBookByID(Long bookID){
        Optional<Book> res= bookRepository.findById(bookID);
        if(res.isPresent()){
            return res.get();
        }
        return null;
    }

    //Read by title
    public List<Book> fetchBookByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    //Read by author
    public List<Book> fetchBookByAuthor(String authorName){
        return bookRepository.findBookByAuthor(authorName);
    }

    //Delete
    @Transactional
    public boolean deleteBook(Long bookID){
        Optional<Book> aux= bookRepository.findById(bookID);
        if(aux.isPresent()){
            Book book=aux.get();
            book.removeAllAuthors();
            bookRepository.delete(book);
            return true;
        }
        return false;
    }

    //Update
    public Book updateBook(Book book, Long bookID){
        if(bookRepository.findById(bookID).isPresent()){
            Book aux=bookRepository.findById(bookID).get();

            if(!aux.getTitle().equals(book.getTitle()))
                aux.setTitle(book.getTitle());
            if(aux.getIsbn()!=book.getIsbn())
                aux.setIsbn(book.getIsbn());
            if(aux.getPublish_date().compareTo(book.getPublish_date())!=0)
                aux.setPublish_date(book.getPublish_date());
            if(aux.getPrice()!=book.getPrice())
                aux.setPrice(book.getPrice());
            if(!aux.getGenre().equals(book.getGenre()))
                aux.setGenre(book.getGenre());
            return bookRepository.save(aux);

        }
        return null;
    }
}
