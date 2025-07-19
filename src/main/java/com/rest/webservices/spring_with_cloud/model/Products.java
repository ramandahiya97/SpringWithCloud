package com.rest.webservices.spring_with_cloud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rest.webservices.spring_with_cloud.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Products")
@Data //includes all getters and setter and toString()
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

