package com.task.management.service;

import java.util.List;
import java.util.Optional;

import com.task.management.entity.Task;
import com.task.management.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired 
    private TaskRepository taskRepository;
    
    //Save
    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    //Read  
    public List<Task> fetchTaskList(){
        return taskRepository.findAll();
    }

    //Read 1
    public Task fetchTaskByID(Long taskID){
        Optional<Task> res= taskRepository.findById(taskID);
        if(res.isPresent()){
            return res.get();
        }
        return null;
    }

    //Delete
    public boolean deleteTask(Long taskID){
        boolean res=false;
        if(taskRepository.findById(taskID).isPresent()){
            res=true;
            taskRepository.deleteById(taskID);
        }
        return res;
    }

    //Update
    public Task updateTask(Task task, Long taskID){
        if(taskRepository.findById(taskID).isPresent()){
            Task aux=taskRepository.findById(taskID).get();

            if(!aux.getTitle().equals(task.getTitle()))
                aux.setTitle(task.getTitle());
            if(!aux.getDescription().equals(task.getDescription()))
                aux.setDescription(task.getDescription());
            if(!aux.getStatus().equals(task.getStatus()))
                aux.setStatus(task.getStatus());
            if(aux.getCreatedDate().compareTo(task.getCreatedDate())!=0)
                aux.setCreatedDate(task.getCreatedDate());
            return taskRepository.save(aux);

        }
        return null;
    }

}
