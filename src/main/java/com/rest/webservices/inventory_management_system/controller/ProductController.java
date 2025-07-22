package com.rest.webservices.inventory_management_system.controller;

import com.rest.webservices.inventory_management_system.dto.ProductsDTO;
import com.rest.webservices.inventory_management_system.model.Products;
import com.rest.webservices.inventory_management_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //    curl -X POST http://localhost:8080/products \
//            -H "Content-Type: application/json" \
//            -d '{
//            "name": "Bluetooth Speaker",
//            "description": "Portable and rechargeable",
//            "quantity": 15,
//            "price": 1499.99,
    //         "category_id" : 1
//}'
    @PostMapping
    public ResponseEntity<Products> createProduct(@Valid @RequestBody ProductsDTO productsDTO) {
        log.info("Entered CreateProduct method");
        return productService.create(productsDTO);
    }

    // Get all products
    @GetMapping
    public List<Products> getAllProducts() {
        log.info("Entered getAllProduct method");
        return productService.findAll();
    }

    // GET /products/3
    @GetMapping("/{id:[0-9]+}")
    public Products getProductById(@PathVariable int id) {
        log.info("Entered getProductById method");
        return productService.findOne(id);
    }

    // Search product by name
    //GET /products/search?name=keyboard
    @GetMapping("/search")
    public List<Products> searchProducts(@RequestParam String name) {
        log.info("Entered searchProduct method");
        return productService.searchByName(name);
    }


    // Update product by ID
//    PUT /products/3
    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable int id, @Valid @RequestBody ProductsDTO productsDTO) {
        log.info("Entered updateProduct method");
        return productService.update(id, productsDTO);
    }

    // Delete product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        log.info("Entered deleteProduct method");
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /products/paginated?page=0&size=5&sortBy=price&sortDir=desc
    @GetMapping("/paginated")
    public ResponseEntity<Page<Products>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("Entered getPaginatedProduct method");
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Products> result = productService.getPaginatedProducts(pageable);
        return ResponseEntity.ok(result);
    }

    /*
    * curl -X POST http://localhost:8080/products/bulk \
  -H "Content-Type: application/json" \
  -d '[{"name":"Wireless Mouse","description":"Ergonomic USB mouse","quantity":50,"price":599.99},
  * {"name":"Mechanical Keyboard","description":"RGB Backlit keyboard","quantity":30,"price":2499.50}]'
    * */
    @PostMapping("/bulk")
    public ResponseEntity<List<Products>> createProducts(@RequestBody List<ProductsDTO> dtos) {
        log.info("Entered CreateProducts in bulk method");
        List<Products> savedProducts = productService.createBulk(dtos);
        return ResponseEntity.ok(savedProducts);
    }

    // Export products as CSV
    //GET /products/export
    @GetMapping("/export-async")
    public CompletableFuture<ResponseEntity<String>> exportCSV() throws InterruptedException {
        log.info("Entered exportCSV method with API thread : {}",Thread.currentThread().getName());
        return productService.exportAsCSV().thenApply(data -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=products.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(data));
    }

    //http://localhost:8080/filter?minPrice=100.0&category=Electronics&type=native
    @GetMapping("/filter")
    public ResponseEntity<List<Products>> getFilteredProduct(
            @RequestParam double minPrice,
            @RequestParam String category,
            @RequestParam(defaultValue = "jpql") String type
    ){
        log.info("Entered getFilteredProduct method");
        List<Products> result;
        if("native".equalsIgnoreCase(type)){
            result = productService.findProductsNative(minPrice, category);
        }
        else{
            result = productService.findFilteredProducts(minPrice, category);
        }
        return ResponseEntity.ok(result);
    }
}
