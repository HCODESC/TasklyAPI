package com.hcodes.Taskly.Tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Inject a mock of the TaskService into the controller context.
    @MockBean
    private TaskService taskService;

    // ObjectMapper for JSON serialization/deserialization.
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test for GET /api/tasks
     * Verifies that retrieving all tasks returns the expected list.
     */
    @Test
    void testFindAllTasks() throws Exception {
        // Arrange: create sample tasks
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task One");
        task1.setCreateDate(LocalDateTime.now());
        task1.setDueDate(LocalDateTime.now().plusDays(1));

        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task Two");
        task2.setCreateDate(LocalDateTime.now());
        task2.setDueDate(LocalDateTime.now().plusDays(2));

        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskService.findAll()).thenReturn(tasks);

        // Act & Assert: perform GET request and verify response status and content.
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(tasks.size()))
                .andExpect(jsonPath("$[0].name").value("Task One"))
                .andExpect(jsonPath("$[1].name").value("Task Two"));
    }

    /**
     * Test for GET /api/tasks/{id}
     * Verifies that retrieving a single task by id returns the expected task.
     */
    @Test
    void testFindTaskById() throws Exception {
        // Arrange: create a sample task with id 1
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setName("Task One");
        task.setCreateDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));
        when(taskService.findById(taskId)).thenReturn(task);

        // Act & Assert: perform GET request and verify response.
        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.name").value("Task One"));
    }

    /**
     * Test for POST /api/tasks
     * Verifies that creating a new task returns the created task.
     */
    @Test
    void testCreateTask() throws Exception {
        // Arrange: create a sample task payload
        Task task = new Task();
        task.setName("New Task");
        task.setCreateDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));

        // Simulate the service returning the task with an id assigned
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setName(task.getName());
        savedTask.setCreateDate(task.getCreateDate());
        savedTask.setDueDate(task.getDueDate());
        when(taskService.create(any(Task.class))).thenReturn(savedTask);

        // Act & Assert: perform POST request and verify response.
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Task"));
    }

    // Additional tests (e.g., update, delete) can be added following similar patterns.
}
