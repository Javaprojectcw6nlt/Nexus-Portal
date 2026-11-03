package com.example.employeeportal.repository;

import com.example.employeeportal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}