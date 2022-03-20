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



}
