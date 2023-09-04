package com.example.centralperk;

import java.time.LocalDate;

public class Barista extends Worker implements IBarista {
    private static double BASIC_BONUS = 200.00;
    private double extraBonus;

    public Barista(String firstName, String lastName, LocalDate dob) {
        super(firstName, lastName, dob);
    }

    @Override
    public Manager becomeManager(Worker worker) {
        Manager manager = null;
        try {
            manager = new Manager(worker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager;
    }

    @Override
    public void setExtraBonus(double extraBonus) {
        this.extraBonus = extraBonus;
    }

    @Override
    public double calculateSalary() {
        return BASIC_BONUS + extraBonus;
    }

//    @Override
//    public String toString() {
//        for (Order o : super.getOrders()) {
//            System.out.println(o.toString());
//        }
//
//        return "Barista" + super.getOrders();
//    }
}
