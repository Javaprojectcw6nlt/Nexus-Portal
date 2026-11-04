package com.example.employeeportal.config;

import com.example.employeeportal.model.*;
import com.example.employeeportal.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(CategoryRepository categories, PizzaRepository pizzas, OrderRepository orders, AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            
            // 1. Force Admin Password Reset / Creation
            String defaultPassword = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "ChangeMe-Now-2026!");
            
            if (!users.existsByUsername("admin")) {
                users.save(new AppUser("admin", encoder.encode(defaultPassword), "ROLE_ADMIN"));
                System.out.println(">>> ADMIN CREATED WITH PASSWORD: " + defaultPassword);
            } else {
                AppUser admin = users.findByUsername("admin").orElseThrow();
                admin.setPassword(encoder.encode(defaultPassword));
                users.save(admin);
                System.out.println(">>> ADMIN PASSWORD RESET TO: " + defaultPassword);
            }

            // 2. Seed Pizza Database if empty
            if(categories.count() == 0){
                Category page = categories.save(new Category("page", 850.0));
                Category nobleman = categories.save(new Category("nobleman", 950.0));
                Category king = categories.save(new Category("king", 1250.0));
                Category knight = categories.save(new Category("knight", 1150.0));

                pizzas.save(new Pizza("Áfonyás", king, false));
                pizzas.save(new Pizza("Babos", knight, false));
                pizzas.save(new Pizza("Barbecue chicken", knight, false));
                pizzas.save(new Pizza("Betyáros", king, false));
                pizzas.save(new Pizza("Caribi", page, false));
                pizzas.save(new Pizza("Csupa sajt", knight, true));
                pizzas.save(new Pizza("Gombás", page, true));
                pizzas.save(new Pizza("Hawaii", nobleman, false));
                pizzas.save(new Pizza("Kívánság", knight, true));
                pizzas.save(new Pizza("Vega", knight, true));
                
                System.out.println(">>> PIZZA DATABASE SEEDED SUCCESSFULLY.");
            }
        };
    }
}