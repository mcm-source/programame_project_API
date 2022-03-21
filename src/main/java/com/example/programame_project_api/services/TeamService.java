package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.Teacher;
import com.example.programame_project_api.entities.Team;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public ResponseEntity saveTeam(Map<String, Object> teamData) {

        try {

            Teacher teacher = teacherRepository.findByEmail((String) teamData.get("email"));

            if (teacher != null) {
                if (!existsTeamName(teamData)) {
                    teamRepository.save(createTeam(teamData, teacher));
                    return createResponseEntity(HttpStatus.OK, "Create team ok");
                } else {
                    return createResponseEntity(HttpStatus.NOT_ACCEPTABLE, "Team name already exist");
                }
            } else {
                return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "Teacher not exist");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity updateTeam(Map<String, Object> teamData) {

        try {

            int idTeam = (Integer) teamData.get("idTeam");

            if (teamRepository.existsById(idTeam)) {
                teamRepository.update(idTeam, teamData);
                return createResponseEntity(HttpStatus.OK, "Update team ok");
            } else {
                return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "Team not exist");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    public ResponseEntity deleteTeam(int id) {

        try {
            Team team = teamRepository.findById(id);

            if (team != null) {
                teamRepository.delete(team);
                return createResponseEntity(HttpStatus.OK, "Team delete Ok");
            } else {
                return createResponseEntity(HttpStatus.NOT_FOUND, "Team not exist");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }









    private Team createTeam(Map<String, Object> teamData, Teacher teacher) {

        return new Team((String) teamData.get("name"),
                (String) teamData.get("teamMembers"),
                (String) teamData.get("schoolName"),
                (String) teamData.get("location"),
                teacher
        );
    }




    private ResponseEntity createResponseEntity(HttpStatus status, String bodyMessage) {

        return ResponseEntity
                .status(status)
                .body(bodyMessage);


    }

    private boolean existsTeamName(Map<String, Object> teamData) {

        return teamRepository.existsByName((String) teamData.get("name"));
    }


}
