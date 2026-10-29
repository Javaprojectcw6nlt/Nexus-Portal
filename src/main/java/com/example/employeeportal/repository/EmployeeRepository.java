package com.example.employeeportal.repository;
import com.example.employeeportal.model.Employee; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.data.jpa.repository.Query; import java.util.List;
 public interface EmployeeRepository extends JpaRepository<Employee,Long> { @Query("select d.name, count(e) from Employee e join e.department d group by d.name order by d.name") List<Object[]> countByDepartment(); }
