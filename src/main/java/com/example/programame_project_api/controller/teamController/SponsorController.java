package com.example.programame_project_api.controller.teamController;

import com.example.programame_project_api.entities.IssueReport;
import com.example.programame_project_api.services.SponsorService;
import com.example.programame_project_api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @PostMapping("/createSponsor")
    public ResponseEntity<IssueReport> newSponsor(@RequestBody Map<String, Object> SponsorData) {

        return  sponsorService.addSponsorToTeam(SponsorData);


    }

    @PostMapping("/updateSponsor")
    public ResponseEntity updateSponsor(@RequestBody Map<String, Object> SponsorData) {

        return sponsorService.updateSponsor(SponsorData);


    }

    @DeleteMapping("/deleteSponsor/{id}")
    public ResponseEntity deleteSponsor(@PathVariable("dni") int id) {

        return sponsorService.deleteSponsor(id);

    }
}
