package com.example.programame_project_api.entities;

import javax.persistence.*;

@Entity
@Table(name = "Sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(nullable = false)
    private String name;



    @ManyToOne
    private Team team;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "simple_donation_id", referencedColumnName = "id")
    private SimpleDonation simpleDonation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "complex_donation_id", referencedColumnName = "id")
    private ComplexDonation complexDonation;

    public Sponsor() {
    }

    public Sponsor(String name, Team team, SimpleDonation simpleDonation) {
        this.name = name;
        this.team = team;
        this.simpleDonation = simpleDonation;

    }

    public Sponsor(String name, Team team, ComplexDonation complexDonation) {
        this.name = name;
        this.team = team;
        this.complexDonation = complexDonation;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public SimpleDonation getSimpleDonation() {
        return simpleDonation;
    }

    public void setSimpleDonation(SimpleDonation simpleDonation) {
        this.simpleDonation = simpleDonation;
    }

    public ComplexDonation getComplexDonation() {
        return complexDonation;
    }

    public void setComplexDonation(ComplexDonation complexDonation) {
        this.complexDonation = complexDonation;
    }
}
