package com.example.programame_project_api.controller.teamController;

import com.example.programame_project_api.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/listDataForTeamsTable")
    public ResponseEntity listTeamsAndSponsorFromTeacher(@RequestHeader(name="Authorization") String token) {

        return  teacherService.listDataFromTeacher(token);

    }


    @GetMapping("/listDataForOverallTable")
    public ResponseEntity listDataForOverallTable() {

        return  teacherService.listDataForOVerallTable();

    }



}
