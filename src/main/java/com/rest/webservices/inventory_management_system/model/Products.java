package com.rest.webservices.inventory_management_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rest.webservices.inventory_management_system.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Products")
@Data //includes all getters and setter and toString()
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference //It's used to prevent infinite recursion when serializing bidirectional relationships.
    private Category category;
}

