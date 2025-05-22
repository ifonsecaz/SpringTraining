package com.student.demo.service;


import java.util.List;

import com.student.demo.entity.Student;


public interface StudentService {
    //Save
    public Student saveStudent(Student student);

    //Read  
    public List<Student> fetchStudentList();

    //Read 1
    public Student fetchStudentByID(Long studentID);
    

    //Delete
    public void deleteStudent(Long studentID);

    //Update
    public Student updateStudent(Student student, Long studentID);
}
