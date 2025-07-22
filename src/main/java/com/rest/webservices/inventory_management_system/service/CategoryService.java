package com.rest.webservices.inventory_management_system.service;

import com.rest.webservices.inventory_management_system.dto.CategoryDTO;
import com.rest.webservices.inventory_management_system.exception.CategoryNotFountException;
import com.rest.webservices.inventory_management_system.model.Category;
import com.rest.webservices.inventory_management_system.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Category> createCategory(CategoryDTO dto) {
        log.info("Entered CreateCategory with CategoryDTO");
        ResponseEntity<Category> result = null;
        try {
            Category category = new Category();
            category.setName(dto.getName());
            result = createCategory(category);
            log.info("Exiting CreateCategory method");
        }catch(RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return result;
    }

    public ResponseEntity<Category> createCategory(Category category) {
        log.info("Entered CreateCategory with Category");
        Category saved = null;
        URI location = null;
        try{
            saved = categoryRepository.save(category);
            location= ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.created(location).body(saved);
    }


    public List<Category> getAllCategories() {
        log.info("Entered get all categories method");
        return categoryRepository.findAll();
    }

    public Category findCategoryById(int id) {
        log.info("Entered find category by id method with category_id: {}",id);
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }
    public Category updateCategory(int id, CategoryDTO categoryDto){
        log.info("Entered UpdateCategoryDTO method with categoryId: {}",id);
        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName(categoryDto.getName());
        return updateCategory(id, updatedCategory);
    }

    public Category updateCategory(int id, Category updatedCategory) {
        log.info("Entered get Update Category method with categoryID: {}",id);
        if(!categoryRepository.existsById(id)){
            log.error("Unable to find category with categoryID: {}",id);
            throw new CategoryNotFountException("Category doesn't exists to be updated");
        }
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(int id) {
        log.info("Entered delete categories method with category_id : {}",id);
        if(!categoryRepository.existsById(id)){
            throw new CategoryNotFountException("Category doesn't exists to be updated");
        }
    }
}
