package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long id) {
        return taskService.getTaskById(id)

                .map(ResponseEntity::ok)

                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }


    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {
        return taskService.updateTask(id, task)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {
        if (taskService.getTaskById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}