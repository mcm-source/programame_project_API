package com.example.programame_project_api.servicesTools;

import com.example.programame_project_api.entities.AuthenticationRequest;
import com.example.programame_project_api.entities.UserRole;
import com.example.programame_project_api.repositories.TeamRepository;
import com.example.programame_project_api.repositories.UserRepository;
import com.example.programame_project_api.security.JWTUtil;
import com.example.programame_project_api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServicesTools {


    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;


    public boolean existsTeamName(Map<String, Object> teamData) {

        return teamRepository.existsByName((String) teamData.get("name"));
    }

    public String extractEmailFromToken(String token) {

        System.out.println(token);
        String dato = token;
        dato = dato.replace("Bearer ", "");
        System.out.println(dato);
        return jwtUtil.extractUsername(dato);


    }

    public boolean isUserAdmin(String token) {

        AuthenticationRequest authenticationRequest = userRepository.findByUsername(extractEmailFromToken(token));
        return authenticationRequest.getUserRole() == UserRole.ADMINISTRATOR;


    }


    public ResponseEntity createResponseEntity(HttpStatus status, String bodyMessage) {

        return ResponseEntity
                .status(status)
                .body(bodyMessage);

    }



}
