package com.example.centralperk;

import java.time.LocalDate;

public class Supervisor extends Manager implements IBarista {
    private static double BASIC_BONUS = 200.00;
    private double extraBonus;

    public Supervisor(String firstName, String lastName, LocalDate dob) {
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
        return BASIC_BONUS + extraBonus + 5000;
    }
}
