package com.example.pizzaportal.controller;

import com.example.pizzaportal.model.Order;
import com.example.pizzaportal.model.Pizza;
import com.example.pizzaportal.repository.OrderRepository;
import com.example.pizzaportal.repository.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Controller
public class OrderController {

    private final PizzaRepository pizzas;
    private final OrderRepository orders;

    public OrderController(PizzaRepository pizzas, OrderRepository orders) {
        this.pizzas = pizzas;
        this.orders = orders;
    }

    @GetMapping("/order")
    public String orderForm(@RequestParam(required = false) String pizza, Model m) {
        m.addAttribute("selected", pizza);
        m.addAttribute("pizzas", pizzas.findAll().stream()
                .sorted(Comparator.comparing(Pizza::getPname))
                .toList());
        return "order";
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam String pizzaname,
                             @RequestParam Integer amount,
                             @RequestParam(defaultValue = "Pickup") String deliveryMethod,
                             @RequestParam(required = false) String address,
                             RedirectAttributes ra) {
        Pizza pizza = pizzas.findById(pizzaname).orElse(null);
        if (pizza == null) {
            ra.addFlashAttribute("error", "Please choose a pizza from the menu.");
            return "redirect:/order";
        }
        if (amount == null || amount < 1 || amount > 20) {
            ra.addFlashAttribute("error", "Amount must be between 1 and 20.");
            return "redirect:/order";
        }

        boolean delivery = "Delivery".equals(deliveryMethod);
        String cleanAddress = address == null ? "" : address.trim();
        if (delivery && cleanAddress.length() < 5) {
            ra.addFlashAttribute("error", "Please enter your delivery address.");
            return "redirect:/order";
        }

        LocalDateTime taken = LocalDateTime.now().withNano(0);
        LocalDateTime estimated = taken.plusMinutes(delivery ? 30 : 20);
        Order order = new Order(pizza.getPname(), amount, taken, estimated);
        order.setDeliveryMethod(delivery ? "Delivery" : "Pickup");
        order.setAddress(delivery ? cleanAddress : null);
        orders.save(order);

        double total = pizza.getCategory().getPrice() * amount;
        ra.addFlashAttribute("success", "Thank you! Your order of " + amount + " x " + pizza.getPname()
                + " was received at " + taken.format(DateTimeFormatter.ofPattern("HH:mm"))
                + ". Total: " + Math.round(total) + " Ft. "
                + (delivery ? "Estimated delivery to " + cleanAddress + ": " : "Ready for pickup at: ")
                + estimated.format(DateTimeFormatter.ofPattern("HH:mm")) + ".");
        return "redirect:/order";
    }
}