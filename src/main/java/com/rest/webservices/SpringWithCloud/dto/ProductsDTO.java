package com.rest.webservices.SpringWithCloud.dto;

import com.rest.webservices.SpringWithCloud.model.Category;
import jakarta.validation.constraints.*;

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

    public ProductsDTO(String name, String description, int quantity, double price, int categoryId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
