package com.example.centralperk;

import java.time.LocalDate;

public class Manager extends Worker {
    public Manager(String firstName, String lastName, LocalDate dob) {
        super(firstName, lastName, dob);
    }

    public Manager(Worker worker) {
        super(worker.getFirstName(), worker.getLastName(), worker.getDob());
    }

    public void generateRaport() {
        System.out.println("Raport");
    }
}
