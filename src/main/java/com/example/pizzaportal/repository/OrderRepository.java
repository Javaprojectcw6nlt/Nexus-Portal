package com.example.pizzaportal.repository;

import com.example.pizzaportal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}