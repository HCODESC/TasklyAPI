package com.hcodes.Taskly.Tasks;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Task>> getPaginatedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Page<Task> pagedTasks = taskService.findAllTaskPaginatedAndSorted(page, size, sortBy, direction);
        return ResponseEntity.ok(pagedTasks);
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
