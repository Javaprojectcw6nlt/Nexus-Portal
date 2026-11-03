package com.example.employeeportal.controller;

import com.example.employeeportal.model.Pizza;
import com.example.employeeportal.repository.PizzaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chart")
public class ChartApiController {

    private final PizzaRepository pizzas;

    public ChartApiController(PizzaRepository p) {
        pizzas = p;
    }

    @GetMapping("/pizzas-by-category")
    public Map<String, Object> pizzasByCategory() {
        List<Pizza> allPizzas = pizzas.findAll();
        
        // We map the category name to a count. 
        // This avoids the infinite JSON loop.
        Map<String, Long> counts = allPizzas.stream()
            .filter(p -> p.getCategory() != null)
            .collect(Collectors.groupingBy(p -> p.getCategory().getCname(), Collectors.counting()));

        return Map.of(
            "labels", counts.keySet(),
            "values", counts.values()
        );
    }
}