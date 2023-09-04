package com.example.centralperk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends ObjectPlus implements Serializable {
    private static Integer idCounter = 0;
    private Integer id;
    Date date;
    boolean hasOwnCup;
    boolean isToGo;
    OrderStatus status = OrderStatus.PENDING;
    private List<Worker> workers = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();


    public Order(Date date) {
        super();
        this.date = date;
        this.id = createID();
    }

    public static synchronized Integer createID() {
        return idCounter++;
    }


    public boolean hasOwnCup() {
        return hasOwnCup;
    }

    public void setHasOwnCup(boolean hasOwnCup) {
        this.hasOwnCup = hasOwnCup;
    }

    public boolean isToGo() {
        return isToGo;
    }

    public void setToGo(boolean toGo) {
        isToGo = toGo;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addWorker(Worker newWorker) {
        if(newWorker != null && !workers.contains(newWorker)) {
            workers.add(newWorker);
            newWorker.addOrder(this);
        }
    }

    public void addDrink(Drink newDrink) {
        if(newDrink != null && !drinks.contains(newDrink)) {
            drinks.add(newDrink);
            newDrink.addOrder(this);
        }
    }

    public void addFood(Food newFood) {
        if(newFood != null && !foods.contains(newFood)) {
            foods.add(newFood);
            newFood.addOrder(this);
        }
    }

    public void addIngredient(Ingredient newIngredient) {
        if(newIngredient != null && !ingredients.contains(newIngredient)) {
            ingredients.add(newIngredient);
            newIngredient.addOrder(this);
        }
    }

    @Override
    public String toString() {
        return "Zamówienie | Status: " + status;
    }


}
