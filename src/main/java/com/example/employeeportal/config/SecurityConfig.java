package com.example.employeeportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow public access to static files, login, register, and the API
                .requestMatchers("/", "/database", "/contact", "/chart", "/register", "/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/**").permitAll() // <--- THIS IS THE CRUCIAL LINE
                .requestMatchers("/h2-console/**").permitAll()
                // Restrict messages to logged-in users
                .requestMatchers("/messages").hasAnyRole("USER", "ADMIN")
                // Restrict CRUD to admin only
                .requestMatchers("/crud/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/?logout")
                .permitAll()
            )
            // Disabling CSRF for API calls and H2 console (so the chart fetch works)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/h2-console/**")
            )
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // For H2 console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}