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
    public ResponseEntity<IssueReport> newTeam(@RequestBody Map<String, Object> teamData) {

        return  teamService.saveTeam(teamData);


    }

    @PostMapping("/updateTeam")
    public ResponseEntity updateTeam(@RequestBody Map<String, Object> teamData) {

        return teamService.updateTeam(teamData);


    }

    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity deleteClient(@PathVariable("dni") int id) {

        return teamService.deleteTeam(id);

    }





}
