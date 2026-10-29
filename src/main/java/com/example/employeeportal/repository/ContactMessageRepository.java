package com.example.employeeportal.repository;
import com.example.employeeportal.model.ContactMessage; import org.springframework.data.jpa.repository.JpaRepository;
 public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> { java.util.List<ContactMessage> findAllByOrderBySentAtDesc(); }
