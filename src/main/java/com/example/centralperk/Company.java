package com.example.centralperk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Company extends ObjectPlus implements Serializable {
    private String companyName;
    private String NIPNumber;
    private List<Branch> branches = new ArrayList<>();
    private Owner owner;
    private Address address;

    public Company(String name, String NIPNumber) {
        super();
        this.companyName = name;
        this.NIPNumber = NIPNumber;
    }

    public Branch createBranch(String name) {
        Branch branch = new Branch(name);
        branches.add(branch);

        return branch;
    }

    public void addOwner(Owner owner) {
        if(this.owner == null && owner != null) {
            this.owner = owner;
            owner.addCompany(this);
        }
    }

    public void addAddress(Address address) {
        if(this.address == null && address != null) {
            this.address = address;
            address.addCompany(this);
        }
    }

    @Override
    public String toString() {
        return "Company: " + companyName;
    }

    public class Branch extends ObjectPlus implements Serializable {
        private String branchName;
        private static int OPEN_HOUR = 7;
        private static int CLOSE_HOUR = 20;

        public Branch(String name) {
            super();
            this.branchName = name;
        }

        public Company getCompany() {
            return Company.this;
        }

        @Override
        public String toString() {
            return "Branch: " + branchName;
        }

    }
}
