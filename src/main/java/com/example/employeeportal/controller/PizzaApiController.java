package com.example.employeeportal.controller;

import com.example.employeeportal.model.Pizza;
import com.example.employeeportal.model.Category;
import com.example.employeeportal.repository.PizzaRepository;
import com.example.employeeportal.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaApiController {

    private final PizzaRepository repo;
    private final CategoryRepository categories;

    public PizzaApiController(PizzaRepository r, CategoryRepository c) {
        repo = r;
        categories = c;
    }

    @GetMapping
    public List<Pizza> all() {
        return repo.findAll();
    }

    @GetMapping("/{pname}")
    public Pizza one(@PathVariable String pname) {
        return repo.findById(pname)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pizza not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza create(@Valid @RequestBody Pizza p) {
        p.setCategory(resolveCategory(p.getCategory()));
        return repo.save(p);
    }

    @PutMapping("/{pname}")
    public Pizza update(@PathVariable String pname, @Valid @RequestBody Pizza p) {
        Pizza existing = repo.findById(pname)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pizza not found"));

        existing.setCategory(resolveCategory(p.getCategory()));
        existing.setVegetarian(p.getVegetarian());

        return repo.save(existing);
    }

    private Category resolveCategory(Category c) {
        if (c == null || c.getCname() == null) return null;
        return categories.findById(c.getCname())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Category not found"));
    }

    @DeleteMapping("/{pname}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String pname) {
        if (!repo.existsById(pname)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found");
        }
        repo.deleteById(pname);
    }
}