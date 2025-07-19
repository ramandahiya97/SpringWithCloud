package com.rest.webservices.spring_with_cloud.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {
/*
* to provide current username we created bean of AuditorAware
* */
    private final Logger logger = LoggerFactory.getLogger(AuditorAwareImpl.class);
    @Override
    public Optional<String> getCurrentAuditor() throws RuntimeException {
        logger.info("Entered getCurrentAuditor() of AuditorAwareImpl class to get current " +
                "System or host name");
        // If using Spring Security, it gets user from SecurityContext
        String systemName = null;
        try {
            systemName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error Returning System name:  "+e.getMessage());
        }

        return Optional.of(systemName);
        // or extract from SecurityContextHolder.getContext().getAuthentication()
    }
}
