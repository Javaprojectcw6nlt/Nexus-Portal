package com.example.pizzaportal.repository;

import com.example.pizzaportal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}