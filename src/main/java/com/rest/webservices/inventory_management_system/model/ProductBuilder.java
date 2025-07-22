package com.rest.webservices.inventory_management_system.model;

import com.rest.webservices.inventory_management_system.dto.ProductsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductBuilder {
    public Products productsFromDto(ProductsDTO productsDTO,Category category) {
        log.info("Entered ProductsFrom DTO method");
        return Products.builder().name(productsDTO.getName())
                .description(productsDTO.getDescription())
                .quantity(productsDTO.getQuantity())
                .price(productsDTO.getPrice())
                .isDeleted(false)
                .category(category)
                .build();
    }
}