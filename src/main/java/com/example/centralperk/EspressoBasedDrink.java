package com.example.centralperk;

import java.util.Arrays;
import java.util.List;

public class EspressoBasedDrink extends Drink {
    BeanRoast beanRoast = BeanRoast.MEDIUM;

    public EspressoBasedDrink(String name, List<Ingredient> ingredients) {
        super(name, ingredients);
    }

    public void setBeanRoast(BeanRoast beanRoast) {
        this.beanRoast = beanRoast;
    }

    @Override
    double calculateFinalPrice() {
        double price = ingredients.stream().mapToDouble(Ingredient::getPricePerUnit).sum();
        if (beanRoast == BeanRoast.LIGHT) {
            price += 2;
        }
        if (beanRoast == BeanRoast.MEDIUM) {
            price += 3;
        }
        if (beanRoast == BeanRoast.DARK) {
            price += 4;
        }
        return price;
    }
}
