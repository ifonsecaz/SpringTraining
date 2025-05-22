package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Authorsbooks;
import com.library.librarymanagement.entity.Authorsbookskey;

@Repository
public interface AuthorsBooksRepository extends JpaRepository<Authorsbooks,Authorsbookskey>{
    
}
