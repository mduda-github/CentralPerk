package com.example.centralperk;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class OrderItemModel {

    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public OrderItemModel(String name, double price) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getOrderItemName() {
        return name.get();
    }

    public void setOrderItemName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public double getOrderItemPrice() {
        return price.get();
    }

    public void setOrderItemPrice(int price) {
        this.price = new SimpleDoubleProperty(price);
    }

    @Override
    public String toString() {
        return "OrderItemModel{" +
                "name=" + name +
                ", price=" + price +
                '}';
    }
}
