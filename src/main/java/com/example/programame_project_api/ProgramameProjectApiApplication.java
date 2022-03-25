package com.example.programame_project_api;

import com.example.programame_project_api.entities.AuthenticationRequest;
import com.example.programame_project_api.entities.UserRole;
import com.example.programame_project_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Column;

@SpringBootApplication
public class ProgramameProjectApiApplication {

    



    public static void main(String[] args) {
        SpringApplication.run(ProgramameProjectApiApplication.class, args);
    }

}
