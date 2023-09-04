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
import java.util.*;

public class OrderViewController implements Initializable {

    @FXML
    private CheckBox cbForHere;

    @FXML
    private CheckBox cdOwnCup;

    @FXML
    private CheckBox cdToGo;

    @FXML
    private Label lTotal;

    @FXML
    private ListView<Drink> lvDrinks;

    @FXML
    private ListView<Food> lvFood;

    @FXML
    private ListView<Ingredient> lvIngredient;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TableView<OrderItemModel> tOrder;

    @FXML
    private TableColumn<OrderItemModel, Double> tcPrice;

    @FXML
    private TableColumn<OrderItemModel, String> tcProduct;

    private Order order;

    private final OrderItemModel rebate = new OrderItemModel("Rabat", -4);

    public OrderViewController() {
        this.order = new Order(new Date());
    }

    @FXML
    void handleForHere(ActionEvent event) {
        if (cdToGo.isSelected()) {
            order.setToGo(true);
            cdToGo.setSelected(false);
        }
    }

    @FXML
    void handleToGo(ActionEvent event) {
        if (cbForHere.isSelected()) {
            order.setToGo(false);
            cbForHere.setSelected(false);
        }
    }

    private void showDrinkPopup(Drink drink) {
        boolean isMilkBasedDrink = drink.getClass().getSimpleName().equals("MilkBasedDrink");

        String beansMessage = "Wybierz stopień wypalenia ziaren";
        String sizeMessage = "Wybierz pojemność napoju";

        ButtonType lightRoast = new ButtonType("Jasnopalone", ButtonBar.ButtonData.OK_DONE);
        ButtonType mediumRoast = new ButtonType("Średniopalone", ButtonBar.ButtonData.OK_DONE);
        ButtonType darkRoast = new ButtonType("Ciemnopalone", ButtonBar.ButtonData.OK_DONE);

        ButtonType smallSize = new ButtonType("Mały", ButtonBar.ButtonData.OK_DONE);
        ButtonType mediumSize = new ButtonType("Średni", ButtonBar.ButtonData.OK_DONE);
        ButtonType largeSize = new ButtonType("Duży", ButtonBar.ButtonData.OK_DONE);


        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        isMilkBasedDrink ? sizeMessage : beansMessage,
                        isMilkBasedDrink ? smallSize : lightRoast,
                        isMilkBasedDrink ? mediumSize : mediumRoast,
                        isMilkBasedDrink ? largeSize : darkRoast);
        alert.setHeaderText(null);
        alert.setTitle("Potwierdzenie");
        Optional<ButtonType> result = alert.showAndWait();

    }

    @FXML
    void handleOnAdd(ActionEvent event) {
        Drink selectedDrink = lvDrinks.getSelectionModel().getSelectedItem();
        Food selectedFood = lvFood.getSelectionModel().getSelectedItem();
        Ingredient selectedIngredient = lvIngredient.getSelectionModel().getSelectedItem();

        if (selectedDrink != null) {
            showDrinkPopup(selectedDrink);
            tOrder.getItems().add(new OrderItemModel(selectedDrink.getName(), selectedDrink.calculateFinalPrice()));
            order.addDrink(selectedDrink);
            calculateTotal();
            clearListViewSelections();
        }

        if (selectedFood != null) {
            tOrder.getItems().add(new OrderItemModel(selectedFood.getName(), selectedFood.getPrice()));
            order.addFood(selectedFood);
            calculateTotal();
            clearListViewSelections();
        }

        if (selectedIngredient != null) {
            tOrder.getItems().add(new OrderItemModel(selectedIngredient.getName(), selectedIngredient.getPricePerUnit()));
            order.addIngredient(selectedIngredient);
            calculateTotal();
            clearListViewSelections();
        }
    }

    private void clearListViewSelections() {
        lvDrinks.getSelectionModel().clearSelection();
        lvFood.getSelectionModel().clearSelection();
        lvIngredient.getSelectionModel().clearSelection();
    }

    @FXML
    void handleOnCancel(ActionEvent event) throws IOException {
        AnchorPane orderView = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        rootAnchorPane.getChildren().setAll(orderView);
    }

    @FXML
    void handleOnConfirm(ActionEvent event) throws IOException {
        boolean areCheckboxesValid = (cbForHere.isSelected() && !cdToGo.isSelected() || (!cbForHere.isSelected() && cdToGo.isSelected()));
        boolean isProductAdded = tOrder.getItems().size() > 0;
        String errorMessage = "";


        if(!areCheckboxesValid) {
            errorMessage += "Oznacz typ zamówienia \n";
        }

        if(!isProductAdded) {
            errorMessage += "Dodaj produkt do listy \n";
        }


        if (errorMessage.length() == 0) {
            AnchorPane orderView = FXMLLoader.load(HelloApplication.class.getResource("order-confirmation-view.fxml"));
            rootAnchorPane.getChildren().setAll(orderView);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nowe zamówienie");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }

    @FXML
    void handleOnRemove(ActionEvent event) {
        OrderItemModel selectedItem = tOrder.getSelectionModel().getSelectedItem();
        tOrder.getItems().remove(selectedItem);
        cdOwnCup.setSelected(false);
        calculateTotal();
    }

    @FXML
    void handleOwnCup(ActionEvent event) {
        if(cdOwnCup.isSelected()) {
            tOrder.getItems().add(rebate);
            order.setHasOwnCup(true);
        } else {
            tOrder.getItems().remove(rebate);
            order.setHasOwnCup(false);
        }
        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0.00;
        for (OrderItemModel row : tOrder.getItems()) {
            total += row.getOrderItemPrice();
        }
        lTotal.setText(String.format("%.2f", total) + " zł");
    }

    private void populateListViews() {
        // Add Drinks to the list
        ObservableList<Drink> drinks = FXCollections.observableArrayList();
        try {
            drinks.addAll(ObjectPlus.getExtent(MilkBasedDrink.class));
            drinks.addAll(ObjectPlus.getExtent(EspressoBasedDrink.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        lvDrinks.setItems(drinks);

        // Add Foods to the list
        ObservableList<Food> foods = FXCollections.observableArrayList();
        try {
            foods.addAll(ObjectPlus.getExtent(Food.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        lvFood.setItems(foods);

        // Add Foods to the list
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        try {
            ingredients.addAll(ObjectPlus.getExtent(Ingredient.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        lvIngredient.setItems(ingredients);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tOrder.setPlaceholder(new Label("Dodaj produkt"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("OrderItemPrice"));
        tcPrice.setStyle( "-fx-alignment: CENTER-RIGHT;");
        tcProduct.setCellValueFactory(new PropertyValueFactory<>("OrderItemName"));

        populateListViews();


    }
}
