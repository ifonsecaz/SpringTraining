package com.student.demo.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.student.demo.entity.Student;
import com.student.demo.repository.StudentRepository;
import com.student.demo.service.StudentService;

@RestController
public class studentController {
    @Autowired private StudentService studentService;
    
    @PostMapping("/students")
    public Student saveStudent(
        @Valid @RequestBody Student student)
    {
        return studentService.saveStudent(student);
    }


    @GetMapping("/students")
    public List<Student> fetchStudentList()
    {
        return studentService.fetchStudentList();
    }

    @GetMapping("/students/{id}")
    public Student fetchStudentById(@PathVariable("id") Long studentId)
    {
        return studentService.fetchStudentByID(studentId);
    }
 
    @PutMapping("/students/{id}")
    public Student
    updateStudent(@RequestBody Student student,
                     @PathVariable("id") Long studentId)
    {
        return studentService.updateStudent(
            student, studentId);
    }
    

    @DeleteMapping("/students/{id}")
    public String deleteStudentById(@PathVariable("id")
                                       Long studentId)
    {
        studentService.deleteStudent(
            studentId);
        return "Deleted Successfully";
    }
}
