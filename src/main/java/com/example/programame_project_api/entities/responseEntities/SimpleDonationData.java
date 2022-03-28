package com.example.programame_project_api.entities.responseEntities;

import org.springframework.stereotype.Component;

@Component
public class SimpleDonationData {


    private double amount;

    public SimpleDonationData() {
    }

    public SimpleDonationData(double amount) {
        this.amount = amount;

    }

    public double getAmount() {
        return amount;
    }
}
