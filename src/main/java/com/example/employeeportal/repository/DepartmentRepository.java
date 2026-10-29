package com.example.employeeportal.repository;
import com.example.employeeportal.model.Department; import org.springframework.data.jpa.repository.JpaRepository;
 public interface DepartmentRepository extends JpaRepository<Department,Long> {}
