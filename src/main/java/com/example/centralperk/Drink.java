package com.example.centralperk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Drink extends ObjectPlus implements Serializable {
    private String name;
    protected List<Ingredient> ingredients;
    private List<Order> orders = new ArrayList<>();

    public Drink(String name, List<Ingredient> ingredients) {
        super();
        this.name = name;
        this.ingredients = ingredients;
    }

    public boolean isFit() {
        AtomicInteger totalKcal = new AtomicInteger();
        ingredients.forEach(ingredient -> totalKcal.addAndGet(ingredient.getKcal()));

        return totalKcal.intValue() > 100;
    }

    public void addOrder(Order newOrder) {
        if(newOrder != null && !orders.contains(newOrder)) {
            orders.add(newOrder);
            newOrder.addDrink(this);
        }
    }

    public String getName() {
        return name;
    }

    abstract double calculateFinalPrice();

    static void showOffer() throws Exception {
        ObjectPlus.showExtent(Drink.class);
    }

    @Override
    public String toString() {
        return name;
    }
}
