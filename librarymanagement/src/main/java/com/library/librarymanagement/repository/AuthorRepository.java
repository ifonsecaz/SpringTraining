package com.library.librarymanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Author;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>{
    @Query(value="SELECT * FROM author WHERE name=?1 AND nationality=?2 AND birth=?3", nativeQuery = true)
    Optional<Author> findByNameNationalityAndBirth(String name, String nationality, LocalDate birth);
}

