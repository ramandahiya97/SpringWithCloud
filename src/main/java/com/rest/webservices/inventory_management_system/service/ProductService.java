package com.rest.webservices.inventory_management_system.service;

import com.rest.webservices.inventory_management_system.async.AsyncExportService;
import com.rest.webservices.inventory_management_system.dto.ProductsDTO;
import com.rest.webservices.inventory_management_system.exception.ProductNotFoundException;
import com.rest.webservices.inventory_management_system.model.Category;
import com.rest.webservices.inventory_management_system.model.ProductBuilder;
import com.rest.webservices.inventory_management_system.model.Products;
import com.rest.webservices.inventory_management_system.repository.CategoryRepository;
import com.rest.webservices.inventory_management_system.repository.ProductJPARepository;
import com.rest.webservices.inventory_management_system.strategy.CSVExportStrategy;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductJPARepository productRepo;
    private final CategoryRepository categoryRepository;
    private final ProductBuilder productBuilder;
    private final CSVExportStrategy csvExportStrategy;
    public final AsyncExportService asyncExportService;

    @Autowired
    public ProductService(ProductJPARepository productRepo
            , CategoryRepository categoryRepository
            , ProductBuilder productBuilder
            , @Qualifier("csv") CSVExportStrategy csvExportStrategy
            , AsyncExportService asyncExportService) {
        this.productRepo = productRepo;
        this.categoryRepository = categoryRepository;
        this.productBuilder = productBuilder;
        this.csvExportStrategy = csvExportStrategy;
        this.asyncExportService = asyncExportService;
    }

    public ResponseEntity<Products> create(ProductsDTO dto) {
        log.info("Entered CreateProducts with ProductDTO");
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        Products products = productBuilder.productsFromDto(dto,category);
        return create(products);
    }

    public ResponseEntity<Products> create(Products products) {
        log.info("Entered CreateProducts with Products");
        Products saved = productRepo.save(products);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    public List<Products> findAll() {
        log.info("Entered find all Products method");
        return productRepo.findAll();
    }

    public Products findOne(int id) {
        log.info("Entered find Product by id method with product_id: {}",id);
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public Products update(int id, ProductsDTO dto) {
        log.info("Entered UpdateProductDTO method with productId: {}", id);
        Products existing = findOne(id);
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setQuantity(dto.getQuantity());
        existing.setPrice(dto.getPrice());

        return productRepo.save(existing);
    }

    public Products update(int id, Products updatedProduct) {
        log.info("Entered UpdateProduct method with productId: {}", id);
        updatedProduct.setId(id);
        return productRepo.save(updatedProduct);
    }


    public void delete(int id) {
        log.info("Entered Delete Product method with productId: {}", id);

        Products product = productRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with product ID: {}", id);
                    return new ProductNotFoundException("Product with ID " + id + " not found");
                });

        product.setIsDeleted(true); // Soft delete
        productRepo.save(product); // Save the updated status

        log.info("Product with ID: {} marked as deleted (soft delete)", id);
    }


    public List<Products> searchByName(String name) {
        log.info("Entered Products in searchByName method with name : {}",name);
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    public CompletableFuture<String> exportAsCSV() throws InterruptedException {
        log.info("Entered Export products as CSV method");
        List<Products> allProducts = productRepo.findAll();
        return asyncExportService.exportCsvAsync(allProducts,csvExportStrategy);
    }


    public Page<Products> getPaginatedProducts(Pageable pageable) {
        log.info("Entered get paginated Products method");
        return productRepo.findAll(pageable);
    }

    public List<Products> createBulk(List<ProductsDTO> dtos) {
        log.info("Entered create bulk Products method");
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
        log.info("Entered find filtered Products method");
        return productRepo.findByPriceGreaterThanAndSorted(minPrice,category);
    }
    public List<Products> findProductsNative(double minPrice,String category){
        log.info("Entered find Products Native method");
        return productRepo.findProductsNative(minPrice, category);
    }
}
