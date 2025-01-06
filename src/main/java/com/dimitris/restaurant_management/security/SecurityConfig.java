package com.dimitris.restaurant_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/register/**" ,"/css/**","/lib/**", "/images/**", "/error/**","/restaurant/**","/api/v1/**").permitAll()
                        .requestMatchers("/api/v1/newOrder").hasAnyRole("ADMIN", "USER","OWNER")
                        .requestMatchers("/product").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/table").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/order").hasAnyRole("OWNER","ADMIN")
                        .requestMatchers("/tag").hasAnyRole("OWNER","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> {
                    form.loginPage("/login").permitAll();
                })
                .rememberMe(remember -> remember
                        .tokenRepository(persistentTokenRepository(dataSource))
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(14 * 24 * 60 * 60))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            CustomUserDetailService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
