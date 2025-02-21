package com.hcodes.Taskly.Tasks;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public List<Task> findAllTasks(){
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task findOneTask(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.create(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Task updateTask(@Valid @RequestBody Task task, @PathVariable Long id) {
        return taskService.update(task);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }

    @DeleteMapping("/delete/all")
    public void deleteAllTasks() {
        taskService.deleteAll();
    }

    @GetMapping("/status/{status}")
    public List<Task> findAllTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.findByStatus(status);
    }
}
