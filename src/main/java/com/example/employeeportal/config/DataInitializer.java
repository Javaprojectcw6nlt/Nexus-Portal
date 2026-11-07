package com.example.employeeportal.config;

import com.example.employeeportal.model.*;
import com.example.employeeportal.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(CategoryRepository categories, PizzaRepository pizzas, OrderRepository orders, AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            
            // 1. Force Admin Password Reset / Creation
            String defaultPassword = System.getenv().getOrDefault("APP_ADMIN_PASSWORD", "ChangeMe-Now-2026!");
            
            if (!users.existsByUsername("admin")) {
                users.save(new AppUser("admin", encoder.encode(defaultPassword), "ROLE_ADMIN"));
            } else {
                AppUser admin = users.findByUsername("admin").orElseThrow();
                admin.setPassword(encoder.encode(defaultPassword));
                users.save(admin);
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
            }

            // 3. Seed some orders if the orders table is empty (third table for the Database page)
            if (orders.count() == 0) {
                LocalDateTime base = LocalDateTime.now().minusDays(3).withHour(11).withMinute(0).withSecond(0).withNano(0);
                String[] names = {"Hawaii","Csupa sajt","Betyáros","Gombás","Áfonyás","Vega","Babos","Barbecue chicken","Caribi","Kívánság","Hawaii","Betyáros"};
                int[] amounts = {2,1,3,1,2,1,4,2,1,2,3,1};
                for (int i = 0; i < names.length; i++) {
                    LocalDateTime taken = base.plusMinutes(i * 95L);
                    orders.save(new Order(names[i], amounts[i], taken, taken.plusMinutes(25 + (i % 4) * 5)));
                }
            }
        };
    }
}