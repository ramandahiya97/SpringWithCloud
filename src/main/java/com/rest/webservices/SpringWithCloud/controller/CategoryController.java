package com.rest.webservices.SpringWithCloud.controller;

import com.rest.webservices.SpringWithCloud.dto.CategoryDTO;
import com.rest.webservices.SpringWithCloud.model.Category;
import com.rest.webservices.SpringWithCloud.service.CategoryService;
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
    @Autowired
    private CategoryService categoryService;
    //add category
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO dto){
        logger.info("Entered Create category method");
       return categoryService.createCategory(dto);
    }
    //get all categories
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    //get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoriesById(@PathVariable int id){
        Category result = categoryService.findCategoryById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    //update category
    @PutMapping("/{id}")
    public Category updateProduct(@PathVariable int id,@Valid @RequestBody CategoryDTO categoryDto){
        return categoryService.updateCategory(id,categoryDto);
    }
    //delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
