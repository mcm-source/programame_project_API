package com.example.programame_project_api.controller.teamController;

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

    @CrossOrigin(origins = {"https://localhost:8080"})
    @PostMapping("/createTeam")
    public ResponseEntity newTeam(@RequestBody Map<String, Object> teamData,
                                               @RequestHeader(name = "Authorization") String token) {

        return teamService.saveTeam(teamData, token);


    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @PostMapping("/updateTeam")
    public ResponseEntity updateTeam(@RequestBody Map<String, Object> teamData,
                                     @RequestHeader(name = "Authorization") String token) {

        return teamService.updateTeam(teamData, token);


    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @DeleteMapping("/deleteTeam/{id}")
    public ResponseEntity deleteTeam(@PathVariable("id") int id,
                                     @RequestHeader(name = "Authorization") String token) {

        return teamService.deleteTeam(id, token);

    }


    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/getTeamData/{id}")
    public ResponseEntity listTeacherDataForAdminUser(@PathVariable("id") int id,
                                                      @RequestHeader(name = "Authorization") String token) {

        return teamService.getDataOfTeamByIdOfTeacher(id, token);

    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/sponsorsdata/{teamName}")
    public ResponseEntity getSponsorDataFromTeam(@PathVariable("teamName") String teamName) {

        return teamService.getSponsorsDataOfTeam(teamName);

    }


}
