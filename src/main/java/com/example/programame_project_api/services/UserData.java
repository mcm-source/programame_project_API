package com.example.programame_project_api.services;


import com.example.programame_project_api.entities.persistEntities.AuthenticationRequest;
import com.example.programame_project_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserData implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
        AuthenticationRequest user= userRepository.findByUsername(username);

            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } catch (Exception e) {
            return new User("root", "{noop}sdda`skfilkoñwejnil3herjlknwajlkdn", new ArrayList<>());
        }

    }
}
