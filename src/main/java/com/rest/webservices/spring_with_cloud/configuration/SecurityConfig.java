package com.rest.webservices.spring_with_cloud.configuration;

import com.rest.webservices.spring_with_cloud.JWT.JwtRequestFilter;
import com.rest.webservices.spring_with_cloud.audit.AuditorAwareImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public UserDetailsService userDetailsService() {
        logger.info("Entered UserDetailsService method from security config and " +
                "building user details with credentials and roles");
        try{
            UserDetails user = User.builder()
                    .username("admin")
                    .password("{noop}password") // NoOpPasswordEncoder
                    .roles("ADMIN")
                    .build();
            logger.info("Exiting UserDetailsService()");
            return new InMemoryUserDetailsManager(user);
        }
        catch(RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtRequestFilter jwtRequestFilter) throws Exception {
        logger.info("Entered SecurityFilterChain method");

        try{
            http.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/authenticate", "/register").permitAll()
                            .requestMatchers("/products/**").permitAll()
                            .anyRequest().authenticated())
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
            logger.info("Exiting securityFilterChain method");
            return http.build();
        }
        catch(RuntimeException ex){
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
