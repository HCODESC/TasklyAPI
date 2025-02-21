package com.hcodes.Taskly.category;

import com.hcodes.Taskly.exceptions.ResourceAlreadyExistsException;
import com.hcodes.Taskly.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    public final CategoryRepository categoryRepository;
    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    //Find all categories
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    //find category by id

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
    }
    //create category
    public Category createCategory(@Valid Category category) {
        Category existCategory = categoryRepository.findByName(category.getName());

        if (existCategory != null) {
            throw new ResourceAlreadyExistsException("Cannot create duplicate category");
        }

        return categoryRepository.save(category);
    }
    //update category
    public Category updateCategory(@Valid Category category) {
        Category existingCategory = categoryRepository
                .findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);

    }
    //delete category
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    //delete all categories

    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

}
