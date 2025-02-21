package com.hcodes.Taskly.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CategoryService categoryService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * Test for GET /api/categories
     * This test verifies that the findAll endpoint returns all categories.
     */
    @Test
    void findAll() throws Exception {
        // Arrange: create sample categories
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Category One");
        
        Category cat2 = new Category();
        cat2.setId(2L);
        cat2.setName("Category Two");
        
        List<Category> categories = Arrays.asList(cat1, cat2);
        when(categoryService.findAll()).thenReturn(categories);
        
        // Act & Assert: perform GET request and verify response
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(categories.size()))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category One"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category Two"));
    }
    
    /**
     * Test for GET /api/categories/{id}
     * This test verifies that the findOne endpoint returns the expected category.
     */
    @Test
    void findOne() throws Exception {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("Category One");
        
        when(categoryService.findById(id)).thenReturn(category);
        
        mockMvc.perform(get("/api/categories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Category One"));
    }
    
    /**
     * Test for POST /api/categories
     * This test verifies that a new category is created.
     */
    @Test
    void create() throws Exception {
        // Arrange: create a category payload
        Category category = new Category();
        category.setName("New Category");
        
        // Simulate the service returning the created category with an assigned ID
        Category createdCategory = new Category();
        createdCategory.setId(1L);
        createdCategory.setName("New Category");
        when(categoryService.createCategory(any(Category.class))).thenReturn(createdCategory);
        
        // Act & Assert: perform POST and verify response
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk()) // Adjust if you later change to HttpStatus.CREATED
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdCategory.getId()))
                .andExpect(jsonPath("$.name").value(createdCategory.getName()));
    }
    
    /**
     * Test for PUT /api/categories/{id}
     * This test verifies that an existing category is updated.
     */
    @Test
    void update() throws Exception {
        Long id = 1L;
        // Arrange: create an updated category payload
        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName("Updated Category");
        
        // Simulate the service updating and returning the updated category
        when(categoryService.updateCategory(any(Category.class))).thenReturn(updatedCategory);
        
        // Act & Assert: perform PUT request and verify that the response status is NO_CONTENT.
        mockMvc.perform(put("/api/categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isNoContent());
    }
    

}
