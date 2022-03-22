package com.example.programame_project_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Column;

@SpringBootApplication
public class ProgramameProjectApiApplication {

    
   public   final static int NUMBEROFSIMPLEPROBLEM = 30;
    public final static int NUMBEROFMEDIUMPROBLEM = 30;
    public final static int NUMBEROFHARDPROBLEM = 30;

    public static void main(String[] args) {
        SpringApplication.run(ProgramameProjectApiApplication.class, args);
    }

}
