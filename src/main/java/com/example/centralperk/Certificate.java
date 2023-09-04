package com.example.centralperk;

import java.io.Serializable;
import java.util.ArrayList;

public class Certificate extends ObjectPlus implements Serializable {
    private static Integer idCounter = 0;
    private Integer id;
    private String code;
    private String name;
    private ArrayList<Worker> workers = new ArrayList<>();

    public Certificate(String code, String name) {
        super();
        this.id = createID();
        this.code = code;
        this.name = name;
    }

    public void addPerson(Worker newWorker) {
        if(newWorker != null && !workers.contains(newWorker)) {
            this.workers.add(newWorker);
            newWorker.addCertificateQualif(this);
        }
    }

    public static synchronized Integer createID() {
        return idCounter++;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String info = "Qualification: " + this.name + "\n";

        for (Worker w : workers) {
            info += " Person: " + w.getFirstName() + " " + w.getLastName() + "\n";
        }

        return info;
    }
}
