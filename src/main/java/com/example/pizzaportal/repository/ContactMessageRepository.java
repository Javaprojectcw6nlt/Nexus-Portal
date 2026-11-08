package com.example.pizzaportal.repository;
import com.example.pizzaportal.model.ContactMessage; import org.springframework.data.jpa.repository.JpaRepository;
 public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> { java.util.List<ContactMessage> findAllByOrderBySentAtDesc(); }
