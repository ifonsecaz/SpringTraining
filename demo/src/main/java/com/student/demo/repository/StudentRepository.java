package com.student.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.student.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{

}
