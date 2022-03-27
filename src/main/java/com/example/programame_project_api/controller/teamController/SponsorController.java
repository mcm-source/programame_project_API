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

    @CrossOrigin(origins = {"https://localhost:8080"})
    @PostMapping("/createSponsor")
    public ResponseEntity<IssueReport> newSponsor(@RequestBody Map<String, Object> SponsorData,
                                                  @RequestHeader (name="Authorization") String token) {

        return  sponsorService.addSponsorToTeam(SponsorData, token);


    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @PostMapping("/updateSponsor")
    public ResponseEntity updateSponsor(@RequestBody Map<String, Object> SponsorData,
                                        @RequestHeader (name="Authorization") String token) {

        return sponsorService.updateSponsor(SponsorData, token);


    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @DeleteMapping("/deleteSponsor/{id}")
    public ResponseEntity deleteSponsor(@PathVariable("id") int id,
                                        @RequestHeader (name="Authorization") String token) {

        return sponsorService.deleteSponsor(id, token);

    }












}
