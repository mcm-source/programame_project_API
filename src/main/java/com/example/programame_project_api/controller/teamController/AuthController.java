package com.example.programame_project_api.controller.teamController;


import com.example.programame_project_api.entities.AuthenticationRequest;
import com.example.programame_project_api.entities.AuthenticationResponse;
import com.example.programame_project_api.entities.IssueReport;
import com.example.programame_project_api.repositories.UserRepository;
import com.example.programame_project_api.security.JWTUtil;
import com.example.programame_project_api.services.UserData;
import com.example.programame_project_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserData userData;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        try {
            AuthenticationRequest bloqueado= userRepository.findByUsername(request.getUsername());

//            Esto manda a validar el usuario introducido
                UsernamePasswordAuthenticationToken UsuarioConPass = new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword());
                authenticationManager.authenticate(UsuarioConPass);

//            Esto vale para entrar despues con el usuario
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                UserDetails userDetails = userData.loadUserByUsername(request.getUsername());
                String jwt = jwtUtil.generateToken(userDetails);
                return new ResponseEntity<>(new AuthenticationResponse(jwt), headers, HttpStatus.OK);

        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @PostMapping("/createUser")
    public ResponseEntity<IssueReport> newUser(@RequestBody Map<String, Object> user,
                                               @RequestHeader(name = "Authorization") String token) {

        return  userService.createUser(user,token);


    }
    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @PostMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody Map<String, Object> user,
                                     @RequestHeader(name = "Authorization") String token) {

       return  userService.updateUser(user, token);


    }

    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestBody String email,
                                     @RequestHeader(name = "Authorization") String token) {


       return  userService.deleteUser(email, token);
    }


    @CrossOrigin(origins = {"http://localhost:3001","http://localhost:8080"})
    @GetMapping("/listUserData")
    public ResponseEntity listUserData() {

        try {

            List<AuthenticationRequest> userList = (List<AuthenticationRequest>) userRepository.findAll();


            if (userList != null ) {

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(userList);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Data not avaible");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
