package com.rest.webservices.SpringWithCloud.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    @NotBlank
    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
