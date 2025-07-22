package com.rest.webservices.inventory_management_system.async;

import com.rest.webservices.inventory_management_system.model.Products;
import com.rest.webservices.inventory_management_system.strategy.CSVExportStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncExportService {
    @Async
    public CompletableFuture<String> exportCsvAsync(List<Products> products
            , CSVExportStrategy csvExportStrategy) throws InterruptedException {
        log.info("Entered method exportCsvAsync");
        try {
            Thread.sleep(20000);
        }catch (InterruptedException ie){
            throw new InterruptedException(ie.getMessage());
        }
        String csv = csvExportStrategy.export(products);
        log.info("Exiting method exportCsvAsync with response: {}",csv);
        return CompletableFuture.completedFuture(csv);
    }

}
