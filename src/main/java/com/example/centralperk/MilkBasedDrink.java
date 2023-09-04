package com.example.centralperk;

import java.util.Arrays;
import java.util.List;

public class MilkBasedDrink extends Drink {
    Size size = Size.MEDIUM;

    public MilkBasedDrink(String name, List<Ingredient> ingredients) {
        super(name, ingredients);
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    double calculateFinalPrice() {
        double price = ingredients.stream().mapToDouble(Ingredient::getPricePerUnit).sum();
        if (size == Size.SMALL) {
            price += 3;
        }
        if (size == Size.MEDIUM) {
            price += 4;
        }
        if (size == Size.LARGE) {
            price += 5;
        }
        return price;
    }
}
