package com.example.programame_project_api.entities;

import org.springframework.stereotype.Component;


public class ContainerOverallTable {


    private String teamName;
    private  String schoolName;
    private  String sponsorName;
    private  SimpleDonation simpleDonation;
    private  ComplexDonation complexDonation;
    private  double totalAcount;

    public ContainerOverallTable(String teamName,
                                 String schoolName,
                                 String sponsorName,
                                 SimpleDonation simpleDonation,
                                 double totalAcount) {
        this.teamName = teamName;
        this.schoolName = schoolName;
        this.sponsorName = sponsorName;
        this.simpleDonation = simpleDonation;
        this.totalAcount = totalAcount;
    }

    public ContainerOverallTable(String teamName,
                                 String schoolName,
                                 String sponsorName,
                                 ComplexDonation complexDonation,
                                 double totalAcount) {
        this.teamName = teamName;
        this.schoolName = schoolName;
        this.sponsorName = sponsorName;
        this.complexDonation = complexDonation;
        this.totalAcount = totalAcount;
    }
}
