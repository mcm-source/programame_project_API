package com.example.programame_project_api.entities;

import javax.persistence.*;

@Entity
@Table(name = "ComplexDonations")
public class ComplexDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(nullable = false)
    private float amountForSimpleProblem;

    @Column(nullable = false)
    private float amountForMediumProblem;

    @Column(nullable = false)
    private float amountForHardProblem;

    @OneToOne(  mappedBy = "complexDonation", fetch = FetchType.LAZY)
    private Sponsor sponsor;


    public ComplexDonation() {
    }

    public ComplexDonation(float amountForSimpleProblem,
                           float amountForMediumProblem,
                           float amountForHardProblem) {
        this.amountForSimpleProblem = amountForSimpleProblem;
        this.amountForMediumProblem = amountForMediumProblem;
        this.amountForHardProblem = amountForHardProblem;
    }


    public long getId() {
        return id;
    }


    public float getAmountForSimpleProblem() {
        return amountForSimpleProblem;
    }

    public void setAmountForSimpleProblem(float amountForSimpleProblem) {
        this.amountForSimpleProblem = amountForSimpleProblem;
    }

    public float getAmountForMediumProblem() {
        return amountForMediumProblem;
    }

    public void setAmountForMediumProblem(float amountForMediumProblem) {
        this.amountForMediumProblem = amountForMediumProblem;
    }

    public float getAmountForHardProblem() {
        return amountForHardProblem;
    }

    public void setAmountForHardProblem(float amountForHardProblem) {
        this.amountForHardProblem = amountForHardProblem;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }
}
