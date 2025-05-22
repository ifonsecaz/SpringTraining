package com.task.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.task.management.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{

}

