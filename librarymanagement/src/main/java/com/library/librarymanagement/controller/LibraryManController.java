package com.library.librarymanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.entity.AuthorBookDTO;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BookDTO;

import com.library.librarymanagement.service.AuthorService;
import com.library.librarymanagement.service.BookService;


@RestController
@RequestMapping("/library")
public class LibraryManController {
    @Autowired 
    private AuthorService authorService;
    @Autowired 
    private BookService bookService;

    @PostMapping("/author")
    public ResponseEntity<?> saveAuthor(
        @Valid @RequestBody Author author)
    {
        Author res=authorService.saveAuthor(author);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    @PostMapping("/book")
    public ResponseEntity<?> saveBook(
        @Valid @RequestBody Book book)
    {
        Book res=bookService.saveBook(book);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    @PostMapping("/bookAndAuthor")
    public ResponseEntity<?> saveBookAuthor(
        @Valid @RequestBody AuthorBook body)
    {
        Book res=bookService.saveBook(body.getBook(),body.getAuthor());
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    @PostMapping("/bookAndAuthor/{id}")
    public ResponseEntity<?> saveBookAuthor(
        @Valid @RequestBody Author author,
        @PathVariable("id") Long bookId)
    {
        Book res=bookService.saveBookAuthor(bookId, author);
        
        if(res!=null){
            BookDTO aux=new BookDTO(res);
            return ResponseEntity.status(HttpStatus.CREATED).body(aux);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    //PostMapping("/bookAndAuthor")
    //@RequestParam(name = "bookID") long bookId,
    //@RequestParam(name= "authorID") long authorId,
    @PostMapping("/bookAndAuthor/book/{id1}/author/{id2}")
    public ResponseEntity<?> saveBookAuthor(
        @PathVariable("id1") Long bookId,
        @PathVariable("id2") Long authorId)
    {
        Book res=bookService.saveBookAuthor(bookId, authorId);
        if(res!=null){
            BookDTO aux=new BookDTO(res);
            return ResponseEntity.status(HttpStatus.CREATED).body(aux);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    @DeleteMapping("/removeAuthorFromBook/book/{id1}/author/{id2}")
    public ResponseEntity<?> removeBookAuthor(
        @PathVariable("id1") Long bookId,
        @PathVariable("id2") Long authorId)
    {
        boolean res=bookService.deleteBookAuthor(bookId, authorId);
        if(res){
            return ResponseEntity.status(HttpStatus.CREATED).body("Author was removed succesfully");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the ID's");
        }
    }

    @GetMapping("/books")
    public List<Book> fetchBookList()
    {
        return bookService.fetchBookList();
    }

    @GetMapping("/booksInfo")
    public List<AuthorBookDTO> fetchAllInfo()
    {
        return bookService.fetchAll();
    }

    //better formating
    @GetMapping("/bookWithAuthors/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        Book aux=bookService.fetchBookByID(id);
        if(aux!=null){
            BookDTO res=new BookDTO(aux);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @GetMapping("/booksWithAuthors")
    public List<BookDTO> getAllBooks() {
        return bookService.fetchBookList().stream()
            .map(BookDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/authors")
    public List<Author> fetchAuthorList()
    {
        return authorService.fetchAuthorList();
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<?> fetchBookById(@PathVariable("id") Long bookID)
    {
        Book res= bookService.fetchBookByID(bookID);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<?> fetchAuthorById(@PathVariable("id") Long authorID)
    {
        Author res= authorService.fetchAuthorByID(authorID);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @GetMapping("/books/title")
    public List<Book> fetchBookByTitle(@RequestParam(name="title") String title)
    {
        return bookService.fetchBookByTitle(title);
    }

    @GetMapping("/books/author")
    public List<Book> fetchBookByAuthor(@RequestParam(name ="author") String author)
    {
        return bookService.fetchBookByAuthor(author);
    }
 
    @PutMapping("/books/{id}")
    public ResponseEntity<?>
    updateBook(@Valid @RequestBody Book book,
                     @PathVariable("id") Long bookId)
    {
        Book res= bookService.updateBook(book, bookId);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<?>
    updateAuthor(@Valid @RequestBody Author author,
                     @PathVariable("id") Long authorId)
    {
        Author res= authorService.updateAuthor(author, authorId);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
    

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable("id")
                                       Long bookId)
    {
        if(bookService.deleteBook(bookId))
            return ResponseEntity.status(HttpStatus.OK).body("Deleted succesfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("id")
                                       Long authorId)
    {
        if(authorService.deleteAuthor(authorId))
            return ResponseEntity.status(HttpStatus.OK).body("Deleted succesfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
}


