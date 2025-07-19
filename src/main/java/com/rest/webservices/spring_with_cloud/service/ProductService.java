package com.rest.webservices.spring_with_cloud.service;

import com.rest.webservices.spring_with_cloud.dto.ProductsDTO;
import com.rest.webservices.spring_with_cloud.exception.ProductNotFoundException;
import com.rest.webservices.spring_with_cloud.model.Category;
import com.rest.webservices.spring_with_cloud.model.Products;
import com.rest.webservices.spring_with_cloud.repository.CategoryRepository;
import com.rest.webservices.spring_with_cloud.repository.ProductJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductJPARepository productRepo;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductJPARepository productRepo, CategoryRepository categoryRepository) {
        this.productRepo = productRepo;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Products> create(ProductsDTO dto) {
        logger.info("Entered CreateProducts with ProductDTO");
        Products product = new Products();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        product.setCategory(category);
        return create(product);
    }

    public ResponseEntity<Products> create(Products products) {
        logger.info("Entered CreateProducts with Products");
        Products saved = productRepo.save(products);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    public List<Products> findAll() {
        logger.info("Entered find all Products method");
        return productRepo.findAll();
    }

    public Products findOne(int id) {
        logger.info("Entered find Product by id method with product_id: {}",id);
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public Products update(int id, ProductsDTO dto) {
        logger.info("Entered UpdateProductDTO method with productId: {}", id);
        Products existing = findOne(id);
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setQuantity(dto.getQuantity());
        existing.setPrice(dto.getPrice());

        return productRepo.save(existing);
    }

    public Products update(int id, Products updatedProduct) {
        logger.info("Entered UpdateProduct method with productId: {}", id);
        updatedProduct.setId(id);
        return productRepo.save(updatedProduct);
    }


    public void delete(int id) {
        logger.info("Entered Delete Product method with productId: {}", id);

        Products product = productRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with product ID: {}", id);
                    return new ProductNotFoundException("Product with ID " + id + " not found");
                });

        product.setIsDeleted(true); // Soft delete
        productRepo.save(product); // Save the updated status

        logger.info("Product with ID: {} marked as deleted (soft delete)", id);
    }


    public List<Products> searchByName(String name) {
        logger.info("Entered Products in searchByName method with name : {}",name);
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    public String exportAsCSV() {
        logger.info("Entered Export products as CSV method");
        List<Products> allProducts = productRepo.findAll();
        StringBuilder csvBuilder = new StringBuilder("ID,Name,Description,Quantity,Price,CreatedDate\n");

        for (Products p : allProducts) {
            csvBuilder.append(p.getId()).append(",")
                    .append(p.getName()).append(",")
                    .append(p.getDescription()).append(",")
                    .append(p.getQuantity()).append(",")
                    .append(p.getPrice()).append(",")
                    .append(p.getCreatedDate()).append("\n");
        }

        return csvBuilder.toString();
    }


    public Page<Products> getPaginatedProducts(Pageable pageable) {
        logger.info("Entered get paginated Products method");
        return productRepo.findAll(pageable);
    }

    public List<Products> createBulk(List<ProductsDTO> dtos) {
        logger.info("Entered create bulk Products method");
        List<Products> products = dtos.stream().map(dto -> {
            Products p = new Products();
            p.setName(dto.getName());
            p.setDescription(dto.getDescription());
            p.setQuantity(dto.getQuantity());
            p.setPrice(dto.getPrice());
            return p;
        }).collect(Collectors.toList());
        return productRepo.saveAll(products);
    }
    public List<Products> findFilteredProducts(double minPrice, String category){
        logger.info("Entered find filtered Products method");
        return productRepo.findByPriceGreaterThanAndSorted(minPrice,category);
    }
    public List<Products> findProductsNative(double minPrice,String category){
        logger.info("Entered find Products Native method");
        return productRepo.findProductsNative(minPrice, category);
    }
}
