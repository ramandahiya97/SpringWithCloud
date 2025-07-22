package com.rest.webservices.inventory_management_system.strategy;

import com.rest.webservices.inventory_management_system.model.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("csv")
public class CSVExportStrategy implements ProductExportStrategy{

    @Override
    public String export(List<Products> products) {
        log.info("Entered method export inside CSVExportStrategy");
        StringBuilder csv = new StringBuilder("ID,Name,Description,Quantity,Price,CreatedDate\n");
        for(Products p:products){
            csv.append(p.getId()).append(",")
                    .append(p.getName()).append(",")
                    .append(p.getDescription()).append(",")
                    .append(p.getQuantity()).append(",")
                    .append(p.getPrice()).append(",")
                    .append(p.getCreatedDate()).append("\n");
        }
        log.info("Exiting method export inside CSVExportStrategy");
        return csv.toString();
    }
}