package com.hcodes.Taskly.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories
    // This endpoint retrieves all categories from the database.
    // TODO: Implement logic to fetch and return a list of all categories.

    @GetMapping("")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    // GET /api/categories/{id}
    // This endpoint retrieves a specific category by its unique identifier.
    // TODO: Implement logic to fetch a category by id and handle
    //  the case where the category isn't found (e.g., throw a ResourceNotFoundException).

    @GetMapping("/{id}")
    public Category findOne(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    // POST /api/categories
    // This endpoint creates a new category using the provided request body data.
    // TODO: Implement logic to validate and create a new category,
    //  ensuring that duplicate category names are not allowed,
    //  and return the created category (with HTTP 201 Created status).
    @PostMapping("")
    public Category create(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    // PUT /api/categories/{id}
    // This endpoint updates an existing category by its id.
    // TODO: Implement logic to verify the category exists,
    //  update the relevant fields based on the request body,
    //  and return the updated category.
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    // DELETE /api/categories/{id}
    // This endpoint deletes a category by its id.
    // TODO: Implement logic to check if the category exists,
    //  delete it from the database,
    //  and return an appropriate HTTP status (like 204 No Content).
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @DeleteMapping("")
    public void deleteAll() {
        categoryService.deleteAllCategories();
    }


}
