package com.rest.webservices.inventory_management_system.strategy;

import com.rest.webservices.inventory_management_system.model.Products;

import java.util.List;

public interface ProductExportStrategy{
    String export(List<Products> products);
}