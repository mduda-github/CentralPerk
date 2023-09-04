package com.example.centralperk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Food extends ObjectPlus implements Serializable {
    private String name;
    private Double price;
    private List<Order> orders = new ArrayList<>();

    public Food(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public void addOrder(Order newOrder) {
        if(newOrder != null && !orders.contains(newOrder)) {
            orders.add(newOrder);
            newOrder.addFood(this);
        }
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
