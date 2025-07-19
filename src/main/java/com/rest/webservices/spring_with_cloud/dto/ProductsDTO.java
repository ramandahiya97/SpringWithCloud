package com.rest.webservices.spring_with_cloud.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 1, message = "Quantity should be at least 1")
    private int quantity;

    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private double price;

    @NotNull
    private int categoryId;
}
