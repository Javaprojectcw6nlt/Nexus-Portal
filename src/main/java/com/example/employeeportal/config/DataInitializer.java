package com.example.employeeportal.config;
import com.example.employeeportal.model.*; import com.example.employeeportal.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(DepartmentRepository departments, EmployeeRepository employees, ProjectRepository projects, AppUserRepository users, PasswordEncoder encoder){return args -> {
  if(!users.existsByUsername("admin")){String password=System.getenv().getOrDefault("APP_ADMIN_PASSWORD","ChangeMe-Now-2026!");users.save(new AppUser("admin",encoder.encode(password),"ROLE_ADMIN"));}
  if(departments.count()==0){Department it=departments.save(new Department("Technology","Software, systems and digital services."));Department hr=departments.save(new Department("Human Resources","People, learning and workplace support."));Department ops=departments.save(new Department("Operations","Service delivery and daily operations."));
   employees.save(emp("Amina Yusuf","amina@example.com","Software Developer",it,450000d));employees.save(emp("Musa Ibrahim","musa@example.com","Systems Analyst",it,380000d));employees.save(emp("Fatima Bello","fatima@example.com","HR Officer",hr,280000d));employees.save(emp("Daniel James","daniel@example.com","Operations Lead",ops,350000d));
   projects.save(proj("Staff Portal","Internal staff self-service portal","In progress",it));projects.save(proj("Learning Week","Quarterly staff training programme","Planned",hr));projects.save(proj("Service Review","Review and improve service workflows","Completed",ops)); }
 };}
 private Employee emp(String n,String e,String j,Department d,Double s){Employee x=new Employee();x.setFullName(n);x.setEmail(e);x.setJobTitle(j);x.setDepartment(d);x.setSalary(s);return x;}
 private Project proj(String n,String d,String s,Department dep){Project x=new Project();x.setName(n);x.setDescription(d);x.setStatus(s);x.setDepartment(dep);return x;}
}
