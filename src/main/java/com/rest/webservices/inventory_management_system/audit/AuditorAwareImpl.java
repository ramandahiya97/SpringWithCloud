package com.rest.webservices.inventory_management_system.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {
/*
* to provide current username we created bean of AuditorAware
* */
    @Override
    public Optional<String> getCurrentAuditor() throws RuntimeException {
        log.info("Entered getCurrentAuditor() of AuditorAwareImpl class to get current " +
                "System or host name");
        // If using Spring Security, it gets user from SecurityContext
        String systemName = null;
        try {
            systemName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error Returning System name:  "+e.getMessage());
        }
        log.info("Exiting method getCurrentAuditor with host name : {}",systemName);
        return Optional.of(systemName);
        // or extract from SecurityContextHolder.getContext().getAuthentication()
    }
}
