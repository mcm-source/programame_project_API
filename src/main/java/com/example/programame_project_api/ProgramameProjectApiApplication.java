package com.example.programame_project_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProgramameProjectApiApplication {

    public static final String URLCORS = "*";
    public static void main(String[] args) {

        SpringApplication.run(ProgramameProjectApiApplication.class, args);
    }



}
