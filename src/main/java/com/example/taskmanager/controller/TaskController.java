package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {

        String email = authentication.getName();

        return taskService.getAllTasks(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return taskService.getTaskById(id, email)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody Task task,
            Authentication authentication) {

        String email = authentication.getName();

        Task createdTask =
                taskService.createTask(task, email);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task,
            Authentication authentication) {

        String email = authentication.getName();

        return taskService.updateTask(id, task, email)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        boolean deleted =
                taskService.deleteTask(id, email);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}