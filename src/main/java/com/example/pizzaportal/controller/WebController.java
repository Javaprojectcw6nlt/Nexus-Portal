package com.example.pizzaportal.controller;

import com.example.pizzaportal.model.*;
import com.example.pizzaportal.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    private final CategoryRepository categories;
    private final PizzaRepository pizzas;
    private final OrderRepository orders;
    private final ContactMessageRepository messages;
    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    public WebController(CategoryRepository c, PizzaRepository p, OrderRepository o, ContactMessageRepository m, AppUserRepository u, PasswordEncoder enc) {
        categories = c;
        pizzas = p;
        orders = o;
        messages = m;
        users = u;
        encoder = enc;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pizzaCount", pizzas.count());
        model.addAttribute("categoryCount", categories.count());
        model.addAttribute("orderCount", orders.count());
        model.addAttribute("latestPizzas", pizzas.findAll().stream().limit(4).toList());
        return "index";
    }

    @GetMapping("/database")
    public String database(Model m) {
        m.addAttribute("categories", categories.findAll());
        m.addAttribute("pizzas", pizzas.findAll());
        m.addAttribute("orders", orders.findAll().stream().limit(10).toList());
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
    public String sendContact(@Valid @ModelAttribute ContactMessage contactMessage, BindingResult result, RedirectAttributes ra) {
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

    // --- CRUD Operations for Pizza ---

    @GetMapping("/crud")
    public String crud(Model m) {
        m.addAttribute("pizzas", pizzas.findAll());
        return "crud";
    }

    @GetMapping("/crud/new")
    public String newPizza(Model m) {
        m.addAttribute("pizza", new Pizza());
        m.addAttribute("categories", categories.findAll());
        return "pizza-form";
    }

    @GetMapping("/crud/edit/{pname}")
    public String editPizza(@PathVariable String pname, Model m) {
        m.addAttribute("pizza", pizzas.findById(pname).orElseThrow());
        m.addAttribute("categories", categories.findAll());
        return "pizza-form";
    }

    @PostMapping("/crud/save")
    public String savePizza(@Valid @ModelAttribute Pizza pizza, BindingResult result, Model m, RedirectAttributes ra) {
        if (result.hasErrors()) {
            m.addAttribute("categories", categories.findAll());
            return "pizza-form";
        }

        if (pizza.getCategory() != null && pizza.getCategory().getCname() != null) {
            pizza.setCategory(categories.findById(pizza.getCategory().getCname()).orElse(null));
        } else {
            pizza.setCategory(null);
        }

        if (pizzas.existsById(pizza.getPname())) {
            Pizza existing = pizzas.findById(pizza.getPname()).orElseThrow();
            existing.setCategory(pizza.getCategory());
            existing.setVegetarian(pizza.getVegetarian());
            pizzas.save(existing);
        } else {
            pizzas.save(pizza);
        }

        ra.addFlashAttribute("success", "Pizza record saved.");
        return "redirect:/crud";
    }

    @PostMapping("/crud/delete/{pname}")
    public String deletePizza(@PathVariable String pname, RedirectAttributes ra) {
        pizzas.deleteById(pname);
        ra.addFlashAttribute("success", "Pizza record deleted.");
        return "redirect:/crud";
    }
}