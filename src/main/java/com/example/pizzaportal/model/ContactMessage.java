package com.example.pizzaportal.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDateTime;
@Entity @Table(name="contact_messages")
public class ContactMessage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Size(max=100) private String name;
 @NotBlank @Email @Size(max=180) private String email;
 @NotBlank @Size(max=160) private String subject;
 @NotBlank @Size(max=3000) @Column(length=3000) private String message;
 @Column(nullable=false) private LocalDateTime sentAt;
 @PrePersist void onCreate(){if(sentAt==null)sentAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getSubject(){return subject;} public void setSubject(String v){subject=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;} public LocalDateTime getSentAt(){return sentAt;}
}
