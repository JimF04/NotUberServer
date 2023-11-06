package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Task;
import com.apiproyect.NotUberServer.Repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {
    @Autowired
    private AppRepository appRepository;

    @GetMapping(value = "/")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(value = "/tasks")
    public List<Task> getTasks(){
        return appRepository.findAll();
    }

    @PostMapping(value = "/savetask")
    public String saveTask(@RequestBody Task task){
        appRepository.save(task);
        return "Task Saved";
    }

    @PutMapping(value = "/update/{id}")
    public String updateTask(@PathVariable long id, @RequestBody Task task){
        Task updatedTask = appRepository.findById(id).get();
        updatedTask.setCompanyID(task.getCompanyID());
        updatedTask.setName(task.getName());
        updatedTask.setPassword(task.getPassword());
        updatedTask.setRole(task.getRole());
        appRepository.save(updatedTask);
        return "Task Updated";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteTask(@PathVariable long id){
        Task deletedTask = appRepository.findById(id).get();
        appRepository.delete(deletedTask);
        return "Task Deleted";
    }

}
