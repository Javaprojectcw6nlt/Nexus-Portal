package com.example.employeeportal.model;
import jakarta.persistence.*; import jakarta.validation.constraints.NotBlank;
@Entity @Table(name="projects")
public class Project {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false) private String name;
 @Column(length=800) private String description;
 @Column(nullable=false) private String status="Planned";
 @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="department_id") private Department department;
 public Project(){} public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public Department getDepartment(){return department;} public void setDepartment(Department v){department=v;}
}
