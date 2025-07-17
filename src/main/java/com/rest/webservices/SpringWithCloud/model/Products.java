package com.rest.webservices.SpringWithCloud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private LocalDateTime addedDate;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference //It's used to prevent infinite recursion when serializing bidirectional relationships.
    private Category category;

    public Products(Integer id, String name, String description
            , int quantity, double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public Products() {

    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", desc=" + description + ", quantity=" + quantity +
                ", price=" + price + ", addedDate=" + addedDate + "]";
    }
    @PrePersist
    public void prePersist() {
        this.addedDate = LocalDateTime.now();
    }
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDateTime getAddedDate() { return addedDate; }
    public void setAddedDate(LocalDateTime addedDate) { this.addedDate = addedDate; }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

