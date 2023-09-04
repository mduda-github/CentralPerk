package com.example.centralperk;

public enum OrderStatus {
    PENDING {
        public String toString() {
            return "Oczekujące";
        }
    },
    CONFIRMED {
        public String toString() {
            return "Złożone";
        }
    }
}
