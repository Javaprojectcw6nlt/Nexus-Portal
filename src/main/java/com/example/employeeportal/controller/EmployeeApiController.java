
package com.example.employeeportal.controller;

import com.example.employeeportal.model.Employee;
import com.example.employeeportal.model.Department;
import com.example.employeeportal.repository.EmployeeRepository;
import com.example.employeeportal.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeApiController {

    private final EmployeeRepository repo;
    private final DepartmentRepository departments;

    public EmployeeApiController(EmployeeRepository r, DepartmentRepository d) {
        repo = r;
        departments = d;
    }

    @GetMapping
    public List<Employee> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Employee one(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee not found"));
    }

    
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Employee create(@Valid @RequestBody Employee e) {
    e.setDepartment(resolveDepartment(e.getDepartment()));
    return repo.save(e);
}


    
@PutMapping("/{id}")
public Employee update(@PathVariable Long id, @Valid @RequestBody Employee e) {
    Employee existing = repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee not found"));

    existing.setFullName(e.getFullName());
    existing.setEmail(e.getEmail());
    existing.setJobTitle(e.getJobTitle());
    existing.setDepartment(resolveDepartment(e.getDepartment()));
    existing.setSalary(e.getSalary());

    return repo.save(existing);
}


    private Department resolveDepartment(Department d) {
        if (d == null || d.getId() == null) {
            return null;
        }

        return departments.findById(d.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Department not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Employee not found");
        }

        repo.deleteById(id);
    }
}
