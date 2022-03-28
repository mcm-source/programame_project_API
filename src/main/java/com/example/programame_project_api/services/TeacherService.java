package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.persistEntities.*;
import com.example.programame_project_api.entities.responseEntities.ContainerOverallTable;
import com.example.programame_project_api.repositories.SponsorRepository;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.repositories.TeamRepository;
import com.example.programame_project_api.security.JWTUtil;
import com.example.programame_project_api.servicesTools.ServicesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private ServicesTools servicesTools;


    public ResponseEntity listDataFromTeacher(String token) {

        try {

            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(prepareDataForResponse(teacher.getListTeams()));


        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


    public ResponseEntity listDataForOVerallTable() {

        try {

            List<Sponsor> listSponsor = sponsorRepository.findAll();
            List<ContainerOverallTable> containerOverallTable = new ArrayList<>();

            for (Sponsor sponsor : listSponsor) {
                if (sponsor.getSimpleDonation() != null) {
                    containerOverallTable.add(doContainerDataForSimpleDonation(sponsor));
                } else if (sponsor.getComplexDonation() != null) {
                    containerOverallTable.add(doContainerDataForComplexDonation(sponsor));
                }
            }


            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(containerOverallTable);

        } catch (Exception e) {
            return createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }


    }


    public ResponseEntity listTeacherData(String token) {

        try {

            if (servicesTools.isUserAdmin(token)) {
                List<Teacher> listTeachers = teacherRepository.findAll();
                for (Teacher teacher: listTeachers){
                    teacher.setListTeams(null);
                }
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(listTeachers);
            } else {
                return  servicesTools.createResponseEntity(
                        HttpStatus.FORBIDDEN,
                        "User doesn´t have permissions");
            }

        } catch (Exception e) {
            return  servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }


    }

    public ResponseEntity getNameOfTeacherFromToken(String token){

        try {

          Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));
          return     servicesTools.createResponseEntity(
                    HttpStatus.OK,
                    teacher.getName());


        } catch (Exception e) {
            return  servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    public ResponseEntity getNameOfTeacherFromId(int id, String token){

        try {

            Teacher teacher = teacherRepository.findById(id);
            if (teacher !=null){
                return     servicesTools.createResponseEntity(
                        HttpStatus.OK,
                        teacher.getName());
            }else {
                return servicesTools.createResponseEntity(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Teacher doesn´t exists");

            }


        } catch (Exception e) {
            return  servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }



    private ContainerOverallTable doContainerDataForSimpleDonation(Sponsor sponsor) {

        SimpleDonation simpleDonation = sponsor.getSimpleDonation();
        simpleDonation.setSponsor(null);



        return new ContainerOverallTable(
                sponsor.getTeam().getName(),
                sponsor.getTeam().getSchoolName(),
                sponsor.getName(),
                simpleDonation);


    }

    private ContainerOverallTable doContainerDataForComplexDonation(Sponsor sponsor) {


        ComplexDonation complexDonation = sponsor.getComplexDonation();
        complexDonation.setSponsor(null);



        return new ContainerOverallTable(
                sponsor.getTeam().getName(),
                sponsor.getTeam().getSchoolName(),
                sponsor.getName(),
                complexDonation);


    }


    private List prepareDataForResponse(List<Team> listData) {


        listData.forEach(team -> {
            team.setTeacher(null);
            team.getListSponsors().forEach(sponsor -> {
                sponsor.setTeam(null);
                if (sponsor.getSimpleDonation() != null) {
                    SimpleDonation simpleDonation = sponsor.getSimpleDonation();
                    sponsor.getSimpleDonation().setSponsor(null);
                }
                if (sponsor.getComplexDonation() != null) {
                    ComplexDonation complexDonation = sponsor.getComplexDonation();
                    sponsor.getComplexDonation().setSponsor(null);
                }
            });

        });

        return listData;

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
