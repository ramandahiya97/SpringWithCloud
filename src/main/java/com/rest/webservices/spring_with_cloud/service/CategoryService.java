package com.rest.webservices.spring_with_cloud.service;

import com.rest.webservices.spring_with_cloud.dto.CategoryDTO;
import com.rest.webservices.spring_with_cloud.exception.CategoryNotFountException;
import com.rest.webservices.spring_with_cloud.model.Category;
import com.rest.webservices.spring_with_cloud.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class CategoryService {
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Category> createCategory(CategoryDTO dto) {
        logger.info("Entered CreateCategory with CategoryDTO");
        ResponseEntity<Category> result = null;
        try {
            Category category = new Category();
            category.setName(dto.getName());
            result = createCategory(category);
            logger.info("Exiting CreateCategory method");
        }catch(RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return result;
    }

    public ResponseEntity<Category> createCategory(Category category) {
        logger.info("Entered CreateCategory with Category");
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
        logger.info("Entered get all categories method");
        return categoryRepository.findAll();
    }

    public Category findCategoryById(int id) {
        logger.info("Entered find category by id method with category_id: {}",id);
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }
    public Category updateCategory(int id, CategoryDTO categoryDto){
        logger.info("Entered UpdateCategoryDTO method with categoryId: {}",id);
        Category category = findCategoryById(id);
        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName(categoryDto.getName());
        return updateCategory(id, updatedCategory);
    }

    public Category updateCategory(int id, Category updatedCategory) {
        logger.info("Entered get Update Category method with categoryID: {}",id);
        if(!categoryRepository.existsById(id)){
            logger.error("Unable to find category with categoryID: {}",id);
            throw new CategoryNotFountException("Category doesn't exists to be updated");
        }
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(int id) {
        logger.info("Entered delete categories method with category_id : {}",id);
        if(!categoryRepository.existsById(id)){
            throw new CategoryNotFountException("Category doesn't exists to be updated");
        }
    }
}
