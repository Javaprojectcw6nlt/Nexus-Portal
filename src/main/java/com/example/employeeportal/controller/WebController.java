package com.example.employeeportal.controller;

import com.example.employeeportal.model.*;
import com.example.employeeportal.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    private final DepartmentRepository departments;
    private final EmployeeRepository employees;
    private final ProjectRepository projects;
    private final ContactMessageRepository messages;
    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    public WebController(DepartmentRepository d, EmployeeRepository e, ProjectRepository p, ContactMessageRepository m, AppUserRepository u, PasswordEncoder enc) {
        departments = d;
        employees = e;
        projects = p;
        messages = m;
        users = u;
        encoder = enc;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employeeCount", employees.count());
        model.addAttribute("departmentCount", departments.count());
        model.addAttribute("projectCount", projects.count());
        model.addAttribute("latestEmployees", employees.findAll().stream().limit(4).toList());
        return "index";
    }

    @GetMapping("/database")
    public String database(Model m) {
        m.addAttribute("departments", departments.findAll());
        m.addAttribute("employees", employees.findAll());
        m.addAttribute("projects", projects.findAll());
        return "database";
    }

    @GetMapping("/chart")
    public String chart() {
        return "chart";
    }

    @GetMapping("/contact")
    public String contact(Model m) {
        m.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String sendContact(@Valid @ModelAttribute ContactMessage contactMessage, BindingResult result, RedirectAttributes ra, Model model) {
        if (result.hasErrors()) return "contact";
        messages.save(contactMessage);
        ra.addFlashAttribute("success", "Thanks! Your message was saved successfully.");
        return "redirect:/contact";
    }

    @GetMapping("/messages")
    public String messages(Model m) {
        m.addAttribute("messages", messages.findAllByOrderBySentAtDesc());
        return "messages";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model m) {
        m.addAttribute("username", " ");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, RedirectAttributes ra, Model model) {
        String u = username == null ? "" : username.trim();
        if (!u.matches("[A-Za-z0-9._-]{3,40}")) {
            model.addAttribute("error", "Username must be 3–40 characters and use letters, numbers, dot, underscore or hyphen.");
            return "register";
        }
        if (password == null || password.length() < 10) {
            model.addAttribute("error", "Password must be at least 10 characters.");
            return "register";
        }
        if (users.existsByUsername(u)) {
            model.addAttribute("error", "That username is already registered.");
            return "register";
        }
        users.save(new AppUser(u, encoder.encode(password), "ROLE_USER"));
        ra.addFlashAttribute("success", "Registration complete. You can now sign in.");
        return "redirect:/login";
    }

    @GetMapping("/crud")
    public String crud(Model m) {
        m.addAttribute("employees", employees.findAll());
        return "crud";
    }

    @GetMapping("/crud/new")
    public String newEmployee(Model m) {
        m.addAttribute("employee", new Employee());
        m.addAttribute("departments", departments.findAll());
        return "employee-form";
    }

    @GetMapping("/crud/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model m) {
        m.addAttribute("employee", employees.findById(id).orElseThrow());
        m.addAttribute("departments", departments.findAll());
        return "employee-form";
    }

    @PostMapping("/crud/save")
    public String saveEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model m, RedirectAttributes ra) {
        if (result.hasErrors()) {
            m.addAttribute("departments", departments.findAll());
            return "employee-form";
        }

        // Resolve the department
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            employee.setDepartment(departments.findById(employee.getDepartment().getId()).orElse(null));
        } else {
            employee.setDepartment(null);
        }

        // Fixed: Prevent creating duplicate records when updating an existing employee
        if (employee.getId() != null) {
            // It's an update. Fetch the existing record so we don't create a duplicate.
            Employee existing = employees.findById(employee.getId()).orElseThrow();
            existing.setFullName(employee.getFullName());
            existing.setEmail(employee.getEmail());
            existing.setJobTitle(employee.getJobTitle());
            existing.setDepartment(employee.getDepartment());
            existing.setSalary(employee.getSalary());
            employees.save(existing);
        } else {
            // It's a new record.
            employees.save(employee);
        }

        ra.addFlashAttribute("success", "Employee record saved.");
        return "redirect:/crud";
    }

    @PostMapping("/crud/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes ra) {
        employees.deleteById(id);
        ra.addFlashAttribute("success", "Employee record deleted.");
        return "redirect:/crud";
    }
}