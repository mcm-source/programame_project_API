package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.Teacher;
import com.example.programame_project_api.entities.Team;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JWTUtil jwtUtil;


    public ResponseEntity listDataFromTeacher(String token) {

        try {

            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(clearValuesFromListData(teacher.getListTeams()));


        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


    private  List clearValuesFromListData(List<Team> listData){

       listData.forEach(team -> {
            team.setTeacher(null);
            team.getListSponsors().forEach(sponsor -> {
                sponsor.setTeam(null);
                if (sponsor.getSimpleDonation() != null) {
                    sponsor.getSimpleDonation().setSponsor(null);
                }
                if (sponsor.getComplexDonation() != null) {
                    sponsor.getComplexDonation().setSponsor(null);
                }
            });

        });

       return  listData;

    }


    private ResponseEntity createResponseEntity(HttpStatus status, String bodyMessage) {

        return ResponseEntity
                .status(status)
                .body(bodyMessage);


    }

    private String extractEmailFromToken(String token) {

        System.out.println(token);
        String dato = token;
        dato = dato.replace("Bearer ", "");
        System.out.println(dato);
        return jwtUtil.extractUsername(dato);


    }
}
