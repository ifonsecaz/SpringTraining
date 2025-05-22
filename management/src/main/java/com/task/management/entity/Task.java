package com.task.management.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long task_id;
    @Column(nullable = false)
    private String title;
    private String description;
    private String status;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    public Task() {
    }

    public Task(String title, String description, String status, Date createdDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
    }

    public long getTask_id() {
        return task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
