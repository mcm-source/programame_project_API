package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.Sponsor;
import com.example.programame_project_api.entities.Teacher;
import com.example.programame_project_api.entities.Team;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.repositories.TeamRepository;
import com.example.programame_project_api.repositories.UserRepository;
import com.example.programame_project_api.security.JWTUtil;
import com.example.programame_project_api.servicesTools.ServicesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServicesTools servicesTools;

    public ResponseEntity saveTeam(Map<String, Object> teamData, String token) {


        try {

            Teacher teacher = teacherRepository.findByEmail(servicesTools.extractEmailFromToken(token));

            if (teacher != null) {
                if (!servicesTools.existsTeamName(teamData)) {
                    teamRepository.save(createTeam(teamData, teacher));
                    return servicesTools.createResponseEntity(
                            HttpStatus.OK,
                            "Create team ok");
                } else {
                    return servicesTools.createResponseEntity(
                            HttpStatus.NOT_ACCEPTABLE,
                            "Team name already exist");
                }
            } else {
                return servicesTools.createResponseEntity(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Teacher not exist");
            }

        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());

        }

    }

    public ResponseEntity updateTeam(Map<String, Object> teamData, String token) {

        try {
            if (servicesTools.isUserAdmin(token)) {

                System.out.println("User is admin");
                return updateTeamWithAdminUser(teamData);
            } else {
                return updateTeamFromTeacher(teamData, token);
            }

        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }


    }

    private ResponseEntity updateTeamWithAdminUser(Map<String, Object> teamData) {


        int idTeam = (int) teamData.get("idTeam");

        if (teamRepository.existsById(idTeam)) {
            teamRepository.update(idTeam, teamData);
            return servicesTools.createResponseEntity(
                    HttpStatus.OK,
                    "Update team ok");
        } else {
            return servicesTools.createResponseEntity(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Team doesn´t exist");
        }

    }


    private ResponseEntity updateTeamFromTeacher(Map<String, Object> teamData, String token) {

        Teacher teacher = teacherRepository.findByEmail(servicesTools.extractEmailFromToken(token));

        int idTeam = (int) teamData.get("idTeam");

        if (teacher.haveTheTeam(idTeam)) {
            teamRepository.update(idTeam, teamData);
            return servicesTools.createResponseEntity(
                    HttpStatus.OK,
                    "Update team ok");
        } else {
            return servicesTools.createResponseEntity(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Teacher doesn´t have the team");
        }

    }


    public ResponseEntity deleteTeam(int id, String token) {

        try {

            if (servicesTools.isUserAdmin(token)) {
                return deleteTeamWithAdminUser(id);
            } else {
                return deleteTeamWithGeneralTeacher(id, token);
            }
        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }


    }


    public ResponseEntity getDataOfTeamByIdOfTeacher(int id, String token) {

        try {

            if (servicesTools.isUserAdmin(token)) {
                Teacher teacher = teacherRepository.findById(id);
                List<Team> listTeams = teacher.getListTeams();

                listTeams.forEach(team -> {
                    team.setTeacher(null);
                    team.getListSponsors().forEach(sponsor -> {
                        sponsor.setTeam(null);
                    });
                });

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(listTeams);
            } else {
                return servicesTools.createResponseEntity(
                        HttpStatus.FORBIDDEN,
                        "User doesn´t have permissions");
            }
        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }


    }


    private ResponseEntity deleteTeamWithAdminUser(int id) {

        if (teamRepository.existsById(id)) {
            Team team = teamRepository.findById(id);
            teamRepository.delete(team);
            return servicesTools.createResponseEntity(
                    HttpStatus.OK,
                    "Team delete Ok");
        } else {
            return servicesTools.createResponseEntity(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Team doesn´t exist");
        }

    }


    private ResponseEntity deleteTeamWithGeneralTeacher(int id, String token) {

        Teacher teacher = teacherRepository.findByEmail(servicesTools.extractEmailFromToken(token));

        if (teacher.haveTheTeam(id)) {

            Team team = teamRepository.findById(id);
            teamRepository.delete(team);
            return servicesTools.createResponseEntity(
                    HttpStatus.OK,
                    "Team delete Ok");
        } else {
            return servicesTools.createResponseEntity(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Teacher doesn´t have the team");
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

}
