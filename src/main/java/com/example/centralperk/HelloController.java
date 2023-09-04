package com.example.centralperk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class HelloController implements Initializable  {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ListView<Order> lvNewOrders;

    @FXML
    void handleOnNewOrderClick(ActionEvent event) throws IOException {
        AnchorPane orderView = FXMLLoader.load(HelloApplication.class.getResource("order-view.fxml"));
        rootAnchorPane.getChildren().setAll(orderView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        try {
            ObjectPlus.getExtent(Order.class).forEach(order -> {
                if (order.getStatus() == OrderStatus.CONFIRMED) {
                    orders.add(order);
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        lvNewOrders.setItems(orders);


    }


}
