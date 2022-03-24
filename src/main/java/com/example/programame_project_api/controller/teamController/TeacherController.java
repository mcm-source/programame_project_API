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

    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @GetMapping("/listDataForTeamsTable")
    public ResponseEntity listTeamsAndSponsorFromTeacher(@RequestHeader(name="Authorization") String token) {

        return  teacherService.listDataFromTeacher(token);

    }

    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @GetMapping("/listDataForOverallTable")
    public ResponseEntity listDataForOverallTable() {

        return  teacherService.listDataForOVerallTable();

    }

    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @GetMapping("/listTeacherData")
    public ResponseEntity listTeacherDataForAdminUser(@RequestHeader(name="Authorization") String token) {

        return  teacherService.listTeacherData(token);

    }



}
