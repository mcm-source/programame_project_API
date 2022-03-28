package com.example.programame_project_api.entities.responseEntities;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamSponsorData {


    private String teamName;
    private List<SponsorData> sponsorDataList;
    public TeamSponsorData() {
    }

    public TeamSponsorData(String teamName, List<SponsorData> sponsorDataList) {
        this.teamName = teamName;
        this.sponsorDataList = sponsorDataList;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<SponsorData> getSponsorDataList() {
        return sponsorDataList;
    }

    public void setSponsorDataList(List<SponsorData> sponsorDataList) {
        this.sponsorDataList = sponsorDataList;
    }
}
