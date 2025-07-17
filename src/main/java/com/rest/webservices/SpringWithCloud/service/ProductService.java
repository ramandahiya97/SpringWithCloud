package com.rest.webservices.SpringWithCloud.service;

import com.rest.webservices.SpringWithCloud.dto.ProductsDTO;
import com.rest.webservices.SpringWithCloud.exception.ProductNotFoundException;
import com.rest.webservices.SpringWithCloud.model.Category;
import com.rest.webservices.SpringWithCloud.model.Products;
import com.rest.webservices.SpringWithCloud.repository.CategoryRepository;
import com.rest.webservices.SpringWithCloud.repository.ProductJPARepository;
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
    @Autowired
    private ProductJPARepository productRepo;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductService(ProductJPARepository productRepo) {
        this.productRepo = productRepo;
    }

    public ResponseEntity<Products> create(ProductsDTO dto) {
        logger.info("Entered CreateCategory with ProductDTO");
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
        logger.info("Entered CreateCategory with Products");
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
        logger.info("Entered UpdateProductDTO method with productId: {}",id);
        Products existing = findOne(id);
        Products updatedProduct = new Products();
        updatedProduct.setId(id);
        updatedProduct.setName(dto.getName());
        updatedProduct.setDescription(dto.getDescription());
        updatedProduct.setQuantity(dto.getQuantity());
        updatedProduct.setPrice(dto.getPrice());
        updatedProduct.setAddedDate(existing.getAddedDate());
        return update(id, updatedProduct);
    }


    public Products update(int id, Products updatedProduct) {
        logger.info("Entered UpdateProduct method with productId: {}",id);
        Products existing = findOne(id);
        updatedProduct.setId(id);
        updatedProduct.setAddedDate(existing.getAddedDate()); // preserve original date
        return productRepo.save(updatedProduct);
    }

    public void delete(int id) {
        logger.info("Entered Delete Product method with productId: {}",id);
        if (!productRepo.existsById(id)) {
            logger.error("Product not found with product ID: {}",id);
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        productRepo.deleteById(id);
    }

    public List<Products> searchByName(String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    public String exportAsCSV() {
        List<Products> allProducts = productRepo.findAll();
        StringBuilder csvBuilder = new StringBuilder("ID,Name,Description,Quantity,Price,AddedDate\n");
            // Append each product row
        for (Products p : allProducts) {
        csvBuilder.append(p.getId()).append(",")
                .append(p.getName()).append(",")
                .append(p.getDescription()).append(",")
                .append(p.getQuantity()).append(",")
                .append(p.getPrice()).append(",")
                .append(p.getAddedDate()).append("\n");
            }

            return csvBuilder.toString();
        }

    public Page<Products> getPaginatedProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public List<Products> createBulk(List<ProductsDTO> dtos) {
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
        return productRepo.findByPriceGreaterThanAndSorted(minPrice,category);
    }
    public List<Products> findProductsNative(double minPrice,String category){
        return productRepo.findProductsNative(minPrice, category);
    }
}
