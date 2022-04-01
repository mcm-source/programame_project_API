package com.example.programame_project_api.entities;

import com.example.programame_project_api.entities.persistEntities.AuthenticationRequest;
import com.example.programame_project_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAdminInitialization implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

//        AuthenticationRequest user = new AuthenticationRequest("maria",
//                passwordEncoder.encode("1234"),
//                UserRole.ADMINISTRATOR);
//        userRepository.save(user);

    }
}
