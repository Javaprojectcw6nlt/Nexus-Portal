package com.example.pizzaportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Category {
    @Id
    private String cname;
    private Double price;

    @OneToMany(mappedBy = "category")
    private List<Pizza> pizzas;

    public Category() {}
    public Category(String cname, Double price) {
        this.cname = cname;
        this.price = price;
    }

    public String getCname() { return cname; }
    public void setCname(String cname) { this.cname = cname; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}