package com.example.employeeportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pizza {
    @Id
    private String pname;
    private Boolean vegetarian;

    @ManyToOne
    @JoinColumn(name = "categoryname")
    private Category category;

    // Constructors
    public Pizza() {}
    public Pizza(String pname, Category category, Boolean vegetarian) {
        this.pname = pname;
        this.category = category;
        this.vegetarian = vegetarian;
    }

    // Getters and Setters
    public String getPname() { return pname; }
    public void setPname(String pname) { this.pname = pname; }
    public Boolean getVegetarian() { return vegetarian; }
    public void setVegetarian(Boolean vegetarian) { this.vegetarian = vegetarian; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}