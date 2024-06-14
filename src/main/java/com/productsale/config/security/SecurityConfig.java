package com.productsale.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(new AntPathRequestMatcher("/api/products")).permitAll() // Public endpoint
                .requestMatchers(new AntPathRequestMatcher("/api/products/**")).hasRole("ADMIN") // Protected endpoint
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults()).build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails user = User.builder()
            .username("admin")
            .password("{noop}admin")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
