package com.example.pizzaportal.repository;

import com.example.pizzaportal.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, String> {
}