package com.example.programame_project_api.controller.teamController;

import com.example.programame_project_api.services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.programame_project_api.ProgramameProjectApiApplication.URLCORS;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @CrossOrigin(origins = {URLCORS})
    @PostMapping("/createSponsor")
    public ResponseEntity newSponsor(@RequestBody Map<String, Object> SponsorData,
                                                  @RequestHeader (name="Authorization") String token) {

        return  sponsorService.addSponsorToTeam(SponsorData, token);


    }

    @CrossOrigin(origins = {URLCORS})
    @PostMapping("/updateSponsor")
    public ResponseEntity updateSponsor(@RequestBody Map<String, Object> SponsorData,
                                        @RequestHeader (name="Authorization") String token) {

        return sponsorService.updateSponsor(SponsorData, token);


    }

    @CrossOrigin(origins = {URLCORS})
    @DeleteMapping("/deleteSponsor/{id}")
    public ResponseEntity deleteSponsor(@PathVariable("id") int id,
                                        @RequestHeader (name="Authorization") String token) {

        return sponsorService.deleteSponsor(id, token);

    }












}
