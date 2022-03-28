package com.example.programame_project_api.entities.responseEntities;

import com.example.programame_project_api.entities.persistEntities.SimpleDonation;
import com.example.programame_project_api.entities.persistEntities.ComplexDonation;
import org.springframework.stereotype.Component;

@Component
public class ContainerOverallTable {


    private String teamName;
    private  String schoolName;
    private  String sponsorName;
    private SimpleDonation simpleDonation;
    private ComplexDonation complexDonation;


    public ContainerOverallTable() {
    }

    public ContainerOverallTable(String teamName,
                                 String schoolName,
                                 String sponsorName,
                                 SimpleDonation simpleDonation) {
        this.teamName = teamName;
        this.schoolName = schoolName;
        this.sponsorName = sponsorName;
        this.simpleDonation = simpleDonation;

    }

    public ContainerOverallTable(String teamName,
                                 String schoolName,
                                 String sponsorName,
                                 ComplexDonation complexDonation) {
        this.teamName = teamName;
        this.schoolName = schoolName;
        this.sponsorName = sponsorName;
        this.complexDonation = complexDonation;

    }

    public String getTeamName() {
        return teamName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public SimpleDonation getSimpleDonation() {
        return simpleDonation;
    }

    public ComplexDonation getComplexDonation() {
        return complexDonation;
    }


}
