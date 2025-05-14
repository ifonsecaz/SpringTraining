package com.library.librarymanagement.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.AuthorBookDTO;


@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    @Query(value="SELECT * FROM book WHERE title=?1", nativeQuery = true)
    List<Book> findBookByTitle(String title);

    @Query(value="SELECT book.* FROM book INNER JOIN authorsbooks ON authorsbooks.book_id = book.book_id INNER JOIN author ON authorsbooks.author_id = author.author_id WHERE author.name=?1", nativeQuery = true)
    List<Book> findBookByAuthor(String authorName);

    //@Query(value="SELECT author.*, book.* FROM book INNER JOIN authorsbooks ON authorsbooks.book_id = book.book_id INNER JOIN author ON authorsbooks.author_id = author.author_id", nativeQuery = true)
    @Query("SELECT new com.library.librarymanagement.entity.AuthorBookDTO(" +
       "a.author_id, a.name, a.nationality, a.birth, " +
       "b.book_id, b.title, b.isbn, b.publish_date, b.price, b.genre) " +
       "FROM Author a JOIN a.books ab JOIN ab.book b")
    List<AuthorBookDTO> findAllInfo();
}
