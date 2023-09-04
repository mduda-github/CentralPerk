package com.example.centralperk;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderItem {

    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public OrderItem(String name, double price) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }
    public String getOrderItemName() {
        return name.get();
    }

    public double getOrderItemPrice() {
        return price.get();
    }


}
