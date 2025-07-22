package com.rest.webservices.inventory_management_system.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){
        log.info("Entered method taskExecutor inside config");
        ThreadPoolTaskExecutor service = new ThreadPoolTaskExecutor();
        service.setCorePoolSize(4); //no of core threads
        service.setMaxPoolSize(8); //max threads
        service.setQueueCapacity(500);
        service.setThreadNamePrefix("ExportExport-");
        service.initialize();
        log.info("Exiting method taskExecutor with executor core pool size : {}, \n max pool size : {}"
                ,service.getCorePoolSize()
                ,service.getMaxPoolSize());
        return service;
    }
}
