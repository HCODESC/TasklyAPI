package com.hcodes.Taskly.Tasks;

import com.hcodes.Taskly.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

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

    public Page<Task> findAllTaskPaginatedAndSorted(int page, int size, String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort); 
        return taskRepository.findAll(pageable);
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
