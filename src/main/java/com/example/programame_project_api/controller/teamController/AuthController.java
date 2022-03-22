package com.example.programame_project_api.controller.teamController;


import com.example.programame_project_api.entities.AuthenticationRequest;
import com.example.programame_project_api.entities.AuthenticationResponse;
import com.example.programame_project_api.entities.IssueReport;
import com.example.programame_project_api.repositories.UserRepository;
import com.example.programame_project_api.security.JWTUtil;
import com.example.programame_project_api.services.UserData;
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


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        try {
            AuthenticationRequest bloqueado= userRepository.findByUsername(request.getUsername());
            if(!bloqueado.getBlock()) {
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
            }else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/createUser")
    public ResponseEntity<IssueReport> newUser(@RequestBody Map<String, Object> user) {
        try {
            String rootUser= jwtUtil.extractUsername((String)user.get("token"));

            AuthenticationRequest usuarioExiste = userRepository.findByUsername((String)user.get("email"));

            if(user.get("password").equals(user.get("passwordRepeat")) && usuarioExiste==null && rootUser.equals("root")) {
                AuthenticationRequest User = new AuthenticationRequest(((String) user.get("email")),
                        ((String) user.get("password"))
                );
                this.userRepository.save(User);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody Map<String, Object> user) {

        try {
            String rootUser= jwtUtil.extractUsername((String)user.get("token"));
            if (userRepository.existsByUsername((String)user.get("email")) && rootUser.equals("root")) {

                AuthenticationRequest usuario= new AuthenticationRequest((String)user.get("email"),(String)user.get("password"));
                userRepository.update(usuario);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Update ok");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Update has fault");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestBody Map<String, Object> user) {


        try {
            System.out.println("DASdas");
            String rootUser= jwtUtil.extractUsername((String)user.get("token"));
            System.out.println(rootUser);
            AuthenticationRequest usuario = userRepository.findByUsername((String)user.get("email"));

            if (usuario != null && rootUser.equals("root")) {
                userRepository.delete(usuario);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Client delete Ok");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Client not exist");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/blockUser")
    public ResponseEntity blockUser(@RequestBody Map<String, Object> user) {

        try {
            String rootUser= jwtUtil.extractUsername((String)user.get("token"));
            if (userRepository.existsByUsername((String)user.get("email")) && rootUser.equals("root")) {

                AuthenticationRequest usuario= userRepository.findByUsername((String)user.get("email"));
                if(usuario.getBlock()){
                    usuario.setBlock(false);
                }else {
                    usuario.setBlock(true);
                }
                userRepository.update(usuario);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Update ok");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Update has fault");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


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
