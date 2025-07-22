package com.rest.webservices.inventory_management_system.controller;

import com.rest.webservices.inventory_management_system.dto.CategoryDTO;
import com.rest.webservices.inventory_management_system.model.Category;
import com.rest.webservices.inventory_management_system.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //add category
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO dto){
        log.info("Entered Create category method");
       return categoryService.createCategory(dto);
    }
    //get all categories
    @GetMapping
    public List<Category> getAllCategories(){

        log.info("Entered getAllCategory method");
        return categoryService.getAllCategories();
    }
    //get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoriesById(@PathVariable int id){
        log.info("Entered getCategoryById category method");
        Category result = categoryService.findCategoryById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    //update category
    @PutMapping("/{id}")
    public Category updateProduct(@PathVariable int id,@Valid @RequestBody CategoryDTO categoryDto){
        log.info("Entered UpdateProduct category method");
        return categoryService.updateCategory(id,categoryDto);
    }
    //delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id){
        log.info("Entered DeleteCategory method");
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
