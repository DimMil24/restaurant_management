package com.dimitris.restaurant_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/register/**" ,"/lib/**", "/error/**","/images/**","/restaurant/**","/api/v1/**").permitAll()
                        .requestMatchers("/product").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/table").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/order").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/restaurant/newOrder").hasAnyRole("OWNER","ADMIN","USER")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
