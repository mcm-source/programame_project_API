package com.example.programame_project_api.entities;

import javax.persistence.*;


@Entity
@Table(name = "SimpleDonations")
public class SimpleDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(nullable = false)
    private float amount;


    @OneToOne(  mappedBy = "simpleDonation", fetch = FetchType.LAZY)
    private Sponsor sponsor;

    public SimpleDonation() {
    }

    public SimpleDonation(float amount, Sponsor sponsor) {
        this.amount = amount;
        this.sponsor = sponsor;
    }


}
