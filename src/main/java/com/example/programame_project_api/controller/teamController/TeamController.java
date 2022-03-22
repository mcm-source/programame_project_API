package com.example.programame_project_api.controller.teamController;

import com.example.programame_project_api.entities.IssueReport;
import com.example.programame_project_api.entities.Team;
import com.example.programame_project_api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseEntity<IssueReport> newTeam(@RequestBody Map<String, Object> teamData,
                                               @RequestHeader (name="Authorization") String token) {

        return  teamService.saveTeam(teamData, token);


    }

    @PostMapping("/updateTeam")
    public ResponseEntity updateTeam(@RequestBody Map<String, Object> teamData,
                                     @RequestHeader (name="Authorization") String token) {

        return teamService.updateTeam(teamData, token);


    }

    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity deleteTeam(@PathVariable("id") int id,
                                       @RequestHeader (name="Authorization") String token) {

        return teamService.deleteTeam(id, token);

    }





}
