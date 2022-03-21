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

    public SimpleDonation(float amount) {
        this.amount = amount;

    }

    public long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }
}
