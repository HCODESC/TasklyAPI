package com.hcodes.Taskly.Tasks;

// Import statements
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    // Mock the repository dependency
    @Mock
    private TaskRepository taskRepository;

    // Inject the mock into the service
    @InjectMocks
    private TaskService taskService;

    /**
     * Test for findAll() method.
     * Verifies that findAll returns the expected list of tasks.
     */
    @Test
    void findAll() {
        // Arrange: Create a sample list of tasks
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act: Call the service method
        List<Task> result = taskService.findAll();

        // Assert: Verify the result and repository call
        assertEquals(2, result.size());
        verify(taskRepository).findAll();
    }

    /**
     * Test for findById() method.
     * Verifies that the method returns the correct task when found.
     */
    @Test
    void findById() {
        // Arrange: Create a sample task with id 1
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act: Call the service method
        Task result = taskService.findById(taskId);

        // Assert: Verify that the returned task matches expectations
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        verify(taskRepository).findById(taskId);
    }

    /**
     * Test for create() method.
     * Verifies that a task is saved properly.
     */
    @Test
    void create() {
        // Arrange: Create a sample task (with no id yet)
        Task task = new Task();
        task.setName("New Task");
        when(taskRepository.save(task)).thenReturn(task);

        // Act: Call the service method
        Task result = taskService.create(task);

        // Assert: Verify the task was saved
        assertNotNull(result);
        assertEquals("New Task", result.getName());
        verify(taskRepository).save(task);
    }

    /**
     * Test for update() method.
     * Verifies that an existing task is updated correctly.
     */
    @Test
    void update() {
        // Arrange: Create a sample existing task
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setName("Old Name");

        // Simulate finding the task by id
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Create an updated task payload (with same id)
        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setName("New Name");
        // When saving, return the updated task
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Act: Call the service update method
        Task result = taskService.update(updatedTask);

        // Assert: Verify that the task's name was updated
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);
    }

    /**
     * Test for delete() method.
     * Verifies that an existing task is deleted.
     */
    @Test
    void delete() {
        // Arrange: Create a sample task and assume its id is 1
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act: Call the service delete method (assuming it takes an id)
        taskService.delete(taskId);

        // Assert: Verify that the delete method was called on the repository
        verify(taskRepository).delete(task);
    }

    /**
     * Test for deleteAll() method.
     * Verifies that deleteAll is called on the repository.
     */
    @Test
    void deleteAll() {
        // Act: Call the service deleteAll method
        taskService.deleteAll();

        // Assert: Verify that the repository's deleteAll method was invoked
        verify(taskRepository).deleteAll();
    }

    /**
     * Test for findByStatus() method.
     * Verifies that tasks are fetched correctly based on status.
     */
    @Test
    void findByStatus() {
        // Arrange: Define a status and a sample list of tasks
        TaskStatus status = TaskStatus.COMPLETED;
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findByStatus(status)).thenReturn(tasks);

        // Act: Call the service method
        List<Task> result = taskService.findByStatus(status);

        // Assert: Verify that the correct tasks are returned
        assertEquals(2, result.size());
        verify(taskRepository).findByStatus(status);
    }
}