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

    public ComplexDonation(float amountForSimpleProblem,
                           float amountForMediumProblem,
                           float amountForHardProblem) {
        this.amountForSimpleProblem = amountForSimpleProblem;
        this.amountForMediumProblem = amountForMediumProblem;
        this.amountForHardProblem = amountForHardProblem;
    }
}
