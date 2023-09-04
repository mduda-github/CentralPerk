package com.example.centralperk;

import java.io.Serializable;

public class Address extends ObjectPlus implements Serializable {
    private String street;
    private int houseNumber;
    private String postcode;
    private String city;
    private String country;
    private Company company;

    public Address(String street, int houseNumber, String postcode, String city, String country) {
        super();
        this.street = street;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }

    public void addCompany(Company company) {
        if(this.company == null && company != null) {
            this.company = company;
            company.addAddress(this);
        }
    }
}
