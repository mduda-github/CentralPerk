package com.example.centralperk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ingredient extends ObjectPlus implements Serializable {
    private String name;
    private double pricePerUnit;
    private int kcal;
    private List<Order> orders = new ArrayList<>();

    public Ingredient(String name, double pricePerUnit, int kcal) {
        super();
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.kcal = kcal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void addOrder(Order newOrder) {
        if(newOrder != null && !orders.contains(newOrder)) {
            orders.add(newOrder);
            newOrder.addIngredient(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
