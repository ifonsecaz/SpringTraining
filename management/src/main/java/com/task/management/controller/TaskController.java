package com.task.management.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.task.management.entity.Task;
import com.task.management.repository.TaskRepository;
import com.task.management.service.TaskService;

@RestController
public class TaskController {
    @Autowired private TaskService taskService;
    
    @PostMapping("/tasks")
    public ResponseEntity<?> saveTask(
        @Valid @RequestBody Task task)
    {
        Task res=taskService.saveTask(task);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }


    @GetMapping("/tasks")
    public List<Task> fetchTaskList()
    {
        return taskService.fetchTaskList();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> fetchTaskById(@PathVariable("id") Long taskId)
    {
        Task res= taskService.fetchTaskByID(taskId);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
 
    @PutMapping("/tasks/{id}")
    public ResponseEntity<?>
    updateTask(@Valid @RequestBody Task task,
                     @PathVariable("id") Long taskId)
    {
        Task res= taskService.updateTask(task, taskId);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
    

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("id")
                                       Long taskId)
    {
        if(taskService.deleteTask(taskId))
            return ResponseEntity.status(HttpStatus.OK).body("Deleted succesfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
}


