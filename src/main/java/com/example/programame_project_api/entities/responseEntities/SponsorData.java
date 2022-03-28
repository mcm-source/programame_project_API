package com.example.programame_project_api.entities.responseEntities;

import org.springframework.stereotype.Component;

@Component
public class SponsorData {

    private String name;
    private SimpleDonationData simpleDonation;
    private ComplexDonationData complexDonation;

    public SponsorData() {
    }

    public SponsorData(String name, SimpleDonationData simpleDonation) {
        this.name = name;
        this.simpleDonation = simpleDonation;
    }

    public SponsorData(String name, ComplexDonationData complexDonation) {
        this.name = name;
        this.complexDonation = complexDonation;
    }

    public String getName() {
        return name;
    }

    public SimpleDonationData getSimpleDonation() {
        return simpleDonation;
    }

    public ComplexDonationData getComplexDonation() {
        return complexDonation;
    }
}
