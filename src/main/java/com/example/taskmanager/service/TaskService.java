package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks(String email) {
        return taskRepository.findByUserEmail(email);
    }

    public Optional<Task> getTaskById(Long id, String email) {
        return taskRepository.findByIdAndUserEmail(id, email);
    }

    public Task createTask(Task task, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("ユーザーが見つかりません")
                );

        task.setUser(user);

        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(
            Long id,
            Task newTask,
            String email) {

        return taskRepository.findByIdAndUserEmail(id, email)
                .map(task -> {

                    task.setTitle(newTask.getTitle());
                    task.setDescription(newTask.getDescription());
                    task.setCompleted(newTask.isCompleted());

                    return taskRepository.save(task);
                });
    }

    public boolean deleteTask(Long id, String email) {

        Optional<Task> task =
                taskRepository.findByIdAndUserEmail(id, email);

        if (task.isEmpty()) {
            return false;
        }

        taskRepository.delete(task.get());
        return true;
    }
}