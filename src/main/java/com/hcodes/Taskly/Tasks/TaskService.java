package com.hcodes.Taskly.Tasks;

import com.hcodes.Taskly.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
       return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task id " + id + " not found!"));
    }

    public Task create(@Valid Task task) {
        return taskRepository.save(task);
    }

    public Task update(@Valid Task task) {
        if(task.getId() == 0){
            throw new IllegalArgumentException("Task id can not be zero!");
        }

        Task existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task id " + task.getId() + " not found!"));


        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setCreateDate(task.getCreateDate());
        existingTask.setDueDate(task.getDueDate());


        return taskRepository.save(existingTask);
    }


    public void delete(Long id) {
        Task existingTask = taskRepository.
                findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task id " + id + " not found!"));

        taskRepository.delete(existingTask);
    }

    public void deleteAll() {
        taskRepository.deleteAll();
    }


    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
}
