package com.logprof.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long user_id;
    @Column(nullable = false, unique=true)
    private String username;
    @Column(nullable = false, unique=true)
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth_date;

    public Users() {
    }

    public Users(String username, String email, Date birth_date) {
        this.username = username;
        this.email = email;
        this.birth_date = birth_date;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

}
