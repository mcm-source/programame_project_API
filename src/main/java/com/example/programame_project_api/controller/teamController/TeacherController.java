package com.example.programame_project_api.controller.teamController;

import com.example.programame_project_api.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/listDataForTeamsTable")
    public ResponseEntity listTeamsAndSponsorFromTeacher(@RequestHeader(name="Authorization") String token) {

        return  teacherService.listDataFromTeacher(token);

    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/listDataForOverallTable")
    public ResponseEntity listDataForOverallTable() {

        return  teacherService.listDataForOVerallTable();

    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/listTeacherData")
    public ResponseEntity listTeachersDataForAdminUser(@RequestHeader(name="Authorization") String token) {

        return  teacherService.listTeacherData(token);

    }

    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/getTeacherName")
    public ResponseEntity getTeacherNameFromToken(@RequestHeader(name="Authorization") String token) {

        return  teacherService.getNameOfTeacherFromToken(token);

    }


    @CrossOrigin(origins = {"https://localhost:8080"})
    @GetMapping("/getTeacherNameWithId/{id}")
    public ResponseEntity getTeacherNameFromId(@PathVariable("id") int id,
                                                      @RequestHeader(name = "Authorization") String token) {

        return teacherService.getNameOfTeacherFromId(id, token);

    }



}
