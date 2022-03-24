package com.example.programame_project_api.entities;

import javax.persistence.*;

import static com.example.programame_project_api.ProgramameProjectApiApplication.*;

@Entity
@Table(name = "ComplexDonations")
public class ComplexDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(nullable = false)
    private double amountForSimpleProblem;

    @Column(nullable = false)
    private double amountForMediumProblem;

    @Column(nullable = false)
    private double amountForHardProblem;




    @OneToOne(  mappedBy = "complexDonation", fetch = FetchType.LAZY)
    private Sponsor sponsor;


    public ComplexDonation() {
    }

    public ComplexDonation(double amountForSimpleProblem,
                           double amountForMediumProblem,
                           double amountForHardProblem) {
        this.amountForSimpleProblem = amountForSimpleProblem;
        this.amountForMediumProblem = amountForMediumProblem;
        this.amountForHardProblem = amountForHardProblem;
    }


    public long getId() {
        return id;
    }

    public double getAmountForSimpleProblem() {
        return amountForSimpleProblem;
    }

    public void setAmountForSimpleProblem(double amountForSimpleProblem) {
        this.amountForSimpleProblem = amountForSimpleProblem;
    }

    public double getAmountForMediumProblem() {
        return amountForMediumProblem;
    }

    public void setAmountForMediumProblem(double amountForMediumProblem) {
        this.amountForMediumProblem = amountForMediumProblem;
    }

    public double getAmountForHardProblem() {
        return amountForHardProblem;
    }

    public void setAmountForHardProblem(double amountForHardProblem) {
        this.amountForHardProblem = amountForHardProblem;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }




}
