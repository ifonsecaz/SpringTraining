package com.student.demo.service;

import java.util.List;
import java.util.Optional;

import com.student.demo.entity.Student;
import com.student.demo.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired 
    private StudentRepository studentRepository;

    //Save
    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    //Read  
    public List<Student> fetchStudentList(){
        return studentRepository.findAll();
    }

    //Read 1
    public Student fetchStudentByID(Long studentID){
        Optional<Student> res=studentRepository.findById(studentID);
        if(res.isPresent())
            return res.get();
        return null;
    }

    //Delete
    public void deleteStudent(Long studentID){
        studentRepository.deleteById(studentID);
    }

    //Update
    public Student updateStudent(Student student, Long studentID){
        Student aux= studentRepository.findById(studentID).get();
        if(aux!=null){
            if(!aux.getName().equals(student.getName())){
                aux.setName(student.getName());
            }
            if(!aux.getEmail().equals(student.getEmail())){
                aux.setEmail(student.getEmail());
            }
            if(aux.getAge()!=student.getAge()){
                aux.setAge(student.getAge());
            }
            
        }
        return studentRepository.save(aux);
    } 
}
