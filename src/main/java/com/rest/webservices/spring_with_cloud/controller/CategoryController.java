package com.rest.webservices.spring_with_cloud.controller;

import com.rest.webservices.spring_with_cloud.dto.CategoryDTO;
import com.rest.webservices.spring_with_cloud.model.Category;
import com.rest.webservices.spring_with_cloud.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //add category
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO dto){
        logger.info("Entered Create category method");
       return categoryService.createCategory(dto);
    }
    //get all categories
    @GetMapping
    public List<Category> getAllCategories(){

        logger.info("Entered getAllCategory method");
        return categoryService.getAllCategories();
    }
    //get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoriesById(@PathVariable int id){
        logger.info("Entered getCategoryById category method");
        Category result = categoryService.findCategoryById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    //update category
    @PutMapping("/{id}")
    public Category updateProduct(@PathVariable int id,@Valid @RequestBody CategoryDTO categoryDto){
        logger.info("Entered UpdateProduct category method");
        return categoryService.updateCategory(id,categoryDto);
    }
    //delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id){
        logger.info("Entered DeleteCategory method");
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
