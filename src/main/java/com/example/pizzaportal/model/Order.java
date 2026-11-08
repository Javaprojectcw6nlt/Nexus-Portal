package com.example.pizzaportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // 'order' is a reserved keyword in SQL, so we use 'orders'
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pizzaname;
    private Integer amount;
    private LocalDateTime taken;
    private LocalDateTime dispatched;
    private String deliveryMethod; // "Pickup" or "Delivery"
    private String address;        // only used for delivery

    // Constructors
    public Order() {}
    public Order(String pizzaname, Integer amount, LocalDateTime taken, LocalDateTime dispatched) {
        this.pizzaname = pizzaname;
        this.amount = amount;
        this.taken = taken;
        this.dispatched = dispatched;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPizzaname() { return pizzaname; }
    public void setPizzaname(String pizzaname) { this.pizzaname = pizzaname; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public LocalDateTime getTaken() { return taken; }
    public void setTaken(LocalDateTime taken) { this.taken = taken; }
    public LocalDateTime getDispatched() { return dispatched; }
    public void setDispatched(LocalDateTime dispatched) { this.dispatched = dispatched; }
    public String getDeliveryMethod() { return deliveryMethod; }
    public void setDeliveryMethod(String deliveryMethod) { this.deliveryMethod = deliveryMethod; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}