package com.example.employeeportal.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity @Table(name="employees")
public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false) private String fullName;
 @NotBlank @Email @Column(nullable=false) private String email;
 @NotBlank @Column(nullable=false) private String jobTitle;
 @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="department_id") private Department department;
 @DecimalMin("0.0") private Double salary;
 public Employee(){}
 public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;} public Department getDepartment(){return department;} public void setDepartment(Department v){department=v;} public Double getSalary(){return salary;} public void setSalary(Double v){salary=v;}
}
