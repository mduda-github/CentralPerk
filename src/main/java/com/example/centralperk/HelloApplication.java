package com.example.centralperk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

public class HelloApplication extends Application {
    private static void populateSystem() throws Exception {
        // Create Owner
        Owner owner = new Owner("John", "Smith", LocalDate.of(1970, 1, 1));

        // Create Company and Branch
        Company goodCoffees = new Company("Good Coffees S.A.", "120391031");
        Company.Branch centralPerk = goodCoffees.createBranch("Central Perk");

        // Create Address and link it to Company
        Address companyAddress = new Address("Marszałkowska", 22, "01-123", "Warszawa", "Polska");
        goodCoffees.addAddress(companyAddress);

        // Create link between Owner and Company
        goodCoffees.addOwner(owner);

        // Create Manager, Supervisor and Barista
        Manager manager = new Manager("TestManager", "TestManager", LocalDate.of(1980, 1, 1));
        Supervisor supervisor = new Supervisor("TestSupervisor", "TestSupervisor", LocalDate.of(1980, 1, 1));
        Barista barista = new Barista("TestBarista", "TestBarista", LocalDate.of(1980, 1, 1));

        // Create Certificate and add it to Barista
        Certificate certificate = new Certificate("PPK", "Podstawy parzenia kawy");

        barista.addCertificateQualif(certificate);
        supervisor.addCertificateQualif(certificate);
        manager.addCertificateQualif(certificate);

        // Create dynamic Manager
        Manager newManager = supervisor.becomeManager(supervisor);

        // Create Ingredients
        Ingredient ingredientEspresso = new Ingredient("Shot Espresso", 7.00, 5);
        Ingredient ingredientChocolate = new Ingredient("Czekolada", 1.25, 140);
        Ingredient ingredientVanillaIceCream = new Ingredient("Lody waniliowe", 5.00, 140);
        Ingredient ingredientSteamedMilk = new Ingredient("Mleko", 0.20, 45);
        Ingredient ingredientFoamedMilk = new Ingredient("Spienione mleko", 0.20, 45);
        Ingredient ingredientWater = new Ingredient("Woda", 0.00, 0);

        // Create EspressoBasedDrinks
        EspressoBasedDrink espresso = new EspressoBasedDrink("Espresso", Arrays.asList(ingredientEspresso));
        EspressoBasedDrink doppio = new EspressoBasedDrink("Doppio", Arrays.asList(ingredientEspresso, ingredientEspresso));
        EspressoBasedDrink lungo = new EspressoBasedDrink("Lungo", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientEspresso));
        EspressoBasedDrink americano = new EspressoBasedDrink("Americano", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientWater, ingredientWater, ingredientWater));
        EspressoBasedDrink cafeConBielo = new EspressoBasedDrink("Cafe con bielo", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientWater, ingredientWater, ingredientWater));

        // Create MilkBasedDrinks
        MilkBasedDrink macchiato = new MilkBasedDrink("Macchiato", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientFoamedMilk));
        MilkBasedDrink capuccino = new MilkBasedDrink("Capuccino", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientSteamedMilk, ingredientSteamedMilk, ingredientFoamedMilk, ingredientFoamedMilk));
        MilkBasedDrink affogato = new MilkBasedDrink("Affogato", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientVanillaIceCream));
        MilkBasedDrink mocha = new MilkBasedDrink("Mocha", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientChocolate, ingredientChocolate, ingredientSteamedMilk));
        MilkBasedDrink flatWhite = new MilkBasedDrink("Flat white", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk));
        MilkBasedDrink latte = new MilkBasedDrink("Latte", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk));
        MilkBasedDrink doubleLatte = new MilkBasedDrink("Double latte", Arrays.asList(ingredientEspresso, ingredientEspresso, ingredientEspresso, ingredientEspresso, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk, ingredientSteamedMilk));

        // Create Foods
        Food chocolateCake = new Food("Ciasto czekoladowe", 14.50);
        Food cheeseCake = new Food("Sernik", 16.50);
        Food sandwich = new Food("Kanapka", 9.00);

        OrderItem orderItem1 = new OrderItem(latte.getName(), latte.calculateFinalPrice());
        OrderItem orderItem2 = new OrderItem(espresso.getName(), espresso.calculateFinalPrice());


        // Create Order and link it to Worker
        Order order = new Order(new Date());
        order.addWorker(barista);
        order.setStatus(OrderStatus.CONFIRMED);



    }

    public static void main(String[] args) throws Exception {
//        populateSystem();
//        writeExtentsToFile();
        readExtentsFromFile();
        launch();
    }

    protected static void writeExtentsToFile() {
        final String extentFile = "allExtents.bin";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(extentFile)));
            ObjectPlus.writeExtents(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readExtentsFromFile() {
        final String extentFile = "allExtents.bin";

        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(extentFile)));
            ObjectPlus.readExtents(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Central Perk");
        stage.setScene(scene);
        stage.show();
    }
}