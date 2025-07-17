package com.rest.webservices.SpringWithCloud.controller;

import com.rest.webservices.SpringWithCloud.dto.ProductsDTO;
import com.rest.webservices.SpringWithCloud.model.Products;
import com.rest.webservices.SpringWithCloud.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

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
        return productService.create(productsDTO);
    }

    // Get all products
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.findAll();
    }

    // GET /products/3
    @GetMapping("/{id}")
    public Products getProductById(@PathVariable int id) {
        return productService.findOne(id);
    }

    // Search product by name
    //GET /products/search?name=keyboard
    @GetMapping("/search")
    public List<Products> searchProducts(@RequestParam String name) {
        return productService.searchByName(name);
    }


    // Update product by ID
//    PUT /products/3
    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable int id, @Valid @RequestBody ProductsDTO productsDTO) {
        return productService.update(id, productsDTO);
    }

    // Delete product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
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
        List<Products> savedProducts = productService.createBulk(dtos);
        return ResponseEntity.ok(savedProducts);
    }

    // Export products as CSV
    //GET /products/export
    @GetMapping("/export")
    public ResponseEntity<String> exportCSV() {
        String csv = productService.exportAsCSV();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Products>> getFilteredProduct(
            @RequestParam double minPrice,
            @RequestParam String category,
            @RequestParam(defaultValue = "jpql") String type
    ){
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
