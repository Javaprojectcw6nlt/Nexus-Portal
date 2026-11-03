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
            if(!users.existsByUsername("admin")){
                String password = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "ChangeMe-Now-2026!");
                users.save(new AppUser("admin", encoder.encode(password), "ROLE_ADMIN"));
            }
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
            }
        };
    }
}