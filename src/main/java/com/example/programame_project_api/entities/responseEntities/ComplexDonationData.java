package com.example.programame_project_api.entities.responseEntities;

import org.springframework.stereotype.Component;

@Component
public class ComplexDonationData {

    private double amountForSimpleProblem;
    private double amountForMediumProblem;
    private double amountForHardProblem;


    public ComplexDonationData() {
    }

    public ComplexDonationData(double amountForSimpleProblem,
                               double amountForMediumProblem,
                               double amountForHardProblem) {
        this.amountForSimpleProblem = amountForSimpleProblem;
        this.amountForMediumProblem = amountForMediumProblem;
        this.amountForHardProblem = amountForHardProblem;
    }

    public double getAmountForSimpleProblem() {
        return amountForSimpleProblem;
    }

    public double getAmountForMediumProblem() {
        return amountForMediumProblem;
    }

    public double getAmountForHardProblem() {
        return amountForHardProblem;
    }
}
