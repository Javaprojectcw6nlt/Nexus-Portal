package com.example.employeeportal.repository;

import com.example.employeeportal.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, String> {
}