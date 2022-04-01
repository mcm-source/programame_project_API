package com.example.programame_project_api.controller.teamController;


import com.example.programame_project_api.entities.UserRole;
import com.example.programame_project_api.entities.persistEntities.AuthenticationRequest;
import com.example.programame_project_api.entities.persistEntities.AuthenticationResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.programame_project_api.ProgramameProjectApiApplication.URLCors;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = {URLCors})
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        try {

            if(!userRepository.existsByUsername("test1234")){
                AuthenticationRequest user = new AuthenticationRequest("test1234",passwordEncoder.encode("1234"),
                        UserRole.ADMINISTRATOR);

                userRepository.save(user);
            }

                UsernamePasswordAuthenticationToken UsuarioConPass = new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword());
                authenticationManager.authenticate(UsuarioConPass);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                UserDetails userDetails = userData.loadUserByUsername(request.getUsername());
                String jwt = jwtUtil.generateToken(userDetails);
                return new ResponseEntity<>(new AuthenticationResponse(jwt), headers, HttpStatus.OK);

        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
    @CrossOrigin(origins = {URLCors})
    @PostMapping("/createUser")
    public ResponseEntity newUser(@RequestBody Map<String, Object> user,
                                               @RequestHeader(name = "Authorization") String token) {

        return  userService.createUser(user,token);


    }
    @CrossOrigin(origins = {URLCors})
    @PostMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody Map<String, Object> user,
                                     @RequestHeader(name = "Authorization") String token) {

       return  userService.updateUser(user, token);


    }

    @CrossOrigin(origins = {URLCors})
    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestBody String email,
                                     @RequestHeader(name = "Authorization") String token) {


       return  userService.deleteUser(email, token);
    }

    @CrossOrigin(origins = {URLCors})
    @GetMapping("/isUserAdmin")
    public ResponseEntity isUserAdmin(@RequestHeader(name = "Authorization") String token) {

        return userService.isUserAdmin(token);

    }


    @CrossOrigin(origins = {URLCors})
    @GetMapping("/getUserNameFromToken")
    public ResponseEntity getUserNameFromToken(@RequestHeader(name = "Authorization") String token) {

        return userService.getNameUserFromToken(token);

    }



}
