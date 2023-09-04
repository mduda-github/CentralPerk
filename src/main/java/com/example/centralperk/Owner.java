package com.example.centralperk;

import java.time.LocalDate;

public class Owner extends Manager {
    public Company company;
    public Owner(String firstName, String lastName, LocalDate dob) {
        super(firstName, lastName, dob);
    }

    public void addCompany(Company company) {
        if(this.company == null && company != null) {
            this.company = company;
            company.addOwner(this);
        }
    }
}
