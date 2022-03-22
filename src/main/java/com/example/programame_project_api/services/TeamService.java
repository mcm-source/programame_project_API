package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.Teacher;
import com.example.programame_project_api.entities.Team;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.repositories.TeamRepository;
import com.example.programame_project_api.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private JWTUtil jwtUtil;

    public ResponseEntity saveTeam(Map<String, Object> teamData, String token) {



        try {


          //  Teacher teacher = teacherRepository.findByEmail((String) teamData.get("email"));
            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));

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
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }

    }

    public ResponseEntity updateTeam(Map<String, Object> teamData,  String token) {

        try {
            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));

            int idTeam = (int) teamData.get("idTeam");

            if (teacher.haveTheTeam(idTeam)) {
                teamRepository.update(idTeam, teamData);
                return createResponseEntity(HttpStatus.OK, "Update team ok");
            } else {
                return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "Teacher doesn´t have the team");
            }

        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


    public ResponseEntity deleteTeam(int id, String token) {

        try {
            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));

            if (teacher.haveTheTeam(id)) {

                Team team = teamRepository.findById(id);
                teamRepository.delete(team);
                return createResponseEntity(HttpStatus.OK, "Team delete Ok");
            } else {
                return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "Teacher doesn´t have the team");
            }

        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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


    private String extractEmailFromToken(String token){

        System.out.println(token);
        String dato =token;
        dato = dato.replace("Bearer ","");
        System.out.println(dato);
         return  jwtUtil.extractUsername(dato);


    }











}
