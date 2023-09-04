package com.example.centralperk;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Worker extends ObjectPlus implements Serializable {

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private Map<Integer, Certificate> certificatesQualif = new TreeMap<>();
    private List<Order> orders = new ArrayList<>();


    public Worker(String firstName, String lastName, LocalDate dob) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Worker(String firstName, String lastName, LocalDate dob, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addCertificateQualif(Certificate newCertificate) {
        if (newCertificate != null && !certificatesQualif.containsKey(newCertificate.getId())) {
            certificatesQualif.put(newCertificate.getId(), newCertificate);
            newCertificate.addPerson(this);
        }
    }

    public Certificate findCertificateQualif(Integer id) throws Exception {
        if(id == null){
            throw new Exception("Incorrect id");
        }
        if(!certificatesQualif.containsKey(id)) {
            throw new Exception("Unable to find a qualification: " + id);
        }
        return certificatesQualif.get(id);
    }

    public void addOrder(Order newOrder) {
        if(newOrder != null && !orders.contains(newOrder)) {
            orders.add(newOrder);
            newOrder.addWorker(this);
        }
    }
}
