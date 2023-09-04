package com.example.centralperk;

public interface IBarista {
     Manager becomeManager(Worker worker);
     void setExtraBonus(double extraBonus);
     double calculateSalary();
}
