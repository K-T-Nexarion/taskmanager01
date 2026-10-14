package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> updateTask(Long id, Task newTask) {
    // 指定されたidのTaskをDBから探す。
    return taskRepository.findById(id)
            .map(task -> {

                task.setTitle(newTask.getTitle());

                task.setDescription(newTask.getDescription());

                task.setCompleted(newTask.isCompleted());

                return taskRepository.save(task);
            });
}
}