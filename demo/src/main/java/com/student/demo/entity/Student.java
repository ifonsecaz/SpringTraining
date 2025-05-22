package com.student.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long student_id;
    private String name;
    private String email;
    private int age;

    public Student(){
    }

    public Student(String name, String email, int age){
        this.name=name;
        this.email=email;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getStudentId() {
        return student_id;
    }

    public String to_String(){
        return "\nStudentID: " + student_id + " Name: " + name + " Email: " + email + " Age: " + age;
    }

}
