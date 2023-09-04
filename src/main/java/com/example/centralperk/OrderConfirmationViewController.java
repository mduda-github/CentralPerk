package com.example.centralperk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderConfirmationViewController implements Initializable {

    @FXML
    private Label lPaymentStatus;

    @FXML
    private Label lTotal;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TableView<OrderItemModel> tOrder;

    @FXML
    private TableColumn<OrderItemModel, Double> tcPrice;

    @FXML
    private TableColumn<OrderItemModel, String> tcProduct;

    @FXML
    void handleOnCancel(ActionEvent event) throws IOException {
        AnchorPane orderView = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        rootAnchorPane.getChildren().setAll(orderView);
    }

    @FXML
    void handleOnConfirm(ActionEvent event) throws Exception {
        boolean isPaymentConfirmed = lPaymentStatus.getText() == "Potwierdzona";

        if (isPaymentConfirmed) {
            AnchorPane orderView = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
            rootAnchorPane.getChildren().setAll(orderView);
            HelloApplication.writeExtentsToFile();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nowe zamówienie");
            alert.setHeaderText(null);
            alert.setContentText("Płatność nie została potwierdzona");
            alert.showAndWait();
        }


    }

    @FXML
    void handlePayment(ActionEvent event) throws ClassNotFoundException {
        ButtonType bYes = new ButtonType("Tak", ButtonBar.ButtonData.OK_DONE);
        ButtonType bNo = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "Czy płatność została przyjęta?",
                        bYes,
                        bNo);
        alert.setHeaderText(null);
        alert.setTitle("Płatność");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == bYes) {
            lPaymentStatus.setText("Potwierdzona");
            Order order = ObjectPlus.getExtent(Order.class).stream().reduce((prev, next) -> next).orElse(null);
            order.setStatus(OrderStatus.CONFIRMED);
        }
    }

    private void calculateTotal() {
        var total = 0;
        for (OrderItemModel row : tOrder.getItems()) {
            total += row.getOrderItemPrice();
        }
        lTotal.setText(String.valueOf(total) + " zł");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("OrderItemPrice"));
        tcPrice.setStyle( "-fx-alignment: CENTER-RIGHT;");
        tcProduct.setCellValueFactory(new PropertyValueFactory<>("OrderItemName"));

        populateTableView();
        calculateTotal();
    }

    private void populateTableView() {
        ObservableList<OrderItemModel> orderItems = FXCollections.observableArrayList();

        try {
            Order order = ObjectPlus.getExtent(Order.class).stream().reduce((prev, next) -> next).orElse(null);

            order.getDrinks().forEach(drink -> {
                var item = new OrderItemModel(drink.getName(), drink.calculateFinalPrice());
                orderItems.add(item);
            });

            order.getFoods().forEach(food -> {
                var item = new OrderItemModel(food.getName(), food.getPrice());
                orderItems.add(item);
            });

            order.getIngredients().forEach(ingredient -> {
                var item = new OrderItemModel(ingredient.getName(), ingredient.getPricePerUnit());
                orderItems.add(item);
            });

            if (order.hasOwnCup()) {
                orderItems.add(new OrderItemModel("Rabat", -4));
            };


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };


        tOrder.setItems(orderItems);
    }

}
